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

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-80950]: https://issues.liferay.com/browse/LPS-80950
[LPS-82857]: https://issues.liferay.com/browse/LPS-82857
[LPS-82960]: https://issues.liferay.com/browse/LPS-82960