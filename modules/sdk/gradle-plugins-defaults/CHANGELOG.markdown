# Liferay Gradle Plugins Defaults Change Log

## 1.1.3 - 2016-08-22

### Changed
- [LPS-67658]: Update the [Liferay Gradle Plugins] dependency to version 2.0.10.

### Fixed
- [LPS-67658]: Compile the plugin against Gradle 2.14 to make it compatible with
both Gradle 2.14+ and Gradle 3.0.

## 1.1.4 - 2016-08-23

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.11.

## 1.1.5 - 2016-08-23

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

## 1.1.12 - 2016-08-30

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
- [LPS-67863]: The `.version-override-${project.name}.properties` file now
contains only the version overrides that differ from the versions specified in
the `bnd.bnd` and `packageinfo` files.

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

## 1.2.12 - 2016-09-07

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.26.

## 1.2.13 - 2016-09-07

### Fixed
- [LPS-68009]: Reject snapshot artifacts while resolving the `baseline`
configuration.

## 1.2.14 - 2016-09-07

### Changed
- [LPS-68014]: Update the [Liferay Gradle Plugins] dependency to version 2.0.27.

## 1.2.15 - 2016-09-08

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.28.

## 1.2.16 - 2016-09-08

### Added
- [LPS-67863]: Allow dependency versions to be overridden in the
`.version-override-${project.name}.properties` file:

		[artifact group]-[artifact name]=[new version]

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.29.

## 1.2.17 - 2016-09-09

### Changed
- [LPS-61099]: Update the [Liferay Gradle Plugins] dependency to version 2.0.30.
- [LRDOCS-2841]: Look for the `.releng` directory starting from the project
directory instead of the root project directory. Doing this lets submodules like
`content-targeting` have their own separate `.releng` directory.

## 1.2.18 - 2016-09-12

### Changed
- [LPS-67766]: Update the [Liferay Gradle Plugins] dependency to version 2.0.31.

## 1.2.19 - 2016-09-13

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.32.

## 1.2.20 - 2016-09-13

### Changed
- [LPS-67986]: Update the [Liferay Gradle Plugins] dependency to version 2.0.33.

## 1.2.21 - 2016-09-13

### Changed
- [LRDOCS-2981]: Prepend *Module* string to `appJavadoc` module headings.

## 1.2.22 - 2016-09-14

### Changed
- [LPS-68131]: Update the [Liferay Gradle Plugins] dependency to version 2.0.34.

## 1.2.23 - 2016-09-16

### Changed
- [LPS-68131]: Update the [Liferay Gradle Plugins] dependency to version 2.0.35.

## 1.2.24 - 2016-09-20

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.36.
- [LPS-68230]: Configure [Liferay Gradle Plugins Node] to use version 6.6.0 of
Node.js.

### Removed
- [LPS-68230]: To reduce the number of plugins applied to a project and improve
performance, plugins in `com.liferay.gradle.plugins.defaults.internal` are no
longer applied via `apply plugin`.

## 1.2.25 - 2016-09-20

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins] dependency to version 2.0.37.

## 1.2.26 - 2016-09-21

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.38.

## 1.2.27 - 2016-09-22

### Changed
- [LPS-68305]: Set the `buildService.buildNumberIncrement` property to `false`
by default.

## 1.2.28 - 2016-09-22

### Changed
- [LPS-68297]: Update the [Liferay Gradle Plugins] dependency to version 2.0.39.

## 1.2.29 - 2016-09-22

### Added
- [LPS-66906]: Override the [`sass-binary-path`](https://github.com/sass/node-sass#binary-configuration-parameters)
argument in the `npmInstall` task with the value of the project property
`nodejs.npm.ci.sass.binary.site` when on Jenkins.

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins] dependency to version 2.0.40.

## 1.2.30 - 2016-09-23

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.41.

## 1.2.31 - 2016-09-23

### Added
- [LPS-68306]: Set the system property `portal.pre.build` to `true` to only
include the projects containing a `.lfrbuild-portal-pre` marker file.

## 1.2.32 - 2016-09-26

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.42.

## 1.2.33 - 2016-09-27

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 2.0.44.

## 1.2.34 - 2016-09-27

### Changed
- [LPS-67863]: Change dependency version override declarations in
`.version-override-${project.name}.properties` to follow a new format:

		[artifact group]/[artifact name]=[new version]

### Fixed
- [LPS-67863]: Fix commit deletion process of version override files.

## 1.2.35 - 2016-09-28

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.45.

## 1.2.36 - 2016-09-29

### Changed
- [LPS-58672]: Update the [Liferay Gradle Plugins] dependency to version 2.0.46.

## 1.2.37 - 2016-09-30

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 2.0.47.

## 1.2.38 - 2016-10-01

### Added
- [LPS-68448]: Fail the build of an OSGi project if the version in the
`npm-shrinkwrap.json` file does not match the project version.
- [LPS-68448]: The task `updateVersion` of OSGi and theme projects updates the
version in the `npm-shrinkwrap.json` file, if present.

## 1.2.39 - 2016-10-03

### Added
- [LPS-68402]: Set the [`org.apache.maven.offline`](https://github.com/shrinkwrap/resolver#system-properties)
system property to `true` for the `testIntegration` task.

### Changed
- [LPS-68485]: Update the [Liferay Gradle Plugins] dependency to version 2.0.48.

## 1.2.40 - 2016-10-04

### Added
- [LPS-68506]: Exclude unpublished projects from the API documentation generated
by the `appJavadoc` task.

### Changed
- [LPS-68504]: Update the [Liferay Gradle Plugins] dependency to version 2.0.49.
- [LPS-68506]: Update the [Liferay Gradle Plugins App Javadoc Builder]
dependency to version 1.1.0.

## 1.2.41 - 2016-10-05

### Added
- [LPS-68540]: Fail the `uploadArchives` task execution if the project directory
contains the marker file `.lfrbuild-missing-resources-importer`.

## 1.2.42 - 2016-10-05

### Added
- [LPS-66396]: Exclude specific project types from the build by setting the
following system properties to `true`:
	- `build.exclude.ant.plugin` to exclude all projects that contain a
	`build.xml` file from the build.
	- `build.exclude.module` to exclude all projects that contain a `bnd.bnd`
	file from the build.
	- `build.exclude.theme` to exclude all projects that contain a `gulpfile.js`
	file from the build.

### Removed
- [LPS-66396]: The `modules.only.build` system property is no longer available.

## 1.2.43 - 2016-10-05

### Changed
- [LPS-68334]: Update the [Liferay Gradle Plugins] dependency to version 2.0.50.

## 1.2.44 - 2016-10-06

### Changed
- [LPS-66396]: Update the [Liferay Gradle Plugins] dependency to version 3.0.0.

### Fixed
- [LPS-66396]: Update import in several classes from
`java.io.UncheckedIOException` to `org.gradle.api.UncheckedIOException` to
remove Java 8 dependency.

## 1.2.45 - 2016-10-06

### Changed
- [LPS-68415]: Update the [Liferay Gradle Plugins] dependency to version 3.0.1.

## 1.2.46 - 2016-10-06

### Added
- [LPS-68564]: Bypass https://github.com/npm/npm/issues/14042 and always exclude
the `fsevents` dependency from the generated `npm-shrinkwrap.json` files.

### Changed
- [LPS-68564]: Update the [Liferay Gradle Plugins] dependency to version 3.0.2.

## 1.2.47 - 2016-10-07

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.3.

## 1.2.48 - 2016-10-07

### Added
- [LRDOCS-3023]: The `com.liferay.app.defaults.plugin` now automatically adds
the local Maven and [Liferay CDN] repositories to the project.
- [LRDOCS-3023]: The `com.liferay.app.defaults.plugin` now automatically applies
the `com.liferay.app.tlddoc.builder` plugin.

### Changed
- [LRDOCS-3023]: Update the [Liferay Gradle Plugins] dependency to version
3.0.4.

## 1.2.49 - 2016-10-10

### Added
- [LPS-68611]: Update the [Liferay Gradle Plugins] dependency to version 3.0.5.
- [LRDOCS-2594]: Apply the [Liferay stylesheet](https://github.com/liferay/liferay-portal/blob/master/tools/styles/javadoc.css)
file, if found, to the API documentation generated by the `appJavadoc` and
`javadoc` tasks.

## 1.2.50 - 2016-10-10

### Changed
- [LPS-68618]: Update the [Liferay Gradle Plugins] dependency to version 3.0.6.

## 1.2.51 - 2016-10-11

### Changed
- [LPS-68598]: Update the [Liferay Gradle Plugins] dependency to version 3.0.7.

## 1.2.52 - 2016-10-11

### Added
- [LPS-68650]: Automatically update the versions in `npm-shrinkwrap.json` and
`package.json` files after running the `baseline` task.

## 1.2.53 - 2016-10-11

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.8.

## 1.2.54 - 2016-10-12

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.9.

## 1.2.55 - 2016-10-12

### Added
- [LPS-68666]: Include the subprojects of the private counterpart, if found, in
the API and tag library documentations of an app.

### Changed
- [LPS-68666]: Update the [Liferay Gradle Plugins] dependency to version 3.0.10.
- [LPS-68666]: Update the [Liferay Gradle Plugins App Javadoc Builder]
dependency to version 1.2.0.

### Fixed
- [LPS-68666]: Set the `title` property of the `appJavadoc` task based on the
`app.properties` file of the private counterpart, if found. This way, the API
documentation of the app, when generated from a private branch, will display the
latest published version of the private app.

## 1.2.56 - 2016-10-13

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.11.
- [LRDOCS-3038]: Include the bundle symbolic name in the API documentation title
generated by the `javadoc` task.

## 1.2.57 - 2016-10-13

### Removed
- [LPS-68448]: Temporarily disable `npm-shrinkwrap.json` version check.

## 1.2.58 - 2016-10-13

### Removed
- [LPS-68448]: Temporarily disable `package.json` version check.

## 1.2.59 - 2016-10-13

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.12.

## 1.2.60 - 2016-10-17

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.13.

## 1.2.61 - 2016-10-17

### Added
- [LPS-68772]: Allow module dependencies to be overridden with project
dependencies in the `.version-override-${project.name}.properties` file:

		[artifact group]/[artifact name]=[project path]

## 1.2.62 - 2016-10-17

### Changed
- [LPS-68779]: Update the [Liferay Gradle Plugins] dependency to version 3.0.14.

## 1.2.63 - 2016-10-18

### Added
- [LPS-68817]: Set the system property `build.profile` to only include projects
containing a `.lfrbuild-portal-${build.profile}` marker file.

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.15.

### Removed
- [LPS-68817]: The system property `portal.build` is no longer available; use
the `-Dbuild.profile=portal` parameter instead.
- [LPS-68817]: The system property `portal.pre.build` is no longer available;
use the `-Dbuild.profile=portal-pre` parameter instead.

## 1.2.64 - 2016-10-18

### Changed
- [LPS-68779]: Update the [Liferay Gradle Plugins] dependency to version 3.0.16.

## 1.2.65 - 2016-10-19

### Added
- [LPS-68448]: If the version override file of the project declares a different
version, update the `npm-shrinkwrap.json` and `package.json` files accordingly.

### Fixed
- [LPS-68448]: Restore `npm-shrinkwrap.json` and `package.json` version checks.

## 1.2.66 - 2016-10-19

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.17.

## 1.2.67 - 2016-10-20

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.18.

## 1.2.68 - 2016-10-20

### Changed
- [LPS-67434]: Update the [Liferay Gradle Plugins] dependency to version 3.0.19.

## 1.2.69 - 2016-10-20

### Changed
- [LPS-68839]: Update the [Liferay Gradle Plugins] dependency to version 3.0.20.

## 1.2.70 - 2016-10-21

### Changed
- [LPS-68838]: Update the [Liferay Gradle Plugins] dependency to version 3.0.21.

## 1.2.71 - 2016-10-21

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins] dependency to version 3.0.22.

## 1.2.72 - 2016-10-24

### Changed
- [LPS-68917]: Update the [Liferay Gradle Plugins] dependency to version 3.0.23.

## 1.2.73 - 2016-10-25

### Changed
- [LPS-52675]: Update the [Liferay Gradle Plugins] dependency to version 3.0.25.

## 1.2.74 - 2016-10-25

### Added
- [LPS-68935]: Set the Maven description to the project description when
publishing.

## 1.2.75 - 2016-10-26

### Changed
- [LPS-68917]: Update the [Liferay Gradle Plugins] dependency to version 3.0.26.

## 1.2.76 - 2016-10-27

### Changed
- [LPS-68980]: Update the [Liferay Gradle Plugins] dependency to version 3.0.27.

## 1.2.77 - 2016-10-28

*No changes.*

## 1.2.78 - 2016-10-31

### Changed
- [LPS-69013]: Update the [Liferay Gradle Plugins] dependency to version 3.0.29.

## 1.2.79 - 2016-10-31

### Changed
- [LPS-69013]: Update the [Liferay Gradle Plugins] dependency to version 3.0.30.

## 1.2.80 - 2016-11-01

### Changed
- [LPS-68293]: Update the [Liferay Gradle Plugins] dependency to version 3.0.31.

## 1.2.81 - 2016-11-01

### Changed
- [LPS-69026]: Update the [Liferay Gradle Plugins] dependency to version 3.0.32.

## 1.2.82 - 2016-11-02

### Changed
- [LPS-68293]: Update the [Liferay Gradle Plugins] dependency to version 3.0.33.

## 1.2.83 - 2016-11-03

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.34.

## 1.2.84 - 2016-11-03

### Changed
- [LPS-68298]: Update the [Liferay Gradle Plugins] dependency to version 3.0.35.

## 1.2.85 - 2016-11-04

### Changed
- [LPS-68298]: Update the [Liferay Gradle Plugins] dependency to version 3.0.36.

## 2.0.0 - 2016-11-17

### Changed
- [LPS-66762]: Apply [Liferay Gradle Plugins Baseline].
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-69223]: Update the [Liferay Gradle Plugins] dependency to version 3.0.37.

### Removed
- [LPS-66762]: The `BaselineTask` class is no longer available. Use the class in
[Liferay Gradle Plugins Baseline] instead.
- [LPS-67573]: Remove all deprecated methods.

## 2.0.1 - 2016-11-21

### Added
- [LPS-69288]: Set the `app.description` project property to override the
project description if the `com.liferay.app.defaults.plugin` is applied.
- [LPS-69288]: Set the `app.title` project property to override the title used
in the `appJavadoc` task.
- [LPS-69288]: Set the `app.version` project property to override the project
version if the `com.liferay.app.defaults.plugin` is applied.

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins] dependency to version 3.0.38.

## 2.0.2 - 2016-11-22

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.39.

## 2.0.3 - 2016-11-23

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.40.

## 2.0.4 - 2016-11-24

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins] dependency to version 3.0.41.

## 2.0.5 - 2016-11-28

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.42.

## 2.0.6 - 2016-11-29

### Added
- [LPS-68813]: Add the `findSecurityBugs` task to check for security problems.

### Changed
- [LPS-69445]: Update the [Liferay Gradle Plugins] dependency to version 3.0.43.

## 2.0.7 - 2016-11-30 [YANKED]

### Changed
- [LPS-69470]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.0.

## 2.0.8 - 2016-12-01

### Changed
- [LPS-69488]: Update the [Liferay Gradle Plugins] dependency to version 3.0.44.

### Removed
- [LPS-69488]: Remove the default Node.js version configuration since it has
been moved to [Liferay Gradle Plugins].

## 2.0.9 - 2016-12-01

### Changed
- [LPS-69492]: Update the [Liferay Gradle Plugins] dependency to version 3.0.45.

## 2.1.0 - 2016-12-03

### Added
- [LPS-68289]: Add module agent support for aspect in the `test` and
`testIntegration` tasks.

### Changed
- [LPS-69518]: Update the [Liferay Gradle Plugins] dependency to version 3.0.46.

## 2.1.1 - 2016-12-05

### Changed
- [LPS-69501]: Update the [Liferay Gradle Plugins] dependency to version 3.0.47.

## 2.2.0 - 2016-12-08

### Added
- [LPS-63943]: Add the ability to create additional *prep next* commits via
`PrintArtifactPublishCommands` tasks.

### Changed
- [LPS-63943]: Commit themes' `.digest` files in a separate *prep next* commit.
- [LPS-69618]: Update the [Liferay Gradle Plugins] dependency to version 3.0.48.

## 2.2.1 - 2016-12-08

### Changed
- [LPS-69501]: Update the [Liferay Gradle Plugins] dependency to version 3.0.49.

## 2.2.2 - 2016-12-12

### Added
- [LPS-69606]: Publish the WSDD fragment JAR of an OSGi project with the
`install` and `uploadArchives` tasks.

### Changed
- [LPS-69501]: Use the [Liferay Source Formatter] JAR files deployed locally in
the `tools/sdk/dependencies/com.liferay.source.formatter/lib` directory, if
found.

## 2.2.3 - 2016-12-14

### Changed
- [LPS-69677]: Update the [Liferay Gradle Plugins] dependency to version 3.0.50.

## 2.2.4 - 2016-12-14

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.51.
- [LPS-67694]: Disable the `install` and `uploadArchives` tasks for `*-test`
OSGi projects, Ant plugins and themes if the `-PsnapshotIfStale` argument is
provided.

## 2.3.0 - 2016-12-15

### Fixed
- [LPS-69606]: Fix artifact publish commands for the "WSDD" commit in older
versions of Git.
- [LPS-69606]: Fix artifact URL generation in the `artifact.properties` files.

## 2.3.1 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.52.

## 2.3.2 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.53.

## 2.3.3 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.54.

## 2.3.4 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.55.

## 2.3.5 - 2016-12-19

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.56.

## 2.3.6 - 2016-12-19

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.57.

## 2.3.7 - 2016-12-19

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins] dependency to version 3.0.58.

## 2.3.8 - 2016-12-20

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.59.

## 2.3.9 - 2016-12-21

### Changed
- [LPS-69802]: Update the [Liferay Gradle Plugins] dependency to version 3.0.60.

## 2.3.10 - 2016-12-21

### Changed
- [LPS-69838]: Update the [Liferay Gradle Plugins] dependency to version 3.0.61.

## 2.3.11 - 2016-12-27

### Added
- [LPS-69847]: Apply the [Liferay Gradle Plugins Dependency Checker] to throw an
error if the [Liferay Source Formatter] version in use is not the latest one
and is older than 30 days.

## 2.4.0 - 2016-12-29

### Added
- [LPS-61987]: Enforce the use of snapshot timestamp versions for dependencies.
- [LPS-69453]: Add an empty `deployDependencies` task to copy additional
dependency JAR files to the `deploy` directory.
- [LPS-69847]: Add the ability to ignore the locally deployed JAR files of a
portal tool by setting the project property `[portal tool name].ignore.local`.

## 2.4.1 - 2016-12-29

### Changed
- [LPS-69824]: Update the [Liferay Gradle Plugins] dependency to version 3.0.62.

## 2.4.2 - 2016-12-29

### Changed
- [LPS-69920]: Update the [Liferay Gradle Plugins] dependency to version 3.0.63.

## 2.4.3 - 2016-12-29

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.64.

## 2.4.4 - 2017-01-02

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.65.

## 2.4.5 - 2017-01-03

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.0.67.

## 2.4.6 - 2017-01-03

### Added
- [LPS-69719]: Exclude `**/archetype-resources/**.java` files from the
`.classpath` file to avoid compilation errors in Eclipse.

## 2.4.7 - 2017-01-04

### Fixed
- [LPS-61987] Fix snapshot timestamp version enforcement when `-Psnapshot` is
used.

## 2.4.8 - 2017-01-04

### Changed
- [LPS-69899]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.1.

### Fixed
- [LPS-69606]: Fix artifact publish commands for the *WSDD* commit in older
versions of Git.

## 2.4.9 - 2017-01-06

### Changed
- [LPS-69706]: Update the [Liferay Gradle Plugins] dependency to version 3.0.68.

### Fixed
- [LPS-65179]: Fix artifact publish commands in case of local unstaged changes.

## 2.4.10 - 2017-01-09

### Changed
- [LPS-69706]: Update the [Liferay Gradle Plugins] dependency to version 3.0.69.

## 2.4.11 - 2017-01-10

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.0.70.

## 2.4.12 - 2017-01-10

### Changed
- [LPS-70084]: Update the [Liferay Gradle Plugins] dependency to version 3.0.71.

## 2.4.13 - 2017-01-12

### Changed
- [LPS-70092]: Update the [Liferay Gradle Plugins] dependency to version 3.1.0.

## 2.4.14 - 2017-01-13

### Added
- [LPS-70146]: Add the `printDependentArtifact` task to print the project
directory if the project contains dependencies to other projects.

### Changed
- [LPS-70036]: Update the [Liferay Gradle Plugins] dependency to version 3.1.1.

## 2.4.15 - 2017-01-17

### Changed
- [LPS-70170]: Use the deployed JAR file of Liferay taglib dependencies for JSP
compilation.
- [LPS-70170]: Use the latest snapshot of the `com.liferay.util.taglib`
dependency for JSP compilation.

## 2.4.16 - 2017-01-20

### Changed
- [LPS-69501]: Use the published portal tools by default instead of the ones
deployed locally in the `tools/sdk/dependencies` directories.

## 2.4.17 - 2017-01-26

### Added
- [LPS-70282]: Automatically apply the `application` plugin if the `bnd.bnd`
file contains a `Main-Class` header.

### Changed
- [LPS-70282]: Update the [Liferay Gradle Plugins] dependency to version 3.1.2.
- [LPS-70286]: Change the `appJavadoc` task's generated module headings (e.g.,
*Liferay Journal API - com.liferay:com.liferay.journal:2.0.0*).

## 2.4.18 - 2017-01-27

### Changed
- [LPS-69926]: Make dependencies in the `compileInclude` configuration
non-transitive by default.
- [LPS-69926]: Make the `testCompile` configuration extend from the
`compileInclude` configuration.

## 2.4.19 - 2017-01-29

### Changed
- [LPS-70335]: Skip replacements of the `updateFileVersions` task in read-only
sub-repositories.
- [LPS-70336]: Update the [Liferay Gradle Plugins] dependency to version 3.1.3.

## 2.4.20 - 2017-01-30

### Fixed
- [LPS-70335]: Fix location of version override files.

## 2.4.21 - 2017-01-30

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.1.4.

## 2.4.22 - 2017-01-31

### Changed
- [LPS-69606]: Generate WSDD files in a temporary directory.
- [LPS-70379]: Update the [Liferay Gradle Plugins] dependency to version 3.1.5.
- [LPS-70379]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.2.

## 2.4.23 - 2017-02-01

### Changed
- [LPS-69926]: Move the `compileInclude` dependencies at the beginning of the
test compile and runtime classpaths.

## 2.4.24 - 2017-02-02

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.1.6.

## 2.4.25 - 2017-02-03

### Added
- [LPS-70424]: Add the ability to set a custom value for the project group by
setting the property `project.group` in a `gradle.properties` file located in
any parent directory of the project.

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins] dependency to version 3.1.7.

## 2.4.26 - 2017-02-04

### Changed
- [LPS-69926]: Set the `liferayOSGi.expandCompileInclude` property to `false` by
default.

## 2.4.27 - 2017-02-08

### Changed
- [LPS-70486]: Make the `compileClasspath` configuration non-transitive for
Liferay apps.
- [LPS-70486]: Use the `compileClasspath` configuration in the compile and
runtime classpaths of the `test` and `testIntegration` source sets.
- [LPS-70515]: Update the [Liferay Gradle Plugins] dependency to version 3.1.8.

## 2.4.28 - 2017-02-09

### Added
- [LPS-69920]: Set the system property `clean.node.modules` to `true` to delete
the `node_modules` directory of a project with the `clean` task.

### Changed
- [LPS-70451]: Update the [Liferay Gradle Plugins] dependency to version 3.1.9.

## 2.4.29 - 2017-02-09

### Changed
- [LPS-70555]: Update the [Liferay Gradle Plugins] dependency to version 3.2.0.

## 2.4.30 - 2017-02-12

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.2.1.

### Fixed
- [LPS-70584]: Avoid internet connection requirement when parsing `service.xml`
files.

## 2.4.31 - 2017-02-12

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.2.2.

## 2.4.32 - 2017-02-13

### Changed
- [LPS-70618]: Update the [Liferay Gradle Plugins] dependency to version 3.2.3.

## 2.4.33 - 2017-02-14

### Changed
- [LPS-70494]: Update the [Liferay Gradle Plugins] dependency to version 3.2.4.

### Fixed
- [LPS-67863]: Exclude test projects from the `gradlew baseline -PsyncRelease`
process.

## 2.4.34 - 2017-02-16

### Changed
- [LPS-70677]: Update the [Liferay Gradle Plugins] dependency to version 3.2.5.

### Removed
- [LPS-70677]: Remove exclusion of `com.liferay.portal` transitive dependencies
from the `com.liferay.jasper.jspc` default dependency in the `jspCTool`
configuration.
- [LPS-70699]: Disable the update check when building themes.

## 2.4.35 - 2017-02-17

### Changed
- [LPS-70707]: Update the [Liferay Gradle Plugins] dependency to version 3.2.6.

## 2.4.36 - 2017-02-21

### Added
- [LPS-70170]: Print out implicit dependency replacements in the `jspC`
configuration.

## 2.5.0 - 2017-02-22

### Added
- [LPS-70819]: Publish a JAR file with the compiled JSP classes of an OSGi
project with the `install` and `uploadArchives` tasks.

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.2.7.

## 3.0.0 - 2017-02-23

### Changed
- [LPS-63943]: Refactor the `PrintArtifactPublishCommandsTask` class and rename
it to `WriteArtifactPublishCommandsTask`. Executing
`gradlew writeArtifactPublishCommands` in a parent directory generates the file
`build/artifacts-publish-commands/artifacts-publish-commands.sh` with the
following commands for all publishable subprojects:
	- `gradlew baseline` (failing when semantic versioning errors are detected).
	- `gradlew baseline` (ignoring semantic versioning errors) and Git commands
	to commit the files modified by the `baseline` task.
	- the publish commands returned by the previous version of the plugin.
- [LPS-63943]: Rename the `printArtifactPublishCommands` task to
`writeArtifactPublishCommands`.

## 3.1.0 - 2017-02-23

### Added
- [LPS-70819]: Set the `jsp.precompile.from.source` project property to `false`
to make the `compileJSP` task download the archive listed in the
`artifact.jspc.url` artifact property instead of compiling the JSP pages of the
OSGi project.

### Changed
- [LPS-70870]: Update the [Liferay Gradle Plugins] dependency to version 3.2.8.

## 3.1.1 - 2017-02-24

### Changed
- [LPS-70170]: Change dependency replacements in the `jspC` configuration:
	- always use the deployed `util-taglib.jar` file; fail if it's not found.
	- substitute module taglib dependencies with project dependencies if they're
	found, falling back to the deployed JAR file; fail if neither the project nor
	the deployed JAR file are found.

## 3.1.2 - 2017-02-25

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins] dependency to version 3.2.9.

## 3.1.3 - 2017-02-27

### Changed
- [LPS-70170]: Lower the log level of `jspC` configuration dependency
replacement messages if the `compileJSP` task is not explicitly invoked.

## 3.1.4 - 2017-02-28

### Changed
- [LPS-70929]: Disable `-check: exports` if the `bnd.bnd` file contains the
`-exportcontents` instruction.
- [LPS-70941]: Update the [Liferay Gradle Plugins] dependency to version 3.2.10.

## 3.2.0 - 2017-03-01

### Added
- [LPS-63943]: Add Bash shebang and `set -e` at the beginning of the file
generated by the `mergeArtifactsPublishCommands` task.
- [LPS-63943]: Add the ability to set a header and/or a footer in the output
file generated by `MergeFilesTask` instances.
- [LPS-63943]: Make the file generated by the `mergeArtifactsPublishCommands`
task executable.

### Changed
- [LPS-70890]: Update the [Liferay Gradle Plugins] dependency to version 3.2.11.

## 3.2.1 - 2017-03-02

### Changed
- [LPS-62970]: Update the [Liferay Gradle Plugins] dependency to version 3.2.12.

## 3.2.2 - 2017-03-02

### Added
- [LPS-67039]: Add the ability to invoke PMD from a sub-repository by using
`gradle.gradleUserHomeDir` as the root for the `standard-rules.xml` file path.
- [LPS-71005]: Update the [Liferay Gradle Plugins] dependency to version 3.2.13.

## 3.2.3 - 2017-03-03

### Changed
- [LPS-71048]: Update the [Liferay Gradle Plugins] dependency to version 3.2.14.

## 3.2.4 - 2017-03-06

### Changed
- [LPS-70604]: Check whether the `:util:` or `:private:util:` projects have
their dependencies published before enabling the `printArtifactPublishCommands`
task.
- [LPS-71005]: Update the [Liferay Gradle Plugins] dependency to version 3.2.15.

## 3.2.5 - 2017-03-08

### Added
- [LPS-63943]: Print the file path for the `writeArtifactPublishCommands` task's
resulting `.sh` file.

### Changed
- [LPS-68405]: Update the [Liferay Gradle Plugins] dependency to version 3.2.16.

### Fixed
- [LPS-63943]: Avoid executing `writeArtifactPublishCommands` tasks if they're
not explicitly invoked.

## 3.3.0 - 2017-03-09

### Added
- [LPS-70634]: Automatically publish public themes to the NPM registry while
executing the `uploadArchives` task.

### Changed
- [LPS-70634]: Update the [Liferay Gradle Plugins] dependency to version 3.2.17.

## 3.3.1 - 2017-03-09

### Changed
- [LPS-67688]: Update the [Liferay Gradle Plugins] dependency to version 3.2.18.

## 3.4.0 - 2017-03-11

### Added
- [LPS-71201]: Fail release tasks if the project is being published from the
master branch, but it was previously already published from a release branch.

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.19.

## 3.4.1 - 2017-03-13

### Changed
- [LPS-71222]: Update the [Liferay Gradle Plugins] dependency to version 3.2.20.

## 3.4.2 - 2017-03-14

### Fixed
- [LPS-71224]: Always point the `artifact.url` property of `artifact.properties`
to the primary artifact, even when the `application` plugin is applied.

## 3.4.3 - 2017-03-15

### Changed
- [LPS-71118]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.3.
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.21.

## 3.5.0 - 2017-03-16

### Added
- [LPS-71303]: Set the `liferayThemeDefaults.useLocalDependencies` property to
`false` to avoid providing the `--css-common-path`, `--styled-path`, and
`--unstyled-path` arguments to the Gulp tasks. The dependencies declared in the
`package.json` are used instead.

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.22.

### Fixed
- [LPS-71264]: Use the Maven local repository's actual directory as the
default value for the `InstallCacheTask`'s `mavenRootDir` property.

## 3.5.1 - 2017-03-17

### Changed
- [LPS-71331]: Update the [Liferay Gradle Plugins] dependency to version 3.2.23.

## 3.5.2 - 2017-03-17

### Changed
- [LPS-66891]: Update the [Liferay Gradle Plugins] dependency to version 3.2.24.

## 3.5.3 - 2017-03-21

### Added
- [LPS-70146]: Disable the `printDependentArtifact` task for `*-test` projects.
- [LPS-71376]: Disable the `uploadArchives` task for `*-test` projects.

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.25.

### Fixed
- [LPS-63943]: Avoid failing the build when running
`gradlew writeArtifactPublishCommands` from a directory that does not contain
any publishable subprojects.

## 3.5.4 - 2017-03-22

### Added
- [LPS-71354]: Add the ability to set specific directories to include for
multi-project builds by setting the `build.include.dirs` system property.

## 3.5.5 - 2017-03-22

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.26.

## 3.5.6 - 2017-03-24

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.27.

## 3.5.7 - 2017-03-27

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.28.

## 3.5.8 - 2017-03-28

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.29.
- [LPS-71535]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.4.

## 3.5.9 - 2017-03-30

### Changed
- [LPS-71603]: Update the [Liferay Gradle Plugins] dependency to version 3.2.30.

### Removed
- [LPS-70819]: Avoid publishing the JAR file with the compiled JSP classes of an
OSGi project snapshot with the `install` and `uploadArchives` tasks.

## 3.5.10 - 2017-04-03

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.31.

## 3.5.11 - 2017-04-03

### Changed
- [LPS-53392]: Update the [Liferay Gradle Plugins] dependency to version 3.2.32.

## 3.5.12 - 2017-04-04

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.33.

## 3.5.13 - 2017-04-04

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.34.

## 3.5.14 - 2017-04-05

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.35.

## 3.5.15 - 2017-04-06

### Changed
- [LPS-71591]: Update the [Liferay Gradle Plugins] dependency to version 3.2.36.

## 3.5.16 - 2017-04-08

### Changed
- [LPS-64098]: Update the [Liferay Gradle Plugins] dependency to version 3.2.37.

### Fixed
- [LPS-71795]: Fix the [Gradle issue](https://github.com/gradle/gradle/issues/1094)
that occurs when executing the `findbugsMain` task on OSGi modules that include
resource files.

## 3.5.17 - 2017-04-11

### Changed
- [LPS-71826]: Update the [Liferay Gradle Plugins] dependency to version 3.2.38.

### Fixed
- [LPS-71795]: Generalize the [Gradle issue](https://github.com/gradle/gradle/issues/1094)
fix for all tasks of type `FindBugs` related to source sets.

## 3.5.18 - 2017-04-12

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins] dependency to version 3.2.39.

## 3.6.0 - 2017-04-14

### Added
- [LPS-71901]: Add task `updateFileSnapshotVersions` to update the project
version in external files to the latest snapshot.

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.2.40.

## 3.6.1 - 2017-04-17

### Changed
- [LPS-71686]: Update the [Liferay Gradle Plugins] dependency to version 3.2.41.

## 3.6.2 - 2017-04-18

### Changed
- [LPS-70451]: Update the [Liferay Gradle Plugins] dependency to version 3.3.0.

### Fixed
- [LPS-71901]: Fix wrong caching logic in `ReplaceRegexTask`.

## 3.6.3 - 2017-04-19

### Changed
- [LPS-72039]: Update the [Liferay Gradle Plugins] dependency to version 3.3.1.

## 3.6.4 - 2017-04-20

### Changed
- [LPS-72030]: Update the [Liferay Gradle Plugins] dependency to version 3.3.2.

## 3.6.5 - 2017-04-21

### Added
- [LPS-72045]: When on Jenkins, fail the `testIntegration` task if any dependent
projects defined in the `testIntegrationCompile` configuration do not have the
`.lfrbuild-portal` marker file.

### Changed
- [LPS-72067]: Avoid including `compileInclude` dependencies in the classpath of
[Find Security Bugs].
- [LPS-72067]: Avoid running the `findSecurityBugs` task if the classpath does
not contain a class or JAR file.
- [LPS-72067]: Update the [Find Security Bugs] dependency to version
1.6.0.LIFERAY-PATCHED-1.
- [LPS-72102]: Update the [Liferay Gradle Plugins] dependency to version 3.3.3.

## 3.6.6 - 2017-04-21

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins] dependency to version 3.3.4.

## 3.6.7 - 2017-04-25

### Changed
- [LPS-70819]: Use Gradle dependency management in the `downloadCompiledJSP`
task if the URL is protected.
- [LPS-72152]: Update the [Liferay Gradle Plugins] dependency to version 3.3.5.

## 3.6.8 - 2017-04-27

### Added
- [LPS-68813]: Execute `findSecurityBugs` with the `check` task.
- [LPS-68813]: Make the `findSecurityBugs` task visible in `gradle task`.

### Changed
- [LPS-67039]: Embed and use [Liferay's PMD rule set file](https://github.com/liferay/liferay-plugins/blob/master/dependencies/net.sourceforge.pmd/rulesets/java/standard-rules.xml).
- [LPS-71728]: Update the [Liferay Gradle Plugins] dependency to version 3.3.6.
- [LPS-71728]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.5.

## 3.6.9 - 2017-04-28

### Changed
- [LPS-71728]: Update the [Liferay Gradle Plugins] dependency to version 3.3.7.
- [LPS-71728]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.6.

## 3.6.10 - 2017-05-03

### Changed
- [LPS-72340]: Update the [Liferay Gradle Plugins] dependency to version 3.3.8.

## 3.6.11 - 2017-05-03

### Changed
- [LPS-72252]: Update the [Liferay Gradle Plugins] dependency to version 3.3.9.

## 3.6.12 - 2017-05-09

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins] dependency to version 3.3.10.

## 3.6.13 - 2017-05-09

### Changed
- [LRDOCS-3643]: Apply the [Liferay stylesheet](https://github.com/liferay/liferay-portal/blob/master/tools/styles/dtddoc.css)
file (if found) to the tag library documentation generated by the `appTLDDoc`
task.

## 3.6.14 - 2017-05-11

### Changed
- [LPS-72514]: Update the [Liferay Gradle Plugins] dependency to version 3.3.11.

## 3.6.15 - 2017-05-11

### Changed
- [LPS-72456]: Update the [Find Security Bugs] dependency to version
1.6.0.LIFERAY-PATCHED-2.

## 3.6.16 - 2017-05-13

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.3.12.

## 3.6.17 - 2017-05-15

### Changed
- [LPS-72562]: Update the [Liferay Gradle Plugins] dependency to version 3.3.13.

## 3.6.18 - 2017-05-16

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.3.14.

## 3.6.19 - 2017-05-19

### Changed
- [LPS-72572]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.7.
- [LPS-72656]: Update the [Liferay Gradle Plugins] dependency to version 3.3.15.

## 3.6.20 - 2017-05-23

### Changed
- [LPS-72723]: Update the [Liferay Gradle Plugins] dependency to version 3.3.16.

## 3.6.21 - 2017-05-23

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins] dependency to version 3.3.17.

## 3.6.22 - 2017-05-23

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins] dependency to version 3.3.18.

## 3.6.23 - 2017-05-23

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins] dependency to version 3.3.19.

## 3.6.24 - 2017-05-25

### Changed
- [LPS-72750]: Update the [Liferay Gradle Plugins] dependency to version 3.3.20.

## 3.6.25 - 2017-05-25

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.21.

## 3.7.0 - 2017-05-30

### Added
- [LPS-72830]: Add the `checkOSGiBundleState` task to fail the build if the
deployed OSGi bundle's state is not `ACTIVE`.

### Changed
- [LPS-69661]: Update the [Liferay Gradle Plugins] dependency to version 3.3.22.

## 3.7.1 - 2017-05-31

### Changed
- [LPS-72851]: Update the [Liferay Gradle Plugins] dependency to version 3.3.23.

## 3.7.2 - 2017-06-04

### Changed
- [LPS-72868]: Update the [Liferay Gradle Plugins] dependency to version 3.3.24.

### Fixed
- [LPS-72465]: Avoid caching the following system property values when using the
Gradle Daemon:
	- `maven.local.ignore`
	- `repository.private.password`
	- `repository.private.url`
	- `repository.private.username`
	- `repository.url`

## 3.7.3 - 2017-06-08

### Changed
- [LPS-72914]: Update the [Liferay Gradle Plugins] dependency to version 3.3.25.

### Fixed
- [LPS-72989]: Fix [Find Security Bugs] custom configuration loading on Windows.

[Find Security Bugs]: https://github.com/liferay/liferay-portal/tree/master/modules/third-party/com-h3xstream-findsecbugs
[Liferay CDN]: https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/public
[Liferay Gradle Plugins]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins
[Liferay Gradle Plugins App Javadoc Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-app-javadoc-builder
[Liferay Gradle Plugins Baseline]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-baseline
[Liferay Gradle Plugins Dependency Checker]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-dependency-checker
[Liferay Gradle Plugins Node]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-node
[Liferay Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/source-formatter
[LPS-52675]: https://issues.liferay.com/browse/LPS-52675
[LPS-53392]: https://issues.liferay.com/browse/LPS-53392
[LPS-58672]: https://issues.liferay.com/browse/LPS-58672
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-61987]: https://issues.liferay.com/browse/LPS-61987
[LPS-62970]: https://issues.liferay.com/browse/LPS-62970
[LPS-63943]: https://issues.liferay.com/browse/LPS-63943
[LPS-64098]: https://issues.liferay.com/browse/LPS-64098
[LPS-65179]: https://issues.liferay.com/browse/LPS-65179
[LPS-66396]: https://issues.liferay.com/browse/LPS-66396
[LPS-66762]: https://issues.liferay.com/browse/LPS-66762
[LPS-66853]: https://issues.liferay.com/browse/LPS-66853
[LPS-66891]: https://issues.liferay.com/browse/LPS-66891
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67039]: https://issues.liferay.com/browse/LPS-67039
[LPS-67352]: https://issues.liferay.com/browse/LPS-67352
[LPS-67434]: https://issues.liferay.com/browse/LPS-67434
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-67688]: https://issues.liferay.com/browse/LPS-67688
[LPS-67694]: https://issues.liferay.com/browse/LPS-67694
[LPS-67766]: https://issues.liferay.com/browse/LPS-67766
[LPS-67804]: https://issues.liferay.com/browse/LPS-67804
[LPS-67863]: https://issues.liferay.com/browse/LPS-67863
[LPS-67986]: https://issues.liferay.com/browse/LPS-67986
[LPS-67996]: https://issues.liferay.com/browse/LPS-67996
[LPS-68009]: https://issues.liferay.com/browse/LPS-68009
[LPS-68014]: https://issues.liferay.com/browse/LPS-68014
[LPS-68131]: https://issues.liferay.com/browse/LPS-68131
[LPS-68230]: https://issues.liferay.com/browse/LPS-68230
[LPS-68289]: https://issues.liferay.com/browse/LPS-68289
[LPS-68293]: https://issues.liferay.com/browse/LPS-68293
[LPS-68297]: https://issues.liferay.com/browse/LPS-68297
[LPS-68298]: https://issues.liferay.com/browse/LPS-68298
[LPS-68305]: https://issues.liferay.com/browse/LPS-68305
[LPS-68306]: https://issues.liferay.com/browse/LPS-68306
[LPS-68334]: https://issues.liferay.com/browse/LPS-68334
[LPS-68402]: https://issues.liferay.com/browse/LPS-68402
[LPS-68405]: https://issues.liferay.com/browse/LPS-68405
[LPS-68415]: https://issues.liferay.com/browse/LPS-68415
[LPS-68448]: https://issues.liferay.com/browse/LPS-68448
[LPS-68485]: https://issues.liferay.com/browse/LPS-68485
[LPS-68504]: https://issues.liferay.com/browse/LPS-68504
[LPS-68506]: https://issues.liferay.com/browse/LPS-68506
[LPS-68540]: https://issues.liferay.com/browse/LPS-68540
[LPS-68564]: https://issues.liferay.com/browse/LPS-68564
[LPS-68598]: https://issues.liferay.com/browse/LPS-68598
[LPS-68611]: https://issues.liferay.com/browse/LPS-68611
[LPS-68618]: https://issues.liferay.com/browse/LPS-68618
[LPS-68650]: https://issues.liferay.com/browse/LPS-68650
[LPS-68666]: https://issues.liferay.com/browse/LPS-68666
[LPS-68772]: https://issues.liferay.com/browse/LPS-68772
[LPS-68779]: https://issues.liferay.com/browse/LPS-68779
[LPS-68813]: https://issues.liferay.com/browse/LPS-68813
[LPS-68817]: https://issues.liferay.com/browse/LPS-68817
[LPS-68838]: https://issues.liferay.com/browse/LPS-68838
[LPS-68839]: https://issues.liferay.com/browse/LPS-68839
[LPS-68917]: https://issues.liferay.com/browse/LPS-68917
[LPS-68935]: https://issues.liferay.com/browse/LPS-68935
[LPS-68980]: https://issues.liferay.com/browse/LPS-68980
[LPS-69013]: https://issues.liferay.com/browse/LPS-69013
[LPS-69026]: https://issues.liferay.com/browse/LPS-69026
[LPS-69223]: https://issues.liferay.com/browse/LPS-69223
[LPS-69271]: https://issues.liferay.com/browse/LPS-69271
[LPS-69288]: https://issues.liferay.com/browse/LPS-69288
[LPS-69445]: https://issues.liferay.com/browse/LPS-69445
[LPS-69453]: https://issues.liferay.com/browse/LPS-69453
[LPS-69470]: https://issues.liferay.com/browse/LPS-69470
[LPS-69488]: https://issues.liferay.com/browse/LPS-69488
[LPS-69492]: https://issues.liferay.com/browse/LPS-69492
[LPS-69501]: https://issues.liferay.com/browse/LPS-69501
[LPS-69518]: https://issues.liferay.com/browse/LPS-69518
[LPS-69606]: https://issues.liferay.com/browse/LPS-69606
[LPS-69618]: https://issues.liferay.com/browse/LPS-69618
[LPS-69661]: https://issues.liferay.com/browse/LPS-69661
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677
[LPS-69706]: https://issues.liferay.com/browse/LPS-69706
[LPS-69719]: https://issues.liferay.com/browse/LPS-69719
[LPS-69730]: https://issues.liferay.com/browse/LPS-69730
[LPS-69802]: https://issues.liferay.com/browse/LPS-69802
[LPS-69824]: https://issues.liferay.com/browse/LPS-69824
[LPS-69838]: https://issues.liferay.com/browse/LPS-69838
[LPS-69847]: https://issues.liferay.com/browse/LPS-69847
[LPS-69899]: https://issues.liferay.com/browse/LPS-69899
[LPS-69920]: https://issues.liferay.com/browse/LPS-69920
[LPS-69926]: https://issues.liferay.com/browse/LPS-69926
[LPS-70036]: https://issues.liferay.com/browse/LPS-70036
[LPS-70084]: https://issues.liferay.com/browse/LPS-70084
[LPS-70092]: https://issues.liferay.com/browse/LPS-70092
[LPS-70146]: https://issues.liferay.com/browse/LPS-70146
[LPS-70170]: https://issues.liferay.com/browse/LPS-70170
[LPS-70282]: https://issues.liferay.com/browse/LPS-70282
[LPS-70286]: https://issues.liferay.com/browse/LPS-70286
[LPS-70335]: https://issues.liferay.com/browse/LPS-70335
[LPS-70336]: https://issues.liferay.com/browse/LPS-70336
[LPS-70379]: https://issues.liferay.com/browse/LPS-70379
[LPS-70424]: https://issues.liferay.com/browse/LPS-70424
[LPS-70451]: https://issues.liferay.com/browse/LPS-70451
[LPS-70486]: https://issues.liferay.com/browse/LPS-70486
[LPS-70494]: https://issues.liferay.com/browse/LPS-70494
[LPS-70515]: https://issues.liferay.com/browse/LPS-70515
[LPS-70555]: https://issues.liferay.com/browse/LPS-70555
[LPS-70584]: https://issues.liferay.com/browse/LPS-70584
[LPS-70604]: https://issues.liferay.com/browse/LPS-70604
[LPS-70618]: https://issues.liferay.com/browse/LPS-70618
[LPS-70634]: https://issues.liferay.com/browse/LPS-70634
[LPS-70677]: https://issues.liferay.com/browse/LPS-70677
[LPS-70699]: https://issues.liferay.com/browse/LPS-70699
[LPS-70707]: https://issues.liferay.com/browse/LPS-70707
[LPS-70819]: https://issues.liferay.com/browse/LPS-70819
[LPS-70870]: https://issues.liferay.com/browse/LPS-70870
[LPS-70890]: https://issues.liferay.com/browse/LPS-70890
[LPS-70929]: https://issues.liferay.com/browse/LPS-70929
[LPS-70941]: https://issues.liferay.com/browse/LPS-70941
[LPS-71005]: https://issues.liferay.com/browse/LPS-71005
[LPS-71048]: https://issues.liferay.com/browse/LPS-71048
[LPS-71118]: https://issues.liferay.com/browse/LPS-71118
[LPS-71164]: https://issues.liferay.com/browse/LPS-71164
[LPS-71201]: https://issues.liferay.com/browse/LPS-71201
[LPS-71222]: https://issues.liferay.com/browse/LPS-71222
[LPS-71224]: https://issues.liferay.com/browse/LPS-71224
[LPS-71264]: https://issues.liferay.com/browse/LPS-71264
[LPS-71303]: https://issues.liferay.com/browse/LPS-71303
[LPS-71331]: https://issues.liferay.com/browse/LPS-71331
[LPS-71354]: https://issues.liferay.com/browse/LPS-71354
[LPS-71376]: https://issues.liferay.com/browse/LPS-71376
[LPS-71535]: https://issues.liferay.com/browse/LPS-71535
[LPS-71591]: https://issues.liferay.com/browse/LPS-71591
[LPS-71603]: https://issues.liferay.com/browse/LPS-71603
[LPS-71686]: https://issues.liferay.com/browse/LPS-71686
[LPS-71722]: https://issues.liferay.com/browse/LPS-71722
[LPS-71728]: https://issues.liferay.com/browse/LPS-71728
[LPS-71795]: https://issues.liferay.com/browse/LPS-71795
[LPS-71826]: https://issues.liferay.com/browse/LPS-71826
[LPS-71901]: https://issues.liferay.com/browse/LPS-71901
[LPS-72030]: https://issues.liferay.com/browse/LPS-72030
[LPS-72039]: https://issues.liferay.com/browse/LPS-72039
[LPS-72045]: https://issues.liferay.com/browse/LPS-72045
[LPS-72067]: https://issues.liferay.com/browse/LPS-72067
[LPS-72102]: https://issues.liferay.com/browse/LPS-72102
[LPS-72152]: https://issues.liferay.com/browse/LPS-72152
[LPS-72252]: https://issues.liferay.com/browse/LPS-72252
[LPS-72340]: https://issues.liferay.com/browse/LPS-72340
[LPS-72456]: https://issues.liferay.com/browse/LPS-72456
[LPS-72465]: https://issues.liferay.com/browse/LPS-72465
[LPS-72514]: https://issues.liferay.com/browse/LPS-72514
[LPS-72562]: https://issues.liferay.com/browse/LPS-72562
[LPS-72572]: https://issues.liferay.com/browse/LPS-72572
[LPS-72656]: https://issues.liferay.com/browse/LPS-72656
[LPS-72705]: https://issues.liferay.com/browse/LPS-72705
[LPS-72723]: https://issues.liferay.com/browse/LPS-72723
[LPS-72750]: https://issues.liferay.com/browse/LPS-72750
[LPS-72830]: https://issues.liferay.com/browse/LPS-72830
[LPS-72851]: https://issues.liferay.com/browse/LPS-72851
[LPS-72868]: https://issues.liferay.com/browse/LPS-72868
[LPS-72914]: https://issues.liferay.com/browse/LPS-72914
[LPS-72989]: https://issues.liferay.com/browse/LPS-72989
[LRDOCS-2594]: https://issues.liferay.com/browse/LRDOCS-2594
[LRDOCS-2841]: https://issues.liferay.com/browse/LRDOCS-2841
[LRDOCS-2981]: https://issues.liferay.com/browse/LRDOCS-2981
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023
[LRDOCS-3038]: https://issues.liferay.com/browse/LRDOCS-3038
[LRDOCS-3643]: https://issues.liferay.com/browse/LRDOCS-3643