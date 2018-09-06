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
from the `jspCTool` configuration's `com.liferay.jasper.jspc` default
dependency.
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
- [LPS-71901]: Add the task `updateFileSnapshotVersions` to update the project
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

## 3.7.4 - 2017-06-13

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.26.

### Fixed
- [LPS-71201]: Allow projects with a `.lfrbuild-releng-ignore` marker file to be
published from the master branch.

## 3.7.5 - 2017-06-13

### Changed
- [LPS-73058]: Update the [Liferay Gradle Plugins] dependency to version 3.3.27.

## 3.7.6 - 2017-06-15

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.28.

## 3.7.7 - 2017-06-16

### Changed
- [LPS-73148]: Update the [Liferay Gradle Plugins] dependency to version 3.3.29.

## 3.7.8 - 2017-06-19

### Changed
- [LPS-73156]: Update the [Liferay Gradle Plugins] dependency to version 3.3.31.

## 3.7.9 - 2017-06-22

### Changed
- [LPS-73128]: Configure [Find Security Bugs] to report only medium and high
confidence warnings.
- [LPS-73128]: Update the [Find Security Bugs] dependency to version
1.6.0.LIFERAY-PATCHED-3.

### Removed
- [LPS-73235]: Remove snapshot publish commands generated by the
`writeArtifactPublishCommands` task.

## 3.7.10 - 2017-06-23

### Added
- [LPS-73271]: Set the [Find Security Bugs] exit code, but ignore it by default.

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.32.
- [LPS-73271]: Print the [Find Security Bugs] report location, even in case of
failure.

## 3.7.11 - 2017-06-27

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.33.
- [LPS-73289]: Update the [Find Security Bugs] dependency to version
1.6.0.LIFERAY-PATCHED-4.

## 3.7.12 - 2017-06-28

### Changed
- [LPS-73327]: Disable JVM assertions for unit tests.

## 3.8.0 - 2017-06-30

### Added
- [LPS-73141]: Add the `.m2-tmp` directory in the portal root as a temporary
Maven repository.
- [LPS-73141]: Add the ability for `InstallCacheTask` to install the project's
artifact in a Maven repository.
- [LPS-73141]: Configure the `installCache` task to install the project's
artifact in the `.m2-tmp` directory by default.
- [LPS-73352]: Add the JaCoCo Java Agent to the `test` and `testIntegration`
tasks if the system property `jacoco.code.coverage` is set to `true`.

## 3.8.1 - 2017-06-30

### Changed
- [LPS-65930]: Update the [Liferay Gradle Plugins] dependency to version 3.3.34.

## 3.8.2 - 2017-07-03

### Changed
- [LPS-73352]: Rename classes and configuration names from `Jacoco` to `JaCoCo`.

## 3.8.3 - 2017-07-04

### Changed
- [LPS-73383]: Update the [Liferay Gradle Plugins] dependency to version 3.3.35.

## 3.8.4 - 2017-07-06

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.36.

## 3.8.5 - 2017-07-07

*No changes.*

## 3.8.6 - 2017-07-10

### Changed
- [LPS-73495]: Update the [Liferay Gradle Plugins] dependency to version 3.3.37.

## 3.8.7 - 2017-07-10

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.3.38.

## 3.9.0 - 2017-07-11

### Added
- [LPS-73489]: Add the plugin `LiferayRootDefaultsPlugin`, which can be applied
to root projects to
	- apply the [Liferay Gradle Plugins Source Formatter].
	- apply `com.liferay.app.defaults.plugin`.
	- automatically configure the subprojects.
	- configure default Maven repositories.

### Changed
- [LPS-73489]: Update the [Liferay Gradle Plugins] dependency to version 3.4.0.

## 3.9.1 - 2017-07-11

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.1.

## 4.0.0 - 2017-07-12

### Changed
- [LPS-73525]: Update the [Liferay Gradle Plugins] dependency to version 3.4.2.

### Removed
- [LPS-73525]: Remove all deprecated methods.
- [LPS-73525]: The tasks `test` and `testIntegration` are no longer configurated
with an AspectJ weaver; hence the configuration `aspectJWeaver` is no longer
available.

## 4.0.1 - 2017-07-13

### Changed
- [LPS-73584]: Update the [Liferay Gradle Plugins] dependency to version 3.4.3.

## 4.0.2 - 2017-07-13

### Changed
- [LPS-73584]: Update the [Liferay Gradle Plugins] dependency to version 3.4.4.

## 4.0.3 - 2017-07-14

### Added
- [LPS-73607]: Add the ability to force deployment in a different directory by
setting the project property `forced.deploy.dir`.

### Changed
- [LPS-73470]: Update the [Liferay Gradle Plugins] dependency to version 3.4.5.

### Fixed
- [LPS-73584]: Trigger the `-PsyncRelease` logic even when Gradle is invoked
with the full path of the `baseline` task.

## 4.0.4 - 2017-07-17

### Fixed
- [LPS-73652]: Ignore test project dependencies in the `printDependentArtifact`
and `writeArtifactPublishCommands` tasks.

## 4.0.5 - 2017-07-17

### Changed
- [LPS-73642]: Update the [Liferay Gradle Plugins] dependency to version 3.4.6.

## 4.0.6 - 2017-07-18

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.7.

## 4.0.7 - 2017-07-19

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.8.

## 4.0.8 - 2017-07-19

### Changed
- [LPS-73600]: Update the [Liferay Gradle Plugins] dependency to version 3.4.9.

## 4.0.9 - 2017-07-20

### Changed
- [LPS-73600]: Update the [Liferay Gradle Plugins] dependency to version 3.4.10.

## 4.0.10 - 2017-07-21

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.11.

## 4.0.11 - 2017-07-24

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.12.

## 4.0.12 - 2017-07-24

### Changed
- [LPS-73353]: Add the JaCoCo Java Agent only to the `test` task if the system
or project property `junit.code.coverage` is set to `true`.
- [LPS-73353]: Update the [Liferay Gradle Plugins] dependency to version 3.4.13.

### Removed
- [LPS-73353]: The `jacoco.code.coverage` system property is no longer
available.

## 4.1.0 - 2017-07-24

### Added
- [LPS-72854]: Allow the [Liferay Gradle Plugins Lang Merger] to be applied to
a theme project.

## 4.1.1 - 2017-07-25

### Changed
- [LPS-73807]: Update the [Find Security Bugs] dependency to version
1.6.0.LIFERAY-PATCHED-5.

## 4.1.2 - 2017-07-25

### Changed
- [LPS-72347]: Update the [Liferay Gradle Plugins] dependency to version 3.4.14.

## 4.1.3 - 2017-07-26

### Changed
- [LPS-73818]: Update the [Liferay Gradle Plugins] dependency to version 3.4.15.

## 4.1.4 - 2017-07-26

### Added
- [LPS-73655]: Add new task type called `CopyIvyDependenciesTask`, which allows
dependencies declared in an `ivy.xml` file to be downloaded via Gradle.
- [LPS-73655]: Download Ant plugins' Ivy dependencies via Gradle.

## 4.1.5 - 2017-07-26

### Added
- [LPS-73655]: Add support in `CopyIvyDependenciesTask` for Ivy [`<exclude>`](http://ant.apache.org/ivy/history/latest-milestone/ivyfile/exclude.html)
elements.

## 4.1.6 - 2017-07-27

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.16.

## 5.0.0 - 2017-07-31

### Added
- [LPS-73655]: Add support in `CopyIvyDependenciesTask` for the `transitive`
attribute in Ivy `<dependency>` elements.

### Changed
- [LPS-63943]: Show `mergeArtifactsPublishCommands` messages at log level
`QUIET` instead of `LIFECYCLE`.
- [LPS-73855]: Update the [Liferay Gradle Plugins] dependency to version 3.4.17.

## 5.0.1 - 2017-08-01

### Changed
- Update the [Liferay Gradle Plugins] dependency to version 3.4.18.

## 5.0.2 - 2017-08-03

### Changed
- [LPS-73935]: Update the [Liferay Gradle Plugins] dependency to version 3.4.19.

## 5.0.3 - 2017-08-04

### Changed
- [LPS-74034]: Update the [Liferay Gradle Plugins] dependency to version 3.4.20.

## 5.0.4 - 2017-08-07

### Added
- [LPS-74054]: Fail snapshot release task if the project is being published from
a release branch.

## 5.0.5 - 2017-08-07

### Changed
- [LPS-74063]: Update the [Liferay Gradle Plugins] dependency to version 3.4.21.

## 5.0.6 - 2017-08-07

### Added
- [LPS-73955]: Enhance project inclusion logic based on the values of the
`build.profile` system property and the `liferay.releng.public` project
property:
	- if the `liferay.releng.public` project property is `false`, include all
	projects containing either a `.lfrbuild-[build.profile]` or a
	`.lfrbuild-[build.profile]-private` marker file.
	- otherwise, include all projects containing either a
	`.lfrbuild-[build.profile]` or a `.lfrbuild-[build.profile]-public` marker
	file.

## 5.0.7 - 2017-08-08

### Changed
- [LPS-74092]: Update the [Liferay Gradle Plugins] dependency to version 3.4.22.

## 5.0.8 - 2017-08-09

### Changed
- [LPS-74104]: Update the [Liferay Gradle Plugins] dependency to version 3.4.23.

## 5.0.9 - 2017-08-09

### Changed
- [LPS-73967]: Update the [Liferay Gradle Plugins] dependency to version 3.4.24.

## 5.0.10 - 2017-08-09

### Changed
- [LPS-74088]: Update the [Liferay Gradle Plugins] dependency to version 3.4.25.

## 5.0.11 - 2017-08-11

### Changed
- [LPS-73967]: Update the [Liferay Gradle Plugins] dependency to version 3.4.26.

## 5.0.12 - 2017-08-12

### Changed
- [LPS-74126]: Update the [Liferay Gradle Plugins] dependency to version 3.4.27.

## 5.0.13 - 2017-08-15

### Changed
- [LPS-74155]: Update the [Liferay Gradle Plugins] dependency to version 3.4.28.

## 5.0.14 - 2017-08-15

### Changed
- [LPS-74139]: Update the [Liferay Gradle Plugins] dependency to version 3.4.29.

## 5.0.15 - 2017-08-15

### Changed
- [LPS-74126]: Update the [Liferay Gradle Plugins] dependency to version 3.4.30.

## 5.0.16 - 2017-08-15

### Added
- [LPS-71285]: Apply and configure version 0.3.11 of the [Gradle License Report]
plugin in OSGi and Ant plugin projects if the system property
`license.report.enabled` is `true`. Doing this generates a `versions.xml` file
containing information about dependencies
	- declared in an OSGi project's `compileInclude` configuration.
	- declared in an OSGi project's `provided` configuration; the project's
	`bnd.bnd` file must include a `-includeresource` or `Include-Resource`
	property.
	- declared in the Ant plugin project's `ivy.xml` file.
- [LPS-71285]: Add the ability to overwrite the default destination directory of
the `versions.xml` file by setting the system property
`license.report.output.dir`.

## 5.0.17 - 2017-08-16

### Changed
- [LPS-74139]: Update the [Liferay Gradle Plugins] dependency to version 3.4.31.

## 5.0.18 - 2017-08-16

### Added
- [LPS-74210]: Add the ability to set one or more `-Xlint` compiler arguments by
setting the system property `[task name].lint`, where `[task name]` is the name
of the `JavaCompile` task to configure.

## 5.0.19 - 2017-08-17

### Changed
- [LPS-74222]: Update the [Liferay Gradle Plugins] dependency to version 3.4.32.

## 5.0.20 - 2017-08-18

### Changed
- [LPS-74155]: Update the [Liferay Gradle Plugins] dependency to version 3.4.33.

## 5.0.21 - 2017-08-21

### Changed
- [LPS-74250]: Update the [Liferay Gradle Plugins] dependency to version 3.4.34.

## 5.0.22 - 2017-08-22

### Changed
- [LPS-74269]: Update the [Liferay Gradle Plugins] dependency to version 3.4.35.

## 5.0.23 - 2017-08-23

### Changed
- [LPS-74278]: Update the [Liferay Gradle Plugins] dependency to version 3.4.36.

## 5.0.24 - 2017-08-24

### Changed
- [LPS-74314]: Update the [Liferay Gradle Plugins] dependency to version 3.4.37.

## 5.0.25 - 2017-08-24

### Changed
- [LPS-74343]: Update the [Liferay Gradle Plugins] dependency to version 3.4.38.

## 5.0.26 - 2017-08-24

### Changed
- [LPS-74345]: Update the [Liferay Gradle Plugins] dependency to version 3.4.39.

### Removed
- [LPS-74345]: The [`Eclipse`](https://docs.gradle.org/current/userguide/eclipse_plugin.html)
plugin is no longer applied to OSGi projects.

## 5.0.27 - 2017-08-28

### Changed
- [LPS-74368]: Update the [Liferay Gradle Plugins] dependency to version 3.4.41.

## 5.0.28 - 2017-08-28

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.42.

## 5.0.29 - 2017-08-29

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.43.

## 5.0.30 - 2017-08-29

### Added
- [LPS-73070]: Check the module's version in the `package-lock.json` file and
ensure it matches the project version.

### Changed
- [LPS-73472]: Update the [Liferay Gradle Plugins] dependency to version 3.4.44.

## 5.0.31 - 2017-08-29

### Changed
- [LPS-73124]: Update the [Liferay Gradle Plugins] dependency to version 3.4.45.

## 5.0.32 - 2017-08-29

### Changed
- [LPS-74433]: Update the [Liferay Gradle Plugins] dependency to version 3.4.46.

## 5.0.33 - 2017-08-31

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins] dependency to version 3.4.47.

## 5.0.34 - 2017-08-31

### Added
- [LPS-74469]: Fail the build if the `soyCompile` configuration contains project
dependencies that belong to a different sub-repository.

### Changed
- [LPS-74469]: Allow publishing modules whose `soyCompile` configuration
contains project dependencies.

## 5.0.35 - 2017-08-31

### Changed
- [LPS-74469]: Look for `.gitrepo` files instead of `settings.gradle` when
checking if the `soyCompile` configuration contains project dependencies that
belong to a different sub-repository.

## 5.0.36 - 2017-09-06

### Changed
- [LPS-74538]: Update the [Liferay Gradle Plugins] dependency to version 3.4.49.

## 5.0.37 - 2017-09-06

### Changed
- [LPS-74490]: Update the [Liferay Gradle Plugins] dependency to version 3.4.50.

## 5.0.38 - 2017-09-07

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.4.51.

## 5.0.39 - 2017-09-08

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.4.52.

## 5.0.40 - 2017-09-10

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.4.53.

## 5.0.41 - 2017-09-11

### Changed
- [LPS-74614]: Update the [Liferay Gradle Plugins] dependency to version 3.4.54.

## 5.0.42 - 2017-09-11

### Changed
- [LPS-74373]: Update the [Liferay Gradle Plugins] dependency to version 3.4.55.

## 5.0.43 - 2017-09-12

### Changed
- [LPS-74207]: Update the [Liferay Gradle Plugins] dependency to version 3.4.56.

## 5.0.44 - 2017-09-12

### Changed
- [LPS-74637]: Update the [Liferay Gradle Plugins] dependency to version 3.4.57.

## 5.0.45 - 2017-09-13

### Changed
- [LPS-74657]: Update the [Liferay Gradle Plugins] dependency to version 3.4.58.

## 5.0.46 - 2017-09-14

### Changed
- [LPS-74614]: Update the [Liferay Gradle Plugins] dependency to version 3.4.59.

## 5.0.47 - 2017-09-18

### Changed
- [LPS-74752]: Update the [Liferay Gradle Plugins] dependency to version 3.4.60.

## 5.0.48 - 2017-09-18

### Changed
- [LPS-74637]: Update the [Liferay Gradle Plugins] dependency to version 3.4.61.

## 5.0.49 - 2017-09-18

### Changed
- [LPS-74770]: Update the [Liferay Gradle Plugins] dependency to version 3.4.62.

## 5.0.50 - 2017-09-19

### Changed
- [LPS-74657]: Update the [Liferay Gradle Plugins] dependency to version 3.4.63.

## 5.0.51 - 2017-09-19

### Changed
- [LPS-74789]: Update the [Liferay Gradle Plugins] dependency to version 3.4.64.

## 5.0.52 - 2017-09-19

### Changed
- [LPS-74657]: Update the [Liferay Gradle Plugins] dependency to version 3.4.65.

## 5.0.53 - 2017-09-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.4.66.

## 5.0.54 - 2017-09-19

### Changed
- [LPS-74738]: Update the [Liferay Gradle Plugins] dependency to version 3.4.67.

## 5.0.55 - 2017-09-21

### Changed
- [LPS-74824]: Update the [Liferay Gradle Plugins] dependency to version 3.4.68.

## 5.0.56 - 2017-09-23

### Changed
- [LPS-71117]: Update the [Liferay Gradle Plugins] dependency to version 3.4.69.

## 5.0.57 - 2017-09-25

### Changed
- [LPS-74884]: Update the [Liferay Gradle Plugins] dependency to version 3.4.70.

## 5.1.0 - 2017-09-26

### Added
- [LPS-74749]: Update the [Liferay Gradle Plugins] dependency to version 3.4.71.
- [LPS-74892]: For OSGi modules, publish an additional `sources-commercial` JAR.
If the module is public, the original copyright in the source files is replaced
with a commercial copyright.

## 5.1.1 - 2017-09-27

### Changed
- [LPS-74867]: Update the [Liferay Gradle Plugins] dependency to version 3.4.72.

## 5.1.2 - 2017-09-28

### Added
- [LPS-74933]: Automatically disable the `PublishNodeModuleTask` instances for
private projects.
- [LPS-74933]: Publish *alpha* versions of packages on the NPM registry when
running the `uploadArchives` task with `-Psnapshot`.

### Changed
- [LPS-74933]: Update the [Liferay Gradle Plugins] dependency to version 3.4.73.

## 5.1.3 - 2017-10-02

### Changed
- [LPS-75009]: Update the [Liferay Gradle Plugins] dependency to version 3.4.74.

## 5.1.4 - 2017-10-02 [YANKED]

### Changed
- [LPS-74110]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.8.
- [LPS-74110]: Update the [Liferay Gradle Plugins] dependency to version 3.4.75.
- [LPS-75039]: Make the `updateFileVersions` task exclude build and temporary
directories.
- [LPS-75039]: Make the `updateFileVersions` task update only the Gradle files
in the `modules` directory.

### Fixed
- [LPS-63943]: Always print the status message after executing the
`mergeArtifactsPublishCommands` task.

## 5.1.5 - 2017-10-04

### Changed
- [LPS-74110]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.7.
- [LPS-74314]: Update the [Liferay Gradle Plugins] dependency to version 3.5.0.

## 5.1.6 - 2017-10-05

### Changed
- [LPS-75047]: Update the [Liferay Gradle Plugins] dependency to version 3.5.1.

## 5.1.7 - 2017-10-05

### Changed
- [LPS-74143]: Update the [Liferay Gradle Plugins] dependency to version 3.5.2.

## 5.1.8 - 2017-10-06

### Changed
- [LPS-74426]: Update the [Liferay Gradle Plugins] dependency to version 3.5.3.

## 5.1.9 - 2017-10-06

### Changed
- [LPS-74143]: Update the [Liferay Gradle Plugins] dependency to version 3.5.4.

## 5.1.10 - 2017-10-08

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.5.

## 5.1.11 - 2017-10-09

### Fixed
- [LPS-63943]: Disable the `mergeArtifactsPublishCommands` task's up-to-date
check.
- [LPS-63943]: Fix the error thrown when executing the
`writeArtifactPublishCommands` task from the root project directory.

## 5.1.12 - 2017-10-10

### Changed
- [LPS-75175]: Update the [Liferay Gradle Plugins] dependency to version 3.5.6.

## 5.1.13 - 2017-10-11

### Changed
- [LPS-75096]: Update the [Liferay Gradle Plugins] dependency to version 3.5.7.

## 5.1.14 - 2017-10-11

### Changed
- [LPS-74449]: Update the [Liferay Gradle Plugins] dependency to version 3.5.8.

## 5.1.15 - 2017-10-16

### Changed
- [LPS-75254]: Update the [Liferay Gradle Plugins] dependency to version 3.5.9.

## 5.1.16 - 2017-10-16

### Changed
- [LPS-75273]: Update the [Liferay Gradle Plugins] dependency to version 3.5.10.

## 5.1.17 - 2017-10-17

### Changed
- [LPS-75100]: Update the [Liferay Gradle Plugins] dependency to version 3.5.11.

## 5.1.18 - 2017-10-17

### Changed
- [LPS-75100]: Update the [Liferay Gradle Plugins] dependency to version 3.5.12.

## 5.1.19 - 2017-10-17

### Changed
- [LPS-75239]: Update the [Liferay Gradle Plugins] dependency to version 3.5.13.

## 5.1.20 - 2017-10-18

### Changed
- [LPS-74849]: Update the [Liferay Gradle Plugins] dependency to version 3.5.14.

## 5.1.21 - 2017-10-18

### Changed
- [LPS-74849]: Update the [Liferay Gradle Plugins] dependency to version 3.5.15.

## 5.1.22 - 2017-10-19

### Changed
- [LPS-74348]: Update the [Liferay Gradle Plugins] dependency to version 3.5.16.

## 5.1.23 - 2017-10-20

### Changed
- [LPS-75254]: Update the [Liferay Gradle Plugins] dependency to version 3.5.17.

## 5.1.24 - 2017-10-22

### Changed
- [LPS-74457]: Update the [Liferay Gradle Plugins] dependency to version 3.5.18.

## 5.1.25 - 2017-10-23

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.19.

## 5.1.26 - 2017-10-24

### Changed
- [LPS-75430]: Update the [Liferay Gradle Plugins] dependency to version 3.5.20.

## 5.1.27 - 2017-10-24

### Changed
- [LPS-75323]: Update the [Liferay Gradle Plugins] dependency to version 3.5.21.

## 5.1.28 - 2017-10-25

### Changed
- [LPS-74849]: Update the [Liferay Gradle Plugins] dependency to version 3.5.22.

## 5.1.29 - 2017-10-26

### Changed
- [LPS-75323]: Update the [Liferay Gradle Plugins] dependency to version 3.5.23.

## 5.1.30 - 2017-10-31

### Changed
- [LPS-75488]: Update the [Liferay Gradle Plugins] dependency to version 3.5.24.

## 5.1.31 - 2017-11-01

### Changed
- [LPS-75624]: Update the [Liferay Gradle Plugins] dependency to version 3.5.26.

## 5.1.32 - 2017-11-02

### Changed
- [LPS-75399]: Update the [Liferay Gradle Plugins] dependency to version 3.5.27.

## 5.1.33 - 2017-11-03

### Changed
- [LPS-75247]: Update the [Liferay Gradle Plugins] dependency to version 3.5.28.
- [LPS-75705]: Always exclude `build` and `node_modules` directories from the
multi-project build.

## 5.1.34 - 2017-11-06

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.29.

## 5.1.35 - 2017-11-07

### Changed
- [LPS-75745]: Update the [Liferay Gradle Plugins] dependency to version 3.5.30.

## 5.1.36 - 2017-11-07

### Changed
- [LPS-75633]: Update the [Liferay Gradle Plugins] dependency to version 3.5.31.

## 5.1.37 - 2017-11-07

### Changed
- [LPS-74457]: Update the [Liferay Gradle Plugins] dependency to version 3.5.32.

## 5.1.38 - 2017-11-08

### Changed
- [LPS-75323]: Update the [Liferay Gradle Plugins] dependency to version 3.5.33.

## 5.1.39 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins] dependency to version 3.5.34.

## 5.1.40 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins] dependency to version 3.5.35.

## 5.1.41 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins] dependency to version 3.5.36.

## 5.1.42 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins] dependency to version 3.5.37.

## 5.1.43 - 2017-11-09

### Changed
- [LPS-75610]: Update the [Liferay Gradle Plugins] dependency to version 3.5.38.

## 5.1.44 - 2017-11-10

### Changed
- [LPS-69999]: Prevent the `updateFileVersions` task from converting project
dependencies into module dependencies in test projects.

## 5.1.45 - 2017-11-10

### Changed
- [LPS-75010]: Update the [Liferay Gradle Plugins] dependency to version 3.5.39.

## 5.1.46 - 2017-11-12

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins] dependency to version 3.5.40.

## 5.1.47 - 2017-11-13

### Changed
- [LPS-75829]: Update the [Liferay Gradle Plugins] dependency to version 3.5.41.

## 5.1.48 - 2017-11-14

### Added
- [LPS-75359]: Automatically exclude unfetchable [EasyConf](http://easyconf.sourceforge.net/)
transitive dependencies.
- [LPS-75359]: Force specific versions of EasyConf transitive dependencies in
the `testCompileClasspath` and `testRuntime` configurations:
	- `commons-configuration:commons-configuration:1.10`
	- `xerces:xercesImpl:2.11.0`
	- `xml-apis:xml-apis:1.4.01`

## 5.1.49 - 2017-11-14

### Changed
- [LPS-74526]: Update the [Liferay Gradle Plugins] dependency to version 3.5.42.

## 5.1.50 - 2017-11-14

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins] dependency to version 3.5.43.

## 5.1.51 - 2017-11-15

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins] dependency to version 3.5.44.
- [LPS-75910]: Update the [Liferay Gradle Plugins Dependency Checker] dependency
to version 1.0.1.

## 5.1.52 - 2017-11-16

### Changed
- [LPS-75952]: Update the [Liferay Gradle Plugins] dependency to version 3.5.45.

## 5.1.53 - 2017-11-20

### Added
- [LPS-73070]: Prevent npm from creating a `package-lock.json` file when running
the `npmInstall` task.

### Changed
- [LPS-75965]: Update the [Liferay Gradle Plugins] dependency to version 3.5.46.

## 5.1.54 - 2017-11-21

### Changed
- [LPS-75971]: Update the [Liferay Gradle Plugins] dependency to version 3.5.47.

## 5.1.55 - 2017-11-24

### Changed
- [LPS-76110]: Update the [Liferay Gradle Plugins] dependency to version 3.5.48.

## 5.2.0 - 2017-11-27

### Added
- [LPS-76145]: Add the new `com.liferay.osgi.portal.compat.defaults.plugin`
Gradle plugin to properly configure the portal compatibility OSGi modules.

### Changed
- [LPS-75778]: Update the [Liferay Gradle Plugins] dependency to version 3.5.49.

## 5.2.1 - 2017-11-28

### Changed
- [LPS-72912]: Update the [Liferay Gradle Plugins] dependency to version 3.5.50.

## 5.2.2 - 2017-11-28

### Added
- [LPS-76181]: Add the property `exactVersion` to the `updateFileVersions` task.
If set to `true`, the task updates all versions to the current one, even if the
*major* part has not been increased.
- [LPS-76182]: Ignore a whole subtree if a `.lfrbuild-releng-ignore` marker file
is found in a parent directory.

## 5.2.3 - 2017-11-28

### Changed
- [LPS-75859]: Update the [Liferay Gradle Plugins] dependency to version 3.5.51.

## 5.2.4 - 2017-11-28

### Changed
- [LPS-75901]: Update the [Liferay Gradle Plugins] dependency to version 3.5.52.

## 5.2.5 - 2017-11-29

### Changed
- [LPS-75901]: Update the [Liferay Gradle Plugins] dependency to version 3.5.53.

## 5.2.6 - 2017-11-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.54.

## 5.2.7 - 2017-11-30

### Changed
- [LPS-76202]: Update the [Liferay Gradle Plugins] dependency to version 3.5.55.

## 5.2.8 - 2017-12-01

### Changed
- [LPS-76224]: Update the [Liferay Gradle Plugins] dependency to version 3.5.56.
- [LPS-76224]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.1.9.

## 5.2.9 - 2017-12-01

### Fixed
- [LPS-69999]: Prevent the `updateFileVersions` task from changing files in
read-only sub-repositories.

## 5.2.10 - 2017-12-04

### Changed
- [LPS-76221]: Set the `translate` property of all `BuildLangTask` instances to
`false` by default.
- [LPS-76221]: Update the [Liferay Gradle Plugins] dependency to version 3.5.57.

## 5.2.11 - 2017-12-05

### Changed
- [LPS-76221]: Update the [Liferay Gradle Plugins] dependency to version 3.5.58.

### Fixed
- [LPS-76202]: Fix usages of the `compileJSP.destinationDir` property.

## 5.2.12 - 2017-12-05

### Changed
- [LPS-76256]: Update the [Liferay Gradle Plugins] dependency to version 3.5.59.

## 5.2.13 - 2017-12-05

### Changed
- [LPS-76226]: Update the [Liferay Gradle Plugins] dependency to version 3.5.60.

## 5.2.14 - 2017-12-07

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.61.

## 5.2.15 - 2017-12-10

### Changed
- [LPS-76326]: Update the [Liferay Gradle Plugins] dependency to version 3.5.62.

## 5.2.16 - 2017-12-12

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.63.

## 5.2.17 - 2017-12-12

### Changed
- [LPS-76018]: Update the [Liferay Gradle Plugins] dependency to version 3.5.64.

## 5.2.18 - 2017-12-12

### Changed
- [LPS-76018]: Update the [Liferay Gradle Plugins] dependency to version 3.5.65.

## 5.2.19 - 2017-12-12

### Changed
- [LPS-76018]: Update the [Liferay Gradle Plugins] dependency to version 3.5.66.

## 5.2.20 - 2017-12-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.67.

## 5.2.21 - 2017-12-14

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.68.

## 5.2.22 - 2017-12-15

### Fixed
- [LPS-61099]: Fix configuration exceptions for OSGi modules in
sub-repositories.

## 5.2.23 - 2017-12-19

### Changed
- [LPS-76601]: Update the [Liferay Gradle Plugins] dependency to version 3.5.69.

## 5.2.24 - 2017-12-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.70.

## 5.2.25 - 2017-12-20

### Changed
- [LPS-76221]: Update the [Liferay Gradle Plugins] dependency to version 3.5.71.

### Removed
- [LPS-76221]: Remove the `translate` property's default configuration for all
`BuildLangTask` instances.

## 5.2.26 - 2017-12-20

### Changed
- [LPS-76221]: Update the [Liferay Gradle Plugins] dependency to version 3.5.72.

## 5.2.27 - 2017-12-20

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins] dependency to version 3.5.73.

## 5.2.28 - 2017-12-21

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins] dependency to version 3.5.74.

## 5.2.29 - 2017-12-24

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.75.

## 5.2.30 - 2017-12-26

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.76.

## 5.2.31 - 2017-12-29

### Added
- [LRDOCS-4111]: Apply and preconfigure [Liferay Gradle Plugins JSDoc] for OSGi
projects.
- [LRDOCS-4111]: Publish the JavaScript API documentation JAR of an OSGi project
with the `install` and `uploadArchives` tasks.

### Changed
- [LPS-76747]: Update the [Liferay Gradle Plugins] dependency to version 3.5.77.

## 5.2.32 - 2018-01-02

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.78.

## 5.2.33 - 2018-01-02

### Added
- [LPS-76644]: Automatically configure the [Plugin Publishing Plugin](https://plugins.gradle.org/docs/publish-plugin)
when applied.

### Changed
- [LPS-74904]: Update the [Liferay Gradle Plugins] dependency to version 3.5.79.
- [LPS-74904]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
1.0.1.

## 5.2.34 - 2018-01-03

### Fixed
- [LPS-76623]: Fix Maven coordinates of the [Gradle License Report] dependency.

## 5.2.35 - 2018-01-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.80.

## 5.2.36 - 2018-01-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.81.

## 5.2.37 - 2018-01-08

### Changed
- [LPS-76840]: Update the [Liferay Gradle Plugins] dependency to version 3.5.82.

## 5.2.38 - 2018-01-08

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins] dependency to version 3.5.83.

## 5.2.39 - 2018-01-09

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.84.

## 5.2.40 - 2018-01-10

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins] dependency to version 3.5.85.

## 5.2.41 - 2018-01-11

### Changed
- [LPS-76954]: Update the [Liferay Gradle Plugins] dependency to version 3.5.86.

## 5.2.42 - 2018-01-11

### Changed
- [LPS-76957]: Update the [Liferay Gradle Plugins] dependency to version 3.5.87.

## 5.2.43 - 2018-01-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.88.

## 5.2.44 - 2018-01-14

### Changed
- [LPS-77111]: Update the [Liferay Gradle Plugins] dependency to version 3.5.89.

## 5.2.45 - 2018-01-17

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins] dependency to version 3.5.90.

## 5.2.46 - 2018-01-17

### Changed
- [LPS-76644]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
1.0.2.
- [LPS-77250]: Update the [Liferay Gradle Plugins] dependency to version 3.5.91.

## 5.2.47 - 2018-01-22

### Changed
- [LPS-77305]: Update the [Liferay Gradle Plugins] dependency to version 3.5.92.

## 5.2.48 - 2018-01-23

### Changed
- [LPS-77402]: Update the [Liferay Gradle Plugins] dependency to version 3.5.93.

## 5.2.49 - 2018-01-23

### Changed
- [LPS-77400]: Update the [Liferay Gradle Plugins] dependency to version 3.5.94.

## 5.2.50 - 2018-01-23

### Changed
- [LPS-77186]: Update the [Liferay Gradle Plugins] dependency to version 3.5.95.

## 5.2.51 - 2018-01-25

### Changed
- [LPS-77143]: Update the [Liferay Gradle Plugins] dependency to version 3.5.95.

## 5.2.52 - 2018-01-25

### Changed
- [LPS-77423]: Update the [Liferay Gradle Plugins] dependency to version 3.5.97.

### Removed
- [LPS-77423]: Remove the `--no-package-lock` default argument in the
`npmInstall` task.
- [LPS-77423]: Remove the `fsevents` dependency exclusion from the generated
`npm-shrinkwrap.json` files.

## 5.2.53 - 2018-01-26

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.98.

## 5.2.54 - 2018-01-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.5.99.
- [LPS-77441]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.2.0.

### Removed
- [LPS-77441]: Remove code that set the default values of the properties
`reportDiff` and `reportOnlyDiffPackages` for all `BaselineTask` instances,
since it has been moved into [Liferay Gradle Plugins Baseline].

## 5.2.55 - 2018-01-30

### Changed
- [LPS-77630]: Update the [Liferay Gradle Plugins] dependency to version
3.5.100.

## 5.2.56 - 2018-01-31

### Changed
- [LPS-77630]: Update the [Liferay Gradle Plugins] dependency to version
3.5.101.

## 5.2.57 - 2018-02-01

### Changed
- [LPS-77350]: Update the [Liferay Gradle Plugins] dependency to version 3.6.0.
- [LPS-77350]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.2.1.

## 5.2.58 - 2018-02-05

### Changed
- [LPS-77795]: Update the [Liferay Gradle Plugins] dependency to version 3.6.1.

## 5.2.59 - 2018-02-06

### Added
- [LPS-77359]: Add the plugin `com.liferay.poshi.runner.resources.defaults` to
properly apply and configure the `com.liferay.poshi.runner` plugin.
- [LPS-77797]: Make the `deploy` task depend on `buildWSDD` if the project
directory contains the marker file `.lfrbuild-deploy-wsdd`.

### Changed
- [LPS-77836]: Update the [Liferay Gradle Plugins] dependency to version 3.6.2.

## 5.2.60 - 2018-02-08

### Changed
- [LPS-77886]: Update the [Liferay Gradle Plugins] dependency to version 3.6.3.

## 5.2.61 - 2018-02-08

### Changed
- [LPS-69802]: Update the [Liferay Gradle Plugins] dependency to version 3.6.4.
- [LPS-69802]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
1.0.3.

## 5.3.0 - 2018-02-08

### Added
- [LPS-77840]: The `com.liferay.app.defaults.plugin` plugin now automatically
applies the `com.liferay.app.jsdoc` plugin.

### Changed
- [LPS-77840]: Update the [Liferay Gradle Plugins] dependency to version 3.7.0.
- [LPS-77840]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.0.

## 5.3.1 - 2018-02-11

### Changed
- [LPS-77916]: Update the [Liferay Gradle Plugins] dependency to version 3.7.1.

## 5.3.2 - 2018-02-12

### Changed
- [LPS-77968]: Update the [Liferay Gradle Plugins] dependency to version 3.7.2.

## 5.3.3 - 2018-02-13

### Added
- [LPS-77996]: Set the `npmInstall` task's `nodeModulesDigestFile` property to
`"${project.projectDir}/node_modules/.digest"` by default.

### Changed
- [LPS-77996]: Update the [Liferay Gradle Plugins] dependency to version 3.7.3.
- [LPS-77996]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.1.

## 5.3.4 - 2018-02-14

### Changed
- [LPS-78033]: Update the [Liferay Gradle Plugins] dependency to version 3.7.4.

## 5.3.5 - 2018-02-15

### Changed
- [LPS-78038]: Update the [Liferay Gradle Plugins] dependency to version 3.7.5.

## 5.3.6 - 2018-02-18

### Added
- [LPS-78096]: Set the `poshiRunnerResources.rootDirName` property to
`"testFunctional"` by default.

### Changed
- [LPS-78096]: Update the [Liferay Gradle Plugins] dependency to version 3.7.6.
- [LPS-78096]: Update the [Liferay Gradle Plugins Poshi Runner] dependency to
version 2.1.0.

## 5.3.7 - 2018-02-20

### Changed
- [LPS-78071]: Update the [Liferay Gradle Plugins] dependency to version 3.7.7.

## 5.3.8 - 2018-02-21

### Changed
- [LPS-78033]: Update the [Liferay Gradle Plugins] dependency to version 3.7.8.

## 5.3.9 - 2018-02-22

### Changed
- [LPS-78150]: Update the [Liferay Gradle Plugins] dependency to version 3.7.9.

## 5.3.10 - 2018-02-25

### Changed
- [LPS-77532]: Update the [Liferay Gradle Plugins] dependency to version 3.8.0.

## 5.3.11 - 2018-02-26

### Changed
- [LPS-78231]: Update the [Liferay Gradle Plugins] dependency to version 3.8.1.

## 5.3.12 - 2018-02-26

### Changed
- [LPS-78261]: Update the [Liferay Gradle Plugins] dependency to version 3.8.2.

## 5.3.13 - 2018-02-28

### Changed
- [LPS-78266]: Update the [Liferay Gradle Plugins] dependency to version 3.8.3.
- [LPS-78266]: Update the [Liferay Gradle Plugins Poshi Runner] dependency to
version 2.2.0.

## 5.3.14 - 2018-03-01

### Changed
- [LPS-76926]: Update the [Liferay Gradle Plugins] dependency to version 3.8.4.

## 5.3.15 - 2018-03-02

### Changed
- [LPS-78436]: Update the [Liferay Gradle Plugins] dependency to version 3.8.5.

## 5.3.16 - 2018-03-05

### Changed
- [LPS-76997]: Update the [Liferay Gradle Plugins] dependency to version 3.8.6.

## 5.3.17 - 2018-03-05

### Changed
- [LPS-78459]: Update the [Liferay Gradle Plugins] dependency to version 3.8.7.

## 5.3.18 - 2018-03-07

### Changed
- [LPS-78050]: Update the [Liferay Gradle Plugins] dependency to version 3.8.8.
- [LPS-78537]: Update the [Liferay Gradle Plugins Poshi Runner] dependency to
version 2.2.1.

## 5.3.19 - 2018-03-07

### Changed
- [LPS-77425]: Update the [Liferay Gradle Plugins] dependency to version 3.8.9.

## 5.3.20 - 2018-03-07

### Changed
- [LPS-78571]: Update the [Liferay Gradle Plugins] dependency to version 3.8.10.

## 5.3.21 - 2018-03-07

### Changed
- [LPS-78459]: Update the [Liferay Gradle Plugins] dependency to version 3.8.11.

## 5.3.22 - 2018-03-07

### Changed
- [LPS-78493]: Update the [Liferay Gradle Plugins] dependency to version 3.8.12.

## 5.3.23 - 2018-03-08

### Changed
- [LPS-78558]: Update the [Find Security Bugs] dependency to version
1.7.1.LIFERAY-PATCHED-1.
- [LPS-78571]: Update the [Liferay Gradle Plugins] dependency to version 3.8.13.

## 5.3.24 - 2018-03-09

### Changed
- [LPS-77524]: Allow a `testIntegrationCompile` dependency from a project with
path `:test:arquillian-extension-junit-bridge` to not have a `.lfrbuild-portal`
marker file.

## 5.3.25 - 2018-03-10

### Changed
- [LPS-78308]: Update the [Liferay Gradle Plugins] dependency to version 3.8.14.

## 5.3.26 - 2018-03-12

### Changed
- [LPS-78669]: Update the [Liferay Gradle Plugins] dependency to version 3.8.15.

## 5.3.27 - 2018-03-12

### Changed
- [LPS-78269]: Update the [Liferay Gradle Plugins] dependency to version 3.8.16.

## 5.3.28 - 2018-03-13

### Changed
- [LPS-78767]: Update the [Liferay Gradle Plugins] dependency to version 3.8.17.

## 5.3.29 - 2018-03-13

### Changed
- [LPS-78767]: Update the [Liferay Gradle Plugins] dependency to version 3.8.18.

## 5.3.30 - 2018-03-15

### Changed
- [LPS-78741]: Configure the `npmInstall` task to run `npm ci` instead of `npm
install` when not on Jenkins.
- [LPS-78741]: Update the [Liferay Gradle Plugins] dependency to version 3.8.19.
- [LPS-78741]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.2.

## 5.3.31 - 2018-03-16

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins] dependency to version 3.8.20.

## 5.3.32 - 2018-03-16

### Changed
- [LPS-78845]: Update the [Liferay Gradle Plugins] dependency to version 3.8.21.

## 5.3.33 - 2018-03-16

### Changed
- [LPS-77425]: Update the [Liferay Gradle Plugins] dependency to version 3.9.0.

## 5.3.34 - 2018-03-17

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins] dependency to version 3.9.1.

## 5.3.35 - 2018-03-18

### Changed
- [LPS-78911]: Update the [Liferay Gradle Plugins] dependency to version 3.9.2.

## 5.3.36 - 2018-03-19

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins] dependency to version 3.9.3.

## 5.3.37 - 2018-03-19

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins] dependency to version 3.9.4.

## 5.3.38 - 2018-03-20

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins] dependency to version 3.9.5.

## 5.3.39 - 2018-03-20

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.9.6.

## 5.3.40 - 2018-03-20

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.9.7.

## 5.3.41 - 2018-03-21

### Changed
- [LPS-78750]: Update the [Liferay Gradle Plugins] dependency to version 3.9.8.

## 5.3.42 - 2018-03-21

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins] dependency to version 3.9.9.

## 5.3.43 - 2018-03-22

### Changed
- [LPS-78741]: Configure the `npmInstall` task to run `npm install` instead of
`npm ci`.
- [LPS-78741]: Update the [Liferay Gradle Plugins] dependency to version 3.9.10.
- [LPS-78741]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.3.

## 5.3.44 - 2018-03-23

### Changed
- [LPS-78911]: Update the [Liferay Gradle Plugins] dependency to version 3.9.11.

## 5.3.45 - 2018-03-26

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.9.12.

## 5.3.46 - 2018-03-26

### Changed
- [LPS-79191]: Update the [Liferay Gradle Plugins] dependency to version 3.9.13.

## 5.3.47 - 2018-03-27

### Changed
- [LPS-79192]: Update the [Liferay Gradle Plugins] dependency to version 3.9.14.

## 5.3.48 - 2018-03-27

### Added
- [LPS-78477]: When on Jenkins, fail the `testIntegration` task if any dependent
projects defined in the `testIntegrationCompile` configuration do not have a
`.lfrbuild-portal` or a `lfrbuild-ci` marker file.

### Changed
- [LPS-78477]: Update the [Liferay Gradle Plugins] dependency to version 3.9.15.

## 5.3.49 - 2018-03-27

### Changed
- [LPS-78854]: Update the [Liferay Gradle Plugins] dependency to version 3.9.16.

## 5.3.50 - 2018-03-27

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.9.17.

## 5.3.51 - 2018-03-27

### Changed
- [LPS-79131]: Update the [Liferay Gradle Plugins] dependency to version 3.9.18.

## 5.3.52 - 2018-03-27

### Changed
- [LPS-79226]: Update the [Liferay Gradle Plugins] dependency to version 3.9.19.

## 5.3.53 - 2018-03-27

### Changed
- [LPS-78901]: Update the [Liferay Gradle Plugins] dependency to version 3.9.20.

## 5.3.54 - 2018-03-29

### Changed
- [LPS-79286]: Update the [Liferay Gradle Plugins] dependency to version 3.9.21.

## 5.3.55 - 2018-03-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.9.22.

## 5.3.56 - 2018-03-30

### Changed
- [LPS-78741]: Configure the `npmInstall` task to run `npm ci` instead of
`npm install` when on the master branch and not on Jenkins.
- [LPS-78741]: Set the Node.js version to 6.6.0 and disable the global Node.js
execution when on the `7.0.x` branch.
- [LPS-78741]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.4.
- [LPS-79282]: Update the [Liferay Gradle Plugins] dependency to version 3.10.0.

## 5.3.57 - 2018-03-31

### Changed
- [LPS-69247]: Update the [Liferay Gradle Plugins] dependency to version 3.10.1.

## 5.3.58 - 2018-04-02

### Changed
- [LPS-79192]: Update the [Liferay Gradle Plugins] dependency to version 3.10.2.

## 5.3.59 - 2018-04-02

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version 3.10.3.

## 5.3.60 - 2018-04-02

### Changed
- [LPS-78571]: Update the [Liferay Gradle Plugins] dependency to version 3.10.4.

## 5.3.61 - 2018-04-03

### Changed
- [LPS-74110]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.2.2.
- [LPS-75010]: Update the [Liferay Gradle Plugins] dependency to version 3.10.5.

## 5.3.62 - 2018-04-04

### Changed
- [LPS-79360]: Update the [Liferay Gradle Plugins] dependency to version 3.10.6.

## 5.3.63 - 2018-04-04

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.5.
- [LPS-79282]: Update the [Liferay Gradle Plugins] dependency to version 3.10.7.

## 5.3.64 - 2018-04-04

### Changed
- [LPS-79365]: Update the [Liferay Gradle Plugins] dependency to version 3.10.8.

## 5.3.65 - 2018-04-05

### Changed
- [LPS-75049]: Add support for the `compileOnly` configuration.
- [LPS-75049]: Add `compileOnly` dependencies to the test integration compile
classpath.
- [LPS-79365]: Update the [Liferay Gradle Plugins] dependency to version 3.10.9.

## 5.3.66 - 2018-04-05

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.10.10.

## 5.3.67 - 2018-04-06

### Changed
- [LPS-78971]: Update the [Liferay Gradle Plugins] dependency to version
3.10.11.

## 5.3.68 - 2018-04-06

### Changed
- [LPS-75049]: Add `compileOnly` dependencies to the test integration runtime
classpath.

## 5.3.69 - 2018-04-09

### Changed
- [LPS-79450]: Update the [Liferay Gradle Plugins] dependency to version
3.10.12.

## 5.3.70 - 2018-04-09

### Changed
- [LPS-74171]: Update the [Liferay Gradle Plugins] dependency to version
3.10.13.

## 5.3.71 - 2018-04-10

### Changed
- [LPS-78308]: Update the [Liferay Gradle Plugins] dependency to version
3.10.14.

## 5.3.72 - 2018-04-10

### Changed
- [LPS-78911]: Update the [Liferay Gradle Plugins] dependency to version
3.10.15.

## 5.3.73 - 2018-04-11

### Changed
- [LPS-75010]: Update the [Liferay Gradle Plugins] dependency to version
3.10.16.

## 5.3.74 - 2018-04-12

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins] dependency to version
3.10.17.

## 5.3.75 - 2018-04-12

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins] dependency to version
3.10.18.

## 5.3.76 - 2018-04-12

### Changed
- [LPS-75530]: Update the [Liferay Gradle Plugins] dependency to version 3.11.0.

## 5.3.77 - 2018-04-13

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins] dependency to version 3.11.1.

## 5.3.78 - 2018-04-13

### Changed
- [LPS-77639]: Update the [Liferay Gradle Plugins] dependency to version 3.11.2.

## 5.3.79 - 2018-04-13

### Changed
- [LPS-79623]: Update the [Liferay Gradle Plugins] dependency to version 3.11.3.

## 5.3.80 - 2018-04-16

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins] dependency to version 3.11.4.

## 5.3.81 - 2018-04-16

### Changed
- [LPS-79665]: Update the [Liferay Gradle Plugins] dependency to version 3.11.5.

## 5.3.82 - 2018-04-17

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version 3.11.6.

## 5.3.83 - 2018-04-18

### Changed
- [LPS-79755]: Update the [Liferay Gradle Plugins] dependency to version 3.11.7.

## 5.3.84 - 2018-04-18

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version 3.11.8.

## 5.3.85 - 2018-04-19

### Changed
- [LPS-79386]: Update the [Liferay Gradle Plugins] dependency to version 3.11.9.

## 5.3.86 - 2018-04-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.11.10.

## 5.3.87 - 2018-04-20

### Changed
- [LPS-79919]: Update the [Liferay Gradle Plugins] dependency to version
3.11.11.

## 5.3.88 - 2018-04-20

### Changed
- [LPS-79919]: Update the [Liferay Gradle Plugins] dependency to version
3.11.12.

## 5.3.89 - 2018-04-22

### Changed
- [LPS-75049]: Update the [Liferay Gradle Plugins] dependency to version
3.11.13.

## 5.3.90 - 2018-04-23

### Changed
- [LPS-79953]: Update the [Liferay Gradle Plugins] dependency to version
3.11.14.

## 5.3.91 - 2018-04-23

### Changed
- [LPS-80055]: Update the [Liferay Gradle Plugins] dependency to version
3.11.15.

## 5.3.92 - 2018-04-23

### Changed
- [LPS-79799]: Update the [Liferay Gradle Plugins] dependency to version
3.11.16.

## 5.3.93 - 2018-04-24

### Changed
- [LPS-80091]: Update the [Liferay Gradle Plugins] dependency to version
3.11.17.

## 5.3.94 - 2018-04-25

### Changed
- [LPS-79963]: Update the [Liferay Gradle Plugins] dependency to version
3.11.18.

## 5.3.95 - 2018-04-25

### Changed
- [LPS-79388]: Update the [Liferay Gradle Plugins] dependency to version
3.11.19.

## 5.3.96 - 2018-04-26

### Changed
- [LPS-80184]: Update the [Liferay Gradle Plugins] dependency to version
3.11.20.

## 5.3.97 - 2018-04-26

### Changed
- [LPS-80125]: Update the [Liferay Gradle Plugins] dependency to version
3.11.21.

## 5.3.98 - 2018-04-26

### Changed
- [LPS-80123]: Update the [Liferay Gradle Plugins] dependency to version
3.11.22.

## 5.3.99 - 2018-04-29

### Changed
- [LPS-80123]: Update the [Liferay Gradle Plugins] dependency to version
3.11.23.

## 5.3.100 - 2018-04-30

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.11.24.

## 5.3.101 - 2018-04-30

### Changed
- [LPS-80122]: Update the [Liferay Gradle Plugins] dependency to version
3.11.25.

## 5.3.102 - 2018-05-01

### Added
- [LPS-80332]: Update the `-include` instruction for projects that have a
`suite.bnd` file.

## 5.3.103 - 2018-05-01

### Changed
- [LPS-80184]: Update the [Liferay Gradle Plugins] dependency to version
3.11.26.

## 5.3.104 - 2018-05-02

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.11.27.

## 5.3.105 - 2018-05-02

### Changed
- [LPS-80332]: Update the [Liferay Gradle Plugins] dependency to version
3.11.28.

## 5.3.106 - 2018-05-02

### Changed
- [LPS-80394]: Update the [Liferay Gradle Plugins] dependency to version
3.11.29.
- [LPS-80394]: Update the [Liferay Gradle Plugins Poshi Runner] dependency to
version 2.2.3.

### Fixed
- [LPS-65633]: If the module is `private`, search for the public `app.bnd`.

## 5.3.107 - 2018-05-03

### Changed
- [LPS-80386]: Update the [Liferay Gradle Plugins] dependency to version
3.11.30.

## 5.3.108 - 2018-05-03

### Changed
- [LPS-80466]: Update the [Liferay Gradle Plugins] dependency to version
3.11.31.

## 5.3.109 - 2018-05-03

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.11.32.

## 5.3.110 - 2018-05-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.11.33.

## 5.3.111 - 2018-05-06

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins] dependency to version
3.11.34.

## 5.3.112 - 2018-05-06

### Changed
- [LPS-80520]: Update the [Liferay Gradle Plugins] dependency to version
3.11.35.

## 5.3.113 - 2018-05-07

### Changed
- [LPS-75530]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.6.
- [LPS-75530]: Update the [Liferay Gradle Plugins] dependency to version
3.11.36.

## 5.3.114 - 2018-05-07

### Changed
- [LPS-80513]: Update the [Liferay Gradle Plugins] dependency to version
3.11.37.

## 5.3.115 - 2018-05-08

### Changed
- [LPS-80544]: Update the [Liferay Gradle Plugins] dependency to version
3.11.38.

## 5.3.116 - 2018-05-10

### Changed
- [LPS-80332]: Update the [Liferay Gradle Plugins] dependency to version
3.11.39.

## 5.3.117 - 2018-05-10

### Changed
- [LPS-79453]: Update the [Liferay Gradle Plugins] dependency to version 3.12.0.

## 5.3.118 - 2018-05-13

### Changed
- [LPS-80840]: Update the [Liferay Gradle Plugins] dependency to version 3.12.1.

## 5.3.119 - 2018-05-14

### Changed
- [LPS-79799]: Update the [Liferay Gradle Plugins] dependency to version 3.12.2.

## 5.3.120 - 2018-05-14

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version 3.12.3.

## 5.3.121 - 2018-05-15

### Changed
- [LPS-80920]: Update the [Liferay Gradle Plugins] dependency to version 3.12.4.

## 5.4.0 - 2018-05-15

### Added
- [LPS-80944]: Add the plugin `com.liferay.spring.boot.defaults` to configure
Spring Boot projects according to Liferay defaults.

### Changed
- [LPS-80944]: Update the [Liferay Gradle Plugins] dependency to version 3.12.5.

## 5.4.1 - 2018-05-15

### Changed
- [LPS-79262]: Update the [Liferay Gradle Plugins] dependency to version 3.12.6.

## 5.4.2 - 2018-05-15

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version 3.12.7.

## 5.4.3 - 2018-05-16

### Changed
- [LPS-80950]: Update the [Liferay Gradle Plugins Poshi Runner] dependency to
version 2.2.4.

## 5.4.4 - 2018-05-16

### Added
- [LPS-80944]: Configure the `com.liferay.spring.boot.defaults` plugin to
automatically add local Maven and [Liferay CDN] repositories to the project.

## 5.4.5 - 2018-05-16

### Changed
- [LPS-80950]: Update the [Liferay Gradle Plugins Change Log Builder] dependency
to version 1.0.4.

## 5.4.6 - 2018-05-16

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version 3.12.8.

## 5.4.7 - 2018-05-17

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version 3.12.9.

## 5.4.8 - 2018-05-17

### Changed
- [LPS-81106]: Update the [Liferay Gradle Plugins] dependency to version
3.12.10.

## 5.4.9 - 2018-05-17

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins] dependency to version
3.12.11.

## 5.4.10 - 2018-05-19

### Changed
- [LPS-80920]: Update the [Liferay Gradle Plugins] dependency to version
3.12.12.

## 5.4.11 - 2018-05-21

### Changed
- [LPS-79963]: Update the [Liferay Gradle Plugins] dependency to version
3.12.13.

## 5.4.12 - 2018-05-21

### Changed
- [LPS-81106]: Update the [Liferay Gradle Plugins] dependency to version
3.12.14.

## 5.4.13 - 2018-05-22

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.15.

## 5.4.14 - 2018-05-23

### Changed
- [LPS-80723]: Update the [Liferay Gradle Plugins] dependency to version
3.12.16.

## 5.4.15 - 2018-05-23

### Changed
- [LPS-79709]: Update the [Liferay Gradle Plugins] dependency to version
3.12.17.

## 5.4.16 - 2018-05-23

### Changed
- [LPS-81404]: Update the [Liferay Gradle Plugins] dependency to version
3.12.18.

## 5.4.17 - 2018-05-24

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins] dependency to version
3.12.19.

## 5.4.18 - 2018-05-28

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.20.

## 5.4.19 - 2018-05-29

### Changed
- [LPS-81635]: Update the [Liferay Gradle Plugins] dependency to version
3.12.21.

## 5.4.20 - 2018-05-29

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.22.

## 5.4.21 - 2018-05-30

### Changed
- [LPS-81555]: Update the [Liferay Gradle Plugins] dependency to version
3.12.23.

## 5.4.22 - 2018-05-31

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.24.

## 5.4.23 - 2018-05-31

### Changed
- [LPS-81795]: Update the [Liferay Gradle Plugins] dependency to version
3.12.25.

## 5.4.24 - 2018-06-01

### Added
- [LPS-80944]: Show full JUnit log when running tests on Spring Boot and any
Java project.

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.26.

## 5.4.25 - 2018-06-04

### Changed
- [LPS-81795]: Update the [Liferay Gradle Plugins] dependency to version
3.12.27.
- [LPS-81895]: Update the [Liferay Gradle Plugins Lang Merger] dependency to
version 1.1.0.

## 5.4.26 - 2018-06-04

### Fixed
- [LPS-67694]: Fix error when `-PsnapshotIfStale` is set and the latest snapshot
of a module includes transitive dependencies.

## 5.4.27 - 2018-06-04

### Changed
- [LPS-81336]: Update the [Liferay Gradle Plugins] dependency to version
3.12.28.

## 5.4.28 - 2018-06-05

### Changed
- [LPS-81336]: Update the [Liferay Gradle Plugins] dependency to version
3.12.29.

## 5.4.29 - 2018-06-05

### Changed
- [LPS-82001]: Update the [Liferay Gradle Plugins] dependency to version
3.12.30.

## 5.4.30 - 2018-06-06

### Changed
- [LPS-82001]: Update the [Liferay Gradle Plugins] dependency to version
3.12.31.

## 5.4.31 - 2018-06-07

### Changed
- [LPS-81900]: Update the [Liferay Gradle Plugins] dependency to version
3.12.32.

### Fixed
- [LPS-80332]: Fix `suite.bnd` inclusion when running in sub-repositories.

## 5.4.32 - 2018-06-08

### Changed
- [LPS-75530]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.7.
- [LPS-82130]: Update the [Liferay Gradle Plugins] dependency to version
3.12.33.
- [LPS-82178]: Update the [Liferay Gradle Plugins Patcher] dependency to version
1.0.13.

## 5.4.33 - 2018-06-11

### Changed
- [LPS-82209]: Update the [Liferay Gradle Plugins] dependency to version
3.12.34.

## 5.4.34 - 2018-06-11

### Changed
- [LPS-81638]: Update the [Liferay Gradle Plugins] dependency to version
3.12.35.

## 5.4.35 - 2018-06-11

### Changed
- [LPS-82121]: Update the [Liferay Gradle Plugins] dependency to version
3.12.36.

## 5.4.36 - 2018-06-11

### Changed
- [LPS-77875]: Update the constant `DEFAULT_REPOSITORY_URL` in the
`GradlePluginsDefaultsUtil` class to
`https://repository-cdn.liferay.com/nexus/content/groups/public`.
- [LPS-77875]: Update the [Liferay Gradle Plugins] dependency to version
3.12.37.

## 5.4.37 - 2018-06-11

### Changed
- [LPS-82261]: Update the [Liferay Gradle Plugins] dependency to version
3.12.38.

## 5.4.38 - 2018-06-12

### Changed
- [LPS-82261]: Update the [Liferay Gradle Plugins] dependency to version
3.12.39.

## 5.4.39 - 2018-06-13

### Changed
- [LPS-82343]: Update the [Liferay Gradle Plugins] dependency to version
3.12.40.

## 5.4.40 - 2018-06-13

### Changed
- [LPS-77425]: Update the [Liferay Gradle Plugins] dependency to version
3.12.41.

## 5.4.41 - 2018-06-13

### Changed
- [LPS-77875]: Update the constant `DEFAULT_REPOSITORY_URL` in the
`GradlePluginsDefaultsUtil` class to
`https://repository.liferay.com/nexus/content/groups/public`.
- [LPS-77875]: Update the [Liferay Gradle Plugins] dependency to version
3.12.42.

## 5.4.42 - 2018-06-13

### Changed
- [LPS-77425]: Update the constant `DEFAULT_REPOSITORY_URL` in the
`GradlePluginsDefaultsUtil` class to
`https://repository-cdn.liferay.com/nexus/content/groups/public`.
- [LPS-77425]: Update the [Liferay Gradle Plugins] dependency to version
3.12.43.

## 5.4.43 - 2018-06-13

### Changed
- [LPS-77425]: Update the [Liferay Gradle Plugins] dependency to version
3.12.44.

## 5.4.44 - 2018-06-14

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.45.

## 5.4.45 - 2018-06-15

### Changed
- [LPS-82534]: Update the [Liferay Gradle Plugins] dependency to version
3.12.46.

## 5.4.46 - 2018-06-15

### Changed
- [LPS-77143]: Update the [Liferay Gradle Plugins] dependency to version
3.12.47.

## 5.4.47 - 2018-06-18

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.48.

## 5.4.48 - 2018-06-19

### Changed
- [LPS-82420]: Update the [Liferay Gradle Plugins] dependency to version
3.12.49.

## 5.4.49 - 2018-06-20

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.50.

## 5.4.50 - 2018-06-20

### Changed
- [LPS-82433]: Update the [Liferay Gradle Plugins] dependency to version
3.12.51.

## 5.4.51 - 2018-06-20

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.52.

## 5.4.52 - 2018-06-21

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.53.

## 5.4.53 - 2018-06-22

### Added
- [LPS-82568]: Set the `npmRunBuild` task's `environment` property to
`LIFERAY_NPM_BUNDLER_NO_TRACKING=1` by default to disable a prompt from
[`liferay-npm-bundler`](https://github.com/liferay/liferay-npm-build-tools)
about tracking data.

### Changed
- [LPS-82568]: Update the [Liferay Gradle Plugins JSDoc] dependency to version
2.0.8.
- [LPS-82815]: Update the [Liferay Gradle Plugins] dependency to version
3.12.54.

## 5.4.54 - 2018-06-25

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins] dependency to version
3.12.55.

## 5.4.55 - 2018-06-25

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins] dependency to version
3.12.56.
- [LPS-82857]: Update the [Liferay Gradle Plugins Change Log Builder] dependency
to version 1.1.0.

## 5.4.56 - 2018-06-25

### Changed
- [LPS-74608]: Update the [Liferay Gradle Plugins] dependency to version
3.12.57.

## 5.4.57 - 2018-06-26

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins] dependency to version
3.12.58.

## 5.4.58 - 2018-06-26

### Added
- [LPS-82960]: Set the project property `jira.project.keys` to add new ticket ID
prefixes to the `buildChangeLog` task.

## 5.4.59 - 2018-06-27

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins] dependency to version
3.12.59.

## 5.4.60 - 2018-06-28

### Changed
- [LPS-82343]: Update the [Liferay Gradle Plugins] dependency to version
3.12.60.

## 5.4.61 - 2018-06-28

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.61.

## 5.4.62 - 2018-06-28

### Changed
- [LPS-83067]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.3.0.

## 5.4.63 - 2018-06-29

### Added
- [LPS-83104]: Set `liferay.releng.` default property values.

### Changed
- [LPS-83067]: Update the [Liferay Gradle Plugins] dependency to version
3.12.62.

### Fixed
- [LPS-83104]: Fix publishing from sub-repositories.

## 5.4.64 - 2018-07-02

### Added
- [LPS-82976]: Set the system property `build.profile` to `portal-all` so only
projects containing one of the following marker files are included:
	- `.lfrbuild-portal`
	- `.lfrbuild-portal-all`
	- `.lfrbuild-portal-all-private` when on private branches, or
	`.lfrbuild-portal-all-public` when on public branches
	- `.lfrbuild-portal-private` when on private branches, or
	`.lfrbuild-portal-public` when on public branches

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins] dependency to version
3.12.63.

## 5.4.65 - 2018-07-02

### Changed
- [LPS-83067]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.3.1.
- [LPS-83067]: Update the [Liferay Gradle Plugins] dependency to version
3.12.64.

## 5.4.66 - 2018-07-03

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins] dependency to version
3.12.65.

## 5.4.67 - 2018-07-03

### Changed
- [LPS-77359]: Update the [Liferay Gradle Plugins Poshi Runner] dependency to
version 2.2.5.

## 5.4.68 - 2018-07-04

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins] dependency to version
3.12.66.

## 5.4.69 - 2018-07-05

### Changed
- [LPS-83220]: Update the [Liferay Gradle Plugins] dependency to version
3.12.67.

## 5.4.70 - 2018-07-09

### Fixed
- [LPS-83300]: Ensure the system property `junit.code.coverage` is being read.

## 5.4.71 - 2018-07-10

### Changed
- [LPS-82960]: Update the [Liferay Gradle Plugins] dependency to version
3.12.68.
- [LPS-82960]: Update the [Liferay Gradle Plugins Change Log Builder] dependency
to version 1.1.1.

### Fixed
- [LPS-82960]: Set the project property `jira.project.keys` for sub-repositories
to add new ticket ID prefixes for the `buildChangeLog` task.

## 5.4.72 - 2018-07-10

### Fixed
- [LPS-82960]: Load the `ci.properties` file to read `jira.project.keys`.

## 5.4.73 - 2018-07-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.69.

## 5.4.74 - 2018-07-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.70.

## 5.4.75 - 2018-07-12

### Fixed
- [LPS-82960]: Check `gradle.properties` for `jira.project.keys` when running
the `buildChangeLog` task.

## 5.4.76 - 2018-07-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.71.

## 5.4.77 - 2018-07-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.72.

## 5.4.78 - 2018-07-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.73.

### Fixed
- [LPS-74544]: Fixed URLs in `config-maven.gradle`
(`https://repository-cdn.liferay.com/nexus/content/repositories/`).

## 5.4.79 - 2018-07-14

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.74.

## 5.4.80 - 2018-07-16

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.75.

## 5.4.81 - 2018-07-16

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.76.

## 5.4.82 - 2018-07-16

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.77.

## 5.4.83 - 2018-07-17

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.78.

## 5.4.84 - 2018-07-17

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.79.

## 5.4.85 - 2018-07-17

### Changed
- [LPS-83520]: Update the [Liferay Gradle Plugins] dependency to version
3.12.80.

## 5.4.86 - 2018-07-18

### Changed
- [LPS-83576]: Update the [Liferay Gradle Plugins] dependency to version
3.12.81.

## 5.4.87 - 2018-07-18

### Changed
- [LPS-83483]: Update the [Liferay Gradle Plugins] dependency to version
3.12.82.

## 5.4.88 - 2018-07-18

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.83.

## 5.4.89 - 2018-07-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.84.

## 5.4.90 - 2018-07-23

### Changed
- [LPS-82976]: Change the marker file name `.lfrbuild-portal-all` to
`.lfrbuild-portal-deprecated`.

## 5.4.91 - 2018-07-24

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.85.
- [LPS-82960]: Remove the logic that loaded the `ci.properties` file to read
the `jira.project.keys`. This property should be set in the `gradle.properties`
file.

## 5.4.92 - 2018-07-24

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.86.

## 5.4.93 - 2018-07-25

### Changed
- [LPS-83761]: Update the [Liferay Gradle Plugins] dependency to version
3.12.87.

## 5.4.94 - 2018-07-25

### Changed
- [LPS-83705]: Update the [Liferay Gradle Plugins] dependency to version
3.12.88.

## 5.4.95 - 2018-07-27

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.89.

## 5.4.96 - 2018-07-27

### Changed
- [LPS-83920]: Skip replacements of the `updateFileVersions` task for projects
in the `aspectj` directory.

## 5.4.97 - 2018-07-27

### Changed
- [LPS-78938]: Update the [Liferay Gradle Plugins] dependency to version
3.12.90.

## 5.4.98 - 2018-07-27

### Added
- [LPS-83920]: Skip replacements of the `updateFileVersions` task if a
`.lfrbuild-releng-skip-update-file-versions` marker file is found in a parent
directory.
- [LPS-83929]: Add the ability to exclude certain projects from executing the
`writeArtifactPublishCommands` task by setting the property
`writeArtifactPublishCommands.ignore.project.regex`.

## 5.4.99 - 2018-07-30

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.91.

## 5.4.100 - 2018-07-31

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.92.

## 5.4.101 - 2018-08-01

### Changed
- [LPS-84039]: Update the [Liferay Gradle Plugins] dependency to version
3.12.93.

## 5.4.102 - 2018-08-01

### Fixed
- [LPS-84027]: Fix error when using the [Gradle Dependency Management] plugin in
OSGi modules.

## 5.4.103 - 2018-08-01

### Added
- [LPS-84027]: Add support for the `SPRING_BOOT_JAVA_OPTS` environment variable.

## 5.4.104 - 2018-08-01

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.94.

## 5.5.0 - 2018-08-01

### Added
- [LPS-83168]: Embed `pom.properties` and `pom.xml` when publishing OSGi modules
to Maven.
- [LPS-84055]: Automatically exclude Git ignored projects from executing the
`writeArtifactPublishCommands` task.

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.95.

## 5.5.1 - 2018-08-02

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins] dependency to version
3.12.96.

## 5.5.2 - 2018-08-02

### Changed
- [LPS-76475]: Update the [Liferay Gradle Plugins] dependency to version
3.12.97.

### Fixed
- [LPS-84055]: Fix the `printDependentArtifact` task so it prints the project
directory when it has a dependency with version `default`.

## 5.5.3 - 2018-08-05

### Changed
- [LPS-83705]: Update the [Liferay Gradle Plugins] dependency to version
3.12.98.

## 5.5.4 - 2018-08-06

### Changed
- [LPS-78033]: Update the [Liferay Gradle Plugins] dependency to version
3.12.99.

## 5.5.5 - 2018-08-06

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.100.

## 5.5.6 - 2018-08-06

### Changed
- [LPS-84213]: Update the [Liferay Gradle Plugins] dependency to version
3.12.101.

## 5.5.7 - 2018-08-06

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.102.

### Fixed
- [LPS-84055]: Fix the `printDependentArtifact` task so it prints the project
directory when it has a `compile*` dependency with the version `default`.

## 5.5.8 - 2018-08-07

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.103.

## 5.5.9 - 2018-08-08

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.104.

## 5.5.10 - 2018-08-09

### Fixed
- [LPS-84055]: Fix the `writeArtifactPublishCommands` task so it's not skipped
when the `test` dependency is version `default`.

## 5.5.11 - 2018-08-09

### Changed
- [LPS-84307]: Update the [Liferay Gradle Plugins] dependency to version
3.12.105.

## 5.5.12 - 2018-08-09

### Changed
- [LPS-84313]: Update the `com.liferay.spring.boot.defaults` plugin to
automatically apply the `com.liferay.test.integration.base` plugin. Configure
the `check` task to depend on the `testIntegration` task.

## 5.5.13 - 2018-08-10

### Changed
- [LPS-84039]: Update the [Liferay Gradle Plugins] dependency to version
3.12.106.

## 5.5.14 - 2018-08-13

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.107.

## 5.5.15 - 2018-08-15

### Changed
- [LPS-84039]: Update the [Liferay Gradle Plugins] dependency to version
3.12.108.

## 5.5.16 - 2018-08-15

### Changed
- [LPS-84473]: Update the [Liferay Gradle Plugins] dependency to version
3.12.109.

## 5.5.17 - 2018-08-15

### Changed
- [LPS-83790]: Update the [Liferay Gradle Plugins] dependency to version
3.12.110.

## 5.5.18 - 2018-08-20

### Changed
- [LPS-84621]: Disable the `validateSchema` task for CI.

## 5.5.19 - 2018-08-21

### Changed
- [LPS-84615]: Update the [Liferay Gradle Plugins] dependency to version
3.12.111.
- [LPS-84624]: Update the `com.liferay.spring.boot.defaults` plugin to configure
the `test` and `testIntegration` tasks with sensible defaults.

## 5.5.20 - 2018-08-22

### Changed
- [LPS-84218]: Update the [Liferay Gradle Plugins] dependency to version
3.12.112.

## 5.5.21 - 2018-08-23

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.113.

## 5.5.22 - 2018-08-23

### Added
- [LPS-83929]: Add the ability to exclude certain projects from executing the
`printDependentArtifact` task by setting the property
`printDependentArtifact.ignore.project.regex`.

### Changed
- [LPS-83067]: Update the [Liferay Gradle Plugins] dependency to version
3.12.114.
- [LPS-83067]: Update the [Liferay Gradle Plugins Baseline] dependency to
version 1.3.2.

## 5.5.23 - 2018-08-27

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.115.

## 5.5.24 - 2018-08-27

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.116.

## 5.5.25 - 2018-08-28

### Changed
- [LPS-84094]: Allow properties defined in `gradle-ext.properties` to be
overridden by values defined in a `gradle-ext.properties` file.
- [LPS-84094]: Update the [Liferay Gradle Plugins] dependency to version
3.12.117.

## 5.5.26 - 2018-08-29

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.118.

### Fixed
- [LPS-84887]: Update Maven SCM property default values in
`config-maven.gradle`:
	- `scm.connection` = `scm:git:git@github.com:liferay/liferay-portal.git`
	- `scm.developerConnection` = `scm:git:git@github.com:liferay/liferay-portal.git`
	- `scm.url` = `http://github.com/liferay/liferay-portal`

## 5.5.27 - 2018-08-30

### Changed
- [LPS-84094]: Move `GradleUtil` methods to [Liferay Gradle Util] so it can be
used inside `build.gradle` files.
- [LPS-84621]: Enable the `validateSchema` task for CI.
- [LPS-84891]: Update the [Liferay Gradle Plugins] dependency to version
3.12.119.

## 5.5.28 - 2018-08-30

### Changed
- [LPS-80388]: Update the [Liferay Gradle Plugins] dependency to version
3.12.120.

## 5.5.29 - 2018-09-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.121.

## 5.5.30 - 2018-09-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.122.

## 5.5.31 - 2018-09-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.123.

## 5.5.32 - 2018-09-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.124.

## 5.5.33 - 2018-09-04

### Fixed
- [LPS-70819]: Fix the `compileJSP.destinationDir` property when the
`jsp.precompile.from.source` project property is set to `true`.

## 5.5.34 - 2018-09-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.125.

## 5.5.35 - 2018-09-04

### Changed
- [LPS-85092]: Update the [Liferay Gradle Plugins] dependency to version
3.12.126.

## 5.5.36 - 2018-09-05

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins] dependency to version
3.12.127.

[Find Security Bugs]: https://github.com/liferay/liferay-portal/tree/master/modules/third-party/com-h3xstream-findsecbugs
[Gradle Dependency Management]: https://github.com/spring-gradle-plugins/dependency-management-plugin
[Gradle License Report]: https://github.com/jk1/Gradle-License-Report
[Liferay CDN]: https://repository-cdn.liferay.com/nexus/content/groups/public
[Liferay Gradle Plugins]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins
[Liferay Gradle Plugins App Javadoc Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-app-javadoc-builder
[Liferay Gradle Plugins Baseline]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-baseline
[Liferay Gradle Plugins Change Log Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-change-log-builder
[Liferay Gradle Plugins Dependency Checker]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-dependency-checker
[Liferay Gradle Plugins JSDoc]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-jsdoc
[Liferay Gradle Plugins Lang Merger]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-lang-merger
[Liferay Gradle Plugins Node]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-node
[Liferay Gradle Plugins Patcher]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-patcher
[Liferay Gradle Plugins Poshi Runner]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-poshi-runner
[Liferay Gradle Plugins Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-source-formatter
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
[LPS-65633]: https://issues.liferay.com/browse/LPS-65633
[LPS-65930]: https://issues.liferay.com/browse/LPS-65930
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
[LPS-69247]: https://issues.liferay.com/browse/LPS-69247
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
[LPS-69999]: https://issues.liferay.com/browse/LPS-69999
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
[LPS-71117]: https://issues.liferay.com/browse/LPS-71117
[LPS-71118]: https://issues.liferay.com/browse/LPS-71118
[LPS-71164]: https://issues.liferay.com/browse/LPS-71164
[LPS-71201]: https://issues.liferay.com/browse/LPS-71201
[LPS-71222]: https://issues.liferay.com/browse/LPS-71222
[LPS-71224]: https://issues.liferay.com/browse/LPS-71224
[LPS-71264]: https://issues.liferay.com/browse/LPS-71264
[LPS-71285]: https://issues.liferay.com/browse/LPS-71285
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
[LPS-72347]: https://issues.liferay.com/browse/LPS-72347
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
[LPS-72854]: https://issues.liferay.com/browse/LPS-72854
[LPS-72868]: https://issues.liferay.com/browse/LPS-72868
[LPS-72912]: https://issues.liferay.com/browse/LPS-72912
[LPS-72914]: https://issues.liferay.com/browse/LPS-72914
[LPS-72989]: https://issues.liferay.com/browse/LPS-72989
[LPS-73058]: https://issues.liferay.com/browse/LPS-73058
[LPS-73070]: https://issues.liferay.com/browse/LPS-73070
[LPS-73124]: https://issues.liferay.com/browse/LPS-73124
[LPS-73128]: https://issues.liferay.com/browse/LPS-73128
[LPS-73141]: https://issues.liferay.com/browse/LPS-73141
[LPS-73148]: https://issues.liferay.com/browse/LPS-73148
[LPS-73156]: https://issues.liferay.com/browse/LPS-73156
[LPS-73235]: https://issues.liferay.com/browse/LPS-73235
[LPS-73271]: https://issues.liferay.com/browse/LPS-73271
[LPS-73289]: https://issues.liferay.com/browse/LPS-73289
[LPS-73327]: https://issues.liferay.com/browse/LPS-73327
[LPS-73352]: https://issues.liferay.com/browse/LPS-73352
[LPS-73353]: https://issues.liferay.com/browse/LPS-73353
[LPS-73383]: https://issues.liferay.com/browse/LPS-73383
[LPS-73470]: https://issues.liferay.com/browse/LPS-73470
[LPS-73472]: https://issues.liferay.com/browse/LPS-73472
[LPS-73489]: https://issues.liferay.com/browse/LPS-73489
[LPS-73495]: https://issues.liferay.com/browse/LPS-73495
[LPS-73525]: https://issues.liferay.com/browse/LPS-73525
[LPS-73584]: https://issues.liferay.com/browse/LPS-73584
[LPS-73600]: https://issues.liferay.com/browse/LPS-73600
[LPS-73607]: https://issues.liferay.com/browse/LPS-73607
[LPS-73642]: https://issues.liferay.com/browse/LPS-73642
[LPS-73652]: https://issues.liferay.com/browse/LPS-73652
[LPS-73655]: https://issues.liferay.com/browse/LPS-73655
[LPS-73725]: https://issues.liferay.com/browse/LPS-73725
[LPS-73807]: https://issues.liferay.com/browse/LPS-73807
[LPS-73818]: https://issues.liferay.com/browse/LPS-73818
[LPS-73855]: https://issues.liferay.com/browse/LPS-73855
[LPS-73935]: https://issues.liferay.com/browse/LPS-73935
[LPS-73955]: https://issues.liferay.com/browse/LPS-73955
[LPS-73967]: https://issues.liferay.com/browse/LPS-73967
[LPS-74034]: https://issues.liferay.com/browse/LPS-74034
[LPS-74054]: https://issues.liferay.com/browse/LPS-74054
[LPS-74063]: https://issues.liferay.com/browse/LPS-74063
[LPS-74088]: https://issues.liferay.com/browse/LPS-74088
[LPS-74092]: https://issues.liferay.com/browse/LPS-74092
[LPS-74104]: https://issues.liferay.com/browse/LPS-74104
[LPS-74110]: https://issues.liferay.com/browse/LPS-74110
[LPS-74126]: https://issues.liferay.com/browse/LPS-74126
[LPS-74139]: https://issues.liferay.com/browse/LPS-74139
[LPS-74143]: https://issues.liferay.com/browse/LPS-74143
[LPS-74155]: https://issues.liferay.com/browse/LPS-74155
[LPS-74171]: https://issues.liferay.com/browse/LPS-74171
[LPS-74207]: https://issues.liferay.com/browse/LPS-74207
[LPS-74210]: https://issues.liferay.com/browse/LPS-74210
[LPS-74222]: https://issues.liferay.com/browse/LPS-74222
[LPS-74250]: https://issues.liferay.com/browse/LPS-74250
[LPS-74269]: https://issues.liferay.com/browse/LPS-74269
[LPS-74278]: https://issues.liferay.com/browse/LPS-74278
[LPS-74314]: https://issues.liferay.com/browse/LPS-74314
[LPS-74343]: https://issues.liferay.com/browse/LPS-74343
[LPS-74345]: https://issues.liferay.com/browse/LPS-74345
[LPS-74348]: https://issues.liferay.com/browse/LPS-74348
[LPS-74368]: https://issues.liferay.com/browse/LPS-74368
[LPS-74373]: https://issues.liferay.com/browse/LPS-74373
[LPS-74426]: https://issues.liferay.com/browse/LPS-74426
[LPS-74433]: https://issues.liferay.com/browse/LPS-74433
[LPS-74449]: https://issues.liferay.com/browse/LPS-74449
[LPS-74457]: https://issues.liferay.com/browse/LPS-74457
[LPS-74469]: https://issues.liferay.com/browse/LPS-74469
[LPS-74490]: https://issues.liferay.com/browse/LPS-74490
[LPS-74526]: https://issues.liferay.com/browse/LPS-74526
[LPS-74538]: https://issues.liferay.com/browse/LPS-74538
[LPS-74544]: https://issues.liferay.com/browse/LPS-74544
[LPS-74608]: https://issues.liferay.com/browse/LPS-74608
[LPS-74614]: https://issues.liferay.com/browse/LPS-74614
[LPS-74637]: https://issues.liferay.com/browse/LPS-74637
[LPS-74657]: https://issues.liferay.com/browse/LPS-74657
[LPS-74738]: https://issues.liferay.com/browse/LPS-74738
[LPS-74749]: https://issues.liferay.com/browse/LPS-74749
[LPS-74752]: https://issues.liferay.com/browse/LPS-74752
[LPS-74770]: https://issues.liferay.com/browse/LPS-74770
[LPS-74789]: https://issues.liferay.com/browse/LPS-74789
[LPS-74824]: https://issues.liferay.com/browse/LPS-74824
[LPS-74849]: https://issues.liferay.com/browse/LPS-74849
[LPS-74867]: https://issues.liferay.com/browse/LPS-74867
[LPS-74884]: https://issues.liferay.com/browse/LPS-74884
[LPS-74892]: https://issues.liferay.com/browse/LPS-74892
[LPS-74904]: https://issues.liferay.com/browse/LPS-74904
[LPS-74933]: https://issues.liferay.com/browse/LPS-74933
[LPS-75009]: https://issues.liferay.com/browse/LPS-75009
[LPS-75010]: https://issues.liferay.com/browse/LPS-75010
[LPS-75039]: https://issues.liferay.com/browse/LPS-75039
[LPS-75047]: https://issues.liferay.com/browse/LPS-75047
[LPS-75049]: https://issues.liferay.com/browse/LPS-75049
[LPS-75096]: https://issues.liferay.com/browse/LPS-75096
[LPS-75100]: https://issues.liferay.com/browse/LPS-75100
[LPS-75175]: https://issues.liferay.com/browse/LPS-75175
[LPS-75239]: https://issues.liferay.com/browse/LPS-75239
[LPS-75247]: https://issues.liferay.com/browse/LPS-75247
[LPS-75254]: https://issues.liferay.com/browse/LPS-75254
[LPS-75273]: https://issues.liferay.com/browse/LPS-75273
[LPS-75323]: https://issues.liferay.com/browse/LPS-75323
[LPS-75359]: https://issues.liferay.com/browse/LPS-75359
[LPS-75399]: https://issues.liferay.com/browse/LPS-75399
[LPS-75430]: https://issues.liferay.com/browse/LPS-75430
[LPS-75488]: https://issues.liferay.com/browse/LPS-75488
[LPS-75530]: https://issues.liferay.com/browse/LPS-75530
[LPS-75610]: https://issues.liferay.com/browse/LPS-75610
[LPS-75624]: https://issues.liferay.com/browse/LPS-75624
[LPS-75633]: https://issues.liferay.com/browse/LPS-75633
[LPS-75705]: https://issues.liferay.com/browse/LPS-75705
[LPS-75745]: https://issues.liferay.com/browse/LPS-75745
[LPS-75778]: https://issues.liferay.com/browse/LPS-75778
[LPS-75798]: https://issues.liferay.com/browse/LPS-75798
[LPS-75829]: https://issues.liferay.com/browse/LPS-75829
[LPS-75859]: https://issues.liferay.com/browse/LPS-75859
[LPS-75901]: https://issues.liferay.com/browse/LPS-75901
[LPS-75910]: https://issues.liferay.com/browse/LPS-75910
[LPS-75952]: https://issues.liferay.com/browse/LPS-75952
[LPS-75965]: https://issues.liferay.com/browse/LPS-75965
[LPS-75971]: https://issues.liferay.com/browse/LPS-75971
[LPS-76018]: https://issues.liferay.com/browse/LPS-76018
[LPS-76110]: https://issues.liferay.com/browse/LPS-76110
[LPS-76145]: https://issues.liferay.com/browse/LPS-76145
[LPS-76181]: https://issues.liferay.com/browse/LPS-76181
[LPS-76182]: https://issues.liferay.com/browse/LPS-76182
[LPS-76202]: https://issues.liferay.com/browse/LPS-76202
[LPS-76221]: https://issues.liferay.com/browse/LPS-76221
[LPS-76224]: https://issues.liferay.com/browse/LPS-76224
[LPS-76226]: https://issues.liferay.com/browse/LPS-76226
[LPS-76256]: https://issues.liferay.com/browse/LPS-76256
[LPS-76326]: https://issues.liferay.com/browse/LPS-76326
[LPS-76475]: https://issues.liferay.com/browse/LPS-76475
[LPS-76601]: https://issues.liferay.com/browse/LPS-76601
[LPS-76623]: https://issues.liferay.com/browse/LPS-76623
[LPS-76626]: https://issues.liferay.com/browse/LPS-76626
[LPS-76644]: https://issues.liferay.com/browse/LPS-76644
[LPS-76747]: https://issues.liferay.com/browse/LPS-76747
[LPS-76840]: https://issues.liferay.com/browse/LPS-76840
[LPS-76926]: https://issues.liferay.com/browse/LPS-76926
[LPS-76954]: https://issues.liferay.com/browse/LPS-76954
[LPS-76957]: https://issues.liferay.com/browse/LPS-76957
[LPS-76997]: https://issues.liferay.com/browse/LPS-76997
[LPS-77111]: https://issues.liferay.com/browse/LPS-77111
[LPS-77143]: https://issues.liferay.com/browse/LPS-77143
[LPS-77186]: https://issues.liferay.com/browse/LPS-77186
[LPS-77250]: https://issues.liferay.com/browse/LPS-77250
[LPS-77305]: https://issues.liferay.com/browse/LPS-77305
[LPS-77350]: https://issues.liferay.com/browse/LPS-77350
[LPS-77359]: https://issues.liferay.com/browse/LPS-77359
[LPS-77400]: https://issues.liferay.com/browse/LPS-77400
[LPS-77402]: https://issues.liferay.com/browse/LPS-77402
[LPS-77423]: https://issues.liferay.com/browse/LPS-77423
[LPS-77425]: https://issues.liferay.com/browse/LPS-77425
[LPS-77441]: https://issues.liferay.com/browse/LPS-77441
[LPS-77524]: https://issues.liferay.com/browse/LPS-77524
[LPS-77532]: https://issues.liferay.com/browse/LPS-77532
[LPS-77630]: https://issues.liferay.com/browse/LPS-77630
[LPS-77639]: https://issues.liferay.com/browse/LPS-77639
[LPS-77795]: https://issues.liferay.com/browse/LPS-77795
[LPS-77797]: https://issues.liferay.com/browse/LPS-77797
[LPS-77836]: https://issues.liferay.com/browse/LPS-77836
[LPS-77840]: https://issues.liferay.com/browse/LPS-77840
[LPS-77875]: https://issues.liferay.com/browse/LPS-77875
[LPS-77886]: https://issues.liferay.com/browse/LPS-77886
[LPS-77916]: https://issues.liferay.com/browse/LPS-77916
[LPS-77968]: https://issues.liferay.com/browse/LPS-77968
[LPS-77996]: https://issues.liferay.com/browse/LPS-77996
[LPS-78033]: https://issues.liferay.com/browse/LPS-78033
[LPS-78038]: https://issues.liferay.com/browse/LPS-78038
[LPS-78050]: https://issues.liferay.com/browse/LPS-78050
[LPS-78071]: https://issues.liferay.com/browse/LPS-78071
[LPS-78096]: https://issues.liferay.com/browse/LPS-78096
[LPS-78150]: https://issues.liferay.com/browse/LPS-78150
[LPS-78231]: https://issues.liferay.com/browse/LPS-78231
[LPS-78261]: https://issues.liferay.com/browse/LPS-78261
[LPS-78266]: https://issues.liferay.com/browse/LPS-78266
[LPS-78269]: https://issues.liferay.com/browse/LPS-78269
[LPS-78308]: https://issues.liferay.com/browse/LPS-78308
[LPS-78436]: https://issues.liferay.com/browse/LPS-78436
[LPS-78459]: https://issues.liferay.com/browse/LPS-78459
[LPS-78477]: https://issues.liferay.com/browse/LPS-78477
[LPS-78493]: https://issues.liferay.com/browse/LPS-78493
[LPS-78537]: https://issues.liferay.com/browse/LPS-78537
[LPS-78558]: https://issues.liferay.com/browse/LPS-78558
[LPS-78571]: https://issues.liferay.com/browse/LPS-78571
[LPS-78669]: https://issues.liferay.com/browse/LPS-78669
[LPS-78741]: https://issues.liferay.com/browse/LPS-78741
[LPS-78750]: https://issues.liferay.com/browse/LPS-78750
[LPS-78767]: https://issues.liferay.com/browse/LPS-78767
[LPS-78772]: https://issues.liferay.com/browse/LPS-78772
[LPS-78845]: https://issues.liferay.com/browse/LPS-78845
[LPS-78854]: https://issues.liferay.com/browse/LPS-78854
[LPS-78901]: https://issues.liferay.com/browse/LPS-78901
[LPS-78911]: https://issues.liferay.com/browse/LPS-78911
[LPS-78938]: https://issues.liferay.com/browse/LPS-78938
[LPS-78971]: https://issues.liferay.com/browse/LPS-78971
[LPS-79131]: https://issues.liferay.com/browse/LPS-79131
[LPS-79191]: https://issues.liferay.com/browse/LPS-79191
[LPS-79192]: https://issues.liferay.com/browse/LPS-79192
[LPS-79226]: https://issues.liferay.com/browse/LPS-79226
[LPS-79262]: https://issues.liferay.com/browse/LPS-79262
[LPS-79282]: https://issues.liferay.com/browse/LPS-79282
[LPS-79286]: https://issues.liferay.com/browse/LPS-79286
[LPS-79360]: https://issues.liferay.com/browse/LPS-79360
[LPS-79365]: https://issues.liferay.com/browse/LPS-79365
[LPS-79386]: https://issues.liferay.com/browse/LPS-79386
[LPS-79388]: https://issues.liferay.com/browse/LPS-79388
[LPS-79450]: https://issues.liferay.com/browse/LPS-79450
[LPS-79453]: https://issues.liferay.com/browse/LPS-79453
[LPS-79576]: https://issues.liferay.com/browse/LPS-79576
[LPS-79623]: https://issues.liferay.com/browse/LPS-79623
[LPS-79665]: https://issues.liferay.com/browse/LPS-79665
[LPS-79679]: https://issues.liferay.com/browse/LPS-79679
[LPS-79709]: https://issues.liferay.com/browse/LPS-79709
[LPS-79755]: https://issues.liferay.com/browse/LPS-79755
[LPS-79799]: https://issues.liferay.com/browse/LPS-79799
[LPS-79919]: https://issues.liferay.com/browse/LPS-79919
[LPS-79953]: https://issues.liferay.com/browse/LPS-79953
[LPS-79963]: https://issues.liferay.com/browse/LPS-79963
[LPS-80055]: https://issues.liferay.com/browse/LPS-80055
[LPS-80091]: https://issues.liferay.com/browse/LPS-80091
[LPS-80122]: https://issues.liferay.com/browse/LPS-80122
[LPS-80123]: https://issues.liferay.com/browse/LPS-80123
[LPS-80125]: https://issues.liferay.com/browse/LPS-80125
[LPS-80184]: https://issues.liferay.com/browse/LPS-80184
[LPS-80332]: https://issues.liferay.com/browse/LPS-80332
[LPS-80386]: https://issues.liferay.com/browse/LPS-80386
[LPS-80388]: https://issues.liferay.com/browse/LPS-80388
[LPS-80394]: https://issues.liferay.com/browse/LPS-80394
[LPS-80466]: https://issues.liferay.com/browse/LPS-80466
[LPS-80513]: https://issues.liferay.com/browse/LPS-80513
[LPS-80517]: https://issues.liferay.com/browse/LPS-80517
[LPS-80520]: https://issues.liferay.com/browse/LPS-80520
[LPS-80544]: https://issues.liferay.com/browse/LPS-80544
[LPS-80723]: https://issues.liferay.com/browse/LPS-80723
[LPS-80840]: https://issues.liferay.com/browse/LPS-80840
[LPS-80920]: https://issues.liferay.com/browse/LPS-80920
[LPS-80944]: https://issues.liferay.com/browse/LPS-80944
[LPS-80950]: https://issues.liferay.com/browse/LPS-80950
[LPS-81106]: https://issues.liferay.com/browse/LPS-81106
[LPS-81336]: https://issues.liferay.com/browse/LPS-81336
[LPS-81404]: https://issues.liferay.com/browse/LPS-81404
[LPS-81555]: https://issues.liferay.com/browse/LPS-81555
[LPS-81635]: https://issues.liferay.com/browse/LPS-81635
[LPS-81638]: https://issues.liferay.com/browse/LPS-81638
[LPS-81795]: https://issues.liferay.com/browse/LPS-81795
[LPS-81895]: https://issues.liferay.com/browse/LPS-81895
[LPS-81900]: https://issues.liferay.com/browse/LPS-81900
[LPS-82001]: https://issues.liferay.com/browse/LPS-82001
[LPS-82121]: https://issues.liferay.com/browse/LPS-82121
[LPS-82130]: https://issues.liferay.com/browse/LPS-82130
[LPS-82178]: https://issues.liferay.com/browse/LPS-82178
[LPS-82209]: https://issues.liferay.com/browse/LPS-82209
[LPS-82261]: https://issues.liferay.com/browse/LPS-82261
[LPS-82343]: https://issues.liferay.com/browse/LPS-82343
[LPS-82420]: https://issues.liferay.com/browse/LPS-82420
[LPS-82433]: https://issues.liferay.com/browse/LPS-82433
[LPS-82534]: https://issues.liferay.com/browse/LPS-82534
[LPS-82568]: https://issues.liferay.com/browse/LPS-82568
[LPS-82815]: https://issues.liferay.com/browse/LPS-82815
[LPS-82828]: https://issues.liferay.com/browse/LPS-82828
[LPS-82857]: https://issues.liferay.com/browse/LPS-82857
[LPS-82960]: https://issues.liferay.com/browse/LPS-82960
[LPS-82976]: https://issues.liferay.com/browse/LPS-82976
[LPS-83067]: https://issues.liferay.com/browse/LPS-83067
[LPS-83104]: https://issues.liferay.com/browse/LPS-83104
[LPS-83168]: https://issues.liferay.com/browse/LPS-83168
[LPS-83220]: https://issues.liferay.com/browse/LPS-83220
[LPS-83300]: https://issues.liferay.com/browse/LPS-83300
[LPS-83483]: https://issues.liferay.com/browse/LPS-83483
[LPS-83520]: https://issues.liferay.com/browse/LPS-83520
[LPS-83576]: https://issues.liferay.com/browse/LPS-83576
[LPS-83705]: https://issues.liferay.com/browse/LPS-83705
[LPS-83761]: https://issues.liferay.com/browse/LPS-83761
[LPS-83790]: https://issues.liferay.com/browse/LPS-83790
[LPS-83920]: https://issues.liferay.com/browse/LPS-83920
[LPS-83929]: https://issues.liferay.com/browse/LPS-83929
[LPS-84027]: https://issues.liferay.com/browse/LPS-84027
[LPS-84039]: https://issues.liferay.com/browse/LPS-84039
[LPS-84055]: https://issues.liferay.com/browse/LPS-84055
[LPS-84094]: https://issues.liferay.com/browse/LPS-84094
[LPS-84119]: https://issues.liferay.com/browse/LPS-84119
[LPS-84213]: https://issues.liferay.com/browse/LPS-84213
[LPS-84218]: https://issues.liferay.com/browse/LPS-84218
[LPS-84307]: https://issues.liferay.com/browse/LPS-84307
[LPS-84313]: https://issues.liferay.com/browse/LPS-84313
[LPS-84473]: https://issues.liferay.com/browse/LPS-84473
[LPS-84615]: https://issues.liferay.com/browse/LPS-84615
[LPS-84621]: https://issues.liferay.com/browse/LPS-84621
[LPS-84624]: https://issues.liferay.com/browse/LPS-84624
[LPS-84887]: https://issues.liferay.com/browse/LPS-84887
[LPS-84891]: https://issues.liferay.com/browse/LPS-84891
[LPS-85092]: https://issues.liferay.com/browse/LPS-85092
[LRDOCS-2594]: https://issues.liferay.com/browse/LRDOCS-2594
[LRDOCS-2841]: https://issues.liferay.com/browse/LRDOCS-2841
[LRDOCS-2981]: https://issues.liferay.com/browse/LRDOCS-2981
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023
[LRDOCS-3038]: https://issues.liferay.com/browse/LRDOCS-3038
[LRDOCS-3643]: https://issues.liferay.com/browse/LRDOCS-3643
[LRDOCS-4111]: https://issues.liferay.com/browse/LRDOCS-4111