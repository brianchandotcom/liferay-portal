## Timestamp

In Upgrade classes use the type `Timestamp` instead of the type `Date`.
Using `Timestamp` can result in a `java.lang.ClassCastException` when using
`SQL Server`.

### Example

Incorrect:

```java
ps.setDate(1, date);

...

Date createDate = rs.getDate("createDate");
```

Correct:

```java
ps.setTimestamp(1, timestamp);

...

Timestamp timestamp = rs.getTimestamp("createDate");
```