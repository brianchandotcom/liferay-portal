# Liferay Gradle Plugins Poshi Runner Change Log

## 1.0.1 - 2015-04-29

### Commits
- [LPS-55187]: Bump version (ff2f8d72a1)
- [LPS-55187]: OpenCV version as parameter (6ab3fc3ecc)
- [LPS-55187]: Use Gradle test task (8a35113133)
- [LPS-55220]: Simplify (93c943ba30)
- [LPS-55220]: Misuse? See sdk (2a3e197eec)
- [LPS-55220]: Use conf mapping instead of transitive attribute (42bfd15e29)

## 1.0.2 - 2015-05-27

### Commits
- [LPS-55187]: increment (8f5151a893)
- [LPS-55187]: Rename (5a01e9eecb)
- [LPS-55187]: Add task to call PoshiRunnerContext (5fd413aed6)
- [LPS-55187]: Accept any kind of input for the "baseDir" property (dff71e80b3)
- [LPS-55187]: Add task to call PoshiRunnerValidation (b8eb0ed241)
- [LPS-55187]: Make constants public, for consistency with other Gradle plugins
(c579faf697)

## 1.0.3 - 2015-06-02

### Commits
- [LRQA-16011]: increment (5b97d876e6)
- [LPS-51081]: Update to Gradle 2.4 (9966e0be8d)

## 1.0.4 - 2015-06-10

### Commits
- [LPS-55187]: Clean test outputs before running it again in order to bypass the
up-to-date check (59fd9e696c)

## 1.0.5 - 2015-07-27

### Commits
- [LPS-51081]: Update to Gradle 2.5 (3aa4c1f053)
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-51081]: Consistency with Gradle 2.4 dependencies (6d4008a98c)
- [LPS-51081]: Move OSDetector to "gradle-util" (dd5435db32)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.

## 1.0.6 - 2015-08-17

### Commits
- [LRQA-17405]: Split the Poshi Runner and the Sikuli configurations, so we can
just manually fill the "poshiRunner" one and leave the other one with its
defaults (5712c65e05)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.7 - 2015-08-18

### Commits
- [LRQA-17405]: add a line break since we declared closure for just this method.
Please apply else where (d7c142f1f0)
- [LRQA-17405]: Look for the Poshi Runner jar file as late as possible, so
"copyLocalPoshi" has a chance to run (12dd686c73)

## 1.0.8 - 2015-08-24

### Commits
- [LPS-55187]: Add Gradle task "evaluatePoshiConsole", which calls
PoshiRunnerConsoleEvaluator (573190f9b3)
- [LPS-55187]: Simplify, and make it resilient if tasks are overwritten
(7567219cf1)

## 1.0.9 - 2015-11-16

### Commits
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.

## 1.0.10 - 2016-03-28

### Commits
- [LPS-62986]: Sample (1f557b19e7)
- [LPS-62986]: Read file in Gradle instead of passing it in the classpath
(ea20877602)
- [LPS-62986]: Add "test.base.dir.name" system property only if dir exists
(699d07b2e4)
- [LPS-62986]: Add shortcut configuration to specify the test names (7c9d71da7d)
- [LPS-62986]: We should not fail if "test.name" is set via properties file
(84c931b905)
- [LPS-62986]: Force UTF-8 (bdd7dda09a)
- [LPS-62986]: More Gradle-like default value (b721d0f086)
- [LPS-62986]: Minor adjustments to the extension object (3e222fc49d)
- [LPS-62986]: Now it is called "test.base.dir.name" (a629c9cce3)
- [LPS-62986]: Suppress warning (7fe0569f6e)
- [LPS-63986]: Copy and rename poshiProperties (864bfa558c)
- [LPS-63986]: Add default value for poshiproperties (297c6521c8)
- [LPS-63986]: Allow more flexibility for poshi-runner.properties (53543d9cc2)
- [LPS-63986]: Rename variable (601d094ae4)
- [LPS-63986]: Use actual value instead of constant (98101457ed)
- [LPS-63986]: Add properties file for projects (aca050b4ee)
- [LPS-63986]: Start Tomcat if TestIntegrationPlugin is applied (a2b21b66d1)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)
- [LPS-61420]: Incorrect tabs and linebreaks in /modules/sdk (955b0fba88)

### Dependencies
- [LPS-62986]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.11 - 2016-06-16

### Commits
- [LPS-65749]: Closures with null owners don't work in Gradle 2.14 (b42316699d)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-65178]: Update Poshi Test to reflect changes in LRDCOM (c141185247)
- [LPS-63986]: Update smoketest (34ebfc4b74)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.12 - 2017-10-02

### Commits
- [LPS-75039]: Manually update gradle-plugins-poshi-runner version (d743371a1f)
- [LPS-67352]: Keep empty line between declaring a variable and using it
(ea421a5ab5)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.0.13 - 2018-02-06

### Commits
- [LPS-77359]: Add plugin to publish Poshi Runner resources artifacts
(d91e3c8546)
- [LPS-77359]: No need to expose the project in the extension object
(d4ab4983ec)
- [LPS-77359]: Use Gradle built-in method (141f8f703d)
- [LPS-77359]: Enforce semantic versioning (9fa19de1bf)
- [LPS-67573]: Make methods private to reduce API surface (57a1832e9c)

## 2.0.0 - 2018-02-06

### Commits
- [LPS-77359]: Remove marker file code (00e2c6d0ca)

## 2.0.1 - 2018-02-18

### Commits
- [LPS-78096]: (61c48c970b)

## 2.1.0 - 2018-02-18

### Commits
- [LPS-78096]: Sort (5a4003a45b)

### Description
- [LPS-78096]: Use the `poshiRunnerResources.rootDirName` property to specify a
root directory name in the JAR files generated by the Poshi Runner Resources
plugin.

## 2.1.1 - 2018-02-28

### Commits
- [LPS-78266]: Create and add poshiRunnerResources configuration if it does not
exist (7845d06370)
- [LPS-78266]: (aee74182b0)

## 2.2.0 - 2018-02-28

### Commits
- [LPS-78266]: Reuse constant (664f5393a1)

### Description
- [LPS-78266]: Add the `poshiRunnerResources` configuration to the classpath for
the Poshi Runner plugin.

## 2.2.1 - 2018-03-07

### Commits
- [LPS-78537]: Ensure abbreviated hash is unique (8eb67f81d5)
- [LPS-78537]: (efec6d94fc)

### Description
- [LPS-78537]: Shorten the name of the JAR file generated by the Poshi Runner
Resources plugin by using the abbreviated SHA-1.

## 2.2.2 - 2018-05-02

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Partial revert of b739c8fcdc5d1546bd642ca931476c71bbaef1fb
(02ca75b1da)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 2.2.3 - 2018-05-02

### Commits
- [LPS-80394]: Remove Clock, since it doesn't exist in newer versions of Gradle
(14e3d1f2b1)
- [LPS-80394]: No need to retain the Git repository object (5c227d5236)

### Description
- [LPS-80394]: Avoid out-of-memory errors when running on large Git
repositories.

## 2.2.4 - 2018-05-16

### Commits
- [LPS-80950]: Stop using RepositoryCache since it will hold reference to git
repositories and later cause OOM (8784dc3caf)

### Description
- [LPS-80950]: Avoid out-of-memory errors when running on large Git
repositories.

## 2.2.5 - 2018-07-04

### Commits
- [LPS-77359]: Short hash (a8a59c5eef)
- [LPS-77359]: Invoke Git directly (3ba956b46f)
- [LPS-77359]: Remove jgit to avoid out-of-memory errors (571043d94c)

### Description
- [LPS-77359]: Avoid out-of-memory errors when running on large Git
repositories by invoking Git directly.

## 2.2.6 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 2.2.7 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.2.8 - 2018-11-28

### Commits
- [LPS-87890]: drop hours, minutes, and seconds (d089b938a9)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

## 2.2.9 - 2019-04-07

### Commits
- [LRCI-65]: Add ability to override system properties from the command line
(432821e6b9)
- [LRCI-65]: Add executePQLQuery task (b17fa46a46)

## 2.2.10 - 2019-05-13

### Commits
- [LPS-94947]: use setTestClassesDirs instead of the deprecated method
setTestClassesDir (474da1e76d)

## 2.2.11 - 2019-05-16

### Commits
- [LRCI-264]: Set poshi runner version to current stable release (1.0.222)
(a27af08853)

## 2.2.12 - 2019-05-20

### Commits
- [LRCI-264]: Update to poshi runner 1.0.229 (5af347452a)

## 2.2.13 - 2019-06-10

### Commits
- [LRCI-350]: Update to poshi runner 1.231 (138d83aec7)

## 2.2.14 - 2019-07-22

### Commits
- [LRCI-473]: Update com.liferay.poshi.runner to 1.0.232 (c255ece1db)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.2.15 - 2019-10-08

### Commits
- [LRCI-642]: Update default poshi runner to 1.0.239 (e5435ab4b2)

## 2.2.16 - 2019-10-29

### Commits
- [LRCI-715]: Update default poshi runner to 1.0.242 (bb68d50b9f)

## 2.2.17 - 2019-11-11

### Commits
- [LRCI-766]: Update poshi runner to 1.0.243 (f62428024c)

## 2.2.18 - 2020-01-06

### Commits
- [LRCI-902]: Update default poshi runner version to 1.0.246 (40cd41289f)
- [LPS-100515]: Update plugins Gradle version (448efac158)

## 2.2.19 - 2020-02-03

### Commits
- [LRCI-1003]: Update poshi runner version to 1.0.249 (75a965a44e)