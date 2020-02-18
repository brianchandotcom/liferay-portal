# Liferay Gradle Plugins App Docker Change Log

## 1.0.10 - 2019-11-19

### Commits
- [LPS-100515]: Check .m2-tmp repository (7e54299419)
- [LPS-100515]: The task name must not contain a colon or forward slash
(202125c648)
- [LPS-84119]: Do not declare var (85dc5fdf91)
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-86806]: Fix line breaks in return statements (29ae0ec415)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 1.0.9 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.0.8 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 1.0.7 - 2018-11-01

### Commits
- [LPS-86875]: update gradle-docker-plugin to 3.6.2 (ca4517a231)

### Dependencies
- [LPS-86875]: Update the gradle-docker-plugin dependency to version 3.6.2.

## 1.0.6 - 2018-10-22

### Commits
- [LPS-86588]: Upgrade gradle-docker-plugin to 3.6.1 for Gradle 4.10.2
(3afdd4d33b)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)

### Dependencies
- [LPS-86588]: Update the gradle-docker-plugin dependency to version 3.6.1.

## 1.0.5 - 2018-08-30

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)
- [LPS-66709]: Fix readme (5d383a2929)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 1.0.4 - 2017-11-03

### Dependencies
- [LPS-75704]: Update the gradle-docker-plugin dependency to version 3.2.0.

## 1.0.3 - 2017-10-18

### Commits
- [LPS-75327]: Automatically fix line endings of .sh files (82ee1acba1)

### Description
- [LPS-75327]: Automatically convert `.sh` files to Unix-style line endings when
building the app's Docker image.

## 1.0.2 - 2017-09-20

### Commits
- [LPS-74811]: Add Gradle test (3eb7288f0f)
- [LPS-74811]: Copy the .war file of WAR projects (d94d503c01)

### Description
- [LPS-74811]: Include the WAR file of WAR projects in the Docker image.

## 1.0.1 - 2017-09-19

### Commits
- [LPS-74785]: Avoid failing the build if Git throws an error (43aeb3737d)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.

### Description
- [LPS-74785]: Avoid failing the build in the case of a Git error.