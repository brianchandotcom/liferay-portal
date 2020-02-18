# Liferay Gradle Plugins Soy Change Log

## 3.1.8 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 3.1.7 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 3.1.6 - 2018-06-11

### Commits
- [LPS-81638]: Run "replaceSoyTranslation" after all the other tasks
(7ab6b63898)

### Description
- [LPS-81638]: Automatically run the `replaceSoyTranslation` task after both
`configJSModules` and `transpileJS`, if present.

## 3.1.5 - 2018-06-07

### Commits
- [LPS-81900]: Fix plugin publishing (8411ce5b74)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 3.1.4 - 2017-12-05

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 3.1.3 - 2017-05-25

### Commits
- [LPS-72429]: Use Collections.addAll() (6c448e59ea)

## 3.1.2 - 2017-04-25

### Commits
- [LPS-69139]: Exclude Maven-only transitive dependencies (974a74453c)

## 3.1.1 - 2017-04-18

### Commits
- [LPS-69139]: Update readme (6fd45708c3)
- [LPS-69139]: Always use local util class (b69ebfd285)
- [LPS-69139]: Fix compile (e3b5c4c5fd)
- [LPS-69139]: Reuse logic from portal-tools-soy-builder (875532d8c1)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-69139]: Fix Gradle test (f072a53bc2)
- [LPS-69139]: Minor edits to Soy Gradle plugin's CL and README (9a35839266)

### Dependencies
- [LPS-69139]: Update the com.liferay.portal.tools.soy.builder dependency to
version 3.0.0.
- [LPS-70890]: Update the com.liferay.portal.tools.soy.builder dependency to
version 2.0.1.

### Description
- [LPS-69139]: Reuse logic from [Liferay Portal Tools Soy Builder] in
`BuildSoyTask`.

## 3.1.0 - 2017-02-13

### Commits
- [LPS-69139]: Update readme (f41dc6bece)
- [LPS-67766]: Fix readme (b86f2e6713)
- [LPS-69139]: Update Gradle test (007fd87aea)

### Description
- [LPS-69139]: Add task `wrapSoyAlloyTemplate` (disabled by default) to wrap the
JavaScript functions compiled from Closure Templates into AlloyUI modules.

## 3.0.2 - 2017-02-13

### Commits
- [LPS-69139]: Add "wrapAlloyTemplate" task, disabled by default (5be94432d6)
- [LPS-69139]: Extract constants (1cf52649a4)
- [LPS-69139]: Add new Gradle task (cde87ebab6)
- [LPS-69139]: Fix compile (8b75ece605)
- [LPS-69139]: Use old version for now (cb4404cd56)

### Dependencies
- [LPS-69139]: Update the com.liferay.portal.tools.soy.builder dependency to
version 2.0.0.
- [LPS-69139]: Update the com.liferay.portal.tools.soy.builder dependency to
version 1.0.0.
- [LPS-69139]: Update the com.liferay.portal.tools.soy.builder dependency to
version 2.0.0.

## 3.0.1 - 2017-01-13

### Commits
- [LPS-70036]: Reuse logic in the Gradle plugin (e520cb720d)

### Dependencies
- [LPS-70036]: Update the com.liferay.portal.tools.soy.builder dependency to
version 1.0.0.

### Description
- [LPS-70036]: Reuse logic from [Liferay Portal Tools Soy Builder] in
`ReplaceSoyTranslationTask`.

## 3.0.0 - 2017-01-12

### Commits
- [LPS-70092]: Simplify (8d82c726fe)
- [LPS-70092]: Better check for arguments (367171b296)
- [LPS-70092]: Fix regex (f417b3873c)

### Description
- [LPS-70092]: Support translation replacement for version 2 of the
[Command Line Tools for Metal.js].
- [LPS-67573]: Remove deprecated `BuildSoyTask` class from the
`com.liferay.gradle.plugins.soy` package.

## 2.0.1 - 2017-01-12

### Commits
- [LPS-70092]: Use different argument delimiter to support metal-cli 2+
(0ebb21bf82)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-67766]: Fix gradle-plugins-soy changelog (66fcb637d1)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67766]: Edit Soy Gradle plugin README (54128cd4ad)

## 2.0.0 - 2016-09-12

### Commits
- [LPS-67766]: Minor fixes to the README (60e05bffc2)
- [LPS-67766]: Update README (e40b06f7c3)

### Description
- [LPS-67766]: Add a new `com.liferay.soy.translation` plugin to use a custom
localization mechanism in the generated `.soy.js` files by replacing
`goog.getMsg` definitions with a different function call (e.g.,
`Liferay.Language.get`).
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-67573]: Move `BuildSoyTask` from the `com.liferay.gradle.plugins.soy`
package to the `com.liferay.gradle.plugins.soy.tasks` package.

## 1.0.5 - 2016-09-12

### Commits
- [LPS-67766]: Export packages (502c71a535)
- [LPS-67766]: Bypass SF "Missing language key" error (af9b5fbf67)
- [LPS-67766]: Plugin to replace Soy translation calls with Liferay ones
(9934539c27)
- [LPS-67766]: Move task class to "tasks" package (a8cf89105e)
- [LPS-67766]: Make methods private to reduce API surface (dfc065963c)
- [LPS-67658]: Convert gradle-plugins-soy sample into a smoke test (c79d40a9c9)
- [LPS-67658]: Configure GradleTest in gradle-plugins-soy (5a53bd43bb)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-66709]: Edit README for Soy Gradle plugin (3fd4e5e62b)
- [LPS-66709]: README for gradle-plugins-soy (f06bdf275d)

## 1.0.4 - 2016-06-16

### Commits
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.3 - 2016-03-18

### Commits
- [LPS-64376]: Update soy compiler version (49db439fd3)
- [LPS-63943]: This is done automatically now (f1e42382d9)

## 1.0.2 - 2016-02-22

### Commits
- [LPS-63525]: Update sample (714486fa7a)
- [LPS-63525]: Don't assume "java" plugin is already applied (f5a7f3d1bf)
- [LPS-63525]: Isolate Soy classpath in its own configuration (d6801c0681)
- [LPS-63525]: Simplify (ab24dcb904)
- [LPS-63525]: Add Gradle input annotation (6e228405ee)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-63525]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.1 - 2015-11-16

### Commits
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.