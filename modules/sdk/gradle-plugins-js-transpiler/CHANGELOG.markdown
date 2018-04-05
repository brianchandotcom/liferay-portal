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

## 2.3.4 - 2016-12-21

### Changed
- [LPS-69802]: Update the [Liferay Gradle Plugins Node] dependency to version
1.5.0.

## 2.3.5 - 2016-12-29

### Changed
- [LPS-69920]: Update the [Liferay Gradle Plugins Node] dependency to version
1.5.1.

## 2.3.6 - 2017-02-09

### Changed
- [LPS-69920]: Update the [Liferay Gradle Plugins Node] dependency to version
1.5.2.

## 2.3.7 - 2017-02-23

### Changed
- [LPS-70870]: Update the [Liferay Gradle Plugins Node] dependency to version
2.0.0.

## 2.3.8 - 2017-03-09

### Changed
- [LPS-70634]: Update the [Liferay Gradle Plugins Node] dependency to version
2.0.1.

## 2.3.9 - 2017-03-13

### Changed
- [LPS-71222]: Update the [Liferay Gradle Plugins Node] dependency to version
2.0.2.

## 2.3.10 - 2017-04-11

### Changed
- [LPS-71826]: Update the [Liferay Gradle Plugins Node] dependency to version
2.1.0.

## 2.3.11 - 2017-04-25

### Changed
- [LPS-72152]: Update the [Liferay Gradle Plugins Node] dependency to version
2.2.0.

## 2.3.12 - 2017-05-03

### Changed
- [LPS-72340]: Update the [Liferay Gradle Plugins Node] dependency to version
2.2.1.

## 2.3.13 - 2017-05-23

### Fixed
- [LPS-72723]: Avoid the `npmInstall` task from deleting the `node_modules`
subdirectories created from the dependencies in the `jsCompile` configuration.

## 2.3.14 - 2017-05-31

### Fixed
- [LPS-72851]: Fix `InvalidUserDataException` in parallel builds when the
`jsCompile` or `soyCompile` configurations include project dependencies.

## 2.3.15 - 2017-07-07

### Changed
- [LPS-73472]: Update the [Liferay Gradle Plugins Node] dependency to version
2.3.0.

## 2.3.16 - 2017-07-17

### Changed
- [LPS-73472]: Update the [Liferay Gradle Plugins Node] dependency to version
3.0.0.

## 2.3.17 - 2017-08-24

### Changed
- [LPS-74343]: Explicitly set the `TranspileJSTask`'s `sourceDir` property as
required.

## 2.3.18 - 2017-08-29

### Changed
- [LPS-73472]: Update the [Liferay Gradle Plugins Node] dependency to version
3.1.0.

## 2.3.19 - 2017-09-18

### Changed
- [LPS-74770]: Update the [Liferay Gradle Plugins Node] dependency to version
3.1.1.

## 2.3.20 - 2017-09-28

### Changed
- [LPS-74933]: Update the [Liferay Gradle Plugins Node] dependency to version
3.2.0.

## 2.3.21 - 2017-10-10

### Changed
- [LPS-75175]: Update the [Liferay Gradle Plugins Node] dependency to version
3.2.1.

## 2.4.0 - 2017-11-13

### Added
- [LPS-75829]: Add the new `com.liferay.js.transpiler.base` plugin to apply the
`jsCompile` configuration expansion logic.

## 2.4.1 - 2017-11-20

### Changed
- [LPS-75965]: Update the [Liferay Gradle Plugins Node] dependency to version
4.0.0.

## 2.4.2 - 2018-01-02

### Changed
- [LPS-74904]: Update the [Liferay Gradle Plugins Node] dependency to version
4.0.1.

## 2.4.3 - 2018-01-17

### Changed
- [LPS-76644]: Update the [Liferay Gradle Plugins Node] dependency to version
4.0.2.
- [LPS-77250]: Update the default value of the `soyDependencies` for
`TranspileJSTask` instances from `"${npmInstall.workingDir}/node_modules/lexicon*/src/**/*.soy"`
to `"${npmInstall.workingDir}/node_modules/clay*/src/**/*.soy"`.

## 2.4.4 - 2018-02-08

### Changed
- [LPS-69802]: Update the [Liferay Gradle Plugins Node] dependency to version
4.1.0.

## 2.4.5 - 2018-02-13

### Changed
- [LPS-77996]: Update the [Liferay Gradle Plugins Node] dependency to version
4.2.0.

## 2.4.6 - 2018-03-15

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Node] dependency to version
4.3.0.

## 2.4.7 - 2018-03-22

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Node] dependency to version
4.3.1.

## 2.4.8 - 2018-03-30

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Node] dependency to version
4.3.2.

## 2.4.9 - 2018-04-05

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Node] dependency to version
4.3.3.

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
[LPS-69802]: https://issues.liferay.com/browse/LPS-69802
[LPS-69920]: https://issues.liferay.com/browse/LPS-69920
[LPS-70634]: https://issues.liferay.com/browse/LPS-70634
[LPS-70870]: https://issues.liferay.com/browse/LPS-70870
[LPS-71222]: https://issues.liferay.com/browse/LPS-71222
[LPS-71826]: https://issues.liferay.com/browse/LPS-71826
[LPS-72152]: https://issues.liferay.com/browse/LPS-72152
[LPS-72340]: https://issues.liferay.com/browse/LPS-72340
[LPS-72723]: https://issues.liferay.com/browse/LPS-72723
[LPS-72851]: https://issues.liferay.com/browse/LPS-72851
[LPS-73472]: https://issues.liferay.com/browse/LPS-73472
[LPS-74343]: https://issues.liferay.com/browse/LPS-74343
[LPS-74770]: https://issues.liferay.com/browse/LPS-74770
[LPS-74904]: https://issues.liferay.com/browse/LPS-74904
[LPS-74933]: https://issues.liferay.com/browse/LPS-74933
[LPS-75175]: https://issues.liferay.com/browse/LPS-75175
[LPS-75829]: https://issues.liferay.com/browse/LPS-75829
[LPS-75965]: https://issues.liferay.com/browse/LPS-75965
[LPS-76644]: https://issues.liferay.com/browse/LPS-76644
[LPS-77250]: https://issues.liferay.com/browse/LPS-77250
[LPS-77996]: https://issues.liferay.com/browse/LPS-77996
[LPS-78741]: https://issues.liferay.com/browse/LPS-78741