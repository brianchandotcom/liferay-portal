# Liferay Gradle Plugins Jasper JSPC Change Log

## 2.0.6 - 2019-08-08

### Commits
- [LPS-98937]: Source formatting (a67bd0f220)
- [LPS-98937]: fix tests (7dfc89d5ea)
- [LPS-98937]: change to test to align with new defaults for JspCPlugin
(b936b01cf7)
- [LPS-98937]: refactor compileJSP generateJSPJava tasks to run before building
jar (5bdc217573)
- [LPS-98877 LPS-96095]: auto SF for portlet-api (fc1fff6de9)
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.0.5 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.0.4 - 2018-11-16

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
- [LPS-79653]: Portlet 3.0: Upgrade to the Portlet 3.0.0 API (upgrade modules)
(65a8772c08)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LRDOCS-4129]: Fix Gradle plugin README links (4592b9f829)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 2.0.3 - 2017-11-30

### Commits
- [LPS-76202]: Add Gradle test (845d047848)
- [LPS-76202]: Defer temporary dir evaluation (46d2bd88b2)
- [LPS-76202]: Defer build dir evaluation (a7a285c877)

### Description
- [LPS-76202]: Defer evaluation of the project's build directory so it can be
changed after applying the plugin.

## 2.0.2 - 2017-08-28

### Commits
- [LPS-74368]: Not needed anymore (525c8dc058)
- [LPS-74368]: Fix readme (92d54061e1)
- [LPS-67573]: Enable semantic versioning check on CI (36750689a4)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

### Description
- [LPS-74368]: Remove all dependency exclusions from the `jspCTool`
configuration's `com.liferay.jasper.jspc` default dependency.

## 2.0.1 - 2017-03-03

### Commits
- [LPS-71048]: Update readme (8e34944a83)
- [LPS-71048]: Exclude old version of "javax.servlet:servlet-api" (ee08884e25)
- [LPS-70677]: Wordsmith Jasper JSPC change log and README (2e2c896b98)

### Description
- [LPS-71048]: Exclude `javax.servlet` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.

## 2.0.0 - 2017-02-16

### Commits
- [LPS-70677]: Update readme (9e8a9db253)

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-70677]: Exclude `com.liferay.portal` transitive dependencies from the
`jspCTool` configuration's `com.liferay.jasper.jspc` default dependency.
- [LPS-70677]: Support `compileOnly` dependencies by using
`sourceSets.main.compileClasspath` as a dependency in the `jspC` configuration.

## 1.0.10 - 2017-02-16

### Commits
- [LPS-67573]: Make methods private to reduce API surface (098f7b3465)
- [LPS-70677]: Fix Gradle test (4b1eb8dd5b)
- [LPS-70677]: Exclude old version of util-taglib that comes with jasper-jspc
(6c6806eabc)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70677]: Use "compileOnly" configuration if it exists (04e12ef358)
- [LPS-70677]: Use fixed version of portal-kernel (e05828a5f2)
- [LPS-70677]: Pass the whole "compileClasspath" configuration to JspC
(61a531bd62)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66853]: Remove unused taglib imports (ac90cb066b)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-66709]: Edit gradle-plugins-jasper-jspc README (071c1cfacb)
- [LPS-66709]: Add README for gradle-plugins-jasper-jspc (d2e102d92a)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Convert gradle-plugins-jasper-jspc sample into a smoke test
(b3d79990da)
- [LPS-67658]: Configure GradleTest in gradle-plugins-jasper-jspc (e1afb2c791)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

## 1.0.9 - 2016-06-16

### Commits
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.8 - 2016-03-23

### Commits
- [LPS-64343]: Update sample (e98ed9bef2)
- [LPS-64343]: Don't assume "war" plugin will be applied first (5d182bf679)
- [LPS-64343]: Rename (2fb09d1bf4)
- [LPS-64343]: Add task description (f69887c01d)
- [LPS-64343]: Use constant (c61be03bae)
- [LPS-64343]: JspC does not require these system properties anymore
(17e31a30c2)
- [LPS-64343]: Extract methods (4733bbaea5)

## 1.0.7 - 2016-03-17

### Commits
- [LPS-64343]: Fix sample (bda30f8080)
- [LPS-64343]: Add the jar to the classpath (6fdfd3aa8a)
- [LPS-64343]: Use "compile" configuration instead (34de4f8a73)
- [LPS-63943]: This is done automatically now (f1e42382d9)

## 1.0.6 - 2016-03-03

### Commits
- [LPS-63864]: Add jspCTool dependencies only if no one already added them
(ca86b257db)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

## 1.0.5 - 2016-01-04

### Commits
- [LPS-60950]: Update sample (ab8007627d)
- [LPS-60950]: We use sourceSets, so we should apply the "java" plugin first
(3f0a2ca335)
- [LPS-60950]: Simplify (4af480f418)
- [LPS-60950]: Not necessary (55698a654c)
- [LPS-60950]: Pass the JspC classpath via System.in (a83dd61b13)

### Dependencies
- [LPS-60950]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.4 - 2015-11-16

### Commits
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.

## 1.0.3 - 2015-08-31

### Commits
- [LPS-51081]: Show JSP compilation messages/errors (0584cd87c5)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.2 - 2015-07-27

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.

## 1.0.1 - 2015-07-21

### Commits
- [LPS-51081]: Split the classpath in 2 different configurations, so they can be
assembled in the correct order: the Jasper JspC jars must come first
(c19e98e8aa)
- [LPS-51081]: Defer evaluation of portalDir and webAppDir, so, if their values
are closures, they will be evaluated while running the tasks (b6cddc02e0)
- [LPS-51081]: Rename (bf2e1b9918)
- [LPS-51081]: Set default for webAppDir from the WAR plugin (236b730c16)
- [LPS-51081]: Skip the JSP compilation if there are no JSP files in the project
(7bc515dd8d)