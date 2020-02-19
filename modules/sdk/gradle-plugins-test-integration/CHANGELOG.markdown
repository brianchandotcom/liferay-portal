# Liferay Gradle Plugins Test Integration Change Log

## 3.0.1 - 2020-01-07

### Commits
- [LPS-102243]: Remove from Gradle plugins (b713dd7982)
- [LPS-102243]: Partial revert (b5ee7e9c16)
- [LPS-102243]: Remove portal-test-integration and all references to it from
build and classpath (c2efa6c7f2)
- [LPS-100515]: Update plugins Gradle version (448efac158)

## 2.4.7 - 2019-08-27

### Commits
- [LPS-100491]: Add SuppressWarnings (652c8a75c1)
- [LPS-100491]: Remove JMX Java options (1cdf041543)
- [LPS-84119]: Do not declare var (85dc5fdf91)

## 2.4.6 - 2019-06-21

### Commits
- [LPS-96247]: Update (7385039e86)
- [LPS-96247]: Source formatting (aae01ea1d4)
- [LPS-96247]: Migrate away from deprecated SourceSetOutput.getClassesDir
(5b0f9860d5)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.4.5 - 2019-06-15

### Commits
- [LPS-95455]: Update testModules configuration (add org.apache.aries.jmx.core)
(47712a89e5)

## 2.4.4 - 2019-06-12

### Commits
- [LPS-95455]: Update testModules configuration (use
com.liferay.arquillian.extension.junit.bridge.connector) (879677deda)
- [LPS-85997]: Upgrade Tomcat to 9.0.17 (c59adf8378)

## 2.4.3 - 2019-05-08

### Commits
- [LPS-95261]: Set JVM arguments for Arquillian (fc9bafcc6c)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

## 2.4.2 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.4.1 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Fix logic (ad3070aa14)
- [LPS-86589]: Fix gradle tests (9acc287650)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 2.4.0 - 2018-10-17

### Description
- [LPS-86447]: Add the ability to set environment variables for launching
Tomcat.

## 2.3.2 - 2018-10-17

### Commits
- [LPS-86447]: semver (529110d199)
- [LPS-86447]: add environment input to BaseAppServerTask (69db40ea25)
- [LPS-84119]: Move variable declaration inside if/else statement for better
performance (8dd499456b)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 2.3.1 - 2018-08-15

### Commits
- [LPS-83790]: Update Tomcat from 9.0.6 to 9.0.10 due to CVE-2018-1336
(5082b1c803)

### Description
- [LPS-83790]: Update the `setUpTestableTomcat.zipUrl` property to
`http://archive.apache.org/dist/tomcat/tomcat-9/v9.0.10/bin/apache-tomcat-9.0.10.zip`.

## 2.3.0 - 2018-07-17

### Description
- [LPS-83520]: Add the ability to set the application server host name by
setting the property `testIntegrationTomcat.hostName`. The default value is
`localhost`.

## 2.2.2 - 2018-07-17

### Commits
- [LPS-83520]: Add ability to set the application server hostName (4db28fab93)
- [LPS-74171]: Update Tomcat version from 8.0.32 to 9.0.6 (236900f929)

## 2.2.1 - 2018-03-21

### Commits
- [LPS-78750]: add missing directory (9f525bc6e2)
- [LPS-78750]: tomcat version doesn't matter just setup skeleton to execute task
(b2af5925d3)
- [LPS-78750]: download and copy bundle for test (ed7047eaf9)
- [LPS-78750]: Fix Gradle test (6d8223f990)
- [LPS-78750]: Simplify (6d1b3f8272)
- [LPS-78750]: Exit fast (206a0cbf17)
- [LPS-78750]: add test (62269ea547)
- [LPS-78750]: move to setUpTestableTomcat action (e93f6423cc)
- [LPS-78750]: Make tomcat scripts executable (fd7e617210)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Partial revert of b739c8fcdc5d1546bd642ca931476c71bbaef1fb
(02ca75b1da)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

### Description
- [LPS-78750]: Automatically set Tomcat's `.sh` files when executing the
`setUpTestableTomcat` task.

## 2.2.0 - 2017-10-17

### Description
- [LPS-75239]: Automatically deploy version 3.0.0 of [Liferay Portal Test] and
[Liferay Portal Test Integration] when executing the `setUpTestableTomcat` task.
- [LPS-75239]: Increase the default application server check timeout from 5 to
10 minutes.
- [LPS-75239]: Disable the `copyTestModules` task's up-to-date check.
- [LPS-75239]: Disable the `copyTestModules` task's behavior of not checking if
a file already exists in the `osgi/test` directory before copying it from the
`testModules` configuration by setting the property
`testIntegrationTomcat.overwriteCopyTestModules` to `false`.

## 2.1.3 - 2017-10-17

### Commits
- [LPS-75239]: Disable up-to-date check of "copyTestModules" task (ca65aac88e)
- [LPS-75239]: Overwrite old test modules everywhere but in Liferay modules
(170fbbe0d7)
- [LPS-75239]: Allow "copyTestModules" task to overwrite old test modules
(c6e53c32e8)
- [LPS-75239]: Add portal-test/integration as default "testModules" (e464f29b6f)
- [LPS-75239]: Increase check timeout (995d4360f7)

## 2.1.2 - 2017-09-23

### Commits
- [LPS-71117]: Bypass https://github.com/gradle/gradle/issues/2343 (b2715307af)

### Description
- [LPS-71117]: Bypass the following Gradle 4.0
[issue](https://github.com/gradle/gradle/issues/2343).

## 2.1.1 - 2017-09-20

### Commits
- [LPS-71117]: Test with newer Gradle versions (2250353541)
- [LPS-71117]: Add Gradle test (15577109d1)
- [LPS-71117]: Fix gradle-plugins-test-integration for Gradle 4.0+ (c5b91392ff)

### Description
- [LPS-71117]: Add support for Gradle 4.0 and newer.

## 2.1.0 - 2017-07-24

### Commits
- [LPS-73353]: Prevent sorting (86c849a213)
- [LPS-73353]: Since it's a file, rename property to "File" (ba15397c9d)
- [LPS-73353]: Allow to specify closures and callables as values (4cd791b62f)

### Description
- [LPS-73353]: Add the ability to configure the JaCoCo Java Agent in the
`setenv.sh` file during the execution of a `SetUpTestableTomcatTask` instance.

## 2.0.1 - 2017-07-24

### Commits
- [LPS-73353]: Add jacoco support for tomcat setup (gradle), jacoco agent must be
the first java agent (fc2689517f)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.

## 2.0.0 - 2017-07-12

### Description
- [LPS-73525]: Add the ability to configure an AspectJ weaver in the `setenv.sh`
file during the execution of a `SetUpTestableTomcatTask` instance.
- [LPS-73525]: Automatically set the `JPDA_ADDRESS` environment variable to
`8000` in the `setenv.sh` file during the execution of a
`SetUpTestableTomcatTask` instance.
- [LPS-73525]: Remove the `SetUpTestableTomcatTask`'s `catalinaOptsReplacements`
property.

## 1.3.0 - 2017-07-12

### Commits
- [LPS-73525]: Allow closures as values for AspectJ properties (5638b10f07)
- [LPS-73525]: This is useless now, better remove it (65ea06a900)
- [LPS-73525]: SF, group changes to setenv to a new method like what we did in
ant script (fe22939acb)

## 1.2.1 - 2017-07-12

### Commits
- [LPS-73525]: Add AspectJ settings to gradle SetUpTestableTomcatTask, make it
consistent with setting logic in ant task "setup-testable-tomcat" (1eab66c85c)
- [LPS-73525]: Add jpda settings to gradle SetUpTestableTomcatTask, make it
consistent with setting logic in ant task "setup-testable-tomcat" (a069b6e7af)
- [LPS-73525]: Remove/Deprecated unused code (a4ac45fa29)

### Dependencies
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.2.0 - 2017-05-11

### Description
- [LPS-72365]: Add the ability to deploy additional OSGi modules during the
execution of a `SetUpTestableTomcatTask` instance.
- [LPS-72365]: Automatically deploy version 1.1.7 of Apache Aries JMX Core (and
its transitive dependencies) when executing the `setUpTestableTomcat` task.

## 1.1.1 - 2017-05-11

### Commits
- [LPS-72365]: Fix "stopTestableTomcat.deleteTestModules" (7dffa0af54)
- [LPS-72365]: Deploy Aries JMX if not present (5289598b1a)
- [LPS-72365]: Allow to pass additional modules to deploy (c4b23f0e50)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)

## 1.1.0 - 2016-12-01

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-69492]: Add the source directories of the `testIntegration` source set to
the IML file generated by the `idea` task.

## 1.0.10 - 2016-12-01

### Commits
- [LPS-69492]: Test if Eclipse and Idea contain the "testIntegration" dirs
(d2eb1b103f)
- [LPS-69492]: Add "testIntegration" dirs to Idea (5502af827a)
- [LPS-67573]: Export packages (c600f71ec3)
- [LPS-67573]: Use logging placeholder (db2f8c8c8a)
- [LPS-67573]: Rename wrong variable (e75f56a748)
- [LPS-67573]: Use task's logger (7e7c8c4f9e)
- [LPS-67573]: Make methods private to reduce API surface (e167a2273c)
- [LPS-67573]: Move internal classes to their own packages (cd5e0a4fa4)
- [LPS-67352]: SF, enforce empty line after finishing referencing variable
(4ff2bb6038)
- [LPS-67352]: Keep empty line between declaring a variable and using it
(ea421a5ab5)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

## 1.0.9 - 2016-07-08

### Commits
- [LPS-67048]: Wait some more after Tomcat is unreachable (b68a554683)
- [LPS-67048]: Wait until localhost:8080 is no longer reachable (fe11392c13)
- [LPS-67048]: Move check properties to base class (ae865b9783)

## 1.0.8 - 2016-06-29

### Commits
- [LPS-66873]: Add warning if unable to replace string in setenv.* (0a71c0f25a)
- [LPS-66873]: Rename everything else to "SetUp" (23cf7f7c5c)
- [LPS-66873]: Rename tasks to "SetUp*Task" (441641719b)
- [LPS-66873]: Temporarily rename tasks to "SetUp*Task1" (6eac9c18bd)
- [LPS-66873]: Add option to replace strings in setenv.* (9ab027bd43)
- [LPS-66873]: These methods only throw IOException (2fe3d7c869)

## 1.0.7 - 2016-06-27

### Commits
- [LPS-65749]: "testSrcDirs" property is deprecated and not used by Gradle
(4437123328)

## 1.0.6 - 2016-06-16

### Commits
- [LPS-65749]: Closures with null owners don't work in Gradle 2.14 (b42316699d)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.5 - 2016-05-26

### Commits
- [LPS-66139]: Check console to find out if Tomcat has started (29d00ef89d)
- [LPS-66139]: stderr is already redirected to stdout (9e1dea1521)
- [LPS-66139]: Rename for more clarity (8d9b4389a1)
- [LPS-65810]: Gradle plugins aren't used in OSGi, no need to export anything
(83cdd8ddcd)

## 1.0.4 - 2016-05-09

### Commits
- [LPS-65067]: Use and configure the new task (82371b2355)
- [LPS-65067]: Implement the new interface (05e10d5af5)
- [LPS-65067]: Add task to remove test modules in osgi/modules at the end
(13804f97d8)
- [LPS-65067]: Don't overwrite test modules in osgi/modules if already exist
(5c0891a39f)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)

### Dependencies
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.3 - 2016-03-23

### Commits
- [LPS-64586]: liferayHome is required only if deleteLiferayHome == true
(40ff8594a2)
- [LPS-64586]: Prevent NPE if the file is a callable which returns null
(c8cf48659c)
- [LPS-64586]: No reason to use closures here (ea19b57ef9)
- [LPS-64586]: Add task default values for easier plugin reuse (dfb12bed19)
- [LPS-64586]: Cleanup static list after stopping the app server (ee4f97c2be)
- [LPS-61420]: Move leading space to previous append value (77b3b89217)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-61420]: Source formatting (0df5dd83d6)

## 1.0.2 - 2016-02-11

### Commits
- [LPS-63166]: Look for test Java files instead of classes (5d18ed8dca)
- [LPS-63166]: Extract method (0f3746d132)
- [LPS-63166]: Search inside any src directory, not just the first one
(683ed79c13)
- [LPS-63166]: Categorize tasks into groups (c4956e12e1)
- [LPS-63166]: Add task descriptions (72c8ba11ba)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-62942]: Explicitly list exported packages for correctness (f095a51e25)

## 1.0.1 - 2016-01-27

### Commits
- [LPS-62589]: Write one EOL before starting new commands (5975c0c222)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)
- [LPS-62111]: Sort (2e70f94148)
- [LPS-62111]: Add @Override (7d6efeca5a)