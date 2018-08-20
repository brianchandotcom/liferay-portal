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

## 2.1.5 - 2017-10-11

### Changed
- [LPS-74449]: Update the [Liferay CSS Builder] dependency to version 2.0.0.

## 2.1.6 - 2017-11-01

### Changed
- [LPS-75589]: Update the [Liferay CSS Builder] dependency to version 2.0.1.

## 2.1.7 - 2017-11-07

### Changed
- [LPS-75633]: Update the [Liferay CSS Builder] dependency to version 2.0.2.

## 2.2.0 - 2017-12-19

### Changed
- [LPS-76475]: Replace the `BuildCSSTask`'s `docrootDir`, `portalCommonDir`,
`portalCommonFile`, and `portalCommonPath` properties with `baseDir`,
`importDir`, `importFile`, and `importPath`. The previous properties are still
available, but they are deprecated.
- [LPS-76475]: Update the [Liferay CSS Builder] dependency to version 2.1.0.

### Fixed
- [LPS-76475]: Fix invocation of the [Liferay CSS Builder] if the
`BuildCSSTask`'s `dirNames` property contains more than one value.

## 2.2.2 - 2018-08-15
- [LPS-84473]: Update the [Liferay CSS Builder] dependency to version 2.1.2.

[Liferay CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/css-builder
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-69223]: https://issues.liferay.com/browse/LPS-69223
[LPS-73495]: https://issues.liferay.com/browse/LPS-73495
[LPS-74126]: https://issues.liferay.com/browse/LPS-74126
[LPS-74315]: https://issues.liferay.com/browse/LPS-74315
[LPS-74426]: https://issues.liferay.com/browse/LPS-74426
[LPS-74449]: https://issues.liferay.com/browse/LPS-74449
[LPS-74789]: https://issues.liferay.com/browse/LPS-74789
[LPS-75589]: https://issues.liferay.com/browse/LPS-75589
[LPS-75633]: https://issues.liferay.com/browse/LPS-75633
[LPS-76475]: https://issues.liferay.com/browse/LPS-76475
[LPS-84473]: https://issues.liferay.com/browse/LPS-84473