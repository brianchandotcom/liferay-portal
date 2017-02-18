# Liferay Gradle Plugins Jasper JSPC Change Log

## 2.0.0 - 2017-02-16

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-70677]: Exclude `com.liferay.portal` transitive dependencies from the
`com.liferay.jasper.jspc` default dependency in the `jspCTool` configuration.
- [LPS-70677]: Support `compileOnly` dependencies by using
`sourceSets.main.compileClasspath` as a dependency in the `jspC` configuration.

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-70677]: https://issues.liferay.com/browse/LPS-70677