## SQL Count Value

When calling `java.sql.Connection.prepareStatement(String sql)` with `sql` of
the format `SELECT COUNT(*)`, we should use the type `int` when retrieving the
value for `count` instead of `long`.

The call `long count = recordSet.getLong(1)` will fail with a
`java.lang.ClassCastException`.

### Example

Incorrect:

```java
PreparedStatement ps = con.prepareStatement("select count(*) from Table");

ResultSet rs = ps.executeQuery();

if (rs.next()) {
    long count = rs.getLong(1);
}
```

Correct:

```java
PreparedStatement ps = con.prepareStatement("select count(*) from Table");

ResultSet rs = ps.executeQuery();

if (rs.next()) {
    int count = rs.getInt(1);
}
```