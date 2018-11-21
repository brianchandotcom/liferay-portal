# Liferay Gradle Plugins WSDL Builder Change Log

## 2.0.0 - 2017-01-10

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.

### Fixed
- [LPS-70060]: Fix compatibility issue with Gradle 3.3.

## 2.0.1 - 2017-10-16

### Fixed
- [LPS-75273]: Avoid deleting the destination directory of the `buildWSDL` task
if the property `buildLibs` is `false`.

## 2.0.2 - 2018-11-16

### Changed
- [LPS-87466]: Update the [Liferay Gradle Util] dependency to version 1.0.32.

## 2.0.3 - 2018-11-19

### Changed
- [LPS-87466]: Update the [Liferay Gradle Util] dependency to version 1.0.33.

[Liferay Gradle Util]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-util
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-70060]: https://issues.liferay.com/browse/LPS-70060
[LPS-75273]: https://issues.liferay.com/browse/LPS-75273
[LPS-87466]: https://issues.liferay.com/browse/LPS-87466