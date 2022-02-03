## JavaUpgradeClassCheck

### LocaleUtil.getDefault

In Upgrade classes, we should not be making calls to
`com.liferay.portal.kernel.util.LocaleUtil.getDefault` in order to retrieve the
decault locale. At the time the upgrade is executed, the thread locale is not
initialized yet, and therefore the wrong value can be returned.

Instead, use `com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil` to
retrieve the locale.

### Example

Incorrect:

```java
Locale locale = LocaleUtil.getDefault();
```

Correct:

```java
String languageId = UpgradeProcessUtil.getDefaultLanguageId(companyId);

Locale locale = LocaleUtil.fromLanguageId(languageId);
```

## Registering classes

When the order of Upgrade classes matter, make it clear by breaking them up with
a minor version increment when registering the classes.

### Example

Incorrect:

```java
registry.register(
    "com.liferay.portal.workflow.kaleo.service", "1.1.0", "1.2.0",
    new UpgradeSchema(), new UpgradeKaleoLog(),
    new UpgradeKaleoNotificationRecipient());
```

Correct:

```java
registry.register(
    "com.liferay.portal.workflow.kaleo.service", "1.1.0", "1.2.0",
    new UpgradeSchema());

registry.register(
    "com.liferay.portal.workflow.kaleo.service", "1.2.0", "1.2.1",
    new UpgradeKaleoLog(), new UpgradeKaleoNotificationRecipient());
```

## ServiceUtil

In Upgrade classes, we should not be making calls to `*ServiceUtil` classes.
When a column has been added in the new version, it will do so via an
`ALTER TABLE` sql statement. At this point, in the upgrade code, that column has
not been added, but the hibernate XML file assumes it is there in
`portal-hbm.xml`. Any `SELECT` or `UPDATE` call via `*ServiceUtil` methods will
fail.

We need to do it manually via SQL statements.

## Timestamp vs. Date

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