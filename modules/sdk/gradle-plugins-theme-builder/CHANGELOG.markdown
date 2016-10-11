# Liferay Gradle Plugins Theme Builder Change Log

## 2.0.0 - 2016-10-11

### Changed
- [LPS-66396]: The `buildTheme` task now writes its output files in a temporary
directory, so the generated files are no more mixed in with source files.
- [LPS-66396]: The `diffsDir` property of the `buildTheme` task now points
directly to `project.webAppDir`, which means that all the files contained in
that directory (by default: `src/main/webapp`) will be copied over the parent
theme (if specified) and included in the WAR file.
- [LPS-66396]: The `parentDir` and `unstyledDir` properties of `BuildThemeTask`
now only support directories as input. In order to point these properties to a
file, please use `parentFile` and `unstyledFile` respectively.

### Fixed
- [LPS-66396]: Include only the compiled CSS files, and not the SCSS ones, into
the WAR file.

### Removed
- [LPS-66396]: The `outputThemeDirs` property of `BuildThemeTask` is no longer
available.

[LPS-66396]: https://issues.liferay.com/browse/LPS-66396