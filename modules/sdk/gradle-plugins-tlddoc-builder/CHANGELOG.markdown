# Liferay Gradle Plugins TLDDoc Builder Change Log

## 1.0.1 - 2016-02-06

### Commits
- [LRDOCS-2147]: Update sample (373967f738)
- [LRDOCS-2147]: Add task to copy TLDDoc images and other resources (a5dcce2f91)
- [LRDOCS-2147]: Better description, more similar to "javadoc" (fd8ffa9818)
- [LRDOCS-2147]: Add task group (ec70931034)
- [LPS-62942]: Explicitly list exported packages for correctness (f095a51e25)

## 1.0.2 - 2016-05-23

### Commits
- [LRDOCS-2547]: Update sample (41238f030f)
- [LRDOCS-2547]: Add task argument for "-xslt" parameter (4be0253df9)
- [LPS-65810]: Gradle plugins aren't used in OSGi, no need to export anything
(83cdd8ddcd)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)

### Dependencies
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.3 - 2016-06-16

### Commits
- [LPS-65749]: Closures with null owners don't work in Gradle 2.14 (b42316699d)

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.4 - 2016-10-07

### Commits
- [LRDOCS-3023]: Add plugin to create TLD docs for the whole app (afd72ba74b)
- [LPS-67573]: Export packages (aa1ef0065a)
- [LPS-67573]: Make methods private to reduce API surface (8e5fc8cfc2)
- [LPS-68231]: Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658]: Convert gradle-plugins-tlddoc-builder sample into a smoke test
(3c66c8dab1)
- [LPS-67658]: Configure GradleTest in gradle-plugins-tlddoc-builder
(147cb57ab3)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-66709]: Edit README for TLDDoc Builder (f365657ae2)
- [LPS-66709]: README for gradle-plugins-tlddoc-builder (fa15ccee40)

## 1.1.0 - 2016-10-07

### Commits
- [LRDOCS-3023]: Update README with new AppTLDDocBuilder plugin (194c473218)
- [LRDOCS-3023]: Fix README (176a132a23)
- [LRDOCS-3023]: Sync task descriptions with README (a08897506e)
- [LRDOCS-3023]: Consistency (8a0feda331)

### Description
- [LRDOCS-3023]: Add the new plugin `com.liferay.app.tlddoc.builder` to generate
the tag library documentation as a single, combined HTML document for an
application that spans different subprojects, each one representing a different
component of the same application.
- [LPS-67573]: Make most methods private in order to reduce API surface.

## 1.1.1 - 2016-10-12

### Commits
- [LPS-68666]: Allow to define which subprojects to include in "appTLDDoc"
(43d9a59e7f)

## 1.2.0 - 2016-10-12

### Commits
- [LPS-68666]: Update readmes (9bc6b579c1)

### Description
- [LPS-68666]: Add the ability to define which subprojects to include in the tag
library documentation of the app by using the `appTLDDocBuilder.subprojects`
property.

## 1.2.1 - 2017-04-06

### Commits
- [LPS-71591]: Fix readme (a6fb7fea49)
- [LPS-71591]: Update readme (73d12f455b)
- [LPS-71591]: Use XML Resolver by default (6c0bea0e67)
- [LPS-71591]: Allow to specify a custom XML Parser (6ac496e0c1)
- []: Revert "LPS-71591 Temporarily disable Gradle test's TLD schema validation"
(82848390e8)
- [LPS-71591]: Temporarily disable Gradle test's TLD schema validation
(5aafb9144e)
- [LPS-66709]: We say "directory that contains" everywhere else (fbdaeffe5b)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)
- [LPS-70677]: No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259]: Test plugins with Gradle 3.2 (dec6105d3d)

## 1.3.0 - 2017-04-06

### Description
- [LPS-71591]: Add the ability to set a custom XML parser usable by
`ValidateSchemaTask` tasks.
- [LPS-71591]: Automatically configure the `validateTLD` task to use the version
0.12.5 of the [XML Resolver].

## 1.3.1 - 2018-08-30

### Commits
- [LPS-84621]: Set the description for publishPlugins (d6a39138b8)
- [LPS-84621]: Set the displayName for publishPlugins (8ee9cbae3e)
- [LPS-84621]: Rename taglibNode to rootNode (ec97eed84c)
- [LPS-84621]: Rename currentXSDFile to curXSDFile to match existing pattern
(34ffa8333e)
- [LPS-84621]: Avoid unnecessary null checks (2c3b0d6bcc)
- [LPS-84621]: Get the file name from the schema location (a225f5d265)
- [LPS-84621]: Cache nested xsd scan results. (ff437ef4e8)
- [LPS-84621]: Rename (9cc344c3c1)
- [LPS-84621]: This trim is unnecessary, the regex split is eating up all spaces.
(5338da25fe)
- [LPS-84621]: Trim on the output rather than input, less garbage. (25c7035594)
- [LPS-84621]: Avoid duplicated null checking. (77b2b5dc24)
- [LPS-84621]: DocumentBuilder.parse(File) is incapable of returning null.
(7c648929a1)
- [LPS-84621]: Avoid parsing the same tld xml twice. (c330d89297)
- [LPS-84621]: Avoid recursive calls (710face562)
- [LPS-84621]: It is static field, no need to pass it around. (7505227d57)
- [LPS-84621]: taglib is the document element (22a25f3e02)
- [LPS-84621]: Avoid double map lookup (40c2540904)
- [LPS-84621]: Update readme (93f5f7c934)
- [LPS-84621]: Use gradle.user.home system property (33ff885477)
- [LPS-84621]: Use protected methods only if its necessary (074552844e)
- [LPS-84621]: Cache portal definitions (f8071f3234)
- [LPS-84621]: Use gradle home directory to find the portal definitions
(4be68d57fa)
- [LPS-84621]: Add missing override annotations (4fe5569259)
- [LPS-84621]: Use the default xml parser (33e98fbb90)
- [LPS-84621]: Pass files to the SchemaValidate Ant task (ce8f673340)
- [LPS-84621]: Add logic to scan 'portal/definitions' directory for tld
validation (f3af8e7713)
- [LPS-79679]: Fix incorrect naming (6d14ee950f)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)
- [LPS-71591]: README Wordsmithing (6910db2a62)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

### Description
- [LPS-84621]: Update the `validateTLD` task to automatically search the parent
directories for `portal/definitions` to use these files for TLD scheme
validation. Set the properties `xmlParserClassName` and `xmlParserClasspath` to
`null` to use the default XML parser provided by Ant.

## 1.3.2 - 2018-11-16

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

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 1.3.3 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.