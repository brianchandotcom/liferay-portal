## Upgrade

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