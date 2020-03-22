# Liferay Gradle Plugins Alloy Taglib Change Log

## 2.0.3 - 2020-03-04

### Commits
- [LPS-106149] Baseline (becb322fa3)
- [LPS-106149] Cacheable tasks (5f1911b5ba)
- [LPS-106167] Partial revert (06136ec8) (c7fc18dd4a)
- [LPS-106167] Update build.gradle (06136ec832)
- [LPS-106167] Use com.liferay.petra.string.StringPool instead (9aa4d72e67)
- [LPS-100515] Update plugins Gradle version (448efac158)
- [LPS-94523] Bump up jackson dependencies to 2.9.10 or 2.9.10.1 (1f4f471bfc)
- [LPS-100448] Auto-SF (0ff1cd4057)
- [LPS-98879] [LPS-96095] auto SF for servlet-api (032b53ca5e)
- [LPS-98631] [LPS-96095] auto SF for xstream (349cd5e076)
- [LPS-98631] [LPS-96095] Revert "LPS-98631 LPS-96095 auto SF for xstream"
(267420ee84)
- [LPS-98631] [LPS-96095] auto SF for xstream (a0bc3d419a)
- [LPS-84119] Use toArray(new T[0]) instead of toArray(new T[size()])
(c23914c90b)
- [LPS-85609] Simplify gradleTest (a8b0feff31)
- [LPS-85609] Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-106149] Update the com.liferay.gradle.util dependency to version 1.0.35.
- [LPS-96247] Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.0.2 - 2018-11-19

### Dependencies
- [LPS-87466] Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.0.1 - 2018-11-16

### Commits
- [LPS-87192] Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192] Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609] Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609] Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-86589] Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117] Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-77425] Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425] Increment all major versions (d25f48516a)
- [LPS-67658] Apply plugin logic goes before script blocks (1cd182b678)
- [LPS-74807] Fix test (2a44e0436c)
- [LPS-67658] Add smoke test (d46a84aa2e)
- [LPS-67658] Add code to run Gradle tests (5794419f99)

### Dependencies
- [LPS-87466] Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094] Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094] Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425] Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584] Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584] Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914] Update the com.liferay.gradle.util dependency to version 1.0.27.

## 2.0.0 - 2017-04-18

### Description
- [LPS-67573] Make most methods private in order to reduce API surface.

## 1.0.4 - 2017-04-18

### Commits
- [LPS-67573] Enable semver check for gradle-plugins-alloy-taglib (72a985ef36)
- [LPS-67573] Remove unused method (0355d2a43d)
- [LPS-67573] Make methods private to reduce API surface (ac537a9812)
- [LPS-67658] Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658] These plugins must work with Gradle 2.5+ (5b963e363d)

## 1.0.3 - 2016-06-16

### Commits
- [LPS-61099] Delete build.xml in modules (c9a7e1d370)
- [LPS-63943] This is done automatically now (f1e42382d9)
- [LPS-62883] Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61088] Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-65749] Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086] Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.1 - 2015-11-08

### Commits
- [LPS-60272] Add Gradle input annotations (7decf4b672)
- [LPS-60272] Simplify by applying JavaPlugin (38efe658c2)
- [LPS-60272] Automatically create a task, ready to be configured (b5ab49c8eb)
- [LPS-60272] Set default for osgiModuleSymbolicName argument (e1088cadae)
- [LPS-60272] src/main/resources/META-INF/resources is the true default
(a6ee275756)
- [LPS-60272] Set default for javaDir argument (cfae9130d3)
- [LPS-60272] Set tasks defaults with callables instead of afterEvaluate
(bef0079634)
- [LPS-60272] Allow custom system properties (d239e4eab3)
- [LPS-60272] projectDir is already the default value of workingDir (f291329be2)
- [LPS-60272] Allow custom main class (2faf9f667f)
- [LPS-59564] Update directory layout for "sdk" modules (ea19635556)
- [LPS-51081] Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081] Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081] Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-60272] Update the com.liferay.gradle.util dependency to version 1.0.23.
- [LPS-58467] Update the com.liferay.gradle.util dependency to version 1.0.19.

[LPS-51081]: https://issues.liferay.com/browse/LPS-51081
[LPS-58467]: https://issues.liferay.com/browse/LPS-58467
[LPS-59564]: https://issues.liferay.com/browse/LPS-59564
[LPS-60272]: https://issues.liferay.com/browse/LPS-60272
[LPS-61088]: https://issues.liferay.com/browse/LPS-61088
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-62883]: https://issues.liferay.com/browse/LPS-62883
[LPS-63943]: https://issues.liferay.com/browse/LPS-63943
[LPS-65086]: https://issues.liferay.com/browse/LPS-65086
[LPS-65749]: https://issues.liferay.com/browse/LPS-65749
[LPS-67573]: https://issues.liferay.com/browse/LPS-67573
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-71117]: https://issues.liferay.com/browse/LPS-71117
[LPS-72914]: https://issues.liferay.com/browse/LPS-72914
[LPS-73584]: https://issues.liferay.com/browse/LPS-73584
[LPS-74807]: https://issues.liferay.com/browse/LPS-74807
[LPS-77425]: https://issues.liferay.com/browse/LPS-77425
[LPS-84094]: https://issues.liferay.com/browse/LPS-84094
[LPS-84119]: https://issues.liferay.com/browse/LPS-84119
[LPS-85609]: https://issues.liferay.com/browse/LPS-85609
[LPS-86589]: https://issues.liferay.com/browse/LPS-86589
[LPS-87192]: https://issues.liferay.com/browse/LPS-87192
[LPS-87466]: https://issues.liferay.com/browse/LPS-87466
[LPS-94523]: https://issues.liferay.com/browse/LPS-94523
[LPS-96095]: https://issues.liferay.com/browse/LPS-96095
[LPS-96247]: https://issues.liferay.com/browse/LPS-96247
[LPS-98631]: https://issues.liferay.com/browse/LPS-98631
[LPS-98879]: https://issues.liferay.com/browse/LPS-98879
[LPS-100448]: https://issues.liferay.com/browse/LPS-100448
[LPS-100515]: https://issues.liferay.com/browse/LPS-100515
[LPS-106149]: https://issues.liferay.com/browse/LPS-106149
[LPS-106167]: https://issues.liferay.com/browse/LPS-106167