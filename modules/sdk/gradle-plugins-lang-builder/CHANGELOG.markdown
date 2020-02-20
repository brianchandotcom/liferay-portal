# Liferay Gradle Plugins Lang Builder Change Log

## 3.0.14 - 2020-01-16

### Dependencies
- [LPS-106614]: Update the com.liferay.lang.builder dependency to version
1.0.33.

## 3.0.13 - 2020-01-15

### Commits
- [LPS-100515]: Update README.markdown (694b3791de)
- [LPS-100515]: Update plugins Gradle version (448efac158)
- [LPS-102700]: Fix bnd error (include literal dot) (d65985bae3)
- [LPS-84119]: Do not declare var (85dc5fdf91)

### Dependencies
- [LPS-106614]: Update the com.liferay.lang.builder dependency to version
1.0.32.

## 3.0.12 - 2019-06-26

### Commits
- [LPS-95819]: Fix for theme projects (abba685d82)

## 3.0.11 - 2019-06-26

### Commits
- [LPS-95819]: Improve logic for finding the app project (fcb4436eff)

## 3.0.10 - 2019-06-24

### Commits
- [LPS-95819]: Replace releng variables in app localization files (955d61e03d)
- [LPS-95819]: Copy app localization files into the jar (eaf8f453c8)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 3.0.9 - 2019-06-12

### Dependencies
- [LPS-96860]: Update the com.liferay.lang.builder dependency to version 1.0.31.

## 3.0.8 - 2019-06-04

### Commits
- [LPS-95819]: Configure buildLang to translate app property files too
(01a699f7b6)

## 3.0.7 - 2019-05-22

### Dependencies
- [LPS-94555]: Update the com.liferay.lang.builder dependency to version 1.0.30.

## 3.0.6 - 2019-01-22

### Commits
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)

### Dependencies
- [LPS-89388]: Update the com.liferay.lang.builder dependency to version 1.0.29.

### Description
- [LPS-89388]: The `BuildLangTask` did not remove keys from the
`Language_xx.properties` files when the `Language.properties` file was empty.

## 3.0.5 - 2018-12-17

### Commits
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-87590]: Update the com.liferay.lang.builder dependency to version 1.0.28.

## 3.0.4 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 3.0.3 - 2018-11-16

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

## 3.0.2 - 2018-09-04

### Dependencies
- [LPS-85092]: Update the com.liferay.lang.builder dependency to version 1.0.27.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 3.0.1 - 2018-07-05

### Dependencies
- [LPS-82343]: Update the com.liferay.lang.builder dependency to version 1.0.26.

## 2.2.3 - 2018-06-28

### Commits
- [LPS-82343]: Remove import (dac8b5668a)
- [LPS-82343]: Remove functionality to warn for duplicate keys since SF is taking
care of that now (looks like nobody was using that anyway) (188e2a041f)

## 2.2.2 - 2018-06-11

### Dependencies
- [LPS-82209]: Update the com.liferay.lang.builder dependency to version 1.0.25.

### Description
- [LPS-82343]: Removed the `BuildLangTask`'s `plugin` and
`portalLanguagePropertiesFile` properties.

## 2.2.1 - 2018-03-16

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-78493]: update test (4c0227472d)

### Dependencies
- [LPS-78845]: Update the com.liferay.lang.builder dependency to version 1.0.24.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-77425]: Update the com.liferay.lang.builder dependency to version 1.0.23.

## 2.2.0 - 2018-03-07

### Commits
- [LPS-78493]: Add Gradle test (f52a9db50d)
- [LPS-78493]: Sort (08d2d56733)

### Dependencies
- [LPS-78493]: Update the com.liferay.lang.builder dependency to version 1.0.23.

### Description
- [LPS-78493]: Add the property `titleCapitalization` in `BuildLangTask` to
enable or disable the automatic capitalization of titles based on
http://titlecapitalization.com.

## 2.1.9 - 2018-03-07

### Commits
- [LPS-78493]: semver gradle-plugins-lang-builder (7980ed4d8d)
- [LPS-78493]: - add property to control whether titles are capitalized
(0d65437319)
- [LRDOCS-4319]: Update Gradle plugin README intro descriptions for consistency
(72104bde58)
- [LRDOCS-4319]: Update Gradle plugin BND descriptions for consistency
(e1495e8e8d)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

## 2.1.8 - 2017-12-20

### Commits
- [LPS-76221]: republish (c7b9a54d12)

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.22.

## 2.1.7 - 2017-12-20

### Commits
- [LPS-76221]: release new jars as part of revert (73dc6a86e0)
- [LPS-76221]: Revert "LPS-76221 Fix Gradle test" (226226efe1)

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.21.

## 2.1.6 - 2017-12-05

### Commits
- [LPS-76221]: Fix Gradle test (0ed9bfba1b)

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.20.

## 2.1.5 - 2017-12-04

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.19.

## 2.1.4 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.18.

## 2.1.3 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.17.

## 2.1.2 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.16.

## 2.1.1 - 2017-11-08

### Commits
- [LPS-73725]: for LangBuilder to use new constants in
https://github.com/liferay/liferay-portal/commit/4bf57ddfe3f6 (6d48debbe9)
- [LPS-74250]: Update readme (1efb525e4c)

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.15.

## 2.1.0 - 2017-08-21

### Dependencies
- [LPS-74250]: Update the com.liferay.lang.builder dependency to version 1.0.14.

### Description
- [LPS-74250]: Add the property `excludedLanguageIds` in `BuildLangTask` to
configure which language IDs to exclude in the automatic translation.

## 2.0.2 - 2017-08-21

### Commits
- [LPS-74250]: Add new property to the Gradle task (66ac47ba67)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 2.0.1 - 2017-04-21

### Commits
- [LPS-71375]: Fix Gradle test (02730211ae)

### Dependencies
- [LPS-72102]: Update the com.liferay.lang.builder dependency to version 1.0.13.

## 2.0.0 - 2017-04-06

### Commits
- [LPS-71375]: Update readme (ed91a092e1)
- [LPS-71375]: Use Gradle built-in method (4801de9e89)

### Dependencies
- [LPS-71375]: Update the com.liferay.lang.builder dependency to version 1.0.12.

### Description
- [LPS-71375]: Add the property `translateSubscriptionKey` in `BuildLangTask` to
support the Translator Text Translation API on Microsoft Cognitive Services.
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-71375]: The properties `translateClientId` and `translateClientSecret` of
`BuildLangTask` are no longer available.

## 1.0.7 - 2017-04-06

### Commits
- [LPS-71375]: Pass subscription key from Gradle to Lang Builder (fba9c370b0)
- [LPS-67573]: Make methods private to reduce API surface (57fc08fbc7)
- [LPS-67573]: Enable semantic versioning check on CI (36750689a4)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)

## 1.0.6 - 2017-03-02

### Commits
- [LPS-62970]: Add tasks to "build" group (fae0d79142)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-66709]: Add command-line arguments in the READMEs (4c6dc97741)
- [LPS-66709]: Edit READMEs (2072601ff5)
- [LPS-66709]: Add links to portal tools (8baf0882de)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Convert gradle-plugins-lang-builder sample into a smoke test
(769ea6d12b)
- [LPS-67658]: Configure GradleTest in gradle-plugins-lang-builder (702b252e2f)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-66709]: README Edits (0e01b6111d)
- [LPS-66709]: README for gradle-plugins-lang-builder (d5955e670f)

### Dependencies
- [LPS-70890]: Update the com.liferay.lang.builder dependency to version 1.0.11.
- [LPS-68165]: Update the com.liferay.lang.builder dependency to version 1.0.10.
- [LPS-67658]: Update the com.liferay.lang.builder dependency to version 1.0.9.
- [LPS-67151]: Update the com.liferay.lang.builder dependency to version 1.0.9.
- [LPS-67042]: Update the com.liferay.lang.builder dependency to version 1.0.8.

## 1.0.5 - 2016-06-16

### Commits
- [LPS-66064]: Better glob to distinguish asm-*.jar from asm-commons-*.jar
(d50ced317d)
- [LPS-66064]: Remove use of "-liferay-includeresource" (bbd6b63415)
- [LPS-65909]: Translations sync from translate.liferay.com [by Liferay
Translation Sync Tool v.4.0.9] (ddb77ec725)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-66061]: Update the com.liferay.lang.builder dependency to version 1.0.7.
- [LPS-66061]: Update the com.liferay.lang.builder dependency to version 1.0.6.
- [LPS-66061]: Update the com.liferay.lang.builder dependency to version 1.0.5.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.4 - 2016-03-21

### Commits
- [LPS-63699]: wordsmith (5019b314ac)
- [LPS-63699]: Show warning only if translate == true (dd8fc2f418)
- [LPS-63699]: Will only sort if no credentials passed in (bc1903d8e8)
- [LPS-63699]: Make Azure credentials optional (4bb161860b)
- [LPS-64021]: Prefix directive (0eb9b8b7d8)
- [LPS-64021]: Apply for "sdk" (50aea4bb04)
- [LPS-64021]: Use directive instead (9c31b9fc18)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61420]: Fit on single line (9cc5731c19)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

## 1.0.3 - 2015-11-16

### Commits
- [LPS-60548]: Set task default when "java" is applied (cefe0061ef)
- [LPS-60548]: Fix sample (2108d3ae83)
- [LPS-60548]: Allow custom classpath (646134b0f8)
- [LPS-60548]: Not really used (9cd2717abe)
- [LPS-60548]: Allow custom main class (2da8581e26)
- [LPS-60548]: Add Gradle input annotations (4ffba7be4f)
- [LPS-60548]: projectDir is already the default value of workingDir
(0ed6642f53)
- [LPS-60548]: Allow custom args (431d86e028)
- [LPS-60548]: Refactor to accept closures as arguments (451b012b66)

### Dependencies
- [LPS-60548]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.2 - 2015-11-16

### Commits
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)
- [LPS-59465]: Those are samples (e838ea2374)
- [LPS-59465]: Duplicate language keys across modules and between core and
modules (22cf1c6ed9)
- [LPS-58330]: The portal tool dependencies are only used to embed single *Args
classes, so they should be considered "provided" (da7c77ffbc)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

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