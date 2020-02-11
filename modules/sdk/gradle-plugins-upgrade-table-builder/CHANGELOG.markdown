# Liferay Gradle Plugins Upgrade Table Builder Change Log

## 1.0.1 - 2015-07-27

### Commits
- [LPS-51081]: Update to Gradle 2.5 (3aa4c1f053)
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-51081]: Consistency with Gradle 2.4 dependencies (6d4008a98c)
- [LPS-55187]: Use only 1.0.6 (f63748d15a)
- [LPS-51081]: use only 1.0.5 (4d9c09dfce)
- [LPS-51081]: Update to Gradle 2.4 (9966e0be8d)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.

## 1.0.2 - 2015-11-10

### Commits
- [LPS-60306]: Fix sample (94538ce54d)
- [LPS-60306]: Add Gradle input annotations (88fbb690cb)
- [LPS-60306]: Allow closures and callables as values for the task args
(1481cc8e57)
- [LPS-60306]: Set task group (1e39f417b7)
- [LPS-60306]: Allow custom classpath (bf0382a5f4)
- [LPS-60306]: Allow custom args (32a2e6900e)
- [LPS-60306]: projectDir is already the default value of workingDir
(2ea76b8d12)
- [LPS-60306]: Allow custom main class (3f54a00d22)
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)
- [LPS-58330]: The portal tool dependencies are only used to embed single *Args
classes, so they should be considered "provided" (da7c77ffbc)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-60306]: Update the com.liferay.gradle.util dependency to version 1.0.23.
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.3 - 2015-11-16

### Commits
- [LPS-60243]: Ran gradlew formatJavadoc (3417b8f7c8)

## 1.0.4 - 2016-05-31

### Commits
- [LPS-66222]: Update Gradle (9e336fc2ba)
- [LPS-66064]: Better glob to distinguish asm-*.jar from asm-commons-*.jar
(d50ced317d)
- [LPS-66064]: Remove use of "-liferay-includeresource" (bbd6b63415)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-64021]: Prefix directive (0eb9b8b7d8)
- [LPS-64021]: Apply for "sdk" (50aea4bb04)
- [LPS-64021]: Use directive instead (9c31b9fc18)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61420]: Fit on single line (9cc5731c19)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-66222]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.4.
- [LPS-65971]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.3.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.5 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.6 - 2016-10-28

### Commits
- [LPS-66222]: Use Gradle built-in method instead (f4ada4df66)
- [LPS-67573]: Make methods private to reduce API surface (e165206a41)
- [LPS-66222]: Avoid NPE if "releaseInfoFile" (which is optional) is null
(523e9151ba)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-68165]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.5.

## 2.0.0 - 2016-10-28

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-66222]: Allow the optional `releaseInfoFile` property of the
`BuildUpgradeTableTask` to be `null` without throwing a `NullPointerException`.

## 2.0.1 - 2017-04-27

### Commits
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-66222]: Convert sample into a Gradle "smoke" test (5bb0e36452)
- [LPS-66222]: Using "compileOnly" is the same (64959a4066)

### Dependencies
- [LPS-53392]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.7.
- [LPS-70890]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.6.
- [LPS-66222]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.5.

## 2.0.2 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-81944]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.9.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-77425]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.8.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-73148]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.8.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 2.0.3 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.0.4 - 2019-10-22

### Commits
- [LPS-103170]: Update Gradle task inputs (6ea71d56fc)
- [LPS-102700]: Fix bnd error (include literal dot) (d65985bae3)
- [LPS-101089]: Remove usages on private package classes. (b598761012)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 3.0.0 - 2019-10-22

### Dependencies
- [LPS-103170]: Update the com.liferay.portal.tools.upgrade.table.builder
dependency to version 1.0.10.