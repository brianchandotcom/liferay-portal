## LocalUtil

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