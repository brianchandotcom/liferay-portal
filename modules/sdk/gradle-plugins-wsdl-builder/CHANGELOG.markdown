# Liferay Gradle Plugins WSDL Builder Change Log

## 1.0.1 - 2015-05-04

### Commits
- [LPS-51081]: Increment to prepeare for next versions (71f784ba94)
- [LPS-51081]: Use com.liferay.gradle.util 1.0.1 release (86c44a6095)

## 1.0.2 - 2015-07-27

### Commits
- [LPS-51081]: Update to Gradle 2.5 (3aa4c1f053)
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-51081]: Consistency with Gradle 2.4 dependencies (6d4008a98c)
- [LPS-55187]: Use only 1.0.6 (f63748d15a)
- [LPS-51081]: use only 1.0.5 (4d9c09dfce)
- [LPS-51081]: Update to Gradle 2.4 (9966e0be8d)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.

## 1.0.3 - 2015-11-14

### Commits
- [LPS-60346]: Fix sample (32d60d242f)
- [LPS-60346]: Set task default when "war" is applied (ded544863a)
- [LPS-60346]: Set task defaults (9265575925)
- [LPS-60346]: We use the "compile" configuration, so we apply JavaPlugin for
consistency (e23e3a34f3)
- [LPS-60346]: Add Gradle input annotations (f31921ea73)
- [LPS-60346]: Use util method (f1236a92c2)
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)
- [LPS-59405]: Source formatting (0a6c07a7e6)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-60346]: Update the com.liferay.gradle.util dependency to version 1.0.23.
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.5 - 2016-04-26

### Commits
- [LPS-65387]: Reuse configuration variable (2afb8a47ed)
- [LPS-65387]: Add option to just generate the Java files from the WSDLs
(e8035c897e)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.6 - 2016-04-27

### Commits
- [LPS-65387]: Gradle method works even when we extend from the configuration
(c4de576b00)

## 1.0.7 - 2016-04-30

### Commits
- [LPS-65490]: Clean destination dir before calling WSDL2Java (0f0ff7b270)
- [LPS-65490]: Save Java files in OSGI-OPT/src (601f898b80)
- [LPS-65490]: Convert "buildWSDL" to a SourceTask, so we can add excludes
(7bbb339d6e)
- [LPS-65425]: Add configuration options for WSDL2Java (26d7b60fb6)

## 1.0.8 - 2016-06-14

### Commits
- [LPS-66620]: Add option to include the WSDL files in the jar (bae4607dc1)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)

## 1.0.9 - 2016-06-16

### Commits
- [LPS-65749]: Closures with null owners don't work in Gradle 2.14 (b42316699d)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.10 - 2017-01-10

### Commits
- [LPS-67573]: Make methods private to reduce API surface (3c10814db2)
- [LPS-70060]: Fix issue with Gradle 3.3 (e370159f1c)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Copy transitive dependencies in a dir used by GradleTest
(4bff5b3eaa)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-67658]: Convert gradle-plugins-wsdl-builder into a smoke test
(a5f4376fce)
- [LPS-67658]: Configure GradleTest in gradle-plugins-wsdl-builder (41b163f211)
- [LPS-66709]: Edit Gradle plugin README files for Service Builder, TLD
Formatter, WSDD Builder, WSDL Builder, XML Formatter, and XSD Builder.
(c2e7e02b4a)
- [LPS-66709]: README for gradle-plugins-wsdl-builder (ea311c8850)

## 2.0.0 - 2017-01-10

### Description
- [LPS-67573]: Make most methods private in order to reduce API surface.
- [LPS-70060]: Fix compatibility issue with Gradle 3.3.

## 2.0.1 - 2017-10-16

### Commits
- [LPS-75273]: Add Gradle test (5a066b7cfd)
- [LPS-75273]: Delete the destination dir only if it is a temporary dir
(43d6b4b00c)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

### Description
- [LPS-75273]: Avoid deleting the destination directory of the `buildWSDL` task
if the property `buildLibs` is `false`.

## 2.0.2 - 2018-11-16

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
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LRDOCS-4319]: Update Gradle plugin README intro descriptions for consistency
(72104bde58)
- [LRDOCS-4319]: Update Gradle plugin BND descriptions for consistency
(e1495e8e8d)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

## 2.0.3 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.0.4 - 2019-11-27

### Commits
- [LPS-100515]: Update README.markdown (694b3791de)
- [LPS-100515]: Update plugins Gradle version (448efac158)
- [LPS-101026]: Fix typo (c0191a878f)
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.