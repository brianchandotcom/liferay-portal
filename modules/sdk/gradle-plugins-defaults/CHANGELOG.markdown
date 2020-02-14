# Liferay Gradle Plugins Defaults Change Log

## 1.0.2 - 2016-08-11

### Dependencies
- [LPS-65786]: Update the com.liferay.gradle.plugins dependency to version
2.0.3.

## 1.0.3 - 2016-08-15

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.4.

## 1.0.4 - 2016-08-15

### Dependencies
- [LPS-66906]: Update the com.liferay.gradle.plugins dependency to version
2.0.5.

## 1.0.5 - 2016-08-15

### Commits
- [LPS-64588]: Add task to create the theme LARs before running Gulp
(82694978f6)

## 1.0.6 - 2016-08-16

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.6.

## 1.0.7 - 2016-08-16

### Commits
- [LPS-67596]: These methods are not available in Gradle 3.0 (13ab6279ff)

### Dependencies
- [LPS-67596]: Update the com.liferay.gradle.plugins dependency to version
2.0.7.

## 1.0.8 - 2016-08-17

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.8.

## 1.0.9 - 2016-08-19

### Commits
- [LPS-67694]: Baseline gradle-plugins-defaults (dfe465b1e9)
- [LPS-67694]: Add option to publish a snapshot only if stale (0d0cc7008b)
- [LPS-67694]: Move method to new util class (cc4308c1b9)
- [LPS-67694]: These methods are in the same class (02c426a405)

## 1.1.1 - 2016-08-19

### Commits
- [LPS-61099]: Add property to set the project path prefix (dee03afc1c)
- [LPS-61099]: Add util method to read properties via reflection (d8fbb3633f)

## 1.1.2 - 2016-08-19

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.9.

## 1.1.3 - 2016-08-22

### Commits
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.14+ (8cf811e5af)

### Dependencies
- [LPS-67658]: Update the com.liferay.gradle.plugins dependency to version
2.0.10.

### Description
- [LPS-67658]: Compile the plugin against Gradle 2.14 to make it compatible with
both Gradle 2.14+ and Gradle 3.0.

## 1.1.4 - 2016-08-23

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.11.

## 1.1.5 - 2016-08-23

### Commits
- [LPS-67694]: Use Gradle to download the latest artifact, not Nexus
(19af76875a)
- [LPS-67694]: Check the snapshot staleness during configuration instead
(975ceca950)

### Description
- [LPS-67694]: Disable the `install` and `uploadArchives` tasks and all their
dependencies during the configuration phase if the `-PsnapshotIfStale` argument
is provided and the latest published snapshot is up-to-date.
- [LPS-67694]: Use Gradle to download the latest published artifact of a project
instead of the Nexus REST API, as the latter does not always return the correct
artifact.

## 1.1.6 - 2016-08-25

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.12.

## 1.1.7 - 2016-08-27

### Dependencies
- [LPS-67804]: Update the com.liferay.gradle.plugins dependency to version
2.0.13.

## 1.1.8 - 2016-08-27

### Commits
- [LPS-67023]: Copy CI configuration in a Gradle plugin (c9459a437b)

### Dependencies
- [LPS-67023]: Update the com.liferay.gradle.plugins dependency to version
2.0.14.

### Description
- [LPS-67023]: Automatically apply the following default settings when on
Jenkins:
	- Block Node.js invocations if the `com.liferay.cache` plugin is applied.
	- Enable the `node_modules` directory cache.
	- Retry `npm install` three times if a Node.js invocation fails.
	- Set up the NPM registry URL based on the `nodejs.npm.ci.registry` project
	property.

## 1.1.9 - 2016-08-27

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.15.

## 1.1.10 - 2016-08-27

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.16.

## 1.1.11 - 2016-08-29

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.17.

## 1.1.12 - 2016-08-30

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.18.

## 1.1.13 - 2016-08-31

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.19.

## 1.1.14 - 2016-08-31

### Commits
- [LPS-67863]: Deprecate duplicate method (d6e5a91819)
- [LPS-67863]: Automatically ignore baseline errors when syncing (b12276a7fa)
- [LPS-67863]: Commit synced changes after running "baseline" (f6f89c0864)
- [LPS-67863]: Sort keys while saving .properties files (89e236b059)
- [LPS-67863]: Sync bundle and package versions with release branch (565c87aad4)
- [LPS-67863]: Extract util method to write .properties file (506c2abc7b)
- [LPS-67863]: If project.version is overridden, we need to set a new title
(2efd6067c1)
- [LPS-67863]: Allow to override Bundle-Version and packageinfo (4aeea06d10)

## 1.2.0 - 2016-08-31

### Description
- [LPS-67863]: Allow the `Bundle-Version` and `packageinfo` versions of an OSGi
project to be overridden by creating a
`.version-overrides-${project.name}.properties` file in the parent directory of
the `.gitrepo` file with the following values:
	- `Bundle-Version=[new bundle version]`
	- `com.liferay.foo.bar=[new packageinfo version for com.liferay.foo.bar
package]`
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

### Commits
- [LPS-67863]: Avoid Git error if the module does not have packageinfo files
(0d464d263d)

### Description
- [LPS-67863]: Avoid Git error while running `gradlew baseline -PsyncRelease` on
an OSGi project that does not contain a `packageinfo` file.

## 1.2.2 - 2016-09-01

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.20.

## 1.2.3 - 2016-09-01

### Commits
- [LPS-67863]: Always override package versions (3a1d67608f)
- [LPS-67863]: Rename variables (18705ce501)
- [LPS-67863]: Rename version override file (39e45335b6)

### Description
- [LPS-67863]: The file that contains the version overrides for an OSGi module
is now called `.version-override-${project.name}.properties`.
- [LPS-67863]: The `packageinfo` versions are always overridden with the
versions specified in the `.version-override-${project.name}.properties` file,
even if the versions in the `packageinfo` files are greater.

## 1.2.4 - 2016-09-01

### Commits
- [LPS-67863]: If there are no version overrides to write, delete the file
(f522949e20)
- [LPS-67863]: Only write a version override if it's necessary (038e2f2510)
- [LPS-67863]: Disable "printArtifactPublishCommands" if we have "default"
dependencies (f1106bd97a)

### Description
- [LPS-67863]: Disable the `printArtifactPublishCommands` task if the project's
`build.gradle` contains the string `version: "default"`, to prevent releasing
modules with unpublished dependencies.
- [LPS-67863]: The `.version-override-${project.name}.properties` file now
contains only the version overrides that differ from the versions specified in
the `bnd.bnd` and `packageinfo` files.

## 1.2.5 - 2016-09-01

### Commits
- [LPS-67863]: The version override file may not exist (7a2b2d5511)

### Description
- [LPS-67863]: Avoid throwing an exception while running `gradlew baseline
-PsyncRelease` on a project that does not contain a
`.version-override-${project.name}.properties` file.

## 1.2.6 - 2016-09-02

### Commits
- [LPS-67863]: Don't fail if build.gradle does not exist (a736c5f7b4)

### Description
- [LPS-67863]: Avoid throwing an exception while running the
`printArtifactPublishCommands` task on a project that does not contain a
`build.gradle` file.

## 1.2.7 - 2016-09-02

### Dependencies
- [LPS-67986]: Update the com.liferay.gradle.plugins dependency to version
2.0.21.

## 1.2.8 - 2016-09-02

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.22.

## 1.2.9 - 2016-09-05

### Commits
- [LPS-66853]: cache (1b8dfc1c29)

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.23.

## 1.2.10 - 2016-09-05

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.24.

## 1.2.11 - 2016-09-06

### Dependencies
- [LPS-67996]: Update the com.liferay.gradle.plugins dependency to version
2.0.25.

## 1.2.12 - 2016-09-07

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.26.

## 1.2.13 - 2016-09-07

### Commits
- [LPS-68009]: Reject snapshots in "baseline" configuration (eaae137a41)

### Description
- [LPS-68009]: Reject snapshot artifacts while resolving the `baseline`
configuration.

## 1.2.14 - 2016-09-07

### Dependencies
- [LPS-68035]: Update the com.liferay.gradle.plugins dependency to version
2.0.27.

## 1.2.15 - 2016-09-08

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.28.

## 1.2.16 - 2016-09-08

### Commits
- [LPS-67863]: Force dependency versions via version override file (21402e917f)

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.29.

### Description
- [LPS-67863]: Allow dependency versions to be overridden in the
`.version-override-${project.name}.properties` file:
		[artifact group]-[artifact name]=[new version]

## 1.2.17 - 2016-09-09

### Commits
- [LRDOCS-2841]: modules/apps/content-targeting contains a .releng dir
(31838934b4)

### Dependencies
- [LRDOCS-2841]: Update the com.liferay.gradle.plugins dependency to version
2.0.30.

### Description
- [LRDOCS-2841]: Look for the `.releng` directory starting from the project
directory instead of the root project directory. Doing this lets submodules like
`content-targeting` have their own separate `.releng` directory.

## 1.2.18 - 2016-09-12

### Dependencies
- [LPS-67766]: Update the com.liferay.gradle.plugins dependency to version
2.0.31.

## 1.2.19 - 2016-09-13

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.32.

## 1.2.20 - 2016-09-13

### Dependencies
- [LPS-67986]: Update the com.liferay.gradle.plugins dependency to version
2.0.33.

## 1.2.21 - 2016-09-13

### Commits
- [LRDOCS-2981]: Prepend "Module " to appJavadoc module headings (28ca16e327)

### Description
- [LRDOCS-2981]: Prepend *Module* string to `appJavadoc` module headings.

## 1.2.22 - 2016-09-14

### Dependencies
- [LPS-68131]: Update the com.liferay.gradle.plugins dependency to version
2.0.34.

## 1.2.23 - 2016-09-16

### Dependencies
- [LPS-68131]: Update the com.liferay.gradle.plugins dependency to version
2.0.35.

## 1.2.24 - 2016-09-20

### Commits
- [LPS-67352]: SF, enforce empty line after finishing referencing variable
(4ff2bb6038)
- [LPS-68230]: These are internal plugins, no need to apply them via Gradle
(821c0ced23)
- [LPS-68230]: Hard-code Node.js version (fc21d5b11a)

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.36.

### Description
- [LPS-68230]: Configure [Liferay Gradle Plugins Node] to use version 6.6.0 of
Node.js.
- [LPS-68230]: To reduce the number of plugins applied to a project and improve
performance, plugins in `com.liferay.gradle.plugins.defaults.internal` are no
longer applied via `apply plugin`.

## 1.2.25 - 2016-09-20

### Dependencies
- [LPS-67653]: Update the com.liferay.gradle.plugins dependency to version
2.0.37.

## 1.2.26 - 2016-09-21

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.38.

## 1.2.27 - 2016-09-22

### Dependencies
- [LPS-68297]: Update the com.liferay.gradle.plugins dependency to version
2.0.39.

### Description
- [LPS-68305]: Set the `buildService.buildNumberIncrement` property to `false`
by default.

## 1.2.28 - 2016-09-22

### Commits
- [LPS-68305]: Disable service.properties increment (f7918df329)

## 1.2.29 - 2016-09-22

### Commits
- [LPS-66906]: Set the "sass-binary-site" argument via build property in CI
(a4cbb8246b)

### Dependencies
- [LPS-66906]: Update the com.liferay.gradle.plugins dependency to version
2.0.40.

### Description
- [LPS-66906]: Override the [`sass-binary-path`]
(https://github.com/sass/node-sass#binary-configuration-parameters) argument in
the `npmInstall` task with the value of the project property
`nodejs.npm.ci.sass.binary.site` when on Jenkins.

## 1.2.30 - 2016-09-23

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.41.

## 1.2.31 - 2016-09-23

### Commits
- [LPS-68306]: Add property to include projects with ".lfrbuild-portal-pre"
(117c16ca84)

### Description
- [LPS-68306]: Set the system property `portal.pre.build` to `true` to only
include the projects containing a `.lfrbuild-portal-pre` marker file.

## 1.2.32 - 2016-09-26

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.42.

## 1.2.33 - 2016-09-27

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
2.0.44.

## 1.2.34 - 2016-09-27

### Commits
- [LPS-67863]: Commit deletion of version override file (27dff45ba5)
- [LPS-67863]: Use another character to avoid conflict with "Bundle-Version"
(a8ad7f5506)

### Description
- [LPS-67863]: Change dependency version override declarations in
`.version-override-${project.name}.properties` to follow a new format:
		[artifact group]/[artifact name]=[new version]
- [LPS-67863]: Fix commit deletion process of version override files.

## 1.2.35 - 2016-09-28

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.45.

## 1.2.36 - 2016-09-29

### Dependencies
- [LPS-58672]: Update the com.liferay.gradle.plugins dependency to version
2.0.46.

## 1.2.37 - 2016-09-30

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
2.0.47.

## 1.2.38 - 2016-10-01

### Commits
- [LPS-68448]: Automatically update the version in npm-shrinkwrap.json
(3984d2f302)
- [LPS-68448]: Check if the version in npm-shrinkwrap.json matches (679dcc87ad)
- [LPS-68448]: Simplify and assume package.json is in the root project dir
(80be24045a)

### Description
- [LPS-68448]: Fail the build of an OSGi project if the version in the
`npm-shrinkwrap.json` file does not match the project version.
- [LPS-68448]: The task `updateVersion` of OSGi and theme projects updates the
version in the `npm-shrinkwrap.json` file, if present.

## 1.2.39 - 2016-10-03

### Commits
- [LPS-68402]: Force the ShrinkWrap Maven Resolver to stay offline (ff464edb65)

### Dependencies
- [LPS-68485]: Update the com.liferay.gradle.plugins dependency to version
2.0.48.

### Description
- [LPS-68402]: Set the [`org.apache.maven.offline`]
(https://github.com/shrinkwrap/resolver#system-properties) system property to
`true` for the `testIntegration` task.

## 1.2.40 - 2016-10-04

### Commits
- [LPS-68506]: Exclude from "appJavadoc" if artifact.properties is missing
(f1b31b7846)

### Dependencies
- [LPS-68506]: Update the com.liferay.gradle.plugins dependency to version
2.0.49.
- [LPS-68506]: Update the com.liferay.gradle.plugins.app.javadoc.builder
dependency to version 1.1.0.

### Description
- [LPS-68506]: Exclude unpublished projects from the API documentation generated
by the `appJavadoc` task.

## 1.2.41 - 2016-10-05

### Commits
- [LPS-68540]: Fail "uploadArchives" if ".lfrbuild-missing-resources-importer"
exists (3d6c690efe)

### Description
- [LPS-68540]: Fail the `uploadArchives` task execution if the project directory
contains the marker file `.lfrbuild-missing-resources-importer`.

## 1.2.42 - 2016-10-05

### Dependencies
- [LPS-68334]: Update the com.liferay.gradle.plugins dependency to version
2.0.50.

### Description
- [LPS-66396]: Exclude specific project types from the build by setting the
following system properties to `true`:
	- `build.exclude.ant.plugin` to exclude all projects that contain a
	`build.xml` file from the build.
	- `build.exclude.module` to exclude all projects that contain a `bnd.bnd`
	file from the build.
	- `build.exclude.theme` to exclude all projects that contain a `gulpfile.js`
	file from the build.
- [LPS-66396]: The `modules.only.build` system property is no longer available.

## 1.2.43 - 2016-10-05

### Commits
- [LPS-66396]: Add more flags to exclude specific project types (2b9e02e942)

## 1.2.44 - 2016-10-06

### Commits
- [LPS-67573]: Fix wrong import (71217304bc)

### Dependencies
- [LPS-67573]: Update the com.liferay.gradle.plugins dependency to version
3.0.0.

### Description
- [LPS-66396]: Update import in several classes from
`java.io.UncheckedIOException` to `org.gradle.api.UncheckedIOException` to
remove Java 8 dependency.

## 1.2.45 - 2016-10-06

### Dependencies
- [LPS-68415]: Update the com.liferay.gradle.plugins dependency to version
3.0.1.

## 1.2.46 - 2016-10-06

### Commits
- [LPS-68564]: Always exclude "fsevents" from our "npm-shrinkwrap.json" files
(5cf81f9b51)

### Dependencies
- [LPS-68564]: Update the com.liferay.gradle.plugins dependency to version
3.0.2.

### Description
- [LPS-68564]: Bypass https://github.com/npm/npm/issues/14042 and always exclude
the `fsevents` dependency from the generated `npm-shrinkwrap.json` files.

## 1.2.47 - 2016-10-07

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.3.

## 1.2.48 - 2016-10-07

### Commits
- [LRDOCS-3023]: Add default repositories (f44f053812)
- [LRDOCS-3023]: Apply the AppTLDDocBuilder plugin on all apps (fdaebbb683)

### Dependencies
- [LRDOCS-3023]: Update the com.liferay.gradle.plugins dependency to version
3.0.4.

### Description
- [LRDOCS-3023]: The `com.liferay.app.defaults.plugin` now automatically adds
the local Maven and [Liferay CDN] repositories to the project.
- [LRDOCS-3023]: The `com.liferay.app.defaults.plugin` now automatically applies
the `com.liferay.app.tlddoc.builder` plugin.

## 1.2.49 - 2016-10-10

### Commits
- [LRDOCS-2594]: Apply Liferay stylesheet to "javadoc" tasks (da640c11af)
- [LRDOCS-2594]: Apply Liferay stylesheet to "appJavadoc" tasks (aa1445a3ad)

### Dependencies
- [LRDOCS-2594]: Update the com.liferay.gradle.plugins dependency to version
3.0.5.

### Description
- [LRDOCS-2594]: Apply the [Liferay stylesheet]
(https://github.com/liferay/liferay-portal/blob/master/tools/styles/javadoc.css)
file, if found, to the API documentation generated by the `appJavadoc` and
`javadoc` tasks.

## 1.2.50 - 2016-10-10

### Dependencies
- [LPS-68618]: Update the com.liferay.gradle.plugins dependency to version
3.0.6.

## 1.2.51 - 2016-10-11

### Dependencies
- [LPS-68598]: Update the com.liferay.gradle.plugins dependency to version
3.0.7.

## 1.2.52 - 2016-10-11

### Commits
- [LPS-68650]: Update npm-shrinkwrap.json and package.json after "baseline"
(7e3dfa8982)

### Description
- [LPS-68650]: Automatically update the versions in `npm-shrinkwrap.json` and
`package.json` files after running the `baseline` task.

## 1.2.53 - 2016-10-11

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.8.

## 1.2.54 - 2016-10-12

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.9.

## 1.2.55 - 2016-10-12

### Commits
- [LPS-68666]: Force private subprojects evaluation (125e23d7d3)
- [LPS-68666]: Include private subprojects in "appTLDDoc" (6f394b4165)
- [LPS-68666]: Include private subprojects in "appJavadoc" (54bbc7e26c)
- [LPS-68666]: Read the app.properties of the "private" counterpart if found
(7cb6c8755f)
- [LPS-68666]: Always use Gradle util methods (44a796ab73)

### Dependencies
- [LPS-68666]: Update the com.liferay.gradle.plugins dependency to version
3.0.10.
- [LPS-68666]: Update the com.liferay.gradle.plugins.app.javadoc.builder
dependency to version 1.2.0.

### Description
- [LPS-68666]: Include the subprojects of the private counterpart, if found, in
the API and tag library documentations of an app.
- [LPS-68666]: Set the `title` property of the `appJavadoc` task based on the
`app.properties` file of the private counterpart, if found. This way, the API
documentation of the app, when generated from a private branch, will display the
latest published version of the private app.

## 1.2.56 - 2016-10-13

### Commits
- [LRDOCS-3038]: Match Javadoc title logic with "appJavadoc.groupNameClosure"
(0f60fcbf92)

### Dependencies
- [LRDOCS-3038]: Update the com.liferay.gradle.plugins dependency to version
3.0.11.

### Description
- [LRDOCS-3038]: Include the bundle symbolic name in the API documentation title
generated by the `javadoc` task.

## 1.2.57 - 2016-10-13

### Commits
- [LPS-68448]: disable temporarily because it conflicts with "gradlew baseline
-PsyncRelease" (3dead77acd)

### Description
- [LPS-68448]: Temporarily disable `npm-shrinkwrap.json` version check.

## 1.2.58 - 2016-10-13

### Commits
- [LPS-68448]: disable temporarily because it conflicts with "gradlew baseline
-PsyncRelease" (2e28322db1)

### Description
- [LPS-68448]: Temporarily disable `package.json` version check.

## 1.2.59 - 2016-10-13

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.12.

## 1.2.60 - 2016-10-17

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.13.

## 1.2.61 - 2016-10-17

### Dependencies
- [LPS-68779]: Update the com.liferay.gradle.plugins dependency to version
3.0.14.

### Description
- [LPS-68772]: Allow module dependencies to be overridden with project
dependencies in the `.version-override-${project.name}.properties` file:
		[artifact group]/[artifact name]=[project path]

## 1.2.62 - 2016-10-17

### Commits
- [LPS-68772]: Add comments (b2bc9bb049)
- [LPS-68772]: Force dependency projects evaluation (1d5c03a04e)
- [LPS-68772]: Allow to replace module with project dependencies (dbf81ac02a)

## 1.2.63 - 2016-10-18

### Commits
- [LPS-68817]: implement build profiles (462204651a)

### Dependencies
- [LPS-68817]: Update the com.liferay.gradle.plugins dependency to version
3.0.15.

### Description
- [LPS-68817]: Set the system property `build.profile` to only include projects
containing a `.lfrbuild-portal-${build.profile}` marker file.
- [LPS-68817]: The system property `portal.build` is no longer available; use
the `-Dbuild.profile=portal` parameter instead.
- [LPS-68817]: The system property `portal.pre.build` is no longer available;
use the `-Dbuild.profile=portal-pre` parameter instead.

## 1.2.64 - 2016-10-18

### Dependencies
- [LPS-68779]: Update the com.liferay.gradle.plugins dependency to version
3.0.16.

## 1.2.65 - 2016-10-19

### Commits
- [LPS-68448]: Change version in JS files based on the version override
(9dc7b4f396)
- [LPS-68448]: Revert "LPS-68448 disable temporarily because it conflicts with
"gradlew baseline -PsyncRelease"" (4bfb49c73a)
- [LPS-68448]: Revert "LPS-68448 disable temporarily because it conflicts with
"gradlew baseline -PsyncRelease"" (1281edbc51)

### Description
- [LPS-68448]: If the version override file of the project declares a different
version, update the `npm-shrinkwrap.json` and `package.json` files accordingly.
- [LPS-68448]: Restore `npm-shrinkwrap.json` and `package.json` version checks.

## 1.2.66 - 2016-10-19

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.17.

## 1.2.67 - 2016-10-20

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.18.

## 1.2.68 - 2016-10-20

### Dependencies
- [LPS-67434]: Update the com.liferay.gradle.plugins dependency to version
3.0.19.

## 1.2.69 - 2016-10-20

### Dependencies
- [LPS-68839]: Update the com.liferay.gradle.plugins dependency to version
3.0.20.

## 1.2.70 - 2016-10-21

### Dependencies
- [LPS-68838]: Update the com.liferay.gradle.plugins dependency to version
3.0.21.

## 1.2.71 - 2016-10-21

### Dependencies
- [LPS-66906]: Update the com.liferay.gradle.plugins dependency to version
3.0.22.

## 1.2.72 - 2016-10-24

### Dependencies
- [LPS-68917]: Update the com.liferay.gradle.plugins dependency to version
3.0.23.

## 1.2.73 - 2016-10-25

### Dependencies
- [LPS-52675]: Update the com.liferay.gradle.plugins dependency to version
3.0.25.
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.24.

## 1.2.74 - 2016-10-25

### Commits
- [LPS-68935]: Pass the project description to Maven when publishing
(3cae2bdd09)

### Description
- [LPS-68935]: Set the Maven description to the project description when
publishing.

## 1.2.75 - 2016-10-26

### Dependencies
- [LPS-68917]: Update the com.liferay.gradle.plugins dependency to version
3.0.26.

## 1.2.76 - 2016-10-27

### Dependencies
- [LPS-68980]: Update the com.liferay.gradle.plugins dependency to version
3.0.27.

## 1.2.77 - 2016-10-28

### Dependencies
- [LPS-66222]: Update the com.liferay.gradle.plugins dependency to version
3.0.28.

## 1.2.78 - 2016-10-31

### Dependencies
- [LPS-69013]: Update the com.liferay.gradle.plugins dependency to version
3.0.29.

## 1.2.79 - 2016-10-31

### Dependencies
- [LPS-69013]: Update the com.liferay.gradle.plugins dependency to version
3.0.30.

## 1.2.80 - 2016-11-01

### Dependencies
- [LPS-68923]: Update the com.liferay.gradle.plugins dependency to version
3.0.31.

## 1.2.81 - 2016-11-01

### Dependencies
- [LPS-69026]: Update the com.liferay.gradle.plugins dependency to version
3.0.32.

## 1.2.82 - 2016-11-02

### Dependencies
- [LPS-68923]: Update the com.liferay.gradle.plugins dependency to version
3.0.33.

## 1.2.83 - 2016-11-03

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.34.

## 1.2.84 - 2016-11-03

### Dependencies
- [LPS-68298]: Update the com.liferay.gradle.plugins dependency to version
3.0.35.

## 1.2.85 - 2016-11-04

### Dependencies
- [LPS-68298]: Update the com.liferay.gradle.plugins dependency to version
3.0.36.

## 1.2.86 - 2016-11-17

### Commits
- [LPS-66762]: This is an internal method, move it to an internal util class
(282c4eb687)
- [LPS-66762]: Remove deprecated methods (8924f0f52a)
- [LPS-67573]: Make methods private to reduce API surface (5b782a2933)
- [LPS-66906]: Avoid using deprecated method (f483d9bf82)
- [LPS-66762]: Apply Baseline plugin (97faf6c12a)
- [LPS-66762]: Move task class (976e90605b)

### Dependencies
- [LPS-66762]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.0.0.

## 2.0.0 - 2016-11-17

### Dependencies
- [LPS-66762]: Update the com.liferay.gradle.plugins dependency to version
3.0.37.

### Description
- [LPS-66762]: Apply [Liferay Gradle Plugins Baseline].
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-66762]: The `BaselineTask` class is no longer available. Use the class in
[Liferay Gradle Plugins Baseline] instead.
- [LPS-67573]: Remove all deprecated methods.

## 2.0.1 - 2016-11-21

### Commits
- [LPS-69288]: Add property to override the app version (0f2cf96a47)
- [LPS-69288]: Add property to override the app title (f3f25a59b7)
- [LPS-69288]: Add property to override the app description (42eb6b0845)

### Dependencies
- [LPS-69248]: Update the com.liferay.gradle.plugins dependency to version
3.0.38.

### Description
- [LPS-69288]: Set the `app.description` project property to override the
project description if the `com.liferay.app.defaults.plugin` is applied.
- [LPS-69288]: Set the `app.title` project property to override the title used
in the `appJavadoc` task.
- [LPS-69288]: Set the `app.version` project property to override the project
version if the `com.liferay.app.defaults.plugin` is applied.

## 2.0.2 - 2016-11-22

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.39.

## 2.0.3 - 2016-11-23

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.40.

## 2.0.4 - 2016-11-24

### Dependencies
- [LPS-69271]: Update the com.liferay.gradle.plugins dependency to version
3.0.41.

## 2.0.5 - 2016-11-28

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.42.

## 2.0.6 - 2016-11-29

### Commits
- [LPS-67573]: Reuse variable (d01cc05f6e)
- [LPS-68813]: Pass the same classpath used to compile JSP Java files
(f11f2424cd)
- [LPS-68813]: The "unzipJar" task is always there, even in non *-web modules
(31613ff28a)
- [LPS-68813]: Update FindSecurityBugs version with "updateFileVersions" task
(333143501d)
- [LPS-68813]: Only *-web modules has unzipped-jar, from other modules we need to
take classes dir (57478a9dfd)
- [LPS-68813]: Also output and include derived summaries to avoid computing again
(2869bc12cf)
- [LPS-68813]: Always include liferay-config/liferay.txt (1b104d322b)
- [LPS-68813]: Filter only security category (ac1c59612e)
- [LPS-68813]: Add "FindSecurityBugs" task (59884c383a)
- [LPS-68813]: Increase heap size for FindBugs (114393f638)

### Dependencies
- [LPS-69445]: Update the com.liferay.gradle.plugins dependency to version
3.0.43.

### Description
- [LPS-68813]: Add the `findSecurityBugs` task to check for security problems.

## 2.0.7 - 2016-11-30

### Commits
- [LPS-69470]: Force calculated Bundle-Version for Liferay modules (9213512800)

### Dependencies
- [LPS-69470]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.0.

## 2.0.8 - 2016-12-01

### Commits
- [LPS-69470]: Revert "LPS-69470 Force calculated Bundle-Version for Liferay
modules" (fd37c69959)
- [LPS-69488]: Move Node.js default version configuration to gradle-plugins
(89d1af444b)

### Dependencies
- [LPS-69488]: Update the com.liferay.gradle.plugins dependency to version
3.0.44.

### Description
- [LPS-69488]: Remove the default Node.js version configuration since it has
been moved to [Liferay Gradle Plugins].

## 2.0.9 - 2016-12-01

### Dependencies
- [LPS-69492]: Update the com.liferay.gradle.plugins dependency to version
3.0.45.

## 2.0.10 - 2016-12-03

### Commits
- [LPS-68289]: Add module agent support for aspect via Gradle plugin
(c5b19a285b)

## 2.1.0 - 2016-12-03

### Dependencies
- [LPS-68289]: Update the com.liferay.gradle.plugins dependency to version
3.0.46.

### Description
- [LPS-68289]: Add module agent support for aspect in the `test` and
`testIntegration` tasks.

## 2.1.1 - 2016-12-05

### Dependencies
- [LPS-69501]: Update the com.liferay.gradle.plugins dependency to version
3.0.47.

## 2.2.0 - 2016-12-08

### Dependencies
- [LPS-63943]: Update the com.liferay.gradle.plugins dependency to version
3.0.48.

### Description
- [LPS-63943]: Add the ability to create additional *prep next* commits via
`PrintArtifactPublishCommands` tasks.
- [LPS-63943]: Commit themes' `.digest` files in a separate *prep next* commit.

## 2.2.1 - 2016-12-08

### Dependencies
- [LPS-69501]: Update the com.liferay.gradle.plugins dependency to version
3.0.49.

## 2.2.2 - 2016-12-12

### Commits
- [LPS-69606]: Keep "buildWSDD" enabled if we already have WSDD files
(3ccfae3bcc)
- [LPS-69606]: No need to run "buildWSDDJar" if "buildWSDD" is disabled
(3818fd0c29)
- [LPS-69606]: Add WSDD to the artifact publish commands (797e6a91b5)
- [LPS-69606]: Publish the WSDD jar only if module has remote services
(a2a8478c52)
- [LPS-69606]: Publish the WSDD jar (b0cc193245)
- [LPS-69501]: Skip if the SDK directory can't be found (b0bf2cb1ae)
- [LPS-69501]: Use the SF deployed jars in tools/sdk/dependencies (524f50702c)

### Description
- [LPS-69606]: Publish the WSDD fragment JAR of an OSGi project with the
`install` and `uploadArchives` tasks.
- [LPS-69501]: Use the [Liferay Source Formatter] JAR files deployed locally in
the `tools/sdk/dependencies/com.liferay.source.formatter/lib` directory, if
found.

## 2.2.3 - 2016-12-14

### Dependencies
- [LPS-69677]: Update the com.liferay.gradle.plugins dependency to version
3.0.50.

## 2.2.4 - 2016-12-14

### Commits
- [LPS-67694]: Don't publish plugins or themes if "snapshotIfStale" is set
(e0d63d2d99)
- [LPS-67694]: Don't publish snapshots of test projects when stale (af74495fa6)

### Dependencies
- [LPS-67694]: Update the com.liferay.gradle.plugins dependency to version
3.0.51.

### Description
- [LPS-67694]: Disable the `install` and `uploadArchives` tasks for `*-test`
OSGi projects, Ant plugins and themes if the `-PsnapshotIfStale` argument is
provided.

## 2.2.5 - 2016-12-15

### Commits
- [LPS-60606]: Bypass SF error (44c1929a0f)
- [LPS-69606]: Fix artifact URL generation (7dc9b8361b)
- [LPS-69606]: Allow to defer key evaluation (76a66dbd07)
- [LPS-69606]: Add all .wsdd files to the same commit (ae41e19f0e)

## 2.3.0 - 2016-12-15

### Description
- [LPS-69606]: Fix artifact publish commands for the "WSDD" commit in older
versions of Git.
- [LPS-69606]: Fix artifact URL generation in the `artifact.properties` files.

## 2.3.1 - 2016-12-18

### Dependencies
- [LPS-67688]: Update the com.liferay.gradle.plugins dependency to version
3.0.52.

## 2.3.2 - 2016-12-19

### Commits
- [LPS-69730]: Recommit (a4ace881cf)
- [LPS-69730]: Revert (1182979b8c)

### Dependencies
- [LPS-69730]: Update the com.liferay.gradle.plugins dependency to version
3.0.57.
- [LPS-69730]: Update the com.liferay.gradle.plugins dependency to version
3.0.52.
- [LPS-69730]: Update the com.liferay.gradle.plugins dependency to version
3.0.57.
- [LPS-69730]: Update the com.liferay.gradle.plugins dependency to version
3.0.54.
- [LPS-69730]: Update the com.liferay.gradle.plugins dependency to version
3.0.53.

## 2.3.7 - 2016-12-19

### Dependencies
- [LPS-69730]: Update the com.liferay.gradle.plugins dependency to version
3.0.58.

## 2.3.8 - 2016-12-20

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.59.

## 2.3.9 - 2016-12-21

### Dependencies
- [LPS-69802]: Update the com.liferay.gradle.plugins dependency to version
3.0.60.

## 2.3.10 - 2016-12-21

### Dependencies
- [LPS-69838]: Update the com.liferay.gradle.plugins dependency to version
3.0.61.

## 2.3.11 - 2016-12-27

### Commits
- [LPS-69847]: Apply Dependency Checker plugin (a83ed2e495)

### Dependencies
- [LPS-69847]: Update the com.liferay.gradle.plugins.dependency.checker
dependency to version 1.0.0.

### Description
- [LPS-69847]: Apply the [Liferay Gradle Plugins Dependency Checker] to throw an
error if the [Liferay Source Formatter] version in use is not the latest one
and is older than 30 days.

## 2.3.12 - 2016-12-29

### Commits
- [LPS-69453]: Reuse reference (8c02b80cfb)
- [LPS-69453]: Add an empty "deployDependencies" task (e0f545c3de)
- [LPS-61987]: Fail the build if a configuration has a "-SNAPSHOT" dependency
(60883e9dce)
- [LPS-69847]: Add option to ignore local portal tool jars (c199be9a55)

## 2.4.0 - 2016-12-29

### Description
- [LPS-61987]: Enforce the use of snapshot timestamp versions for dependencies.
- [LPS-69453]: Add an empty `deployDependencies` task to copy additional
dependency JAR files to the `deploy` directory.
- [LPS-69847]: Add the ability to ignore the locally deployed JAR files of a
portal tool by setting the project property `[portal tool name].ignore.local`.

## 2.4.1 - 2016-12-29

### Dependencies
- [LPS-69453]: Update the com.liferay.gradle.plugins dependency to version
3.0.62.

## 2.4.2 - 2016-12-29

### Dependencies
- [LPS-69920]: Update the com.liferay.gradle.plugins dependency to version
3.0.63.

## 2.4.3 - 2016-12-29

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.64.

## 2.4.4 - 2017-01-02

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.65.

## 2.4.5 - 2017-01-03

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.0.67.

## 2.4.6 - 2017-01-03

### Commits
- [LPS-69719]: Exclude .java files from "archetype-resources" dirs in Eclipse
(23e612e990)

### Description
- [LPS-69719]: Exclude `**/archetype-resources/**.java` files from the
`.classpath` file to avoid compilation errors in Eclipse.

## 2.4.7 - 2017-01-04

### Commits
- [LPS-61987]: Fix timestamp version enforcement when "-Psnapshot" is used
(7db38f16ff)

### Description
- [LPS-61987] Fix snapshot timestamp version enforcement when `-Psnapshot` is
used.

## 2.4.8 - 2017-01-04

### Commits
- [LPS-69606]: Prevent crashing if module does not have WSDD files in subdirs
(3af681b856)

### Dependencies
- [LPS-69899]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.1.

### Description
- [LPS-69606]: Fix artifact publish commands for the *WSDD* commit in older
versions of Git.

## 2.4.9 - 2017-01-06

### Commits
- [LPS-65179]: Avoid committing if there are unstaged changes (2c13f00288)

### Dependencies
- [LPS-69706]: Update the com.liferay.gradle.plugins dependency to version
3.0.68.

### Description
- [LPS-65179]: Fix artifact publish commands in case of local unstaged changes.

## 2.4.10 - 2017-01-09

### Dependencies
- [LPS-69706]: Update the com.liferay.gradle.plugins dependency to version
3.0.69.

## 2.4.11 - 2017-01-10

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.0.70.

## 2.4.12 - 2017-01-10

### Dependencies
- [LPS-67573]: Update the com.liferay.gradle.plugins dependency to version
3.0.71.

## 2.4.13 - 2017-01-12

### Dependencies
- [LPS-70092]: Update the com.liferay.gradle.plugins dependency to version
3.1.0.

## 2.4.14 - 2017-01-13

### Commits
- [LPS-70146]: Add task to print project dir if it has project dependencies
(6150f8d73c)
- [LPS-70146]: Inline method (a8e9c92f07)
- [LPS-70146]: Extract logic (905d6fca5c)

### Dependencies
- [LPS-70036]: Update the com.liferay.gradle.plugins dependency to version
3.1.1.

### Description
- [LPS-70146]: Add the `printDependentArtifact` task to print the project
directory if the project contains dependencies to other projects.

## 2.4.15 - 2017-01-17

### Commits
- [LPS-70170]: Use deployed JARs as JSP compilation dependencies (d087544dca)
- [LPS-70170]: The JSPC plugin is always applied (6f23afb630)
- [LPS-70170]: Force "default" versions during JSP compilation (67571b11e7)

### Description
- [LPS-70170]: Use the deployed JAR file of Liferay taglib dependencies for JSP
compilation.
- [LPS-70170]: Use the latest snapshot of the `com.liferay.util.taglib`
dependency for JSP compilation.

## 2.4.16 - 2017-01-20

### Commits
- [LPS-69501]: Use the published portal tools by default (3c926cfc74)

### Description
- [LPS-69501]: Use the published portal tools by default instead of the ones
deployed locally in the `tools/sdk/dependencies` directories.

## 2.4.17 - 2017-01-26

### Commits
- [LPS-70286]: Change appJavadoc title (31b5d9702a)
- [LPS-70282]: Apply the "application" plugin if bnd.bnd has a Main-Class
(683d4fb5dc)

### Dependencies
- [LPS-70286]: Update the com.liferay.gradle.plugins dependency to version
3.1.2.

### Description
- [LPS-70282]: Automatically apply the `application` plugin if the `bnd.bnd`
file contains a `Main-Class` header.
- [LPS-70286]: Change the `appJavadoc` task's generated module headings (e.g.,
*Liferay Journal API - com.liferay:com.liferay.journal:2.0.0*).

## 2.4.18 - 2017-01-27

### Commits
- [LPS-69926]: Make "compileInclude" dependencies available to tests
(ddd4b9a66e)
- [LPS-69926]: Make the "compileInclude" configuration non-transitive
(5a7b783e67)

### Description
- [LPS-69926]: Make dependencies in the `compileInclude` configuration
non-transitive by default.
- [LPS-69926]: Make the `testCompile` configuration extend from the
`compileInclude` configuration.

## 2.4.19 - 2017-01-29

### Commits
- [LPS-70335]: Skip "updateFileVersions" replacement in read-only repos
(73622e68ae)
- [LPS-70335]: Pass the current file to the "replaceOnlyIf" closures
(a42458c808)
- [LPS-70335]: Extract constants (33a9b37a27)
- [LPS-70335]: Reuse Git repo information (c822ab26ac)
- [LPS-70335]: Extract Git repo information for reuse (3a461225df)

### Dependencies
- [LPS-70335]: Update the com.liferay.gradle.plugins dependency to version
3.1.3.

### Description
- [LPS-70335]: Skip replacements of the `updateFileVersions` task in read-only
sub-repositories.

## 2.4.20 - 2017-01-30

### Commits
- [LPS-70335]: Fix regression caused by 3a461225dfa944236a2c73991a93d3513f1668dc
(125580f685)

### Description
- [LPS-70335]: Fix location of version override files.

## 2.4.21 - 2017-01-30

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.1.4.

## 2.4.22 - 2017-01-31

### Commits
- [LPS-69606]: WSDD files are not committed anymore (94d342786b)
- [LPS-69606]: Generate WSDD files in a temporary directory (2c55bd1cb8)

### Dependencies
- [LPS-69606]: Update the com.liferay.gradle.plugins dependency to version
3.1.5.
- [LPS-70379]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.2.

### Description
- [LPS-69606]: Generate WSDD files in a temporary directory.

## 2.4.23 - 2017-02-01

### Commits
- [LPS-69926]: Move "compileInclude" at the beginning of the test classpath
(11415c1cd2)

### Description
- [LPS-69926]: Move the `compileInclude` dependencies at the beginning of the
test compile and runtime classpaths.

## 2.4.24 - 2017-02-02

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.1.6.

## 2.4.25 - 2017-02-03

### Commits
- [LPS-70424]: Read "project.group" from gradle.properties (9a32dfa455)

### Dependencies
- [LPS-70424]: Update the com.liferay.gradle.plugins dependency to version
3.1.7.

### Description
- [LPS-70424]: Add the ability to set a custom value for the project group by
setting the property `project.group` in a `gradle.properties` file located in
any parent directory of the project.

## 2.4.26 - 2017-02-04

### Commits
- [LPS-69926]: Don't expand "compileInclude" dependencies by default
(f69b2326e5)

### Description
- [LPS-69926]: Set the `liferayOSGi.expandCompileInclude` property to `false` by
default.

## 2.4.27 - 2017-02-08

### Commits
- [LPS-70486]: "compileClasspath" already extends from both configurations
(64fc89988b)
- [LPS-70486]: Make the "compileClasspath" configuration non-transitive
(84ca04b4c5)

### Dependencies
- [LPS-70486]: Update the com.liferay.gradle.plugins dependency to version
3.1.8.

### Description
- [LPS-70486]: Make the `compileClasspath` configuration non-transitive for
Liferay apps.
- [LPS-70486]: Use the `compileClasspath` configuration in the compile and
runtime classpaths of the `test` and `testIntegration` source sets.

## 2.4.28 - 2017-02-09

### Commits
- [LPS-69920]: Add option to remove the "node_modules" dir during "clean"
(c3b5587e79)

### Dependencies
- [LPS-69920]: Update the com.liferay.gradle.plugins dependency to version
3.1.9.

### Description
- [LPS-69920]: Set the system property `clean.node.modules` to `true` to delete
the `node_modules` directory of a project with the `clean` task.

## 2.4.29 - 2017-02-09

### Commits
- [LPS-69926]: Revert "LPS-69926 Don't expand "compileInclude" dependencies by
default" (5a43881bc4)

### Dependencies
- [LPS-69926]: Update the com.liferay.gradle.plugins dependency to version
3.2.0.

## 2.4.30 - 2017-02-12

### Commits
- [LPS-70584]: Do not load external DTD (bfd8e44ac4)

### Dependencies
- [LPS-70584]: Update the com.liferay.gradle.plugins dependency to version
3.2.1.

### Description
- [LPS-70584]: Avoid internet connection requirement when parsing `service.xml`
files.

## 2.4.31 - 2017-02-12

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.2.2.

## 2.4.32 - 2017-02-13

### Dependencies
- [LPS-69139]: Update the com.liferay.gradle.plugins dependency to version
3.2.3.

## 2.4.33 - 2017-02-14

### Commits
- [LPS-67863]: Test projects are never published, exclude from "syncRelease"
(826d7fe0de)

### Dependencies
- [LPS-70494]: Update the com.liferay.gradle.plugins dependency to version
3.2.4.

### Description
- [LPS-67863]: Exclude test projects from the `gradlew baseline -PsyncRelease`
process.

## 2.4.34 - 2017-02-16

### Commits
- [LPS-70677]: Not needed anymore (3caf184522)
- [LPS-70699]: Skip update check when building themes via Gulp (bac633703e)

### Dependencies
- [LPS-70677]: Update the com.liferay.gradle.plugins dependency to version
3.2.5.

### Description
- [LPS-70677]: Remove exclusion of `com.liferay.portal` transitive dependencies
from the `jspCTool` configuration's `com.liferay.jasper.jspc` default
dependency.
- [LPS-70699]: Disable the update check when building themes.

## 2.4.35 - 2017-02-17

### Dependencies
- [LPS-70707]: Update the com.liferay.gradle.plugins dependency to version
3.2.6.

## 2.4.36 - 2017-02-21

### Commits
- [LPS-70170]: Print out util-taglib dependency replacement in "jspC"
(caaef782ab)
- [LPS-70170]: Print out dependency replacement in "jspC" configuration
(85f6994830)

### Description
- [LPS-70170]: Print out implicit dependency replacements in the `jspC`
configuration.

## 2.4.37 - 2017-02-22

### Commits
- [LPS-70819]: Publish jar with compiled JSPs of a module (4ae8b0c06e)

## 2.5.0 - 2017-02-22

### Commits
- [LPS-70819]: call it jspc (5fa6c83759)

### Dependencies
- [LPS-70819]: Update the com.liferay.gradle.plugins dependency to version
3.2.7.

### Description
- [LPS-70819]: Publish a JAR file with the compiled JSP classes of an OSGi
project with the `install` and `uploadArchives` tasks.

## 2.5.1 - 2017-02-23

### Commits
- [LPS-63943]: Print out "gradlew baseline" command for publishable artifacts
(b9a6e67871)
- [LPS-63943]: All "writeArtifactPublishCommands" write in the same files
(1213547948)
- [LPS-63943]: Write publish artifacts commands in a file instead (db48ccb4da)
- [LPS-63943]: Extract method (e25d9024e2)
- [LPS-63943]: Rename (1f83e447c2)

## 3.0.0 - 2017-02-23

### Commits
- [LPS-63943]: Write file in 3 separate groups (80e5fc77ce)
- [LPS-63943]: Print out commented baseline commands (1c0a83dcbc)
- [LPS-63943]: Create the clean/merge tasks in the current dir project
(dc6b375d2b)
- [LPS-63943]: Merge artifacts publish commands files (e32daf30b6)
- [LPS-63943]: Delete all scripts first (1dee4d1f73)

### Description
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

## 3.0.1 - 2017-02-23

### Commits
- [LPS-70819]: Download compiled JSP instead (6efd590352)
- [LPS-69920]: Fix compile in gradle-plugins-defaults (1e5ec3d762)

## 3.1.0 - 2017-02-23

### Dependencies
- [LPS-70819]: Update the com.liferay.gradle.plugins dependency to version
3.2.8.

### Description
- [LPS-70819]: Set the `jsp.precompile.from.source` project property to `false`
to make the `compileJSP` task download the archive listed in the
`artifact.jspc.url` artifact property instead of compiling the JSP pages of the
OSGi project.

## 3.1.1 - 2017-02-24

### Commits
- [LPS-70170]: Force dependent taglib projects evaluation (d979136da3)
- [LPS-70170]: Find only leaf projects (1ea22fa07d)
- [LPS-70170]: Change taglib modules resolution during "compileJSP" (17c8aff166)

### Description
- [LPS-70170]: Change dependency replacements in the `jspC` configuration:
	- always use the deployed `util-taglib.jar` file; fail if it's not found.
	- substitute module taglib dependencies with project dependencies if they're
	found, falling back to the deployed JAR file; fail if neither the project nor
	the deployed JAR file are found.

## 3.1.2 - 2017-02-25

### Dependencies
- [LPS-66853]: Update the com.liferay.gradle.plugins dependency to version
3.2.9.

## 3.1.3 - 2017-02-27

### Commits
- [LPS-70170]: Hide log if not explicitly compiling JSP (ce2aa0f12a)

### Description
- [LPS-70170]: Lower the log level of `jspC` configuration dependency
replacement messages if the `compileJSP` task is not explicitly invoked.

## 3.1.4 - 2017-02-28

### Commits
- [LPS-70929]: Bnd check fails when using "-exportcontents" on non-OSGi jars
(c53533fade)

### Dependencies
- [LPS-70929]: Update the com.liferay.gradle.plugins dependency to version
3.2.10.

### Description
- [LPS-70929]: Disable `-check: exports` if the `bnd.bnd` file contains the
`-exportcontents` instruction.

## 3.1.5 - 2017-03-01

### Commits
- [LPS-63943]: Make artifact publish commands script executable (49d3c5329b)
- [LPS-63943]: Fail artifact publish commands script in case of error
(9fff0eaf80)
- [LPS-63943]: Add properties to set header and footer while merging
(197eecd01b)

## 3.2.0 - 2017-03-01

### Dependencies
- [LPS-63943]: Update the com.liferay.gradle.plugins dependency to version
3.2.11.

### Description
- [LPS-63943]: Add Bash shebang and `set -e` at the beginning of the file
generated by the `mergeArtifactsPublishCommands` task.
- [LPS-63943]: Add the ability to set a header and/or a footer in the output
file generated by `MergeFilesTask` instances.
- [LPS-63943]: Make the file generated by the `mergeArtifactsPublishCommands`
task executable.

## 3.2.1 - 2017-03-02

### Dependencies
- [LPS-62970]: Update the com.liferay.gradle.plugins dependency to version
3.2.12.

## 3.2.2 - 2017-03-02

### Commits
- [LPS-67039]: Allow to call PMD from a subrepo (if using central's gradlew)
(b3274aa8e2)

### Dependencies
- [LPS-71005]: Update the com.liferay.gradle.plugins dependency to version
3.2.13.

### Description
- [LPS-67039]: Add the ability to invoke PMD from a sub-repository by using
`gradle.gradleUserHomeDir` as the root for the `standard-rules.xml` file path.

## 3.2.3 - 2017-03-03

### Dependencies
- [LPS-70282]: Update the com.liferay.gradle.plugins dependency to version
3.2.14.

## 3.2.4 - 2017-03-06

### Commits
- [LPS-70604]: Check dependencies before publishing "util" modules (b2db7c6393)

### Dependencies
- [LPS-70604]: Update the com.liferay.gradle.plugins dependency to version
3.2.15.

### Description
- [LPS-70604]: Check whether the `:util:` or `:private:util:` projects have
their dependencies published before enabling the `printArtifactPublishCommands`
task.

## 3.2.5 - 2017-03-08

### Commits
- [LPS-63943]: Print out artifacts publish commands file path (1f7e067345)
- [LPS-63943]: Use log placeholder (cc569b53ca)
- [LPS-63943]: Wordsmith (68d2fc7ba0)
- [LPS-63943]: If "merge" depends on "write" tasks, they'll always run
(d7286776d3)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)

### Dependencies
- [LPS-68405]: Update the com.liferay.gradle.plugins dependency to version
3.2.16.

### Description
- [LPS-63943]: Print the file path for the `writeArtifactPublishCommands` task's
resulting `.sh` file.
- [LPS-63943]: Avoid executing `writeArtifactPublishCommands` tasks if they're
not explicitly invoked.

## 3.2.6 - 2017-03-09

### Commits
- [LPS-70634]: Create the LARs before publish the theme to NPM (f81dd3fc52)
- [LPS-70634]: Rename (f7ceaf4433)
- [LPS-70634]: Automatically publish public themes to NPM (5b5a5470c9)
- [LPS-70634]: Rename (b50d83bf28)

## 3.3.0 - 2017-03-09

### Dependencies
- [LPS-70634]: Update the com.liferay.gradle.plugins dependency to version
3.2.17.

### Description
- [LPS-70634]: Automatically publish public themes to the NPM registry while
executing the `uploadArchives` task.

## 3.3.1 - 2017-03-09

### Dependencies
- [LPS-67688]: Update the com.liferay.gradle.plugins dependency to version
3.2.18.

## 3.3.2 - 2017-03-11

### Commits
- [LPS-71201]: Block publishing tasks on master if already released (62627f5617)

## 3.4.0 - 2017-03-11

### Dependencies
- [LPS-71201]: Update the com.liferay.gradle.plugins dependency to version
3.2.19.

### Description
- [LPS-71201]: Fail release tasks if the project is being published from the
master branch, but it was previously already published from a release branch.

## 3.4.1 - 2017-03-13

### Dependencies
- [LPS-71222]: Update the com.liferay.gradle.plugins dependency to version
3.2.20.

## 3.4.2 - 2017-03-14

### Commits
- [LPS-71224]: "artifact.url" must always point to the primary artifact
(3d0b4dc991)

### Description
- [LPS-71224]: Always point the `artifact.url` property of `artifact.properties`
to the primary artifact, even when the `application` plugin is applied.

## 3.4.3 - 2017-03-15

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.21.
- [LPS-71118]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.3.

## 3.4.4 - 2017-03-16

### Commits
- [LPS-71303]: Export package (b443b08a4a)
- [LPS-71303]: Add value to avoid problems when arguments come before task
(8b1d11f167)
- [LPS-71303]: Add option to skip the theme's local dependencies (1bfe51736f)
- [LPS-71264]: Find actual Maven repo local dir (93d5e58655)

## 3.5.0 - 2017-03-16

### Dependencies
- [LPS-71303]: Update the com.liferay.gradle.plugins dependency to version
3.2.22.

### Description
- [LPS-71303]: Set the `liferayThemeDefaults.useLocalDependencies` property to
`false` to avoid providing the `--css-common-path`, `--styled-path`, and
`--unstyled-path` arguments to the Gulp tasks. The dependencies declared in the
`package.json` are used instead.
- [LPS-71264]: Use the Maven local repository's actual directory as the
default value for the `InstallCacheTask`'s `mavenRootDir` property.

## 3.5.1 - 2017-03-17

### Commits
- [LPS-66891]: Wordsmith (3a8f765732)
- [LPS-66891]: Use logger (dd5916ebf5)
- [LPS-66891]: Defer read of "reporting.baseDir" (47621c3d35)
- [LPS-66891]: Update FindSecBugs definitions (f1e395e56f)

### Dependencies
- [LPS-66891]: Update the com.liferay.gradle.plugins dependency to version
3.2.23.

## 3.5.2 - 2017-03-17

### Dependencies
- [LPS-66891]: Update the com.liferay.gradle.plugins dependency to version
3.2.24.

## 3.5.3 - 2017-03-21

### Commits
- [LPS-71376]: Disable "uploadArchives" for "*-test" projects (44876c6c61)
- [LPS-70146]: Disable "printDependentArtifact" for "*-test" projects
(c8cd23fd7a)
- [LPS-63943]: Don't fail the build if there are no files to merge (74b92fdac2)

### Dependencies
- [LPS-71376]: Update the com.liferay.gradle.plugins dependency to version
3.2.25.

### Description
- [LPS-70146]: Disable the `printDependentArtifact` task for `*-test` projects.
- [LPS-71376]: Disable the `uploadArchives` task for `*-test` projects.
- [LPS-63943]: Avoid failing the build when running
`gradlew writeArtifactPublishCommands` from a directory that does not contain
any publishable subprojects.

## 3.5.4 - 2017-03-22

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.26.

### Description
- [LPS-71354]: Add the ability to set specific directories to include for
multi-project builds by setting the `build.include.dirs` system property.

## 3.5.5 - 2017-03-22

### Commits
- [LPS-71354]: Add option to include only projects from certain subtrees
(dedfaa1219)

## 3.5.6 - 2017-03-24

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.27.

## 3.5.7 - 2017-03-27

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.28.

## 3.5.8 - 2017-03-28

### Dependencies
- [LPS-71535]: Update the com.liferay.gradle.plugins dependency to version
3.2.29.
- [LPS-71535]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.4.

## 3.5.9 - 2017-03-30

### Commits
- [LPS-70819]: Avoid adding the JSP artifact if publishing a snapshot
(09e34a044e)
- [LPS-70819]: Extract property names array, so it can reused (54f4b85bda)
- [LPS-70819]: Extract util method (f9fa4f0e67)

### Dependencies
- [LPS-70819]: Update the com.liferay.gradle.plugins dependency to version
3.2.30.

### Description
- [LPS-70819]: Avoid publishing the JAR file with the compiled JSP classes of an
OSGi project snapshot with the `install` and `uploadArchives` tasks.

## 3.5.10 - 2017-04-03

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.31.

## 3.5.11 - 2017-04-03

### Dependencies
- [LPS-53392]: Update the com.liferay.gradle.plugins dependency to version
3.2.32.

## 3.5.12 - 2017-04-04

### Dependencies
- [LPS-53392]: Update the com.liferay.gradle.plugins dependency to version
3.2.33.

## 3.5.13 - 2017-04-04

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.34.

## 3.5.14 - 2017-04-05

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.35.

## 3.5.15 - 2017-04-06

### Dependencies
- [LPS-71591]: Update the com.liferay.gradle.plugins dependency to version
3.2.36.

## 3.5.16 - 2017-04-08

### Commits
- [LPS-71795]: Bypass https://github.com/gradle/gradle/issues/1094 (494bde9991)

### Dependencies
- [LPS-64098]: Update the com.liferay.gradle.plugins dependency to version
3.2.37.

### Description
- [LPS-71795]: Fix the [Gradle issue]
(https://github.com/gradle/gradle/issues/1094) that occurs when executing the
`findbugsMain` task on OSGi modules that include resource files.

## 3.5.17 - 2017-04-11

### Commits
- [LPS-71795]: Generalize
https://github.com/liferay/liferay-portal/commit/494bde999167d0c786519918b7a70bc5f76e93c3
for all "findbugs*" tasks (5c38e9bcdc)

### Dependencies
- [LPS-71826]: Update the com.liferay.gradle.plugins dependency to version
3.2.38.

### Description
- [LPS-71795]: Generalize the [Gradle issue]
(https://github.com/gradle/gradle/issues/1094) fix for all tasks of type
`FindBugs` related to source sets.

## 3.5.18 - 2017-04-12

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.2.39.

## 3.5.19 - 2017-04-14

### Commits
- [LPS-71901]: Avoid pointlessly calling a Callable multiple times (333624c458)
- [LPS-71901]: Add task to update dependencies to the latest snapshot version
(052d1b093e)

## 3.6.0 - 2017-04-14

### Dependencies
- [LPS-71901]: Update the com.liferay.gradle.plugins dependency to version
3.2.40.

### Description
- [LPS-71901]: Add the task `updateFileSnapshotVersions` to update the project
version in external files to the latest snapshot.

## 3.6.1 - 2017-04-17

### Dependencies
- [LPS-71686]: Update the com.liferay.gradle.plugins dependency to version
3.2.41.

## 3.6.2 - 2017-04-18

### Commits
- [LPS-71901]: Closure is also a callable, but we can't cache it (fab860d971)

### Dependencies
- [LPS-71925]: Update the com.liferay.gradle.plugins dependency to version
3.3.0.

### Description
- [LPS-71901]: Fix wrong caching logic in `ReplaceRegexTask`.

## 3.6.3 - 2017-04-19

### Dependencies
- [LPS-72039]: Update the com.liferay.gradle.plugins dependency to version
3.3.1.

## 3.6.4 - 2017-04-20

### Dependencies
- [LPS-72030]: Update the com.liferay.gradle.plugins dependency to version
3.3.2.

## 3.6.5 - 2017-04-21

### Commits
- [LPS-72067]: Don't hard-code dir names (04fa17a4d8)
- [LPS-72067]: Fix wrong logic (9371a7fae3)
- [LPS-72067]: Move logic to avoid a minor version increase (89e6196b63)
- [LPS-72067]: Pass an array of the right size (92403d3589)
- [LPS-72067]: Just append the condition (c7795f4bc9)
- [LPS-72067]: Sort (d516ab1b9c)
- [LPS-72067]: Update FindSecurityBugs JAR version to 1.6 (2f8293e2ad)
- [LPS-72067]: Do not run FindSecurityBugs if there is no input (d1eec604ff)
- [LPS-72067]: Use only classes dir for input to avoid compileInclude
dependencies (65883ad8cb)
- [LPS-72045]: Fail tests if there are undeployed dependent projects
(4c6a75591e)

### Dependencies
- [LPS-72067]: Update the com.liferay.gradle.plugins dependency to version
3.3.3.

### Description
- [LPS-72045]: When on Jenkins, fail the `testIntegration` task if any dependent
projects defined in the `testIntegrationCompile` configuration do not have the
`.lfrbuild-portal` marker file.
- [LPS-72067]: Avoid including `compileInclude` dependencies in the classpath of
[Find Security Bugs].
- [LPS-72067]: Avoid running the `findSecurityBugs` task if the classpath does
not contain a class or JAR file.

## 3.6.6 - 2017-04-21

### Dependencies
- [LPS-71722]: Update the com.liferay.gradle.plugins dependency to version
3.3.4.

## 3.6.7 - 2017-04-25

### Commits
- [LPS-70819]: Try to download via Gradle if URL is protected (b59714ac00)

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.3.5.

### Description
- [LPS-70819]: Use Gradle dependency management in the `downloadCompiledJSP`
task if the URL is protected.

## 3.6.8 - 2017-04-27

### Commits
- [LPS-67039]: Use embedded PMD rule set file (b1fbbc052c)
- [LPS-67039]: Embed PMD rule set file into gradle-plugins-defaults (e9791f1cd9)
- [LPS-68813]: Run task with "check" (a422d50706)
- [LPS-68813]: Add task to group (451c0d1340)

### Dependencies
- [LPS-71728]: Update the com.liferay.gradle.plugins dependency to version
3.3.6.
- [LPS-71728]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.5.

### Description
- [LPS-68813]: Execute `findSecurityBugs` with the `check` task.
- [LPS-68813]: Make the `findSecurityBugs` task visible in `gradle task`.
- [LPS-67039]: Embed and use [Liferay's PMD rule set
file](https://github.com/liferay/liferay-plugins/blob/master/dependencies/net.sourceforge.pmd/rulesets/java/standard-rules.xml).

## 3.6.9 - 2017-04-28

### Dependencies
- [LPS-70890]: Update the com.liferay.gradle.plugins dependency to version
3.3.7.
- [LPS-71728]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.6.

## 3.6.10 - 2017-05-03

### Dependencies
- [LPS-72340]: Update the com.liferay.gradle.plugins dependency to version
3.3.8.

## 3.6.11 - 2017-05-03

### Dependencies
- [LPS-72252]: Update the com.liferay.gradle.plugins dependency to version
3.3.9.

## 3.6.12 - 2017-05-09

### Dependencies
- [LPS-71164]: Update the com.liferay.gradle.plugins dependency to version
3.3.10.

## 3.6.13 - 2017-05-09

### Commits
- [LRDOCS-3643]: Reuse value (7cc347f836)
- [LRDOCS-3643]: Use portal styles for app's TLD docs (07d9e52b9e)

### Description
- [LRDOCS-3643]: Apply the [Liferay stylesheet]
(https://github.com/liferay/liferay-portal/blob/master/tools/styles/dtddoc.css)
file (if found) to the tag library documentation generated by the `appTLDDoc`
task.

## 3.6.14 - 2017-05-11

### Dependencies
- [LPS-72365]: Update the com.liferay.gradle.plugins dependency to version
3.3.11.

## 3.6.15 - 2017-05-11

### Commits
- [LPS-72456]: Update com-h3xstream-findsecbugs version (517b59b4d5)

## 3.6.16 - 2017-05-13

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.3.12.

## 3.6.17 - 2017-05-15

### Dependencies
- [LPS-72562]: Update the com.liferay.gradle.plugins dependency to version
3.3.13.

## 3.6.18 - 2017-05-16

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.3.14.

## 3.6.19 - 2017-05-19

### Dependencies
- [LPS-72572]: Update the com.liferay.gradle.plugins dependency to version
3.3.15.
- [LPS-72572]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.7.

## 3.6.20 - 2017-05-23

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.3.16.

## 3.6.21 - 2017-05-23

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.3.17.

## 3.6.22 - 2017-05-23

### Dependencies
- [LPS-71722]: Update the com.liferay.gradle.plugins dependency to version
3.3.18.

## 3.6.23 - 2017-05-23

### Dependencies
- [LPS-71722]: Update the com.liferay.gradle.plugins dependency to version
3.3.19.

## 3.6.24 - 2017-05-25

### Dependencies
- [LPS-72750]: Update the com.liferay.gradle.plugins dependency to version
3.3.20.

## 3.6.25 - 2017-05-25

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.21.

## 3.6.26 - 2017-05-30

### Commits
- [LPS-72830]: Add task to check state of current OSGi bundle (d293b58921)
- [LPS-72830]: Add task class to check state of one or all the OSGi bundles
(7126bc5bcf)

### Dependencies
- [LPS-72830]: Update the org.apache.aries.jmx.api dependency to version 1.1.0.

## 3.7.0 - 2017-05-30

### Dependencies
- [LPS-72830]: Update the com.liferay.gradle.plugins dependency to version
3.3.22.

### Description
- [LPS-72830]: Add the `checkOSGiBundleState` task to fail the build if the
deployed OSGi bundle's state is not `ACTIVE`.

## 3.7.1 - 2017-05-31

### Dependencies
- [LPS-72851]: Update the com.liferay.gradle.plugins dependency to version
3.3.23.

## 3.7.2 - 2017-06-04

### Commits
- [LPS-72465]: Avoid caching system properties, especially with Gradle Daemon
(a93c2e8200)

### Dependencies
- [LPS-72465]: Update the com.liferay.gradle.plugins dependency to version
3.3.24.

### Description
- [LPS-72465]: Avoid caching the following system property values when using the
Gradle Daemon:
	- `maven.local.ignore`
	- `repository.private.password`
	- `repository.private.url`
	- `repository.private.username`
	- `repository.url`

## 3.7.3 - 2017-06-08

### Commits
- [LPS-72989]: Use File.pathSeparator to separate files on path (4ace1a8bc3)

### Dependencies
- [LPS-72914]: Update the com.liferay.gradle.plugins dependency to version
3.3.25.

### Description
- [LPS-72989]: Fix [Find Security Bugs] custom configuration loading on Windows.

## 3.7.4 - 2017-06-13

### Commits
- [LPS-71201]: Suppress check for modules without a .releng dir (df64d6ef87)

### Dependencies
- [LPS-71201]: Update the com.liferay.gradle.plugins dependency to version
3.3.26.

### Description
- [LPS-71201]: Allow projects with a `.lfrbuild-releng-ignore` marker file to be
published from the master branch.

## 3.7.5 - 2017-06-13

### Dependencies
- [LPS-72875]: Update the com.liferay.gradle.plugins dependency to version
3.3.27.

## 3.7.6 - 2017-06-15

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.28.

## 3.7.7 - 2017-06-16

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.29.

## 3.7.8 - 2017-06-19

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.31.
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.30.

## 3.7.9 - 2017-06-22

### Commits
- [LPS-73235]: Remove publishing of module snapshots (a3b43e6fa5)
- [LPS-73128]: Consistency with other lifecycle messages (af5348b8f6)
- [LPS-73128]: Not needed, you can use "--debug-jvm" (03bb8686ca)
- [LPS-73218]: gradlew updateFileVersions (8b8f2bdebb)
- [LPS-73218]: Decrease FindSecurityBugs bugs reporting level into medium and
allow debugging (3ae9ec050c)

### Description
- [LPS-73128]: Configure [Find Security Bugs] to report only medium and high
confidence warnings.
- [LPS-73235]: Remove snapshot publish commands generated by the
`writeArtifactPublishCommands` task.

## 3.7.10 - 2017-06-23

### Commits
- [LPS-73271]: Ignore Find Security Bugs exit value by default (04a2c9f1b3)
- [LPS-73271]: Simplify (fd0a13572c)
- [LPS-73271]: Print the message also when the task fails (032d1722ae)
- [LPS-73271]: Output file name without trailing period, keep consistency with
findBugs task (c38135b1dd)

### Dependencies
- [LPS-73273]: Update the com.liferay.gradle.plugins dependency to version
3.3.32.

### Description
- [LPS-73271]: Set the [Find Security Bugs] exit code, but ignore it by default.
- [LPS-73271]: Print the [Find Security Bugs] report location, even in case of
failure.

## 3.7.11 - 2017-06-27

### Commits
- [LPS-73289]: updateFileVersions (e12d78a7f2)

### Dependencies
- [LPS-73289]: Update the com.liferay.gradle.plugins dependency to version
3.3.33.

## 3.7.12 - 2017-06-28

### Commits
- [LPS-73327]: Disable JVM assertions for unit tests (006f129ee9)

### Description
- [LPS-73327]: Disable JVM assertions for unit tests.

## 3.7.13 - 2017-06-30

### Commits
- [LPS-73141]: Move to internal util class to avoid major version bump
(0747ecb9fa)
- [LPS-73141]: Configure "installCache" to install to the temp Maven repo
(87523d2e76)
- [LPS-73141]: Add option in "InstallCacheTask" to install to a Maven repo
(c0f50c6946)
- [LPS-73141]: Add ".m2-tmp" dir as temporary Maven repository (5078e7fd21)
- [LPS-73352]: Apply to all Test tasks (bf7a4bd76c)
- [LPS-73352]: Avoid hard-coding the build directory name (e71be87ad2)
- [LPS-73352]: Move to separate plugin class (13a5d5c011)
- [LPS-73352]: Add support of jacoco to gradle, jacoco agent must be the first
java agent (47185b3d9e)

## 3.8.0 - 2017-06-30

### Description
- [LPS-73141]: Add the `.m2-tmp` directory in the portal root as a temporary
Maven repository.
- [LPS-73141]: Add the ability for `InstallCacheTask` to install the project's
artifact in a Maven repository.
- [LPS-73141]: Configure the `installCache` task to install the project's
artifact in the `.m2-tmp` directory by default.
- [LPS-73352]: Add the JaCoCo Java Agent to the `test` and `testIntegration`
tasks if the system property `jacoco.code.coverage` is set to `true`.

## 3.8.1 - 2017-06-30

### Dependencies
- [LPS-65930]: Update the com.liferay.gradle.plugins dependency to version
3.3.34.

## 3.8.2 - 2017-07-03

### Commits
- [LPS-73352]: Rename to "JaCoCo" (bca9d42c27)

### Description
- [LPS-73352]: Rename classes and configuration names from `Jacoco` to `JaCoCo`.

## 3.8.3 - 2017-07-04

### Dependencies
- [LPS-73383]: Update the com.liferay.gradle.plugins dependency to version
3.3.35.

## 3.8.4 - 2017-07-06

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.36.

## 3.8.6 - 2017-07-10

### Dependencies
- [LPS-73495]: Update the com.liferay.gradle.plugins dependency to version
3.3.37.

## 3.8.7 - 2017-07-10

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.3.38.

## 3.8.8 - 2017-07-11

### Commits
- [LPS-73489]: Create plugin with default configuration for subrepositories
(f8f9c8d9d9)

## 3.9.0 - 2017-07-11

### Dependencies
- [LPS-73261]: Update the com.liferay.gradle.plugins dependency to version
3.4.0.

### Description
- [LPS-73489]: Add the plugin `LiferayRootDefaultsPlugin`, which can be applied
to root projects to
	- apply the [Liferay Gradle Plugins Source Formatter].
	- apply `com.liferay.app.defaults.plugin`.
	- automatically configure the subprojects.
	- configure default Maven repositories.

## 3.9.1 - 2017-07-11

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.1.

## 3.9.2 - 2017-07-12

### Commits
- [LPS-73525]: Since we're having a major bump, remove deprecated methods
(cdea771961)
- [LPS-73525]: Remove aspectj support in gradle. Since we enable aspectj in
tomcat level, no need to enable it in gradle any more (7d26ef5b2b)

## 4.0.0 - 2017-07-12

### Dependencies
- [LPS-73525]: Update the com.liferay.gradle.plugins dependency to version
3.4.2.

### Description
- [LPS-73525]: Remove all deprecated methods.
- [LPS-73525]: The tasks `test` and `testIntegration` are no longer configurated
with an AspectJ weaver; hence the configuration `aspectJWeaver` is no longer
available.

## 4.0.1 - 2017-07-13

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.plugins dependency to version
3.4.3.

## 4.0.2 - 2017-07-13

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.plugins dependency to version
3.4.4.

## 4.0.3 - 2017-07-14

### Commits
- [LPS-73607]: Move logic to Gradle plugin and apply for all project types
(949a31e1c2)
- [LPS-73465]: Fix condition if Gradle is launched with a full task path
(a55ff20665)

### Dependencies
- [LPS-73607]: Update the com.liferay.gradle.plugins dependency to version
3.4.5.

### Description
- [LPS-73607]: Add the ability to force deployment in a different directory by
setting the project property `forced.deploy.dir`.
- [LPS-73584]: Trigger the `-PsyncRelease` logic even when Gradle is invoked
with the full path of the `baseline` task.

## 4.0.4 - 2017-07-17

### Commits
- [LPS-73652]: Ignore test project dependencies (9dd540c42a)

### Description
- [LPS-73652]: Ignore test project dependencies in the `printDependentArtifact`
and `writeArtifactPublishCommands` tasks.

## 4.0.5 - 2017-07-17

### Dependencies
- [LPS-73472]: Update the com.liferay.gradle.plugins dependency to version
3.4.6.

## 4.0.6 - 2017-07-18

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.7.

## 4.0.7 - 2017-07-19

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.8.

## 4.0.8 - 2017-07-19

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.9.

## 4.0.9 - 2017-07-20

### Dependencies
- [LPS-73600]: Update the com.liferay.gradle.plugins dependency to version
3.4.10.

## 4.0.10 - 2017-07-21

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.11.

## 4.0.11 - 2017-07-24

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.12.

## 4.0.12 - 2017-07-24

### Commits
- [LPS-73353]: Simplify (6643e364bf)
- [LPS-73353]: Apply properties of jacoco to gradle support (79b3d35917)
- [LPS-73353]: Only apply jacoco agent to module unit test, will apply jacoco
agent to tomcat later for module integration test (834f055431)

### Dependencies
- [LPS-73353]: Update the com.liferay.gradle.plugins dependency to version
3.4.13.

### Description
- [LPS-73353]: Add the JaCoCo Java Agent only to the `test` task if the system
or project property `junit.code.coverage` is set to `true`.
- [LPS-73353]: The `jacoco.code.coverage` system property is no longer
available.

## 4.0.13 - 2017-07-24

### Commits
- [LPS-73854]: Run the "mergeLang" task before Gulp (1fab9dfc8c)

### Dependencies
- [LPS-73854]: Update the com.liferay.gradle.plugins.lang.merger dependency to
version 1.0.3.

## 4.1.0 - 2017-07-24

### Commits
- [LPS-73854]: Set a default destination dir for the "mergeLang" taskk
(48496025f1)

### Description
- [LPS-72854]: Allow the [Liferay Gradle Plugins Lang Merger] to be applied to
a theme project.

## 4.1.1 - 2017-07-25

### Commits
- [LPS-73807]: Update versions (1ae9592288)
- [LPS-73807]: ServletResponseUtil is a Liferay specific sink (6215bd1ddf)

## 4.1.2 - 2017-07-25

### Dependencies
- [LPS-72347]: Update the com.liferay.gradle.plugins dependency to version
3.4.14.

## 4.1.3 - 2017-07-26

### Dependencies
- [LPS-73818]: Update the com.liferay.gradle.plugins dependency to version
3.4.15.

## 4.1.4 - 2017-07-26

### Commits
- [LPS-73655]: Download Ivy dependencies in Gradle before Ant (d0e72ead43)
- [LPS-73655]: Add task type to download Ivy dependencies via Gradle
(5a0e4617db)
- [LPS-73655]: Extract DocumentBuilder creation into util method (70d758103f)

### Description
- [LPS-73655]: Add new task type called `CopyIvyDependenciesTask`, which allows
dependencies declared in an `ivy.xml` file to be downloaded via Gradle.
- [LPS-73655]: Download Ant plugins' Ivy dependencies via Gradle.

## 4.1.5 - 2017-07-26

### Commits
- [LPS-73655]: Add support for "exclude" elements in ivy.xml (ebe3d6a716)

### Description
- [LPS-73655]: Add support in `CopyIvyDependenciesTask` for Ivy
[`<exclude>`](http://ant.apache.org/ivy/history/latest-milestone/ivyfile/exclude.html)
elements.

## 4.1.6 - 2017-07-27

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.16.

## 4.1.7 - 2017-07-31

### Commits
- [LPS-73655]: Add support for "transitive" attributes in ivy.xml (c72b82adfc)
- [LPS-63943]: Show "mergeArtifactsPublishCommands" msgs even with "--quiet"
(edb2f52f0d)
- [LPS-73855]: Generated (450f3672d3)

## 5.0.0 - 2017-07-31

### Dependencies
- [LPS-73655]: Update the com.liferay.gradle.plugins dependency to version
3.4.17.

### Description
- [LPS-73655]: Add support in `CopyIvyDependenciesTask` for the `transitive`
attribute in Ivy `<dependency>` elements.
- [LPS-63943]: Show `mergeArtifactsPublishCommands` messages at log level
`QUIET` instead of `LIFECYCLE`.

## 5.0.1 - 2017-08-01

### Dependencies
- [LPS-73855]: Update the com.liferay.gradle.plugins dependency to version
3.4.18.

## 5.0.2 - 2017-08-03

### Dependencies
- [LPS-73935]: Update the com.liferay.gradle.plugins dependency to version
3.4.19.

## 5.0.3 - 2017-08-04

### Dependencies
- [LPS-74034]: Update the com.liferay.gradle.plugins dependency to version
3.4.20.

## 5.0.4 - 2017-08-07

### Commits
- [LPS-74054]: Prevent publishing snapshots on release branches (57d131ecda)

### Description
- [LPS-74054]: Fail snapshot release task if the project is being published from
a release branch.

## 5.0.5 - 2017-08-07

### Dependencies
- [LPS-74063]: Update the com.liferay.gradle.plugins dependency to version
3.4.21.

## 5.0.6 - 2017-08-07

### Commits
- [LPS-73955]: Add support for branch-specific build profile (2110a1c209)

### Description
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

### Dependencies
- [LPS-74092]: Update the com.liferay.gradle.plugins dependency to version
3.4.22.

## 5.0.8 - 2017-08-09

### Dependencies
- [LPS-74104]: Update the com.liferay.gradle.plugins dependency to version
3.4.23.

## 5.0.9 - 2017-08-09

### Dependencies
- [LPS-73967]: Update the com.liferay.gradle.plugins dependency to version
3.4.24.

## 5.0.10 - 2017-08-09

### Dependencies
- [LPS-74088]: Update the com.liferay.gradle.plugins dependency to version
3.4.25.

## 5.0.11 - 2017-08-11

### Dependencies
- [LPS-73967]: Update the com.liferay.gradle.plugins dependency to version
3.4.26.

## 5.0.12 - 2017-08-12

### Dependencies
- [LPS-74126]: Update the com.liferay.gradle.plugins dependency to version
3.4.27.

## 5.0.13 - 2017-08-15

### Dependencies
- [LPS-74102]: Update the com.liferay.gradle.plugins dependency to version
3.4.28.

## 5.0.14 - 2017-08-15

### Dependencies
- [LPS-74139]: Update the com.liferay.gradle.plugins dependency to version
3.4.29.

## 5.0.15 - 2017-08-15

### Dependencies
- [LPS-74126]: Update the com.liferay.gradle.plugins dependency to version
3.4.30.

## 5.0.16 - 2017-08-15

### Commits
- [LPS-71285]: Apply and configure License Report plugin (a5017fbba6)

### Dependencies
- [LPS-71285]: Update the com.github.jk1.gradle-license-report dependency to
version 0.3.11.

### Description
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

### Dependencies
- [LPS-74139]: Update the com.liferay.gradle.plugins dependency to version
3.4.31.

## 5.0.18 - 2017-08-16

### Commits
- [LPS-74210]: Allow to pass -Xlint arguments via CLI (ad44433f49)

### Description
- [LPS-74210]: Add the ability to set one or more `-Xlint` compiler arguments by
setting the system property `[task name].lint`, where `[task name]` is the name
of the `JavaCompile` task to configure.

## 5.0.19 - 2017-08-17

### Dependencies
- [LPS-74222]: Update the com.liferay.gradle.plugins dependency to version
3.4.32.

## 5.0.20 - 2017-08-18

### Dependencies
- [LPS-74126]: Update the com.liferay.gradle.plugins dependency to version
3.4.33.

## 5.0.21 - 2017-08-21

### Dependencies
- [LPS-74155]: Update the com.liferay.gradle.plugins dependency to version
3.4.34.

## 5.0.22 - 2017-08-22

### Dependencies
- [LPS-74265]: Update the com.liferay.gradle.plugins dependency to version
3.4.35.

## 5.0.23 - 2017-08-23

### Dependencies
- [LPS-74278]: Update the com.liferay.gradle.plugins dependency to version
3.4.36.

## 5.0.24 - 2017-08-24

### Dependencies
- [LPS-74314]: Update the com.liferay.gradle.plugins dependency to version
3.4.37.

## 5.0.25 - 2017-08-24

### Dependencies
- [LPS-74343]: Update the com.liferay.gradle.plugins dependency to version
3.4.38.

## 5.0.26 - 2017-08-24

### Commits
- [LPS-74345]: gradle-plugins is already applying the "eclipse" plugin
(a4a7e1839e)

### Dependencies
- [LPS-74345]: Update the com.liferay.gradle.plugins dependency to version
3.4.39.

### Description
- [LPS-74345]: The [`Eclipse`]
(https://docs.gradle.org/current/userguide/eclipse_plugin.html) plugin is no
longer applied to OSGi projects.

## 5.0.27 - 2017-08-28

### Dependencies
- [LPS-74368]: Update the com.liferay.gradle.plugins dependency to version
3.4.41.
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.40.

## 5.0.28 - 2017-08-28

### Dependencies
- [LPS-74369]: Update the com.liferay.gradle.plugins dependency to version
3.4.42.

## 5.0.29 - 2017-08-29

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.43.

## 5.0.30 - 2017-08-29

### Commits
- [LPS-73070]: Keep package-lock.json in sync (4fa8e84fe9)
- [LPS-73070]: Extract NPM file names (76a9d10011)
- [LPS-73070]: Reuse constant (1c32365088)

### Dependencies
- [LPS-73070]: Update the com.liferay.gradle.plugins dependency to version
3.4.44.

### Description
- [LPS-73070]: Check the module's version in the `package-lock.json` file and
ensure it matches the project version.

## 5.0.31 - 2017-08-29

### Dependencies
- [LPS-73124]: Update the com.liferay.gradle.plugins dependency to version
3.4.45.

## 5.0.32 - 2017-08-29

### Dependencies
- [LPS-74278]: Update the com.liferay.gradle.plugins dependency to version
3.4.46.

## 5.0.33 - 2017-08-31

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.4.47.

## 5.0.34 - 2017-08-31

### Commits
- [LPS-74469]: wordsmith (d2b6951ab4)
- [LPS-74469]: Fail build if there's a cross-repo "soyCompile" dependency
(869c36ab6b)
- [LPS-74469]: Ignore "soyCompile" dependencies when publishing (0802f541fc)

### Description
- [LPS-74469]: Fail the build if the `soyCompile` configuration contains project
dependencies that belong to a different sub-repository.
- [LPS-74469]: Allow publishing modules whose `soyCompile` configuration
contains project dependencies.

## 5.0.35 - 2017-08-31

### Commits
- [LPS-74469]: Check for .gitrepo files instead (3206fab919)

### Description
- [LPS-74469]: Look for `.gitrepo` files instead of `settings.gradle` when
checking if the `soyCompile` configuration contains project dependencies that
belong to a different sub-repository.

## 5.0.36 - 2017-09-06

### Dependencies
- [LPS-74538]: Update the com.liferay.gradle.plugins dependency to version
3.4.49.
- [LPS-74475]: Update the com.liferay.gradle.plugins dependency to version
3.4.48.

## 5.0.37 - 2017-09-06

### Dependencies
- [LPS-74490]: Update the com.liferay.gradle.plugins dependency to version
3.4.50.

## 5.0.38 - 2017-09-07

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.4.51.

## 5.0.39 - 2017-09-08

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.4.52.

## 5.0.40 - 2017-09-10

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.4.53.

## 5.0.41 - 2017-09-11

### Dependencies
- [LPS-74614]: Update the com.liferay.gradle.plugins dependency to version
3.4.54.

## 5.0.42 - 2017-09-11

### Dependencies
- [LPS-74373]: Update the com.liferay.gradle.plugins dependency to version
3.4.55.

## 5.0.43 - 2017-09-12

### Dependencies
- [LPS-74207]: Update the com.liferay.gradle.plugins dependency to version
3.4.56.

## 5.0.44 - 2017-09-12

### Dependencies
- [LPS-74637]: Update the com.liferay.gradle.plugins dependency to version
3.4.57.

## 5.0.45 - 2017-09-13

### Dependencies
- [LPS-74657]: Update the com.liferay.gradle.plugins dependency to version
3.4.58.

## 5.0.46 - 2017-09-14

### Dependencies
- [LPS-74614]: Update the com.liferay.gradle.plugins dependency to version
3.4.59.

## 5.0.47 - 2017-09-18

### Dependencies
- [LPS-74315]: Update the com.liferay.gradle.plugins dependency to version
3.4.60.

## 5.0.48 - 2017-09-18

### Dependencies
- [LPS-74637]: Update the com.liferay.gradle.plugins dependency to version
3.4.61.

## 5.0.49 - 2017-09-18

### Dependencies
- [LPS-74770]: Update the com.liferay.gradle.plugins dependency to version
3.4.62.

## 5.0.50 - 2017-09-19

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.4.63.
- [LPS-74544]: Update the com.liferay.gradle.plugins.whip dependency to version
1.0.5.

## 5.0.51 - 2017-09-19

### Dependencies
- [LPS-74789]: Update the com.liferay.gradle.plugins dependency to version
3.4.64.

## 5.0.52 - 2017-09-19

### Dependencies
- [LPS-74657]: Update the com.liferay.gradle.plugins dependency to version
3.4.65.

## 5.0.53 - 2017-09-19

### Dependencies
- [LPS-74657]: Update the com.liferay.gradle.plugins dependency to version
3.4.66.

## 5.0.54 - 2017-09-19

### Dependencies
- [LPS-74738]: Update the com.liferay.gradle.plugins dependency to version
3.4.67.

## 5.0.55 - 2017-09-21

### Dependencies
- [LPS-73070]: Update the com.liferay.gradle.plugins dependency to version
3.4.68.

## 5.0.56 - 2017-09-23

### Dependencies
- [LPS-71117]: Update the com.liferay.gradle.plugins dependency to version
3.4.69.

## 5.0.57 - 2017-09-25

### Dependencies
- [LPS-74884]: Update the com.liferay.gradle.plugins dependency to version
3.4.70.

## 5.0.58 - 2017-09-26

### Commits
- [LPS-74892]: Publish jar with commercial sources (e7a2e7b44a)
- [LPS-74892]: Add FilterReader to replace blocks of text from files
(a9d2d234b5)

## 5.1.0 - 2017-09-26

### Dependencies
- [LPS-74749]: Update the com.liferay.gradle.plugins dependency to version
3.4.71.

### Description
- [LPS-74892]: For OSGi modules, publish an additional `sources-commercial` JAR.
If the module is public, the original copyright in the source files is replaced
with a commercial copyright.

## 5.1.1 - 2017-09-27

### Dependencies
- [LPS-74867]: Update the com.liferay.gradle.plugins dependency to version
3.4.72.

## 5.1.2 - 2017-09-28

### Commits
- [LPS-74933]: Set a prerelease version on NPM when publishing a snapshot
(a0e0e6ef0a)
- [LPS-74933]: Remove duplicate constant (17e87c207e)
- [LPS-74933]: Always disable "publishNodeModule" for private projects
(09c5812540)
- [LPS-74933]: Move util methods (6d0745072e)
- [LPS-74933]: Apply the same configuration for both OSGi modules and themes
(d5c7288051)
- [LPS-74933]: Extract method (c9581b10e4)
- [LPS-74933]: Run "publishNodeModule" tasks even publishing snapshots
(8b6f30d916)

### Dependencies
- [LPS-74933]: Update the com.liferay.gradle.plugins dependency to version
3.4.73.

### Description
- [LPS-74933]: Automatically disable the `PublishNodeModuleTask` instances for
private projects.
- [LPS-74933]: Publish *alpha* versions of packages on the NPM registry when
running the `uploadArchives` task with `-Psnapshot`.

## 5.1.3 - 2017-10-02

### Dependencies
- [LPS-75009]: Update the com.liferay.gradle.plugins dependency to version
3.4.74.

## 5.1.4 - 2017-10-04

### Commits
- [LPS-75039]: Update file versions only in the "modules" dir by default
(19ed29902a)
- [LPS-75039]: Exclude build directories (e6d1499bd5)
- [LPS-63943]: Remove the output file if there are no existing input files
(5174e90a88)
- [LPS-63943]: Disable up-to-date check to always print a message (ca898390d2)

### Dependencies
- [LPS-74110]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.7.
- [LPS-75039]: Update the com.liferay.gradle.plugins dependency to version
3.4.74.
- [LPS-75039]: Update the com.liferay.gradle.plugins dependency to version
3.4.75.
- [LPS-74110]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.8.

### Description
- [LPS-75039]: Make the `updateFileVersions` task exclude build and temporary
directories.
- [LPS-75039]: Make the `updateFileVersions` task update only the Gradle files
in the `modules` directory.
- [LPS-63943]: Always print the status message after executing the
`mergeArtifactsPublishCommands` task.

## 5.1.5 - 2017-10-04

### Dependencies
- [LPS-74314]: Update the com.liferay.gradle.plugins dependency to version
3.5.0.

## 5.1.6 - 2017-10-05

### Dependencies
- [LPS-74872]: Update the com.liferay.gradle.plugins dependency to version
3.5.1.

## 5.1.7 - 2017-10-05

### Dependencies
- [LPS-74143]: Update the com.liferay.gradle.plugins dependency to version
3.5.2.

## 5.1.8 - 2017-10-06

### Dependencies
- [LPS-74426]: Update the com.liferay.gradle.plugins dependency to version
3.5.3.

## 5.1.9 - 2017-10-06

### Dependencies
- [LPS-74143]: Update the com.liferay.gradle.plugins dependency to version
3.5.4.

## 5.1.10 - 2017-10-08

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.5.

## 5.1.11 - 2017-10-09

### Commits
- [LPS-63943]: Always execute "mergeArtifactsPublishCommands" task (4b47705843)
- [LPS-63943]: Revert "LPS-63943 Disable up-to-date check to always print a
message" (bec00956b3)

### Description
- [LPS-63943]: Disable the `mergeArtifactsPublishCommands` task's up-to-date
check.
- [LPS-63943]: Fix the error thrown when executing the
`writeArtifactPublishCommands` task from the root project directory.

## 5.1.12 - 2017-10-10

### Dependencies
- [LPS-75175]: Update the com.liferay.gradle.plugins dependency to version
3.5.6.

## 5.1.13 - 2017-10-11

### Dependencies
- [LPS-75096]: Update the com.liferay.gradle.plugins dependency to version
3.5.7.

## 5.1.14 - 2017-10-11

### Dependencies
- [LPS-74449]: Update the com.liferay.gradle.plugins dependency to version
3.5.8.

## 5.1.15 - 2017-10-16

### Commits
- [LPS-75238]: Disable JSP compilation for OSGi fragments (5b9fd2940a)

### Dependencies
- [LPS-75238]: Update the com.liferay.gradle.plugins dependency to version
3.5.9.

## 5.1.16 - 2017-10-16

### Dependencies
- [LPS-75273]: Update the com.liferay.gradle.plugins dependency to version
3.5.10.

## 5.1.17 - 2017-10-17

### Dependencies
- [LPS-74849]: Update the com.liferay.gradle.plugins dependency to version
3.5.11.

## 5.1.18 - 2017-10-17

### Dependencies
- [LPS-75100]: Update the com.liferay.gradle.plugins dependency to version
3.5.12.

## 5.1.19 - 2017-10-17

### Commits
- [LPS-75239]: Overwrite old test modules everywhere but in Liferay modules
(170fbbe0d7)

### Dependencies
- [LPS-75239]: Update the com.liferay.gradle.plugins dependency to version
3.5.13.

## 5.1.20 - 2017-10-18

### Dependencies
- [LPS-74849]: Update the com.liferay.gradle.plugins dependency to version
3.5.14.

## 5.1.21 - 2017-10-18

### Dependencies
- [LPS-74849]: Update the com.liferay.gradle.plugins dependency to version
3.5.15.

## 5.1.22 - 2017-10-19

### Dependencies
- [LPS-74348]: Update the com.liferay.gradle.plugins dependency to version
3.5.16.

## 5.1.23 - 2017-10-20

### Dependencies
- [LPS-75254]: Update the com.liferay.gradle.plugins dependency to version
3.5.17.

## 5.1.24 - 2017-10-22

### Dependencies
- [LPS-74457]: Update the com.liferay.gradle.plugins dependency to version
3.5.18.

## 5.1.25 - 2017-10-23

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.19.

## 5.1.26 - 2017-10-24

### Dependencies
- [LPS-75427]: Update the com.liferay.gradle.plugins dependency to version
3.5.20.

## 5.1.27 - 2017-10-24

### Dependencies
- [LPS-74457]: Update the com.liferay.gradle.plugins dependency to version
3.5.21.

## 5.1.28 - 2017-10-25

### Dependencies
- [LPS-74849]: Update the com.liferay.gradle.plugins dependency to version
3.5.22.

## 5.1.29 - 2017-10-26

### Dependencies
- [LPS-75323]: Update the com.liferay.gradle.plugins dependency to version
3.5.23.

## 5.1.30 - 2017-10-31

### Dependencies
- [LPS-75488]: Update the com.liferay.gradle.plugins dependency to version
3.5.24.

## 5.1.31 - 2017-11-01

### Dependencies
- [LPS-75589]: Update the com.liferay.gradle.plugins dependency to version
3.5.26.
- [LPS-75613]: Update the com.liferay.gradle.plugins dependency to version
3.5.25.

## 5.1.32 - 2017-11-02

### Dependencies
- [LPS-75399]: Update the com.liferay.gradle.plugins dependency to version
3.5.27.

## 5.1.33 - 2017-11-03

### Commits
- [LPS-75705]: Always exclude "build" and "node_modules" dirs (2e1ab98aba)

### Dependencies
- [LPS-75705]: Update the com.liferay.gradle.plugins dependency to version
3.5.28.

### Description
- [LPS-75705]: Always exclude `build` and `node_modules` directories from the
multi-project build.

## 5.1.34 - 2017-11-06

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.29.

## 5.1.35 - 2017-11-07

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.30.

## 5.1.36 - 2017-11-07

### Dependencies
- [LPS-75633]: Update the com.liferay.gradle.plugins dependency to version
3.5.31.

## 5.1.37 - 2017-11-07

### Dependencies
- [LPS-74457]: Update the com.liferay.gradle.plugins dependency to version
3.5.32.

## 5.1.38 - 2017-11-08

### Dependencies
- [LPS-74457]: Update the com.liferay.gradle.plugins dependency to version
3.5.33.

## 5.1.39 - 2017-11-08

### Commits
- [LPS-73725]: for LangBuilder to use new constants in
https://github.com/liferay/liferay-portal/commit/4bf57ddfe3f6 (6d48debbe9)

### Dependencies
- [LPS-73725]: Update the com.liferay.gradle.plugins dependency to version
3.5.34.

## 5.1.40 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.gradle.plugins dependency to version
3.5.35.

## 5.1.41 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.gradle.plugins dependency to version
3.5.36.

## 5.1.42 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.gradle.plugins dependency to version
3.5.37.

## 5.1.43 - 2017-11-09

### Dependencies
- [LPS-75610]: Update the com.liferay.gradle.plugins dependency to version
3.5.38.

## 5.1.44 - 2017-11-10

### Commits
- [LPS-69999]: Avoid replacing project dependencies in test projects
(4be8600f3e)

### Description
- [LPS-69999]: Prevent the `updateFileVersions` task from converting project
dependencies into module dependencies in test projects.

## 5.1.45 - 2017-11-10

### Dependencies
- [LPS-75010]: Update the com.liferay.gradle.plugins dependency to version
3.5.39.

## 5.1.46 - 2017-11-12

### Dependencies
- [LPS-75798]: Update the com.liferay.gradle.plugins dependency to version
3.5.40.

## 5.1.47 - 2017-11-13

### Dependencies
- [LPS-74526]: Update the com.liferay.gradle.plugins dependency to version
3.5.41.

## 5.1.48 - 2017-11-14

### Commits
- [LPS-75359]: Force EasyConf transitive dependencies to match portal
(2341588bda)
- [LPS-75359]: Exclude unfetchable EasyConf dependencies (a1c6f7e4ac)

### Description
- [LPS-75359]: Automatically exclude unfetchable
[EasyConf](http://easyconf.sourceforge.net/)
transitive dependencies.
- [LPS-75359]: Force specific versions of EasyConf transitive dependencies in
the `testCompileClasspath` and `testRuntime` configurations:
	- `commons-configuration:commons-configuration:1.10`
	- `xerces:xercesImpl:2.11.0`
	- `xml-apis:xml-apis:1.4.01`

## 5.1.49 - 2017-11-14

### Dependencies
- [LPS-74526]: Update the com.liferay.gradle.plugins dependency to version
3.5.42.

## 5.1.50 - 2017-11-14

### Dependencies
- [LPS-75798]: Update the com.liferay.gradle.plugins dependency to version
3.5.43.

## 5.1.51 - 2017-11-15

### Dependencies
- [LPS-75798]: Update the com.liferay.gradle.plugins dependency to version
3.5.44.
- [LPS-75910]: Update the com.liferay.gradle.plugins.dependency.checker
dependency to version 1.0.1.

## 5.1.52 - 2017-11-16

### Dependencies
- [LPS-75798]: Update the com.liferay.gradle.plugins dependency to version
3.5.45.

## 5.1.53 - 2017-11-20

### Commits
- [LPS-73070]: Copy
https://github.com/liferay/liferay-portal/commit/61e14073037885b35461ab01cb3eace31a161342
inside plugin (b9cf2dfff6)

### Dependencies
- [LPS-75965]: Update the com.liferay.gradle.plugins dependency to version
3.5.46.

### Description
- [LPS-73070]: Prevent npm from creating a `package-lock.json` file when running
the `npmInstall` task.

## 5.1.54 - 2017-11-21

### Dependencies
- [LPS-74457]: Update the com.liferay.gradle.plugins dependency to version
3.5.47.

## 5.1.55 - 2017-11-24

### Dependencies
- [LPS-76110]: Update the com.liferay.gradle.plugins dependency to version
3.5.48.

## 5.1.56 - 2017-11-27

### Commits
- [LPS-76145]: Create portal compat Gradle plugin (3e77064229)

## 5.2.0 - 2017-11-27

### Dependencies
- [LPS-76145]: Update the com.liferay.gradle.plugins dependency to version
3.5.49.

### Description
- [LPS-76145]: Add the new `com.liferay.osgi.portal.compat.defaults.plugin`
Gradle plugin to properly configure the portal compatibility OSGi modules.

## 5.2.1 - 2017-11-28

### Dependencies
- [LPS-72912]: Update the com.liferay.gradle.plugins dependency to version
3.5.50.

## 5.2.2 - 2017-11-28

### Commits
- [LPS-76182]: Support ".lfrbuild-releng-ignore" for a whole subtree
(06244897e8)
- [LPS-76181]: Allow "updateFileVersions" to update to the exact version
(5d61eed88e)

### Description
- [LPS-76181]: Add the property `exactVersion` to the `updateFileVersions` task.
If set to `true`, the task updates all versions to the current one, even if the
*major* part has not been increased.
- [LPS-76182]: Ignore a whole subtree if a `.lfrbuild-releng-ignore` marker file
is found in a parent directory.

## 5.2.3 - 2017-11-28

### Dependencies
- [LPS-75859]: Update the com.liferay.gradle.plugins dependency to version
3.5.51.

## 5.2.4 - 2017-11-28

### Dependencies
- [LPS-75901]: Update the com.liferay.gradle.plugins dependency to version
3.5.52.

## 5.2.5 - 2017-11-29

### Dependencies
- [LPS-75859]: Update the com.liferay.gradle.plugins dependency to version
3.5.53.

## 5.2.6 - 2017-11-29

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.54.

## 5.2.7 - 2017-11-30

### Dependencies
- [LPS-76202]: Update the com.liferay.gradle.plugins dependency to version
3.5.55.

## 5.2.8 - 2017-12-01

### Dependencies
- [LPS-76224]: Update the com.liferay.gradle.plugins dependency to version
3.5.56.
- [LPS-76224]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.1.9.

## 5.2.9 - 2017-12-01

### Commits
- [LPS-69999]: Prevent "updateFileVersions" from changing read-only repos
(e908cfebc5)

### Description
- [LPS-69999]: Prevent the `updateFileVersions` task from changing files in
read-only sub-repositories.

## 5.2.10 - 2017-12-04

### Commits
- [LPS-76221]: Disable translation in modules (88d782a459)

### Dependencies
- [LPS-76221]: Update the com.liferay.gradle.plugins dependency to version
3.5.57.

### Description
- [LPS-76221]: Set the `translate` property of all `BuildLangTask` instances to
`false` by default.

## 5.2.11 - 2017-12-05

### Commits
- [LPS-76202]: "compileJSP.destinationDir" is set after evaluation (fdf55f8f30)

### Dependencies
- [LPS-76221]: Update the com.liferay.gradle.plugins dependency to version
3.5.58.

### Description
- [LPS-76202]: Fix usages of the `compileJSP.destinationDir` property.

## 5.2.12 - 2017-12-05

### Dependencies
- [LPS-76256]: Update the com.liferay.gradle.plugins dependency to version
3.5.59.

## 5.2.13 - 2017-12-05

### Dependencies
- [LPS-76226]: Update the com.liferay.gradle.plugins dependency to version
3.5.60.

## 5.2.14 - 2017-12-07

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.61.

## 5.2.15 - 2017-12-10

### Dependencies
- [LPS-76326]: Update the com.liferay.gradle.plugins dependency to version
3.5.62.

## 5.2.16 - 2017-12-12

### Dependencies
- [LPS-72912]: Update the com.liferay.gradle.plugins dependency to version
3.5.63.

## 5.2.17 - 2017-12-12

### Dependencies
- [LPS-76018]: Update the com.liferay.gradle.plugins dependency to version
3.5.64.

## 5.2.18 - 2017-12-12

### Dependencies
- [LPS-76018]: Update the com.liferay.gradle.plugins dependency to version
3.5.65.

## 5.2.19 - 2017-12-12

### Dependencies
- [LPS-76018]: Update the com.liferay.gradle.plugins dependency to version
3.5.66.

## 5.2.20 - 2017-12-13

### Commits
- [LPS-74544]: use (cbcf29f2c3)

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.67.

## 5.2.21 - 2017-12-14

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.68.

## 5.2.22 - 2017-12-15

### Commits
- [LPS-61099]: Limit to only module apps (2c1e847295)
- [LPS-61099]: Projects paths are the same, even in subrepositories (54a09601cc)

### Description
- [LPS-61099]: Fix configuration exceptions for OSGi modules in
sub-repositories.

## 5.2.23 - 2017-12-19

### Dependencies
- [LPS-76475]: Update the com.liferay.gradle.plugins dependency to version
3.5.69.

## 5.2.24 - 2017-12-19

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.70.

## 5.2.25 - 2017-12-20

### Commits
- [LPS-76221]: release new jars as part of revert (73dc6a86e0)
- [LPS-76221]: Revert "LPS-76221 Disable translation in modules" (bc11176e43)
- [LPS-76623]: Use dependencies from plugins.gradle.org/m2 (388758937f)

### Dependencies
- [LPS-76221]: Update the com.liferay.gradle.plugins dependency to version
3.5.71.
- [LPS-76623]: Update the com.github.jk1.dependency-license-report.gradle.plugin
dependency to version 0.3.11.

### Description
- [LPS-76221]: Remove the `translate` property's default configuration for all
`BuildLangTask` instances.

## 5.2.26 - 2017-12-20

### Commits
- [LPS-76221]: republish (c7b9a54d12)

### Dependencies
- [LPS-76221]: Update the com.liferay.gradle.plugins dependency to version
3.5.72.

## 5.2.27 - 2017-12-20

### Dependencies
- [LPS-76626]: Update the com.liferay.gradle.plugins dependency to version
3.5.73.

## 5.2.28 - 2017-12-21

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.74.

## 5.2.29 - 2017-12-24

### Dependencies
- [LPS-67352]: Update the com.liferay.gradle.plugins dependency to version
3.5.75.

## 5.2.30 - 2017-12-26

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.76.

## 5.2.31 - 2017-12-29

### Commits
- [LRDOCS-4111]: Use new util method (e42d617661)
- [LRDOCS-4111]: Publish a "jsdoc" jar (d5df2508e9)
- [LRDOCS-4111]: Create class for file extension specs (2d6649f9cc)
- [LRDOCS-4111]: Preconfigure JSDoc plugin for Liferay projects (642db4095a)
- [LRDOCS-4111]: Apply JSDoc plugin to Liferay OSGi projects (8dd115dd3d)

### Dependencies
- [LRDOCS-4111]: Update the com.liferay.gradle.plugins dependency to version
3.5.77.
- [LRDOCS-4111]: Update the com.liferay.gradle.plugins.jsdoc dependency to
version 1.0.0.

### Description
- [LRDOCS-4111]: Apply and preconfigure [Liferay Gradle Plugins JSDoc] for OSGi
projects.
- [LRDOCS-4111]: Publish the JavaScript API documentation JAR of an OSGi project
with the `install` and `uploadArchives` tasks.

## 5.2.32 - 2018-01-02

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.78.

## 5.2.33 - 2018-01-02

### Commits
- [LPS-76644]: Always use "Bundle-Name" in the Javadoc title (d889812345)
- [LPS-76644]: Use new util class everywhere (68c08138b0)
- [LPS-76644]: Automatically configure the Plugin Publish plugin (6b18371e7d)
- [LPS-76644]: Move to util class (e4d8b8558e)

### Dependencies
- [LPS-74904]: Update the com.liferay.gradle.plugins dependency to version
3.5.79.
- [LPS-74904]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
1.0.1.
- [LPS-76644]: Update the plugin-publish-plugin dependency to version 0.9.9.

### Description
- [LPS-76644]: Automatically configure the [Plugin Publishing Plugin]
(https://plugins.gradle.org/docs/publish-plugin) when applied.

## 5.2.34 - 2018-01-03

### Commits
- [LPS-76623]: Fix Gradle plugin coordinates (ec02037585)

### Dependencies
- [LPS-76623]: Update the gradle-license-report dependency to version 0.3.11.

### Description
- [LPS-76623]: Fix Maven coordinates of the [Gradle License Report] dependency.

## 5.2.35 - 2018-01-04

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.80.

## 5.2.36 - 2018-01-04

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.81.

## 5.2.37 - 2018-01-08

### Dependencies
- [LPS-76840]: Update the com.liferay.gradle.plugins dependency to version
3.5.82.

## 5.2.38 - 2018-01-08

### Dependencies
- [LPS-76626]: Update the com.liferay.gradle.plugins dependency to version
3.5.83.

## 5.2.39 - 2018-01-09

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.84.

## 5.2.40 - 2018-01-10

### Dependencies
- [LPS-76226]: Update the com.liferay.gradle.plugins dependency to version
3.5.85.

## 5.2.41 - 2018-01-11

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.86.

## 5.2.42 - 2018-01-11

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.87.

## 5.2.43 - 2018-01-11

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.88.

## 5.2.44 - 2018-01-14

### Dependencies
- [LPS-77110]: Update the com.liferay.gradle.plugins dependency to version
3.5.89.

## 5.2.45 - 2018-01-17

### Dependencies
- [LPS-76626]: Update the com.liferay.gradle.plugins dependency to version
3.5.90.

## 5.2.46 - 2018-01-17

### Dependencies
- [LPS-76644]: Update the com.liferay.gradle.plugins dependency to version
3.5.91.
- [LPS-76644]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
1.0.2.

## 5.2.47 - 2018-01-22

### Dependencies
- [LPS-77286]: Update the com.liferay.gradle.plugins dependency to version
3.5.92.

## 5.2.48 - 2018-01-23

### Dependencies
- [LPS-77402]: Update the com.liferay.gradle.plugins dependency to version
3.5.93.

## 5.2.49 - 2018-01-23

### Dependencies
- [LPS-77400]: Update the com.liferay.gradle.plugins dependency to version
3.5.94.

## 5.2.50 - 2018-01-23

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.95.

## 5.2.51 - 2018-01-25

### Dependencies
- [LPS-77143]: Update the com.liferay.gradle.plugins dependency to version
3.5.96.

## 5.2.52 - 2018-01-25

### Commits
- [LPS-77423]: Not needed anymore (610d4364ac)
- [LPS-73070]: Revert "LPS-73070 Copy
https://github.com/liferay/liferay-portal/commit/61e14073037885b35461ab01cb3eace31a161342
inside plugin" (f56f6f91f8)

### Dependencies
- [LPS-77423]: Update the com.liferay.gradle.plugins dependency to version
3.5.97.

### Description
- [LPS-77423]: Remove the `--no-package-lock` default argument in the
`npmInstall` task.
- [LPS-77423]: Remove the `fsevents` dependency exclusion from the generated
`npm-shrinkwrap.json` files.

## 5.2.53 - 2018-01-26

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.5.98.

## 5.2.54 - 2018-01-29

### Commits
- [LPS-77441]: Not needed anymore (23879b0c8b)

### Dependencies
- [LPS-77441]: Update the com.liferay.gradle.plugins dependency to version
3.5.99.
- [LPS-77441]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.2.0.

### Description
- [LPS-77441]: Remove code that set the default values of the properties
`reportDiff` and `reportOnlyDiffPackages` for all `BaselineTask` instances,
since it has been moved into [Liferay Gradle Plugins Baseline].

## 5.2.55 - 2018-01-30

### Dependencies
- [LPS-77630]: Update the com.liferay.gradle.plugins dependency to version
3.5.100.

## 5.2.56 - 2018-01-31

### Dependencies
- [LPS-77459]: Update the com.liferay.gradle.plugins dependency to version
3.5.101.

## 5.2.57 - 2018-02-01

### Dependencies
- [LPS-77350]: Update the com.liferay.gradle.plugins dependency to version
3.6.0.
- [LPS-77350]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.2.1.

## 5.2.58 - 2018-02-05

### Dependencies
- [LPS-77795]: Update the com.liferay.gradle.plugins dependency to version
3.6.1.

## 5.2.59 - 2018-02-06

### Commits
- [LPS-77359]: Configure task "uploadPoshiRunnerResources" (37e739b74d)
- [LPS-77359]: Move GitRepo class outside (9264a8396b)
- [LPS-77797]: Deploys WSDD jar if the new marker file is present (8c3bce9634)

### Dependencies
- [LPS-77836]: Update the com.liferay.gradle.plugins dependency to version
3.6.2.
- [LPS-77359]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.0.0.

### Description
- [LPS-77359]: Add the plugin `com.liferay.poshi.runner.resources.defaults` to
properly apply and configure the `com.liferay.poshi.runner` plugin.
- [LPS-77797]: Make the `deploy` task depend on `buildWSDD` if the project
directory contains the marker file `.lfrbuild-deploy-wsdd`.

## 5.2.60 - 2018-02-08

### Dependencies
- [LPS-77886]: Update the com.liferay.gradle.plugins dependency to version
3.6.3.

## 5.2.61 - 2018-02-08

### Dependencies
- [LPS-69802]: Update the com.liferay.gradle.plugins dependency to version
3.6.4.
- [LPS-69802]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
1.0.3.

## 5.2.62 - 2018-02-08

### Commits
- [LPS-77840]: Pass app title, description, and version to JSDoc (a6d34e78b6)
- [LPS-77840]: Sort (712968df5f)
- [LPS-77840]: Always apply the JSDoc configuration (2c5e71fc68)
- [LPS-77840]: Always apply the Node defaults plugin (8c4a80cb8f)
- [LPS-77840]: Add filter for '.es.js', '.jsdoc', '.jsx' files (e3c1090261)
- [LPS-77840]: Apply NodeDefaultsPlugin for AppJSDoc task (6f252d51f7)
- [LPS-77840]: Add appJSDoc task (a8a4c41ae7)

## 5.3.0 - 2018-02-08

### Dependencies
- [LPS-77840]: Update the com.liferay.gradle.plugins dependency to version
3.7.0.
- [LPS-77840]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.0.

### Description
- [LPS-77840]: The `com.liferay.app.defaults.plugin` plugin now automatically
applies the `com.liferay.app.jsdoc` plugin.

## 5.3.1 - 2018-02-11

### Dependencies
- [LPS-77916]: Update the com.liferay.gradle.plugins dependency to version
3.7.1.

## 5.3.2 - 2018-02-12

### Dependencies
- [LPS-68297]: Update the com.liferay.gradle.plugins dependency to version
3.7.2.

## 5.3.3 - 2018-02-13

### Commits
- [LPS-77996]: Configure digest file (fb1c773d21)

### Dependencies
- [LPS-77996]: Update the com.liferay.gradle.plugins dependency to version
3.7.3.
- [LPS-77996]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.1.

### Description
- [LPS-77996]: Set the `npmInstall` task's `nodeModulesDigestFile` property to
`"${project.projectDir}/node_modules/.digest"` by default.

## 5.3.4 - 2018-02-14

### Dependencies
- [LPS-78033]: Update the com.liferay.gradle.plugins dependency to version
3.7.4.

## 5.3.5 - 2018-02-15

### Dependencies
- [LPS-78038]: Update the com.liferay.gradle.plugins dependency to version
3.7.5.

## 5.3.6 - 2018-02-18

### Commits
- [LPS-78096]: (61c48c970b)

### Dependencies
- [LPS-78096]: Update the com.liferay.gradle.plugins dependency to version
3.7.6.
- [LPS-78096]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.1.0.

### Description
- [LPS-78096]: Set the `poshiRunnerResources.rootDirName` property to
`"testFunctional"` by default.

## 5.3.7 - 2018-02-20

### Dependencies
- [LPS-78071]: Update the com.liferay.gradle.plugins dependency to version
3.7.7.

## 5.3.8 - 2018-02-21

### Dependencies
- [LPS-78033]: Update the com.liferay.gradle.plugins dependency to version
3.7.8.

## 5.3.9 - 2018-02-22

### Dependencies
- [LPS-78150]: Update the com.liferay.gradle.plugins dependency to version
3.7.9.

## 5.3.10 - 2018-02-25

### Dependencies
- [LPS-77532]: Update the com.liferay.gradle.plugins dependency to version
3.8.0.

## 5.3.11 - 2018-02-26

### Commits
- [LPS-74544]: Remove unnecessary semi colons (a8e65c4cca)

### Dependencies
- [LPS-76926]: Update the com.liferay.gradle.plugins dependency to version
3.8.1.

## 5.3.12 - 2018-02-26

### Dependencies
- [LPS-75323]: Update the com.liferay.gradle.plugins dependency to version
3.8.2.

## 5.3.13 - 2018-02-28

### Dependencies
- [LPS-78266]: Update the com.liferay.gradle.plugins dependency to version
3.8.3.
- [LPS-78266]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.0.

## 5.3.14 - 2018-03-01

### Dependencies
- [LPS-76926]: Update the com.liferay.gradle.plugins dependency to version
3.8.4.

## 5.3.15 - 2018-03-02

### Dependencies
- [LPS-78312]: Update the com.liferay.gradle.plugins dependency to version
3.8.5.

## 5.3.16 - 2018-03-05

### Dependencies
- [LPS-76997]: Update the com.liferay.gradle.plugins dependency to version
3.8.6.

## 5.3.17 - 2018-03-05

### Dependencies
- [LPS-78312]: Update the com.liferay.gradle.plugins dependency to version
3.8.7.

## 5.3.18 - 2018-03-07

### Dependencies
- [LPS-78537]: Update the com.liferay.gradle.plugins dependency to version
3.8.8.
- [LPS-78537]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.1.

## 5.3.19 - 2018-03-07

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.plugins dependency to version
3.8.9.

## 5.3.20 - 2018-03-07

### Dependencies
- [LPS-78571]: Update the com.liferay.gradle.plugins dependency to version
3.8.10.

## 5.3.21 - 2018-03-07

### Dependencies
- [LPS-78459]: Update the com.liferay.gradle.plugins dependency to version
3.8.11.

## 5.3.22 - 2018-03-07

### Dependencies
- [LPS-78493]: Update the com.liferay.gradle.plugins dependency to version
3.8.12.

## 5.3.23 - 2018-03-08

### Commits
- [LPS-78558]: If the version changes, the patch number should reset
(acb3583b33)
- [LPS-78559]: Increase version number (4d8a3c2f96)

### Dependencies
- [LPS-78571]: Update the com.liferay.gradle.plugins dependency to version
3.8.13.

## 5.3.24 - 2018-03-09

### Commits
- [LPS-77425]: Exclude "arquillian-extension-junit-bridge" from marker file check
(054a7b3a88)

### Description
- [LPS-77524]: Allow a `testIntegrationCompile` dependency from a project with
path `:test:arquillian-extension-junit-bridge` to not have a `.lfrbuild-portal`
marker file.

## 5.3.25 - 2018-03-10

### Dependencies
- [LPS-78288]: Update the com.liferay.gradle.plugins dependency to version
3.8.14.

## 5.3.26 - 2018-03-12

### Dependencies
- [LPS-78308]: Update the com.liferay.gradle.plugins dependency to version
3.8.15.

## 5.3.27 - 2018-03-12

### Dependencies
- [LPS-78269]: Update the com.liferay.gradle.plugins dependency to version
3.8.16.

## 5.3.28 - 2018-03-13

### Dependencies
- [LPS-78457]: Update the com.liferay.gradle.plugins dependency to version
3.8.17.

## 5.3.29 - 2018-03-13

### Dependencies
- [LPS-78767]: Update the com.liferay.gradle.plugins dependency to version
3.8.18.

## 5.3.30 - 2018-03-15

### Commits
- [LPS-78741]: Apply LiferayCIPlugin after NodeDefaultsPlugin to set 'useNpmCI'
to false (a5de944c65)
- [LPS-78741]: Simplify (see gradle-plugins-node readme) (e86f08e61b)
- [LPS-78741]: Remove 'clean.node.modules' logic (cb84fdf2e4)
- [LPS-78741]: Disable for CI (8b1925b50b)
- [LPS-78741]: Enable for gradle-plugins-defaults (c16242a9d0)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Partial revert of b739c8fcdc5d1546bd642ca931476c71bbaef1fb
(02ca75b1da)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-78741]: Update the com.liferay.gradle.plugins dependency to version
3.8.19.
- [LPS-78741]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.2.
- [LPS-77425]: Update the com.liferay.gradle.plugins dependency to version
3.8.18.
- [LPS-77425]: Update the com.liferay.gradle.plugins.app.javadoc.builder
dependency to version 1.2.0.
- [LPS-77425]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.2.1.
- [LPS-77425]: Update the com.liferay.gradle.plugins.cache dependency to version
1.0.12.
- [LPS-77425]: Update the com.liferay.gradle.plugins.change.log.builder
dependency to version 1.0.3.
- [LPS-77425]: Update the com.liferay.gradle.plugins.dependency.checker
dependency to version 1.0.1.
- [LPS-77425]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.1.
- [LPS-77425]: Update the com.liferay.gradle.plugins.lang.merger dependency to
version 1.0.3.
- [LPS-77425]: Update the com.liferay.gradle.plugins.patcher dependency to
version 1.0.12.
- [LPS-77425]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.1.
- [LPS-77425]: Update the com.liferay.gradle.plugins.whip dependency to version
1.0.5.
- [LPS-77425]: Update the gradle-extra-configurations-plugin dependency to
version 3.0.3.
- [LPS-77425]: Update the gradle-license-report dependency to version 0.3.11.
- [LPS-77425]: Update the org.apache.aries.jmx.api dependency to version 1.1.0.
- [LPS-77425]: Update the gradle-extra-configurations-plugin dependency to
version 3.0.3.
- [LPS-77425]: Update the gradle-license-report dependency to version 0.3.11.
- [LPS-77425]: Update the org.apache.aries.jmx.api dependency to version 1.1.0.

### Description
- [LPS-78741]: Configure the `npmInstall` task to run `npm ci` instead of `npm
install` when not on Jenkins.

## 5.3.31 - 2018-03-16

### Dependencies
- [LPS-78772]: Update the com.liferay.gradle.plugins dependency to version
3.8.20.

## 5.3.32 - 2018-03-16

### Dependencies
- [LPS-78845]: Update the com.liferay.gradle.plugins dependency to version
3.8.21.

## 5.3.33 - 2018-03-16

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.plugins dependency to version
3.9.0.

## 5.3.34 - 2018-03-17

### Dependencies
- [LPS-78772]: Update the com.liferay.gradle.plugins dependency to version
3.9.1.

## 5.3.35 - 2018-03-18

### Dependencies
- [LPS-78911]: Update the com.liferay.gradle.plugins dependency to version
3.9.2.

## 5.3.36 - 2018-03-19

### Dependencies
- [LPS-78772]: Update the com.liferay.gradle.plugins dependency to version
3.9.3.

## 5.3.37 - 2018-03-19

### Dependencies
- [LPS-78772]: Update the com.liferay.gradle.plugins dependency to version
3.9.4.

## 5.3.38 - 2018-03-20

### Dependencies
- [LPS-78772]: Update the com.liferay.gradle.plugins dependency to version
3.9.5.

## 5.3.39 - 2018-03-20

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.9.6.

## 5.3.40 - 2018-03-20

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.9.7.

## 5.3.41 - 2018-03-21

### Dependencies
- [LPS-78750]: Update the com.liferay.gradle.plugins dependency to version
3.9.8.

## 5.3.42 - 2018-03-21

### Dependencies
- [LPS-78772]: Update the com.liferay.gradle.plugins dependency to version
3.9.9.

## 5.3.43 - 2018-03-22

### Commits
- [LPS-78741]: Disable 'npm ci' (1178ffcef5)

### Dependencies
- [LPS-78741]: Update the com.liferay.gradle.plugins dependency to version
3.9.10.
- [LPS-78741]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.3.

### Description
- [LPS-78741]: Configure the `npmInstall` task to run `npm install` instead of
`npm ci`.

## 5.3.44 - 2018-03-23

### Dependencies
- [LPS-78911]: Update the com.liferay.gradle.plugins dependency to version
3.9.11.

## 5.3.45 - 2018-03-26

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.9.12.

## 5.3.46 - 2018-03-26

### Dependencies
- [LPS-79191]: Update the com.liferay.gradle.plugins dependency to version
3.9.13.

## 5.3.47 - 2018-03-27

### Dependencies
- [LPS-79192]: Update the com.liferay.gradle.plugins dependency to version
3.9.14.

## 5.3.48 - 2018-03-27

### Commits
- [LPS-78477]: Update LiferayCIPlugin to allow for ci only test modules.
(261ecc4dad)

### Dependencies
- [LPS-78477]: Update the com.liferay.gradle.plugins dependency to version
3.9.15.

### Description
- [LPS-78477]: When on Jenkins, fail the `testIntegration` task if any dependent
projects defined in the `testIntegrationCompile` configuration do not have a
`.lfrbuild-portal` or a `lfrbuild-ci` marker file.

## 5.3.49 - 2018-03-27

### Dependencies
- [LPS-77577]: Update the com.liferay.gradle.plugins dependency to version
3.9.16.

## 5.3.50 - 2018-03-27

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.9.17.

## 5.3.51 - 2018-03-27

### Dependencies
- [LPS-79131]: Update the com.liferay.gradle.plugins dependency to version
3.9.18.

## 5.3.52 - 2018-03-27

### Dependencies
- [LPS-79226]: Update the com.liferay.gradle.plugins dependency to version
3.9.19.

## 5.3.53 - 2018-03-27

### Dependencies
- [LPS-78901]: Update the com.liferay.gradle.plugins dependency to version
3.9.20.

## 5.3.54 - 2018-03-29

### Dependencies
- [LPS-79286]: Update the com.liferay.gradle.plugins dependency to version
3.9.21.

## 5.3.55 - 2018-03-29

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.9.22.

## 5.3.56 - 2018-03-30

### Commits
- [LPS-78741]: Can't use Node.js 6.6.0 globally on 7.0.x (b8d81037a8)
- [LPS-78741]: Reuse portal version (d408635a13)
- [LPS-78741]: No need to have a constant here (e485e7ebd8)
- [LPS-78741]: Use 'npm ci' for master (86aaa20669)
- [LPS-78741]: Set Node.js version to 6.6.0 for 7.0.x (1f89c4b3eb)
- [LPS-78741]: Run 'npm install' on Jenkins (ce37daba3b)
- [LPS-78741]: Use 'npm ci' (8926c6f4b6)

### Dependencies
- [LPS-78741]: Update the com.liferay.gradle.plugins dependency to version
3.10.0.
- [LPS-78741]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.4.

### Description
- [LPS-78741]: Configure the `npmInstall` task to run `npm ci` instead of
`npm install` when on the master branch and not on Jenkins.
- [LPS-78741]: Set the Node.js version to 6.6.0 and disable the global Node.js
execution when on the `7.0.x` branch.

## 5.3.57 - 2018-03-31

### Dependencies
- [LPS-79248]: Update the com.liferay.gradle.plugins dependency to version
3.10.1.

## 5.3.58 - 2018-04-02

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.10.2.

## 5.3.59 - 2018-04-02

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.10.3.

## 5.3.60 - 2018-04-02

### Dependencies
- [LPS-75010]: Update the com.liferay.gradle.plugins dependency to version
3.10.4.

## 5.3.61 - 2018-04-03

### Commits
- [LPS-74110]: SB uses deprecated annotations, must hide warning (be07aa286e)

### Dependencies
- [LPS-74110]: Update the com.liferay.gradle.plugins dependency to version
3.10.5.
- [LPS-74110]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.2.2.

## 5.3.62 - 2018-04-04

### Dependencies
- [LPS-79360]: Update the com.liferay.gradle.plugins dependency to version
3.10.6.

## 5.3.63 - 2018-04-04

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.10.7.

## 5.3.64 - 2018-04-04

### Dependencies
- [LPS-79365]: Update the com.liferay.gradle.plugins dependency to version
3.10.8.

## 5.3.65 - 2018-04-05

### Commits
- [LPS-75049]: Update copyLibs task to include 'compileOnly' dependencies
(21e2b830a9)
- [LPS-75049]: Add 'compileOnly' dependencies to the test integration compile
classpath (27d4f18244)
- [LPS-75049]: Support both "provided" and "compileOnly" dependencies
(98c61d4170)
- [LPS-75049]: Extract method (13b017f3a2)
- [LPS-75049]: Stop applying "optional" plugin, it's not used often enough
(7cca8eb976)

### Dependencies
- [LPS-75049]: Update the com.liferay.gradle.plugins dependency to version
3.10.9.
- [LPS-75049]: Update the gradle-extra-configurations-plugin dependency to
version 3.1.0.
- [LPS-78741]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.5.

### Description
- [LPS-75049]: Add support for the `compileOnly` configuration.
- [LPS-75049]: Add `compileOnly` dependencies to the test integration compile
classpath.

## 5.3.66 - 2018-04-06

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.10.10.

## 5.3.67 - 2018-04-06

### Dependencies
- [LPS-78971]: Update the com.liferay.gradle.plugins dependency to version
3.10.11.

## 5.3.68 - 2018-04-06

### Commits
- [LPS-75049]: Fix integration test runtime classpath (7b3eb57fc7)

### Description
- [LPS-75049]: Add `compileOnly` dependencies to the test integration runtime
classpath.

## 5.3.69 - 2018-04-09

### Dependencies
- [LPS-79450]: Update the com.liferay.gradle.plugins dependency to version
3.10.12.

## 5.3.70 - 2018-04-09

### Dependencies
- [LPS-74171]: Update the com.liferay.gradle.plugins dependency to version
3.10.13.

## 5.3.71 - 2018-04-10

### Dependencies
- [LPS-78308]: Update the com.liferay.gradle.plugins dependency to version
3.10.14.

## 5.3.72 - 2018-04-10

### Dependencies
- [LPS-78911]: Update the com.liferay.gradle.plugins dependency to version
3.10.15.

## 5.3.73 - 2018-04-11

### Dependencies
- [LPS-75010]: Update the com.liferay.gradle.plugins dependency to version
3.10.16.

## 5.3.74 - 2018-04-12

### Dependencies
- [LPS-78459]: Update the com.liferay.gradle.plugins dependency to version
3.10.17.

## 5.3.75 - 2018-04-12

### Dependencies
- [LPS-79576]: Update the com.liferay.gradle.plugins dependency to version
3.10.18.

## 5.3.76 - 2018-04-12

### Dependencies
- [LPS-75530]: Update the com.liferay.gradle.plugins dependency to version
3.11.0.

## 5.3.77 - 2018-04-13

### Dependencies
- [LPS-79576]: Update the com.liferay.gradle.plugins dependency to version
3.11.1.

## 5.3.78 - 2018-04-13

### Dependencies
- [LPS-77639]: Update the com.liferay.gradle.plugins dependency to version
3.11.2.

## 5.3.79 - 2018-04-13

### Dependencies
- [LPS-79623]: Update the com.liferay.gradle.plugins dependency to version
3.11.3.

## 5.3.80 - 2018-04-16

### Dependencies
- [LPS-79576]: Update the com.liferay.gradle.plugins dependency to version
3.11.4.

## 5.3.81 - 2018-04-16

### Dependencies
- [LPS-79576]: Update the com.liferay.gradle.plugins dependency to version
3.11.5.

## 5.3.82 - 2018-04-17

### Commits
- [LPS-79679]: Fix incorrect naming (6d14ee950f)

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.11.6.

## 5.3.83 - 2018-04-18

### Dependencies
- [LPS-79755]: Update the com.liferay.gradle.plugins dependency to version
3.11.7.

## 5.3.84 - 2018-04-18

### Dependencies
- [LPS-77645]: Update the com.liferay.gradle.plugins dependency to version
3.11.8.

## 5.3.85 - 2018-04-19

### Dependencies
- [LPS-79386]: Update the com.liferay.gradle.plugins dependency to version
3.11.9.

## 5.3.86 - 2018-04-19

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.11.10.

## 5.3.87 - 2018-04-20

### Dependencies
- [LPS-79919]: Update the com.liferay.gradle.plugins dependency to version
3.11.11.

## 5.3.88 - 2018-04-20

### Dependencies
- [LPS-72705]: Update the com.liferay.gradle.plugins dependency to version
3.11.12.

## 5.3.89 - 2018-04-22

### Dependencies
- [LPS-75049]: Update the com.liferay.gradle.plugins dependency to version
3.11.13.

## 5.3.90 - 2018-04-23

### Dependencies
- [LPS-79799]: Update the com.liferay.gradle.plugins dependency to version
3.11.14.

## 5.3.91 - 2018-04-23

### Dependencies
- [LPS-80054]: Update the com.liferay.gradle.plugins dependency to version
3.11.15.

## 5.3.92 - 2018-04-23

### Dependencies
- [LPS-80054]: Update the com.liferay.gradle.plugins dependency to version
3.11.16.

## 5.3.93 - 2018-04-24

### Dependencies
- [LPS-80064]: Update the com.liferay.gradle.plugins dependency to version
3.11.17.

## 5.3.94 - 2018-04-25

### Dependencies
- [LPS-79963]: Update the com.liferay.gradle.plugins dependency to version
3.11.18.

## 5.3.95 - 2018-04-25

### Dependencies
- [LPS-79388]: Update the com.liferay.gradle.plugins dependency to version
3.11.19.

## 5.3.96 - 2018-04-26

### Dependencies
- [LPS-80184]: Update the com.liferay.gradle.plugins dependency to version
3.11.20.

## 5.3.97 - 2018-04-26

### Dependencies
- [LPS-80125]: Update the com.liferay.gradle.plugins dependency to version
3.11.21.

## 5.3.98 - 2018-04-26

### Dependencies
- [LPS-80123]: Update the com.liferay.gradle.plugins dependency to version
3.11.22.

## 5.3.99 - 2018-04-29

### Dependencies
- [LPS-79755]: Update the com.liferay.gradle.plugins dependency to version
3.11.23.

## 5.3.100 - 2018-04-30

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.11.24.

## 5.3.101 - 2018-04-30

### Dependencies
- [LPS-80122]: Update the com.liferay.gradle.plugins dependency to version
3.11.25.

## 5.3.102 - 2018-05-01

### Commits
- [LPS-80332]: Create util method (04a91dc5c9)
- [LPS-80332]: Set max list capacity (f684c47702)
- [LPS-80332]: Append a single character (7f4d1958e3)
- [LPS-80332]: For StringBuilder, capacity is the number of characters
(cb35f1693b)
- [LPS-80332]: Update the include instruction for projects that have a suite.bnd
file (c5f258298a)

### Description
- [LPS-80332]: Update the `-include` instruction for projects that have a
`suite.bnd` file.

## 5.3.103 - 2018-05-01

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.11.26.

## 5.3.104 - 2018-05-02

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.11.27.

## 5.3.105 - 2018-05-02

### Dependencies
- [LPS-80332]: Update the com.liferay.gradle.plugins dependency to version
3.11.28.

## 5.3.106 - 2018-05-02

### Commits
- [LPS-65633]: Fix typo (5301ca5256)

### Dependencies
- [LPS-80394]: Update the com.liferay.gradle.plugins dependency to version
3.11.29.
- [LPS-80394]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.3.
- []: Update the com.liferay.gradle.plugins.poshi.runner dependency to version
2.2.2.

### Description
- [LPS-65633]: If the module is `private`, search for the public `app.bnd`.

## 5.3.107 - 2018-05-03

### Dependencies
- [LPS-80386]: Update the com.liferay.gradle.plugins dependency to version
3.11.30.

## 5.3.108 - 2018-05-03

### Dependencies
- [LPS-80466]: Update the com.liferay.gradle.plugins dependency to version
3.11.31.

## 5.3.109 - 2018-05-03

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.11.32.

## 5.3.110 - 2018-05-04

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.11.33.

## 5.3.111 - 2018-05-06

### Dependencies
- [LPS-80517]: Update the com.liferay.gradle.plugins dependency to version
3.11.34.

## 5.3.112 - 2018-05-06

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.11.35.

## 5.3.113 - 2018-05-07

### Dependencies
- [LPS-75530]: Update the com.liferay.gradle.plugins dependency to version
3.11.36.
- [LPS-75530]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.6.

## 5.3.114 - 2018-05-07

### Dependencies
- [LPS-80513]: Update the com.liferay.gradle.plugins dependency to version
3.11.37.

## 5.3.115 - 2018-05-08

### Dependencies
- [LPS-80544]: Update the com.liferay.gradle.plugins dependency to version
3.11.38.

## 5.3.116 - 2018-05-10

### Dependencies
- [LPS-80332]: Update the com.liferay.gradle.plugins dependency to version
3.11.39.

## 5.3.117 - 2018-05-10

### Dependencies
- [LPS-79453]: Update the com.liferay.gradle.plugins dependency to version
3.12.0.

## 5.3.118 - 2018-05-13

### Dependencies
- [LPS-80840]: Update the com.liferay.gradle.plugins dependency to version
3.12.1.

## 5.3.119 - 2018-05-14

### Dependencies
- [LPS-79799]: Update the com.liferay.gradle.plugins dependency to version
3.12.2.

## 5.3.120 - 2018-05-14

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.3.

## 5.3.121 - 2018-05-15

### Dependencies
- [LPS-78940]: Update the com.liferay.gradle.plugins dependency to version
3.12.4.

## 5.3.122 - 2018-05-15

### Commits
- [LPS-69802]: Update task description (71160693d6)
- [LPS-69802]: Pass the task name as string (e085b30dd0)
- [LPS-69802]: Use ApplicationPlugin.TASK_RUN_NAME (bc0b47dfdb)
- [LPS-69802]: Set project.group to 'com.liferay' (e660ea0e31)
- [LPS-80944]: Add ProjectDirType.SPRING_BOOT (398d93f877)
- [LPS-80944]: Expose plugin with id 'com.liferay.spring.boot.defaults'
(cfa290c557)
- [LPS-80944]: Add plugin (4ce673082a)

## 5.4.0 - 2018-05-15

### Dependencies
- [LPS-80944]: Update the com.liferay.gradle.plugins dependency to version
3.12.5.

### Description
- [LPS-80944]: Add the plugin `com.liferay.spring.boot.defaults` to configure
Spring Boot projects according to Liferay defaults.

## 5.4.1 - 2018-05-15

### Dependencies
- [LPS-79262]: Update the com.liferay.gradle.plugins dependency to version
3.12.6.

## 5.4.2 - 2018-05-15

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.7.

## 5.4.3 - 2018-05-16

### Dependencies
- [LPS-80950]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.4.

## 5.4.4 - 2018-05-16

### Commits
- [LPS-80944]: Add default repositories to Spring Boot projects (180b0d2288)

### Description
- [LPS-80944]: Configure the `com.liferay.spring.boot.defaults` plugin to
automatically add local Maven and [Liferay CDN] repositories to the project.

## 5.4.5 - 2018-05-16

### Dependencies
- [LPS-80950]: Update the com.liferay.gradle.plugins.change.log.builder
dependency to version 1.0.4.

## 5.4.6 - 2018-05-16

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.8.

## 5.4.7 - 2018-05-17

### Dependencies
- [LPS-79240]: Update the com.liferay.gradle.plugins dependency to version
3.12.9.

## 5.4.8 - 2018-05-17

### Dependencies
- [LPS-81106]: Update the com.liferay.gradle.plugins dependency to version
3.12.10.

## 5.4.9 - 2018-05-17

### Dependencies
- [LPS-80517]: Update the com.liferay.gradle.plugins dependency to version
3.12.11.

## 5.4.10 - 2018-05-19

### Dependencies
- [LPS-80920]: Update the com.liferay.gradle.plugins dependency to version
3.12.12.

## 5.4.11 - 2018-05-21

### Dependencies
- [LPS-79963]: Update the com.liferay.gradle.plugins dependency to version
3.12.13.

## 5.4.12 - 2018-05-21

### Dependencies
- [LPS-80777]: Update the com.liferay.gradle.plugins dependency to version
3.12.14.

## 5.4.13 - 2018-05-22

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.15.

## 5.4.14 - 2018-05-23

### Dependencies
- [LPS-80723]: Update the com.liferay.gradle.plugins dependency to version
3.12.16.

## 5.4.15 - 2018-05-23

### Dependencies
- [LPS-79709]: Update the com.liferay.gradle.plugins dependency to version
3.12.17.

## 5.4.16 - 2018-05-23

### Dependencies
- [LPS-81404]: Update the com.liferay.gradle.plugins dependency to version
3.12.18.

## 5.4.17 - 2018-05-24

### Dependencies
- [LPS-80517]: Update the com.liferay.gradle.plugins dependency to version
3.12.19.

## 5.4.18 - 2018-05-28

### Dependencies
- [LPS-81555]: Update the com.liferay.gradle.plugins dependency to version
3.12.20.

## 5.4.19 - 2018-05-29

### Dependencies
- [LPS-81635]: Update the com.liferay.gradle.plugins dependency to version
3.12.21.

## 5.4.20 - 2018-05-29

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.22.

## 5.4.21 - 2018-05-30

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.23.

## 5.4.22 - 2018-05-31

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.24.

## 5.4.23 - 2018-05-31

### Dependencies
- [LPS-81795]: Update the com.liferay.gradle.plugins dependency to version
3.12.25.

## 5.4.24 - 2018-06-01

### Commits
- [LPS-80944]: Show JUnit full log for Spring Boot projects (c51ad02fa7)

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.26.

### Description
- [LPS-80944]: Show full JUnit log when running tests on Spring Boot and any
Java project.

## 5.4.25 - 2018-06-04

### Dependencies
- [LPS-81895]: Update the com.liferay.gradle.plugins dependency to version
3.12.27.
- [LPS-81895]: Update the com.liferay.gradle.plugins.lang.merger dependency to
version 1.1.0.

## 5.4.26 - 2018-06-04

### Commits
- [LPS-67694]: Make configuration non-transitive (57307b5693)

### Description
- [LPS-67694]: Fix error when `-PsnapshotIfStale` is set and the latest snapshot
of a module includes transitive dependencies.

## 5.4.27 - 2018-06-04

### Dependencies
- [LPS-79919]: Update the com.liferay.gradle.plugins dependency to version
3.12.28.

## 5.4.28 - 2018-06-05

### Dependencies
- [LPS-81336]: Update the com.liferay.gradle.plugins dependency to version
3.12.29.

## 5.4.29 - 2018-06-05

### Dependencies
- [LPS-82001]: Update the com.liferay.gradle.plugins dependency to version
3.12.30.

## 5.4.30 - 2018-06-06

### Dependencies
- [LPS-81944]: Update the com.liferay.gradle.plugins dependency to version
3.12.31.

## 5.4.31 - 2018-06-07

### Commits
- [LPS-80332]: Skip when running in subrepositories (8fc69531ee)
- [LPS-80332]: This method is already called only if not null (5fe4e0a053)

### Dependencies
- [LPS-78940]: Update the com.liferay.gradle.plugins dependency to version
3.12.32.

### Description
- [LPS-80332]: Fix `suite.bnd` inclusion when running in sub-repositories.

## 5.4.32 - 2018-06-08

### Dependencies
- [LPS-82178]: Update the com.liferay.gradle.plugins dependency to version
3.12.33.
- [LPS-82178]: Update the com.liferay.gradle.plugins.patcher dependency to
version 1.0.13.
- [LPS-82310]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.7.

## 5.4.33 - 2018-06-11

### Dependencies
- [LPS-80927]: Update the com.liferay.gradle.plugins dependency to version
3.12.34.

## 5.4.34 - 2018-06-11

### Dependencies
- [LPS-81638]: Update the com.liferay.gradle.plugins dependency to version
3.12.35.

## 5.4.35 - 2018-06-11

### Dependencies
- [LPS-82121]: Update the com.liferay.gradle.plugins dependency to version
3.12.36.

## 5.4.36 - 2018-06-11

### Dependencies
- [LPS-77875]: Update the com.liferay.gradle.plugins dependency to version
3.12.37.

### Description
- [LPS-77875]: Update the constant `DEFAULT_REPOSITORY_URL` in the
`GradlePluginsDefaultsUtil` class to
`https://repository-cdn.liferay.com/nexus/content/groups/public`.

## 5.4.37 - 2018-06-11

### Dependencies
- [LPS-82261]: Update the com.liferay.gradle.plugins dependency to version
3.12.38.

## 5.4.38 - 2018-06-12

### Dependencies
- [LPS-82261]: Update the com.liferay.gradle.plugins dependency to version
3.12.39.

## 5.4.39 - 2018-06-13

### Dependencies
- [LPS-82343]: Update the com.liferay.gradle.plugins dependency to version
3.12.40.

## 5.4.40 - 2018-06-13

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.plugins dependency to version
3.12.41.

## 5.4.41 - 2018-06-13

### Dependencies
- [LPS-77875]: Update the com.liferay.gradle.plugins dependency to version
3.12.42.

### Description
- [LPS-77875]: Update the constant `DEFAULT_REPOSITORY_URL` in the
`GradlePluginsDefaultsUtil` class to
`https://repository.liferay.com/nexus/content/groups/public`.

## 5.4.42 - 2018-06-13

### Dependencies
- [LPS-77875]: Update the com.liferay.gradle.plugins dependency to version
3.12.43.

### Description
- [LPS-77425]: Update the constant `DEFAULT_REPOSITORY_URL` in the
`GradlePluginsDefaultsUtil` class to
`https://repository-cdn.liferay.com/nexus/content/groups/public`.

## 5.4.43 - 2018-06-13

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.plugins dependency to version
3.12.44.

## 5.4.44 - 2018-06-14

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.45.

## 5.4.45 - 2018-06-15

### Dependencies
- [LPS-82534]: Update the com.liferay.gradle.plugins dependency to version
3.12.46.

## 5.4.46 - 2018-06-15

### Dependencies
- [LPS-76226]: Update the com.liferay.gradle.plugins dependency to version
3.12.47.

## 5.4.47 - 2018-06-18

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.48.

## 5.4.48 - 2018-06-19

### Dependencies
- [LPS-82420]: Update the com.liferay.gradle.plugins dependency to version
3.12.49.

## 5.4.49 - 2018-06-20

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.50.

## 5.4.50 - 2018-06-20

### Dependencies
- [LPS-82433]: Update the com.liferay.gradle.plugins dependency to version
3.12.51.

## 5.4.51 - 2018-06-20

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.52.

## 5.4.52 - 2018-06-21

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.53.

## 5.4.53 - 2018-06-22

### Commits
- [LPS-82568]: Disable liferay-npm-bundler analytics (735a64c5f8)
- [LPS-82568]: Disable liferay-npm-bundler analytics (547bb1f1bc)

### Dependencies
- [LPS-82568]: Update the com.liferay.gradle.plugins dependency to version
3.12.54.
- [LPS-82568]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.8.

### Description
- [LPS-82568]: Set the `npmRunBuild` task's `environment` property to
`LIFERAY_NPM_BUNDLER_NO_TRACKING=1` by default to disable a prompt from
[`liferay-npm-bundler`](https://github.com/liferay/liferay-npm-build-tools)
about tracking data.

## 5.4.54 - 2018-06-25

### Dependencies
- [LPS-82828]: Update the com.liferay.gradle.plugins dependency to version
3.12.55.

## 5.4.55 - 2018-06-25

### Dependencies
- [LPS-82857]: Update the com.liferay.gradle.plugins dependency to version
3.12.56.
- [LPS-82857]: Update the com.liferay.gradle.plugins.change.log.builder
dependency to version 1.1.0.

## 5.4.56 - 2018-06-26

### Dependencies
- [LPS-74608]: Update the com.liferay.gradle.plugins dependency to version
3.12.57.

## 5.4.57 - 2018-06-26

### Dependencies
- [LPS-82828]: Update the com.liferay.gradle.plugins dependency to version
3.12.58.

## 5.4.58 - 2018-06-26

### Commits
- [LPS-82960]: Read Jira ticket prefixes from a project property (97df74e4fd)

### Description
- [LPS-82960]: Set the project property `jira.project.keys` to add new ticket ID
prefixes to the `buildChangeLog` task.

## 5.4.59 - 2018-06-27

### Dependencies
- [LPS-79679]: Update the com.liferay.gradle.plugins dependency to version
3.12.59.

## 5.4.60 - 2018-06-28

### Dependencies
- [LPS-82343]: Update the com.liferay.gradle.plugins dependency to version
3.12.60.

## 5.4.61 - 2018-06-28

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.61.

## 5.4.62 - 2018-06-28

### Dependencies
- [LPS-83067]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.0.

## 5.4.63 - 2018-06-29

### Commits
- [LPS-83104]: Fix "updateFileVersions" task for subrepositories (bc10a1c85b)
- [LPS-83104]: Set "liferay.releng." default property values (31b4b56260)
- [LPS-83104]: Fix relative paths (7559382a10)

### Dependencies
- [LPS-83104]: Update the com.liferay.gradle.plugins dependency to version
3.12.62.

### Description
- [LPS-83104]: Set `liferay.releng.` default property values.
- [LPS-83104]: Fix publishing from sub-repositories.

## 5.4.64 - 2018-07-02

### Commits
- [LPS-82976]: Add test (9605b21a3a)
- [LPS-82976]: Add support for ".lfrbuild-portal-all" (ec5b548e9e)
- [LPS-82976]: Extract method for testing (870e818178)

### Dependencies
- [LPS-82976]: Update the com.liferay.gradle.plugins dependency to version
3.12.63.

### Description
- [LPS-82976]: Set the system property `build.profile` to `portal-all` so only
projects containing one of the following marker files are included:
	- `.lfrbuild-portal`
	- `.lfrbuild-portal-all`
	- `.lfrbuild-portal-all-private` when on private branches, or
	`.lfrbuild-portal-all-public` when on public branches
	- `.lfrbuild-portal-private` when on private branches, or
	`.lfrbuild-portal-public` when on public branches

## 5.4.65 - 2018-07-02

### Dependencies
- [LPS-83067]: Update the com.liferay.gradle.plugins dependency to version
3.12.64.
- [LPS-83067]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.1.

## 5.4.66 - 2018-07-03

### Dependencies
- [LPS-82828]: Update the com.liferay.gradle.plugins dependency to version
3.12.65.

## 5.4.67 - 2018-07-04

### Dependencies
- [LPS-77359]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.5.

## 5.4.68 - 2018-07-04

### Dependencies
- [LPS-82828]: Update the com.liferay.gradle.plugins dependency to version
3.12.66.

## 5.4.69 - 2018-07-05

### Dependencies
- [LPS-83220]: Update the com.liferay.gradle.plugins dependency to version
3.12.67.

## 5.4.70 - 2018-07-09

### Commits
- [LPS-83300]: Revert "LPS-73353 Simplify" since it is causing
"-Djunit.code.coverage" not read (1cd250fcc9)

### Description
- [LPS-83300]: Ensure the system property `junit.code.coverage` is being read.

## 5.4.71 - 2018-07-10

### Commits
- [LPS-82960]: Check the root project for valid 'jira.project.keys' (5bd8731a7c)

### Dependencies
- [LPS-82960]: Update the com.liferay.gradle.plugins dependency to version
3.12.68.
- [LPS-82960]: Update the com.liferay.gradle.plugins.change.log.builder
dependency to version 1.1.1.

### Description
- [LPS-82960]: Set the project property `jira.project.keys` for sub-repositories
to add new ticket ID prefixes for the `buildChangeLog` task.

## 5.4.72 - 2018-07-10

### Commits
- [LPS-82960]: Read 'ci.properties' file (17b9f54583)

### Description
- [LPS-82960]: Load the `ci.properties` file to read `jira.project.keys`.

## 5.4.73 - 2018-07-11

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.69.

## 5.4.74 - 2018-07-11

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.70.

## 5.4.75 - 2018-07-12

### Commits
- [LPS-82960]: Check '.gradle/gradle.properties' (00f0b240c0)

### Description
- [LPS-82960]: Check `gradle.properties` for `jira.project.keys` when running
the `buildChangeLog` task.

## 5.4.76 - 2018-07-13

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.71.

## 5.4.77 - 2018-07-13

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.72.

## 5.4.78 - 2018-07-13

### Commits
- [LPS-74544]: Auto SF (update cdn urls) (e6128bc643)

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.73.

### Description
- [LPS-74544]: Fixed URLs in `config-maven.gradle`
(`https://repository-cdn.liferay.com/nexus/content/repositories/`).

## 5.4.79 - 2018-07-14

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.74.

## 5.4.80 - 2018-07-16

### Dependencies
- [LPS-77699]: Update the com.liferay.gradle.plugins dependency to version
3.12.75.

## 5.4.81 - 2018-07-16

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.76.

## 5.4.82 - 2018-07-16

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.77.

## 5.4.83 - 2018-07-17

### Dependencies
- [LPS-77699]: Update the com.liferay.gradle.plugins dependency to version
3.12.78.

## 5.4.84 - 2018-07-17

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.79.

## 5.4.85 - 2018-07-17

### Dependencies
- [LPS-83520]: Update the com.liferay.gradle.plugins dependency to version
3.12.80.

## 5.4.86 - 2018-07-18

### Dependencies
- [LPS-83576]: Update the com.liferay.gradle.plugins dependency to version
3.12.81.

## 5.4.87 - 2018-07-18

### Dependencies
- [LPS-83483]: Update the com.liferay.gradle.plugins dependency to version
3.12.82.

## 5.4.88 - 2018-07-18

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.83.

## 5.4.89 - 2018-07-19

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.84.

## 5.4.90 - 2018-07-23

### Commits
- [LPS-82976]: Rename marker file (6c1f21bd80)

### Description
- [LPS-82976]: Change the marker file name `.lfrbuild-portal-all` to
`.lfrbuild-portal-deprecated`.

## 5.4.91 - 2018-07-24

### Commits
- [LPS-82960]: Revert "LPS-82960 Check the root project for valid
'jira.project.keys'" (7515175be0)
- [LPS-82960]: Revert "LPS-82960 Read 'ci.properties' file" (11572467c5)
- [LPS-82960]: Revert "LPS-82960 Check '.gradle/gradle.properties'" (054df182b8)
- [LPS-82976]: Fix test (cfe2651518)

### Dependencies
- [LPS-82960]: Update the com.liferay.gradle.plugins dependency to version
3.12.85.

### Description
- [LPS-82960]: Remove the logic that loaded the `ci.properties` file to read
the `jira.project.keys`. This property should be set in the `gradle.properties`
file.

## 5.4.92 - 2018-07-24

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.86.

## 5.4.93 - 2018-07-25

### Dependencies
- [LPS-83761]: Update the com.liferay.gradle.plugins dependency to version
3.12.87.

## 5.4.94 - 2018-07-25

### Dependencies
- [LPS-83705]: Update the com.liferay.gradle.plugins dependency to version
3.12.88.

## 5.4.95 - 2018-07-27

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.89.

## 5.4.96 - 2018-07-27

### Commits
- [LPS-83920]: Simplify logic (c92ca7f258)
- [LPS-83920]: Skip updateFileVersions for aspectj projects (4e552c1722)

### Description
- [LPS-83920]: Skip replacements of the `updateFileVersions` task for projects
in the `aspectj` directory.

## 5.4.97 - 2018-07-27

### Dependencies
- [LPS-78938]: Update the com.liferay.gradle.plugins dependency to version
3.12.90.

## 5.4.98 - 2018-07-27

### Commits
- [LPS-83920]: Use better variable name (fdb924e427)
- [LPS-83920]: Simplify logic (use GradleUtil#getRootDir) (def3735a40)
- [LPS-83920]: Update logic to check for
.lfrbuild-releng-skip-update-file-versions in parent directories (1edcf0913f)
- [LPS-83929]: Use regex to provide more flexibility (ba59abcd5e)
- [LPS-83929]: Use validator and save space (fb8fbd3d68)
- [LPS-83929]: Use ignore.project.prefixes to exclude projects from
writeArtifactPublishCommands (ea889a44b2)

### Description
- [LPS-83920]: Skip replacements of the `updateFileVersions` task if a
`.lfrbuild-releng-skip-update-file-versions` marker file is found in a parent
directory.
- [LPS-83929]: Add the ability to exclude certain projects from executing the
`writeArtifactPublishCommands` task by setting the property
`writeArtifactPublishCommands.ignore.project.regex`.

## 5.4.99 - 2018-07-30

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.91.

## 5.4.100 - 2018-07-31

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.92.

## 5.4.101 - 2018-08-01

### Dependencies
- [LPS-84039]: Update the com.liferay.gradle.plugins dependency to version
3.12.93.

## 5.4.102 - 2018-08-01

### Commits
- [LPS-84027]: Support Dependency Management plugin (12b016e5a4)

### Description
- [LPS-84027]: Fix error when using the [Gradle Dependency Management] plugin in
OSGi modules.

## 5.4.103 - 2018-08-01

### Commits
- [LPS-84027]: Add support for "SPRING_BOOT_JAVA_OPTS" (323d24d53f)

### Description
- [LPS-84027]: Add support for the `SPRING_BOOT_JAVA_OPTS` environment variable.

## 5.4.104 - 2018-08-01

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.94.

## 5.4.105 - 2018-08-01

### Commits
- [LPS-83168]: add new API and baseline (7c09171245)
- [LPS-83168]: rename and sort (65bf9afca4)
- [LPS-83168]: sort and rename (45434b8b46)
- [LPS-83168]: description (0ccc6bf405)
- [LPS-83168]: use FileUtil.writeProperties (750888a8e0)
- [LPS-83168]: add maven publication files (7de08aee34)
- [LPS-84055]: Exclude git ignored projects from writeArtifactPublishCommands
(6aba04a100)

## 5.5.0 - 2018-08-01

### Commits
- [LPS-83168]: Create new task only when publishing (22ea82063d)
- [LPS-83168]: Rename variable (05ba2f83f7)
- [LPS-83168]: Hide new task from "gradlew tasks" (8c75a62040)
- [LPS-83168]: Append single character (3f09f2deda)

### Dependencies
- [LPS-83168]: Update the com.liferay.gradle.plugins dependency to version
3.12.95.

### Description
- [LPS-83168]: Embed `pom.properties` and `pom.xml` when publishing OSGi modules
to Maven.
- [LPS-84055]: Automatically exclude Git ignored projects from executing the
`writeArtifactPublishCommands` task.

## 5.5.1 - 2018-08-02

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.96.

## 5.5.2 - 2018-08-02

### Commits
- [LPS-84055]: The printDependentArtifact should print the project directory when
it has a dependency with version 'default' (791984afc2)

### Dependencies
- [LPS-84055]: Update the com.liferay.gradle.plugins dependency to version
3.12.97.

### Description
- [LPS-84055]: Fix the `printDependentArtifact` task so it prints the project
directory when it has a dependency with version `default`.

## 5.5.3 - 2018-08-05

### Dependencies
- [LPS-83705]: Update the com.liferay.gradle.plugins dependency to version
3.12.98.

## 5.5.4 - 2018-08-06

### Dependencies
- [LPS-78033]: Update the com.liferay.gradle.plugins dependency to version
3.12.99.

## 5.5.5 - 2018-08-06

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.100.

## 5.5.6 - 2018-08-06

### Dependencies
- [LPS-84213]: Update the com.liferay.gradle.plugins dependency to version
3.12.101.

## 5.5.7 - 2018-08-06

### Commits
- [LPS-84055]: Update printDependentArtifact check (0ad9d832c1)

### Dependencies
- [LPS-84055]: Update the com.liferay.gradle.plugins dependency to version
3.12.102.

### Description
- [LPS-84055]: Fix the `printDependentArtifact` task so it prints the project
directory when it has a `compile*` dependency with the version `default`.

## 5.5.8 - 2018-08-07

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.103.

## 5.5.9 - 2018-08-08

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.104.

## 5.5.10 - 2018-08-09

### Commits
- [LPS-84055]: Add logging if a compile dependency has the version default
(a4671e63d5)
- [LPS-84055]: Remove check for "default" dependencies (use
hasProjectDependencies instead) (ab652fb69f)

### Description
- [LPS-84055]: Fix the `writeArtifactPublishCommands` task so it's not skipped
when the `test` dependency is version `default`.

## 5.5.11 - 2018-08-09

### Dependencies
- [LPS-84307]: Update the com.liferay.gradle.plugins dependency to version
3.12.105.

## 5.5.12 - 2018-08-09

### Commits
- [LPS-84313]: Configure the check task to depend on the testIntegration task
(917c38d7bc)
- [LPS-84313]: Automatically apply the com.liferay.test.integration.base plugin
to com.liferay.spring.boot.defaults (e7a931870b)

### Description
- [LPS-84313]: Update the `com.liferay.spring.boot.defaults` plugin to
automatically apply the `com.liferay.test.integration.base` plugin. Configure
the `check` task to depend on the `testIntegration` task.

## 5.5.13 - 2018-08-10

### Dependencies
- [LPS-84039]: Update the com.liferay.gradle.plugins dependency to version
3.12.106.

## 5.5.14 - 2018-08-13

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.107.

## 5.5.15 - 2018-08-15

### Dependencies
- [LPS-84039]: Update the com.liferay.gradle.plugins dependency to version
3.12.108.

## 5.5.16 - 2018-08-15

### Dependencies
- [LPS-84473]: Update the com.liferay.gradle.plugins dependency to version
3.12.109.

## 5.5.17 - 2018-08-15

### Dependencies
- [LPS-83790]: Update the com.liferay.gradle.plugins dependency to version
3.12.110.

## 5.5.18 - 2018-08-20

### Commits
- [LPS-84621]: Temporarily disable validateSchema tasks for CI (7a9fa51cc2)

### Description
- [LPS-84621]: Disable the `validateSchema` task for CI.

## 5.5.19 - 2018-08-21

### Commits
- [LPS-84624]: Set test task properties for com.liferay.spring.boot.defaults
(9d5228cc43)
- [LPS-84313]: Source formatting (no logic changes) (df628fdca8)

### Dependencies
- [LPS-84624]: Update the com.liferay.gradle.plugins dependency to version
3.12.111.

### Description
- [LPS-84624]: Update the `com.liferay.spring.boot.defaults` plugin to configure
the `test` and `testIntegration` tasks with sensible defaults.

## 5.5.20 - 2018-08-22

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.112.

## 5.5.21 - 2018-08-23

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.113.

## 5.5.22 - 2018-08-23

### Commits
- [LPS-83929]: Apply to printDependentArtifact task (efc71bc4bf)
- [LPS-83929]: Abstract out common logic (54307b9674)

### Dependencies
- [LPS-83067]: Update the com.liferay.gradle.plugins dependency to version
3.12.114.
- [LPS-83067]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.2.

### Description
- [LPS-83929]: Add the ability to exclude certain projects from executing the
`printDependentArtifact` task by setting the property
`printDependentArtifact.ignore.project.regex`.

## 5.5.23 - 2018-08-27

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.115.

## 5.5.24 - 2018-08-27

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.116.

## 5.5.25 - 2018-08-28

### Commits
- [LPS-84094]: Apply everywhere (1af8cb838a)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.plugins dependency to version
3.12.117.

### Description
- [LPS-84094]: Allow properties defined in a `gradle.properties` file to be
overridden by values defined in a `gradle-ext.properties` file.

## 5.5.26 - 2018-08-29

### Commits
- [LPS-84887]: Update Maven SCM properties (3583c2e053)

### Dependencies
- [LPS-84887]: Update the com.liferay.gradle.plugins dependency to version
3.12.118.

### Description
- [LPS-84887]: Update Maven SCM property default values in
`config-maven.gradle`:
	- `scm.connection` = `scm:git:git@github.com:liferay/liferay-portal.git`
	- `scm.developerConnection` =
`scm:git:git@github.com:liferay/liferay-portal.git`
	- `scm.url` = `http://github.com/liferay/liferay-portal`

## 5.5.27 - 2018-08-30

### Commits
- [LPS-84621]: Revert "LPS-84621 Temporarily disable validateSchema tasks for CI"
(179e9e552a)
- [LPS-84094]: Move reusable logic to gradle-util (7388cfa774)

### Dependencies
- [LPS-84621]: Update the com.liferay.gradle.plugins dependency to version
3.12.119.

### Description
- [LPS-84094]: Move `GradleUtil` methods to [Liferay Gradle Util] so it can be
used inside `build.gradle` files.
- [LPS-84621]: Enable the `validateSchema` task for CI.

## 5.5.28 - 2018-08-30

### Dependencies
- []: Update the com.liferay.gradle.plugins dependency to version 3.12.120.

## 5.5.29 - 2018-09-03

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.121.

## 5.5.30 - 2018-09-03

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.122.

## 5.5.31 - 2018-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.123.

## 5.5.32 - 2018-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.124.

## 5.5.33 - 2018-09-04

### Commits
- [LPS-70819]: Fix the compileJSP desintation directory (f813ebdc07)

### Description
- [LPS-70819]: Fix the `compileJSP.destinationDir` property when the
`jsp.precompile.from.source` project property is set to `true`.

## 5.5.34 - 2018-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.125.

## 5.5.35 - 2018-09-04

### Dependencies
- [LPS-85092]: Update the com.liferay.gradle.plugins dependency to version
3.12.126.

## 5.5.36 - 2018-09-05

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.127.

## 5.5.37 - 2018-09-06

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.128.

## 5.5.38 - 2018-09-10

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.129.

## 5.5.39 - 2018-09-10

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.130.

## 5.5.40 - 2018-09-11

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.131.

## 5.5.41 - 2018-09-11

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.132.

## 5.5.42 - 2018-09-11

### Commits
- [LPS-69453]: Fix for deployDependencies (643594fb13)

### Description
- [LPS-69453]: Avoid throwing an exception while running
`gradlew deployDependencies` when the value of the `Bundle-Symbolic-Name`
manifest header for the deployed JAR does not contain a period.

## 5.5.43 - 2018-09-11

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.133.

## 5.5.44 - 2018-09-12

### Dependencies
- [LPS-65845]: Update the com.liferay.gradle.plugins.cache dependency to version
1.0.13.

## 5.5.45 - 2018-09-12

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.134.

## 5.5.46 - 2018-09-13

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.135.

## 5.5.47 - 2018-09-17

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.136.

## 5.5.48 - 2018-09-17

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.137.
- [LPS-51081]: Update the com.liferay.gradle.plugins.patcher dependency to
version 1.0.14.

## 5.5.49 - 2018-09-18

### Dependencies
- [LPS-85035]: Update the com.liferay.gradle.plugins dependency to version
3.12.138.

## 5.5.50 - 2018-09-18

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.139.

## 5.5.51 - 2018-09-18

### Dependencies
- [LPS-85296]: Update the com.liferay.gradle.plugins dependency to version
3.12.140.

## 5.5.52 - 2018-09-19

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.141.

## 5.5.53 - 2018-09-20

### Dependencies
- [LPS-71117]: Update the com.liferay.gradle.plugins dependency to version
3.12.142.

## 5.5.54 - 2018-09-24

### Dependencies
- [LPS-85677]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.3.
- [LPS-85609]: Update the com.liferay.gradle.plugins dependency to version
3.12.143.

## 5.5.56 - 2018-09-24

### Dependencies
- [LPS-85678]: Update the com.liferay.gradle.plugins dependency to version
3.12.144.
- [LPS-85678]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.4.

## 5.5.57 - 2018-09-24

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.145.

## 5.5.58 - 2018-09-25

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.146.

## 5.5.59 - 2018-09-25

### Dependencies
- [LPS-85556]: Update the com.liferay.gradle.plugins dependency to version
3.12.147.

## 5.5.60 - 2018-09-25

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.148.

## 5.5.61 - 2018-09-27

### Commits
- [LPS-85771]: Set test.outputs.upToDateWhen to false to always run tests
(d8dcc50f45)

### Description
- [LPS-85771]: Always set the `test` and `testIntegration` task's up-to-date
check to `false` for OSGi modules.

## 5.5.62 - 2018-09-30

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.149.

## 5.5.63 - 2018-10-01

### Commits
- [LPS-85905]: Remove hard coded values (05a029534d)
- [LPS-85905]: Add marker file check to prevent the testIntegration task from
failing (d9cb18aa53)
- [LPS-85905]: No logic changes (ed2b2f80ac)
- [LPS-85845]: Add test-util module to the list of modules not to deploy
(852455d6d6)

### Description
- [LPS-85905]: When on Jenkins, fail the `testIntegration` task if any dependent
projects defined in the `testIntegrationCompile` configuration do not have a
`lfrbuild-ci`, `lfrbuild-ci-skip-test-integration-check`, or
`.lfrbuild-portal` marker file.

## 5.5.64 - 2018-10-01

### Dependencies
- [LPS-84138]: Update the com.liferay.gradle.plugins dependency to version
3.12.150.

## 5.5.65 - 2018-10-03

### Dependencies
- [LPS-85959]: Update the com.liferay.gradle.plugins dependency to version
3.12.151.
- [LPS-85959]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.9.

## 5.5.66 - 2018-10-03

### Commits
- [LPS-85987]: Fix NPM invalid version error for the npmRunBuild task
(55264a3729)

### Description
- [LPS-85987]: When running the `npmRunBuild` task on Jenkins, check the version
in `bnd.bnd`, `package.json`, and `package-lock.json` for an invalid
`.hotfix-xxx-xxx` qualifier. If it exists, temporarily change it to a valid
qualifier before the task runs and change it back after it completes.

## 5.5.67 - 2018-10-03

### Commits
- [LPS-85987]: Fix NPE (fe4d00f176)

### Description
- [LPS-85987]: Use the `TaskContainer` to find `npmRunBuild` tasks to avoid a
`NullPointerException` during the configuration phase.

## 5.5.68 - 2018-10-03

### Commits
- [LPS-85987]: Always check the version for the npmRunBuild task (eca566a455)

### Dependencies
- [LPS-85987]: Update the com.liferay.gradle.plugins dependency to version
3.12.152.

### Description
- [LPS-85987]: When running the `npmRunBuild` task on Jenkins, always check for
an invalid `.hotfix-xxx-xxx` qualifier.

## 5.5.69 - 2018-10-04

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.153.

## 5.5.70 - 2018-10-05

### Dependencies
- [LPS-80388]: Update the com.liferay.gradle.plugins dependency to version
3.12.154.
- [LPS-80388]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.5.

## 5.5.71 - 2018-10-07

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.155.

## 5.5.72 - 2018-10-08

### Commits
- [LPS-85987]: Disable node_modules cache when the env variable
FIX_PACKS_RELEASE_ENVIRONMENT is set (8f42f3d31c)

### Description
- [LPS-85987]: Do not use the `node_modules` cache when the
`FIX_PACKS_RELEASE_ENVIRONMENT"` environment variable is set.

## 5.5.73 - 2018-10-08

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.156.

## 5.5.74 - 2018-10-09

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.12.157.

## 5.5.75 - 2018-10-09

### Commits
- [LPS-85987]: The version in the bnd file doesn't have quotes (872e2ef3ee)

### Description
- [LPS-85987]: When running the `npmRunBuild` task on Jenkins, check the
`.hotfix-xxx-xxx` qualifier in `bnd.bnd` files.

## 5.5.76 - 2018-10-09

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.12.158.

## 5.5.77 - 2018-10-09

### Commits
- [LPS-85987]: Source formatting (de9bbeaf08)
- [LPS-85987]: Move afterEvaluate logic after doLast (ddcbcb8a2e)
- [LPS-85987]: Change the version before running any tasks (bfb873a042)

### Dependencies
- [LPS-85959]: Update the com.liferay.gradle.plugins dependency to version
3.12.159.
- [LPS-85959]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.10.

### Description
- [LPS-85987]: When running the `npmRunBuild` task on Jenkins, change the
`.hotfix-xxx-xxx` qualifier before running any tasks.

## 5.5.78 - 2018-10-10

### Dependencies
- [LPS-86308]: Update the com.liferay.gradle.plugins dependency to version
3.12.160.

## 5.5.79 - 2018-10-10

### Commits
- [LPS-85987]: Consolidate (no logic changes) (cfbcbe0f26)
- [LPS-85987]: Check the qualifier for all NPM tasks (a5e6af2f99)

### Description
- [LPS-85987]: When running any NPM task on Jenkins, check for an invalid
`.hotfix-xxx-xxx` qualifier before running the task.

## 5.5.80 - 2018-10-12

### Commits
- [LPS-84119]: SF, declare when used (b1efafe540)

### Dependencies
- [LPS-86371]: Update the com.liferay.gradle.plugins dependency to version
3.12.161.

## 5.5.81 - 2018-10-15

### Dependencies
- [LPS-85954]: Update the com.liferay.gradle.plugins dependency to version
3.12.162.

## 5.5.82 - 2018-10-15

### Dependencies
- [LPS-86362]: Update the com.liferay.gradle.plugins dependency to version
3.12.163.

## 5.5.83 - 2018-10-15

### Commits
- [LPS-86408]: Set Java version to 1.8 for OSGi projects (298e44a150)

### Description
- [LPS-86408]: Set the default Java version to 1.8 for OSGi projects.

## 5.5.84 - 2018-10-15

### Dependencies
- [LPS-86324]: Update the com.liferay.gradle.plugins dependency to version
3.12.164.

## 5.5.85 - 2018-10-16

### Dependencies
- [LPS-85849]: Update the com.liferay.gradle.plugins dependency to version
3.12.165.

## 5.5.86 - 2018-10-16

### Dependencies
- [LPS-85556]: Update the com.liferay.gradle.plugins dependency to version
3.12.166.

## 5.5.87 - 2018-10-16

### Dependencies
- [LPS-85678]: Update the com.liferay.gradle.plugins dependency to version
3.12.167.
- [LPS-85678]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.6.

## 5.5.88 - 2018-10-16

### Dependencies
- [LPS-85678]: Update the com.liferay.gradle.plugins dependency to version
3.12.168.
- [LPS-85678]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.7.

## 5.5.89 - 2018-10-17

### Dependencies
- [LPS-86447]: Update the com.liferay.gradle.plugins dependency to version
3.12.169.

## 5.5.90 - 2018-10-17

### Dependencies
- [LPS-86018]: Update the com.liferay.gradle.plugins dependency to version
3.13.0.

## 5.5.91 - 2018-10-17

### Commits
- [LPS-86477]: Make it a system property (94c0ea79b6)
- [LPS-86477]: Allow the test ignoreFailures property to be overridden
(530747c2e5)

### Description
- [LPS-86477]: Allow the `test` and `testIntegration` tasks' `ignoreFailures`
property to be overridden for OSGi projects. This can be done via command line
argument:
	- `./gradlew test -Dtest.ignore.failures=false`
	- `./gradlew testIntegration -DtestIntegration.ignore.failures=false`

## 5.5.92 - 2018-10-17

### Dependencies
- [LPS-86413]: Update the com.liferay.gradle.plugins dependency to version
3.13.1.

## 5.5.93 - 2018-10-18

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.2.

## 5.5.94 - 2018-10-18

### Dependencies
- [LPS-86493]: Update the com.liferay.gradle.plugins dependency to version
3.13.3.

## 5.5.95 - 2018-10-18

### Dependencies
- [LPS-85556]: Update the com.liferay.gradle.plugins dependency to version
3.13.4.

## 5.5.96 - 2018-10-22

### Dependencies
- [LPS-86576]: Update the com.liferay.gradle.plugins dependency to version
3.13.5.
- [LPS-86576]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.11.

## 5.5.97 - 2018-10-22

### Dependencies
- [LPS-86581]: Update the com.liferay.gradle.plugins dependency to version
3.13.6.

## 5.5.98 - 2018-10-22

### Dependencies
- [LPS-86583]: Update the com.liferay.gradle.plugins dependency to version
3.13.7.
- [LPS-86583]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.8.

## 5.5.99 - 2018-10-23

### Commits
- [LPS-86500]: Remove portal dependencies from the test compile classpath
(971134a966)

### Description
- [LPS-86500]: Remove the `portal` configuration from the `test` source set's
compile classpath.

## 5.5.100 - 2018-10-24

### Dependencies
- [LPS-86583]: Update the com.liferay.gradle.plugins.baseline dependency to
version 1.3.9.

## 5.5.101 - 2018-10-24

### Commits
- [LPS-86669]: The printDependentArtifact task should ignore jsCompile project
dependencies (a6a38b623b)

### Description
- [LPS-86669]: Ignore `jsCompile` project dependencies for the
`printDependentArtifact` and `writeArtifactPublishCommands` tasks.

## 5.5.102 - 2018-10-25

### Commits
- [LPS-86707]: Run npm ci instead of npm install (c97337860b)

### Description
- [LPS-86707]: Configure the `npmInstall` task to run `npm ci` instead of
`npm install` on all branches after `7.0.x`.

## 5.5.103 - 2018-10-29

### Dependencies
- [LPS-86549]: Update the com.liferay.gradle.plugins dependency to version
3.13.8.

## 5.5.104 - 2018-10-29

### Commits
- [LPS-86583]: Resolve dynamic dependency during the execution phase
(9593e95c96)

### Dependencies
- [LPS-86583]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.0.

## 5.5.105 - 2018-11-02

### Dependencies
- [LPS-81704]: Update the com.liferay.gradle.plugins dependency to version
3.13.9.

## 5.5.106 - 2018-11-08

### Dependencies
- [LPS-86916]: Update the com.liferay.gradle.plugins dependency to version
3.13.10.

## 5.5.107 - 2018-11-08

### Dependencies
- [LPS-86916]: Update the com.liferay.gradle.plugins dependency to version
3.13.11.

## 5.5.108 - 2018-11-08

### Commits
- [LPS-87006]: Retrieve sources dependencies only if they exist (2d17cf1a7f)
- [LPS-87006]: Add compileInclude dependencies to the sources jar (d1e2536d09)

### Description
- [LPS-87006]: Update the `jarSources` and `jarSourcesCommercial` tasks to
include `compileInclude` configuration dependency sources in the respective JAR
files.

## 5.5.109 - 2018-11-09

### Commits
- [LPS-87146]: Proper var naming so we don't confuse people (721f2b4a75)
- [LPS-87146]: Skip bad third-party sources jars (9945bcb1f6)

### Description
- [LPS-87006]: Skip copying `compileInclude` configuration dependency sources if
the sources JAR contains invalid entries.

## 5.5.110 - 2018-11-13

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)

### Dependencies
- [LPS-87293]: Update the com.liferay.gradle.plugins dependency to version
3.13.12.

## 5.5.111 - 2018-11-15

### Dependencies
- [LPS-87366]: Update the com.liferay.gradle.plugins dependency to version
3.13.13.

## 5.5.112 - 2018-11-16

### Dependencies
- [LPS-87465]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.12.
- [LPS-87466]: Update the com.liferay.gradle.plugins dependency to version
3.13.14.
- [LPS-87466]: Update the com.liferay.gradle.plugins.app.javadoc.builder
dependency to version 1.2.1.
- [LPS-87466]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.1.
- [LPS-87466]: Update the com.liferay.gradle.plugins.cache dependency to version
1.0.14.
- [LPS-87466]: Update the com.liferay.gradle.plugins.change.log.builder
dependency to version 1.1.2.
- [LPS-87466]: Update the com.liferay.gradle.plugins.dependency.checker
dependency to version 1.0.2.
- [LPS-87466]: Update the com.liferay.gradle.plugins.lang.merger dependency to
version 1.1.1.
- [LPS-87466]: Update the com.liferay.gradle.plugins.patcher dependency to
version 1.0.15.
- [LPS-87466]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.6.
- [LPS-87466]: Update the com.liferay.gradle.plugins.whip dependency to version
1.0.6.

## 5.5.113 - 2018-11-16

### Dependencies
- [LPS-87371]: Update the com.liferay.gradle.plugins dependency to version
3.13.15.

## 5.5.114 - 2018-11-16

### Commits
- [LPS-87477]: Lazily evaluate the property (fix for Gradle 4.10.2) (c7338f80b3)

### Description
- [LPS-87477]: Lazily evaluate the `replacement` property to avoid a
`NumberFormatException` during the configuration phase.

## 5.5.115 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.plugins dependency to version
3.13.16.
- [LPS-87466]: Update the com.liferay.gradle.plugins.app.javadoc.builder
dependency to version 1.2.2.
- [LPS-87466]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.2.
- [LPS-87466]: Update the com.liferay.gradle.plugins.cache dependency to version
1.0.15.
- [LPS-87466]: Update the com.liferay.gradle.plugins.change.log.builder
dependency to version 1.1.3.
- [LPS-87466]: Update the com.liferay.gradle.plugins.dependency.checker
dependency to version 1.0.3.
- [LPS-87466]: Update the com.liferay.gradle.plugins.lang.merger dependency to
version 1.1.2.
- [LPS-87466]: Update the com.liferay.gradle.plugins.patcher dependency to
version 1.0.16.
- [LPS-87466]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.7.
- [LPS-87466]: Update the com.liferay.gradle.plugins.whip dependency to version
1.0.7.

## 5.5.116 - 2018-11-19

### Dependencies
- [LPS-87503]: Update the com.liferay.gradle.plugins dependency to version
3.13.17.

## 5.5.117 - 2018-11-20

### Commits
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-87419]: Update the com.liferay.gradle.plugins dependency to version
3.13.18.
- [LPS-85609]: Update the com.liferay.gradle.plugins.cache dependency to version
1.0.16.

## 5.5.118 - 2018-11-20

### Commits
- [LPS-86806]: Standardize formatting for arrays (eed36c439a)

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.19.

## 5.5.119 - 2018-11-22

### Dependencies
- [LPS-87776]: Update the com.liferay.gradle.plugins dependency to version
3.13.20.
- [LPS-87776]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.3.

## 5.5.120 - 2018-11-26

### Commits
- [LPS-87821]: Copy the compiled JSPs to the work directory (9f86cb4745)
- [LPS-86806]: Fix line breaks in return statements (29ae0ec415)

## 5.5.121 - 2018-11-26

### Dependencies
- [LPS-87839]: Update the com.liferay.gradle.plugins dependency to version
3.13.21.
- [LPS-87839]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.4.

## 5.5.122 - 2018-11-27

### Dependencies
- [LPS-86406]: Update the com.liferay.gradle.plugins dependency to version
3.13.22.

## 5.5.123 - 2018-11-27

### Dependencies
- [LPS-87839]: Update the com.liferay.gradle.plugins dependency to version
3.13.23.
- [LPS-87839]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.5.

## 5.5.124 - 2018-11-28

### Commits
- [LPS-87890]: Add option to override the artifactAppendix extension property
(870e0f334b)
- [LPS-86806]: Fix formatting in if statements (6a6264dd68)

### Dependencies
- [LPS-86806 LPS-87890]: Update the com.liferay.gradle.plugins dependency to
version 3.13.24.

### Description
- [LPS-87890]: Override the `artifactAppendix` extension object by setting the
project property `artifactAppendix`.

## 5.5.125 - 2018-11-28

### Commits
- [LPS-87890]: allow version to be overwritten too (c570ca4d5b)

### Description
- [LPS-87890]: Override the `artifactVersion` extension object by setting the
project property `artifactVersion`.

## 5.5.126 - 2018-11-28

### Dependencies
- [LPS-87890]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.8.

## 5.5.127 - 2018-11-29

### Dependencies
- [LPS-87936]: Update the com.liferay.gradle.plugins dependency to version
3.13.25.

## 5.5.128 - 2018-11-30

### Dependencies
- [LPS-87978]: Update the com.liferay.gradle.plugins dependency to version
3.13.26.

## 5.5.129 - 2018-12-03

### Dependencies
- [LPS-86406]: Update the com.liferay.gradle.plugins dependency to version
3.13.27.

## 5.5.130 - 2018-12-03

### Commits
- [LPS-86806]: Fix formatting in class definitions (304bcc73cd)

### Dependencies
- [LPS-85828]: Update the com.liferay.gradle.plugins dependency to version
3.13.28.

## 5.5.131 - 2018-12-03

### Dependencies
- [LPS-66010]: Update the com.liferay.gradle.plugins dependency to version
3.13.29.

## 5.5.132 - 2018-12-04

### Dependencies
- [LPS-88171]: Update the com.liferay.gradle.plugins dependency to version
3.13.30.

## 5.5.133 - 2018-12-04

### Dependencies
- [LPS-87471]: Update the com.liferay.gradle.plugins dependency to version
3.13.31.

## 5.5.134 - 2018-12-05

### Dependencies
- [LPS-88186]: Update the com.liferay.gradle.plugins dependency to version
3.13.32.

## 5.5.135 - 2018-12-05

### Commits
- [LPS-88226]: Add null check for the release .releng directory (e62548266f)

### Description
- [LPS-88226]: Add a `null` check to avoid throwing a `NullPointerException`
during the execution of the `uploadArchives` task.

## 5.5.136 - 2018-12-05

### Dependencies
- [LPS-88223]: Update the com.liferay.gradle.plugins dependency to version
3.13.33.

## 5.5.137 - 2018-12-06

### Dependencies
- [LPS-88186]: Update the com.liferay.gradle.plugins dependency to version
3.13.34.

## 5.5.138 - 2018-12-07

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.35.

## 5.5.139 - 2018-12-10

### Dependencies
- [LPS-81706]: Update the com.liferay.gradle.plugins dependency to version
3.13.36.

## 5.5.140 - 2018-12-10

### Dependencies
- [LPS-88171]: Update the com.liferay.gradle.plugins dependency to version
3.13.37.

## 5.5.141 - 2018-12-10

### Commits
- [LPS-88306]: Use NPM devDependencies to create the .digest for subrepositories
(43c2df985c)
- [LPS-88306]: Use Gradle projects to create the .digest for portal (99f5f78d15)
- [LPS-88306]: Use constant (no logic changes) (e2061de9bc)
- [LPS-88306]: Create a constant for parent theme project names (74a2f012f8)

### Description
- [LPS-88306]: Update the `writeParentThemesDigest` task to generate the
`.digest` file for theme projects in subrepositories.

## 5.5.142 - 2018-12-11

### Commits
- [LPS-88314]: Wordsmith (fc7cc6c6b1)
- [LPS-88314]: Check the dependency versions in imported-files.properties
(cd23dbe532)

### Description
- [LPS-88314]: Add a check to reject using snapshot artifacts for the
`importFiles` task.

## 5.5.143 - 2018-12-11

### Commits
- [LPS-88314]: Wordsmith (909d71d5ae)

### Dependencies
- [LPS-88183]: Update the com.liferay.gradle.plugins dependency to version
3.13.38.

## 5.5.144 - 2018-12-12

### Commits
- [LPS-88306]: Sort alphabetically (be6f3bb452)
- [LPS-88306]: Download the NPM parent theme sources before checking the .digest
file (5ad2b99100)
- [LPS-88306]: Check if the project is in a subrepository (de2ee59121)
- [LPS-88306]: Rename method to isSubrepository (9154e35a82)
- [LPS-88306]: Abstract out reusable logic (f7d6be4924)

### Description
- [LPS-88306]: Fix the `printStaleArtifact` and `writeArtifactPublishCommands`
tasks by downloading the NPM parent theme sources before checking if the
respective `.digest` file needs to be updated.

## 5.5.145 - 2018-12-13

### Commits
- [LPS-86806]: Always add line breaks for class with empty body (123fd7eee9)
- [LPS-88314]: Fix minor typo (71f3bf0b38)

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.39.

## 5.5.146 - 2018-12-14

### Dependencies
- [LPS-87488]: Update the com.liferay.gradle.plugins dependency to version
3.13.40.

## 5.5.147 - 2018-12-14

### Dependencies
- [LPS-88181]: Update the com.liferay.gradle.plugins dependency to version
3.13.41.

## 5.5.148 - 2018-12-17

### Dependencies
- [LPS-87590]: Update the com.liferay.gradle.plugins dependency to version
3.13.42.

## 5.5.149 - 2018-12-18

### Commits
- [LPS-88319]: Include modules projects if settings.gradle is at the portal root
directory (ae535b5ab2)
- [LPS-88319]: Remove unneeded parameter (no logic changes) (9fca881927)
- [LPS-88319]: allow settings plugin to ignore a top level project without
ignoring its subprojects (6819109769)

### Description
- [LPS-88319]: Automatically include projects in the `modules` directory if a
`settings.gradle` file is located at the portal root directory.

## 5.5.150 - 2018-12-19

### Dependencies
- [LPS-88552]: Update the com.liferay.gradle.plugins dependency to version
3.13.43.

## 5.5.151 - 2018-12-19

### Dependencies
- [LPS-88382]: Update the com.liferay.gradle.plugins dependency to version
3.13.44.
- [LPS-88382]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.6.

## 5.5.152 - 2018-12-19

### Commits
- [LPS-88588]: Loosen regex pattern to check for spaces or a tab (356b010754)

### Description
- [LPS-88588]: Change the constant `jsonVersionPattern` in the
`GradlePluginsDefaultsUtil` class to allow for two spaces or a tab so the
`updateVersion` task can find the version in the `package-lock.json` file.

## 5.5.153 - 2018-12-20

### Commits
- [LPS-88382]: release temp revert (4fb166649a)
- [LPS-88382]: release temp revert (191c822378)

### Dependencies
- [LPS-88382]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.7.

## 5.5.154 - 2018-12-20

### Commits
- [LPS-88382]: release temp revert (414be58373)

### Dependencies
- [LPS-88382]: Update the com.liferay.gradle.plugins dependency to version
3.13.45.

## 5.5.155 - 2018-12-20

### Dependencies
- [LPS-88382]: Update the com.liferay.gradle.plugins dependency to version
3.13.46.
- [LPS-88382]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.8.

## 5.5.156 - 2018-12-20

### Dependencies
- [LPS-88170]: Update the com.liferay.gradle.plugins dependency to version
3.13.47.

## 5.5.157 - 2019-01-02

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.48.

## 5.5.158 - 2019-01-03

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.49.

## 5.5.159 - 2019-01-05

### Dependencies
- [LPS-41848]: Update the com.liferay.gradle.plugins dependency to version
3.13.50.

## 5.5.160 - 2019-01-07

### Dependencies
- [LPS-87479]: Update the com.liferay.gradle.plugins dependency to version
3.13.51.
- [LPS-87479]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.13.

## 5.5.161 - 2019-01-08

### Dependencies
- [LPS-88823]: Update the com.liferay.gradle.plugins dependency to version
3.13.52.

## 5.5.162 - 2019-01-08

### Commits
- [LPS-88859]: Remove hardcoded logic that checks for a previous published
version (d5e6ac29f2)

### Description
- [LPS-88859]: Remove the logic that sets the `baseline.onlyIf` property to
`false` when there is no previous published version for the project.

## 5.5.163 - 2019-01-08

### Dependencies
- [LPS-88903]: Update the com.liferay.gradle.plugins dependency to version
3.13.53.
- [LPS-88903]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.0.9.

## 5.5.164 - 2019-01-08

### Dependencies
- [LPS-87469]: Update the com.liferay.gradle.plugins dependency to version
3.13.54.

## 5.5.165 - 2019-01-09

### Dependencies
- [LPS-88909]: Update the com.liferay.gradle.plugins dependency to version
3.13.55.
- [LPS-88909]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.14.

## 5.5.166 - 2019-01-09

### Dependencies
- [LPS-87479]: Update the com.liferay.gradle.plugins dependency to version
3.13.56.
- [LPS-87479]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.15.

## 5.5.167 - 2019-01-11

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.57.

## 5.5.168 - 2019-01-13

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.58.

## 5.5.169 - 2019-01-14

### Dependencies
- [LPS-89126]: Update the com.liferay.gradle.plugins dependency to version
3.13.59.
- [LPS-89126]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.16.

## 5.5.170 - 2019-01-16

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.60.

## 5.5.171 - 2019-01-16

### Dependencies
- [LPS-88909]: Update the com.liferay.gradle.plugins dependency to version
3.13.61.
- [LPS-88909]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.18.
- [LPS-88909]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.17.

## 5.5.172 - 2019-01-16

### Dependencies
- [LPS-89228]: Update the com.liferay.gradle.plugins dependency to version
3.13.62.

## 5.5.173 - 2019-01-17

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.63.

## 5.5.174 - 2019-01-21

### Commits
- [LPS-89135]: Expose plugin with ID com.liferay.go.defaults (620e41395f)
- [LPS-89135]: Configure Liferay Go defaults (d2a32d0d4e)

### Dependencies
- [LPS-89135]: Update the com.liferay.gradle.plugins.go dependency to version
1.0.0.

### Description
- [LPS-89135]: Add the plugin `com.liferay.go.defaults` to configure Go projects
according to Liferay defaults.
- [LPS-89135]: Set the [Liferay Gradle Plugins Go] dependency to version 1.0.0.

## 5.5.175 - 2019-01-22

### Commits
- [LPS-89415]: Configure Liferay defaults (ee75859e84)

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.64.

### Description
- [LPS-89415]: Configure REST projects according to Liferay defaults.

## 5.5.176 - 2019-01-22

### Dependencies
- [LPS-89388]: Update the com.liferay.gradle.plugins dependency to version
3.13.65.

## 5.5.177 - 2019-01-22

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.66.

## 5.5.178 - 2019-01-22

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.67.

## 5.5.179 - 2019-01-23

### Dependencies
- [LPS-89445 LPS-89457]: Update the com.liferay.gradle.plugins dependency to
version 3.13.68.

## 5.5.180 - 2019-01-23

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.13.69.

## 5.5.181 - 2019-01-24

### Dependencies
- [LPS-89369]: Update the com.liferay.gradle.plugins dependency to version
3.13.70.
- [LPS-89436]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.19.

## 5.5.182 - 2019-01-24

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.71.

## 5.5.183 - 2019-01-24

### Commits
- [LPS-89415]: Set the copyright for Liferay (22780a1564)

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.72.

## 5.5.184 - 2019-01-24

### Dependencies
- [LPS-88665]: Update the com.liferay.gradle.plugins dependency to version
3.13.73.

## 5.5.185 - 2019-01-24

### Dependencies
- [LPS-88665]: Update the com.liferay.gradle.plugins dependency to version
3.13.74.

## 5.5.186 - 2019-01-24

### Dependencies
- [LPS-88665]: Update the com.liferay.gradle.plugins dependency to version
3.13.75.

## 5.5.187 - 2019-01-25

### Commits
- [LPS-89415]: Update args (copyright.file, rest.config.file, rest.openapi.file)
(170da107c5)

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.76.

## 5.5.188 - 2019-01-26

### Dependencies
- [LPS-89567 LPS-89568]: Update the com.liferay.gradle.plugins dependency to
version 3.13.77.

## 5.5.189 - 2019-01-27

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.78.

## 5.5.190 - 2019-01-28

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.79.

## 5.5.191 - 2019-01-29

### Dependencies
- [LPS-88665]: Update the com.liferay.gradle.plugins dependency to version
3.13.80.

## 5.5.192 - 2019-01-29

### Dependencies
- [LPS-88665]: Update the com.liferay.gradle.plugins dependency to version
3.13.81.

## 5.5.193 - 2019-01-30

### Dependencies
- [LPS-69035]: Update the com.liferay.gradle.plugins dependency to version
3.13.82.

## 5.5.194 - 2019-01-30

### Dependencies
- [LPS-88851]: Update the com.liferay.gradle.plugins dependency to version
3.13.83.

## 5.5.195 - 2019-01-31

### Dependencies
- [LPS-89274]: Update the com.liferay.gradle.plugins dependency to version
3.13.84.

## 5.5.196 - 2019-01-31

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.13.85.

## 5.5.197 - 2019-01-31

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.86.

## 5.5.198 - 2019-02-04

### Dependencies
- [LPS-89916]: Update the com.liferay.gradle.plugins dependency to version
3.13.87.
- [LPS-89916]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.20.

## 5.5.199 - 2019-02-04

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.88.

## 5.5.200 - 2019-02-04

### Dependencies
- [LPS-89874]: Update the com.liferay.gradle.plugins dependency to version
3.13.89.

## 5.5.201 - 2019-02-06

### Commits
- [LPS-89415]: Enable portal tools testing in subrepos (3106a6de4a)

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.90.

## 5.5.202 - 2019-02-07

### Commits
- [LPS-89415]: Update buildREST arguments to parse multiple OpenAPI definitions
(29c9e1e6c3)

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.91.

## 5.5.203 - 2019-02-07

### Dependencies
- [LRQA-46630]: Update the com.liferay.gradle.plugins dependency to version
3.13.93.
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.92.

## 5.5.205 - 2019-02-09

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.94.

## 5.5.206 - 2019-02-11

### Commits
- [LPS-90204]: Preserve Node.js/NPM versions for 7.1.x (75338282e2)

### Dependencies
- [LPS-90204]: Update the com.liferay.gradle.plugins dependency to version
3.13.95.

## 5.5.207 - 2019-02-11

### Dependencies
- [LPS-89456]: Update the com.liferay.gradle.plugins dependency to version
3.13.96.

## 5.5.208 - 2019-02-12

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.97.

## 5.5.209 - 2019-02-12

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.98.

## 5.5.210 - 2019-02-13

### Dependencies
- [LPS-86853]: Update the com.liferay.gradle.plugins dependency to version
3.13.99.

## 5.5.211 - 2019-02-13

### Dependencies
- [LPS-89456]: Update the com.liferay.gradle.plugins dependency to version
3.13.100.

## 5.5.212 - 2019-02-13

### Dependencies
- [LPS-90378]: Update the com.liferay.gradle.plugins dependency to version
3.13.101.

## 5.5.213 - 2019-02-14

### Dependencies
- [LRDOCS-6300]: Update the com.liferay.gradle.plugins dependency to version
3.13.102.

## 5.5.214 - 2019-02-14

### Dependencies
- [LPS-90523]: Update the com.liferay.gradle.plugins dependency to version
3.13.103.

## 5.5.215 - 2019-02-14

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.104.

## 5.5.216 - 2019-02-15

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.105.

## 5.5.217 - 2019-02-18

### Dependencies
- [LPS-90380]: Update the com.liferay.gradle.plugins dependency to version
3.13.106.

## 5.5.218 - 2019-02-19

### Commits
- [LPS-89014]: Allow developers to override the portal root directory
(89aadb3036)

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.107.

## 5.5.219 - 2019-02-20

### Dependencies
- [LPS-89456]: Update the com.liferay.gradle.plugins dependency to version
3.13.108.

## 5.5.220 - 2019-02-20

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.109.

## 5.5.221 - 2019-02-20

### Dependencies
- [LPS-90945]: Update the com.liferay.gradle.plugins dependency to version
3.13.110.
- [LPS-90945]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.21.

## 5.5.222 - 2019-02-20

### Dependencies
- [LPS-89637]: Update the com.liferay.gradle.plugins dependency to version
3.13.111.

## 5.5.223 - 2019-02-21

### Dependencies
- [LPS-89637]: Update the com.liferay.gradle.plugins dependency to version
3.13.112.

## 5.5.224 - 2019-02-22

### Dependencies
- [LPS-74544]: Update the com.liferay.gradle.plugins dependency to version
3.13.113.

## 5.5.225 - 2019-02-26

### Dependencies
- [LPS-91231]: Update the com.liferay.gradle.plugins dependency to version
3.13.114.

## 5.5.226 - 2019-02-27

### Dependencies
- [LPS-89874]: Update the com.liferay.gradle.plugins dependency to version
3.13.115.

## 5.5.227 - 2019-02-27

### Dependencies
- [LPS-91343]: Update the com.liferay.gradle.plugins dependency to version
3.13.116.

## 5.5.228 - 2019-02-27

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.117.

## 5.5.229 - 2019-02-28

### Dependencies
- [LPS-91420]: Update the com.liferay.gradle.plugins dependency to version
3.13.118.

## 5.5.230 - 2019-03-01

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.119.

## 5.5.231 - 2019-03-04

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.120.

## 5.5.232 - 2019-03-04

### Dependencies
- [LPS-91420]: Update the com.liferay.gradle.plugins dependency to version
3.13.121.

## 5.5.233 - 2019-03-05

### Commits
- [LPS-91463]: Add deployConfigs task (1ed70f6a07)

### Dependencies
- [LPS-91463]: Update the com.liferay.gradle.plugins dependency to version
3.13.122.

## 5.5.234 - 2019-03-06

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.123.

## 5.5.235 - 2019-03-07

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.124.

## 5.5.236 - 2019-03-08

### Commits
- [LPS-91846]: Don't replace '-' with '.' in the suffix, don't change
package.json or package-lock.json back after the npm step (f41d5742bc)

## 5.5.237 - 2019-03-10

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.125.

## 5.5.238 - 2019-03-11

### Dependencies
- [LPS-89874]: Update the com.liferay.gradle.plugins dependency to version
3.13.126.

## 5.5.239 - 2019-03-11

### Dependencies
- [LPS-91378 LPS-84119]: Update the com.liferay.gradle.plugins dependency to
version 3.13.127.

## 5.5.240 - 2019-03-12

### Dependencies
- [LPS-91803]: Update the com.liferay.gradle.plugins dependency to version
3.13.128.

## 5.5.241 - 2019-03-12

### Dependencies
- [LPS-85855]: Update the com.liferay.gradle.plugins dependency to version
3.13.129.

## 5.5.242 - 2019-03-12

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.130.

## 5.5.243 - 2019-03-13

### Commits
- [LPS-0]: Create a Zip file for each subdirectory (0e37807a1b)
- [LPS-92075]: Add zipFragmentsDirectory task (107752092b)

## 5.5.244 - 2019-03-14

### Dependencies
- [LPS-91970]: Update the com.liferay.gradle.plugins dependency to version
3.13.131.

## 5.5.245 - 2019-03-14

### Commits
- [LPS-92016]: Reorder class path to fix compile for tests. Only saw this issue
because test requires at least OSGi 6 and portal/lib/bnd.jar has OSGi 5 included.
(40a48bd3fc)

## 5.5.246 - 2019-03-15

### Commits
- [LPS-91846]: Restore the version after creating the jar (03107d5eaf)
- [LPS-91846]: Version should match the project version (32e34affcf)
- [LPS-91846]: Replace ad-hoc logic (a01df1aa2b)
- [LPS-91846]: Add task to update/resore the hotfix version (562906658f)

### Dependencies
- [LPS-91846]: Update the com.liferay.gradle.plugins dependency to version
3.13.132.

## 5.5.247 - 2019-03-15

### Commits
- [LPS-91846]: Check if the project has the Jar task (c8c4533238)

## 5.5.248 - 2019-03-15

### Dependencies
- [LPS-91420]: Update the com.liferay.gradle.plugins dependency to version
3.13.133.

## 5.5.249 - 2019-03-18

### Commits
- [LRQA-47104]: Check the project dependency's exported packages (26827e27ca)

### Dependencies
- [LRQA-47104]: Update the com.liferay.gradle.plugins dependency to version
3.13.134.

## 5.5.250 - 2019-03-19

### Commits
- [LPS-92016]: Revert "LPS-92016 Reorder class path to fix compile for tests.
Only saw this issue because test requires at least OSGi 6 and portal/lib/bnd.jar
has OSGi 5 included." (ab794a9aff)

## 5.5.251 - 2019-03-19

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.135.

## 5.5.252 - 2019-03-19

### Dependencies
- [LPS-92311]: Update the com.liferay.gradle.plugins dependency to version
3.13.136.

## 5.5.253 - 2019-03-20

### Dependencies
- [LPS-91967]: Update the com.liferay.gradle.plugins dependency to version
3.13.137.
- [LPS-91967]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.22.

## 5.5.254 - 2019-03-21

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.138.

## 5.5.255 - 2019-03-21

### Commits
- [LPS-91846]: Use the deploy task instead (feb5ddac05)
- [LPS-91846]: Run the tasks when the project version contains the word hotfix
(99fb192b88)
- [LPS-91846]: Use JsonSlurper instead of regex (bb1f08e2c5)
- [LPS-91846]: Use JsonSlurper to find the hotfix version (d08fe8bf9c)
- [LPS-91846]: Fix description (dc8a83c4ab)

## 5.5.256 - 2019-03-21

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.139.

## 5.5.257 - 2019-03-21

### Commits
- [LPS-91342]: regen (334a31a0b6)

### Dependencies
- [LPS-91342]: Update the com.liferay.gradle.plugins dependency to version
3.13.140.

## 5.5.258 - 2019-03-22

### Dependencies
- [LPS-91549]: Update the com.liferay.gradle.plugins dependency to version
3.13.141.

## 5.5.259 - 2019-03-25

### Dependencies
- [LPS-92223]: Update the com.liferay.gradle.plugins dependency to version
3.13.142.

## 5.5.260 - 2019-03-26

### Dependencies
- [LPS-91420]: Update the com.liferay.gradle.plugins dependency to version
3.13.143.

## 5.5.261 - 2019-03-27

### Dependencies
- [LPS-92568]: Update the com.liferay.gradle.plugins dependency to version
3.13.144.

## 5.5.262 - 2019-03-27

### Dependencies
- [LPS-92746]: Update the com.liferay.gradle.plugins dependency to version
3.13.145.

## 5.5.263 - 2019-03-27

### Dependencies
- [LPS-92746]: Update the com.liferay.gradle.plugins dependency to version
3.13.146.

## 5.5.264 - 2019-03-27

### Dependencies
- [LPS-90402]: Update the com.liferay.gradle.plugins dependency to version
3.13.147.

## 5.5.265 - 2019-03-27

### Dependencies
- [LPS-88911]: Update the com.liferay.gradle.plugins dependency to version
3.13.148.

## 5.5.266 - 2019-03-27

### Commits
- [LPS-91772]: this is a publish of a new SF to revert the old one (23537d6e71)

### Dependencies
- [LPS-91772]: Update the com.liferay.gradle.plugins dependency to version
3.13.149.

## 5.5.267 - 2019-03-28

### Dependencies
- [LPS-91420]: Update the com.liferay.gradle.plugins dependency to version
3.13.150.

## 5.5.268 - 2019-03-28

### Commits
- [LPS-91967]: Skip node_modules_cache directory (43a618c849)

### Dependencies
- [LPS-91967]: Update the com.liferay.gradle.plugins dependency to version
3.13.151.

## 5.5.269 - 2019-03-31

### Commits
- [LPS-93083]: Automatically disable the jar task for xxx-test projects
(6e633c0872)

## 5.5.270 - 2019-03-31

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.152.

## 5.5.271 - 2019-04-01

### Dependencies
- [LPS-93045]: Update the com.liferay.gradle.plugins dependency to version
3.13.153.

## 5.5.272 - 2019-04-01

### Dependencies
- [LPS-92677]: Update the com.liferay.gradle.plugins dependency to version
3.13.154.

## 5.5.273 - 2019-04-01

### Dependencies
- [LPS-91772]: Update the com.liferay.gradle.plugins dependency to version
3.13.155.

## 5.5.274 - 2019-04-01

### Dependencies
- [LPS-93124]: Update the com.liferay.gradle.plugins dependency to version
3.13.156.

## 5.5.275 - 2019-04-03

### Dependencies
- [LPS-93258]: Update the com.liferay.gradle.plugins dependency to version
3.13.157.
- [LPS-93258]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.23.

## 5.5.276 - 2019-04-05

### Dependencies
- [LPS-93471]: Update the com.liferay.gradle.plugins dependency to version
3.13.158.

## 5.5.277 - 2019-04-07

### Dependencies
- [LPS-91222]: Update the com.liferay.gradle.plugins dependency to version
3.13.159.
- [LRCI-65]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.9.

## 5.5.278 - 2019-04-08

### Commits
- [LPS-93350]: Apply demo configuration to xxx-demo projects (e166320f93)
- [LPS-93350]: Automatically deploy demo data creator dependencies (1e00480700)
- [LPS-93486]: Disable up-to-date check for "npmRunBuild" when the
FIX_PACKS_RELEASE_ENVIRONMENT env variable is set (139ac28216)

### Dependencies
- [LPS-93350]: Update the com.liferay.gradle.plugins dependency to version
3.13.160.

## 5.5.279 - 2019-04-09

### Commits
- [LPS-93083]: Preserve old behavior for 7.0.x (c85f0304c3)

## 5.5.280 - 2019-04-09

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.161.

## 5.5.281 - 2019-04-10

### Dependencies
- [LRDOCS-6412]: Update the com.liferay.gradle.plugins dependency to version
3.13.162.
- [LRDOCS-6412]: Update the com.liferay.gradle.plugins.jsdoc dependency to
version 2.0.24.

## 5.5.282 - 2019-04-11

### Dependencies
- [LPS-93506]: Update the com.liferay.gradle.plugins dependency to version
3.13.163.

## 5.5.283 - 2019-04-11

### Dependencies
- [LPS-91967]: Update the com.liferay.gradle.plugins dependency to version
3.13.164.
- [LPS-91967]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.25.

## 5.5.284 - 2019-04-11

### Dependencies
- [LPS-93707]: Update the com.liferay.gradle.plugins dependency to version
3.13.165.

## 5.5.285 - 2019-04-14

### Dependencies
- [LPS-91295]: Update the com.liferay.gradle.plugins dependency to version
3.13.166.

## 5.5.286 - 2019-04-15

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
3.13.167.

## 5.5.287 - 2019-04-16

### Dependencies
- [LPS-93265]: Update the com.liferay.gradle.plugins dependency to version
3.13.168.

## 5.5.288 - 2019-04-17

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.169.

## 5.5.289 - 2019-04-17

### Dependencies
- [LPS-94033]: Update the com.liferay.gradle.plugins dependency to version
3.13.170.

## 5.5.290 - 2019-04-18

### Dependencies
- [LPS-89210]: Update the com.liferay.gradle.plugins dependency to version
3.13.171.

## 5.5.291 - 2019-04-18

### Commits
- [LPS-94158]: Update FindSecurityBugs to 1.9.0 (ddbc84cd87)

## 5.5.292 - 2019-04-18

### Dependencies
- [LPS-88911]: Update the com.liferay.gradle.plugins dependency to version
3.13.172.

## 5.5.293 - 2019-04-22

### Dependencies
- [LPS-94523]: Update the com.liferay.gradle.plugins dependency to version
3.13.173.

## 5.5.294 - 2019-04-22

### Dependencies
- [LPS-94466]: Update the com.liferay.gradle.plugins dependency to version
3.13.174.

## 5.5.295 - 2019-04-23

### Dependencies
- [LPS-93513]: Update the com.liferay.gradle.plugins dependency to version
3.13.175.

## 5.5.296 - 2019-04-24

### Commits
- [LPS-69847]: Logging (4122215fa5)
- [LPS-69847]: Add local portal tool check
(com.liferay.lang.builder.ignore.local) (4412463f3d)

## 5.5.297 - 2019-04-25

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.plugins dependency to version
3.13.176.
- [LPS-77425]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.26.

## 5.5.298 - 2019-04-26

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.177.

## 5.5.299 - 2019-04-29

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.178.

## 5.5.300 - 2019-04-29

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.179.

## 5.5.301 - 2019-04-30

### Dependencies
- [LPS-93505]: Update the com.liferay.gradle.plugins dependency to version
3.13.180.

## 5.5.302 - 2019-05-01

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.181.
- [LPS-91967]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.27.

## 5.5.303 - 2019-05-01

### Dependencies
- [LPS-94764]: Update the com.liferay.gradle.plugins dependency to version
3.13.182.

## 5.5.304 - 2019-05-01

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.183.

## 5.5.305 - 2019-05-02

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.184.

## 5.5.306 - 2019-05-03

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
3.13.185.

## 5.5.307 - 2019-05-06

### Dependencies
- [LPS-91967]: Update the com.liferay.gradle.plugins dependency to version
3.13.186.
- [LPS-91967]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.28.

## 5.5.308 - 2019-05-06

### Commits
- [LPS-94947]: Use Collections#emptyList for performance (46ca71a49d)
- [LPS-94947]: deleteAllActions is deprecated and removed in Gradle 5.x
(b456fce82d)

### Dependencies
- [LPS-94947]: Update the com.liferay.gradle.plugins dependency to version
3.13.187.
- [LPS-94947]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.29.

## 5.5.309 - 2019-05-06

### Commits
- [LRDOCS-6412]: Check all files in directories (ecf2fc21c7)

## 5.5.310 - 2019-05-06

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.188.

## 5.5.311 - 2019-05-06

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.189.

## 5.5.312 - 2019-05-07

### Dependencies
- [LPS-91241]: Update the com.liferay.gradle.plugins dependency to version
3.13.190.

## 5.5.313 - 2019-05-08

### Commits
- [LPS-84119]: Use toArray(new T[0]) instead of toArray(new T[size()])
(c23914c90b)

### Dependencies
- [LPS-94948]: Update the com.liferay.gradle.plugins dependency to version
3.13.191.

## 5.5.314 - 2019-05-09

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.192.

## 5.5.315 - 2019-05-09

### Dependencies
- [LPS-95330]: Update the com.liferay.gradle.plugins dependency to version
3.13.193.
- [LPS-95330]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.0.

## 5.5.316 - 2019-05-09

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.194.

## 5.5.317 - 2019-05-10

### Dependencies
- [LPS-94948]: Update the com.liferay.gradle.plugins dependency to version
3.13.195.

## 5.5.318 - 2019-05-13

### Dependencies
- [LPS-84119 LPS-91420]: Update the com.liferay.gradle.plugins dependency to
version 3.13.196.
- [LPS-94947]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.10.

## 5.5.319 - 2019-05-13

### Dependencies
- [LPS-95413]: Update the com.liferay.gradle.plugins dependency to version
3.13.197.

## 5.5.320 - 2019-05-14

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
3.13.198.

## 5.5.321 - 2019-05-14

### Commits
- [LPS-95442]: Assert that it contains exactly one file (73c8df7610)
- [LPS-95442]: clear warnings (233d596db8)
- [LPS-95442]: use newest API (ac7c707831)

### Dependencies
- [LPS-95442]: Update the com.liferay.gradle.plugins dependency to version
4.0.0.

## 5.5.322 - 2019-05-15

### Commits
- [LPS-95442]: Skip creating POM files when there are no compiled classes
(2c9f4e28a5)
- [LPS-95442]: Iterate over compiled classes directories (d2bbdc75df)

## 5.5.323 - 2019-05-15

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.1.

## 5.5.324 - 2019-05-15

### Commits
- [LPS-95442]: fix arquillian tests regression (d2d8b5cbf4)

### Dependencies
- [LPS-95442]: Update the com.liferay.gradle.plugins dependency to version
4.0.2.

## 5.5.325 - 2019-05-15

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.3.

## 5.5.326 - 2019-05-16

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.4.

## 5.5.327 - 2019-05-16

### Dependencies
- [LRCI-264]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.11.

## 5.5.328 - 2019-05-17

### Dependencies
- [LPS-95723]: Update the com.liferay.gradle.plugins dependency to version
4.0.5.

## 5.5.329 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.6.
- [LRCI-264]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.12.

## 5.5.330 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.7.

## 5.5.331 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.8.

## 5.5.332 - 2019-05-20

### Dependencies
- [LPS-95635]: Update the com.liferay.gradle.plugins dependency to version
4.0.9.

## 5.5.333 - 2019-05-21

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.10.

## 5.5.334 - 2019-05-22

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.11.

## 5.5.335 - 2019-05-23

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.12.

## 5.5.336 - 2019-05-23

### Dependencies
- [LPS-94999]: Update the com.liferay.gradle.plugins dependency to version
4.0.13.

## 5.5.337 - 2019-05-23

### Dependencies
- [LPS-95915]: Update the com.liferay.gradle.plugins dependency to version
4.0.14.

## 5.5.338 - 2019-05-24

### Dependencies
- [LPS-96018]: Update the com.liferay.gradle.plugins dependency to version
4.0.15.
- [LPS-88909]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.31.
- [LPS-88909]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.30.

## 5.5.339 - 2019-05-27

### Dependencies
- [LPS-96091]: Update the com.liferay.gradle.plugins dependency to version
4.0.16.

## 5.5.340 - 2019-05-30

### Dependencies
- [LPS-96252]: Update the com.liferay.gradle.plugins dependency to version
4.0.17.

## 5.5.341 - 2019-05-30

### Commits
- [LPS-70170]: Skip compileJSP dependency substitution logic when
jsp.precompile.from.source is false (5a9433db28)

## 5.5.342 - 2019-05-30

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.18.

## 5.5.343 - 2019-05-30

### Commits
- [LPS-70170]: Add jsp.precompile.from.source check (a4983c1976)
- [LPS-70170]: Move (no logic changes) (9a35d3c86d)

### Dependencies
- [LPS-70170]: Update the com.liferay.gradle.plugins dependency to version
4.0.19.

## 5.5.344 - 2019-05-31

### Commits
- [LPS-70170]: Disable generateJSPJava (77e9b5feb8)
- [LPS-70170]: Copy the compiled jsps into the work directory (07762a1df6)

## 5.5.345 - 2019-06-01

### Dependencies
- [LPS-96206]: Update the com.liferay.gradle.plugins dependency to version
4.0.21.
- [LPS-96290]: Update the com.liferay.gradle.plugins dependency to version
4.0.20.

## 5.5.346 - 2019-06-03

### Dependencies
- [LPS-96376]: Update the com.liferay.gradle.plugins dependency to version
4.0.22.

## 5.5.347 - 2019-06-03

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.23.

## 5.5.348 - 2019-06-04

### Dependencies
- [LPS-95442 LPS-95819]: Update the com.liferay.gradle.plugins dependency to
version 4.0.24.

## 5.5.349 - 2019-06-04

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.25.

## 5.5.350 - 2019-06-05

### Dependencies
- [LPS-96198]: Update the com.liferay.gradle.plugins dependency to version
4.0.26.

## 5.5.351 - 2019-06-10

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.27.
- [LPS-93220]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.32.

## 5.5.352 - 2019-06-10

### Commits
- [LPS-93497]: Move JSON version check to ModulesStructuresTest (038ac820d1)
- [LPS-93497]: Add the hotfix qualifier to just the bnd.bnd file (49c2dc2bf0)

## 5.5.353 - 2019-06-11

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.28.
- [LRCI-350]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.13.

## 5.5.354 - 2019-06-11

### Dependencies
- [LPS-93510]: Update the com.liferay.gradle.plugins dependency to version
4.0.29.

## 5.5.355 - 2019-06-11

### Dependencies
- [LPS-95442]: Update the com.liferay.gradle.plugins dependency to version
4.0.30.

## 5.5.356 - 2019-06-12

### Dependencies
- [LPS-95455]: Update the com.liferay.gradle.plugins dependency to version
4.0.31.

## 5.5.357 - 2019-06-12

### Dependencies
- [LPS-96830]: Update the com.liferay.gradle.plugins dependency to version
4.0.32.

## 5.5.358 - 2019-06-12

### Dependencies
- [LPS-96860]: Update the com.liferay.gradle.plugins dependency to version
4.0.33.

## 5.5.359 - 2019-06-13

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.34.

## 5.5.360 - 2019-06-15

### Dependencies
- [LPS-95455]: Update the com.liferay.gradle.plugins dependency to version
4.0.35.

## 5.5.361 - 2019-06-17

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.36.

## 5.5.362 - 2019-06-17

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.37.

## 5.5.363 - 2019-06-18

### Dependencies
- [LPS-96611]: Update the com.liferay.gradle.plugins dependency to version
4.0.38.

## 5.5.364 - 2019-06-19

### Dependencies
- [LPS-95442]: Update the com.liferay.gradle.plugins dependency to version
4.0.39.

## 5.5.365 - 2019-06-19

### Commits
- [LPS-95442]: Fix findBugs (3bbdde7e00)

## 5.5.366 - 2019-06-20

### Dependencies
- [LPS-97169]: Update the com.liferay.gradle.plugins dependency to version
4.0.40.

## 5.5.367 - 2019-06-21

### Commits
- [LPS-96247]: Simplify (410cc25044)
- [LPS-96247]: Update (7385039e86)
- [LPS-96247]: Source formatting (b00a260bd1)
- [LPS-96247]: Fix Java classes directory location (169414989f)
- [LPS-96247]: Source formatting (aae01ea1d4)
- [LPS-96247]: Migrate away from deprecated SourceSetOutput.getClassesDir
(5b0f9860d5)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.plugins dependency to version
4.0.41.
- [LPS-96247]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.33.

## 5.5.368 - 2019-06-23

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.42.

## 5.5.369 - 2019-06-24

### Commits
- [LPS-97331]: Skip releng tasks for subrepos (52229ad7d3)

## 5.5.370 - 2019-06-24

### Dependencies
- [LPS-95819]: Update the com.liferay.gradle.plugins dependency to version
4.0.43.

## 5.5.371 - 2019-06-24

### Dependencies
- [LPS-96911]: Update the com.liferay.gradle.plugins dependency to version
4.0.44.

## 5.5.372 - 2019-06-25

### Dependencies
- [RELEASE-1607]: Update the com.liferay.gradle.plugins dependency to version
4.0.45.

## 5.5.373 - 2019-06-26

### Dependencies
- [LPS-95819]: Update the com.liferay.gradle.plugins dependency to version
4.0.46.

## 5.5.374 - 2019-06-26

### Dependencies
- [LPS-95819]: Update the com.liferay.gradle.plugins dependency to version
4.0.47.

## 5.5.375 - 2019-06-26

### Dependencies
- [LPS-93507]: Update the com.liferay.gradle.plugins dependency to version
4.0.48.

## 5.5.376 - 2019-07-01

### Commits
- [LPS-84119]: Create new var for better readability (753a5f0426)

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.49.

## 5.5.377 - 2019-07-01

### Commits
- [LPS-97614]: Force the test runtime to use the portal snapshot versions
(d2531352a3)

## 5.6.1 - 2019-07-01

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.50.

## 5.6.2 - 2019-07-01

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.51.

## 5.6.3 - 2019-07-02

### Commits
- [LPS-97721]: Fix the test runtime classpath (19e0d11e0e)

## 5.6.4 - 2019-07-04

### Dependencies
- [LPS-94301]: Update the com.liferay.gradle.plugins dependency to version
4.0.52.

## 5.6.5 - 2019-07-08

### Commits
- [LPS-97952]: Update test integration project dependency check (52104d1262)

## 5.6.6 - 2019-07-08

### Commits
- [LPS-97670]: Update task inputs (12c4cc6d18)
- [LPS-97670]: Move license data into a properties file (f753d956be)
- [LPS-97670]: Fix more cases (d1aaa78f83)
- [LPS-97670]: Override license data (11dc528de2)
- [LPS-97670]: Improve variable name (ea32af9bae)
- [LPS-97670]: Skip libraries that have no licenses (41a6445207)
- [LPS-97670]: Remove workaround (7375925f2f)
- [LPS-97670]: Fix compile errors (a21a37e194)
- [LPS-97670]: Update Gradle License Report plugin to the latest version
(655f5e4196)

## 5.6.7 - 2019-07-08

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.53.

## 5.6.8 - 2019-07-08

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.54.

## 5.6.9 - 2019-07-11

### Dependencies
- [LPS-98022]: Update the com.liferay.gradle.plugins dependency to version
4.0.55.

## 5.6.10 - 2019-07-11

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.56.

## 5.6.11 - 2019-07-15

### Commits
- [LPS-84119]: Do not declare var (85dc5fdf91)

### Dependencies
- [LPS-98198]: Update the com.liferay.gradle.plugins dependency to version
4.0.57.

## 5.6.12 - 2019-07-16

### Dependencies
- [LPS-97094]: Update the com.liferay.gradle.plugins dependency to version
4.0.58.

## 5.6.13 - 2019-07-16

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.59.

## 5.6.14 - 2019-07-18

### Commits
- [LPS-84119]: Pass methodcall directly instead of declaring variable first
(1e8886f5ef)

### Dependencies
- [LPS-98409]: Update the com.liferay.gradle.plugins dependency to version
4.0.60.

## 5.6.15 - 2019-07-21

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.61.

## 5.6.16 - 2019-07-22

### Dependencies
- [LRCI-473]: Update the com.liferay.gradle.plugins dependency to version
4.0.62.
- [LRCI-473]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.14.

## 5.6.17 - 2019-07-22

### Dependencies
- [LPS-98024]: Update the com.liferay.gradle.plugins dependency to version
4.0.63.

## 5.6.18 - 2019-07-24

### Dependencies
- [LPS-98450]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.34.

## 5.6.19 - 2019-07-25

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.64.

## 5.6.20 - 2019-07-26

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
4.0.65.

## 5.6.21 - 2019-07-29

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.66.

## 5.6.22 - 2019-07-30

### Dependencies
- [LPS-98190]: Update the com.liferay.gradle.plugins dependency to version
4.0.67.
- [LPS-98190]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.1.

## 5.6.23 - 2019-07-30

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.68.

## 5.6.24 - 2019-08-03

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.0.69.

## 5.6.25 - 2019-08-05

### Commits
- [LPS-99312]: Move deployDependencies task (622f6be5c2)

## 6.0.0 - 2019-08-05

### Dependencies
- [LPS-99312 LPS-99252]: Update the com.liferay.gradle.plugins dependency to
version 4.1.0.

## 6.0.1 - 2019-08-07

### Dependencies
- [LPS-99437]: Update the com.liferay.gradle.plugins dependency to version
4.1.1.

## 6.0.2 - 2019-08-07

### Dependencies
- [LPS-99468]: Update the com.liferay.gradle.plugins dependency to version
4.1.2.

## 6.0.3 - 2019-08-07

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.1.3.

## 6.0.4 - 2019-08-08

### Commits
- [LPS-98937]: Copy JSP Java files to the work directory when
jsp.precompile.enabled is true (0850fd802e)

### Dependencies
- [LPS-98937]: Update the com.liferay.gradle.plugins dependency to version
4.1.4.
- [LPS-98937]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.2.

## 6.0.5 - 2019-08-09

### Commits
- [LPS-98937]: Temp workaround for export-import-changeset-taglib (28f22ca1b5)

## 6.0.6 - 2019-08-09

### Commits
- [LPS-98937]: fix dependency substitution for exportimport taglib name exception
(b3e4719c4b)

## 6.0.7 - 2019-08-12

### Commits
- [LPS-99660]: Fix compileJSP task (d79730b92e)

## 6.0.8 - 2019-08-13

### Commits
- [LPS-99740]: Set environment variable (701d7920e1)
- [LPS-99740]: Simplify (no logic changes) (97c77cd66d)

### Dependencies
- [LPS-99740]: Update the com.liferay.gradle.plugins dependency to version
4.1.5.
- [LPS-99740]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.35.

## 6.0.9 - 2019-08-14

### Commits
- [LPS-99774]: Set environment variable to test for npmRunTest (0741aabb5d)

### Dependencies
- [LPS-99774]: Update the com.liferay.gradle.plugins dependency to version
4.1.6.
- [LPS-99774]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.36.

## 6.0.10 - 2019-08-15

### Dependencies
- [LPS-98468]: Update the com.liferay.gradle.plugins dependency to version
4.1.7.

## 6.0.11 - 2019-08-15

### Dependencies
- [LPS-99807]: Update the com.liferay.gradle.plugins dependency to version
4.1.8.

## 6.0.12 - 2019-08-16

### Commits
- [LPS-99660]: Fix compileJSP task for subrepos (3d15b04a3c)

## 6.0.13 - 2019-08-17

### Commits
- [LPS-99774]: Revert "LPS-99774 Set environment variable to test for npmRunTest"
(d8d2e56e84)

### Dependencies
- [LPS-99774]: Update the com.liferay.gradle.plugins dependency to version
4.1.9.

## 6.0.14 - 2019-08-19

### Commits
- [LPS-99977]: Update Gradle plugins (f125baba8a)

### Dependencies
- [LPS-99977]: Update the com.liferay.gradle.plugins dependency to version
4.1.10.
- [LPS-99977]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.37.

## 6.0.15 - 2019-08-19

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.1.11.

## 6.0.16 - 2019-08-19

### Commits
- [LPS-99895]: Move Liferay specific constant to gradle-plugins (77b618bcdd)
- [LPS-99895]: SF and Rebase (a8d297a5a5)
- [LPS-99895]: use a new property name to be more descriptive (156c27bcb8)
- [LPS-99895]: Use correct property name (ac83a1003a)
- [LPS-99895]: Change jsp.precompile.enabled property in Portal to jsp.precompile
(ccb74fe8f4)
- [LPS-99668]: FS (dc807507ce)
- [LPS-99668]: generateJSPJava should not run if jsp.precompile.enabled is false
(0e91177658)

### Dependencies
- [LPS-99895]: Update the com.liferay.gradle.plugins dependency to version
4.2.0.

## 6.0.17 - 2019-08-20

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.2.1.

## 6.0.18 - 2019-08-20

### Dependencies
- [LPS-100077]: Update the com.liferay.gradle.plugins dependency to version
4.2.2.

## 6.0.19 - 2019-08-21

### Dependencies
- [LPS-99917]: Update the com.liferay.gradle.plugins dependency to version
4.2.3.

## 6.0.20 - 2019-08-21

### Commits
- [LPS-100168]: Yarn subrepository configuration logic (7134484a58)
- [LPS-100168]: Yarn CI configuration logic (abadd6b834)
- [LPS-100168]: Add LiferayCIPatcherPlugin (no logic changes) (3cd49cbed7)

### Dependencies
- [LPS-100168]: Update the com.liferay.gradle.plugins dependency to version
4.3.0.
- [LPS-100168]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.38.

## 6.0.21 - 2019-08-21

### Dependencies
- [LPS-99657]: Update the com.liferay.gradle.plugins dependency to version
4.3.1.

## 6.0.22 - 2019-08-22

### Dependencies
- [LRDOCS-6300]: Update the com.liferay.gradle.plugins dependency to version
4.3.2.

## 6.0.23 - 2019-08-22

### Dependencies
- [LPS-100170]: Update the com.liferay.gradle.plugins dependency to version
4.3.3.

## 6.0.24 - 2019-08-22

### Dependencies
- [LPS-99577]: Update the com.liferay.gradle.plugins dependency to version
4.4.0.

## 6.0.25 - 2019-08-23

### Dependencies
- [LPS-99577]: Update the com.liferay.gradle.plugins dependency to version
4.4.1.

## 6.0.26 - 2019-08-24

### Dependencies
- [LPS-100168]: Update the com.liferay.gradle.plugins dependency to version
4.4.2.
- [LPS-100168]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.39.

## 6.0.27 - 2019-08-27

### Dependencies
- [LPS-100491]: Update the com.liferay.gradle.plugins dependency to version
4.4.3.

## 6.0.28 - 2019-08-27

### Dependencies
- [LPS-100352]: Update the com.liferay.gradle.plugins dependency to version
4.4.4.

## 6.0.29 - 2019-08-27

### Commits
- [LPS-99895]: Skip copying JSP Java files to the work directory (16b3af0d20)

### Dependencies
- [LPS-99895]: Update the com.liferay.gradle.plugins dependency to version
4.4.5.

## 6.0.30 - 2019-08-27

### Commits
- [LPS-100168]: baseline (353fbaa2d8)
- [LPS-100168]: Add LiferayYarnDefaultsPlugin (0a07c2062e)

## 6.1.1 - 2019-08-28

### Commits
- [LPS-97670]: Rename vars/methods for consistency (32926f3a48)
- [LPS-97670]: Rename license.report.properties.file ->
license.report.override.properties.file (6d72865907)

### Dependencies
- [LPS-97670 LPS-100163]: Update the com.liferay.gradle.plugins dependency to
version 4.4.6.
- [LPS-100163]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.40.

## 6.1.2 - 2019-08-29

### Commits
- [LPS-100168]: Remove CI patcher Yarn configuration logic (ec6733214f)

## 6.1.3 - 2019-08-29

### Dependencies
- [LPS-100596]: Update the com.liferay.gradle.plugins dependency to version
4.4.7.

## 6.1.4 - 2019-08-29

### Dependencies
- [LPS-99898]: Update the com.liferay.gradle.plugins dependency to version
4.4.8.

## 6.1.5 - 2019-08-30

### Commits
- [LPS-100166]: Follow symbolic links so the private branches can be symlinked
(abc88f3759)

### Dependencies
- [LPS-100166 LPS-100611]: Update the com.liferay.gradle.plugins dependency to
version 4.4.9.

## 6.1.6 - 2019-08-30

### Dependencies
- [LPS-90008]: Update the com.liferay.gradle.plugins dependency to version
4.4.10.

## 6.1.7 - 2019-08-30

### Dependencies
- [LPS-90008]: Update the com.liferay.gradle.plugins dependency to version
4.4.11.

## 6.1.8 - 2019-09-02

### Dependencies
- [LPS-99898]: Update the com.liferay.gradle.plugins dependency to version
4.4.12.

## 6.1.9 - 2019-09-03

### Dependencies
- [LPS-100937]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.3.

## 6.1.10 - 2019-09-03

### Dependencies
- [LPS-100937]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.4.

## 6.1.11 - 2019-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.4.13.

## 6.1.12 - 2019-09-05

### Dependencies
- [LPS-89415 LPS-84119]: Update the com.liferay.gradle.plugins dependency to
version 4.4.14.

## 6.1.13 - 2019-09-05

### Dependencies
- [LPS-101089]: Update the com.liferay.gradle.plugins dependency to version
4.4.15.

## 6.1.14 - 2019-09-06

### Dependencies
- [LPS-101208]: Update the com.liferay.gradle.plugins dependency to version
4.4.16.

## 6.1.15 - 2019-09-10

### Dependencies
- [LPS-85677]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.5.

## 6.1.16 - 2019-09-11

### Dependencies
- [LPS-101089]: Update the com.liferay.gradle.plugins dependency to version
4.4.17.

## 6.1.17 - 2019-09-12

### Dependencies
- [LPS-101214]: Update the com.liferay.gradle.plugins dependency to version
4.4.18.

## 6.1.18 - 2019-09-13

### Dependencies
- [LPS-101549]: Update the com.liferay.gradle.plugins dependency to version
4.4.19.

## 6.1.19 - 2019-09-16

### Dependencies
- [LPS-101574]: Update the com.liferay.gradle.plugins dependency to version
4.4.20.

## 6.1.20 - 2019-09-16

### Commits
- [LRQA-52072]: Liferay specific configuration logic (cd5352ce93)

### Dependencies
- [LRQA-52072]: Update the com.liferay.gradle.plugins dependency to version
4.4.21.
- [LRQA-52072]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.41.

## 6.1.21 - 2019-09-16

### Dependencies
- [LPS-100427]: Update the com.liferay.gradle.plugins dependency to version
4.4.22.

## 6.1.22 - 2019-09-17

### Dependencies
- [LPS-101450]: Update the com.liferay.gradle.plugins dependency to version
4.4.23.
- [LPS-101450]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.6.

## 6.1.23 - 2019-09-17

### Commits
- [LPS-101089]: Use new version (716dc1f5db)

## 6.1.24 - 2019-09-18

### Commits
- [LPS-101093]: Disable baseline for projects that don't have an Export-Package
instruction (0f4ccb4691)

### Dependencies
- [LPS-101470]: Update the com.liferay.gradle.plugins dependency to version
4.4.24.
- [LPS-101470]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.42.

## 6.1.25 - 2019-09-18

### Dependencies
- [LPS-93513]: Update the com.liferay.gradle.plugins dependency to version
4.4.25.

## 6.1.26 - 2019-09-19

### Dependencies
- [LPS-101470]: Update the com.liferay.gradle.plugins dependency to version
4.4.26.
- [LPS-101470]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.43.

## 6.1.27 - 2019-09-21

### Dependencies
- [LPS-101089 LPS-84119]: Update the com.liferay.gradle.plugins dependency to
version 4.4.27.

## 6.1.28 - 2019-09-21

### Commits
- [LPS-101972]: Skip creating sources-commercial jar for sdk projects
(376fa61469)

## 6.1.29 - 2019-09-23

### Dependencies
- [LPS-101605]: Update the com.liferay.gradle.plugins dependency to version
4.4.28.

## 6.1.30 - 2019-09-23

### Dependencies
- [LPS-86806]: Update the com.liferay.gradle.plugins dependency to version
4.4.29.

## 6.1.31 - 2019-09-24

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.4.30.

## 6.1.32 - 2019-09-26

### Commits
- [LPS-100168]: Add CI configuration logic (04841569e6)
- [LPS-100168]: Update Yarn defaults for subrepositories (868f162192)
- [LPS-100168]: Add null check (17740762e7)
- [LPS-100168]: Fix typo (d69be809df)

## 6.1.33 - 2019-09-27

### Dependencies
- [LPS-101947]: Update the com.liferay.gradle.plugins dependency to version
4.4.31.
- [LPS-101947]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.1.7.

## 6.1.34 - 2019-09-27

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.4.32.

## 6.1.35 - 2019-09-27

### Dependencies
- [LPS-89415]: Update the com.liferay.gradle.plugins dependency to version
4.4.33.

## 6.1.36 - 2019-09-29

### Dependencies
- [LPS-102452]: Update the com.liferay.gradle.plugins dependency to version
4.4.34.

## 6.1.37 - 2019-10-03

### Dependencies
- [LPS-102700]: Update the com.liferay.gradle.plugins dependency to version
4.4.35.
- [LPS-102700]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.2.0.

## 6.1.38 - 2019-10-03

### Dependencies
- [LPS-84119]: Update the com.liferay.gradle.plugins dependency to version
4.4.36.

## 6.1.39 - 2019-10-03

### Dependencies
- [LPS-102700]: Update the com.liferay.gradle.plugins dependency to version
4.4.37.

## 6.1.40 - 2019-10-03

### Dependencies
- [LPS-101108]: Update the com.liferay.gradle.plugins dependency to version
4.4.38.

## 6.1.41 - 2019-10-03

### Dependencies
- [LPS-100436]: Update the com.liferay.gradle.plugins dependency to version
4.4.39.

## 6.1.42 - 2019-10-08

### Dependencies
- [LRCI-642]: Update the com.liferay.gradle.plugins dependency to version
4.4.40.
- [LRCI-642]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.15.

## 6.1.43 - 2019-10-08

### Dependencies
- [LPS-102817]: Update the com.liferay.gradle.plugins dependency to version
4.4.41.

## 6.1.44 - 2019-10-09

### Dependencies
- [LPS-98387]: Update the com.liferay.gradle.plugins dependency to version
4.4.42.

## 6.1.45 - 2019-10-13

### Dependencies
- [LPS-103051]: Update the com.liferay.gradle.plugins dependency to version
4.4.43.

## 6.1.46 - 2019-10-14

### Commits
- [LPS-103051]: temp rollback (9a1e210294)

### Dependencies
- [LPS-103051]: Update the com.liferay.gradle.plugins dependency to version
4.4.44.

## 6.1.47 - 2019-10-14

### Dependencies
- [LPS-103051]: Update the com.liferay.gradle.plugins dependency to version
4.4.45.

## 6.1.48 - 2019-10-14

### Dependencies
- [LPS-103068]: Update the com.liferay.gradle.plugins dependency to version
4.4.46.

## 6.1.49 - 2019-10-14

### Commits
- [LPS-100937]: baseline (e4620f0ef6)

### Dependencies
- [LPS-101026]: Update the com.liferay.gradle.plugins dependency to version
4.4.47.
- [LPS-100937]: Update the com.liferay.gradle.plugins.baseline dependency to
version 2.2.1.

## 6.1.50 - 2019-10-15

### Commits
- [LPS-103170]: Update build profile file names (6fe130bb06)
- [LPS-103170]: Skip the dxp directory (ea6a8dfafa)

### Dependencies
- [LPS-103170]: Update the com.liferay.gradle.plugins dependency to version
4.4.48.

## 6.1.51 - 2019-10-16

### Commits
- [LPS-102481]: Skip the dxp directory only when build.profile is set
(cc74d1f14a)

## 6.1.52 - 2019-10-16

### Commits
- [LPS-102367]: Run "yarn install" if the project's node_modules directory does
not exist (855a6f2a02)

### Dependencies
- [LPS-102367]: Update the com.liferay.gradle.plugins dependency to version
4.4.49.
- [LPS-102367]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.44.

## 6.1.53 - 2019-10-20

### Commits
- [LPS-102481]: Fix DXP references. We still need to fix build-test-batch.xml and
build-app.xml (a3a7e4c89e)

### Dependencies
- [LPS-102481]: Update the com.liferay.gradle.plugins dependency to version
4.4.50.

## 6.1.54 - 2019-10-21

### Dependencies
- [LPS-103170]: Update the com.liferay.gradle.plugins dependency to version
4.4.51.

## 6.1.55 - 2019-10-21

### Dependencies
- [LPS-102481]: Update the com.liferay.gradle.plugins dependency to version
4.4.52.

## 6.1.56 - 2019-10-21

### Dependencies
- [LPS-103461]: Update the com.liferay.gradle.plugins dependency to version
4.4.53.

## 6.1.57 - 2019-10-21

### Dependencies
- [LPS-103461]: Update the com.liferay.gradle.plugins dependency to version
4.4.54.

## 6.1.58 - 2019-10-21

### Commits
- [LPS-102481]: Check DXP project path (83421be850)
- [LPS-102481]: rename (0509d1f374)
- [LPS-102481]: wordsmith (d2d4c554b8)
- [LPS-102481]: Add LiferayDXPPlugin (06ce617316)

### Dependencies
- [LPS-102367]: Update the com.liferay.gradle.plugins dependency to version
4.4.55.
- [LPS-102367]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.45.

## 6.1.59 - 2019-10-22

### Dependencies
- [LPS-103170]: Update the com.liferay.gradle.plugins dependency to version
4.4.56.

## 6.1.60 - 2019-10-23

### Commits
- [LPS-96984]: Remove test for Liferay Slim Runtime (92017455eb)

### Dependencies
- [LPS-103170]: Update the com.liferay.gradle.plugins dependency to version
4.4.57.

## 6.1.61 - 2019-10-23

### Dependencies
- [LPS-103580]: Update the com.liferay.gradle.plugins dependency to version
4.4.58.
- [LPS-103580]: Update the com.liferay.gradle.plugins.jsdoc dependency to version
2.0.46.

## 6.1.62 - 2019-10-28

### Dependencies
- [LPS-103743]: Update the com.liferay.gradle.plugins dependency to version
4.4.59.

## 6.1.63 - 2019-10-29

### Dependencies
- [LPS-103252]: Update the com.liferay.gradle.plugins dependency to version
4.4.60.

## 6.1.64 - 2019-10-29

### Dependencies
- [LPS-103843]: Update the com.liferay.gradle.plugins dependency to version
4.4.61.

## 6.1.65 - 2019-10-29

### Dependencies
- [LRCI-715]: Update the com.liferay.gradle.plugins dependency to version
4.4.62.
- [LRCI-715]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.16.

## 6.1.66 - 2019-10-30

### Dependencies
- [LPS-103466]: Update the com.liferay.gradle.plugins dependency to version
4.4.63.

## 6.1.67 - 2019-10-30

### Commits
- [LPS-103466]: revert (e4f42e7a25)

### Dependencies
- [LPS-103466]: Update the com.liferay.gradle.plugins dependency to version
4.4.64.

## 6.1.68 - 2019-10-31

### Commits
- [LPS-103170]: Override the repository url for dxp projects that are being
published to the liferay repo (495d8725ec)
- [LPS-103170]: Update the default repository url for dxp projects (15955409ee)

## 6.1.69 - 2019-10-31

### Dependencies
- [LPS-103169]: Update the com.liferay.gradle.plugins dependency to version
4.4.65.

## 6.1.70 - 2019-11-01

### Dependencies
- [LPS-103252]: Update the com.liferay.gradle.plugins dependency to version
4.4.66.

## 6.1.71 - 2019-11-01

### Dependencies
- [LPS-103809]: Update the com.liferay.gradle.plugins dependency to version
4.4.67.

## 6.1.72 - 2019-11-01

### Dependencies
- [LPS-103809]: Update the com.liferay.gradle.plugins dependency to version
4.4.68.

## 6.1.73 - 2019-11-01

### Commits
- [LPS-103809]: preop next (3768e9a4b7)

### Dependencies
- [LPS-103809]: Update the com.liferay.gradle.plugins dependency to version
4.4.69.

## 6.1.74 - 2019-11-04

### Commits
- [LPS-104121]: (8571acf287)

## 6.1.75 - 2019-11-04

### Dependencies
- [LPS-103872]: Update the com.liferay.gradle.plugins dependency to version
4.4.70.

## 6.1.76 - 2019-11-04

### Dependencies
- [LPS-102721]: Update the com.liferay.gradle.plugins dependency to version
4.4.71.

## 6.1.77 - 2019-11-04

### Dependencies
- [LPS-104028]: Update the com.liferay.gradle.plugins dependency to version
4.4.72.

## 6.1.78 - 2019-11-04

### Commits
- [LPS-103547]: The bnd.bnd check is good enough (e322046550)
- [LPS-103547]: Use constant (ff6b487323)
- [LPS-103547]: The baseline task is applied in gradle-plugins-defaults
(99bb9f4d65)

## 6.1.79 - 2019-11-04

### Dependencies
- [LPS-103466]: Update the com.liferay.gradle.plugins dependency to version
4.4.73.

## 6.1.80 - 2019-11-05

### Commits
- [LPS-103547]: Temp fix for subrepositories (ee5af84dea)
- [LPS-102481]: The commerce subrepo contains DXP projects (39f025d9c1)

### Dependencies
- [LPS-103547]: Update the com.liferay.gradle.plugins dependency to version
4.4.74.

## 6.1.81 - 2019-11-06

### Commits
- [LPS-102481]: Fix buildService task for subrepositories (61c16a14c1)

### Dependencies
- [LPS-104132]: Update the com.liferay.gradle.plugins dependency to version
4.4.75.

## 6.1.82 - 2019-11-07

### Dependencies
- [LPS-102481]: Update the com.liferay.gradle.plugins dependency to version
4.4.76.

## 6.1.83 - 2019-11-07

### Commits
- [LPS-102481]: Move logic to gradle-plugins-service-builder (7c913dfab2)

### Dependencies
- [LPS-102481]: Update the com.liferay.gradle.plugins dependency to version
4.4.77.

## 6.1.84 - 2019-11-07

### Dependencies
- [LPS-101574]: Update the com.liferay.gradle.plugins dependency to version
4.4.78.

## 6.1.85 - 2019-11-11

### Dependencies
- [LPS-104217]: Update the com.liferay.gradle.plugins dependency to version
4.4.79.

## 6.1.86 - 2019-11-11

### Dependencies
- [LRCI-766]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.17.

## 6.1.87 - 2019-11-11

### Dependencies
- [LPS-104129]: Update the com.liferay.gradle.plugins dependency to version
4.4.80.

## 6.1.88 - 2019-11-13

### Commits
- [LPS-104435]: Check if project dependencies are stale (83e84156c4)
- [LPS-104435]: Move project dependency check to a separate method (no logic
changes) (85cf2db24b)
- [LPS-104435]: Add git working directory so it can be used for portal projects
(no logic changes) (35ff04a9da)
- [LPS-104435]: Skip updateFileVersions for projects that don't have a
.lfrbuild-master-only marker file (5ccefada93)

### Dependencies
- [LPS-104435]: Update the com.liferay.gradle.plugins dependency to version
4.4.81.

## 6.1.89 - 2019-11-14

### Dependencies
- [LPS-104606]: Update the com.liferay.gradle.plugins dependency to version
4.4.82.

## 6.1.90 - 2019-11-15

### Commits
- [LPS-104435]: Save the Git result so its faster (dfea4be678)
- [LPS-104435]: Move Git check (no logic changes) (659a0f590b)

### Dependencies
- [LPS-104540]: Update the com.liferay.gradle.plugins dependency to version
4.4.83.

## 6.1.91 - 2019-11-19

### Dependencies
- [LPS-104435]: Update the com.liferay.gradle.plugins dependency to version
4.4.84.

## 6.1.92 - 2019-11-20

### Dependencies
- [LPS-104606]: Update the com.liferay.gradle.plugins dependency to version
4.4.85.

## 6.1.93 - 2019-11-20

### Dependencies
- [LPS-103252]: Update the com.liferay.gradle.plugins dependency to version
4.4.86.

## 6.1.94 - 2019-11-21

### Commits
- [LPS-104435]: Check the app server for com.liferay.portal dependencies
(cbfac1b766)

### Dependencies
- [LPS-104435]: Update the com.liferay.gradle.plugins dependency to version
4.4.87.

## 6.1.95 - 2019-11-25

### Dependencies
- [LPS-104435]: Update the com.liferay.gradle.plugins dependency to version
4.4.88.

## 6.1.96 - 2019-11-25

### Dependencies
- [LPS-104861]: Update the com.liferay.gradle.plugins dependency to version
4.4.89.

## 6.1.97 - 2019-11-26

### Commits
- [LPS-104435]: Revert "LPS-104435 Check the app server for com.liferay.portal
dependencies" (0c13ff6397)

### Dependencies
- [LPS-102388 LPS-104539 LPS-104435]: Update the com.liferay.gradle.plugins
dependency to version 4.4.90.

## 6.1.98 - 2019-11-27

### Commits
- [LPS-100515]: Fix for Gradle 5.6.4 (113f0c727d)
- [LPS-100515]: Update plugins Gradle version (448efac158)

### Dependencies
- [LPS-100515]: Update the com.liferay.gradle.plugins dependency to version
4.4.91.

## 6.1.99 - 2019-12-03

### Dependencies
- [LPS-105193]: Update the com.liferay.gradle.plugins dependency to version
4.4.92.

## 6.1.100 - 2019-12-03

### Dependencies
- [LPS-100515 LPS-103252]: Update the com.liferay.gradle.plugins dependency to
version 4.4.93.

## 6.1.101 - 2019-12-04

### Commits
- [LPS-102481]: Skip DXP check for CI (58424ee3f5)

### Dependencies
- [LPS-102481]: Update the com.liferay.gradle.plugins dependency to version
4.4.94.

## 6.1.102 - 2019-12-09

### Dependencies
- [LPS-105290 LPS-105237]: Update the com.liferay.gradle.plugins dependency to
version 4.4.95.

## 6.1.103 - 2019-12-09

### Dependencies
- [LPS-105237]: Update the com.liferay.gradle.plugins dependency to version
4.4.96.

## 6.1.104 - 2019-12-10

### Commits
- [LRCI-844]: Skip DXP projects by default (124015ad0d)

## 6.1.105 - 2019-12-10

### Dependencies
- [LPS-103547]: Update the com.liferay.gradle.plugins dependency to version
4.4.97.

## 6.1.106 - 2019-12-11

### Dependencies
- [LRQA-54214]: Update the com.liferay.gradle.plugins dependency to version
4.4.98.

## 6.1.107 - 2019-12-12

### Dependencies
- [LPS-105237]: Update the com.liferay.gradle.plugins dependency to version
4.4.99.

## 6.1.108 - 2019-12-12

### Commits
- [LPS-105756]: Fix writeFindBugsProject task (c12031cd8d)

## 6.1.109 - 2019-12-13

### Dependencies
- [LPS-105773]: Update the com.liferay.gradle.plugins dependency to version
4.4.100.

## 6.1.110 - 2019-12-13

### Dependencies
- [LPS-105247]: Update the com.liferay.gradle.plugins dependency to version
4.4.101.

## 6.1.111 - 2019-12-16

### Dependencies
- [LPS-105719 LPS-100427]: Update the com.liferay.gradle.plugins dependency to
version 4.4.102.

## 6.1.112 - 2019-12-17

### Commits
- [COMMERCE-2540]: Set the project group for commerce (8a29fafe3d)

## 6.1.113 - 2019-12-18

### Dependencies
- [LPS-105982]: Update the com.liferay.gradle.plugins dependency to version
4.4.103.

## 6.1.114 - 2019-12-19

### Dependencies
- [LPS-106128]: Update the com.liferay.gradle.plugins dependency to version
4.4.104.

## 6.1.115 - 2019-12-19

### Dependencies
- [LPS-103464]: Update the com.liferay.gradle.plugins dependency to version
4.4.105.

## 6.1.116 - 2019-12-19

### Commits
- [LPS-104435]: Apply (1e861772bf)
- [LPS-104435]: Use the version in build.properties (738dedf113)

## 6.1.117 - 2019-12-20

### Dependencies
- [LPS-106226 LPS-106079]: Update the com.liferay.gradle.plugins dependency to
version 4.4.106.

## 6.1.118 - 2019-12-24

### Dependencies
- [LPS-102577 LPS-106317]: Update the com.liferay.gradle.plugins dependency to
version 4.4.107.

## 6.1.119 - 2019-12-24

### Dependencies
- [LPS-106226]: Update the com.liferay.gradle.plugins dependency to version
4.4.108.

## 6.1.120 - 2019-12-24

### Dependencies
- [LPS-105228]: Update the com.liferay.gradle.plugins dependency to version
4.4.109.

## 6.1.121 - 2019-12-24

### Dependencies
- [LPS-105380]: Update the com.liferay.gradle.plugins dependency to version
4.4.110.

## 6.1.122 - 2020-01-03

### Dependencies
- [LPS-106333]: Update the com.liferay.gradle.plugins dependency to version
4.4.111.

## 6.1.123 - 2020-01-06

### Dependencies
- [LPS-106522]: Update the com.liferay.gradle.plugins dependency to version
4.4.112.

## 6.1.124 - 2020-01-06

### Dependencies
- [LRCI-902]: Update the com.liferay.gradle.plugins dependency to version
4.4.113.
- [LRCI-902]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.18.

## 6.1.125 - 2020-01-06

### Dependencies
- [LPS-105380]: Update the com.liferay.gradle.plugins dependency to version
4.4.114.

## 6.1.126 - 2020-01-07

### Commits
- [LPS-102243]: Remove from Gradle plugins (b713dd7982)
- [LPS-102243]: Partial revert (b5ee7e9c16)
- [LPS-102243]: Remove portal-test-integration and all references to it from
build and classpath (c2efa6c7f2)

### Dependencies
- [LPS-102243]: Update the com.liferay.gradle.plugins dependency to version
4.5.0.
- [LPS-102243]: Update the com.liferay.gradle.plugins dependency to version
4.4.114.
- [LPS-102243]: Update the com.liferay.gradle.plugins dependency to version
4.4.115.

## 6.1.127 - 2020-01-07

### Dependencies
- [LPS-106666]: Update the com.liferay.gradle.plugins dependency to version
4.5.1.

## 6.1.128 - 2020-01-07

### Commits
- [LPS-94003]: Upgrade to version 5.2.2.RELEASE of the Spring Framework
(272a365710)

### Dependencies
- [LPS-106662 LPS-106315 LPS-94003]: Update the com.liferay.gradle.plugins
dependency to version 4.5.2.

## 6.1.129 - 2020-01-08

### Commits
- [LPS-94003]: Upgrade to version 5.2.2.RELEASE of the Spring Framework (fix
typo) (7a5d14368b)

## 6.1.130 - 2020-01-09

### Commits
- [LPS-94003]: Source formatting (94586a4408)

### Dependencies
- [LPS-98387]: Update the com.liferay.gradle.plugins dependency to version
4.5.3.

## 6.1.131 - 2020-01-11

### Commits
- [LPS-104435]: Revert "LPS-104435 Use the version in build.properties"
(ed0f5abc95)
- [LPS-104435]: Revert "LPS-104435 Apply" (c2611fc844)

### Dependencies
- [LPS-104435]: Update the com.liferay.gradle.plugins dependency to version
4.5.4.

## 6.1.132 - 2020-01-14

### Commits
- [LPS-103170]: Publish dxp projects that end with -api or -spi to the public
repository (ccb9665edf)

## 6.1.133 - 2020-01-14

### Commits
- [LPS-104435]: Apply (6e21d5bf50)

## 6.1.134 - 2020-01-15

### Dependencies
- [LPS-104435]: Update the com.liferay.gradle.plugins dependency to version
4.5.5.

## 6.1.135 - 2020-01-15

### Dependencies
- [LPS-106614]: Update the com.liferay.gradle.plugins dependency to version
4.5.6.

## 6.1.136 - 2020-01-16

### Commits
- [LPS-107238]: (4cca0cd8ae)

### Dependencies
- [LPS-106614]: Update the com.liferay.gradle.plugins dependency to version
4.5.7.

## 6.1.137 - 2020-01-17

### Commits
- [LPS-105380]: Rename exception variables (b3173da81b)

### Dependencies
- [LPS-107313]: Update the com.liferay.gradle.plugins.patcher dependency to
version 1.0.17.

## 6.1.138 - 2020-01-17

### Dependencies
- [LPS-105380]: Update the com.liferay.gradle.plugins dependency to version
4.5.8.

## 6.1.139 - 2020-01-19

### Dependencies
- [LPS-105235]: Update the com.liferay.gradle.plugins dependency to version
4.5.9.

## 6.1.140 - 2020-01-19

### Dependencies
- [LPS-107224]: Update the com.liferay.gradle.plugins dependency to version
4.5.10.

## 6.1.141 - 2020-01-20

### Dependencies
- [LPS-107377 LPS-105235]: Update the com.liferay.gradle.plugins dependency to
version 4.5.11.

## 6.1.142 - 2020-01-20

### Commits
- [LPS-105380]: Rename exception variables (138aaedad1)

### Dependencies
- [LPS-105235]: Update the com.liferay.gradle.plugins dependency to version
4.5.12.

## 6.1.143 - 2020-01-20

### Dependencies
- [LPS-105228]: Update the com.liferay.gradle.plugins dependency to version
4.5.13.

## 6.1.144 - 2020-01-21

### Dependencies
- [LPS-105235]: Update the com.liferay.gradle.plugins dependency to version
4.5.14.

## 6.1.145 - 2020-01-22

### Dependencies
- [LPS-98387]: Update the com.liferay.gradle.plugins dependency to version
4.5.15.

## 6.1.146 - 2020-01-22

### Dependencies
- [LPS-105235]: Update the com.liferay.gradle.plugins dependency to version
4.5.16.

## 6.1.147 - 2020-01-23

### Dependencies
- [LPS-105235]: Update the com.liferay.gradle.plugins dependency to version
4.5.17.

## 6.1.148 - 2020-01-23

### Dependencies
- [LPS-100525]: Update the com.liferay.gradle.plugins dependency to version
4.5.18.

## 6.1.149 - 2020-01-23

### Dependencies
- [LPS-105235]: Update the com.liferay.gradle.plugins dependency to version
4.5.19.

## 6.1.150 - 2020-01-26

### Dependencies
- [LPS-106646]: Update the com.liferay.gradle.plugins dependency to version
4.5.20.

## 6.1.151 - 2020-01-28

### Dependencies
- [LPS-104679]: Update the com.liferay.gradle.plugins dependency to version
4.5.21.

## 6.1.152 - 2020-01-28

### Dependencies
- [LPS-107646]: Update the com.liferay.gradle.plugins dependency to version
4.5.22.

## 6.1.153 - 2020-01-29

### Dependencies
- [LPS-107825]: Update the com.liferay.gradle.plugins dependency to version
4.5.23.

## 6.1.154 - 2020-01-29

### Dependencies
- [LPS-107646]: Update the com.liferay.gradle.plugins dependency to version
4.5.24.

## 6.1.155 - 2020-01-31

### Commits
- [LPS-104435]: Skip Liferay-Releng-Fix-Delivery-Method core (d37b05fa73)

## 6.1.156 - 2020-02-03

### Dependencies
- [LPS-107984]: Update the com.liferay.gradle.plugins dependency to version
4.5.25.

## 6.1.157 - 2020-02-03

### Dependencies
- [LRCI-1003]: Update the com.liferay.gradle.plugins.poshi.runner dependency to
version 2.2.19.

## 6.1.158 - 2020-02-06

### Dependencies
- [LPS-107918]: Update the com.liferay.gradle.plugins dependency to version
4.5.26.

## 6.1.159 - 2020-02-07

### Commits
- [LPS-101372]: Update log messages (d7024b1fd8)
- [LPS-101372]: Fail publish task (this shouldn't happen) (ffdf48974a)
- [LPS-101372]: Check stale dependencies (8fed18294e)
- [LPS-101372]: Move (7c0a62265f)
- [LPS-101372]: Apply (d8a3bb21e4)
- [LPS-101372]: Add method to get artifact properties files (237576233d)
- [LPS-101372]: Inline (c5372101b4)
- [LPS-101372]: Move (aec7a138e3)
- [LPS-101372]: Rename methods (d499332d95)
- [LPS-101372]: Remove unneeded check (83bb866f50)
- [LPS-101372]: Copy logic so it can be changed (49974c8587)
- [LPS-101372]: Pass the project as a parameter (7a9a43d2fb)
- [LPS-101372]: Split out theme digest check (7659d3dfe5)
- [LPS-101372]: Consolidate only if logic for readability (72c2ab7f0b)
- [LPS-101372]: Move to util class (f5bbe39569)

## 6.1.160 - 2020-02-10

### Dependencies
- [LPS-108386]: Update the com.liferay.gradle.plugins dependency to version
4.5.27.

## 6.1.161 - 2020-02-10

### Commits
- [LPS-108388]: Move test task configuration to gradle-plugins-defaults
(80ba4a166b)

### Dependencies
- [LPS-108388]: Update the com.liferay.gradle.plugins dependency to version
4.5.28.

## 6.1.162 - 2020-02-10

### Dependencies
- [LPS-108386]: Update the com.liferay.gradle.plugins dependency to version
4.5.29.

## 6.1.163 - 2020-02-11

### Dependencies
- [LPS-107971]: Update the com.liferay.gradle.plugins dependency to version
4.5.30.

## 6.1.164 - 2020-02-11

### Commits
- [LPS-107513]: Update method name (57ba8ff396)
- [LPS-107513]: Loosen stale check (4ac833ddfc)
- [LPS-107513]: Add boolean to return only portal artifacts (27a922c081)

## 6.1.165 - 2020-02-11

### Commits
- [LPS-107513]: Remove unneeded check (82c69300ad)
- [LPS-107513]: Loosen stale check (7686fce4a3)
- [LPS-107513]: Check the compat version (cab0d34fc4)

### Dependencies
- [LPS-107513]: Update the com.liferay.gradle.plugins dependency to version
4.5.31.

## 6.1.166 - 2020-02-11

### Dependencies
- [LPS-102573]: Update the com.liferay.gradle.plugins dependency to version
4.5.32.