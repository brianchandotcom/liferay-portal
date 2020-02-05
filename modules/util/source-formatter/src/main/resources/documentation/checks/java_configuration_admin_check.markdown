## JavaConfigurationAdminCheck

OSGi Configurations are shared across bundles but are not created with
`location == "?"`

Binding configurations to a specific location can lead to errors.

It is dangerous to call

`org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration(String pid)`
`org.osgi.service.cm.ConfigurationAdmin#createFactoryConfiguration(String pid, String location)`,
where `location != "?"`

or

`org.osgi.service.cm.ConfigurationAdmin#getConfiguration(String pid)`
`org.osgi.service.cm.ConfigurationAdmin#getConfiguration(String pid, String location)`,
where `location != "?"`

The only safe calls are

`configurationAdmin.createFactoryConfiguration( ... , "?")`

and

`configurationAdmin.getConfiguration( ... , "?")`

### Example

```java

Configuration configuration = _configurationAdmin.createFactoryConfiguration(
    "com.liferay.captcha.configuration.CaptchaConfiguration",
    StringPool.QUESTION);

Configuration configuration = _configurationAdmin.getConfiguration(
    "com.liferay.captcha.configuration.CaptchaConfiguration",
    StringPool.QUESTION);
```