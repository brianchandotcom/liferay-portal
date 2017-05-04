# Liferay Gradle Plugins Theme Builder Change Log

## 2.0.0 - 2016-10-11

### Changed
- [LPS-66396]: The `buildTheme` task now writes its output files in a temporary
directory, so the generated files are no longer mixed with source files.
- [LPS-66396]: The `diffsDir` property of the `buildTheme` task now points
directly to `project.webAppDir`, which means that all the files contained in
that directory (by default, `src/main/webapp`) are copied over the parent theme
(if specified) and included in the WAR file.
- [LPS-66396]: The `parentDir` and `unstyledDir` properties of `BuildThemeTask`
now only support directories as input. In order to point these properties to a
file, use the `parentFile` and `unstyledFile` properties, respectively.

### Fixed
- [LPS-66396]: Include only the compiled CSS files, and not SCSS files, into
the WAR file.

### Removed
- [LPS-66396]: Remove the `outputThemeDirs` property of the `BuildThemeTask`.

## 2.0.1 - 2017-01-07

### Changed
- [LPS-69223]: Update the [Liferay Gradle Plugins CSS Builder] dependency to
version 2.0.0.

## 2.0.2 - 2017-05-04

### Added
- [LPS-72367]: Automatically add the latest release of the
[Liferay Frontend Theme Classic] artifact to the `parentThemes` configuration.
- [LPS-72367]: Change the sensible default of the `parentFile` property of the
`buildTheme` task so it is possible to use WAR parent themes from the
`parentThemes` configuration.

[LPS-66396]: https://issues.liferay.com/browse/LPS-66396
[LPS-69223]: https://issues.liferay.com/browse/LPS-69223
[LPS-72367]: https://issues.liferay.com/browse/LPS-72367
[Liferay Frontend Theme Classic]: https://github.com/liferay/liferay-portal/tree/master/modules/apps/foundation/frontend-theme/frontend-theme-classic
[Liferay Gradle Plugins CSS Builder]: https://github.com/liferay/liferay-portal/tree/master/modules/sdk/gradle-plugins-css-builder