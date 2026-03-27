## ResultSetGetCallCheck

When calling `java.sql.Connection.prepareStatement(String sql)` with `sql` of
the format `select count(*)`, we should use the type `int` when retrieving the
value for `count` instead of `long`.

The call `long count = recordSet.getLong(1)` will fail with a
`java.lang.ClassCastException`.

See <https://liferay.atlassian.net/browse/LPSA-19410>.

### Example

Incorrect:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    long count = resultSet.getLong("count");
}
```

Correct:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    int count = resultSet.getInt("count");
}
```

---

Use the simple column name instead of column index.

### Example

Incorrect:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    int count = resultSet.getInt(1);
}
```

Correct:

```java
PreparedStatement preparedStatement =
    connection.prepareStatement("select count(*) as count from Table");

ResultSet resultSet = preparedStatement.executeQuery();

if (resultSet.next()) {
    int count = resultSet.getInt("count");
}
```