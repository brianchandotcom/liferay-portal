# Liferay Gradle Plugins Maven Plugin Builder Change Log

## 1.0.12 - 2016-09-02

### Fixed
- [LPS-67986]: The fully qualified class names in the generated Maven plugin
descriptors are now delimited by dots instead of dollar signs (e.g.,
`java.io.File` instead of `java$io$File`).

## 1.1.0 - 2017-03-08

### Added
- [LPS-71087]: Add task `writeMavenSettings` to configure the Maven invocations
with proxy settings and a mirror URL.

### Changed
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-67573]: Move task classes to the
`com.liferay.gradle.plugins.maven.plugin.builder.tasks` package.
- [LPS-67573]: Move utility classes to the
`com.liferay.gradle.plugins.maven.plugin.builder.internal` package.

## 1.1.1 - 2017-03-17

### Added
- [LPS-71264]: Add the ability to configure the Maven invocations with a local
repository. By default, the value is copied from the `maven.repo.local` system
property.

## 1.1.2 - 2017-03-21

### Fixed
- [LPS-71264]: Avoid throwing a `NullPointerException` if the
`WriteMavenSettingsTask` instance's `localRepositoryDir` property is a closure
that returns `null`.

## 1.2.0 - 2018-03-08

### Added
- [LPS-71264]: Add the ability to attach a remote debugger to the Maven
invocation by setting the `BuildPluginDescriptorTask` instance's `mavenDebug`
property to `true`, or by passing the command line argument
`-DbuildPluginDescriptor.maven.debug=true`.
- [LPS-71264]: Synchronize the Gradle and Maven log levels.

### Fixed
- [LPS-71264]: Fix `pom.xml` generation in case project dependencies are
present.
- [LPS-71264]: Fix the `WriteMavenSettingsTask` instance's `localRepositoryDir`
property usage when running on Windows.

[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67986]: https://issues.liferay.com/browse/LPS-67986
[LPS-71087]: https://issues.liferay.com/browse/LPS-71087
[LPS-71264]: https://issues.liferay.com/browse/LPS-71264