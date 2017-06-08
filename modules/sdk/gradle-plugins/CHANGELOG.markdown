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
- [LPS-69488]: Set default Node.js version to 6.6.0.

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
`com.liferay.jasper.jspc` default dependency in the `jspCTool` configuration.
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
- [LPS-72914]: Update the [Liferay Gradle Util] dependency to version 1.0.27.

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
[Liferay Gradle Plugins Test Integration]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-test-integration
[Liferay Gradle Plugins Theme Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-theme-builder
[Liferay Gradle Plugins Upgrade Table Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-upgrade-table-builder
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
[Liferay XML Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/xml-formatter
[LPS-52675]: https://issues.liferay.com/browse/LPS-52675
[LPS-53392]: https://issues.liferay.com/browse/LPS-53392
[LPS-58672]: https://issues.liferay.com/browse/LPS-58672
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-62970]: https://issues.liferay.com/browse/LPS-62970
[LPS-64098]: https://issues.liferay.com/browse/LPS-64098
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
[LPS-72914]: https://issues.liferay.com/browse/LPS-72914
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023