# Liferay Gradle Plugins Jasper JSPC Change Log

## 1.0.2 - 2015-07-27

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.

## 1.0.3 - 2015-08-31

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.4 - 2015-11-16

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.

## 1.0.5 - 2016-01-04

### Dependencies
- [LPS-60950]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.6 - 2016-03-03

### Commits
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)

## 1.0.7 - 2016-03-17

### Commits
- [LPS-63943]: This is done automatically now (f1e42382d9)

## 1.0.9 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.10 - 2017-02-16

### Commits
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Configure GradleTest in gradle-plugins-jasper-jspc (e1afb2c791)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

## 2.0.0 - 2017-02-16

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-70677]: Exclude `com.liferay.portal` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.
- [LPS-70677]: Support `compileOnly` dependencies by using
`sourceSets.main.compileClasspath` as a dependency in the `jspC` configuration.

## 2.0.1 - 2017-03-03

### Description
- [LPS-71048]: Exclude `javax.servlet` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.

## 2.0.2 - 2017-08-28

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

### Description
- [LPS-74368]: Remove all dependency exclusions from the `jspCTool`
configuration's `com.liferay.jasper.jspc` default dependency.

## 2.0.3 - 2017-11-30

### Description
- [LPS-76202]: Defer evaluation of the project's build directory so it can be
changed after applying the plugin.

## 2.0.4 - 2018-11-16

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
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 2.0.5 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.0.6 - 2019-08-08

### Commits
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.