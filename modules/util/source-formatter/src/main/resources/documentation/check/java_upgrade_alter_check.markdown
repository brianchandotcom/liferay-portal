## JavaUpgradeAlterCheck

### AlterColumnType

When using `AlterColumnType` in Upgrade classes:

* Ensure that the column field names match the ones in the corresponding
  upgrade table class.
* Only use the following data types: `BLOB`, `SBLOB`, `BOOLEAN`, `DATE`,
  `DOUBLE`, `INTEGER`, `LONG`, `STRING`, `TEXT`, `VARCHAR`.
* When using string data types (`VARCHAR`, `STRING`, `TEXT`), we must
  specify whether they are nullable or not by appending `null` or `not null`.

### Examples

#### Incorrect Column Name

Incorrect:

In `CalendarNotificationTemplateUpgradeProcess.java`

```java
alter(
	CalendarNotificationTemplateTable.class,
	new AlterColumnType(
		"notificationtypesettings", "VARCHAR(150) null"));
```

In `util/CalendarNotificationTemplateTable.java`

```java
public class CalendarNotificationTemplateTable {

	public static final String TABLE_NAME = "CalendarNotificationTemplate";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"calendarNotificationTemplateId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"calendarId", Types.BIGINT}, {"notificationType", Types.VARCHAR},
		{"notificationTypeSettings", Types.VARCHAR},
		{"notificationTemplateType", Types.VARCHAR}, {"subject", Types.VARCHAR},
		{"body", Types.CLOB}, {"lastPublishDate", Types.TIMESTAMP}
	};
	...
```

Correct:

```java
alter(
	CalendarNotificationTemplateTable.class,
	new AlterColumnType(
		"notificationTypeSettings", "VARCHAR(150) null"));
```

#### Wrong Data Type

Incorrect:

```java
alter(
	JournalArticleTable.class,
	new AlterColumnType("version", "FLOAT"));
```

Correct:

```java
alter(
	JournalArticleTable.class,
	new AlterColumnType("version", "DOUBLE"));
```

#### String type does not have `null` or `not null`

Incorrect:

```java
alter(
	BlogsEntryTable.class,
	new AlterColumnType("urlTitle", "VARCHAR(255)"));
```

Correct:

```java
alter(
	BlogsEntryTable.class,
	new AlterColumnType("urlTitle", "VARCHAR(255) null"));
```