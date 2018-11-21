# Liferay Gradle Plugins Change Log Builder Change Log

## 1.0.4 - 2018-05-16

### Fixed
- [LPS-80950]: Avoid out-of-memory errors when running on large Git
repositories.

## 1.1.0 - 2018-06-25

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.

### Fixed
- [LPS-82857]: Avoid error when running the `buildChangeLog` task on Git
branches that do not contain commits older than two years.

## 1.1.1 - 2018-07-10

### Fixed
- [LPS-82960]: Avoid error when the only valid commit is the initial commit.

## 1.1.2 - 2018-11-16

### Changed
- [LPS-87466]: Update the [Liferay Gradle Util] dependency to version 1.0.32.

## 1.1.3 - 2018-11-19

### Changed
- [LPS-87466]: Update the [Liferay Gradle Util] dependency to version 1.0.33.

[Liferay Gradle Util]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-util
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-80950]: https://issues.liferay.com/browse/LPS-80950
[LPS-82857]: https://issues.liferay.com/browse/LPS-82857
[LPS-82960]: https://issues.liferay.com/browse/LPS-82960
[LPS-87466]: https://issues.liferay.com/browse/LPS-87466