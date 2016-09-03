# Liferay Gradle Plugins Maven Plugin Builder Change Log

## 1.0.12 - 2016-09-02

### Fixed
- [LPS-67986]: The fully qualified class names in the generated Maven plugin
descriptors are now delimited by dots instead of dollar signs (e.g.,
`java.io.File` instead of `java$io$File`).

[LPS-67986]: https://issues.liferay.com/browse/LPS-67986