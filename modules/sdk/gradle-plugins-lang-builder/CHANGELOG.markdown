# Liferay Gradle Plugins Lang Builder Change Log

## 1.0.1 - 2015-07-27

### Commits
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.

## 1.0.2 - 2015-11-16

### Commits
- [LPS-58330]: The portal tool dependencies are only used to embed single *Args
classes, so they should be considered "provided" (da7c77ffbc)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.3 - 2015-11-16

### Dependencies
- [LPS-60548]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.4 - 2016-03-21

### Commits
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)

## 1.0.5 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-66061]: Update the com.liferay.lang.builder dependency to version 1.0.7.
- [LPS-66061]: Update the com.liferay.lang.builder dependency to version 1.0.6.
- [LPS-66061]: Update the com.liferay.lang.builder dependency to version 1.0.5.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.6 - 2017-03-02

### Commits
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Configure GradleTest in gradle-plugins-lang-builder (702b252e2f)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-70890]: Update the com.liferay.lang.builder dependency to version 1.0.11.
- [LPS-68165]: Update the com.liferay.lang.builder dependency to version 1.0.10.
- [LPS-67658]: Update the com.liferay.lang.builder dependency to version 1.0.9.
- [LPS-67151]: Update the com.liferay.lang.builder dependency to version 1.0.9.
- [LPS-67042]: Update the com.liferay.lang.builder dependency to version 1.0.8.

## 2.0.0 - 2017-04-06

### Dependencies
- [LPS-71375]: Update the com.liferay.lang.builder dependency to version 1.0.12.

### Description
- [LPS-71375]: Add the property `translateSubscriptionKey` in `BuildLangTask` to
support the Translator Text Translation API on Microsoft Cognitive Services.
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-71375]: The properties `translateClientId` and `translateClientSecret` of
`BuildLangTask` are no longer available.

## 2.0.1 - 2017-04-21

### Dependencies
- [LPS-72102]: Update the com.liferay.lang.builder dependency to version 1.0.13.

## 2.0.2 - 2017-08-21

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 2.1.0 - 2017-08-21

### Dependencies
- [LPS-74250]: Update the com.liferay.lang.builder dependency to version 1.0.14.

### Description
- [LPS-74250]: Add the property `excludedLanguageIds` in `BuildLangTask` to
configure which language IDs to exclude in the automatic translation.

## 2.1.1 - 2017-11-08

### Commits
- [LPS-73725]: for LangBuilder to use new constants in
https://github.com/liferay/liferay-portal/commit/4bf57ddfe3f6 (6d48debbe9)

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.15.

## 2.1.2 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.16.

## 2.1.3 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.17.

## 2.1.4 - 2017-11-08

### Dependencies
- [LPS-73725]: Update the com.liferay.lang.builder dependency to version 1.0.18.

## 2.1.5 - 2017-12-04

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.19.

## 2.1.6 - 2017-12-05

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.20.

## 2.1.7 - 2017-12-20

### Commits
- [LPS-76221]: release new jars as part of revert (73dc6a86e0)

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.21.

## 2.1.8 - 2017-12-20

### Commits
- [LPS-76221]: republish (c7b9a54d12)

### Dependencies
- [LPS-76221]: Update the com.liferay.lang.builder dependency to version 1.0.22.

## 2.1.9 - 2018-03-07

### Commits
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)

## 2.2.0 - 2018-03-07

### Dependencies
- [LPS-78493]: Update the com.liferay.lang.builder dependency to version 1.0.23.

### Description
- [LPS-78493]: Add the property `titleCapitalization` in `BuildLangTask` to
enable or disable the automatic capitalization of titles based on
http://titlecapitalization.com.

## 2.2.1 - 2018-03-16

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-78845]: Update the com.liferay.lang.builder dependency to version 1.0.24.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-77425]: Update the com.liferay.lang.builder dependency to version 1.0.23.

## 2.2.2 - 2018-06-11

### Dependencies
- [LPS-82209]: Update the com.liferay.lang.builder dependency to version 1.0.25.

### Description
- [LPS-82343]: Removed the `BuildLangTask`'s `plugin` and
`portalLanguagePropertiesFile` properties.

## 3.0.1 - 2018-07-05

### Dependencies
- [LPS-82343]: Update the com.liferay.lang.builder dependency to version 1.0.26.

## 3.0.2 - 2018-09-04

### Dependencies
- [LPS-85092]: Update the com.liferay.lang.builder dependency to version 1.0.27.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 3.0.3 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 3.0.4 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 3.0.5 - 2018-12-17

### Commits
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-87590]: Update the com.liferay.lang.builder dependency to version 1.0.28.

## 3.0.6 - 2019-01-22

### Commits
- [LPS-85609]: Simplify gradleTest (a8b0feff31)

### Dependencies
- [LPS-89388]: Update the com.liferay.lang.builder dependency to version 1.0.29.

### Description
- [LPS-89388]: The `BuildLangTask` did not remove keys from the
`Language_xx.properties` files when the `Language.properties` file was empty.

## 3.0.7 - 2019-05-22

### Dependencies
- [LPS-94555]: Update the com.liferay.lang.builder dependency to version 1.0.30.

## 3.0.9 - 2019-06-12

### Dependencies
- [LPS-96860]: Update the com.liferay.lang.builder dependency to version 1.0.31.

## 3.0.10 - 2019-06-24

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.