# Liferay Gradle Plugins App Docker Change Log

## 1.0.1 - 2017-09-19

### Changed
- [LPS-74785]: Avoid failing the build in the case of a Git error.

## 1.0.2 - 2017-09-20

### Fixed
- [LPS-74811]: Include the WAR file of WAR projects in the Docker image.

## 1.0.3 - 2017-10-18

### Added
- [LPS-75327]: Automatically convert `.sh` files to Unix-style line endings when
building the app's Docker image.

## 1.0.4 - 2017-11-03

### Changed
- [LPS-75704]: Update the [Gradle Docker Plugin] dependency to version 3.2.0.

## 1.0.5 - 2018-08-30

### Changed
- [LPS-84094]: Update the [Liferay Gradle Util] dependency to version 1.0.31.

## 1.0.6 - 2018-10-22

### Changed
- [LPS-86588]: Update the [Gradle Docker Plugin] dependency to version 3.6.1.

## 1.0.7 - 2018-11-01

### Changed
- [LPS-86875]: Update the [Gradle Docker Plugin] dependency to version 3.6.2.

[Gradle Docker Plugin]: https://github.com/bmuschko/gradle-docker-plugin
[Liferay Gradle Util]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-util
[LPS-74785]: https://issues.liferay.com/browse/LPS-74785
[LPS-74811]: https://issues.liferay.com/browse/LPS-74811
[LPS-75327]: https://issues.liferay.com/browse/LPS-75327
[LPS-75704]: https://issues.liferay.com/browse/LPS-75704
[LPS-84094]: https://issues.liferay.com/browse/LPS-84094
[LPS-86588]: https://issues.liferay.com/browse/LPS-86588
[LPS-86875]: https://issues.liferay.com/browse/LPS-86875