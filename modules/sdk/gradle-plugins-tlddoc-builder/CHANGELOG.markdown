# Liferay Gradle Plugins TLDDoc Builder Change Log

## 1.1.0 - 2016-10-07

### Added
- [LRDOCS-3023]: Add the new plugin `com.liferay.app.tlddoc.builder` to generate
the tag library documentation as a single, combined HTML document for an
application that spans different subprojects, each one representing a different
component of the same application.

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.

## 1.2.0 - 2016-10-12

### Added
- [LPS-68666]: Add the ability to define which subprojects to include in the tag
library documentation of the app by using the `appTLDDocBuilder.subprojects`
property.

## 1.3.0 - 2017-04-06

### Added
- [LPS-71591]: Add the ability to set a custom XML parser usable by
`ValidateSchemaTask` tasks.
- [LPS-71591]: Automatically configure the `validateTLD` task to use the version
0.12.5 of the [XML Resolver].

## 1.3.1 - 2018-08-30

### Changed
- [LPS-84621]: Update the `validateTLD` task to automatically search the parent
directories for `portal/definitions` to use these files for TLD scheme validation.
Set the properties `xmlParserClassName` and `xmlParserClasspath` to `null` to
use the default XML parser provided by Ant.

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68666]: https://issues.liferay.com/browse/LPS-68666
[LPS-71591]: https://issues.liferay.com/browse/LPS-71591
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023
[XML Resolver]: http://xmlresolver.org/