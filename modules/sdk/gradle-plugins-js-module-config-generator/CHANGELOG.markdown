# Liferay Gradle Plugins JS Module Config Generator Change Log

## 1.0.31 - 2016-08-27

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

## 2.0.2 - 2016-10-06

### Changed
- [LPS-68564]: Update the [Liferay Gradle Plugins Node] dependency to version
1.2.0.

## 2.0.3 - 2016-10-10

### Added
- [LPS-68618]: All `ConfigJSModulesTask` instances now depend on `npmInstall`.

## 2.0.4 - 2016-10-21

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins Node] dependency to version
1.3.0.

## 2.1.0 - 2016-11-03

### Added
- [LPS-68298]: Add property `customDefine` to all tasks that extend
`ConfigJSModulesTask` in order to use custom `define(...)` calls in the JS
files.

## 2.1.1 - 2016-11-04

### Fixed
- [LPS-68298]: Replace `define(...)` calls only at the beginning of a line, or
with if preceded by spaces or tabs.

## 2.1.2 - 2016-11-29

### Changed
- [LPS-69445]: Update the [Liferay Gradle Plugins Node] dependency to version
1.4.0.

## 2.1.3 - 2016-12-08

### Changed
- [LPS-69618]: Update the [Liferay Gradle Plugins Node] dependency to version
1.4.1.

## 2.1.4 - 2016-12-14

### Changed
- [LPS-69677]: Update the [Liferay Gradle Plugins Node] dependency to version
1.4.2.

[Liferay Gradle Plugins Node]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-node
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68485]: https://issues.liferay.com/browse/LPS-68485
[LPS-68564]: https://issues.liferay.com/browse/LPS-68564
[LPS-68618]: https://issues.liferay.com/browse/LPS-68618
[LPS-68298]: https://issues.liferay.com/browse/LPS-68298
[LPS-69445]: https://issues.liferay.com/browse/LPS-69445
[LPS-69618]: https://issues.liferay.com/browse/LPS-69618
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677