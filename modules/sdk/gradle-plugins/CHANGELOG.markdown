# Liferay Gradle Plugins Change Log

## 2.0.10 - 2016-08-22

### Changed
- [LPS-67658]: Update the [Gradle Bundle Plugin] dependency to version 0.8.6.

### Fixed
- [LPS-67658]: Compile the plugin against Gradle 2.14 to make it compatible with
both Gradle 2.14+ and Gradle 3.0.

## 2.0.11 - 2016-08-22

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.261.

## 2.0.12 - 2016-08-25

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.262.

## 2.0.13 - 2016-08-27

### Changed
- [LPS-67804]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.124.

## 2.0.14 - 2016-08-27

### Changed
- [LPS-67023]: Update the [Liferay Gradle Plugins Gulp] dependency to version
1.0.10.
- [LPS-67023]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 1.0.31.
- [LPS-67023]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 1.0.35.

### Removed
- [LPS-67023]: The project properties `nodejs.lfr.amd.loader.version` and
`nodejs.metal.cli.version` are no longer available.
- [LPS-67023]: Invoking the `clean` task no longer removes the
`node_modules` directory of a project.

## 2.0.15 - 2016-08-27

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.263.

## 2.0.16 - 2016-08-27

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.264.

## 2.0.17 - 2016-08-29

### Changed
- [LPS-67352]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.125.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.265.

## 2.0.18 - 2016-08-30

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.266.

## 2.0.19 - 2016-08-31

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.267.

## 2.0.20 - 2016-09-01

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.268.

## 2.0.21 - 2016-09-02

### Changed
- [LPS-67986]: Update the [Liferay CSS Builder] dependency to version 1.0.19.

## 2.0.22 - 2016-09-02

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.269.

## 2.0.23 - 2016-09-05

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.270.

## 2.0.24 - 2016-09-05

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.271.

## 2.0.25 - 2016-09-06

### Changed
- [LPS-67996]: Update the [Liferay Source Formatter] dependency to version
1.0.272.

## 2.0.26 - 2016-09-07

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.273.

## 2.0.27 - 2016-09-07

### Changed
- [LPS-68014]: Update the [Liferay Ant BND] dependency to version 2.0.29.
- [LPS-68035]: Update the [Liferay Source Formatter] dependency to version
1.0.274.

## 2.0.28 - 2016-09-08

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.275.

## 2.0.29 - 2016-09-08

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.276.

## 2.0.30 - 2016-09-09

### Added
- [LPS-61099]: Allow to overwrite the default value of
`liferay.appServerParentDir` by setting the project property
`app.server.parent.dir`.

[Gradle Bundle Plugin]: https://github.com/TomDmitriev/gradle-bundle-plugin
[Liferay Ant BND]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/ant-bnd
[Liferay CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/css-builder
[Liferay Gradle Plugins Gulp]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-gulp
[Liferay Gradle Plugins JS Module Config Generator]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-module-config-generator
[Liferay Gradle Plugins JS Transpiler]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-transpiler
[Liferay Portal Tools Service Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-service-builder
[Liferay Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/source-formatter
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-66853]: https://issues.liferay.com/browse/LPS-66853
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67352]: https://issues.liferay.com/browse/LPS-67352
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-67804]: https://issues.liferay.com/browse/LPS-67804
[LPS-67986]: https://issues.liferay.com/browse/LPS-67986
[LPS-67996]: https://issues.liferay.com/browse/LPS-67996
[LPS-68014]: https://issues.liferay.com/browse/LPS-68014
[LPS-68035]: https://issues.liferay.com/browse/LPS-68035