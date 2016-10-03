# Liferay Gradle Plugins JS Module Config Generator Change Log

## 1.0.31 - 2016-08-26

### Changed
- [LPS-67023]: Update the [Liferay Gradle Plugins Node] dependency to version
1.0.22.

## 2.0.0 - 2016-09-20

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins Node] dependency to version
1.1.0.
- [LPS-67573]: Make most methods private in order to reduce API surface.

## 2.0.1 - 2016-10-03

### Fixed
- [LPS-68485]: The up-to-date check for `ConfigJSModulesTask` tasks is incorrect
(files are modified in-place) and it has been disabled.

[Liferay Gradle Plugins Node]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-node
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68485]: https://issues.liferay.com/browse/LPS-68485