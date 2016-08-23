# Liferay Gradle Plugins Defaults Change Log

## 1.1.3 - 2016-08-22

### Changed
- [LPS-67658]: Update the [Liferay Gradle Plugins] dependency to version 2.0.10.

### Fixed
- [LPS-67658]: Compile the plugin against Gradle 2.14 to make it compatible with
both Gradle 2.14+ and Gradle 3.0.

## 1.1.4 - 2016-08-22

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.11.

## 1.1.5 - 2016-08-22

### Changed
- [LPS-67694]: Disable the `install` and `uploadArchives` tasks and all their
dependencies at configuration phase if the `-PsnapshotIfStale` argument is
provided and the latest published snapshot is up-to-date.

### Fixed
- [LPS-67694]: Use Gradle to download the latest published artifact of a project
instead of the Nexus REST API, as the latter does not always return the correct
artifact.

[Liferay Gradle Plugins]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins
[LPS-67352]: https://issues.liferay.com/browse/LPS-67352
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-67694]: https://issues.liferay.com/browse/LPS-67694