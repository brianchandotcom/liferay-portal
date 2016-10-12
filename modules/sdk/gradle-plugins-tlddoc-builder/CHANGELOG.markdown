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

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-68666]: https://issues.liferay.com/browse/LPS-68666
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023