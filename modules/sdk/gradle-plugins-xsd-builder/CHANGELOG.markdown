# Liferay Gradle Plugins XSD Builder Change Log

## 1.0.8 - 2019-11-27

### Commits
- [LPS-100515]: Update README.markdown (694b3791de)
- [LPS-100515]: Update plugins Gradle version (448efac158)
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 1.0.7 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.0.6 - 2018-11-16

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
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67352]: SF, enforce empty line after finishing referencing variable
(4ff2bb6038)
- [LPS-67658]: Copy transitive dependencies in a dir used by GradleTest
(4bff5b3eaa)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-67658]: Check if auto-generated XBean jar exists (b6ca32a06a)
- [LPS-67658]: Convert gradle-plugins-xsd-builder into a smoke test (73d3aee338)
- [LPS-67658]: Configure GradleTest in gradle-plugins-xsd-builder (37bdecd59d)
- [LPS-66709]: Edit Gradle plugin README files for Service Builder, TLD
Formatter, WSDD Builder, WSDL Builder, XML Formatter, and XSD Builder.
(c2e7e02b4a)
- [LPS-66709]: README for gradle-plugins-xsd-builder (42b3647d9b)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.0.5 - 2016-06-18

### Commits
- [LPS-66709]: Add missing task descriptions (7705d2435d)
- [LPS-66709]: Use task description from README (81a5c3364c)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.3 - 2015-11-16

### Commits
- [LPS-60463]: Remove version from the XSD jar (f55bc3df38)
- [LPS-60463]: Leverage the archivesBaseName property (4ef3a1f10a)

## 1.0.2 - 2015-11-15

### Commits
- [LPS-60463]: Fix sample (3dfa0f0be1)
- [LPS-60463]: Set task default when "war" is applied (f1093e2c17)
- [LPS-60463]: Support callable as destinationDir, so webAppDir can be changed
after applying the plugin and the default value will stay updated (3d80afbb70)
- [LPS-60463]: Set task defaults (51c413f60e)
- [LPS-60463]: We use the "compile" configuration, so we apply JavaPlugin for
consistency (f34425f824)
- [LPS-60463]: Use util method (bc6cb5a7b7)
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-60463]: Update the com.liferay.gradle.util dependency to version 1.0.23.
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.1 - 2015-07-27

### Commits
- [LPS-51081]: Update to Gradle 2.5 (3aa4c1f053)
- [LPS-51801]: Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081]: Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-51081]: Consistency with Gradle 2.4 dependencies (6d4008a98c)
- [LPS-55187]: Use only 1.0.6 (f63748d15a)
- [LPS-51081]: use only 1.0.5 (4d9c09dfce)
- [LPS-51081]: Update to Gradle 2.4 (9966e0be8d)
- [LPS-51081]: Consistency (7c7d8676d9)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.