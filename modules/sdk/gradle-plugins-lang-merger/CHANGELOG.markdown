# Liferay Gradle Plugins Lang Merger Change Log

## 1.0.1 - 2016-02-24

### Commits
- [LPS-63631]: Change "-lang" modules logic to support the "ee" subtree
(1f7fb718c6)
- [LPS-62833]: Update build-buildscript.gradle (56106ab47b)
- [LPS-62942]: Explicitly list exported packages for correctness (f095a51e25)
- [LPS-62765]: Revert "LPS-62765 SF not related to this pull" (336ee64ab8)
- [LPS-62765]: SF not related to this pull (bcb423ce84)
- [LPS-61088]: Revert "LPS-61088 Remove classes and resources dir from
Include-Resource" (94f30d28e7)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(900459894c)

## 1.0.2 - 2016-06-16

### Commits
- [LPS-65810]: Gradle plugins aren't used in OSGi, no need to export anything
(83cdd8ddcd)
- [LPS-65909]: Translations sync from translate.liferay.com [by Liferay
Translation Sync Tool v.4.0.9] (ddb77ec725)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-64021]: Not needed (e2141c8438)
- [LPS-64021]: Use directive instead (9c31b9fc18)
- [LPS-63943]: Replace only fixed versions (1e1c97c315)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.3 - 2016-06-27

### Commits
- [LPS-66331]: Read and save properties in UTF-8 (93745fb460)

## 1.0.4 - 2018-06-04

### Commits
- [LPS-81895]: Export packages (e472c2febe)
- [LPS-81895]: Enforce semantic versioning (92d4b7358e)
- [LPS-81895]: Prevent direct copy if we're transforming keys (a95153619d)
- [LPS-81895]: Allow to filter and transform language keys (0fc58bc9fd)
- [LPS-77699]: Update translations (6094d59527)
- [LPS-77699]: Update translations (2aeb4b6e9f)
- [LPS-77699]: Update translations (4a00c0b1c8)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-77699]: Update translations (ab4b82afa6)
- [LPS-76522]: Revert "LPS-76522 Sync with Liferay Translation (Pootle)"
(5fc668ae0c)
- [LPS-76522]: Sync with Liferay Translation (Pootle) (09ee546940)
- [LPS-71375]: Fix Gradle test (129a723061)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Convert gradle-plugins-lang-merger sample into a smoke test
(d857d9b7cc)
- [LPS-67658]: Configure GradleTest in gradle-plugins-lang-merger (ccacdae285)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.1.0 - 2018-06-04

### Commits
- [LPS-81895]: Regen lang (b28b9b3700)
- [LPS-81895]: Add Gradle test for single key (b887673303)
- [LPS-81895]: Add Gradle test (6bb560c13b)

### Description
- [LPS-81895]: Add the ability to filter and transform keys with
`MergePropertiesTask`.

## 1.1.1 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-79679]: Auto SF (do not use slanted quotes) (be6cf50a6a)
- [LPS-77699]: Update translations (00ea657343)
- [LPS-77699]: Update translations (24b2c9a10e)
- [LPS-77699]: Update translations (fdbdef575c)
- [LPS-77699]: Update translations (52b7fc4434)
- [LPS-77699]: Update translations (b31d47df37)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 1.1.2 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.