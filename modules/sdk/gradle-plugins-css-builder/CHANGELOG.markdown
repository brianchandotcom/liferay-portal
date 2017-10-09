# Liferay Gradle Plugins CSS Builder Change Log

## 2.0.0 - 2016-11-17

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-69223]: Update default value of the `precision` property for
`BuildCSSTask` from `5` to `9`.

## 2.0.1 - 2017-07-10

### Changed
- [LPS-73495]: Update the [Liferay CSS Builder] dependency to version 1.0.28.

## 2.1.0 - 2017-08-12

### Added
- [LPS-74126]: Add the `appendCssImportTimestamps` property to `BuildCSSTask`.

### Changed
- [LPS-74126]: Update the [Liferay CSS Builder] dependency to version 1.1.0.

## 2.1.1 - 2017-08-15

### Changed
- [LPS-74126]: Update the [Liferay CSS Builder] dependency to version 1.1.1.

## 2.1.2 - 2017-09-18

### Changed
- [LPS-74315]: Update the [Liferay CSS Builder] dependency to version 1.1.2.

## 2.1.3 - 2017-09-19

### Changed
- [LPS-74789]: Update the [Liferay CSS Builder] dependency to version 1.1.3.

## 2.1.4 - 2017-10-06

### Changed
- [LPS-74426]: Update the [Liferay CSS Builder] dependency to version 1.1.4.

## 3.0.0 - 2017-10-10

### Added
- [LPS-75008]: Add [Liferay CSS Builder] as a transitive compile dependency.

### Changed
- [LPS-75008]: Execute the [Liferay CSS Builder] tool without forking the JVM.

### Removed
- [LPS-75008]: The `BuildCSSTask` class does not extend `JavaExec` anymore.

[Liferay CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/css-builder
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-69223]: https://issues.liferay.com/browse/LPS-69223
[LPS-73495]: https://issues.liferay.com/browse/LPS-73495
[LPS-74126]: https://issues.liferay.com/browse/LPS-74126
[LPS-74315]: https://issues.liferay.com/browse/LPS-74315
[LPS-74426]: https://issues.liferay.com/browse/LPS-74426
[LPS-74789]: https://issues.liferay.com/browse/LPS-74789
[LPS-75008]: https://issues.liferay.com/browse/LPS-75008