## Gradle versioning

Do not use `default` as the version number in gradle files. Instead use the
exact version:

```
provided group: "com.liferay", name: "com.liferay.osgi.util", version: "3.0.0"
```

Exceptions:

```
provided group: "com.liferay.portal", name: "com.liferay.portal.impl", version: "default"
provided group: "com.liferay.portal", name: "com.liferay.portal.kernel", version: "default"
provided group: "com.liferay.portal", name: "com.liferay.portal.test", version: "default"
provided group: "com.liferay.portal", name: "com.liferay.portal.test.integration", version: "default"
provided group: "com.liferay.portal", name: "com.liferay.util.bridges", version: "default"
provided group: "com.liferay.portal", name: "com.liferay.util.taglib", version: "default"
```