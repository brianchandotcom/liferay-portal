# Liferay Gradle Plugins Jasper JSPC Change Log

## 2.0.0 - 2017-02-16

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-70677]: Exclude `com.liferay.portal` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.
- [LPS-70677]: Support `compileOnly` dependencies by using
`sourceSets.main.compileClasspath` as a dependency in the `jspC` configuration.

## 2.0.1 - 2017-03-03

### Changed
- [LPS-71048]: Exclude `javax.servlet` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.

## 2.0.2 - 2017-08-28

### Removed
- [LPS-74368]: Remove all dependency exclusions from the `jspCTool`
configuration's `com.liferay.jasper.jspc` default dependency.

## 3.0.0 - 2017-10-10

### Changed
- [LPS-75008]: Execute the [Liferay Jasper JSPC] tool without forking the JVM.

### Removed
- [LPS-75008]: The `CompileJSPTask` class does not extend `JavaExec` anymore.

[Liferay Jasper JSPC]: https://github.com/liferay/liferay-portal/tree/master/modules/util/jasper-jspc
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-70677]: https://issues.liferay.com/browse/LPS-70677
[LPS-71048]: https://issues.liferay.com/browse/LPS-71048
[LPS-74368]: https://issues.liferay.com/browse/LPS-74368
[LPS-75008]: https://issues.liferay.com/browse/LPS-75008