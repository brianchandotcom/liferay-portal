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

## 2.0.3 - 2017-11-30

### Fixed
- [LPS-76202]: Defer evaluation of the project's build directory so it can be
changed after applying the plugin.

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-70677]: https://issues.liferay.com/browse/LPS-70677
[LPS-71048]: https://issues.liferay.com/browse/LPS-71048
[LPS-74368]: https://issues.liferay.com/browse/LPS-74368
[LPS-76202]: https://issues.liferay.com/browse/LPS-76202