# Liferay Gradle Plugins WeDeploy Change Log

## 3.0.3 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 3.0.2 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-86581]: Use sdk plugin classpath for Gradle tests (786098b708)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 3.0.1 - 2018-09-04

### Commits
- [LPS-84094]: Automatically apply WeDeployPlugin to every subproject that has a
wedeploy.json file (864ca6888a)

## 2.0.2 - 2018-08-30

### Commits
- [LPS-84094]: Semantic versioning (f3051e924f)
- [LPS-84094]: Fixes for gradleTest (20ccecaa5f)
- [LPS-84094]: Move tasks to WeDeployPlugin (71829d5ba3)
- [LPS-84094]: Rename to WeDeployPlugin (5880e6b22f)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.

## 2.0.1 - 2018-08-28

### Commits
- [LPS-84094]: Apply everywhere (1af8cb838a)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 1.0.4 - 2018-08-08

### Commits
- [LPS-84094]: Semantic versioning (6abcccb6d8)
- [LPS-84094]: Fix compile java (3906e26555)
- [LPS-84094]: Rename WeDeploy Message Queue plugin (b7f7319409)
- [LPS-84094]: Rename WeDeploy Data plugin (bb3ff93971)

## 1.0.3 - 2018-08-08

### Commits
- [LPS-84094]: Simplify WeDeployXXXPlugin classes (7b309a5149)
- [LPS-84094]: Add abstract methods (a95c1237c9)
- [LPS-84094]: Blind copy from WeDeployDataPlugin (f06e78c43e)

## 1.0.2 - 2018-08-07

### Commits
- [LPS-84094]: Adapt for message queue (1499ecbf46)
- [LPS-84094]: Blind copy from WeDeployDataPlugin (c01dbd8be9)

## 1.0.1 - 2018-08-05

### Commits
- [LPS-84094]: prep nexxt (cd4504bc9e)
- [LPS-84094]: Read properties from gradle-ext.properties (b32cca1fb7)