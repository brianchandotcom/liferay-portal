# Liferay Gradle Plugins Change Log Builder Change Log

## 1.1.3 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.1.2 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-84119]: Move variable declaration inside if/else statement for better
performance (8dd499456b)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 1.1.1 - 2018-07-10

### Commits
- [LPS-82960]: Check the initial commit (5a0afd795e)
- [LPS-82960]: Add method getTicketId (no logic changes) (20a3e701ad)

### Description
- [LPS-82960]: Avoid error when the only valid commit is the initial commit.

## 1.1.0 - 2018-06-25

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-82857]: Avoid error when running the `buildChangeLog` task on Git
branches that do not contain commits older than two years.

## 1.0.5 - 2018-06-25

### Commits
- [LPS-67653]: Move unexported package to "internal" (fa3f7849a3)
- [LPS-82857]: Enforce semantic versioning (fe9ae1d6b5)
- [LPS-82857]: Add packageinfo (3c1f5ff96a)
- [LPS-82857]: Export package (0edada82c0)
- [LPS-67653]: Make methods private to reduce API surface (b8fab3c7de)
- [LPS-82857]: Find the oldest commit in a branch (fc79118a51)

## 1.0.4 - 2018-05-16

### Commits
- [LPS-80950]: Stop using RepositoryCache since it will hold reference to git
repositories and later cause OOM (f68000470e)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Partial revert of b739c8fcdc5d1546bd642ca931476c71bbaef1fb
(02ca75b1da)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LRDOCS-4129]: Fix Gradle plugin README links (4592b9f829)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-70411]: Provide more information to Assert.assertEquals for arrays
(07dd98a3da)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-66709]: Wordsmithing READMEs for consistency (a3cc8c4c6b)
- [LPS-66709]: Edit gradle-plugins-change-log-builder README (07890cc92c)
- [LPS-66709]: Add README for gradle-plugins-change-log-builder (999f3d2e5a)
- [LPS-67352]: SF, enforce empty line after finishing referencing variable
(4ff2bb6038)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

### Description
- [LPS-80950]: Avoid out-of-memory errors when running on large Git
repositories.

## 1.0.3 - 2016-06-30

### Commits
- [LPS-66951]: Option to specify a custom Git directory (5540a7de2c)
- [LPS-66951]: Option to specify a custom commit range (6b6a36520f)
- [LPS-66951]: Decouple BuildChangeLogTask from the current project (672b52e7a7)
- [LPS-66951]: Option to specify multiple directories for change log
(4b2e0e6cb5)

## 1.0.2 - 2016-06-16

### Commits
- [LPS-65810]: Gradle plugins aren't used in OSGi, no need to export anything
(83cdd8ddcd)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.1 - 2016-03-19

### Commits
- [LPS-61720]: we started this 2 years ago (980df21be4)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-62942]: Explicitly list exported packages for correctness (f095a51e25)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)