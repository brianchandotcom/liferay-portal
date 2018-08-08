# Liferay Gradle Plugins Target Platform Change Log

## 1.0.1 - 2018-05-07

### Added
- [LPS-77343]: Add the ability to use a manually generated bndrun file in a
`ResolveTask` instance by setting the property `bndrunFile` to a valid file.

### Fixed
- [LPS-80222]: Apply BOM files only on the following configurations:
	- configuration `compileInclude` added by [Liferay Gradle Plugins]
	- configuration `default`
	- configurations added by [Liferay Gradle Plugins Test Integration]
	- configurations added by the `java` plugin

## 1.1.0 - 2018-07-17

### Added
- [LPS-81530]: Add the ability to configure imported BOMs to manage Java
dependencies and the various artifacts used in resolving OSGi dependencies.
- [LPS-82491]: Add support for the `idea` plugin.

## 1.1.2 - 2018-08-08

### Fixed
- [LPS-83922]: Don't add requirements for files that don't have BSN.
- [LPS-83922]: Set applyMavenExclusions to false to improve performance.

[Liferay Gradle Plugins]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins
[Liferay Gradle Plugins Test Integration]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-test-integration
[LPS-77343]: https://issues.liferay.com/browse/LPS-77343
[LPS-80222]: https://issues.liferay.com/browse/LPS-80222
[LPS-81530]: https://issues.liferay.com/browse/LPS-81530
[LPS-82491]: https://issues.liferay.com/browse/LPS-82491
[LPS-83922]: https://issues.liferay.com/browse/LPS-83922