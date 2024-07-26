import java.util.*;

public class Tercepat {
    static class Node {
        int x, y;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static List<String> findShortestPath(char[][] grid, int startX, int startY, int endX, int endY) {
        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(startX, startY));
        visited[startX][startY] = true;

        int[] dx = { -1, 1, 0, 0 };
        int[] dy = { 0, 0, -1, 1 };
        String[] directions = { "atas", "bawah", "kiri", "kanan" };

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node.x == endX && node.y == endY) {
                // Reconstruct path
                List<String> path = new ArrayList<>();
                while (node != null) {
                    if (node.parent != null) {
                        int directionIndex = getDirection(node.parent.x, node.parent.y, node.x, node.y, dx, dy);
                        path.add(directions[directionIndex]);
                    }
                    node = node.parent;
                }
                Collections.reverse(path);
                return path;
            }

            for (int i = 0; i < 4; i++) {
                int newX = node.x + dx[i];
                int newY = node.y + dy[i];

                if (isValid(grid, newX, newY, visited)) {
                    visited[newX][newY] = true;
                    Node newNode = new Node(newX, newY);
                    newNode.parent = node;
                    queue.add(newNode);
                }
            }
        }

        return null;
    }

    private static boolean isValid(char[][] grid, int x, int y, boolean[][] visited) {
        int rows = grid.length;
        int cols = grid[0].length;
        return x >= 0 && x < rows && y >= 0 && y < cols && grid[x][y] != '#' && !visited[x][y];
    }

    private static int getDirection(int px, int py, int cx, int cy, int[] dx, int[] dy) {
        for (int i = 0; i < 4; i++) {
            if (px + dx[i] == cx && py + dy[i] == cy) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        String line;

        while (!(line = scanner.nextLine()).equals("OK")) {
            lines.add(line);
        }

        int rows = lines.size();
        int cols = lines.get(0).length();

        char[][] grid = new char[rows][cols];
        int startX = 0, startY = 0, endX = 0, endY = 0;

        for (int i = 0; i < rows; i++) {
            line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == '^') {
                    startX = i;
                    startY = j;
                } else if (grid[i][j] == '*') {
                    endX = i;
                    endY = j;
                }
            }
        }

        List<String> path = findShortestPath(grid, startX, startY, endX, endY);

        if (path != null) {
            int count = 1;
            String prevStep = path.get(0);
            for (int i = 1; i < path.size(); i++) {
                if (path.get(i).equals(prevStep)) {
                    count++;
                } else {
                    System.out.println(count + " " + prevStep);
                    prevStep = path.get(i);
                    count = 1;
                }
            }
            System.out.println(count + " " + prevStep);
            System.out.println(path.size() + " langkah");
        } else {
            System.out.println("Tidak ada jalan");
        }

        scanner.close();
    }
}
