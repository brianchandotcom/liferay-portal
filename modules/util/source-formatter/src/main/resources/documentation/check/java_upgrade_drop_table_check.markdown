## JavaUpgradeDropTableCheck

The SQL query `drop table if exists <table>` does not work for every database
(e.g. SQL Server). To handle this, Liferay provides the SQL macro
`DROP_TABLE_IF_EXISTS(<table>)`, which automatically translates the query to the
SQL dialect of the underlying database. Thus, the macro should be used when
writing upgrade processes that removes tables from the database.

#### Example:

Incorrect:

```java
public class AccountUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() {
		runSQL("drop table if exists AccountEntry, AccountGroup");
	}

}
```

Correct:

```java
public class AccountUpgradeProcess extends UpgradeProcess {

	@Override
	public void doUpgrade() {
		runSQL("DROP_TABLE_IF_EXISTS(AccountEntry, AccountGroup)");
	}

}
```