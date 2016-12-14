# Liferay Gradle Plugins Baseline Change Log

## 1.1.0 - 2016-11-30

### Added
- [LPS-69470]: Add the property `forceCalculatedVersion` to all tasks that
extend `BaselineTask`. If `true`, the baseline check will fail if the
`Bundle-Version` has been excessively increased.

### Changed
- [LPS-69470]: Update the [Liferay Ant BND] dependency to version 2.0.31.

[Liferay Ant BND]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/ant-bnd
[LPS-69470]: https://issues.liferay.com/browse/LPS-69470