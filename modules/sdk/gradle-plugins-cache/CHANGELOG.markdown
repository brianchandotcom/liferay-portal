# Liferay Gradle Plugins Cache Change Log

## 1.0.16 - 2018-11-20

### Commits
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)
- [LPS-85609]: Remove org.gradle.util.Clock (removed in Gradle 4.10.2)
(39fb028ccf)

## 1.0.15 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.0.14 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 1.0.13 - 2018-09-12

### Commits
- [LPS-65845]: Fix writeParentThemesDigest for private themes (447c5f59ad)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-67352]: Remove unnecessary parentheses (c64cf3c14e)
- [LPS-67352]: SF, enforce empty line after finishing referencing variable
(4ff2bb6038)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.0.12 - 2016-08-09

### Commits
- [LPS-67503]: Use task logger (68eba0c8a8)
- [LPS-67503]: Add task to write a digest file (4f88dbf8e8)
- [LPS-67503]: Move digest generation method to util class (93be721fbc)

## 1.0.11 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.10 - 2016-06-14

### Commits
- [LPS-65845]: The info log is too verbose (4179dc7e2b)
- [LPS-65845]: Add unit test (5a656686c8)
- [LPS-65845]: Exclude Git ignored files from task cache test files list
(9016814804)

## 1.0.9 - 2016-06-02

### Commits
- [LPS-63943]: Make the digest file name public (562c211baf)
- [LPS-63943]: Extract util methods to get cache task names (203830fcdc)
- [LPS-63943]: Add task to force refresh the .digest file (374d4171b0)
- [LPS-63943]: Extract method (931a0bfe13)
- [LPS-63943]: toString() for task cache (copied from Gradle's task)
(b5160513f8)

## 1.0.8 - 2016-05-23

### Commits
- [LPS-65810]: Gradle plugins aren't used in OSGi, no need to export anything
(83cdd8ddcd)
- [LPS-66004]: Update sample (8296ba176c)
- [LPS-66004]: Debug logging in Gradle is too verbose (ba62bc8b1f)
- [LPS-66004]: Add command line argument to ignore the task cache (9207219be2)

## 1.0.7 - 2016-05-19

### Commits
- [LPS-65845]: .DS_Store is a file, not a directory (26ea5defea)

## 1.0.6 - 2016-05-16

### Commits
- [LPS-65845]: Add support to skip external project tasks based on their path
(1d05deec50)
- [LPS-65845]: Exclude .DS_Store from the task cache digest calculation
(64ef37bf57)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-63943]: Replace only fixed versions (1e1c97c315)

### Dependencies
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.5 - 2016-02-22

### Commits
- [LPS-63440]: Hugo SF (2ab487b98e)
- [LPS-63440]: No need to have it public (b6196d7ebe)
- [LPS-63440]: Sort all test files, not only the ones we extract from a dir
(5c9feb5f3f)

## 1.0.4 - 2016-02-09

### Commits
- [LPS-63062]: Add more logging (583f4752ef)
- [LPS-63062]: Modified dates are not reliable in Git, use hashes instead
(ab2e0f13ae)
- [LPS-63062]: No need to be final (e66008ac87)
- [LPS-63062]: Add dependency, so we can call the "saveCache" task by itself
(88f6dfb34b)
- [LPS-63062]: Add task descriptions (1fea81a835)
- [LPS-63062]: Never used (23e3aa3c8b)
- [LPS-63062]: Rename to match other classes that implement PatternFilterable
(1983a81068)
- [LPS-62833]: Update build-buildscript.gradle (56106ab47b)
- [LPS-62942]: Explicitly list exported packages for correctness (f095a51e25)
- [LPS-61420]: Source formatting (e74909bba7)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)
- [LPS-59672]: Patterns ending with "/" have "**" automatically appended
(c6b28a4866)

### Dependencies
- [LPS-63062]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.3 - 2015-12-17

### Commits
- [LPS-61070]: task.dependsOn may contain the task object or the task name
(9d7802d8de)

## 1.0.2 - 2015-11-16

### Commits
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)

## 1.0.1 - 2015-09-15

### Commits
- [LPS-58587]: Add option to force the use of the cache (88b576327d)
- [LPS-58587]: Save cache in a directory, not in a zip file (6e96fe323a)

### Dependencies
- [LPS-58587]: Update the com.liferay.gradle.util dependency to version 1.0.21.