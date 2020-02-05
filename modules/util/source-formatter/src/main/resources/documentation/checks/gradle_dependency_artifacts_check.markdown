## GradleDependencyArtifactsCheck

Do not use `default` as the version number in gradle files. Instead use the
exact version:

```
compileOnly group: "com.liferay", name: "com.liferay.osgi.util", version: "3.0.0"
```

Exceptions:

```
compileOnly group: "com.liferay.portal", name: "com.liferay.portal.impl", version: "default"
compileOnly group: "com.liferay.portal", name: "com.liferay.portal.kernel", version: "default"
compileOnly group: "com.liferay.portal", name: "com.liferay.portal.test", version: "default"
compileOnly group: "com.liferay.portal", name: "com.liferay.portal.test.integration", version: "default"
compileOnly group: "com.liferay.portal", name: "com.liferay.util.bridges", version: "default"
compileOnly group: "com.liferay.portal", name: "com.liferay.util.taglib", version: "default"
```