# Liferay Gradle Plugins App Javadoc Builder Change Log

## 1.2.2 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.2.1 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-84119]: Move variable declaration inside if/else statement for better
performance (8dd499456b)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.2.0 - 2016-10-12

### Commits
- [LPS-68666]: Update readmes (9bc6b579c1)

### Description
- [LPS-68666]: Add the ability to define which subprojects to include in the API
documentation of the app by using the `appJavadocBuilder.subprojects` property.

## 1.1.1 - 2016-10-12

### Commits
- [LPS-68666]: Allow to define which subprojects to include in "appJavadoc"
(ecf12dcbab)
- [LPS-68506]: Reduce choppiness in README method description (071fecd122)
- [LPS-68506]: Edit README method descriptions to follow closely with Liferay's
Javadoc guidelines (499ffd66fc)

## 1.1.0 - 2016-10-04

### Commits
- [LPS-68506]: Update README (7506287284)

### Description
- [LPS-68506]: Add the ability to exclude subprojects from the API documentation
by using the `appJavadocBuilder.onlyIf` property.
- [LPS-67573]: Make most methods private in order to reduce API surface.

## 1.0.2 - 2016-10-04

### Commits
- [LPS-67573]: Export package (1aeb1dc458)
- [LPS-67573]: Make methods private to reduce API surface (7135ed85f2)
- [LPS-68506]: Add logging (8844dcef1f)
- [LPS-68506]: Add option to exclude subprojects from "appJavadoc" (5aa709a656)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Convert gradle-plugins-app-javadoc-builder sample into a smoke
test (864be6a959)
- [LPS-67658]: Configure GradleTest in gradle-plugins-app-javadoc-builder
(9593e1f8ae)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-66709]: Edit README (cd7c0f0446)
- [LPS-66709]: README for gradle-plugins-app-javadoc-builder (5c68bff8ea)

## 1.0.1 - 2016-08-10

### Commits
- [LRDOCS-2841]: Avoid crashing if subproject "javadoc" is not a Javadoc task
(5416a3c23b)