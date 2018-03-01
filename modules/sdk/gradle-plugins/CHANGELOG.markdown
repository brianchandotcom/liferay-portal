# Liferay Gradle Plugins Change Log

## 2.0.10 - 2016-08-22

### Changed
- [LPS-67658]: Update the [Gradle Bundle Plugin] dependency to version 0.8.6.

### Fixed
- [LPS-67658]: Compile the plugin against Gradle 2.14 to make it compatible with
both Gradle 2.14+ and Gradle 3.0.

## 2.0.11 - 2016-08-23

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.261.

## 2.0.12 - 2016-08-25

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.262.

## 2.0.13 - 2016-08-27

### Changed
- [LPS-67804]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.124.

## 2.0.14 - 2016-08-27

### Changed
- [LPS-67023]: Update the [Liferay Gradle Plugins Gulp] dependency to version
1.0.10.
- [LPS-67023]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 1.0.31.
- [LPS-67023]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 1.0.35.

### Removed
- [LPS-67023]: The project properties `nodejs.lfr.amd.loader.version` and
`nodejs.metal.cli.version` are no longer available.
- [LPS-67023]: Invoking the `clean` task no longer removes the
`node_modules` directory of a project.

## 2.0.15 - 2016-08-27

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.263.

## 2.0.16 - 2016-08-27

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.264.

## 2.0.17 - 2016-08-29

### Changed
- [LPS-67352]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.125.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.265.

## 2.0.18 - 2016-08-30

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.266.

## 2.0.19 - 2016-08-31

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.267.

## 2.0.20 - 2016-09-01

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.268.

## 2.0.21 - 2016-09-02

### Changed
- [LPS-67986]: Update the [Liferay CSS Builder] dependency to version 1.0.19.

## 2.0.22 - 2016-09-02

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.269.

## 2.0.23 - 2016-09-05

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.270.

## 2.0.24 - 2016-09-05

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.271.

## 2.0.25 - 2016-09-06

### Changed
- [LPS-67996]: Update the [Liferay Source Formatter] dependency to version
1.0.272.

## 2.0.26 - 2016-09-07

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.273.

## 2.0.27 - 2016-09-07

### Changed
- [LPS-68014]: Update the [Liferay Ant BND] dependency to version 2.0.29.
- [LPS-68035]: Update the [Liferay Source Formatter] dependency to version
1.0.274.

## 2.0.28 - 2016-09-08

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.275.

## 2.0.29 - 2016-09-08

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.276.

## 2.0.30 - 2016-09-09

### Added
- [LPS-61099]: Allow the `liferay.appServerParentDir` property's default value
to be overridden by setting the project property `app.server.parent.dir`.

## 2.0.31 - 2016-09-12

### Added
- [LPS-67766]: Automatically apply the `com.liferay.soy.translation` plugin in
order to use the Liferay localization mechanism in the generated `.soy.js`
files.

## 2.0.32 - 2016-09-13

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.277.

## 2.0.33 - 2016-09-13

### Changed
- [LPS-67986]: Update the [Liferay CSS Builder] dependency to version 1.0.20.

## 2.0.34 - 2016-09-14

- [LPS-68131]: Update the [Liferay Source Formatter] dependency to version
1.0.278.

## 2.0.35 - 2016-09-16

- [LPS-68131]: Update the [Liferay Source Formatter] dependency to version
1.0.280.
- [LPS-68165]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.16.
- [LPS-68165]: Update the [Liferay Lang Builder] dependency to version 1.0.10.
- [LPS-68165]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.126.
- [LPS-68165]: Update the [Liferay Portal Tools Upgrade Table Builder]
dependency to version 1.0.5.
- [LPS-68165]: Update the [Liferay Portal Tools WSDD Builder] dependency to
version 1.0.5.
- [LPS-68165]: Update the [Liferay TLD Formatter] dependency to version 1.0.1.
- [LPS-68165]: Update the [Liferay XML Formatter] dependency to version 1.0.1.

## 2.0.36 - 2016-09-20

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.281.

## 2.0.37 - 2016-09-20

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.0.
- [LPS-66906]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.0.0.
- [LPS-66906]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.0.0.

## 2.0.38 - 2016-09-21

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.282.

## 2.0.39 - 2016-09-22

### Changed
- [LPS-68297]: Update the default value of the
`liferay.appServers.jboss.version` property to `7.0.0`.
- [LPS-68297]: Update the [Liferay Source Formatter] dependency to version
1.0.283.

## 2.0.40 - 2016-09-22

### Added
- [LPS-66906]: Add the ability to configure the [`sass-binary-path`](https://github.com/sass/node-sass#binary-configuration-parameters)
argument in the `npmInstall` task by setting the project property
`nodejs.npm.sass.binary.site`.

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.284.

## 2.0.41 - 2016-09-23

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.285.

## 2.0.42 - 2016-09-26

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.286.

## 2.0.43 - 2016-09-27

### Changed
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.287.

## 2.0.44 - 2016-09-27

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.23.

## 2.0.45 - 2016-09-28

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.24.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.288.

## 2.0.46 - 2016-09-29

### Changed
- [LPS-58672]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.12.
- [LPS-58672]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.127.

## 2.0.47 - 2016-09-30

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.25.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.289.

## 2.0.48 - 2016-10-03

### Changed
- [LPS-68485]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.0.1.

## 2.0.49 - 2016-10-04

### Changed
- [LPS-68504]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.26.
- [LPS-68504]: Update the [Liferay Source Formatter] dependency to version
1.0.290.

## 2.0.50 - 2016-10-05

### Changed
- [LPS-68334]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.13.
- [LPS-68334]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.128.

## 3.0.0 - 2016-10-06

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.291.
- [LPS-67573]: Make most methods private in order to reduce API surface.

### Removed
- [LPS-66396]: Remove the task classes `BuildThumbnailsTask` and
`CompileThemeTask` from `com.liferay.gradle.plugins.tasks`. The
[Liferay Gradle Plugins Theme Builder] should be used instead.
- [LPS-67573]: To reduce the number of plugins applied to a project and improve
performance, plugins in `com.liferay.gradle.plugins.internal` are no longer
applied via `apply plugin`.

## 3.0.1 - 2016-10-06

### Changed
- [LPS-68415]: Update the [Liferay Source Formatter] dependency to version
1.0.292.

## 3.0.2 - 2016-10-06

### Changed
- [LPS-68564]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.1.
- [LPS-68564]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.0.2.
- [LPS-68564]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.0.1.

## 3.0.3 - 2016-10-07

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.27.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.293.

## 3.0.4 - 2016-10-07

### Changed
- [LRDOCS-3023]: Update the [Liferay Gradle Plugins TLDDoc Builder] dependency
to version 1.1.0.

## 3.0.5 - 2016-10-10

### Changed
- [LPS-68611]: Update the [Liferay Portal Tools WSDD Builder] dependency to
version 1.0.6.

## 3.0.6 - 2016-10-10

### Changed
- [LPS-68618]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.2.
- [LPS-68618]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.0.3.

## 3.0.7 - 2016-10-11

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.28.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.294.
- [LPS-68598]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.14.
- [LPS-68598]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.129.

## 3.0.8 - 2016-10-11

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.29.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.295.

## 3.0.9 - 2016-10-12

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.30.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.296.

## 3.0.10 - 2016-10-12

### Changed
- [LPS-68666]: Update the [Liferay Gradle Plugins TLDDoc Builder] dependency
to version 1.2.0.

## 3.0.11 - 2016-10-13

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.31.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.297.

## 3.0.12 - 2016-10-13

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.32.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.298.

## 3.0.13 - 2016-10-17

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.33.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.299.

## 3.0.14 - 2016-10-17

### Changed
- [LPS-68779]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.15.
- [LPS-68779]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.130.

## 3.0.15 - 2016-10-18

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.34.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.300.

## 3.0.16 - 2016-10-18

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.35.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.301.
- [LPS-68779]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.16.
- [LPS-68779]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.131.

## 3.0.17 - 2016-10-19

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.36.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.302.

## 3.0.18 - 2016-10-20

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.37.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.303.

## 3.0.19 - 2016-10-20

### Changed
- [LPS-67434]: Update the [Liferay Ant BND] dependency to version 2.0.30.

## 3.0.20 - 2016-10-20

### Changed
- [LPS-68839]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.17.
- [LPS-68839]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.132.

## 3.0.21 - 2016-10-21

### Changed
- [LPS-68838]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.18.
- [LPS-68838]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.133.

## 3.0.22 - 2016-10-21

### Changed
- [LPS-66906]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.3.
- [LPS-66906]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.0.4.
- [LPS-66906]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.0.2.

### Removed
- [LPS-66906]: The `removeShrinkwrappedUrls` property of `NpmInstallTask` can no
longer be set via the `nodejs.npm.remove.shrinkwrapped.urls` project property.

## 3.0.23 - 2016-10-24

### Changed
- [LPS-68917]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.1.0.

## 3.0.24 - 2016-10-24

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.38.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.304.

## 3.0.25 - 2016-10-25

### Changed
- [LPS-52675]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.40.
- [LPS-52675]: Update the [Liferay Source Formatter] dependency to version
1.0.305.

## 3.0.26 - 2016-10-26

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.41.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.306.
- [LPS-68917]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.1.1.

## 3.0.27 - 2016-10-27

### Changed
- [LPS-68980]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.19.
- [LPS-68980]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.134.

## 3.0.28 - 2016-10-28

### Changed
- [LPS-66222]: Update the [Liferay Gradle Plugins Upgrade Table Builder]
dependency to version 2.0.0.
- [LPS-68979]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.2.0.
- [LPS-68995]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.42.
- [LPS-68995]: Update the [Liferay Source Formatter] dependency to version
1.0.308.

## 3.0.29 - 2016-10-31

### Changed
- [LPS-68848]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.43.
- [LPS-68848]: Update the [Liferay Source Formatter] dependency to version
1.0.309.
- [LPS-69013]: Update the [Liferay Jasper JSPC] dependency to version 1.0.8.

## 3.0.30 - 2016-10-31

### Changed
- [LPS-69013]: Update the [Liferay Jasper JSPC] dependency to version 1.0.9.

## 3.0.31 - 2016-11-01

### Changed
- [LPS-68923]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.44.
- [LPS-68923]: Update the [Liferay Source Formatter] dependency to version
1.0.310.

## 3.0.32 - 2016-11-01

### Changed
- [LPS-69026]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.2.1.

## 3.0.33 - 2016-11-02

### Changed
- [LPS-68923]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.45.
- [LPS-68923]: Update the [Liferay Source Formatter] dependency to version
1.0.311.

## 3.0.34 - 2016-11-03

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.46.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.312.

## 3.0.35 - 2016-11-03

### Changed
- [LPS-68298]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.0.
- [LPS-68923]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.47.
- [LPS-68923]: Update the [Liferay Source Formatter] dependency to version
1.0.313.

## 3.0.36 - 2016-11-04

### Changed
- [LPS-68298]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.1.

## 3.0.37 - 2016-11-17

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.48.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.314.
- [LPS-68289]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.20.
- [LPS-68289]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.135.
- [LPS-69223]: Update the [Liferay CSS Builder] dependency to version 1.0.21.
- [LPS-69223]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.0.0.

## 3.0.38 - 2016-11-21

### Changed
- [LPS-69248]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.0.
- [LPS-69271]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.49.
- [LPS-69271]: Update the [Liferay Source Formatter] dependency to version
1.0.315.

## 3.0.39 - 2016-11-22

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.50.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.316.

## 3.0.40 - 2016-11-23

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.51.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.317.

## 3.0.41 - 2016-11-24

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.12.
- [LPS-69271]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.52.
- [LPS-69271]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.17.
- [LPS-69271]: Update the [Liferay Source Formatter] dependency to version
1.0.318.

## 3.0.42 - 2016-11-28

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.53.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.319.

## 3.0.43 - 2016-11-29

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.13.
- [LPS-69271]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.18.
- [LPS-69445]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.4.
- [LPS-69445]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.2.
- [LPS-69445]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.1.

## 3.0.44 - 2016-12-01

### Added
- [LPS-69488]: Set the default Node.js version to 6.6.0.

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.54.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.320.
- [LPS-69470]: Update the [Liferay Ant BND] dependency to version 2.0.31.

## 3.0.45 - 2016-12-01

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.55.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.321.
- [LPS-69492]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 1.1.0.

## 3.0.46 - 2016-12-03

### Added
- [LPS-69518]: Automatically delete the `liferay/logs` directory generated
during the execution of the `autoUpdateXml` task.

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.56.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.322.

## 3.0.47 - 2016-12-05

### Added
- [LPS-69501]: Allow portal tool versions to be overridden in a
`gradle.properties` file located in any parent directory of the project. For
example,

		com.liferay.source.formatter.version=1.0.300

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.57.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.323.

## 3.0.48 - 2016-12-08

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.58.
- [LPS-69271]: Update the [Liferay Source Formatter] dependency to version
1.0.324.
- [LPS-69618]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.5.
- [LPS-69618]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.3.
- [LPS-69618]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.2.

## 3.0.49 - 2016-12-08

### Fixed
- [LPS-69501]: Continue searching in the parent directories for a custom portal
tool version defined in a `gradle.properties` file until one is found.

## 3.0.50 - 2016-12-14

### Changed
- [LPS-69677]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.6.
- [LPS-69677]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.4.
- [LPS-69677]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.3.

## 3.0.51 - 2016-12-14

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.59.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.325.

## 3.0.52 - 2016-12-18

### Added
- [LPS-67688]: Automatically apply and configure
[Liferay Gradle Plugins DB Support] if [Liferay Gradle Plugins Service Builder]
is applied.

## 3.0.52 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.21.
- [LPS-69730]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.60.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.136.
- [LPS-69730]: Update the [Liferay Source Formatter] dependency to version
1.0.326.

## 3.0.53 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.22.
- [LPS-69730]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.61.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.137.
- [LPS-69730]: Update the [Liferay Source Formatter] dependency to version
1.0.327.

## 3.0.54 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.23.
- [LPS-69730]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.62.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.138.
- [LPS-69730]: Update the [Liferay Source Formatter] dependency to version
1.0.328.

## 3.0.55 - 2016-12-18

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.24.
- [LPS-69730]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.63.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.139.
- [LPS-69730]: Update the [Liferay Source Formatter] dependency to version
1.0.329.

## 3.0.56 - 2016-12-19

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.26.
- [LPS-69730]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.64.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.140.
- [LPS-69730]: Update the [Liferay Source Formatter] dependency to version
1.0.330.

## 3.0.57 - 2016-12-19

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.27.
- [LPS-69730]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.65.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.141.
- [LPS-69730]: Update the [Liferay Source Formatter] dependency to version
1.0.331.

## 3.0.58 - 2016-12-19

### Changed
- [LPS-69730]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.28.
- [LPS-69730]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.142.

## 3.0.59 - 2016-12-20

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.66.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.332.

## 3.0.60 - 2016-12-21

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.67.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.333.
- [LPS-69802]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.7.
- [LPS-69802]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.5.
- [LPS-69802]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.4.

## 3.0.61 - 2016-12-21

### Added
- [LPS-69838]: Add the ability to configure the `npmArgs` property in the `node`
extension object by setting the project property `nodejs.npm.args`.

## 3.0.62 - 2016-12-29

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.68.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.334.
- [LPS-69824]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.29.
- [LPS-69824]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.143.

## 3.0.63 - 2016-12-29

### Changed
- [LPS-69920]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.8.
- [LPS-69920]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.6.
- [LPS-69920]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.5.

## 3.0.64 - 2016-12-29

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.69.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.335.

## 3.0.65 - 2017-01-02

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.336.
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.70.

## 3.0.66 - 2017-01-03

### Changed
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.337.

## 3.0.67 - 2017-01-03

*No changes.*

## 3.0.68 - 2017-01-06

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.71.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.338.
- [LPS-69706]: Update the [Liferay CSS Builder] dependency to version 1.0.22.
- [LPS-69899]: Update the [Liferay Ant BND] dependency to version 2.0.32.

## 3.0.69 - 2017-01-09

### Changed
- [LPS-69706]: Update the [Liferay CSS Builder] dependency to version 1.0.23.

## 3.0.70 - 2017-01-10

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.72.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.339.

## 3.0.71 - 2017-01-10

### Changed
- [LPS-70060]: Update the [Liferay Gradle Plugins WSDL Builder] dependency to
version 2.0.0.
- [LPS-70084]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.73.
- [LPS-70084]: Update the [Liferay Source Formatter] dependency to version
1.0.340.

## 3.1.0 - 2017-01-12

### Added
- [LPS-69926]: Add the ability to automatically include one or more
dependencies in the final OSGi bundle via the `compileInclude` configuration.

### Changed
- [LPS-69926]: Update the [Gradle Bundle Plugin] dependency to version 0.9.0.
- [LPS-69980]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.74.
- [LPS-69980]: Update the [Liferay Source Formatter] dependency to version
1.0.341.
- [LPS-70092]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.0.0.

## 3.1.1 - 2017-01-13

### Changed
- [LPS-70036]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.0.1.

## 3.1.2 - 2017-01-26

### Added
- [LPS-70274]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.75.
- [LPS-70274]: Update the [Liferay Source Formatter] dependency to version
1.0.342.
- [LPS-70282]: Automatically configure the `mainClassName` project property
based on the `Main-Class` header in the `bnd.bnd` file, if the `application`
plugin is applied.

## 3.1.3 - 2017-01-29

### Changed
- [LPS-70336]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.76.
- [LPS-70336]: Update the [Liferay Source Formatter] dependency to version
1.0.343.

## 3.1.4 - 2017-01-30

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.77.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.344.

## 3.1.5 - 2017-01-31

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.78.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.345.
- [LPS-70379]: Update the [Liferay Ant BND] dependency to version 2.0.33.

## 3.1.6 - 2017-02-02

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.80.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.347.

## 3.1.7 - 2017-02-03

### Changed
- [LPS-69271]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.81.
- [LPS-69271]: Update the [Liferay Source Formatter] dependency to version
1.0.348.

## 3.1.8 - 2017-02-08

### Changed
- [LPS-70510]: Deploy theme projects to the
`[liferay.appServerParentDir]/deploy` directory by default.
- [LPS-70515]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.82.
- [LPS-70515]: Update the [Liferay Source Formatter] dependency to version
1.0.349.

## 3.1.9 - 2017-02-09

### Changed
- [LPS-69920]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.9.
- [LPS-69920]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.7.
- [LPS-69920]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.6.
- [LPS-70451]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.83.
- [LPS-70451]: Update the [Liferay Source Formatter] dependency to version
1.0.350.

## 3.2.0 - 2017-02-09

### Added
- [LPS-70555]: Add the ability to configure the file name of the JAR files
deployed by the `deploy` task by passing a closure to the
`liferay.deployedFileNameClosure` property.

### Changed
- [LPS-69926]: Embed the `compileInclude` dependency JAR files, instead of
expanding them.

## 3.2.1 - 2017-02-12

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.84.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.351.

## 3.2.2 - 2017-02-12

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.85.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.352.

## 3.2.3 - 2017-02-13

### Changed
- [LPS-69139]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.0.
- [LPS-70618]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.86.
- [LPS-70618]: Update the [Liferay Source Formatter] dependency to version
1.0.353.

## 3.2.4 - 2017-02-14

### Changed
- [LPS-70494]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.30.
- [LPS-70494]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.144.

## 3.2.5 - 2017-02-16

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.87.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.354.
- [LPS-70677]: Exclude `com.liferay.portal` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.
- [LPS-70677]: Update the [Liferay Gradle Plugins Jasper JSPC] dependency to
version 2.0.0.

## 3.2.6 - 2017-02-17

### Changed
- [LPS-70707]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.88.
- [LPS-70707]: Update the [Liferay Source Formatter] dependency to version
1.0.355.

## 3.2.7 - 2017-02-22

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.89.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.356.

## 3.2.8 - 2017-02-23

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.90.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.357.
- [LPS-70870]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.10.
- [LPS-70870]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.8.
- [LPS-70870]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.7.

## 3.2.9 - 2017-02-25

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.91.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.358.

## 3.2.10 - 2017-02-28

### Added
- [LPS-70941]: Add the ability to configure the `showDocumentation` property in
all `FormatSourceTask` instances by setting the project property
`source.formatter.show.documentation`.

### Changed
- [LPS-70941]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.92.
- [LPS-70941]: Update the [Liferay Source Formatter] dependency to version
1.0.359.

## 3.2.11 - 2017-03-01

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.93.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.360.
- [LPS-70890]: Update the [Liferay CSS Builder] dependency to version 1.0.24.
- [LPS-70890]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.19.
- [LPS-70890]: Update the [Liferay Lang Builder] dependency to version 1.0.11.
- [LPS-70890]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.145.
- [LPS-70890]: Update the [Liferay Portal Tools Upgrade Table Builder]
dependency to version 1.0.6.
- [LPS-70890]: Update the [Liferay Portal Tools WSDD Builder] dependency to
version 1.0.7.
- [LPS-70890]: Update the [Liferay TLD Formatter] dependency to version 1.0.2.
- [LPS-70890]: Update the [Liferay XML Formatter] dependency to version 1.0.2.

## 3.2.12 - 2017-03-02

### Changed
- [LPS-62970]: Update the [Liferay Gradle Plugins DB Support] dependency to
version 1.0.1.
- [LPS-62970]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.14.
- [LPS-62970]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 1.0.6.
- [LPS-62970]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.94.
- [LPS-62970]: Update the [Liferay Gradle Plugins TLD Formatter] dependency to
version 1.0.5.
- [LPS-62970]: Update the [Liferay Gradle Plugins XML Formatter] dependency to
version 1.0.7.

## 3.2.13 - 2017-03-02

### Changed
- [LPS-71005]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.95.
- [LPS-71005]: Update the [Liferay Source Formatter] dependency to version
1.0.362.

## 3.2.14 - 2017-03-03

### Changed
- [LPS-71048]: Update the [Liferay Gradle Plugins Jasper JSPC] dependency to
version 2.0.1.

### Fixed
- [LPS-70282]: Add the `compileInclude` configuration's dependencies to the
classpath of the `run` task if the `application` plugin is applied.

## 3.2.15 - 2017-03-06

### Changed
- [LPS-71005]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.96.
- [LPS-71005]: Update the [Liferay Source Formatter] dependency to version
1.0.363.

## 3.2.16 - 2017-03-08

### Changed
- [LPS-68405]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.9.

## 3.2.17 - 2017-03-09

### Added
- [LPS-70634]: Use the `package.json` file's `liferayTheme.distName` property as
the `archivesBaseName` property's value for theme projects, if present.

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.97.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.364.
- [LPS-70634]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.11.
- [LPS-70634]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.10.
- [LPS-70634]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.8.

## 3.2.18 - 2017-03-09

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.98.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.365.
- [LPS-67688]: Update the [Liferay Gradle Plugins DB Support] dependency to
version 1.0.2.

## 3.2.19 - 2017-03-11

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.99.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.366.

## 3.2.20 - 2017-03-13

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.100.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.367.
- [LPS-71222]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.12.
- [LPS-71222]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.11.
- [LPS-71222]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.9.

## 3.2.21 - 2017-03-15

### Changed
- [LPS-71118]: Update the [Liferay Ant BND] dependency to version 2.0.34.
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.101.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.368.

## 3.2.22 - 2017-03-16

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.102.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.369.

## 3.2.23 - 2017-03-17

### Changed
- [LPS-66891]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.31.
- [LPS-66891]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.146.
- [LPS-71331]: Update the [Liferay CSS Builder] dependency to version 1.0.25.

## 3.2.24 - 2017-03-17

### Changed
- [LPS-66891]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.32.
- [LPS-66891]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.147.

## 3.2.25 - 2017-03-21

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.103.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.370.

## 3.2.26 - 2017-03-22

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.104.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.371.

## 3.2.27 - 2017-03-24

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.105.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.372.

## 3.2.28 - 2017-03-27

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.106.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.373.

## 3.2.29 - 2017-03-28

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.107.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.374.

## 3.2.30 - 2017-03-30

### Changed
- [LPS-71558]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.109.
- [LPS-71558]: Update the [Liferay Source Formatter] dependency to version
1.0.376.
- [LPS-71603]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.33.
- [LPS-71603]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.148.

## 3.2.31 - 2017-04-03

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.110.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.377.

## 3.2.32 - 2017-04-03

### Changed
- [LPS-53392]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.149.

## 3.2.33 - 2017-04-04

### Changed
- [LPS-53392]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.34.
- [LPS-53392]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.150.
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.111.

## 3.2.34 - 2017-04-04

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.112.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.379.

## 3.2.35 - 2017-04-05

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.113.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.380.

## 3.2.36 - 2017-04-06

### Added
- [LPS-71375]: Add the ability to configure the `translateSubscriptionKey`
property in all `BuildLangTask` instances by setting the project property
`microsoft.translator.subscription.key`.

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.114.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.381.
- [LPS-71375]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.0.0.
- [LPS-71375]: Update the [Liferay Lang Builder] dependency to version 1.0.12.
- [LPS-71591]: Update the [Liferay Gradle Plugins TLDDoc Builder] dependency
to version 1.3.0.

### Removed
- [LPS-71375]: The project properties `microsoft.translator.client.id` and
`microsoft.translator.client.secret` are no longer available.

## 3.2.37 - 2017-04-08

### Changed
- [LPS-64098]: Update the [Liferay Ant BND] dependency to version 2.0.35.

## 3.2.38 - 2017-04-11

### Changed
- [LPS-71555]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.115.
- [LPS-71555]: Update the [Liferay Source Formatter] dependency to version
1.0.382.
- [LPS-71826]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.13.
- [LPS-71826]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.12.
- [LPS-71826]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.10.

## 3.2.39 - 2017-04-12

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.116.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.383.
- [LPS-71722]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.35.
- [LPS-71722]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.151.

## 3.2.40 - 2017-04-14

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.117.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.384.

## 3.2.41 - 2017-04-17

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.118.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.385.
- [LPS-71686]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.36.
- [LPS-71686]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.152.

## 3.3.0 - 2017-04-18

### Changed
- [LPS-67573]: Update the [Liferay Gradle Plugins Alloy Taglib] dependency to
version 2.0.0.
- [LPS-69139]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.1.
- [LPS-70451]: Update the [Liferay Alloy Taglib] dependency to version 1.1.10.
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.119.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.386.
- [LPS-71925]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.37.
- [LPS-71925]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.153.

## 3.3.1 - 2017-04-19

### Changed
- [LPS-72030]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.120.
- [LPS-72030]: Update the [Liferay Source Formatter] dependency to version
1.0.387.
- [LPS-72039]: Update the [Liferay Jasper JSPC] dependency to version 1.0.10.

## 3.3.2 - 2017-04-20

### Changed
- [LPS-72030]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.121.
- [LPS-72030]: Update the [Liferay Source Formatter] dependency to version
1.0.388.

## 3.3.3 - 2017-04-21

### Changed
- [LPS-72102]: Update the [Liferay Lang Builder] dependency to version 1.0.13.

## 3.3.4 - 2017-04-21

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.38.
- [LPS-71722]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.154.

## 3.3.5 - 2017-04-25

### Changed
- [LPS-69139]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.2.
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.122.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.389.
- [LPS-71601]: Update the [Liferay Alloy Taglib] dependency to version 1.1.12.
- [LPS-72152]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.14.
- [LPS-72152]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.13.
- [LPS-72152]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.11.

## 3.3.6 - 2017-04-27 [YANKED]

### Changed
- [LPS-53392]: Update the [Liferay Gradle Plugins Upgrade Table Builder]
dependency to version 2.0.1.
- [LPS-53392]: Update the [Liferay Portal Tools Upgrade Table Builder]
dependency to version 1.0.7.
- [LPS-71722]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.39.
- [LPS-71722]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.155.
- [LPS-71728]: Update the [Liferay Ant BND] dependency to version 2.0.36.

## 3.3.7 - 2017-04-28

### Changed
- [LPS-70890]: Update the [Liferay CSS Builder] dependency to version 1.0.26.
- [LPS-70890]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.20.
- [LPS-70890]: Update the [Liferay Portal Tools WSDD Builder] dependency to
version 1.0.8.
- [LPS-70890]: Update the [Liferay TLD Formatter] dependency to version 1.0.3.
- [LPS-70890]: Update the [Liferay XML Formatter] dependency to version 1.0.3.
- [LPS-71728]: Update the [Liferay Ant BND] dependency to version 2.0.37.

## 3.3.8 - 2017-05-03

### Changed
- [LPS-72326]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.123.
- [LPS-72326]: Update the [Liferay Source Formatter] dependency to version
1.0.390.
- [LPS-72340]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.15.
- [LPS-72340]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.14.
- [LPS-72340]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.12.

## 3.3.9 - 2017-05-03

### Changed
- [LPS-72252]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.0.

## 3.3.10 - 2017-05-09

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.2.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.392.

## 3.3.11 - 2017-05-11

### Changed
- [LPS-71164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.3.
- [LPS-71164]: Update the [Liferay Source Formatter] dependency to version
1.0.393.
- [LPS-72365]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 1.2.0.
- [LPS-72514]: Update the [Liferay Portal Tools DB Support] dependency to
version 1.0.3.

## 3.3.12 - 2017-05-13

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.4.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.394.

## 3.3.13 - 2017-05-15

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.5.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.395.

## 3.3.14 - 2017-05-16

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.6.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.396.

## 3.3.15 - 2017-05-19

### Changed
- [LPS-72572]: Update the [Liferay Ant BND] dependency to version 2.0.38.
- [LPS-72656]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.7.
- [LPS-72656]: Update the [Liferay Source Formatter] dependency to version
1.0.397.

## 3.3.16 - 2017-05-23

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.8.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.398.
- [LPS-72723]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.13.

## 3.3.17 - 2017-05-23

### Changed
- [LPS-67352]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.9.
- [LPS-67352]: Update the [Liferay Source Formatter] dependency to version
1.0.399.

## 3.3.18 - 2017-05-23

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.40.
- [LPS-71722]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.156.

## 3.3.19 - 2017-05-23

### Changed
- [LPS-71722]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.41.
- [LPS-71722]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.157.

## 3.3.20 - 2017-05-25

### Changed
- [LPS-72750]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.3.

## 3.3.21 - 2017-05-25

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.10.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.400.

## 3.3.22 - 2017-05-30

### Changed
- [LPS-69661]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.11.
- [LPS-69661]: Update the [Liferay Source Formatter] dependency to version
1.0.401.

## 3.3.23 - 2017-05-31

### Changed
- [LPS-72606]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.12.
- [LPS-72606]: Update the [Liferay Source Formatter] dependency to version
1.0.402.
- [LPS-72851]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.14.

## 3.3.24 - 2017-06-04

### Added
- [LPS-72858]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.13.
- [LPS-72858]: Update the [Liferay Source Formatter] dependency to version
1.0.403.
- [LPS-72868]: Automatically exclude the [`CN_IDIOM_NO_SUPER_CALL`](http://findbugs.sourceforge.net/bugDescriptions.html#CN_IDIOM_NO_SUPER_CALL)
FindBugs warning for the `ModelImpl` and `Wrapper` classes autogenerated by
[Liferay Portal Tools Service Builder].

## 3.3.25 - 2017-06-08

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.14.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.404.
- [LPS-72914]: Update the [Liferay Gradle Util] dependency to version 1.0.27.

## 3.3.26 - 2017-06-13

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.15.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.405.

## 3.3.27 - 2017-06-13

### Changed
- [LPS-73058]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.16.
- [LPS-73058]: Update the [Liferay Source Formatter] dependency to version
1.0.406.

## 3.3.28 - 2017-06-15

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.18.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.407.

## 3.3.29 - 2017-06-16

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.19.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.408.
- [LPS-73124]: Update the [Liferay Portal Tools DB Support] dependency to
version 1.0.4.
- [LPS-73148]: Update the [Liferay Portal Tools Upgrade Table Builder]
dependency to version 1.0.8.

### Fixed
- [LPS-72365]: Avoid deleting the `osgi/test` directory when executing the
`clean` task.

### Removed
- [LPS-73147]: Remove the unnecessary default Bnd instruction `-dsannotations:
*`.

## 3.3.30 - 2017-06-19

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.20.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.409.

## 3.3.31 - 2017-06-19

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.22.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.410.
- [LPS-73156]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.42.
- [LPS-73156]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.158.

## 3.3.32 - 2017-06-23

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.23.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.411.

## 3.3.33 - 2017-06-27

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.24.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.412.

## 3.3.34 - 2017-06-30

### Changed
- [LPS-65930]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.25.
- [LPS-65930]: Update the [Liferay Source Formatter] dependency to version
1.0.413.

## 3.3.35 - 2017-07-04

### Changed
- [LPS-73383]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.26.
- [LPS-73383]: Update the [Liferay Source Formatter] dependency to version
1.0.414.

## 3.3.36 - 2017-07-06

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.27.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.416.

## 3.3.37 - 2017-07-10

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.28.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.417.
- [LPS-73472]: Apply the [Liferay Gradle Plugins JS Module Config Generator] and
[Liferay Gradle Plugins JS Transpiler] to an OSGi project only if the
`package.json` file does not declare a script named `"build"`.
- [LPS-73472]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.16.
- [LPS-73472]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.15.
- [LPS-73472]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.15.
- [LPS-73495]: Update the [Liferay CSS Builder] dependency to version 1.0.28.
- [LPS-73495]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.0.1.

## 3.3.38 - 2017-07-10

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.29.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.418.

## 3.4.0 - 2017-07-11

### Changed
- [LPS-73261]: Update the [Liferay Source Formatter] dependency to version
1.0.420.
- [LPS-73489]: Move the `SourceFormatterDefaultsPlugin` class to an exported
package.
- [LPS-73489]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.31.

## 3.4.1 - 2017-07-11

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.32.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.421.

## 3.4.2 - 2017-07-12

### Added
- [LPS-73525]: Add the ability to configure the `setUpTestableTomcat` task's
`aspectJAgent` property by setting the project property `aspectj.agent`.
- [LPS-73525]: Add the ability to configure the `setUpTestableTomcat` task's
`aspectJConfiguration` property by setting the project property
`aspectj.configuration`.

### Changed
- [LPS-73525]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.0.0.

### Removed
- [LPS-73525]: The project properties `app.server.tomcat.setenv.gc.new` and
`app.server.tomcat.setenv.gc.old` are no longer available.

## 3.4.3 - 2017-07-13

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.33.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.422.
- [LPS-73584]: Update the [Liferay Gradle Util] dependency to version 1.0.28.

## 3.4.4 - 2017-07-13

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.34.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.423.
- [LPS-73584]: Update the [Liferay Gradle Util] dependency to version 1.0.29.

## 3.4.5 - 2017-07-14

### Changed
- [LPS-73470]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.35.
- [LPS-73470]: Update the [Liferay Source Formatter] dependency to version
1.0.424.

## 3.4.6 - 2017-07-17

### Added
- [LPS-73642]: Apply the [Liferay Gradle Plugins Lang Builder] to theme
projects. By default, the language directory is set to
`src/WEB-INF/src/content`.

### Changed
- [LPS-73408]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.43.
- [LPS-73408]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.159.
- [LPS-73472]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.17.
- [LPS-73472]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.16.
- [LPS-73472]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.16.

## 3.4.7 - 2017-07-18

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.36.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.425.

## 3.4.8 - 2017-07-19

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.37.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.426.

## 3.4.9 - 2017-07-19

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.38.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.427.

## 3.4.10 - 2017-07-20

### Changed
- [LPS-73600]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.39.
- [LPS-73600]: Update the [Liferay Source Formatter] dependency to version
1.0.428.

## 3.4.11 - 2017-07-21

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.40.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.429.

## 3.4.12 - 2017-07-24

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.41.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.430.

## 3.4.13 - 2017-07-24

### Added
- [LPS-73353]: Add the ability to configure the `setUpTestableTomcat` task's
`jaCoCoAgentConfiguration` property by setting the project property
`jacoco.agent.configuration`.
- [LPS-73525]: Add the ability to configure the `setUpTestableTomcat` task's
`jaCoCoAgentFile` property by setting the project property `jacoco.agent.jar`.

### Changed
- [LPS-73353]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.1.0.

## 3.4.14 - 2017-07-25

### Changed
- [LPS-72347]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.44.
- [LPS-72347]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.160.

## 3.4.15 - 2017-07-26

### Added
- [LPS-73818]: Automatically configure the `cleanServiceBuilder` task to read
the `portal.properties` file contained in `portal-impl.jar`, if no other portal
properties can be found.

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.42.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.431.

## 3.4.16 - 2017-07-27

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.43.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.432.

## 3.4.17 - 2017-07-31

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.44.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.433.
- [LPS-73124]: Update the [Liferay Portal Tools DB Support] dependency to
version 1.0.5.
- [LPS-73855]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.15.
- [LPS-73855]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.21.

## 3.4.18 - 2017-08-01

*No changes.*

## 3.4.19 - 2017-08-03

### Changed
- [LPS-73935]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.45.
- [LPS-73935]: Update the [Liferay Source Formatter] dependency to version
1.0.434.

## 3.4.20 - 2017-08-04

### Changed
- [LPS-73935]: Update the [Liferay Ant BND] dependency to version 2.0.39.
- [LPS-74034]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.46.
- [LPS-74034]: Update the [Liferay Source Formatter] dependency to version
1.0.435.

## 3.4.21 - 2017-08-07

### Changed
- [LPS-74063]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.47.
- [LPS-74063]: Update the [Liferay Source Formatter] dependency to version
1.0.436.

## 3.4.22 - 2017-08-08

### Fixed
- [LPS-74092]: Add the theme WAR file as an artifact of the `default`
configuration.

## 3.4.23 - 2017-08-09

### Changed
- [LPS-74104]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.48.
- [LPS-74104]: Update the [Liferay Source Formatter] dependency to version
1.0.437.

## 3.4.24 - 2017-08-09

### Changed
- [LPS-73967]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.45.
- [LPS-73967]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.161.

## 3.4.25 - 2017-08-09

### Changed
- [LPS-74088]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.49.
- [LPS-74088]: Update the [Liferay Source Formatter] dependency to version
1.0.438.

## 3.4.26 - 2017-08-11

### Changed
- [LPS-73967]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.50.
- [LPS-73967]: Update the [Liferay Source Formatter] dependency to version
1.0.439.

## 3.4.27 - 2017-08-12

### Changed
- [LPS-74126]: Update the [Liferay CSS Builder] dependency to version 1.1.0.
- [LPS-74126]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.0.

## 3.4.28 - 2017-08-15

### Changed
- [LPS-74139]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.51.
- [LPS-74139]: Update the [Liferay Source Formatter] dependency to version
1.0.440.
- [LPS-74155]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.46.
- [LPS-74155]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.162.

## 3.4.29 - 2017-08-15

### Changed
- [LPS-74139]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.52.
- [LPS-74139]: Update the [Liferay Source Formatter] dependency to version
1.0.441.

## 3.4.30 - 2017-08-15

### Changed
- [LPS-74126]: Update the [Liferay CSS Builder] dependency to version 1.1.1.
- [LPS-74126]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.1.

## 3.4.31 - 2017-08-16

### Changed
- [LPS-74139]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.53.
- [LPS-74139]: Update the [Liferay Source Formatter] dependency to version
1.0.442.

## 3.4.32 - 2017-08-17

### Changed
- [LPS-74222]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.54.
- [LPS-74222]: Update the [Liferay Source Formatter] dependency to version
1.0.443.

## 3.4.33 - 2017-08-18

### Changed
- [LPS-74126]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.55.
- [LPS-74126]: Update the [Liferay Source Formatter] dependency to version
1.0.444.
- [LPS-74155]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.47.
- [LPS-74155]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.163.

## 3.4.34 - 2017-08-21

### Changed
- [LPS-74155]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.48.
- [LPS-74155]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.164.
- [LPS-74250]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.0.
- [LPS-74250]: Update the [Liferay Lang Builder] dependency to version 1.0.14.

## 3.4.35 - 2017-08-22

### Changed
- [LPS-74269]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.0.56.
- [LPS-74269]: Update the [Liferay Source Formatter] dependency to version
1.0.445.

## 3.4.36 - 2017-08-23

### Changed
- [LPS-74278]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.49.
- [LPS-74278]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.165.

## 3.4.37 - 2017-08-24

### Added
- [LPS-74314]: Add the ability to configure the `showStatusUpdates` property of
all `FormatSourceTask` instances by setting the project property
`source.formatter.show.status.updates`.

### Changed
- [LPS-74314]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.0.
- [LPS-74314]: Update the [Liferay Source Formatter] dependency to version
1.0.446.

## 3.4.38 - 2017-08-24

### Changed
- [LPS-74343]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.17.
- [LPS-74343]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.17.

## 3.4.39 - 2017-08-24

### Added
- [LPS-74345]: Add the Liferay IDE project nature to Eclipse files.
- [LPS-74345]: Automatically apply the [`Eclipse`](https://docs.gradle.org/current/userguide/eclipse_plugin.html)
plugin to OSGi projects.

### Changed
- [LPS-74328]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.1.
- [LPS-74328]: Update the [Liferay Source Formatter] dependency to version
1.0.447.

## 3.4.40 - 2017-08-28

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.2.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.448.

## 3.4.41 - 2017-08-28

### Changed
- [LPS-74368]: Update the [Liferay Gradle Plugins Jasper JSPC] dependency to
version 2.0.2.
- [LPS-74368]: Update the [Liferay Jasper JSPC] dependency to version 1.0.11.

### Removed
- [LPS-74368]: Remove all dependency exclusions from the `jspCTool`
configuration's `com.liferay.jasper.jspc` default dependency.

## 3.4.42 - 2017-08-28

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.3.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.449.

## 3.4.43 - 2017-08-29

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.4.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.450.

## 3.4.44 - 2017-08-29

### Changed
- [LPS-73472]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.18.
- [LPS-73472]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.18.
- [LPS-73472]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.18.

## 3.4.45 - 2017-08-29

### Changed
- [LPS-73124]: Update the [Liferay Gradle Plugins DB Support] dependency to
version 1.0.3.
- [LPS-73124]: Update the [Liferay Portal Tools DB Support] dependency to
version 1.0.6.

## 3.4.46 - 2017-08-29

### Changed
- [LPS-74278]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.50.
- [LPS-74278]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.166.
- [LPS-74433]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.5.
- [LPS-74433]: Update the [Liferay Source Formatter] dependency to version
1.0.451.

## 3.4.47 - 2017-08-31

### Changed
- [LPS-72705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.6.
- [LPS-72705]: Update the [Liferay Source Formatter] dependency to version
1.0.452.

## 3.4.48 - 2017-09-01

### Changed
- [LPS-74475]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.7.
- [LPS-74475]: Update the [Liferay Source Formatter] dependency to version
1.0.453.

## 3.4.49 - 2017-09-06

### Changed
- [LPS-74538]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.9.
- [LPS-74538]: Update the [Liferay Source Formatter] dependency to version
1.0.454.

## 3.4.50 - 2017-09-06

### Changed
- [LPS-74171]: Update the default value of the
`liferay.appServers.tomcat.version` property to `8.5.20`.
- [LPS-74490]: Update the [Liferay Whip] dependency to version 1.0.2.

## 3.4.51 - 2017-09-07

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.10.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.455.

## 3.4.52 - 2017-09-08

### Changed
- [LPS-74171]: Update the default value of the
`liferay.appServers.tomcat.version` property to `8.0.32`.
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.11.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.456.

## 3.4.53 - 2017-09-10

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.1.12.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.457.

## 3.4.54 - 2017-09-11

### Added
- [LPS-74614]: Add the ability to configure the `showDebugInformation` property
in all `FormatSourceTask` instances by setting the project property
`source.formatter.show.debug.information`.

### Changed
- [LPS-74614]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.0.
- [LPS-74614]: Update the [Liferay Source Formatter] dependency to version
1.0.458.

## 3.4.55 - 2017-09-11

### Changed
- [LPS-73481]: Update the [Liferay Ant BND] dependency to version 2.0.40.
- [LPS-74373]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.1.
- [LPS-74373]: Update the [Liferay Source Formatter] dependency to version
1.0.459.

## 3.4.56 - 2017-09-12

### Changed
- [LPS-74207]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.51.
- [LPS-74207]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.167.

## 3.4.57 - 2017-09-12

### Changed
- [LPS-74637]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.2.
- [LPS-74637]: Update the [Liferay Source Formatter] dependency to version
1.0.460.

## 3.4.58 - 2017-09-13

### Changed
- [LPS-74657]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.3.
- [LPS-74657]: Update the [Liferay Source Formatter] dependency to version
1.0.461.

## 3.4.59 - 2017-09-14

### Changed
- [LPS-74614]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.4.
- [LPS-74614]: Update the [Liferay Source Formatter] dependency to version
1.0.462.

## 3.4.60 - 2017-09-18

### Changed
- [LPS-74315]: Update the [Liferay CSS Builder] dependency to version 1.1.2.
- [LPS-74315]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.2.
- [LPS-74752]: Update the default value of the `jsModuleConfigGenerator.version`
property to `1.3.3`.

## 3.4.61 - 2017-09-18

### Changed
- [LPS-74637]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.5.
- [LPS-74637]: Update the [Liferay Source Formatter] dependency to version
1.0.463.

## 3.4.62 - 2017-09-18

### Changed
- [LPS-74770]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.19.
- [LPS-74770]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.19.
- [LPS-74770]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.19.

## 3.4.63 - 2017-09-19

### Changed
- [LPS-74657]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.6.
- [LPS-74657]: Update the [Liferay Source Formatter] dependency to version
1.0.464.

## 3.4.64 - 2017-09-19

### Changed
- [LPS-74789]: Update the [Liferay CSS Builder] dependency to version 1.1.3.
- [LPS-74789]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.3.

## 3.4.65 - 2017-09-19

### Changed
- [LPS-74657]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.7.
- [LPS-74657]: Update the [Liferay Source Formatter] dependency to version
1.0.465.

## 3.4.66 - 2017-09-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.8.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.466.

## 3.4.67 - 2017-09-19

### Changed
- [LPS-74738]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.9.
- [LPS-74738]: Update the [Liferay Source Formatter] dependency to version
1.0.467.

## 3.4.68 - 2017-09-21

### Changed
- [LPS-71117]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.1.1.
- [LPS-73070]: Set the default Node.js version to 8.4.0.
- [LPS-74503]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.16.
- [LPS-74503]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.22.
- [LPS-74657]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.10.
- [LPS-74657]: Update the [Liferay Source Formatter] dependency to version
1.0.468.
- [LPS-74824]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.52.
- [LPS-74824]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.168.

## 3.4.69 - 2017-09-23

### Changed
- [LPS-71117]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.1.2.

## 3.4.70 - 2017-09-25

### Added
- [LPS-74884]: Exclude the `node_modules` directory in IDEA's `.iml` file to
speed up indexing.

## 3.4.71 - 2017-09-26

### Changed
- [LPS-74749]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.11.
- [LPS-74749]: Update the [Liferay Source Formatter] dependency to version
1.0.469.

## 3.4.72 - 2017-09-27

### Changed
- [LPS-74867]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.2.12.
- [LPS-74867]: Update the [Liferay Source Formatter] dependency to version
1.0.470.

## 3.4.73 - 2017-09-28

### Changed
- [LPS-74933]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.20.
- [LPS-74933]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.20.
- [LPS-74933]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.20.

## 3.4.74 - 2017-10-02

### Changed
- [LPS-75009]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.53.
- [LPS-75009]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.169.

## 3.4.75 - 2017-10-02 [YANKED]

### Changed
- [LPS-74110]: Update the [Liferay Ant BND] dependency to version 2.0.41.

## 3.5.0 - 2017-10-04

### Added
- [LPS-74314]: Expose `SourceFormatterDefaultsPlugin` as a Gradle plugin with ID
`com.liferay.source.formatter.defaults`.

### Changed
- [LPS-73070]: Use a single Node.js installation for the whole multi-project
build by setting the default value of the `node.global` property to `true`.
- [LPS-74110]: Update the [Liferay Ant BND] dependency to version 2.0.40.
- [LPS-74314]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.0.
- [LPS-74314]: Update the [Liferay Source Formatter] dependency to version
1.0.471.

## 3.5.1 - 2017-10-05

### Changed
- [LPS-75047]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.1.
- [LPS-75047]: Update the [Liferay Source Formatter] dependency to version
1.0.472.

## 3.5.2 - 2017-10-05

### Changed
- [LPS-74143]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.54.
- [LPS-74143]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.170.

## 3.5.3 - 2017-10-06

### Changed
- [LPS-74426]: Update the [Liferay CSS Builder] dependency to version 1.1.4.
- [LPS-74426]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.4.

## 3.5.4 - 2017-10-06

### Changed
- [LPS-74143]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.55.
- [LPS-74143]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.171.

## 3.5.5 - 2017-10-08

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.2.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.473.

## 3.5.6 - 2017-10-10

### Changed
- [LPS-75164]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.3.
- [LPS-75164]: Update the [Liferay Source Formatter] dependency to version
1.0.474.
- [LPS-75175]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.21.
- [LPS-75175]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.21.
- [LPS-75175]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.3.21.

## 3.5.7 - 2017-10-11

### Changed
- [LPS-75096]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.56.
- [LPS-75096]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.172.

## 3.5.8 - 2017-10-11

### Changed
- [LPS-74449]: Update the [Liferay CSS Builder] dependency to version 2.0.0.
- [LPS-74449]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.5.

## 3.5.9 - 2017-10-16

### Changed
- [LPS-75238]: Disable JSP compilation for OSGi fragments.
- [LPS-75254]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.4.
- [LPS-75254]: Update the [Liferay Source Formatter] dependency to version
1.0.475.

## 3.5.10 - 2017-10-16

### Changed
- [LPS-75273]: Update the [Liferay Gradle Plugins WSDL Builder] dependency to
version 2.0.1.

## 3.5.11 - 2017-10-17

### Changed
- [LPS-75100]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.5.
- [LPS-75100]: Update the [Liferay Source Formatter] dependency to version
1.0.476.

## 3.5.12 - 2017-10-17

### Changed
- [LPS-75100]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.6.
- [LPS-75100]: Update the [Liferay Source Formatter] dependency to version
1.0.477.

## 3.5.13 - 2017-10-17

### Changed
- [LPS-75239]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.2.0.

## 3.5.14 - 2017-10-18

### Changed
- [LPS-74849]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.7.
- [LPS-74849]: Update the [Liferay Source Formatter] dependency to version
1.0.478.

## 3.5.15 - 2017-10-18

### Changed
- [LPS-74849]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.8.
- [LPS-74849]: Update the [Liferay Source Formatter] dependency to version
1.0.479.

## 3.5.16 - 2017-10-19

### Changed
- [LPS-74348]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.57.
- [LPS-74348]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.173.

## 3.5.17 - 2017-10-20

### Changed
- [LPS-75254]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.9.
- [LPS-75254]: Update the [Liferay Source Formatter] dependency to version
1.0.480.

## 3.5.18 - 2017-10-22

### Changed
- [LPS-74457]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.10.
- [LPS-74457]: Update the [Liferay Source Formatter] dependency to version
1.0.481.

## 3.5.19 - 2017-10-23

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.11.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.482.

## 3.5.20 - 2017-10-24

### Added
- [LPS-75427]: Log the `deploy` task's destination directory after execution.

### Changed
- [LPS-75430]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.12.
- [LPS-75430]: Update the [Liferay Source Formatter] dependency to version
1.0.483.

## 3.5.21 - 2017-10-24

### Changed
- [LPS-75323]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.13.
- [LPS-75323]: Update the [Liferay Source Formatter] dependency to version
1.0.484.

## 3.5.22 - 2017-10-25

### Changed
- [LPS-74849]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.14.
- [LPS-74849]: Update the [Liferay Source Formatter] dependency to version
1.0.485.

## 3.5.23 - 2017-10-26

### Changed
- [LPS-75323]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.15.
- [LPS-75323]: Update the [Liferay Source Formatter] dependency to version
1.0.486.

## 3.5.24 - 2017-10-31

### Changed
- [LPS-75488]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.16.
- [LPS-75488]: Update the [Liferay Source Formatter] dependency to version
1.0.487.

## 3.5.25 - 2017-11-01

### Changed
- [LPS-75613]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.17.
- [LPS-75613]: Update the [Liferay Source Formatter] dependency to version
1.0.488.

## 3.5.26 - 2017-11-01

### Changed
- [LPS-75589]: Update the [Liferay CSS Builder] dependency to version 2.0.1.
- [LPS-75589]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.6.
- [LPS-75624]: Set the default Node.js version to 8.9.0.

## 3.5.27 - 2017-11-02

### Changed
- [LPS-75399]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.58.
- [LPS-75399]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.174.

## 3.5.28 - 2017-11-03

### Changed
- [LPS-75247]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.17.
- [LPS-75247]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.23.

## 3.5.29 - 2017-11-06

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.18.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.489.

## 3.5.30 - 2017-11-07

### Changed
- [LPS-75745]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.19.
- [LPS-75745]: Update the [Liferay Source Formatter] dependency to version
1.0.490.

## 3.5.31 - 2017-11-07

### Changed
- [LPS-75633]: Update the [Liferay CSS Builder] dependency to version 2.0.2.
- [LPS-75633]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.1.7.

## 3.5.32 - 2017-11-07

### Changed
- [LPS-74457]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.20.
- [LPS-74457]: Update the [Liferay Source Formatter] dependency to version
1.0.491.

## 3.5.33 - 2017-11-08

### Changed
- [LPS-75323]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.21.
- [LPS-75323]: Update the [Liferay Source Formatter] dependency to version
1.0.492.

## 3.5.34 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.1.
- [LPS-73725]: Update the [Liferay Lang Builder] dependency to version 1.0.15.

## 3.5.35 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.2.
- [LPS-73725]: Update the [Liferay Lang Builder] dependency to version 1.0.16.

## 3.5.36 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.3.
- [LPS-73725]: Update the [Liferay Lang Builder] dependency to version 1.0.17.

## 3.5.37 - 2017-11-08

### Changed
- [LPS-73725]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.4.
- [LPS-73725]: Update the [Liferay Lang Builder] dependency to version 1.0.18.

## 3.5.38 - 2017-11-09

### Changed
- [LPS-75610]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.22.
- [LPS-75610]: Update the [Liferay Source Formatter] dependency to version
1.0.493.

## 3.5.39 - 2017-11-10

### Changed
- [LPS-75010]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.59.
- [LPS-75010]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.175.

## 3.5.40 - 2017-11-12

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.23.
- [LPS-75798]: Update the [Liferay Source Formatter] dependency to version
1.0.494.

## 3.5.41 - 2017-11-13

### Added
- [LPS-74526]: Execute the `"checkFormat"` script declared in the project's
`package.json` file (if present) before running the task
`checkSourceFormatting`.
- [LPS-74526]: Execute the `"format"` script declared in the project's
`package.json` file (if present) before running the task `formatSource`.
- [LPS-75829]: Apply the `com.liferay.js.transpiler.base` plugin to an OSGi
project if the `package.json` file declares a script named `"build"`.

### Changed
- [LPS-75829]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.0.

## 3.5.42 - 2017-11-14

### Changed
- [LPS-74526]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.24.
- [LPS-74526]: Update the [Liferay Source Formatter] dependency to version
1.0.495.

## 3.5.43 - 2017-11-14

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.25.
- [LPS-75798]: Update the [Liferay Source Formatter] dependency to version
1.0.496.

## 3.5.44 - 2017-11-15

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.26.
- [LPS-75798]: Update the [Liferay Source Formatter] dependency to version
1.0.497.

## 3.5.45 - 2017-11-16

### Changed
- [LPS-75952]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.27.
- [LPS-75952]: Update the [Liferay Source Formatter] dependency to version
1.0.498.

## 3.5.46 - 2017-11-20

### Changed
- [LPS-75965]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.22.
- [LPS-75965]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.1.

## 3.5.47 - 2017-11-21

### Changed
- [LPS-75971]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.28.
- [LPS-75971]: Update the [Liferay Source Formatter] dependency to version
1.0.499.

## 3.5.48 - 2017-11-24

### Changed
- [LPS-76110]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.29.
- [LPS-76110]: Update the [Liferay Source Formatter] dependency to version
1.0.500.

## 3.5.49 - 2017-11-27

### Changed
- [LPS-75778]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.30.
- [LPS-75778]: Update the [Liferay Source Formatter] dependency to version
1.0.501.

## 3.5.50 - 2017-11-28

### Changed
- [LPS-72912]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.31.
- [LPS-72912]: Update the [Liferay Source Formatter] dependency to version
1.0.502.

## 3.5.51 - 2017-11-28

### Changed
- [LPS-75859]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.60.
- [LPS-75859]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.176.

## 3.5.52 - 2017-11-28

### Changed
- [LPS-75901]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.32.
- [LPS-75901]: Update the [Liferay Source Formatter] dependency to version
1.0.503.

## 3.5.53 - 2017-11-29

### Changed
- [LPS-75859]: Update the [Liferay Gradle Plugins Service Builder] dependency
to version 1.0.61.
- [LPS-75859]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.177.
- [LPS-75901]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.33.
- [LPS-75901]: Update the [Liferay Source Formatter] dependency to version
1.0.504.

## 3.5.54 - 2017-11-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.34.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.505.

## 3.5.55 - 2017-11-30

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.35.
- [LPS-75798]: Update the [Liferay Source Formatter] dependency to version
1.0.506.
- [LPS-76202]: Update the [Liferay Gradle Plugins Jasper JSPC] dependency to
version 2.0.3.

## 3.5.56 - 2017-12-01

### Changed
- [LPS-75798]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.37.
- [LPS-75798]: Update the [Liferay Source Formatter] dependency to version
1.0.508.
- [LPS-76224]: Update the [Liferay Ant BND] dependency to version 2.0.42.

## 3.5.57 - 2017-12-04

### Changed
- [LPS-76221]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.5.
- [LPS-76221]: Update the [Liferay Lang Builder] dependency to version 1.0.19.

## 3.5.58 - 2017-12-05

### Changed
- [LPS-75554]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.4.
- [LPS-76221]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.6.
- [LPS-76221]: Update the [Liferay Lang Builder] dependency to version 1.0.20.

## 3.5.59 - 2017-12-05

### Changed
- [LPS-76256]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.38.
- [LPS-76256]: Update the [Liferay Source Formatter] dependency to version
1.0.509.

## 3.5.60 - 2017-12-05

### Changed
- [LPS-76226]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.39.
- [LPS-76226]: Update the [Liferay Source Formatter] dependency to version
1.0.510.

## 3.5.61 - 2017-12-07

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.41.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.512.

## 3.5.62 - 2017-12-10

### Changed
- [LPS-76326]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.43.
- [LPS-76326]: Update the [Liferay Source Formatter] dependency to version
1.0.514.

## 3.5.63 - 2017-12-12

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.44.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.515.

## 3.5.64 - 2017-12-12

### Changed
- [LPS-76018]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.18.
- [LPS-76018]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.62.
- [LPS-76018]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.45.
- [LPS-76018]: Update the [Liferay Gradle Plugins TLD Formatter] dependency to
version 1.0.6.
- [LPS-76018]: Update the [Liferay Gradle Plugins WSDD Builder] dependency to
version 1.0.10.
- [LPS-76018]: Update the [Liferay Gradle Plugins XML Formatter] dependency to
version 1.0.8.
- [LPS-76018]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.24.
- [LPS-76018]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.178.
- [LPS-76018]: Update the [Liferay Portal Tools WSDD Builder] dependency to
version 1.0.9.
- [LPS-76018]: Update the [Liferay Source Formatter] dependency to version
1.0.516.
- [LPS-76018]: Update the [Liferay TLD Formatter] dependency to version 1.0.4.
- [LPS-76018]: Update the [Liferay XML Formatter] dependency to version 1.0.4.

## 3.5.65 - 2017-12-12

### Changed
- [LPS-76018]: Update the [Liferay XML Formatter] dependency to version 1.0.5.

## 3.5.66 - 2017-12-12

### Changed
- [LPS-76018]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.46.
- [LPS-76018]: Update the [Liferay Gradle Plugins TLD Formatter] dependency to
version 1.0.7.
- [LPS-76018]: Update the [Liferay Gradle Plugins WSDD Builder] dependency to
version 1.0.11.
- [LPS-76018]: Update the [Liferay Gradle Plugins XML Formatter] dependency tos
version 1.0.9.
- [LPS-76018]: Update the [Liferay Portal Tools WSDD Builder] dependency to
version 1.0.10.
- [LPS-76018]: Update the [Liferay Source Formatter] dependency to version
1.0.517.
- [LPS-76018]: Update the [Liferay TLD Formatter] dependency to version 1.0.5.

## 3.5.67 - 2017-12-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.48.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.519.

## 3.5.68 - 2017-12-14

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.49.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.520.

## 3.5.69 - 2017-12-19

### Changed
- [LPS-76018]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.20.
- [LPS-76018]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.26.
- [LPS-76475]: Update the [Liferay CSS Builder] dependency to version 2.1.0.
- [LPS-76475]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.0.
- [LPS-76601]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.50.
- [LPS-76601]: Update the [Liferay Source Formatter] dependency to version
1.0.521.

## 3.5.70 - 2017-12-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.51.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.522.

## 3.5.71 - 2017-12-20

### Changed
- [LPS-76221]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.7.
- [LPS-76221]: Update the [Liferay Lang Builder] dependency to version 1.0.21.

## 3.5.72 - 2017-12-20

### Changed
- [LPS-76221]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.1.8.
- [LPS-76221]: Update the [Liferay Lang Builder] dependency to version 1.0.22.

## 3.5.73 - 2017-12-20

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.62.
- [LPS-76626]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.52.
- [LPS-76626]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.179.
- [LPS-76626]: Update the [Liferay Source Formatter] dependency to version
1.0.523.

## 3.5.74 - 2017-12-21

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.53.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.524.
- [LPS-76626]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.63.
- [LPS-76626]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.180.

## 3.5.75 - 2017-12-24

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.54.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.525.

## 3.5.76 - 2017-12-26

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.55.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.526.

## 3.5.77 - 2017-12-29

### Added
- [LPS-72868]: Automatically exclude the [`EI_EXPOSE_REP`](http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP),
[`EI_EXPOSE_REP2`](http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP2),
[`EQ_UNUSUAL`](http://findbugs.sourceforge.net/bugDescriptions.html#EQ_UNUSUAL),
[`MS_MUTABLE_COLLECTION`](http://findbugs.sourceforge.net/bugDescriptions.html#MS_MUTABLE_COLLECTION),
and [`RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE`](http://findbugs.sourceforge.net/bugDescriptions.html#RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE)
FindBugs warnings for the `ModelImpl` classes autogenerated by
[Liferay Portal Tools Service Builder].
- [LPS-72868]: Automatically exclude the [`EI_EXPOSE_REP`](http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP)
and [`EI_EXPOSE_REP2`](http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP2)
FindBugs warnings for the `Soap` classes autogenerated by
[Liferay Portal Tools Service Builder].
- [LPS-72868]: Automatically exclude the [`EI_EXPOSE_REP`](http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP)
and [`MS_PKGPROTECT`](http://findbugs.sourceforge.net/bugDescriptions.html#MS_PKGPROTECT)
FindBugs warnings for `*Comparator`, `*Searcher`, and `*StagedModelDataHandler`
classes.
- [LPS-72868]: Automatically exclude the [`ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD`](http://findbugs.sourceforge.net/bugDescriptions.html#ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD)
FindBugs warning for `*Permission` and `ServletContextUtil` classes.

### Changed
- [LPS-76747]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.56.
- [LPS-76747]: Update the [Liferay Source Formatter] dependency to version
1.0.527.

## 3.5.78 - 2018-01-02

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.57.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.528.

## 3.5.79 - 2018-01-02

### Changed
- [LPS-74904]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.22.
- [LPS-74904]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.23.
- [LPS-74904]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.2.

## 3.5.80 - 2018-01-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.58.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.529.

## 3.5.81 - 2018-01-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.59.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.530.

## 3.5.82 - 2018-01-08

### Changed
- [LPS-76840]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.60.
- [LPS-76840]: Update the [Liferay Source Formatter] dependency to version
1.0.531.

## 3.5.83 - 2018-01-08

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 1.0.64.
- [LPS-76626]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.181.

## 3.5.84 - 2018-01-09

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.61.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.532.

## 3.5.85 - 2018-01-10

### Changed
- [LPS-73124]: Update the [Liferay Portal Tools DB Support] dependency to
version 1.0.7.
- [LPS-76226]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.62.
- [LPS-76226]: Update the [Liferay Source Formatter] dependency to version
1.0.533.

## 3.5.86 - 2018-01-11

### Changed
- [LPS-76954]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.63.
- [LPS-76954]: Update the [Liferay Source Formatter] dependency to version
1.0.534.

## 3.5.87 - 2018-01-11

### Changed
- [LPS-76957]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.64.
- [LPS-76957]: Update the [Liferay Source Formatter] dependency to version
1.0.535.

## 3.5.88 - 2018-01-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.65.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.536.

## 3.5.89 - 2018-01-14

### Changed
- [LPS-76854]: Update the [Liferay Portal Tools DB Support] dependency to
version 1.0.8.
- [LPS-77111]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.66.
- [LPS-77111]: Update the [Liferay Source Formatter] dependency to version
1.0.537.

## 3.5.90 - 2018-01-17

### Changed
- [LPS-76626]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.0.0.
- [LPS-76626]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.182.

## 3.5.91 - 2018-01-17

### Changed
- [LPS-76644]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.23.
- [LPS-76644]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.24.
- [LPS-77250]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.3.

## 3.5.92 - 2018-01-22

### Changed
- [LPS-77305]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.67.
- [LPS-77305]: Update the [Liferay Source Formatter] dependency to version
1.0.538.

## 3.5.93 - 2018-01-23

### Changed
- [LPS-77402]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.68.
- [LPS-77402]: Update the [Liferay Source Formatter] dependency to version
1.0.539.

## 3.5.94 - 2018-01-23

### Changed
- [LPS-77402]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.69.
- [LPS-77402]: Update the [Liferay Source Formatter] dependency to version
1.0.540.

## 3.5.95 - 2018-01-23

### Changed
- [LPS-77186]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.70.
- [LPS-77186]: Update the [Liferay Source Formatter] dependency to version
1.0.541.

## 3.5.96 - 2018-01-25

### Changed
- [LPS-77143]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.71.
- [LPS-77143]: Update the [Liferay Source Formatter] dependency to version
1.0.542.

## 3.5.97 - 2018-01-25

### Changed
- [LPS-77423]: Set the default Node.js version to 8.9.4.

## 3.5.98 - 2018-01-26

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.72.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.543.

## 3.5.99 - 2018-01-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.73.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.544.

## 3.5.100 - 2018-01-30

### Changed
- [LPS-77630]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.74.
- [LPS-77630]: Update the [Liferay Source Formatter] dependency to version
1.0.545.

## 3.5.101 - 2018-01-31

### Changed
- [LPS-77630]: Update the [Liferay Source Formatter] dependency to version
1.0.546.

## 3.6.0 - 2018-02-01

### Added
- [LPS-77350]: Configure Bnd to copy the `service.xml` file from the project's
root directory to the JAR file's `META-INF` directory.
- [LPS-77350]: Set the Bnd instruction `-liferay-service-xml` to
`"service.xml,*/service.xml"` by default.

### Changed
- [LPS-77350]: Update the [Liferay Ant BND] dependency to version 2.0.43.

## 3.6.1 - 2018-02-05

### Changed
- [LPS-77795]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.75.
- [LPS-77795]: Update the [Liferay Source Formatter] dependency to version
1.0.547.

## 3.6.2 - 2018-02-06

### Changed
- [LPS-77836]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.76.
- [LPS-77836]: Update the [Liferay Source Formatter] dependency to version
1.0.548.

### Fixed
- [LPS-77350]: Avoid silently ignoring `Include-Resource` Bnd headers.

## 3.6.3 - 2018-02-08

### Changed
- [LPS-77886]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.77.
- [LPS-77886]: Update the [Liferay Source Formatter] dependency to version
1.0.549.

## 3.6.4 - 2018-02-08

### Changed
- [LPS-69802]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.24.
- [LPS-69802]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.25.
- [LPS-69802]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.4.

## 3.7.0 - 2018-02-08

### Added
- [LPS-77840]: Expose `NodeDefaultsPlugin` as a Gradle plugin with ID
`com.liferay.node.defaults`.

## 3.7.1 - 2018-02-11

### Changed
- [LPS-77916]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.78.
- [LPS-77916]: Update the [Liferay Source Formatter] dependency to version
1.0.550.

## 3.7.2 - 2018-02-12

### Changed
- [LPS-77968]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.79.
- [LPS-77968]: Update the [Liferay Source Formatter] dependency to version
1.0.551.

## 3.7.3 - 2018-02-13

### Changed
- [LPS-77996]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.25.
- [LPS-77996]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.26.
- [LPS-77996]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.5.

## 3.7.4 - 2018-02-14

### Changed
- [LPS-78033]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.80.
- [LPS-78033]: Update the [Liferay Source Formatter] dependency to version
1.0.552.

## 3.7.5 - 2018-02-15

### Changed
- [LPS-78038]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.81.
- [LPS-78038]: Update the [Liferay Source Formatter] dependency to version
1.0.553.

## 3.7.6 - 2018-02-18

*No changes.*

## 3.7.7 - 2018-02-20

### Changed
- [LPS-78071]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.82.
- [LPS-78071]: Update the [Liferay Source Formatter] dependency to version
1.0.554.

## 3.7.8 - 2018-02-21

### Changed
- [LPS-78033]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.83.
- [LPS-78033]: Update the [Liferay Source Formatter] dependency to version
1.0.555.

## 3.7.9 - 2018-02-22

### Changed
- [LPS-78150]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.84.
- [LPS-78150]: Update the [Liferay Source Formatter] dependency to version
1.0.556.

## 3.8.0 - 2018-02-25

### Added
- [LPS-77532]: Add the `com.liferay.ext.plugin` plugin to build Ext plugins.

## 3.8.1 - 2018-02-26

### Changed
- [LPS-76926]: Update the [Liferay Ant BND] dependency to version 2.0.44.
- [LPS-78231]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.85.
- [LPS-78231]: Update the [Liferay Source Formatter] dependency to version
1.0.557.

## 3.8.2 - 2018-02-26

### Changed
- [LPS-78261]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.86.
- [LPS-78261]: Update the [Liferay Source Formatter] dependency to version
1.0.558.

## 3.8.3 - 2018-02-28

*No changes.*

[Gradle Bundle Plugin]: https://github.com/TomDmitriev/gradle-bundle-plugin
[Liferay Alloy Taglib]: https://github.com/liferay/alloy-taglibs
[Liferay Ant BND]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/ant-bnd
[Liferay CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/css-builder
[Liferay Gradle Plugins Alloy Taglib]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-alloy-taglib
[Liferay Gradle Plugins CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-css-builder
[Liferay Gradle Plugins DB Support]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-db-support
[Liferay Gradle Plugins Gulp]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-gulp
[Liferay Gradle Plugins JS Module Config Generator]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-module-config-generator
[Liferay Gradle Plugins JS Transpiler]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-transpiler
[Liferay Gradle Plugins Jasper JSPC]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-jasper-jspc
[Liferay Gradle Plugins Javadoc Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-javadoc-formatter
[Liferay Gradle Plugins Lang Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-lang-builder
[Liferay Gradle Plugins Service Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-service-builder
[Liferay Gradle Plugins Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-source-formatter
[Liferay Gradle Plugins Soy]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-soy
[Liferay Gradle Plugins TLD Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-tld-formatter
[Liferay Gradle Plugins TLDDoc Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-tlddoc-builder
[Liferay Gradle Plugins Test Integration]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-test-integration
[Liferay Gradle Plugins Theme Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-theme-builder
[Liferay Gradle Plugins Upgrade Table Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-upgrade-table-builder
[Liferay Gradle Plugins WSDD Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-wsdd-builder
[Liferay Gradle Plugins WSDL Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-wsdl-builder
[Liferay Gradle Plugins XML Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-xml-formatter
[Liferay Gradle Util]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-util
[Liferay Jasper JSPC]: https://github.com/liferay/liferay-portal/tree/master/modules/util/jasper-jspc
[Liferay Javadoc Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/javadoc-formatter
[Liferay Lang Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/lang-builder
[Liferay Portal Tools DB Support]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-db-support
[Liferay Portal Tools Service Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-service-builder
[Liferay Portal Tools Upgrade Table Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-upgrade-table-builder
[Liferay Portal Tools WSDD Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-wsdd-builder
[Liferay Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/source-formatter
[Liferay TLD Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/tld-formatter
[Liferay Whip]: https://github.com/liferay/liferay-portal/tree/master/modules/test/whip
[Liferay XML Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/xml-formatter
[LPS-52675]: https://issues.liferay.com/browse/LPS-52675
[LPS-53392]: https://issues.liferay.com/browse/LPS-53392
[LPS-58672]: https://issues.liferay.com/browse/LPS-58672
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-62970]: https://issues.liferay.com/browse/LPS-62970
[LPS-64098]: https://issues.liferay.com/browse/LPS-64098
[LPS-65930]: https://issues.liferay.com/browse/LPS-65930
[LPS-66222]: https://issues.liferay.com/browse/LPS-66222
[LPS-66396]: https://issues.liferay.com/browse/LPS-66396
[LPS-66853]: https://issues.liferay.com/browse/LPS-66853
[LPS-66891]: https://issues.liferay.com/browse/LPS-66891
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67352]: https://issues.liferay.com/browse/LPS-67352
[LPS-67434]: https://issues.liferay.com/browse/LPS-67434
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-67688]: https://issues.liferay.com/browse/LPS-67688
[LPS-67766]: https://issues.liferay.com/browse/LPS-67766
[LPS-67804]: https://issues.liferay.com/browse/LPS-67804
[LPS-67986]: https://issues.liferay.com/browse/LPS-67986
[LPS-67996]: https://issues.liferay.com/browse/LPS-67996
[LPS-68014]: https://issues.liferay.com/browse/LPS-68014
[LPS-68035]: https://issues.liferay.com/browse/LPS-68035
[LPS-68131]: https://issues.liferay.com/browse/LPS-68131
[LPS-68165]: https://issues.liferay.com/browse/LPS-68165
[LPS-68289]: https://issues.liferay.com/browse/LPS-68289
[LPS-68297]: https://issues.liferay.com/browse/LPS-68297
[LPS-68298]: https://issues.liferay.com/browse/LPS-68298
[LPS-68334]: https://issues.liferay.com/browse/LPS-68334
[LPS-68405]: https://issues.liferay.com/browse/LPS-68405
[LPS-68415]: https://issues.liferay.com/browse/LPS-68415
[LPS-68485]: https://issues.liferay.com/browse/LPS-68485
[LPS-68504]: https://issues.liferay.com/browse/LPS-68504
[LPS-68564]: https://issues.liferay.com/browse/LPS-68564
[LPS-68598]: https://issues.liferay.com/browse/LPS-68598
[LPS-68611]: https://issues.liferay.com/browse/LPS-68611
[LPS-68618]: https://issues.liferay.com/browse/LPS-68618
[LPS-68666]: https://issues.liferay.com/browse/LPS-68666
[LPS-68779]: https://issues.liferay.com/browse/LPS-68779
[LPS-68838]: https://issues.liferay.com/browse/LPS-68838
[LPS-68839]: https://issues.liferay.com/browse/LPS-68839
[LPS-68848]: https://issues.liferay.com/browse/LPS-68848
[LPS-68917]: https://issues.liferay.com/browse/LPS-68917
[LPS-68923]: https://issues.liferay.com/browse/LPS-68923
[LPS-68979]: https://issues.liferay.com/browse/LPS-68979
[LPS-68980]: https://issues.liferay.com/browse/LPS-68980
[LPS-68995]: https://issues.liferay.com/browse/LPS-68995
[LPS-69013]: https://issues.liferay.com/browse/LPS-69013
[LPS-69026]: https://issues.liferay.com/browse/LPS-69026
[LPS-69139]: https://issues.liferay.com/browse/LPS-69139
[LPS-69223]: https://issues.liferay.com/browse/LPS-69223
[LPS-69248]: https://issues.liferay.com/browse/LPS-69248
[LPS-69271]: https://issues.liferay.com/browse/LPS-69271
[LPS-69445]: https://issues.liferay.com/browse/LPS-69445
[LPS-69470]: https://issues.liferay.com/browse/LPS-69470
[LPS-69488]: https://issues.liferay.com/browse/LPS-69488
[LPS-69492]: https://issues.liferay.com/browse/LPS-69492
[LPS-69501]: https://issues.liferay.com/browse/LPS-69501
[LPS-69518]: https://issues.liferay.com/browse/LPS-69518
[LPS-69618]: https://issues.liferay.com/browse/LPS-69618
[LPS-69661]: https://issues.liferay.com/browse/LPS-69661
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677
[LPS-69706]: https://issues.liferay.com/browse/LPS-69706
[LPS-69730]: https://issues.liferay.com/browse/LPS-69730
[LPS-69802]: https://issues.liferay.com/browse/LPS-69802
[LPS-69824]: https://issues.liferay.com/browse/LPS-69824
[LPS-69838]: https://issues.liferay.com/browse/LPS-69838
[LPS-69899]: https://issues.liferay.com/browse/LPS-69899
[LPS-69920]: https://issues.liferay.com/browse/LPS-69920
[LPS-69926]: https://issues.liferay.com/browse/LPS-69926
[LPS-69980]: https://issues.liferay.com/browse/LPS-69980
[LPS-70036]: https://issues.liferay.com/browse/LPS-70036
[LPS-70060]: https://issues.liferay.com/browse/LPS-70060
[LPS-70084]: https://issues.liferay.com/browse/LPS-70084
[LPS-70092]: https://issues.liferay.com/browse/LPS-70092
[LPS-70274]: https://issues.liferay.com/browse/LPS-70274
[LPS-70282]: https://issues.liferay.com/browse/LPS-70282
[LPS-70336]: https://issues.liferay.com/browse/LPS-70336
[LPS-70379]: https://issues.liferay.com/browse/LPS-70379
[LPS-70451]: https://issues.liferay.com/browse/LPS-70451
[LPS-70494]: https://issues.liferay.com/browse/LPS-70494
[LPS-70510]: https://issues.liferay.com/browse/LPS-70510
[LPS-70515]: https://issues.liferay.com/browse/LPS-70515
[LPS-70555]: https://issues.liferay.com/browse/LPS-70555
[LPS-70618]: https://issues.liferay.com/browse/LPS-70618
[LPS-70634]: https://issues.liferay.com/browse/LPS-70634
[LPS-70677]: https://issues.liferay.com/browse/LPS-70677
[LPS-70707]: https://issues.liferay.com/browse/LPS-70707
[LPS-70870]: https://issues.liferay.com/browse/LPS-70870
[LPS-70890]: https://issues.liferay.com/browse/LPS-70890
[LPS-70941]: https://issues.liferay.com/browse/LPS-70941
[LPS-71005]: https://issues.liferay.com/browse/LPS-71005
[LPS-71048]: https://issues.liferay.com/browse/LPS-71048
[LPS-71117]: https://issues.liferay.com/browse/LPS-71117
[LPS-71118]: https://issues.liferay.com/browse/LPS-71118
[LPS-71164]: https://issues.liferay.com/browse/LPS-71164
[LPS-71222]: https://issues.liferay.com/browse/LPS-71222
[LPS-71331]: https://issues.liferay.com/browse/LPS-71331
[LPS-71375]: https://issues.liferay.com/browse/LPS-71375
[LPS-71555]: https://issues.liferay.com/browse/LPS-71555
[LPS-71558]: https://issues.liferay.com/browse/LPS-71558
[LPS-71591]: https://issues.liferay.com/browse/LPS-71591
[LPS-71601]: https://issues.liferay.com/browse/LPS-71601
[LPS-71603]: https://issues.liferay.com/browse/LPS-71603
[LPS-71686]: https://issues.liferay.com/browse/LPS-71686
[LPS-71722]: https://issues.liferay.com/browse/LPS-71722
[LPS-71728]: https://issues.liferay.com/browse/LPS-71728
[LPS-71826]: https://issues.liferay.com/browse/LPS-71826
[LPS-71925]: https://issues.liferay.com/browse/LPS-71925
[LPS-72030]: https://issues.liferay.com/browse/LPS-72030
[LPS-72039]: https://issues.liferay.com/browse/LPS-72039
[LPS-72102]: https://issues.liferay.com/browse/LPS-72102
[LPS-72152]: https://issues.liferay.com/browse/LPS-72152
[LPS-72252]: https://issues.liferay.com/browse/LPS-72252
[LPS-72326]: https://issues.liferay.com/browse/LPS-72326
[LPS-72340]: https://issues.liferay.com/browse/LPS-72340
[LPS-72347]: https://issues.liferay.com/browse/LPS-72347
[LPS-72365]: https://issues.liferay.com/browse/LPS-72365
[LPS-72514]: https://issues.liferay.com/browse/LPS-72514
[LPS-72572]: https://issues.liferay.com/browse/LPS-72572
[LPS-72606]: https://issues.liferay.com/browse/LPS-72606
[LPS-72656]: https://issues.liferay.com/browse/LPS-72656
[LPS-72705]: https://issues.liferay.com/browse/LPS-72705
[LPS-72723]: https://issues.liferay.com/browse/LPS-72723
[LPS-72750]: https://issues.liferay.com/browse/LPS-72750
[LPS-72851]: https://issues.liferay.com/browse/LPS-72851
[LPS-72858]: https://issues.liferay.com/browse/LPS-72858
[LPS-72868]: https://issues.liferay.com/browse/LPS-72868
[LPS-72912]: https://issues.liferay.com/browse/LPS-72912
[LPS-72914]: https://issues.liferay.com/browse/LPS-72914
[LPS-73058]: https://issues.liferay.com/browse/LPS-73058
[LPS-73070]: https://issues.liferay.com/browse/LPS-73070
[LPS-73124]: https://issues.liferay.com/browse/LPS-73124
[LPS-73147]: https://issues.liferay.com/browse/LPS-73147
[LPS-73148]: https://issues.liferay.com/browse/LPS-73148
[LPS-73156]: https://issues.liferay.com/browse/LPS-73156
[LPS-73261]: https://issues.liferay.com/browse/LPS-73261
[LPS-73353]: https://issues.liferay.com/browse/LPS-73353
[LPS-73383]: https://issues.liferay.com/browse/LPS-73383
[LPS-73408]: https://issues.liferay.com/browse/LPS-73408
[LPS-73470]: https://issues.liferay.com/browse/LPS-73470
[LPS-73472]: https://issues.liferay.com/browse/LPS-73472
[LPS-73481]: https://issues.liferay.com/browse/LPS-73481
[LPS-73489]: https://issues.liferay.com/browse/LPS-73489
[LPS-73495]: https://issues.liferay.com/browse/LPS-73495
[LPS-73525]: https://issues.liferay.com/browse/LPS-73525
[LPS-73584]: https://issues.liferay.com/browse/LPS-73584
[LPS-73600]: https://issues.liferay.com/browse/LPS-73600
[LPS-73642]: https://issues.liferay.com/browse/LPS-73642
[LPS-73725]: https://issues.liferay.com/browse/LPS-73725
[LPS-73818]: https://issues.liferay.com/browse/LPS-73818
[LPS-73855]: https://issues.liferay.com/browse/LPS-73855
[LPS-73935]: https://issues.liferay.com/browse/LPS-73935
[LPS-73967]: https://issues.liferay.com/browse/LPS-73967
[LPS-74034]: https://issues.liferay.com/browse/LPS-74034
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
[LPS-74222]: https://issues.liferay.com/browse/LPS-74222
[LPS-74250]: https://issues.liferay.com/browse/LPS-74250
[LPS-74269]: https://issues.liferay.com/browse/LPS-74269
[LPS-74278]: https://issues.liferay.com/browse/LPS-74278
[LPS-74314]: https://issues.liferay.com/browse/LPS-74314
[LPS-74315]: https://issues.liferay.com/browse/LPS-74315
[LPS-74328]: https://issues.liferay.com/browse/LPS-74328
[LPS-74343]: https://issues.liferay.com/browse/LPS-74343
[LPS-74345]: https://issues.liferay.com/browse/LPS-74345
[LPS-74348]: https://issues.liferay.com/browse/LPS-74348
[LPS-74368]: https://issues.liferay.com/browse/LPS-74368
[LPS-74373]: https://issues.liferay.com/browse/LPS-74373
[LPS-74426]: https://issues.liferay.com/browse/LPS-74426
[LPS-74433]: https://issues.liferay.com/browse/LPS-74433
[LPS-74449]: https://issues.liferay.com/browse/LPS-74449
[LPS-74457]: https://issues.liferay.com/browse/LPS-74457
[LPS-74475]: https://issues.liferay.com/browse/LPS-74475
[LPS-74490]: https://issues.liferay.com/browse/LPS-74490
[LPS-74503]: https://issues.liferay.com/browse/LPS-74503
[LPS-74526]: https://issues.liferay.com/browse/LPS-74526
[LPS-74538]: https://issues.liferay.com/browse/LPS-74538
[LPS-74544]: https://issues.liferay.com/browse/LPS-74544
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
[LPS-74904]: https://issues.liferay.com/browse/LPS-74904
[LPS-74933]: https://issues.liferay.com/browse/LPS-74933
[LPS-75009]: https://issues.liferay.com/browse/LPS-75009
[LPS-75010]: https://issues.liferay.com/browse/LPS-75010
[LPS-75047]: https://issues.liferay.com/browse/LPS-75047
[LPS-75096]: https://issues.liferay.com/browse/LPS-75096
[LPS-75100]: https://issues.liferay.com/browse/LPS-75100
[LPS-75164]: https://issues.liferay.com/browse/LPS-75164
[LPS-75175]: https://issues.liferay.com/browse/LPS-75175
[LPS-75238]: https://issues.liferay.com/browse/LPS-75238
[LPS-75239]: https://issues.liferay.com/browse/LPS-75239
[LPS-75247]: https://issues.liferay.com/browse/LPS-75247
[LPS-75254]: https://issues.liferay.com/browse/LPS-75254
[LPS-75273]: https://issues.liferay.com/browse/LPS-75273
[LPS-75323]: https://issues.liferay.com/browse/LPS-75323
[LPS-75399]: https://issues.liferay.com/browse/LPS-75399
[LPS-75427]: https://issues.liferay.com/browse/LPS-75427
[LPS-75430]: https://issues.liferay.com/browse/LPS-75430
[LPS-75488]: https://issues.liferay.com/browse/LPS-75488
[LPS-75554]: https://issues.liferay.com/browse/LPS-75554
[LPS-75589]: https://issues.liferay.com/browse/LPS-75589
[LPS-75610]: https://issues.liferay.com/browse/LPS-75610
[LPS-75613]: https://issues.liferay.com/browse/LPS-75613
[LPS-75624]: https://issues.liferay.com/browse/LPS-75624
[LPS-75633]: https://issues.liferay.com/browse/LPS-75633
[LPS-75745]: https://issues.liferay.com/browse/LPS-75745
[LPS-75778]: https://issues.liferay.com/browse/LPS-75778
[LPS-75798]: https://issues.liferay.com/browse/LPS-75798
[LPS-75829]: https://issues.liferay.com/browse/LPS-75829
[LPS-75859]: https://issues.liferay.com/browse/LPS-75859
[LPS-75901]: https://issues.liferay.com/browse/LPS-75901
[LPS-75952]: https://issues.liferay.com/browse/LPS-75952
[LPS-75965]: https://issues.liferay.com/browse/LPS-75965
[LPS-75971]: https://issues.liferay.com/browse/LPS-75971
[LPS-76018]: https://issues.liferay.com/browse/LPS-76018
[LPS-76110]: https://issues.liferay.com/browse/LPS-76110
[LPS-76202]: https://issues.liferay.com/browse/LPS-76202
[LPS-76221]: https://issues.liferay.com/browse/LPS-76221
[LPS-76224]: https://issues.liferay.com/browse/LPS-76224
[LPS-76226]: https://issues.liferay.com/browse/LPS-76226
[LPS-76256]: https://issues.liferay.com/browse/LPS-76256
[LPS-76326]: https://issues.liferay.com/browse/LPS-76326
[LPS-76475]: https://issues.liferay.com/browse/LPS-76475
[LPS-76601]: https://issues.liferay.com/browse/LPS-76601
[LPS-76626]: https://issues.liferay.com/browse/LPS-76626
[LPS-76644]: https://issues.liferay.com/browse/LPS-76644
[LPS-76747]: https://issues.liferay.com/browse/LPS-76747
[LPS-76840]: https://issues.liferay.com/browse/LPS-76840
[LPS-76854]: https://issues.liferay.com/browse/LPS-76854
[LPS-76954]: https://issues.liferay.com/browse/LPS-76954
[LPS-76957]: https://issues.liferay.com/browse/LPS-76957
[LPS-77111]: https://issues.liferay.com/browse/LPS-77111
[LPS-76926]: https://issues.liferay.com/browse/LPS-76926
[LPS-77143]: https://issues.liferay.com/browse/LPS-77143
[LPS-77186]: https://issues.liferay.com/browse/LPS-77186
[LPS-77250]: https://issues.liferay.com/browse/LPS-77250
[LPS-77305]: https://issues.liferay.com/browse/LPS-77305
[LPS-77350]: https://issues.liferay.com/browse/LPS-77350
[LPS-77402]: https://issues.liferay.com/browse/LPS-77402
[LPS-77423]: https://issues.liferay.com/browse/LPS-77423
[LPS-77532]: https://issues.liferay.com/browse/LPS-77532
[LPS-77630]: https://issues.liferay.com/browse/LPS-77630
[LPS-77795]: https://issues.liferay.com/browse/LPS-77795
[LPS-77836]: https://issues.liferay.com/browse/LPS-77836
[LPS-77840]: https://issues.liferay.com/browse/LPS-77840
[LPS-77886]: https://issues.liferay.com/browse/LPS-77886
[LPS-77916]: https://issues.liferay.com/browse/LPS-77916
[LPS-77968]: https://issues.liferay.com/browse/LPS-77968
[LPS-77996]: https://issues.liferay.com/browse/LPS-77996
[LPS-78033]: https://issues.liferay.com/browse/LPS-78033
[LPS-78038]: https://issues.liferay.com/browse/LPS-78038
[LPS-78071]: https://issues.liferay.com/browse/LPS-78071
[LPS-78150]: https://issues.liferay.com/browse/LPS-78150
[LPS-78231]: https://issues.liferay.com/browse/LPS-78231
[LPS-78261]: https://issues.liferay.com/browse/LPS-78261
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023