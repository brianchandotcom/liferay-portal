# Liferay Gradle Plugins Source Formatter Change Log

## 2.0.0 - 2017-05-03

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.

### Removed
- [LPS-72252]: The properties `copyrightFile` and `copyrightFileName` of
`FormatSourceTask` are no longer available.
- [LPS-72252]: The property `useProperties` of `FormatSourceTask` is no longer
available.

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-72252]: https://issues.liferay.com/browse/LPS-72252