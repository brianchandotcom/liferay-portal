# Liferay Gradle Utilities Change Log

## 1.0.34 - 2019-06-21

### Commits
- [LPS-96247]: Move common logic to gradle-util (3fdf0aa450)
- [LPS-88524]: Fix findbugsMain (gw :sdk:gradle-util:findbugsMain) (d6b509b10e)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

## 1.0.33 - 2018-11-19

### Commits
- [LPS-87466]: Source formatting (ae184c8556)
- [LPS-87466]: encode all directory segements separately (4cac4ff4ad)

## 1.0.32 - 2018-11-16

### Commits
- [LPS-87466]: encode urls before creating cache directory (c95a68a794)
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)

## 1.0.31 - 2018-08-30

### Commits
- [LPS-84094]: Move reusable logic to gradle-util (7388cfa774)

## 1.0.30 - 2018-08-28

### Commits
- [LPS-84094]: Move gradle-ext.properties logic to gradle-util (b557e7da2c)
- [LPS-81555]: Use enhanced for-loop when iterating over arrays when possible
(bc39eb1e0f)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)

## 1.0.28 - 2017-07-13

### Commits
- [LPS-73584]: Allow to provide basic authentication when downloading a file
(03aaa8d659)

## 1.0.27 - 2017-06-08

### Commits
- [LPS-72914]: Download HTTPS URLs through the Liferay mirror (89ebd98d28)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

## 1.0.26 - 2016-06-16

### Commits
- [LPS-65749]: Closures with null owners don't work in Gradle 2.14 (b42316699d)
- [LPS-65749]: No need to use closures here (749344ac88)
- [LPS-65810]: Gradle plugins aren't used in OSGi, no need to export anything
(83cdd8ddcd)

## 1.0.25 - 2016-04-17

### Commits
- [LPS-65086]: Logging (a636aa9a90)
- [LPS-65086]: Copy 998ede01af55abed36bb8a5f1d2bd78d604a2216 to Gradle
(dce2286b8b)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update build.gradle in plugins that use gradle-util (9ab64f3eb7)
- [LPS-62942]: Explicitly list exported packages for correctness (f095a51e25)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

## 1.0.23 - 2015-11-03

### Commits
- [LPS-60153]: First download to a tmp file, then rename (76ce795917)
- [LPS-60153]: "verbose" does not work when Ant is called from Gradle
(c6891c0886)
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)

## 1.0.22 - 2015-10-07

### Commits
- [LPS-58516]: Rename (c29a9d7fa9)
- [LPS-58516]: Add util method to create one or more "classpath jars" from a
files list (abe9fbb7a2)

## 1.0.21 - 2015-09-15

### Commits
- [LPS-58587]: Find the last modified time by looking at the whole subtree
(468bdf4522)

## 1.0.20 - 2015-09-14

### Commits
- [LPS-58587]: Fix "isUpToDate" method and add support for directories
(bc08d5a2df)

## 1.0.19 - 2015-09-08

### Commits
- [LPS-58467]: The "mirrors-get" cache is not thread-safe (17387f1e29)
- [LPS-58256]: Also update gradle's ArrayUtil (f9c8e3a23c)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

## 1.0.18 - 2015-08-24

### Commits
- [LPS-51081]: Add support for closures and callables in the "toString" method
(0fac0410dd)

## 1.0.17 - 2015-08-13

### Commits
- [LPS-51081]: Utility method to convert an object to Integer (9f3f08e811)
- [LPS-51081]: Utility methods to read properties from a project (6c2eb1ffbe)

## 1.0.16 - 2015-08-07

### Commits
- [LPS-51081]: Copy RenameDependencyClosure to gradle-util, so it can be used by
Gradle scripts (d6992be0e0)

## 1.0.15 - 2015-07-28

### Commits
- [LPS-51081]: The "isUpToDate" method should just return false if the files/dirs
do not exist (812aaf9d5d)

## 1.0.14 - 2015-07-23

### Commits
- [LPS-51081]: Wrap Ant "uptodate" in an utility method for Gradle scripts
(c148ac89c2)
- [LPS-51081]: Update to Gradle 2.5 (3aa4c1f053)

## 1.0.12 - 2015-07-02

### Commits
- [LPS-51081]: Replace previously downloaded file (29ea6ac0dc)
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-51081]: Module scripts use Gradle (and not Ant) to unzip files, so the
plugin should do the same (8b6c7d9dfa)

## 1.0.11 - 2015-07-01

### Commits
- [LPS-51081]: Util method to set value for a non-existent extra property
(4777d78ab1)
- [LPS-51081]: Gradle task to concatenate files (2404c4d12d)
- [LPS-51081]: Create destination dir before copying from mirror cache dir
(1dd22d231e)
- [LPS-51081]: Util classes to manipulate paths while copying/extracting via
Gradle "copy" (24dc6af005)

## 1.0.10 - 2015-06-25

### Commits
- [LPS-51081]: Better API (11aa374bea)

## 1.0.9 - 2015-06-23

### Commits
- [LPS-51081]: Simplify and return the downloaded file (8663c9c27d)

## 1.0.8 - 2015-06-20

### Commits
- [LPS-51081]: Consistency with Gradle 2.4 dependencies (6d4008a98c)
- [LPS-51081]: Move to gradle-util (461215069b)
- [LPS-51081]: Add switch in "initGradle" to temporarily ignore missing
dependencies (bc06326042)
- [LPS-51081]: "startTestableTomcat" Gradle task (94f35577dc)
- [LPS-51081]: Move OSDetector to "gradle-util" (dd5435db32)
- [LPS-51081]: "setupArquillian" Gradle task (f34d201a43)
- [LPS-51081]: Port "mirrrors-get" logic to Gradle (f024ab4680)
- [LPS-51081]: "setupTestableTomcat" Gradle task (837bc67e5b)

## 1.0.7 - 2015-06-18

### Commits
- [LPS-56506]: Gradle util methods (ff2569ff68)

## 1.0.6 - 2015-06-10

### Commits
- [LPS-55187]: prepare for next version (b0834723e3)
- [LPS-55187]: Clean test outputs before running it again in order to bypass the
up-to-date check (59fd9e696c)

## 1.0.5 - 2015-06-02

### Commits
- [LPS-51081]: A configuration is not empty if it inherits from another one
(214f50b28a)
- [LPS-51081]: Update to Gradle 2.4 (9966e0be8d)

## 1.0.4 - 2015-05-29

### Commits
- [LPS-51081]: Support for compiling unit and integration test classes
(9b6abd2e6d)
- [LPS-51081]: as used (4b33849d50)

## 1.0.3 - 2015-05-29

### Commits
- [LPS-55187]: Copy dependencies to "lib" folder in order to match Bnd
"Include-Resource" instructions (3f13d47b3e)
- [LPS-51081]: Change OSGi jar via direct-deploy (31dd3622df)

## 1.0.2 - 2015-05-18

### Commits
- [LPS-51081]: Fix mapping from "import.shared" to Gradle project (7fbf13c406)

## 1.0.1 - 2015-05-04

### Commits
- [LPS-51081]: Move configuration into task (e4d681d6eb)