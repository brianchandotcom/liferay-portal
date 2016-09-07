# Liferay Gradle Plugins Defaults Change Log

## 1.1.3 - 2016-08-22

### Changed
- [LPS-67658]: Update the [Liferay Gradle Plugins] dependency to version 2.0.10.

### Fixed
- [LPS-67658]: Compile the plugin against Gradle 2.14 to make it compatible with
both Gradle 2.14+ and Gradle 3.0.

## 1.1.4 - 2016-08-22

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.11.

## 1.1.5 - 2016-08-22

### Changed
- [LPS-67694]: Disable the `install` and `uploadArchives` tasks and all their
dependencies during the configuration phase if the `-PsnapshotIfStale` argument
is provided and the latest published snapshot is up-to-date.

### Fixed
- [LPS-67694]: Use Gradle to download the latest published artifact of a project
instead of the Nexus REST API, as the latter does not always return the correct
artifact.

## 1.1.6 - 2016-08-25

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.12.

## 1.1.7 - 2016-08-27

### Changed
- [LPS-67804]: Update the [Liferay Gradle Plugins] dependency to version 2.0.13.

## 1.1.8 - 2016-08-27

### Added
- [LPS-67023]: Automatically apply the following default settings when on
Jenkins:
	- Block Node.js invocations if the `com.liferay.cache` plugin is applied.
	- Enable the `node_modules` directory cache.
	- Retry `npm install` three times if a Node.js invocation fails.
	- Set up the NPM registry URL based on the `nodejs.npm.ci.registry` project
	property.

### Changed
- [LPS-67023]: Update the [Liferay Gradle Plugins] dependency to version 2.0.14.

## 1.1.9 - 2016-08-27

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.15.

## 1.1.10 - 2016-08-27

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.16.

## 1.1.11 - 2016-08-29

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.17.

## 1.1.12 - 2016-08-29

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.18.

## 1.1.13 - 2016-08-31

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.19.

## 1.2.0 - 2016-08-31

### Added
- [LPS-67863]: Allow the `Bundle-Version` and `packageinfo` versions of an OSGi
project to be overridden by creating a
`.version-overrides-${project.name}.properties` file in the parent directory of
the `.gitrepo` file with the following values:
	- `Bundle-Version=[new bundle version]`
	- `com.liferay.foo.bar=[new packageinfo version for com.liferay.foo.bar package]`

- [LPS-67863]: Execute the following actions when running `gradlew baseline
-PsyncRelease` on an OSGi project:
	1. Bump up the `Bundle-Version` and `packageinfo` versions based on the same
	module found in the branch defined in the `release.versions.test.other.dir`
	project property. The changes are either saved directly in the project
	files, or in the `.version-overrides-${project.name}.properties` file if the
	`.gitrepo` file contains the string `"mode = pull"`, which denotes a
	read-only sub-repository.
	2. Execute the `baseline` task, automatically ignoring any semantic
	versioning errors.
	3. Commit the project file changes caused by steps 1 and 2.

## 1.2.1 - 2016-08-31

### Fixed
- [LPS-67863]: Avoid Git error while running `gradlew baseline -PsyncRelease` on
an OSGi project that does not contain a `packageinfo` file.

## 1.2.2 - 2016-09-01

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.20.

## 1.2.3 - 2016-09-01

### Changed
- [LPS-67863]: The file that contains the version overrides for an OSGi module
is now called `.version-override-${project.name}.properties`.
- [LPS-67863]: The `packageinfo` versions are always overridden with the
versions specified in the `.version-override-${project.name}.properties` file,
even if the versions in the `packageinfo` files are greater.

## 1.2.4 - 2016-09-01

### Changed
- [LPS-67863]: Disable the `printArtifactPublishCommands` task if the project's
`build.gradle` contains the string `version: "default"`, to prevent releasing
modules with unpublished dependencies.
- [LPS-67863]: The `.version-override-${project.name}.properties` now contains
only the version overrides that differ from the versions specified in the
`bnd.bnd` and `packageinfo` files.

## 1.2.5 - 2016-09-01

### Fixed
- [LPS-67863]: Avoid throwing an exception while running `gradlew baseline
-PsyncRelease` on a project that does not contain a
`.version-override-${project.name}.properties` file.

## 1.2.6 - 2016-09-02

### Fixed
- [LPS-67863]: Avoid throwing an exception while running the
`printArtifactPublishCommands` task on a project that does not contain a
`build.gradle` file.

## 1.2.7 - 2016-09-02

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.21.

## 1.2.8 - 2016-09-02

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.22.

## 1.2.9 - 2016-09-05

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.23.

## 1.2.10 - 2016-09-05

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.24.

## 1.2.11 - 2016-09-06

### Changed
- [LPS-67996]: Update the [Liferay Gradle Plugins] dependency to version 2.0.25.

[Liferay Gradle Plugins]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins
[LPS-66853]: https://issues.liferay.com/browse/LPS-66853
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67352]: https://issues.liferay.com/browse/LPS-67352
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-67694]: https://issues.liferay.com/browse/LPS-67694
[LPS-67804]: https://issues.liferay.com/browse/LPS-67804
[LPS-67863]: https://issues.liferay.com/browse/LPS-67863
[LPS-67996]: https://issues.liferay.com/browse/LPS-67996