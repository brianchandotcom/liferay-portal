## GradleUpgradeReleaseDxpCheck

All dependencies in a `build.gradle` that are already contained in a specific
version of the release DXP fat JAR should be removed and replaced by the
dependency `release.dxp.api`. This applies to test dependencies as well.

### Example

Upgrade DXP version: `7.4.13.u20`

Incorrect:

```groovy
dependencies {
    compileOnly group: "biz.aQute.bnd", name: "biz.aQute.bndlib", version: "3.5.0"
    compileOnly group: "com.liferay.portal", name: "com.liferay.portal.kernel", version: "3.0.0"
    compileOnly group: "org.osgi", name: "org.osgi.core", version: "6.0.0"

    testCompile group: "com.liferay", name: "com.liferay.journal.api", version: "3.0.2"
    testCompile group: 'com.liferay', name: 'com.liferay.dynamic.data.mapping.api', version: '4.8.3'
    testCompile group: 'com.liferay', name: 'com.liferay.petra.lang', version: '3.0.0'
    testCompile group: 'com.liferay', name: 'com.liferay.registry.api', version: '2.1.3'
    testCompile group: "com.liferay.portal", name: "com.liferay.portal.kernel", version: "3.6.2"
    testCompile group: "org.mockito", name: "mockito-core", version: "2.8.47"
    testCompile group: "javax.portlet", name: "portlet-api", version: "2.0"
    testCompile group: "javax.servlet", name: "javax.servlet-api", version: "3.0.1"
    testCompile group: "junit", name: "junit", version: "4.12"
    testCompile group: "org.powermock", name: "powermock-api-mockito2", version: "1.7.3"
    testCompile group: "org.powermock", name: "powermock-module-junit4", version: "1.7.3"
}
```

Correct:

```groovy
dependencies {
    compileOnly group: "com.liferay.portal", name: "release.dxp.api", version: "7.4.13.u20"

    testCompile group: "com.liferay.portal", name: "release.dxp.api", version: "7.4.13.u20"
    testCompile group: "org.mockito", name: "mockito-core", version: "2.8.47"
    testCompile group: "junit", name: "junit", version: "4.12"
    testCompile group: "org.powermock", name: "powermock-api-mockito2", version: "1.7.3"
    testCompile group: "org.powermock", name: "powermock-module-junit4", version: "1.7.3"
}
```