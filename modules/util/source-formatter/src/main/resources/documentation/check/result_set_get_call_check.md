## ResultSetGetCallCheck

Using `resultSet.getInt("count")` for a count result is risky as it will fail
if the value exceeds the int range.

It's safer to use `resultSet.getLong("count")` instead.

### Example

Incorrect:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    int count = resultSet.getInt("count");
}
```

Correct:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    long count = resultSet.getLong("count");
}
```

---

Use the simple column name instead of column index.

### Example

Incorrect:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    long count = resultSet.getLong(1);
}
```

Correct:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    long count = resultSet.getLong("count");
}
```