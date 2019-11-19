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

## 3.8.4 - 2018-03-01

### Changed
- [LPS-76926]: Update the [Liferay Ant BND] dependency to version 2.0.45.

## 3.8.5 - 2018-03-02

### Changed
- [LPS-78436]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.87.
- [LPS-78436]: Update the [Liferay Source Formatter] dependency to version
1.0.559.

## 3.8.6 - 2018-03-05

### Changed
- [LPS-76997]: Update the [Liferay Ant BND] dependency to version 2.0.46.

## 3.8.7 - 2018-03-05

### Changed
- [LPS-78459]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.88.
- [LPS-78459]: Update the [Liferay Source Formatter] dependency to version
1.0.560.

## 3.8.8 - 2018-03-07

### Changed
- [LPS-78050]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.89.
- [LPS-78050]: Update the [Liferay Source Formatter] dependency to version
1.0.561.

## 3.8.9 - 2018-03-07

### Changed
- [LPS-77425]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.90.
- [LPS-77425]: Update the [Liferay Source Formatter] dependency to version
1.0.562.

## 3.8.10 - 2018-03-07

### Changed
- [LPS-78571]: Update the [Liferay Ant BND] dependency to version 2.0.47.

## 3.8.11 - 2018-03-07

### Changed
- [LPS-78459]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.91.
- [LPS-78459]: Update the [Liferay Source Formatter] dependency to version
1.0.563.

## 3.8.12 - 2018-03-07

### Changed
- [LPS-78493]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.2.0.
- [LPS-78493]: Update the [Liferay Lang Builder] dependency to version 1.0.23.

## 3.8.13 - 2018-03-08

### Changed
- [LPS-78459]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.92.
- [LPS-78459]: Update the [Liferay Source Formatter] dependency to version
1.0.564.
- [LPS-78571]: Update the [Liferay Ant BND] dependency to version 2.0.48.

## 3.8.14 - 2018-03-10

### Changed
- [LPS-78308]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.93.
- [LPS-78308]: Update the [Liferay Source Formatter] dependency to version
1.0.565.

## 3.8.15 - 2018-03-12

### Changed
- [LPS-78669]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.94.
- [LPS-78669]: Update the [Liferay Source Formatter] dependency to version
1.0.566.

## 3.8.16 - 2018-03-12

### Changed
- [LPS-78269]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.95.
- [LPS-78269]: Update the [Liferay Source Formatter] dependency to version
1.0.567.

## 3.8.17 - 2018-03-13

### Changed
- [LPS-78767]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.96.
- [LPS-78767]: Update the [Liferay Source Formatter] dependency to version
1.0.568.

## 3.8.18 - 2018-03-13

### Changed
- [LPS-78767]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.97.
- [LPS-78767]: Update the [Liferay Source Formatter] dependency to version
1.0.569.

## 3.8.19 - 2018-03-15

### Changed
- [LPS-78741]: Set the default Node.js version to 8.10.0.
- [LPS-78741]: Set the default npm version to 5.7.1.
- [LPS-78741]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.26.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.27.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.6.

## 3.8.20 - 2018-03-16

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.98.
- [LPS-78772]: Update the [Liferay Source Formatter] dependency to version
1.0.570.

## 3.8.21 - 2018-03-16

### Changed
- [LPS-78845]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.2.1.
- [LPS-78845]: Update the [Liferay Lang Builder] dependency to version 1.0.24.

## 3.9.0 - 2018-03-16

### Added
- [LPS-77425]: Add support for multiple portal versions when selecting the
default tool dependency versions. For example, setting the project property
`portal.version` to `7.0.x` now instructs Gradle to use the portal tools for
Liferay 7.0.x instead of the latest ones.

## 3.9.1 - 2018-03-17

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.99.
- [LPS-78772]: Update the [Liferay Source Formatter] dependency to version
1.0.571.

## 3.9.2 - 2018-03-18

### Changed
- [LPS-78911]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.100.
- [LPS-78911]: Update the [Liferay Source Formatter] dependency to version
1.0.572.

## 3.9.3 - 2018-03-19

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.101.
- [LPS-78772]: Update the [Liferay Source Formatter] dependency to version
1.0.573.

## 3.9.4 - 2018-03-19

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.102.
- [LPS-78772]: Update the [Liferay Source Formatter] dependency to version
1.0.574.

## 3.9.5 - 2018-03-20

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.103.
- [LPS-78772]: Update the [Liferay Source Formatter] dependency to version
1.0.575.

## 3.9.6 - 2018-03-20

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.104.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.576.

## 3.9.7 - 2018-03-20

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.105.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.577.

## 3.9.8 - 2018-03-21

### Changed
- [LPS-78750]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.2.1.

## 3.9.9 - 2018-03-21

### Changed
- [LPS-78772]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.0.
- [LPS-78772]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.183.

## 3.9.10 - 2018-03-22

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.27.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.28.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.7.

## 3.9.11 - 2018-03-23

### Changed
- [LPS-78911]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.106.
- [LPS-78911]: Update the [Liferay Source Formatter] dependency to version
1.0.578.

## 3.9.12 - 2018-03-26

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.107.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.579.

## 3.9.13 - 2018-03-26

### Changed
- [LPS-79191]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.108.
- [LPS-79191]: Update the [Liferay Source Formatter] dependency to version
1.0.580.

## 3.9.14 - 2018-03-27

### Changed
- [LPS-79192]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.109.
- [LPS-79192]: Update the [Liferay Source Formatter] dependency to version
1.0.581.

## 3.9.15 - 2018-03-27

### Changed
- [LPS-78477]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.1.
- [LPS-78477]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.184.

## 3.9.16 - 2018-03-27

### Changed
- [LPS-78854]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.110.
- [LPS-78854]: Update the [Liferay Source Formatter] dependency to version
1.0.582.

## 3.9.17 - 2018-03-27

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.111.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.583.

## 3.9.18 - 2018-03-27

### Changed
- [LPS-79131]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.112.
- [LPS-79131]: Update the [Liferay Source Formatter] dependency to version
1.0.584.

## 3.9.19 - 2018-03-27

### Changed
- [LPS-79226]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.113.
- [LPS-79226]: Update the [Liferay Source Formatter] dependency to version
1.0.585.

## 3.9.20 - 2018-03-27

### Changed
- [LPS-78901]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.2.
- [LPS-78901]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.185.

## 3.9.21 - 2018-03-29

### Changed
- [LPS-79286]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.114.
- [LPS-79286]: Update the [Liferay Source Formatter] dependency to version
1.0.586.

## 3.9.22 - 2018-03-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.3.
- [LPS-74544]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.186.

## 3.10.0 - 2018-03-30

### Added
- [LPS-78741]: Add a method to get the project property `portal.version`.

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.28.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.29.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.8.
- [LPS-79282]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.115.
- [LPS-79282]: Update the [Liferay Source Formatter] dependency to version
1.0.587.

## 3.10.1 - 2018-03-31

### Changed
- [LPS-79248]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.116.
- [LPS-79248]: Update the [Liferay Source Formatter] dependency to version
1.0.588.

### Fixed
- [LPS-69247]: Pass lower case project properties to Bnd when executing the
`buildWSDDJar` task.

## 3.10.2 - 2018-04-02

### Changed
- [LPS-79192]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.117.
- [LPS-79192]: Update the [Liferay Source Formatter] dependency to version
1.0.589.

## 3.10.3 - 2018-04-02

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.118.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.590.

## 3.10.4 - 2018-04-02

### Changed
- [LPS-75010]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.119.
- [LPS-75010]: Update the [Liferay Source Formatter] dependency to version
1.0.591.
- [LPS-78571]: Update the [Liferay Ant BND] dependency to version 2.0.49.

## 3.10.5 - 2018-04-03

### Changed
- [LPS-74110]: Suppress Bnd warning about deprecated annotations.
- [LPS-74110]: Update the [Liferay Ant BND] dependency to version 2.0.50.
- [LPS-75010]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.120.
- [LPS-75010]: Update the [Liferay Source Formatter] dependency to version
1.0.592.

## 3.10.6 - 2018-04-04

### Changed
- [LPS-79360]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.121.
- [LPS-79360]: Update the [Liferay Source Formatter] dependency to version
1.0.593.

## 3.10.7 - 2018-04-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.122.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.594.

## 3.10.8 - 2018-04-04

### Changed
- [LPS-79365]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.4.
- [LPS-79365]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.187.

## 3.10.9 - 2018-04-05

### Added
- [LPS-74171]: Add support for different app server configurations based on the
portal version.

### Changed
- [LPS-78741]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.29.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.30.
- [LPS-78741]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.9.
- [LPS-79365]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.5.
- [LPS-79365]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.188.

## 3.10.10 - 2018-04-05

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.123.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.595.

## 3.10.11 - 2018-04-06

### Changed
- [LPS-78971]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.6.
- [LPS-78971]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.189.

## 3.10.12 - 2018-04-09

### Added
- [LPS-79450]: Filter out the `.git`, `.gradle`, `build`, `node_modules`, and
`tmp` files in the project directory for Eclipse.

### Changed
- [LPS-79385]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.7.
- [LPS-79385]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.190.

## 3.10.13 - 2018-04-09

### Changed
- [LPS-74171]: Update the `liferay.appServers.tomcat.version` property's default
value to `9.0.6`.

## 3.10.14 - 2018-04-10

### Changed
- [LPS-78308]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.124.
- [LPS-78308]: Update the [Liferay Source Formatter] dependency to version
1.0.596.

## 3.10.15 - 2018-04-10

### Changed
- [LPS-78911]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.125.
- [LPS-78911]: Update the [Liferay Source Formatter] dependency to version
1.0.597.

## 3.10.16 - 2018-04-11

### Changed
- [LPS-75010]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.126.
- [LPS-75010]: Update the [Liferay Source Formatter] dependency to version
1.0.598.

## 3.10.17 - 2018-04-12

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.127.
- [LPS-79576]: Update the [Liferay Source Formatter] dependency to version
1.0.600.

## 3.10.18 - 2018-04-12

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.128.
- [LPS-79576]: Update the [Liferay Source Formatter] dependency to version
1.0.601.

## 3.11.0 - 2018-04-12

### Added
- [LPS-75530]: Add `gradlew watch --continuous` to quickly redeploy OSGi modules
at any code change.
- [LPS-75530]: Set the [Liferay Gogo Shell Client] dependency to version 1.0.0.

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.129.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.602.

## 3.11.1 - 2018-04-13

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.130.
- [LPS-79576]: Update the [Liferay Source Formatter] dependency to version
1.0.603.

## 3.11.2 - 2018-04-13

### Changed
- [LPS-77639]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.8.
- [LPS-77639]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.191.

## 3.11.3 - 2018-04-13

### Changed
- [LPS-79623]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.9.
- [LPS-79623]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.192.

## 3.11.4 - 2018-04-16

### Changed
- [LPS-79576]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.131.
- [LPS-79576]: Update the [Liferay Source Formatter] dependency to version
1.0.604.

## 3.11.5 - 2018-04-16

### Changed
- [LPS-79665]: Update the `liferay.appServers.wildfly.version` property's
default value to `11.0.0`.
- [LPS-79576]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.132.
- [LPS-79576]: Update the [Liferay Source Formatter] dependency to version
1.0.605.

## 3.11.6 - 2018-04-17

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.133.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.606.

## 3.11.7 - 2018-04-18

### Changed
- [LPS-79336]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.10.
- [LPS-79336]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.193.
- [LPS-79755]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.134.
- [LPS-79755]: Update the [Liferay Source Formatter] dependency to version
1.0.607.

## 3.11.8 - 2018-04-18

### Changed
- [LPS-77645]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.11.
- [LPS-77645]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.194.
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.135.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.608.

## 3.11.9 - 2018-04-19

### Changed
- [LPS-79386]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.12.
- [LPS-79386]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.195.

## 3.11.10 - 2018-04-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.136.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.609.

## 3.11.11 - 2018-04-20

### Changed
- [LPS-79919]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.13.
- [LPS-79919]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.196.
- [LPS-79919]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.137.
- [LPS-79919]: Update the [Liferay Source Formatter] dependency to version
1.0.610.

## 3.11.12 - 2018-04-20

### Changed
- [LPS-79919]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.138.
- [LPS-79919]: Update the [Liferay Source Formatter] dependency to version
1.0.611.

## 3.11.13 - 2018-04-22

### Changed
- [LPS-75049]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.139.
- [LPS-75049]: Update the [Liferay Source Formatter] dependency to version
1.0.612.

## 3.11.14 - 2018-04-23

### Changed
- [LPS-79953]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.14.
- [LPS-79953]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.197.

## 3.11.15 - 2018-04-23

### Changed
- [LPS-80055]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.15.
- [LPS-80055]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.198.

## 3.11.16 - 2018-04-23

### Changed
- [LPS-79799]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.16.
- [LPS-79799]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.199.

## 3.11.17 - 2018-04-24

### Changed
- [LPS-80064]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.140.
- [LPS-80064]: Update the [Liferay Source Formatter] dependency to version
1.0.613.
- [LPS-80091]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.17.
- [LPS-80091]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.200.

## 3.11.18 - 2018-04-25

### Changed
- [LPS-66797]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.18.
- [LPS-66797]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.201.
- [LPS-79963]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.141.
- [LPS-79963]: Update the [Liferay Source Formatter] dependency to version
1.0.614.

## 3.11.19 - 2018-04-25

### Changed
- [LPS-79388]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.19.
- [LPS-79388]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.202.

## 3.11.20 - 2018-04-26

### Changed
- [LPS-80184]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.20.
- [LPS-80184]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.203.

## 3.11.21 - 2018-04-26

### Changed
- [LPS-80125]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.21.
- [LPS-80125]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.204.

## 3.11.22 - 2018-04-26

### Changed
- [LPS-80123]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.22.
- [LPS-80123]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.205.

## 3.11.23 - 2018-04-29

### Changed
- [LPS-79755]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.142.
- [LPS-79755]: Update the [Liferay Source Formatter] dependency to version
1.0.615.
- [LPS-80123]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.23.
- [LPS-80123]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.206.

## 3.11.24 - 2018-04-30

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.143.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.616.

## 3.11.25 - 2018-04-30

### Changed
- [LPS-80122]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.24.
- [LPS-80122]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.207.

## 3.11.26 - 2018-05-01

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.144.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.617.
- [LPS-79959]: Update the `liferay.appServers.jboss.version` property's default
value to `7.1.0`.
- [LPS-80184]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.25.
- [LPS-80184]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.208.

## 3.11.27 - 2018-05-02

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.145.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.618.

## 3.11.28 - 2018-05-02

### Changed
- [LPS-80332]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.146.
- [LPS-80332]: Update the [Liferay Source Formatter] dependency to version
1.0.619.

## 3.11.29 - 2018-05-02

*No changes.*

## 3.11.30 - 2018-05-03

### Changed
- [LPS-80386]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.26.
- [LPS-80386]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.209.

## 3.11.31 - 2018-05-03

### Changed
- [LPS-80466]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.27.
- [LPS-80466]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.210.

## 3.11.32 - 2018-05-03

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.147.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.620.

## 3.11.33 - 2018-05-04

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.148.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.621.

## 3.11.34 - 2018-05-06

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.149.
- [LPS-80517]: Update the [Liferay Source Formatter] dependency to version
1.0.622.

## 3.11.35 - 2018-05-06

### Changed
- [LPS-80520]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.150.
- [LPS-80520]: Update the [Liferay Source Formatter] dependency to version
1.0.623.

## 3.11.36 - 2018-05-07

### Changed
- [LPS-75530]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.30.
- [LPS-75530]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.31.
- [LPS-75530]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.10.

## 3.11.37 - 2018-05-07

### Changed
- [LPS-78312]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.151.
- [LPS-78312]: Update the [Liferay Source Formatter] dependency to version
1.0.624.
- [LPS-80513]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.28.
- [LPS-80513]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.211.

## 3.11.38 - 2018-05-08

### Changed
- [LPS-80544]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.29.
- [LPS-80544]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.212.

## 3.11.39 - 2018-05-10

### Changed
- [LPS-80332]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.152.
- [LPS-80332]: Update the [Liferay Source Formatter] dependency to version
1.0.625.

## 3.12.0 - 2018-05-10

### Added
- [LPS-79453]: Add the `com.liferay.osgi.ext.plugin` plugin to build Ext OSGi
modules.

### Changed
- [LPS-80332]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.153.
- [LPS-80332]: Update the [Liferay Source Formatter] dependency to version
1.0.626.

### Fixed
- [LPS-79453]: Avoid adding the `-ext` suffix twice when building Ext plugins.
- [LPS-79453]: Fix the `deploy` task of Ext plugins.

## 3.12.1 - 2018-05-13

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.154.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.627.
- [LPS-80840]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.30.
- [LPS-80840]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.213.

## 3.12.2 - 2018-05-14

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.155.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.628.
- [LPS-79799]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.31.
- [LPS-79799]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.214.

## 3.12.3 - 2018-05-14

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.156.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.629.

## 3.12.4 - 2018-05-15

### Changed
- [LPS-80920]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.32.
- [LPS-80920]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.215.

## 3.12.5 - 2018-05-15

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.157.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.630.

## 3.12.6 - 2018-05-15

### Changed
- [LPS-79262]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.33.
- [LPS-79262]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.216.

## 3.12.7 - 2018-05-15

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.158.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.631.

## 3.12.8 - 2018-05-16

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.159.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.632.

## 3.12.9 - 2018-05-17

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.160.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.633.

## 3.12.10 - 2018-05-17

### Changed
- [LPS-81106]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.161.
- [LPS-81106]: Update the [Liferay Source Formatter] dependency to version
1.0.634.

## 3.12.11 - 2018-05-17

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.162.
- [LPS-80517]: Update the [Liferay Source Formatter] dependency to version
1.0.635.

## 3.12.12 - 2018-05-19

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.163.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.636.
- [LPS-80920]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.34.
- [LPS-80920]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.217.

## 3.12.13 - 2018-05-21

### Changed
- [LPS-79963]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.164.
- [LPS-79963]: Update the [Liferay Source Formatter] dependency to version
1.0.637.

## 3.12.14 - 2018-05-21

### Changed
- [LPS-78986]: Update the `app.server.websphere.version` property's default
value to `9.0.0.0`.
- [LPS-79742]: Update the `app.server.tcserver.version` property's default value
to `4.0.0`.
- [LPS-80347]: Update the `app.server.weblogic.version` property's default value
to `12.2.1`.
- [LPS-81106]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.165.
- [LPS-81106]: Update the [Liferay Source Formatter] dependency to version
1.0.638.

## 3.12.15 - 2018-05-22

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.166.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.639.

## 3.12.16 - 2018-05-23

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.167.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.640.
- [LPS-80723]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.35.
- [LPS-80723]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.218.

## 3.12.17 - 2018-05-23

### Changed
- [LPS-79709]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.168.
- [LPS-79709]: Update the [Liferay Source Formatter] dependency to version
1.0.641.

## 3.12.18 - 2018-05-23

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.169.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.642.
- [LPS-81404]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.36.
- [LPS-81404]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.219.

## 3.12.19 - 2018-05-24

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.170.
- [LPS-80517]: Update the [Liferay Source Formatter] dependency to version
1.0.643.

## 3.12.20 - 2018-05-28

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.171.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.644.

## 3.12.21 - 2018-05-29

### Added
- [LPS-81635]: Set the Bnd instruction `-contract` to
`"JavaPortlet,JavaServlet"` by default.

### Changed
- [LPS-80517]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.172.
- [LPS-80517]: Update the [Liferay Source Formatter] dependency to version
1.0.645.
- [LPS-81106]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.37.
- [LPS-81106]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.220.

## 3.12.22 - 2018-05-29

### Changed
- [LPS-68101]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.38.
- [LPS-68101]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.221.
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.173.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.646.

## 3.12.23 - 2018-05-30

### Changed
- [LPS-81555]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.174.
- [LPS-81555]: Update the [Liferay Source Formatter] dependency to version
1.0.647.

## 3.12.24 - 2018-05-31

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.175.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.648.

## 3.12.25 - 2018-05-31

### Changed
- [LPS-81795]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.176.
- [LPS-81795]: Update the [Liferay Source Formatter] dependency to version
1.0.649.

## 3.12.26 - 2018-06-01

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.177.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.650.

## 3.12.27 - 2018-06-04

### Changed
- [LPS-81795]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.178.
- [LPS-81795]: Update the [Liferay Source Formatter] dependency to version
1.0.651.

## 3.12.28 - 2018-06-04

### Changed
- [LPS-79919]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.179.
- [LPS-79919]: Update the [Liferay Source Formatter] dependency to version
1.0.652.
- [LPS-81336]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.39.
- [LPS-81336]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.222.

## 3.12.29 - 2018-06-05

### Changed
- [LPS-81336]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.40.
- [LPS-81336]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.223.

## 3.12.30 - 2018-06-05

### Changed
- [LPS-82001]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.180.
- [LPS-82001]: Update the [Liferay Source Formatter] dependency to version
1.0.653.

### Fixed
- [LPS-75530]: Fix the log message for `ExecuteBndTask` so it displays the
file's correct build time.

## 3.12.31 - 2018-06-06

### Changed
- [LPS-81944]: Update the [Liferay Portal Tools Upgrade Table Builder]
dependency to version 1.0.9.
- [LPS-82001]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.181.
- [LPS-82001]: Update the [Liferay Source Formatter] dependency to version
1.0.654.

## 3.12.32 - 2018-06-07

### Changed
- [LPS-78940]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.41.
- [LPS-78940]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.224.
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.182.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.655.
- [LPS-81900]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.5.

## 3.12.33 - 2018-06-08

### Changed
- [LPS-72445]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.42.
- [LPS-72445]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.225.
- [LPS-82130]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.31.
- [LPS-82130]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.32.
- [LPS-82130]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.11.

## 3.12.34 - 2018-06-11

### Changed
- [LPS-80927]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.43.
- [LPS-80927]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.226.
- [LPS-82128]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.183.
- [LPS-82128]: Update the [Liferay Source Formatter] dependency to version
1.0.656.
- [LPS-82209]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 2.2.2.
- [LPS-82209]: Update the [Liferay Lang Builder] dependency to version 1.0.25.

## 3.12.35 - 2018-06-11

### Changed
- [LPS-81638]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.6.

## 3.12.36 - 2018-06-11

### Changed
- [LPS-82121]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.184.
- [LPS-82121]: Update the [Liferay Source Formatter] dependency to version
1.0.657.

## 3.12.37 - 2018-06-11

### Changed
- [LPS-77875]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.185.
- [LPS-77875]: Update the [Liferay Source Formatter] dependency to version
1.0.658.

## 3.12.38 - 2018-06-11

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.186.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.659.
- [LPS-82261]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.44.
- [LPS-82261]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.227.

## 3.12.39 - 2018-06-12

### Changed
- [LPS-82261]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.45.
- [LPS-82261]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.228.

## 3.12.40 - 2018-06-13

### Changed
- [LPS-82343]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.187.
- [LPS-82343]: Update the [Liferay Source Formatter] dependency to version
1.0.660.

## 3.12.41 - 2018-06-13

### Removed
- [LPS-77425]: Remove version difference for
[Liferay Portal Tools Service Builder] based on the portal version.

## 3.12.42 - 2018-06-13

*No changes.*

## 3.12.43 - 2018-06-13

### Fixed
- [LPS-77425]: Fix error when getting a tool version for an unknown portal
version.

## 3.12.44 - 2018-06-13

### Fixed
- [LPS-77425]: Fix error when configuring the `liferay` extension object for an
unknown portal version.

## 3.12.45 - 2018-06-14

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.188.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.661.

## 3.12.46 - 2018-06-15

### Changed
- [LPS-82469]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.189.
- [LPS-82469]: Update the [Liferay Source Formatter] dependency to version
1.0.662.
- [LPS-82534]: Update the [Liferay Ant BND] dependency to version 2.0.51.

## 3.12.47 - 2018-06-15

### Changed
- [LPS-77143]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.190.
- [LPS-77143]: Update the [Liferay Source Formatter] dependency to version
1.0.663.

## 3.12.48 - 2018-06-18

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.191.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.664.

## 3.12.49 - 2018-06-19

### Changed
- [LPS-82420]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.192.
- [LPS-82420]: Update the [Liferay Source Formatter] dependency to version
1.0.665.

## 3.12.50 - 2018-06-20

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.193.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.666.

## 3.12.51 - 2018-06-20

### Changed
- [LPS-82433]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.46.
- [LPS-82433]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.229.

## 3.12.52 - 2018-06-20

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.194.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.667.

## 3.12.53 - 2018-06-21

### Changed
- [LPS-76509]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.47.
- [LPS-76509]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.230.
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.195.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.668.

## 3.12.54 - 2018-06-22

### Changed
- [LPS-82568]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.32.
- [LPS-82568]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.33.
- [LPS-82568]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.12.
- [LPS-82815]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.48.
- [LPS-82815]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.231.

## 3.12.55 - 2018-06-25

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.49.
- [LPS-82828]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.232.

## 3.12.56 - 2018-06-25

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.196.
- [LPS-82828]: Update the [Liferay Source Formatter] dependency to version
1.0.669.

## 3.12.57 - 2018-06-25

### Changed
- [LPS-74608]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.50.
- [LPS-74608]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.233.

## 3.12.58 - 2018-06-26

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.197.
- [LPS-82828]: Update the [Liferay Source Formatter] dependency to version
1.0.670.
- [LPS-82884]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.27.

## 3.12.59 - 2018-06-27

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.198.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.671.

## 3.12.60 - 2018-06-28

### Changed
- [LPS-82343]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.0.

## 3.12.61 - 2018-06-28

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.199.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.672.

## 3.12.62 - 2018-06-29

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.200.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.673.
- [LPS-83067]: Update the [Liferay Ant BND] dependency to version 2.0.52.

## 3.12.63 - 2018-07-02

### Changed
- [LPS-79679]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.201.
- [LPS-79679]: Update the [Liferay Source Formatter] dependency to version
1.0.674.
- [LPS-82828]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.51.
- [LPS-82828]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.234.

## 3.12.64 - 2018-07-02

### Changed
- [LPS-83067]: Update the [Liferay Ant BND] dependency to version 2.0.53.

## 3.12.65 - 2018-07-03

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.202.
- [LPS-82828]: Update the [Liferay Source Formatter] dependency to version
1.0.675.

## 3.12.66 - 2018-07-04

### Changed
- [LPS-82828]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.21.
- [LPS-82828]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.28.

## 3.12.67 - 2018-07-05

### Changed
- [LPS-82343]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.1.
- [LPS-82343]: Update the [Liferay Lang Builder] dependency to version 1.0.26.
- [LPS-83220]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.22.
- [LPS-83220]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.29.

## 3.12.68 - 2018-07-10

*No changes.*

## 3.12.69 - 2018-07-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.203.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.676.

## 3.12.70 - 2018-07-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.204.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.677.

## 3.12.71 - 2018-07-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.205.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.678.

## 3.12.72 - 2018-07-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.206.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.679.

## 3.12.73 - 2018-07-13

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.207.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.680.

## 3.12.74 - 2018-07-14

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.208.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.681.

## 3.12.75 - 2018-07-16

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.209.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.682.

## 3.12.76 - 2018-07-16

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.210.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.683.

## 3.12.77 - 2018-07-16

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.211.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.684.

## 3.12.78 - 2018-07-17

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.212.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.685.

## 3.12.79 - 2018-07-17

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.213.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.686.

## 3.12.80 - 2018-07-17

### Changed
- [LPS-83520]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.3.0.

## 3.12.81 - 2018-07-18

### Changed
- [LPS-83576]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.214.
- [LPS-83576]: Update the [Liferay Source Formatter] dependency to version
1.0.687.

## 3.12.82 - 2018-07-18

### Changed
- [LPS-83483]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.52.
- [LPS-83483]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.235.

## 3.12.83 - 2018-07-18

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.215.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.688.

## 3.12.84 - 2018-07-19

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.216.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.689.

## 3.12.85 - 2018-07-24

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.217.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.690.

## 3.12.86 - 2018-07-24

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.218.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.691.

## 3.12.87 - 2018-07-25

### Changed
- [LPS-83761]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.53.
- [LPS-83761]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.236.

## 3.12.88 - 2018-07-25

### Changed
- [LPS-83705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.219.
- [LPS-83705]: Update the [Liferay Source Formatter] dependency to version
1.0.692.

## 3.12.89 - 2018-07-27

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.220.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.693.

## 3.12.90 - 2018-07-27

### Changed
- [LPS-78938]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.54.
- [LPS-78938]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.237.

## 3.12.91 - 2018-07-30

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.221.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.694.

## 3.12.92 - 2018-07-31

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.222.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.695.

## 3.12.93 - 2018-08-01

### Changed
- [LPS-84039]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.223.
- [LPS-84039]: Update the [Liferay Source Formatter] dependency to version
1.0.696.

## 3.12.94 - 2018-08-01

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.224.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.697.

## 3.12.95 - 2018-08-01

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.225.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.698.

## 3.12.96 - 2018-08-02

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.226.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.699.

## 3.12.97- 2018-08-01

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.227.
- [LPS-76475]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.1.

## 3.12.98 - 2018-08-05

### Changed
- [LPS-83705]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.227.
- [LPS-83705]: Update the [Liferay Source Formatter] dependency to version
1.0.701.

## 3.12.99 - 2018-08-06

### Changed
- [LPS-78033]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.228.
- [LPS-78033]: Update the [Liferay Source Formatter] dependency to version
1.0.702.

## 3.12.100 - 2018-08-06

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.229.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.703.

## 3.12.101 - 2018-08-06

### Changed
- [LPS-84213]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.230.
- [LPS-84213]: Update the [Liferay Source Formatter] dependency to version
1.0.704.

## 3.12.102 - 2018-08-06

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.231.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.705.

## 3.12.103 - 2018-08-07

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.232.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.706.

## 3.12.104 - 2018-08-08

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.233.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.707.

## 3.12.105 - 2018-08-09

### Changed
- [LPS-84307]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.234.
- [LPS-84307]: Update the [Liferay Source Formatter] dependency to version
1.0.708.

## 3.12.106 - 2018-08-10

### Changed
- [LPS-84039]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.235.
- [LPS-84039]: Update the [Liferay Source Formatter] dependency to version
1.0.709.

## 3.12.107 - 2018-08-13

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.236.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.710.

## 3.12.108 - 2018-08-15

### Changed
- [LPS-84039]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.237.
- [LPS-84039]: Update the [Liferay Source Formatter] dependency to version
1.0.711.

## 3.12.109 - 2018-08-15

### Changed
- [LPS-84473]: Update the [Liferay CSS Builder] dependency to version 2.1.2.
- [LPS-84473]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.2.

## 3.12.110 - 2018-08-15

### Changed
- [LPS-83790]: Update the `liferay.appServers.tomcat.version` property's default
value to `9.0.10`.
- [LPS-83790]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.3.1.

## 3.12.111 - 2018-08-21

### Changed
- [LPS-84615]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.55.
- [LPS-84615]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.238.

## 3.12.112 - 2018-08-22

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.238.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.712.
- [LPS-84218]: Update the [Liferay CSS Builder] dependency to version 2.1.3.
- [LPS-84218]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.3.

## 3.12.113 - 2018-08-23

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.239.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.713.

## 3.12.114 - 2018-08-23

### Changed
- [LPS-83067]: Update the [Liferay Ant BND] dependency to version 2.0.54.

## 3.12.115 - 2018-08-27

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.240.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.714.

## 3.12.116 - 2018-08-27

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.241.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.715.

## 3.12.117 - 2018-08-28

### Changed
- [LPS-84094]: Allow properties defined in a `gradle.properties` file to be
overridden by values defined in a `gradle-ext.properties` file.
- [LPS-84094]: Update the [Liferay Gradle Util] dependency to version 1.0.30.

## 3.12.118 - 2018-08-29

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.242.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.716.

## 3.12.119 - 2018-08-30

### Changed
- [LPS-84094]: Update the [Liferay Gradle Util] dependency to version 1.0.31.
- [LPS-84621]: Update the [Liferay Gradle Plugins TLDDoc Builder] dependency
to version 1.3.1.
- [LPS-84756]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.243.
- [LPS-84756]: Update the [Liferay Source Formatter] dependency to version
1.0.717.
- [LPS-84891]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.56.
- [LPS-84891]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.239.

## 3.12.120 - 2018-08-30

### Changed
- [LPS-80388]: Update the [Liferay Ant BND] dependency to version 2.0.55.

## 3.12.121 - 2018-09-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.244.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.718.

## 3.12.122 - 2018-09-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.245.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.719.

## 3.12.123 - 2018-09-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.246.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.720.

## 3.12.124 - 2018-09-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.247.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.721.

## 3.12.125 - 2018-09-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.248.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.722.

## 3.12.126 - 2018-09-04

### Changed
- [LPS-85092]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.2.
- [LPS-85092]: Update the [Liferay Lang Builder] dependency to version 1.0.27.

## 3.12.127 - 2018-09-05

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.249.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.723.

## 3.12.128 - 2018-09-06

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.250.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.724.

## 3.12.129 - 2018-09-10

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.251.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.725.

## 3.12.130 - 2018-09-10

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.252.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.726.

## 3.12.131 - 2018-09-11

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.253.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.727.

## 3.12.132 - 2018-09-11

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.254.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.728.

## 3.12.133 - 2018-09-11

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.255.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.729.

## 3.12.134 - 2018-09-12

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.256.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.730.

## 3.12.135 - 2018-09-13

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.258.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.731.

## 3.12.136 - 2018-09-17

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.259.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.732.

## 3.12.137 - 2018-09-17

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.260.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.733.

## 3.12.138 - 2018-09-18

### Changed
- [LPS-85035]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.261.
- [LPS-85035]: Update the [Liferay Source Formatter] dependency to version
1.0.734.

## 3.12.139 - 2018-09-18

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.262.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.735.

## 3.12.140 - 2018-09-18

### Changed
- [LPS-85296]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.263.
- [LPS-85296]: Update the [Liferay Source Formatter] dependency to version
1.0.736.

## 3.12.141 - 2018-09-19

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.264.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.737.

## 3.12.142 - 2018-09-20

### Changed
- [LPS-71117]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.265.
- [LPS-71117]: Update the [Liferay Source Formatter] dependency to version
1.0.738.

## 3.12.143 - 2018-09-24

### Changed
- [LPS-85609]: Remove deprecated API calls from IDE tasks.

## 3.12.144 - 2018-09-24

### Changed
- [LPS-85678]: Update the [Liferay Ant BND] dependency to version 2.0.56.

## 3.12.145 - 2018-09-24

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.266.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.739.

## 3.12.146 - 2018-09-25

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.267.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.740.

## 3.12.147 - 2018-09-25

### Changed
- [LPS-85556]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.57.
- [LPS-85556]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.240.

## 3.12.148 - 2018-09-25

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.268.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.741.

## 3.12.149 - 2018-09-30

### Changed
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.742.

## 3.12.150 - 2018-10-01

### Changed
- [LPS-84138]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.58.
- [LPS-84138]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.241.

## 3.12.151 - 2018-10-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.269.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.743.
- [LPS-85959]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.33.
- [LPS-85959]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.34.
- [LPS-85959]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.13.

## 3.12.152 - 2018-10-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.270.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.744.

## 3.12.153 - 2018-10-04

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.271.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.745.

## 3.12.154 - 2018-10-05

### Changed
- [LPS-80388]: Update the [Liferay Ant BND] dependency to version 2.0.57.

## 3.12.155 - 2018-10-07

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.272.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.746.

## 3.12.156 - 2018-10-08

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.273.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.747.

## 3.12.157 - 2018-10-09

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.274.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.748.

## 3.12.158 - 2018-10-09

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.275.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.749.

## 3.12.159 - 2018-10-09

### Changed
- [LPS-85959]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.34.
- [LPS-85959]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.35.
- [LPS-85959]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.14.

## 3.12.160 - 2018-10-10

### Changed
- [LPS-86308]: Update the [Liferay Alloy Taglib] dependency to version 1.1.13.

## 3.12.161 - 2018-10-12

### Changed
- [LPS-86371]: Set the `npmInstall` task's up-to-date check to `false` if the
`node_modules` directory does not exist.

## 3.12.162 - 2018-10-15

### Changed
- [LPS-85954]: Set the `compileJSP` task's `destinationDir` to the
`Liferay Home` module Jasper work directory so it contains the generated Java
files.

## 3.12.163 - 2018-10-15

### Changed
- [LPS-86362]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.276.
- [LPS-86362]: Update the [Liferay Source Formatter] dependency to version
1.0.750.

## 3.12.164 - 2018-10-15

### Changed
- [LPS-86408]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.277.
- [LPS-86408]: Update the [Liferay Source Formatter] dependency to version
1.0.751.

## 3.12.165 - 2018-10-16

### Changed
- [LPS-85849]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.59.
- [LPS-85849]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.242.

## 3.12.166 - 2018-10-16

### Changed
- [LPS-85556]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.60.
- [LPS-85556]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.243.

## 3.12.167 - 2018-10-16

### Changed
- [LPS-85678]: Update the [Liferay Ant BND] dependency to version 2.0.58.

## 3.12.168 - 2018-10-16

### Changed
- [LPS-85678]: Update the [Liferay Ant BND] dependency to version 2.0.59.

## 3.12.169 - 2018-10-17

### Changed
- [LPS-86447]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.4.0.

## 3.13.0 - 2018-10-17

### Changed
- [LPS-86018]: Enable the `watch` task for theme and WAR projects.

## 3.13.1 - 2018-10-17

### Changed
- [LPS-86413]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.278.
- [LPS-86413]: Update the [Liferay Source Formatter] dependency to version
1.0.752.

## 3.13.2 - 2018-10-18

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.279.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.753.

## 3.13.3 - 2018-10-18

### Changed
- [LPS-86493]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.280.
- [LPS-86493]: Update the [Liferay Source Formatter] dependency to version
1.0.754.

## 3.13.4 - 2018-10-18

### Changed
- [LPS-85556]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.61.
- [LPS-85556]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.244.

## 3.13.5 - 2018-10-22

### Changed
- [LPS-86576]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.35.
- [LPS-86576]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.36.
- [LPS-86576]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.15.

## 3.13.6 - 2018-10-22

### Changed
- [LPS-86581]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.4.

## 3.13.7 - 2018-10-22

### Changed
- [LPS-86556]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.281.
- [LPS-86556]: Update the [Liferay Source Formatter] dependency to version
1.0.755.

## 3.13.8 - 2018-10-29

### Changed
- [LPS-86549]: Use *update* rather than *refresh* strategy when watching OSGi
changes.

## 3.13.9 - 2018-11-02

### Changed
- [LPS-86835]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.62.
- [LPS-86835]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.245.

## 3.13.10 - 2018-11-08

### Changed
- [LPS-86916]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.63.
- [LPS-86916]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.246.

## 3.13.11 - 2018-11-08

*No changes.*

## 3.13.12 - 2018-11-13

### Changed
- [LPS-87293]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.282.
- [LPS-87293]: Update the [Liferay Source Formatter] dependency to version
1.0.756.

## 3.13.13 - 2018-11-15

### Fixed
- [LPS-87366]: The Eclipse project name changed after upgrading Gradle to
4.10.2. Set the Eclipse project name with the Gradle project name.

## 3.13.14 - 2018-11-16

### Changed
- [LPS-87466]: Update the [Liferay Gradle Util] dependency to version 1.0.32.

## 3.13.15 - 2018-11-16

### Changed
- [LPS-86916]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.64.
- [LPS-86916]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.247.
- [LPS-87465]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.36.
- [LPS-87465]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.37.
- [LPS-87465]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.16.
- [LPS-87466]: Update the [Liferay Gradle Plugins Alloy Taglib] dependency to
version 2.0.1.
- [LPS-87466]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.5.
- [LPS-87466]: Update the [Liferay Gradle Plugins DB Support] dependency to
version 1.0.4.
- [LPS-87466]: Update the [Liferay Gradle Plugins Jasper JSPC] dependency to
version 2.0.4.
- [LPS-87466]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.23.
- [LPS-87466]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.3.
- [LPS-87466]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.65.
- [LPS-87466]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.283.
- [LPS-87466]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.7.
- [LPS-87466]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.4.1.
- [LPS-87466]: Update the [Liferay Gradle Plugins TLD Formatter] dependency to
version 1.0.8.
- [LPS-87466]: Update the [Liferay Gradle Plugins TLDDoc Builder] dependency
to version 1.3.2.
- [LPS-87466]: Update the [Liferay Gradle Plugins Upgrade Table Builder]
dependency to version 2.0.2.
- [LPS-87466]: Update the [Liferay Gradle Plugins WSDD Builder] dependency to
version 1.0.12.
- [LPS-87466]: Update the [Liferay Gradle Plugins WSDL Builder] dependency to
version 2.0.2.
- [LPS-87466]: Update the [Liferay Gradle Plugins XML Formatter] dependency tos
version 1.0.10.
- [LPS-87466]: Update the [Liferay Gradle Plugins XSD Builder] dependency tos
version 1.0.6.

## 3.13.16 - 2018-11-19

### Changed
- [LPS-87466]: Update the [Liferay Gradle Util] dependency to version 1.0.33.

### Fixed
- [LPS-85954]: Set the `compileJSP.destinationDir` property back to its default
value of `${project.buildDir}/jspc`. Copy the generated JSP Java files into the
`Liferay Home` work directory.

## 3.13.17 - 2018-11-19

### Changed
- [LPS-87466]: Update the [Liferay Gradle Plugins Alloy Taglib] dependency to
version 2.0.2.
- [LPS-87466]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.2.6.
- [LPS-87466]: Update the [Liferay Gradle Plugins DB Support] dependency to
version 1.0.5.
- [LPS-87466]: Update the [Liferay Gradle Plugins Jasper JSPC] dependency to
version 2.0.5.
- [LPS-87466]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.24.
- [LPS-87466]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.4.
- [LPS-87466]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.1.66.
- [LPS-87466]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.284.
- [LPS-87466]: Update the [Liferay Gradle Plugins Soy] dependency to version
3.1.8.
- [LPS-87466]: Update the [Liferay Gradle Plugins Test Integration] dependency
to version 2.4.2.
- [LPS-87466]: Update the [Liferay Gradle Plugins TLD Formatter] dependency to
version 1.0.9.
- [LPS-87466]: Update the [Liferay Gradle Plugins TLDDoc Builder] dependency
to version 1.3.3.
- [LPS-87466]: Update the [Liferay Gradle Plugins Upgrade Table Builder]
dependency to version 2.0.3.
- [LPS-87466]: Update the [Liferay Gradle Plugins WSDD Builder] dependency to
version 1.0.13.
- [LPS-87466]: Update the [Liferay Gradle Plugins WSDL Builder] dependency to
version 2.0.3.
- [LPS-87466]: Update the [Liferay Gradle Plugins XML Formatter] dependency tos
version 1.0.11.
- [LPS-87466]: Update the [Liferay Gradle Plugins XSD Builder] dependency tos
version 1.0.7.
- [LPS-87503]: Update the [Liferay Ant BND] dependency to version 3.0.0.
- [LPS-87503]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.285.
- [LPS-87503]: Update the [Liferay Source Formatter] dependency to version
1.0.757.

## 3.13.18 - 2018-11-20

*No changes.*

## 3.13.19 - 2018-11-20

### Changed
- [LPS-86806]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.286.
- [LPS-86806]: Update the [Liferay Source Formatter] dependency to version
1.0.758.

## 3.13.20 - 2018-11-22

### Changed
- [LPS-87776]: Update the [Liferay Ant BND] dependency to version 3.0.1.

## 3.13.21 - 2018-11-26

### Changed
- [LPS-87776]: Update the [Liferay Ant BND] dependency to version 3.0.2.

## 3.13.22 - 2018-11-27

### Changed
- [LPS-86406]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.0.
- [LPS-86406]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.248.

## 3.13.23 - 2018-11-27

### Changed
- [LPS-87839]: Update the [Liferay Ant BND] dependency to version 3.0.3.

## 3.13.24 - 2018-11-28

### Changed
- [LPS-86806]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.287.
- [LPS-86806]: Update the [Liferay Source Formatter] dependency to version
1.0.759.

## 3.13.25 - 2018-11-29

### Changed
- [LPS-87936]: Update the [Liferay Gradle Plugins Javadoc Formatter] dependency
to version 1.0.25.
- [LPS-87936]: Update the [Liferay Javadoc Formatter] dependency to version
1.0.30.

## 3.13.26 - 2018-11-30

### Fixed
- [LPS-87978]: Quote URL arguments in Gogo shell commands.

## 3.13.27 - 2018-12-03

### Changed
- [LPS-86406]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.1.
- [LPS-86406]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.249.

## 3.13.28 - 2018-12-03

### Changed
- [LPS-85828]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.288.
- [LPS-85828]: Update the [Liferay Source Formatter] dependency to version
1.0.760.

## 3.13.29 - 2018-12-03

### Changed
- [LPS-66010]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.2.
- [LPS-66010]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.250.

## 3.13.30 - 2018-12-04

### Changed
- [LPS-88171]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.289.
- [LPS-88171]: Update the [Liferay Source Formatter] dependency to version
1.0.761.

## 3.13.31 - 2018-12-04

### Changed
- [LPS-87471]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.290.
- [LPS-87471]: Update the [Liferay Source Formatter] dependency to version
1.0.762.

## 3.13.32 - 2018-12-05

### Changed
- [LPS-88186]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.291.
- [LPS-88186]: Update the [Liferay Source Formatter] dependency to version
1.0.763.

## 3.13.33 - 2018-12-05

### Changed
- [LPS-88223]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.292.
- [LPS-88223]: Update the [Liferay Source Formatter] dependency to version
1.0.764.

## 3.13.34 - 2018-12-06

### Changed
- [LPS-88186]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.293.
- [LPS-88186]: Update the [Liferay Source Formatter] dependency to version
1.0.765.

## 3.13.35 - 2018-12-07

### Changed
- [LPS-86806]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.294.
- [LPS-86806]: Update the [Liferay Source Formatter] dependency to version
1.0.766.

## 3.13.36 - 2018-12-10

### Changed
- [LPS-81706]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.3.
- [LPS-81706]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.251.

## 3.13.37 - 2018-12-10

### Changed
- [LPS-88171]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.295.
- [LPS-88171]: Update the [Liferay Source Formatter] dependency to version
1.0.767.

## 3.13.38 - 2018-12-11

### Changed
- [LPS-88183]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.296.
- [LPS-88183]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.252.

## 3.13.39 - 2018-12-13

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.297.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.768.

## 3.13.40 - 2018-12-14

### Fixed
- [LPS-87488]: Fix the `deploy` task for the `com.liferay.ext.plugin` plugin.

## 3.13.41 - 2018-12-14

### Changed
- [LPS-88181]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.4.
- [LPS-88181]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.253.

## 3.13.42 - 2018-12-17

### Changed
- [LPS-87590]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.5.
- [LPS-87590]: Update the [Liferay Lang Builder] dependency to version 1.0.28.

## 3.13.43 - 2018-12-19

### Fixed
- [LPS-88552]: Lazily evaluate the `cleanDeployed` extension object property to
fix the `update-gradle-cache` Ant task.

## 3.13.44 - 2018-12-19

### Changed
- [LPS-88382]: Update the [Liferay Ant BND] dependency to version 3.0.4.

## 3.13.45 - 2018-12-20

### Changed
- [LPS-88382]: Update the [Liferay Ant BND] dependency to version 3.0.3.

## 3.13.46 - 2018-12-20

### Changed
- [LPS-88382]: Update the [Liferay Ant BND] dependency to version 3.0.5.

## 3.13.47 - 2018-12-20

### Changed
- [LPS-88170]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.5.
- [LPS-88170]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.254.

## 3.13.48 - 2019-01-02

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.298.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.769.

## 3.13.49 - 2019-01-03

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.299.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.770.

## 3.13.50 - 2019-01-05

### Changed
- [LPS-41848]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.6.
- [LPS-41848]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.255.

## 3.13.51 - 2019-01-07

### Removed
- [LPS-87479]: The `npmInstall` task's up-to-date check is no longer needed. The
task's inputs have been updated to improve performance.

## 3.13.52 - 2019-01-08

### Changed
- [LPS-88823]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.7.
- [LPS-88823]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.256.

## 3.13.53 - 2019-01-08

### Changed
- [LPS-88903]: Update the [Liferay Ant BND] dependency to version 3.0.6.

## 3.13.54 - 2019-01-08

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.300.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.771.

## 3.13.55 - 2019-01-09

### Changed
- [LPS-88909]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.38.
- [LPS-88909]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.39.
- [LPS-88909]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.18.

## 3.13.56 - 2019-01-09

### Changed
- [LPS-87479]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.39.
- [LPS-87479]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.40.
- [LPS-87479]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.19.

## 3.13.57 - 2019-01-11

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.301.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.772.

## 3.13.58 - 2019-01-13

### Changed
- [LPS-86806]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.302.
- [LPS-86806]: Update the [Liferay Source Formatter] dependency to version
1.0.773.

## 3.13.59 - 2019-01-14

### Changed
- [LPS-89126]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.40.
- [LPS-89126]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.41.
- [LPS-89126]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.20.

## 3.13.60 - 2019-01-16

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.303.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.774.

## 3.13.61 - 2019-01-16

### Changed
- [LPS-88909]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.41.
- [LPS-88909]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.42.
- [LPS-88909]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.21.

## 3.13.62 - 2019-01-16

### Changed
- [LPS-89228]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.8.
- [LPS-89228]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.257.

## 3.13.63 - 2019-01-17

### Changed
- [LPS-84119]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.304.
- [LPS-84119]: Update the [Liferay Source Formatter] dependency to version
1.0.775.

## 3.13.64 - 2019-01-22

### Added
- [LPS-89415]: Set the [Liferay Gradle Plugins REST Builder] dependency to
version 1.0.0.
- [LPS-89415]: Set the [Liferay Portal Tools REST Builder] dependency to version
1.0.0.

## 3.13.65 - 2019-01-22

### Changed
- [LPS-89388]: Update the [Liferay Gradle Plugins Lang Builder] dependency to
version 3.0.6.
- [LPS-89388]: Update the [Liferay Lang Builder] dependency to version 1.0.29.

## 3.13.66 - 2019-01-22

### Changed
- [LPS-86806]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.305.
- [LPS-86806]: Update the [Liferay Source Formatter] dependency to version
1.0.776.

## 3.13.67 - 2019-01-22

### Changed
- [LPS-89415]: Set the [Liferay Gradle Plugins REST Builder] dependency to
version 1.0.1.
- [LPS-89415]: Set the [Liferay Portal Tools REST Builder] dependency to version
1.0.1.

## 3.13.68 - 2019-01-23

### Changed
- [LPS-89457]: Update the [Liferay Gradle Plugins Service Builder] dependency to
version 2.2.9.
- [LPS-89457]: Update the [Liferay Portal Tools Service Builder] dependency to
version 1.0.258.

## 3.13.69 - 2019-01-23

### Changed
- [LPS-74544]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.306.
- [LPS-74544]: Update the [Liferay Source Formatter] dependency to version
1.0.777.

## 3.13.70 - 2019-01-24

### Changed
- [LPS-89369]: Update the [Liferay Gradle Plugins Gulp] dependency to version
2.0.43.
- [LPS-89369]: Update the [Liferay Gradle Plugins JS Module Config Generator]
dependency to version 2.1.43.
- [LPS-89369]: Update the [Liferay Gradle Plugins JS Transpiler] dependency to
version 2.4.22.

## 3.13.71 - 2019-01-24

### Changed
- [LPS-89415]: Set the [Liferay Gradle Plugins REST Builder] dependency to
version 1.0.2.
- [LPS-89415]: Set the [Liferay Portal Tools REST Builder] dependency to version
1.0.2.

## 4.4.84 - 2019-11-19

### Changed
- [LPS-104435]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 2.3.469.
- [LPS-104435]: Update the [Liferay Source Formatter] dependency to version
1.0.941.

[Gradle Bundle Plugin]: https://github.com/TomDmitriev/gradle-bundle-plugin
[Liferay Alloy Taglib]: https://github.com/liferay/alloy-taglibs
[Liferay Ant BND]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/ant-bnd
[Liferay CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/css-builder
[Liferay Gogo Shell Client]: https://github.com/liferay/liferay-portal/tree/master/modules/util/gogo-shell-client
[Liferay Gradle Plugins Alloy Taglib]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-alloy-taglib
[Liferay Gradle Plugins CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-css-builder
[Liferay Gradle Plugins DB Support]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-db-support
[Liferay Gradle Plugins Gulp]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-gulp
[Liferay Gradle Plugins JS Module Config Generator]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-module-config-generator
[Liferay Gradle Plugins JS Transpiler]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-transpiler
[Liferay Gradle Plugins Jasper JSPC]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-jasper-jspc
[Liferay Gradle Plugins Javadoc Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-javadoc-formatter
[Liferay Gradle Plugins Lang Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-lang-builder
[Liferay Gradle Plugins REST Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-rest-builder
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
[Liferay Gradle Plugins XSD Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-xsd-builder
[Liferay Gradle Util]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-util
[Liferay Jasper JSPC]: https://github.com/liferay/liferay-portal/tree/master/modules/util/jasper-jspc
[Liferay Javadoc Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/javadoc-formatter
[Liferay Lang Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/lang-builder
[Liferay Portal Tools DB Support]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-db-support
[Liferay Portal Tools REST Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-rest-builder
[Liferay Portal Tools Service Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-service-builder
[Liferay Portal Tools Upgrade Table Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-upgrade-table-builder
[Liferay Portal Tools WSDD Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-wsdd-builder
[Liferay Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/source-formatter
[Liferay TLD Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/tld-formatter
[Liferay Whip]: https://github.com/liferay/liferay-portal/tree/master/modules/test/whip
[Liferay XML Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/xml-formatter
[LPS-41848]: https://issues.liferay.com/browse/LPS-41848
[LPS-52675]: https://issues.liferay.com/browse/LPS-52675
[LPS-53392]: https://issues.liferay.com/browse/LPS-53392
[LPS-58672]: https://issues.liferay.com/browse/LPS-58672
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-62970]: https://issues.liferay.com/browse/LPS-62970
[LPS-64098]: https://issues.liferay.com/browse/LPS-64098
[LPS-65930]: https://issues.liferay.com/browse/LPS-65930
[LPS-66010]: https://issues.liferay.com/browse/LPS-66010
[LPS-66222]: https://issues.liferay.com/browse/LPS-66222
[LPS-66396]: https://issues.liferay.com/browse/LPS-66396
[LPS-66797]: https://issues.liferay.com/browse/LPS-66797
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
[LPS-68101]: https://issues.liferay.com/browse/LPS-68101
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
[LPS-69247]: https://issues.liferay.com/browse/LPS-69247
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
[LPS-72445]: https://issues.liferay.com/browse/LPS-72445
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
[LPS-74904]: https://issues.liferay.com/browse/LPS-74904
[LPS-74933]: https://issues.liferay.com/browse/LPS-74933
[LPS-75009]: https://issues.liferay.com/browse/LPS-75009
[LPS-75010]: https://issues.liferay.com/browse/LPS-75010
[LPS-75047]: https://issues.liferay.com/browse/LPS-75047
[LPS-75049]: https://issues.liferay.com/browse/LPS-75049
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
[LPS-75530]: https://issues.liferay.com/browse/LPS-75530
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
[LPS-76509]: https://issues.liferay.com/browse/LPS-76509
[LPS-76601]: https://issues.liferay.com/browse/LPS-76601
[LPS-76626]: https://issues.liferay.com/browse/LPS-76626
[LPS-76644]: https://issues.liferay.com/browse/LPS-76644
[LPS-76747]: https://issues.liferay.com/browse/LPS-76747
[LPS-76840]: https://issues.liferay.com/browse/LPS-76840
[LPS-76854]: https://issues.liferay.com/browse/LPS-76854
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
[LPS-77402]: https://issues.liferay.com/browse/LPS-77402
[LPS-77423]: https://issues.liferay.com/browse/LPS-77423
[LPS-77425]: https://issues.liferay.com/browse/LPS-77425
[LPS-77532]: https://issues.liferay.com/browse/LPS-77532
[LPS-77630]: https://issues.liferay.com/browse/LPS-77630
[LPS-77639]: https://issues.liferay.com/browse/LPS-77639
[LPS-77645]: https://issues.liferay.com/browse/LPS-77645
[LPS-77795]: https://issues.liferay.com/browse/LPS-77795
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
[LPS-78150]: https://issues.liferay.com/browse/LPS-78150
[LPS-78231]: https://issues.liferay.com/browse/LPS-78231
[LPS-78261]: https://issues.liferay.com/browse/LPS-78261
[LPS-78269]: https://issues.liferay.com/browse/LPS-78269
[LPS-78308]: https://issues.liferay.com/browse/LPS-78308
[LPS-78312]: https://issues.liferay.com/browse/LPS-78312
[LPS-78436]: https://issues.liferay.com/browse/LPS-78436
[LPS-78459]: https://issues.liferay.com/browse/LPS-78459
[LPS-78477]: https://issues.liferay.com/browse/LPS-78477
[LPS-78493]: https://issues.liferay.com/browse/LPS-78493
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
[LPS-78940]: https://issues.liferay.com/browse/LPS-78940
[LPS-78971]: https://issues.liferay.com/browse/LPS-78971
[LPS-78986]: https://issues.liferay.com/browse/LPS-78986
[LPS-79131]: https://issues.liferay.com/browse/LPS-79131
[LPS-79191]: https://issues.liferay.com/browse/LPS-79191
[LPS-79192]: https://issues.liferay.com/browse/LPS-79192
[LPS-79226]: https://issues.liferay.com/browse/LPS-79226
[LPS-79248]: https://issues.liferay.com/browse/LPS-79248
[LPS-79262]: https://issues.liferay.com/browse/LPS-79262
[LPS-79282]: https://issues.liferay.com/browse/LPS-79282
[LPS-79286]: https://issues.liferay.com/browse/LPS-79286
[LPS-79336]: https://issues.liferay.com/browse/LPS-79336
[LPS-79360]: https://issues.liferay.com/browse/LPS-79360
[LPS-79365]: https://issues.liferay.com/browse/LPS-79365
[LPS-79385]: https://issues.liferay.com/browse/LPS-79385
[LPS-79386]: https://issues.liferay.com/browse/LPS-79386
[LPS-79388]: https://issues.liferay.com/browse/LPS-79388
[LPS-79450]: https://issues.liferay.com/browse/LPS-79450
[LPS-79453]: https://issues.liferay.com/browse/LPS-79453
[LPS-79576]: https://issues.liferay.com/browse/LPS-79576
[LPS-79623]: https://issues.liferay.com/browse/LPS-79623
[LPS-79665]: https://issues.liferay.com/browse/LPS-79665
[LPS-79679]: https://issues.liferay.com/browse/LPS-79679
[LPS-79709]: https://issues.liferay.com/browse/LPS-79709
[LPS-79742]: https://issues.liferay.com/browse/LPS-79742
[LPS-79755]: https://issues.liferay.com/browse/LPS-79755
[LPS-79799]: https://issues.liferay.com/browse/LPS-79799
[LPS-79919]: https://issues.liferay.com/browse/LPS-79919
[LPS-79953]: https://issues.liferay.com/browse/LPS-79953
[LPS-79959]: https://issues.liferay.com/browse/LPS-79959
[LPS-79963]: https://issues.liferay.com/browse/LPS-79963
[LPS-80055]: https://issues.liferay.com/browse/LPS-80055
[LPS-80064]: https://issues.liferay.com/browse/LPS-80064
[LPS-80091]: https://issues.liferay.com/browse/LPS-80091
[LPS-80122]: https://issues.liferay.com/browse/LPS-80122
[LPS-80123]: https://issues.liferay.com/browse/LPS-80123
[LPS-80125]: https://issues.liferay.com/browse/LPS-80125
[LPS-80184]: https://issues.liferay.com/browse/LPS-80184
[LPS-80332]: https://issues.liferay.com/browse/LPS-80332
[LPS-80347]: https://issues.liferay.com/browse/LPS-80347
[LPS-80386]: https://issues.liferay.com/browse/LPS-80386
[LPS-80388]: https://issues.liferay.com/browse/LPS-80388
[LPS-80466]: https://issues.liferay.com/browse/LPS-80466
[LPS-80513]: https://issues.liferay.com/browse/LPS-80513
[LPS-80517]: https://issues.liferay.com/browse/LPS-80517
[LPS-80520]: https://issues.liferay.com/browse/LPS-80520
[LPS-80544]: https://issues.liferay.com/browse/LPS-80544
[LPS-80723]: https://issues.liferay.com/browse/LPS-80723
[LPS-80840]: https://issues.liferay.com/browse/LPS-80840
[LPS-80920]: https://issues.liferay.com/browse/LPS-80920
[LPS-80927]: https://issues.liferay.com/browse/LPS-80927
[LPS-81106]: https://issues.liferay.com/browse/LPS-81106
[LPS-81336]: https://issues.liferay.com/browse/LPS-81336
[LPS-81404]: https://issues.liferay.com/browse/LPS-81404
[LPS-81555]: https://issues.liferay.com/browse/LPS-81555
[LPS-81635]: https://issues.liferay.com/browse/LPS-81635
[LPS-81638]: https://issues.liferay.com/browse/LPS-81638
[LPS-81706]: https://issues.liferay.com/browse/LPS-81706
[LPS-81795]: https://issues.liferay.com/browse/LPS-81795
[LPS-81900]: https://issues.liferay.com/browse/LPS-81900
[LPS-81944]: https://issues.liferay.com/browse/LPS-81944
[LPS-82001]: https://issues.liferay.com/browse/LPS-82001
[LPS-82121]: https://issues.liferay.com/browse/LPS-82121
[LPS-82128]: https://issues.liferay.com/browse/LPS-82128
[LPS-82130]: https://issues.liferay.com/browse/LPS-82130
[LPS-82209]: https://issues.liferay.com/browse/LPS-82209
[LPS-82261]: https://issues.liferay.com/browse/LPS-82261
[LPS-82343]: https://issues.liferay.com/browse/LPS-82343
[LPS-82420]: https://issues.liferay.com/browse/LPS-82420
[LPS-82433]: https://issues.liferay.com/browse/LPS-82433
[LPS-82469]: https://issues.liferay.com/browse/LPS-82469
[LPS-82534]: https://issues.liferay.com/browse/LPS-82534
[LPS-82568]: https://issues.liferay.com/browse/LPS-82568
[LPS-82815]: https://issues.liferay.com/browse/LPS-82815
[LPS-82828]: https://issues.liferay.com/browse/LPS-82828
[LPS-82884]: https://issues.liferay.com/browse/LPS-82884
[LPS-83067]: https://issues.liferay.com/browse/LPS-83067
[LPS-83220]: https://issues.liferay.com/browse/LPS-83220
[LPS-83483]: https://issues.liferay.com/browse/LPS-83483
[LPS-83520]: https://issues.liferay.com/browse/LPS-83520
[LPS-83576]: https://issues.liferay.com/browse/LPS-83576
[LPS-83705]: https://issues.liferay.com/browse/LPS-83705
[LPS-83761]: https://issues.liferay.com/browse/LPS-83761
[LPS-83790]: https://issues.liferay.com/browse/LPS-83790
[LPS-84039]: https://issues.liferay.com/browse/LPS-84039
[LPS-84094]: https://issues.liferay.com/browse/LPS-84094
[LPS-84119]: https://issues.liferay.com/browse/LPS-84119
[LPS-84138]: https://issues.liferay.com/browse/LPS-84138
[LPS-84213]: https://issues.liferay.com/browse/LPS-84213
[LPS-84218]: https://issues.liferay.com/browse/LPS-84218
[LPS-84307]: https://issues.liferay.com/browse/LPS-84307
[LPS-84473]: https://issues.liferay.com/browse/LPS-84473
[LPS-84615]: https://issues.liferay.com/browse/LPS-84615
[LPS-84621]: https://issues.liferay.com/browse/LPS-84621
[LPS-84756]: https://issues.liferay.com/browse/LPS-84756
[LPS-84891]: https://issues.liferay.com/browse/LPS-84891
[LPS-85035]: https://issues.liferay.com/browse/LPS-85035
[LPS-85092]: https://issues.liferay.com/browse/LPS-85092
[LPS-85296]: https://issues.liferay.com/browse/LPS-85296
[LPS-85556]: https://issues.liferay.com/browse/LPS-85556
[LPS-85609]: https://issues.liferay.com/browse/LPS-85609
[LPS-85678]: https://issues.liferay.com/browse/LPS-85678
[LPS-85828]: https://issues.liferay.com/browse/LPS-85828
[LPS-85849]: https://issues.liferay.com/browse/LPS-85849
[LPS-85954]: https://issues.liferay.com/browse/LPS-85954
[LPS-85959]: https://issues.liferay.com/browse/LPS-85959
[LPS-86018]: https://issues.liferay.com/browse/LPS-86018
[LPS-86308]: https://issues.liferay.com/browse/LPS-86308
[LPS-86362]: https://issues.liferay.com/browse/LPS-86362
[LPS-86371]: https://issues.liferay.com/browse/LPS-86371
[LPS-86406]: https://issues.liferay.com/browse/LPS-86406
[LPS-86408]: https://issues.liferay.com/browse/LPS-86408
[LPS-86413]: https://issues.liferay.com/browse/LPS-86413
[LPS-86447]: https://issues.liferay.com/browse/LPS-86447
[LPS-86493]: https://issues.liferay.com/browse/LPS-86493
[LPS-86549]: https://issues.liferay.com/browse/LPS-86549
[LPS-86556]: https://issues.liferay.com/browse/LPS-86556
[LPS-86576]: https://issues.liferay.com/browse/LPS-86576
[LPS-86581]: https://issues.liferay.com/browse/LPS-86581
[LPS-86806]: https://issues.liferay.com/browse/LPS-86806
[LPS-86835]: https://issues.liferay.com/browse/LPS-86835
[LPS-86916]: https://issues.liferay.com/browse/LPS-86916
[LPS-87293]: https://issues.liferay.com/browse/LPS-87293
[LPS-87366]: https://issues.liferay.com/browse/LPS-87366
[LPS-87465]: https://issues.liferay.com/browse/LPS-87465
[LPS-87466]: https://issues.liferay.com/browse/LPS-87466
[LPS-87471]: https://issues.liferay.com/browse/LPS-87471
[LPS-87479]: https://issues.liferay.com/browse/LPS-87479
[LPS-87488]: https://issues.liferay.com/browse/LPS-87488
[LPS-87503]: https://issues.liferay.com/browse/LPS-87503
[LPS-87590]: https://issues.liferay.com/browse/LPS-87590
[LPS-87776]: https://issues.liferay.com/browse/LPS-87776
[LPS-87839]: https://issues.liferay.com/browse/LPS-87839
[LPS-87936]: https://issues.liferay.com/browse/LPS-87936
[LPS-87978]: https://issues.liferay.com/browse/LPS-87978
[LPS-88170]: https://issues.liferay.com/browse/LPS-88170
[LPS-88171]: https://issues.liferay.com/browse/LPS-88171
[LPS-88181]: https://issues.liferay.com/browse/LPS-88181
[LPS-88183]: https://issues.liferay.com/browse/LPS-88183
[LPS-88186]: https://issues.liferay.com/browse/LPS-88186
[LPS-88223]: https://issues.liferay.com/browse/LPS-88223
[LPS-88382]: https://issues.liferay.com/browse/LPS-88382
[LPS-88552]: https://issues.liferay.com/browse/LPS-88552
[LPS-88823]: https://issues.liferay.com/browse/LPS-88823
[LPS-88903]: https://issues.liferay.com/browse/LPS-88903
[LPS-88909]: https://issues.liferay.com/browse/LPS-88909
[LPS-89126]: https://issues.liferay.com/browse/LPS-89126
[LPS-89228]: https://issues.liferay.com/browse/LPS-89228
[LPS-89369]: https://issues.liferay.com/browse/LPS-89369
[LPS-89388]: https://issues.liferay.com/browse/LPS-89388
[LPS-89415]: https://issues.liferay.com/browse/LPS-89415
[LPS-89457]: https://issues.liferay.com/browse/LPS-89457
[LPS-104435]: https://issues.liferay.com/browse/LPS-104435
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023