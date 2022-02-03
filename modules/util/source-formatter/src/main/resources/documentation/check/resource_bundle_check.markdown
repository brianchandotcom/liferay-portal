## ResourceBundleCheck

Avoid making calls to `java.util.ResourceBundle.getBundle`, because this method
may return incorrect values for non-English languages, due to the fact that
`Language.properties` is stored as `UTF-8` while
`java.util.ResourceBundle.getBundle` loads them as `ISO-8859-1`.

Instead, use
`com.liferay.portal.kernel.util.ResourceBundleLoader.loadResourceBundle`.

### Example

Incorrect:

```java
ResourceBundle resourceBundle = ResourceBundle.getBundle(
    "content.Language", locale);

String value = resourceBundle.getString("key");
```

Correct:

```java
ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
    locale);

String value = ResourceBundleUtil.getString(resourceBundle, "key");
```