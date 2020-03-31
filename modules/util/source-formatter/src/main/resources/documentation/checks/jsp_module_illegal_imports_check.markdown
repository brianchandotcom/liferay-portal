## JSPModuleIllegalImportsCheck

Do not import the following classes in modules:

- `com.liferay.registry.*`, instead use `com.liferay.osgi.service.tracker.*`
and/or `org.osgi.framework.*`
- `com.liferay.util.ContentUtil`, instead use
`com.liferay.petra.content.ContentUtil`