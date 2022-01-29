## JavaUpgradeIndexCheck

Creating or dropping Service Builder indexes manually during an UpgradeProcess
class is not necessary. Liferay reviews and syncs all these indexes after
executing an upgrade process.

### Special cases

If you need to create a temporary index due to performance reasons you have to
name it IX_TEMP and drop it after the upgrade logic.

#### Example:

```java
runSQL("create index IX_TEMP on XXX");

try {
    // Logic here
}
finally {
    runSQL("drop index IX_TEMP on XXX");
}
```

If you want to force the regeneration of Service Builder indexes during an
upgrade process, you can achieve it using the method:
_com.liferay.portal.kernel.upgrade.UpgradeProcess.updateIndexes_

#### Example

```java
updateIndexes(GroupTable.class);
```

If you want to force the regeneration of Service Builder indexes with no upgrade
process, you can just register a DummyUpgradeStep and that will force the
indexes update.

#### Example
```java
registry.register("0.0.0", "1.0.0", new DummyUpgradeStep());
```