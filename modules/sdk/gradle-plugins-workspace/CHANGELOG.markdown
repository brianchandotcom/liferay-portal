# Liferay Gradle Plugins Workspace Change Log

## 1.0.41 - 2016-09-26

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.41.
- [LPS-67656]: Use Liferay 7.0.2 GA3 by default.

## 1.1.0 - 2016-09-27

### Added
- [LPS-68293]: Add support for WAR projects, contained in the `wars` directory
of a Liferay Workspace. For each WAR project, the following configuration is
applied:
	- applies the [`war`](https://docs.gradle.org/current/userguide/war_plugin.html)
	plugin
	- adds a `deploy` task
	- configures the `distBundleTar` and `distBundleZip` task to save the
	generated WAR file in the `osgi/war` directory of the bundle

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.45.
- [LPS-67573]: Make most methods private in order to reduce API surface.

[Liferay Gradle Plugins]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins
[LPS-67352]: https://issues.liferay.com/browse/LPS-66853
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67656]: https://issues.liferay.com/browse/LPS-67656
[LPS-68293]: https://issues.liferay.com/browse/LPS-68293