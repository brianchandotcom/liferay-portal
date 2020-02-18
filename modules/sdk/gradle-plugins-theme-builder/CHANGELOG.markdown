# Liferay Gradle Plugins Theme Builder Change Log

## 2.0.9 - 2019-11-04

### Dependencies
- [LPS-104028]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 3.0.3.

## 2.0.8 - 2019-10-31

### Commits
- [LPS-103169]: lock down test dependencies to css-builder (5d928f994f)

### Dependencies
- [LPS-103169]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 3.0.2.
- [LPS-103051]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 3.0.1.

## 2.0.7 - 2019-05-23

### Commits
- [LPS-95388]: don't use the deprecated method (d592ccbb20)
- [LPS-95682]: add font-awesome manually since it is no longer part of the
default sass libs (3d31eb558f)

### Dependencies
- [LPS-94999]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 3.0.0.

## 2.0.6 - 2019-02-19

### Commits
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.6.
- [LPS-87466]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.5.

## 2.0.5 - 2018-10-22

### Commits
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)
- [LPS-74571]: Fix gradleTest (renamed aui to clay) (9dcec103fa)

### Dependencies
- [LPS-86581]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.4.
- [LPS-84218]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.3.
- [LPS-84473]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.2.

## 2.0.4 - 2018-08-02

### Commits
- [LPS-83755]: Update File Versions (c80a286058)
- [LPS-80332]: Manual fixes (df0a491e2e)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LRDOCS-4129]: Fix Gradle plugin README links (4592b9f829)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

### Dependencies
- [LPS-83755]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.1.
- [LPS-77425]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.0.
- [LPS-76475]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.2.0.
- [LPS-75633]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.7.
- [LPS-75589]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.6.
- [LPS-74449]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.5.
- [LPS-74426]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.4.
- [LPS-74789]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.3.
- [LPS-74315]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.2.
- [LPS-74126]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.1.
- [LPS-74126]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.1.0.

## 2.0.3 - 2017-07-10

### Dependencies
- [LPS-73495]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.0.1.

## 2.0.2 - 2017-05-09

### Commits
- [LPS-72367]: Add Gradle test (5630a75f97)
- [LPS-72367]: Update readme (9e8fa09535)
- [LPS-72367]: Automatically add the Classic theme as possible parent
(7398fe6826)
- [LPS-72367]: Automatically find parent theme WAR files (07ae5e3bf4)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)

### Description
- [LPS-72367]: Automatically add the latest release of the
[Liferay Frontend Theme Classic] artifact to the `parentThemes` configuration.
- [LPS-72367]: Change the sensible default of the `buildTheme` task's
`parentFile` property so it's possible to use WAR parent themes from the
`parentThemes` configuration.

## 2.0.1 - 2017-01-07

### Commits
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-66396]: Edit gradle-plugins-theme-builder README and change log
(ae03adff3d)
- [LPS-66396]: Add a real font instead (ebadedfb43)

### Dependencies
- [LPS-69223]: Update the com.liferay.gradle.plugins.css.builder dependency to
version 2.0.0.

## 2.0.0 - 2016-10-11

### Commits
- [LPS-66396]: Test the whole WAR (2da6e3e062)
- [LPS-66396]: Fix smoke test (66a5f98fdc)
- [LPS-66396]: Update readme (dab93a057e)

### Description
- [LPS-66396]: The `buildTheme` task now writes its output files in a temporary
directory, so the generated files are no longer mixed with source files.
- [LPS-66396]: The `diffsDir` property of the `buildTheme` task now points
directly to `project.webAppDir`, which means that all the files contained in
that directory (by default, `src/main/webapp`) are copied over the parent theme
(if specified) and included in the WAR file.
- [LPS-66396]: The `parentDir` and `unstyledDir` properties of `BuildThemeTask`
now only support directories as input. In order to point these properties to a
file, use the `parentFile` and `unstyledFile` properties, respectively.
- [LPS-66396]: Include only the compiled CSS files, and not SCSS files, into
the WAR file.
- [LPS-66396]: Remove the `outputThemeDirs` property of the `BuildThemeTask`.

## 1.0.1 - 2016-10-11

### Commits
- [LPS-66396]: Do not include the empty ".sass-cache" dirs in the WAR file
(e91c92cbaa)
- [LPS-66396]: Split dir and file inputs to have a better up-to-date check
(f04d0f6e59)
- [LPS-66396]: Remove the whole "outputDir" with "cleanBuildTheme" (7051dda58a)
- [LPS-66396]: Defer "buildTheme.outputDir", users can change its value
(aa93f4e26e)
- [LPS-66396]: Avoid hard-coding CSS Builder output dir name (7374f53ba3)
- [LPS-66396]: Run "buildCSS" on "buildTheme" output dir (ccae82ab64)
- [LPS-66396]: fix war tasks to include outputDir and also move css files from
.sass-cache (d9b2f00a12)
- [LPS-66396]: switch defaults for outputDir and diffsDir so that generated files
aren't mixed in with source files (fb7f330f69)
- [LPS-66396]: Edit gradle-plugins-theme-builder README (0ec11c2c12)