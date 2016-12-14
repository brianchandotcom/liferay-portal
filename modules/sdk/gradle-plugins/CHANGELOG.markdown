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
- [LPS-69518]: Automatically delete the `liferay/logs` dir generated during the
execution of the `autoUpdateXml` task.

### Changed
- [LPS-66853]: Update the [Liferay Gradle Plugins Source Formatter] dependency
to version 1.0.56.
- [LPS-66853]: Update the [Liferay Source Formatter] dependency to version
1.0.322.

## 3.0.47 - 2016-12-05

### Added
- [LPS-69501]: Allow portal tool versions to be overridden in a
`gradle.properties` file contained in any parent directory of the project. For
example,

		com.liferay.source.formatter.version=1.0.300

## 3.0.48 - 2016-12-08

### Changed
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

[Gradle Bundle Plugin]: https://github.com/TomDmitriev/gradle-bundle-plugin
[Liferay Ant BND]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/ant-bnd
[Liferay CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/css-builder
[Liferay Gradle Plugins CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-css-builder
[Liferay Gradle Plugins Gulp]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-gulp
[Liferay Gradle Plugins JS Module Config Generator]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-module-config-generator
[Liferay Gradle Plugins JS Transpiler]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-js-transpiler
[Liferay Gradle Plugins Javadoc Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-javadoc-formatter
[Liferay Gradle Plugins Service Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-service-builder
[Liferay Gradle Plugins Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-source-formatter
[Liferay Gradle Plugins Test Integration]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-test-integration
[Liferay Gradle Plugins Theme Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-theme-builder
[Liferay Gradle Plugins Upgrade Table Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-upgrade-table-builder
[Liferay Jasper JSPC]: https://github.com/liferay/liferay-portal/tree/master/modules/util/jasper-jspc
[Liferay Javadoc Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/javadoc-formatter
[Liferay Lang Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/lang-builder
[Liferay Portal Tools Service Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-service-builder
[Liferay Portal Tools Upgrade Table Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-upgrade-table-builder
[Liferay Portal Tools WSDD Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/util/portal-tools-wsdd-builder
[Liferay Source Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/source-formatter
[Liferay TLD Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/tld-formatter
[Liferay XML Formatter]: https://github.com/liferay/liferay-portal/tree/master/modules/util/xml-formatter
[LPS-52675]: https://issues.liferay.com/browse/LPS-52675
[LPS-58672]: https://issues.liferay.com/browse/LPS-58672
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-66222]: https://issues.liferay.com/browse/LPS-66222
[LPS-66396]: https://issues.liferay.com/browse/LPS-66396
[LPS-66853]: https://issues.liferay.com/browse/LPS-66853
[LPS-66906]: https://issues.liferay.com/browse/LPS-66906
[LPS-67023]: https://issues.liferay.com/browse/LPS-67023
[LPS-67352]: https://issues.liferay.com/browse/LPS-67352
[LPS-67434]: https://issues.liferay.com/browse/LPS-67434
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
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
[LPS-69677]: https://issues.liferay.com/browse/LPS-69677
[LRDOCS-3023]: https://issues.liferay.com/browse/LRDOCS-3023