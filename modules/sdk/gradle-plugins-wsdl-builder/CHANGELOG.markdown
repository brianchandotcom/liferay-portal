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

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-70060]: https://issues.liferay.com/browse/LPS-70060
[LPS-75273]: https://issues.liferay.com/browse/LPS-75273