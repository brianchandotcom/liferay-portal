# Liferay Gradle Plugins CSS Builder Change Log

## 1.0.1 - 2015-07-13

### Commits
- [LPS-51081]: Do not expand the portal web jar if there are no CSS files to
compile (12d1e19571)
- [LPS-51081]: Update to Gradle 2.5 (3aa4c1f053)
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-51081]: Consistency with Gradle 2.4 dependencies (6d4008a98c)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.

## 1.0.2 - 2015-07-20

### Commits
- [LPS-51038]: Update sample (cd39ae5065)
- [LPS-51038]: Porting af1fc775a63b9ab54533e9bd694fc21f10d5b10d from Ant to
Gradle (72a3bac13a)

## 1.0.3 - 2015-07-27

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.

## 1.0.4 - 2015-08-07

### Commits
- [LPS-51081]: gradle-plugins-css-builder should be able to grab the portal
common CSS files from a dependency, not only from a dir (6a0f0d207a)
- [LPS-55156]: - update paths to common css, src to tmp (179f23610d)
- [LPS-55156]: - Update common css paths (1f596f86ec)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.5 - 2015-08-10

### Commits
- [LPS-51081]: Maybe "buildCSS" depends from a task that will create the portal
common dir, and so it is too early to check if that dir exists (0af296b1ce)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.

## 1.0.6 - 2015-08-13

### Commits
- [LPS-56798]: - update paths for common css (8b0c12cdd6)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.

## 1.0.7 - 2015-11-05

### Commits
- [LPS-56692]: Fix samples (2737ac7adc)
- [LPS-56692]: Clean .map files (2b5e67a194)
- [LPS-56692]: Update Gradle plugin (815e720215)
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)
- [LPS-58330]: The portal tool dependencies are only used to embed single *Args
classes, so they should be considered "provided" (da7c77ffbc)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.

## 1.0.8 - 2015-11-13

### Commits
- [LPS-60234]: Update sample to better show standalone use of the plugin
(14d6bc2aef)
- [LPS-60234]: Remove dependency (ead6958605)
- [LPS-60234]: Refactor to accept closures as arguments and sensible defaults
(49642a7da1)
- [LPS-60234]: Allow custom classpath (5e55e2374e)
- [LPS-60234]: Allow custom main class (b3b4f28587)
- [LPS-60234]: Simplify, no need for another field (dc2f229ecb)
- [LPS-60234]: projectDir is already the default value of workingDir
(e8b9184e4a)
- [LPS-60234]: Allow custom args (b3532d97b0)

### Dependencies
- [LPS-60234]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.10 - 2015-12-01

### Commits
- [LPS-60172]: Update Gradle plugin (87bab63512)

## 1.0.11 - 2016-04-30

### Commits
- [LPS-62570]: latest css builder maven files (dfa05cae77)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-64021]: Prefix directive (0eb9b8b7d8)
- [LPS-64021]: Apply for "sdk" (50aea4bb04)
- [LPS-64021]: Use directive instead (9c31b9fc18)
- [LPS-64100]: - update references from frontend.common.css to
frontend.css.common (e5d0d01ea2)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61420]: Fit on single line (9cc5731c19)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-62570]: Update the com.liferay.css.builder dependency to version 1.0.17.
- [LPS-62570]: Update the com.liferay.css.builder dependency to version 1.0.16.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.12 - 2016-05-17

### Commits
- [LPS-65568]: Force UTF-8 while invoking CSS Builder via Gradle (63fc36fed6)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)

## 1.0.13 - 2016-06-03

### Commits
- [LPS-66281]: Add support for "sass.output.dir" (f05abb281a)
- [LPS-66281]: Pass com.liferay.frontend.css.common jar to CSS Builder
(698ce3af17)
- [LPS-66281]: Add support to pass a file or a dir as portal common path
(0fb59f5402)
- [LPS-66064]: Better glob to distinguish asm-*.jar from asm-commons-*.jar
(d50ced317d)
- [LPS-66064]: Remove use of "-liferay-includeresource" (bbd6b63415)

### Dependencies
- [LPS-66281]: Update the com.liferay.css.builder dependency to version 1.0.18.

## 1.0.14 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.15 - 2016-09-13

### Commits
- [LPS-67658]: Convert gradle-plugins-css-builder sample into a smoke test
(9645b30f1c)
- [LPS-67658]: Configure GradleTest in gradle-plugins-css-builder (69e3273389)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-66709]: README Edits (0e01b6111d)
- [LPS-66709]: README for gradle-plugins-css-builder (788ab8dce2)

### Dependencies
- [LPS-67986]: Update the com.liferay.css.builder dependency to version 1.0.20.
- [LPS-67986]: Update the com.liferay.css.builder dependency to version 1.0.19.
- [LPS-67658]: Update the com.liferay.css.builder dependency to version 1.0.18.

## 1.0.16 - 2016-11-17

### Commits
- [LPS-69223]: Use Gradle built-in method instead (02a1d7ef0d)
- [LPS-67573]: Make methods private to reduce API surface (ad1491df79)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-66709]: Use present tense, when possible (ce0081421a)
- [LPS-66709]: Add command-line arguments in the READMEs (4c6dc97741)
- [LPS-66709]: Edit READMEs (2072601ff5)
- [LPS-66709]: Add links to portal tools (8baf0882de)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)

## 2.0.0 - 2016-11-17

### Commits
- [LPS-69223]: Update readme (57d24f1100)

### Dependencies
- [LPS-69223]: Update the com.liferay.css.builder dependency to version 1.0.21.

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-69223]: Update default value of the `precision` property for
`BuildCSSTask` from `5` to `9`.

## 2.0.1 - 2017-07-10

### Commits
- [LPS-66709]: We say "directory that contains" everywhere else (fbdaeffe5b)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)

### Dependencies
- [LPS-73495]: Update the com.liferay.css.builder dependency to version 1.0.28.
- [LPS-73495]: Update the com.liferay.css.builder dependency to version 1.0.27.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.
- [LPS-70890]: Update the com.liferay.css.builder dependency to version 1.0.26.
- [LPS-71331]: Update the com.liferay.css.builder dependency to version 1.0.25.
- [LPS-70890]: Update the com.liferay.css.builder dependency to version 1.0.24.
- [LPS-69706]: Update the com.liferay.css.builder dependency to version 1.0.23.
- [LPS-69706]: Update the com.liferay.css.builder dependency to version 1.0.22.

## 2.0.2 - 2017-08-12

### Commits
- [LPS-74126]: Add new setting to the Gradle plugin (d476fbbb30)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.

## 2.1.0 - 2017-08-12

### Commits
- [LPS-74126]: Update readme (a7e1123176)

### Dependencies
- [LPS-74126]: Update the com.liferay.css.builder dependency to version 1.1.0.

### Description
- [LPS-74126]: Add the `appendCssImportTimestamps` property to `BuildCSSTask`.

## 2.1.1 - 2017-08-15

### Commits
- [LPS-74126]: Test Gradle (23f15ef507)

### Dependencies
- [LPS-74126]: Update the com.liferay.css.builder dependency to version 1.1.1.

## 2.1.2 - 2017-09-18

### Commits
- [LPS-66709]: Fix readme (851e97dc76)
- [LPS-74126]: Remove unnecessary whitespace (e9993f4aa5)
- [LPS-74126]: Ran "ant format-source" (a6c3802988)
- [LPS-74126]: Hugo SF, please fix, original was right, but auto SF is forcing me
to do this (93c3c73c9f)
- [LPS-74126]: Test RTL (6537a70b00)
- [LPS-74126]: Test with different quote styles (1ed44369e7)

### Dependencies
- [LPS-74315]: Update the com.liferay.css.builder dependency to version 1.1.2.

## 2.1.3 - 2017-09-19

### Commits
- [LRDOCS-3956]: Sass not SASS (b03b4cb4d3)
- [LRDOCS-3956]: Wordsmithing for CSS Builder note (8dbade9c43)
- [LRDOCS-3956]: Clarify that the first option doesn't work everywhere
(2cdbef2533)
- [LRDOCS-3956]: In Gradle they're called "projects" (45e588543b)
- [LRDOCS-3956]: You can add that line directly at root level (4f279bf761)
- [LRDOCS-3956]: Add note on how to configure Ruby Sass compiler for CSS Gradle
plugin (3ce24fd407)

### Dependencies
- [LPS-74789]: Update the com.liferay.css.builder dependency to version 1.1.3.

## 2.1.4 - 2017-10-06

### Dependencies
- [LPS-74426]: Update the com.liferay.css.builder dependency to version 1.1.4.

## 2.1.5 - 2017-10-11

### Dependencies
- [LPS-74449]: Update the com.liferay.css.builder dependency to version 2.0.0.

## 2.1.6 - 2017-11-01

### Dependencies
- [LPS-75589]: Update the com.liferay.css.builder dependency to version 2.0.1.

## 2.1.7 - 2017-11-07

### Commits
- [LPS-75633]: Update readme (e905cd6920)
- [LPS-75633]: We need to pass a system property, not an argument (a7745c7214)
- [LPS-75633]: set sass.compiler.jni.clean.temp.dir to true for
gradle-plugins-css-builder (17f9e36dff)

### Dependencies
- [LPS-75633]: Update the com.liferay.css.builder dependency to version 2.0.2.

## 2.1.8 - 2017-12-19

### Commits
- [LPS-76475]: Update readme (fe44301b8f)
- [LPS-76475]: CSS Builder does not support the "sass.dir.n" argument anymore
(77fc0270fc)
- [LPS-76475]: Add new task parameters and deprecate the old ones (1ba7b98967)

## 2.2.0 - 2017-12-19

### Dependencies
- [LPS-76475]: Update the com.liferay.css.builder dependency to version 2.1.0.

### Description
- [LPS-76475]: Replace the `BuildCSSTask`'s `docrootDir`, `portalCommonDir`,
`portalCommonFile`, and `portalCommonPath` properties with `baseDir`,
`importDir`, `importFile`, and `importPath`. The previous properties are still
available, but they are deprecated.
- [LPS-76475]: Fix invocation of the [Liferay CSS Builder] if the
`BuildCSSTask`'s `dirNames` property contains more than one value.

## 2.2.1 - 2018-08-02

### Commits
- [LPS-83755]: Update File Versions (c80a286058)
- [LPS-82828]: Revert "LPS-82828 Deprecated as of 7.1.0" (470150b661)
- [LPS-82828]: Revert "LPS-82828 ant format-javadoc" (eb3e7c0a6a)
- [LPS-82828]: ant format-javadoc (b36ea4354d)
- [LPS-82828]: Deprecated as of 7.1.0 (69573bff7e)
- [LPS-80332]: Manual fixes (df0a491e2e)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LRDOCS-4319]: Update Gradle plugin README intro descriptions for consistency
(72104bde58)
- [LRDOCS-4319]: Update Gradle plugin BND descriptions for consistency
(e1495e8e8d)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

### Dependencies
- [LPS-83755]: Update the com.liferay.css.builder dependency to version 2.1.1.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-77425]: Update the com.liferay.css.builder dependency to version 2.1.0.

## 2.2.2 - 2018-08-15

### Commits
- [LPS-84473]: (6659d6f32b)
- [LPS-84039]: Fix calls to deprecated methods (b7297b377f)

### Dependencies
- [LPS-84473]: Update the com.liferay.css.builder dependency to version 2.1.2.

## 2.2.3 - 2018-08-22

### Dependencies
- [LPS-84218]: Update the com.liferay.css.builder dependency to version 2.1.3.

## 2.2.4 - 2018-10-22

### Commits
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 2.2.5 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 2.2.6 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.2.7 - 2019-05-23

### Commits
- [LPS-94999]: Fix whitespace (0085c61296)
- [LPS-94999]: Sort blocks alphabetically (d687e36c58)
- [LPS-94999]: Lazily set imports (8d7fe8c3ce)
- [LPS-94999]: Allow callable/closure as imports value (a647e05a2d)
- [LPS-94999]: Initialize variables with CSSBuilderArgs values (2c4fcffdc4)
- [LPS-95388]: update gradle-plugin-css-builder gradleTest to test for excluded
files (15beed88b4)
- [LPS-95388]: allow excludes to be configurable in gradle-plugins-css-builder
(9ee6dca805)
- [LPS-95388]: allow importPath to accept a fileCollection (8f8c237c9e)
- [LPS-95388]: remove deprecated methods (adf3fa0f88)
- [LPS-95388]: clean up deprecated methods (f1c9e39693)
- [LPS-95388]: use new options (c24806e0d1)
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-94999]: Update the com.liferay.css.builder dependency to version 3.0.0.

## 3.0.1 - 2019-10-13

### Commits
- [LPS-102700]: Fix bnd error (include literal dot) (d65985bae3)

### Dependencies
- [LPS-103051]: Update the com.liferay.css.builder dependency to version 3.0.1.
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 3.0.2 - 2019-10-31

### Dependencies
- [LPS-103169]: Update the com.liferay.css.builder dependency to version 3.0.2.

## 3.0.3 - 2019-11-04

### Commits
- [LPS-104028]: good enough (f6c16e67f3)
- [LPS-104028]: Fix for gradleTest (0d3fdd5b5e)
- [LPS-104028]: Remove unneeded line break (ad7397d51b)
- [LPS-104028]: Add SuppressWarnings unchecked (7bef51fe01)
- [LPS-104028]: Move configuration logic to the plugin (44a08d6eb6)
- [LPS-104028]: fix stack overflow exception when using ruby sass compiler
(2ba07454c9)