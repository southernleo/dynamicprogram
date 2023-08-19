 import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class halil_shapuk_2017510094 {
    public static int DP(int n, int p, int c, List<Integer> demands, List<Integer> salaries) {
        int[] dp = new int[n + 1]; // Creating an array to store the dynamic programming values
        dp[0] = 0; // Base case: no cost for year 0

        for (int i = 1; i <= n; i++) {
            int demand = demands.get(i - 1);// Getting the demand for the current year
            int salary = (i <= salaries.size()) ? salaries.get(i - 1) : salaries.get(salaries.size() - 1); // Getting the salary for the current year or using the last available salary

            int promote = Math.min(p, demand); // Calculating the number of players to promote (minimum of p and demand)
            int hireCoaches = Math.max(0, demand - p);// Calculating the number of coaches to hire (demand minus p, capped at 0)
            int keepUnrented = Math.max(0, p - demand) * salary;// Calculating the cost of keeping unrented players (maximum of 0 and p minus demand, multiplied by the salary)

            dp[i] = dp[i - 1] + hireCoaches * c + keepUnrented; // Updating the dynamic programming value for the current year
            dp[i] = Math.min(dp[i], dp[i - 1] + promote * salary);// Taking the minimum between the previous year's value plus the promotion cost and the current year's value
        }

        return dp[n];// Returning the minimum cost for n years
    }

    public static void main(String[] args) {
        int n = 3; // Number of years
        int p = 5; // Number of players to promote
        int c = 10; // Coach cost

        List<Integer> demands = readPlayerDemands("yearly_player_demand.txt");
        List<Integer> salaries = readPlayerSalaries("players_salary.txt");

        int cost = DP(n, p, c, demands, salaries);// Calculating the minimum cost using dynamic programming
        System.out.println("DP Results: " + cost);// Printing the result
    }

    private static List<Integer> readPlayerDemands(String fileName) {
        List<Integer> demands = new ArrayList<>(); // Creating a new ArrayList to store the demands

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = reader.readLine();// Reading the first line (header) and discarding it
            while ((line = reader.readLine()) != null) {// Reading the remaining lines until the end of the file
                String[] parts = line.trim().split("\t"); // Splitting the line by tab ('\t') delimiter
                int year = Integer.parseInt(parts[0]); // Parsing the year from the first part
                int demand = Integer.parseInt(parts[1]);// Parsing the demand from the second part
                demands.add(demand);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return demands;
    }

    private static List<Integer> readPlayerSalaries(String fileName) {
        List<Integer> salaries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = reader.readLine();// Reading the first line (header) and discarding it
            while ((line = reader.readLine()) != null) {// Reading the remaining lines until the end of the file
                String[] parts = line.trim().split("\t"); // Splitting the line by tab ('\t') delimiter
                int year = Integer.parseInt(parts[0]); // Parsing the year from the first part
                int salary = Integer.parseInt(parts[1]);// Parsing the salary from the second part
                salaries.add(salary);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return salaries;
    }
}
