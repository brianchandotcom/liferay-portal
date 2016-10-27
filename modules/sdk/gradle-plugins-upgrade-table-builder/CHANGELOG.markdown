# Liferay Gradle Plugins Upgrade Table Builder Change Log

## 2.0.0 - 2016-10-28

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.

### Fixed
- [LPS-66222]: Fixed `NullPointerException` if the `releaseInfoFile` property of
`BuildUpgradeTableTask` is not set.

[LPS-66222]: https://issues.liferay.com/browse/LPS-66222
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573