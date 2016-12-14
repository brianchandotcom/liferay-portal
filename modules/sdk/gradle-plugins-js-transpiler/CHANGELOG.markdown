# Liferay Gradle Plugins JS Transpiler Change Log

## 1.0.35 - 2016-08-27

### Changed
- [LPS-67023]: Update the [Liferay Gradle Plugins Node] dependency to version
1.0.22.

### Removed
- [LPS-67023]: Remove the `downloadLfrAmdLoader` task.
- [LPS-67023]: Remove the `jsTranspiler` extension object.

## 2.0.0 - 2016-09-20

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins Node] dependency to version
1.1.0.
- [LPS-67573]: Make most methods private in order to reduce API surface.

## 2.0.1 - 2016-10-06

### Changed
- [LPS-68564]: Update the [Liferay Gradle Plugins Node] dependency to version
1.2.0.

## 2.0.2 - 2016-10-21

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins Node] dependency to version
1.3.0.

## 2.1.0 - 2016-10-24

### Added
- [LPS-68917]: Add configuration `soyCompile` to provide additional Soy
dependencies for the `transpileJS` task.
- [LPS-68917]: Add default Lexicon Soy dependency to all `TranspileJSTask`
instances.

## 2.1.1 - 2016-10-26

### Fixed
- [LPS-68917]: Fixed search pattern for the additional Soy dependencies in the
`soyCompile` configuration.

## 2.2.0 - 2016-10-28

### Added
- [LPS-68979]: Add property `skipWhenEmpty` to all tasks that extend
`TranspileJSTask`. If `true`, the task is disabled if it has no source files
at the end of the project evaluation.

### Changed
- [LPS-68979]: Exclude empty directories while `TranspileJSTask` instances copy
source files to `workingDir`.

## 2.2.1 - 2016-11-01

### Added
- [LPS-69026]: Set the `--logLevel` argument of `metal-cli` based on the Gradle
log level.

## 2.3.0 - 2016-11-21

### Added
- [LPS-69248]: Add the `jsCompile` configuration to provide additional
JavaScript dependencies for the `transpileJS` task.

## 2.3.1 - 2016-11-29

### Changed
- [LPS-69445]: Update the [Liferay Gradle Plugins Node] dependency to version
1.4.0.

## 2.3.2 - 2016-12-08

### Changed
- [LPS-69618]: Update the [Liferay Gradle Plugins Node] dependency to version
1.4.1.

## 2.3.3 - 2016-12-14

### Changed
- [LPS-69677]: Update the [Liferay Gradle Plugins Node] dependency to version
1.4.2.

[Liferay Gradle Plugins Node]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-node
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68564]: https://issues.liferay.com/browse/LPS-68564
[LPS-68917]: https://issues.liferay.com/browse/LPS-68917
[LPS-68979]: https://issues.liferay.com/browse/LPS-68979
[LPS-69026]: https://issues.liferay.com/browse/LPS-69026
[LPS-69248]: https://issues.liferay.com/browse/LPS-69248
[LPS-69445]: https://issues.liferay.com/browse/LPS-69445
[LPS-69618]: https://issues.liferay.com/browse/LPS-69618
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677