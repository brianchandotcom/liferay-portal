# Liferay Gradle Plugins Target Platform Change Log

## 1.0.1 - 2018-05-07

### Commits
- [LPS-80222]: Avoid chaining (a8393cdad7)
- [LPS-80222]: Avoid using raw types, even in Gradle (9255d9c4e2)
- [LPS-80222]: We already know the max size of this list (a5f4505488)
- [LPS-80222]: Use constants if available (85079d5c62)
- [LPS-80222]: only apply dependency management to specific configurations
(bbff5ece71)
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Partial revert of b739c8fcdc5d1546bd642ca931476c71bbaef1fb
(02ca75b1da)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-77343]: Edit Target Platform Gradle plugin Readme (fe25cde2ca)
- [LPS-77343]: Wordsmith (0849914aa8)
- [LPS-77343]: rough draft of README (b2584fd912)
- [LPS-77343]: add additional tests for documented features (2d1030b56a)
- [LPS-77343]: switch bndrunFile to input and write to temp dir if not specified
(8882664e48)

### Dependencies
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.

### Description
- [LPS-77343]: Add the ability to use a manually generated bndrun file in a
`ResolveTask` instance by setting the property `bndrunFile` to a valid file.
- [LPS-80222]: Apply BOM files only on the following configurations:
	- configuration `compileInclude` added by [Liferay Gradle Plugins]
	- configuration `default`
	- configurations added by [Liferay Gradle Plugins Test Integration]
	- configurations added by the `java` plugin

## 1.0.2 - 2018-05-29

### Commits
- [LPS-81530]: Remove unneeded methods (34eb7a5ff0)
- [LPS-81530]: don't save applyToConfiguration Set (26ee244743)
- [LPS-81530]: rename (a0b9764d48)
- [LPS-81530]: fix tests since the distro.jar has portlet 2.0 api instead
(1a20208a67)
- [LPS-81530]: add new extension object method applyToConfiguration (fef625dbf0)
- [LPS-81530]: refactor dependency management configuration to util (7431d1d42c)
- [LPS-81042]: Use Latest Workspace (5ec5c37cf9)
- [LPS-79653]: Portlet 3.0: Upgrade to the Portlet 3.0.0 API (upgrade templates
and unit tests) (045479bf7c)

## 1.1.0 - 2018-07-17

### Commits
- [LPS-74544]: Auto SF (update cdn urls) (e6128bc643)
- [IDE-4081]: update gradle-plugins-workspace to 1.10.1 (da694ed736)
- [LPS-77425]: Restore version (765863e00e)
- [LPS-81530]: Update readme (505b894ae3)
- [LPS-81530]: Simplify and allow to pass configurations instead of names
(e07c601a76)

### Description
- [LPS-81530]: Add the ability to configure imported BOMs to manage Java
dependencies and the various artifacts used in resolving OSGi dependencies.

## 1.1.1 - 2018-07-23

### Commits
- [LPS-82491]: Wordsmith (1e5c6ede6a)
- [LPS-82491]: update changelog and readme (8a099c414d)
- [LPS-82491]: sort (62f640822d)
- [LPS-82491]: simplify (d657dad725)
- [LPS-82491]: fix test (c8100f4a8d)
- [LPS-82491]: fix intellij source set error (4e1614f9ad)
- [LPS-82491]: Not needed (16a290a455)
- [LPS-82491]: don't add source set, java plugin does that (842ebedd9e)
- [LPS-82491]: add test (b1a7bd6728)
- [LPS-82491]: variable names (8f39e0921b)
- [LPS-82491]: rename (c25ca41279)
- [LPS-82491]: sort (0c1296235c)
- [LPS-82491]: Portal target platform plugin should support Intellij IDEA
(59fc629083)

### Description
- [LPS-82491]: Add support for the `idea` plugin.

## 1.1.2 - 2018-08-08

### Commits
- [LPS-83922]: set applyMavenExclusions = false to improve dependency resolution
(d93d54dc18)
- [LPS-83922]: don't add requirements for jars with no BSN (c29b894abe)

### Description
- [LPS-83922]: Don't add requirements for files without a BSN.
- [LPS-83922]: Set `applyMavenExclusions` to `false` to improve performance.

## 1.1.3 - 2018-10-09

### Commits
- [LPS-85946]: add support for ThemeBuilder related configurations (c82f026055)
- [LPS-85264]: rename var (2745afb9fe)
- [LPS-85264]: Target platform plugin should support ext modules (03b5d5ffcd)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)
- [LPS-82491]: Fix gradleTest for Gradle 3.5.1 (0e7dfcf872)
- [LPS-84119]: Remove unused methods (74dba76ca9)

### Dependencies
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 1.1.4 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-86916]: Regen (2bd81691d0)
- [LPS-85609]: Fix for Gradle 4.0.2 (801a24514a)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86581]: Add optional m2-tmp repo to resolve the latest
com.liferay.gradle.plugins (b389243ac9)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 1.1.5 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.1.6 - 2018-11-20

### Commits
- [LPS-87419]: Add optional m2-tmp repo (34cda4b600)
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

## 1.1.8 - 2019-01-02

### Commits
- [LPS-88382]: release temp revert (414be58373)

## 1.1.9 - 2019-01-08

### Commits
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)

## 1.1.12 - 2019-04-15

### Commits
- [LPS-93873]: update dependency-management-plugin version (275e8ed5db)
- [LPS-91772]: this is a publish of a new SF to revert the old one (23537d6e71)
- [LPS-91342]: regen (334a31a0b6)
- [LPS-91342]: regen (a492452b39)

## 1.1.14 - 2019-05-20

### Commits
- [LPS-95279]: semver (c368d050fb)
- [LPS-95279]: refactor targetPlatformIDE extension, remove includedGroups and
add indexSources (9b296b8d83)

## 2.0.0 - 2019-05-20

### Commits
- [LPS-95279]: Fix whitespace (3bfa528f9e)
- [LPS-95279]: Grammar (ae6f4a544c)
- [LPS-95279]: Code consistency with other Gradle plugins (no logic changes)
(ceeb405edf)
- [LPS-95279]: Check logging level (54fb914592)
- [LPS-95279]: Sort alphabetically (e183cb2742)
- [LPS-95279]: Remove duplicate code (7d47a0c18f)
- [LPS-95279]: Use final when the variable is used inside a closure (f3f1d7efab)
- [LPS-95279]: More descriptive constant name (7446fdf338)
- [LPS-95279]: refactor and update tests according to new target-platform plugin
behavior (a5834de9c6)
- [LPS-95279]: add runbundles to accessible API (0b8cc7f9d2)
- [LPS-95279]: if we have a single project, add resolve task if applicable
(64ddc6fcab)
- [LPS-95279]: only apply this plugin to javaPlugin projects (97c8ad0f40)
- [LPS-95279]: wrapping (b2ea2b2358)
- [LPS-95279]: not needed (9d514e7d66)
- [LPS-95279]: move all of our predicates to extension object (d73449d1df)
- [LPS-95279]: don't add resolve if osgi plugin hasn't been applied (d3b8cb36b4)
- [LPS-95279]: only apply plugin to subproject is it passes spec (8510f880be)
- [LPS-95279]: don't add and configure resolveTask to root project (94f9c7a729)
- [LPS-95279]: fix up the resolve task (1f219430c4)
- [LPS-95279]: more resolve task work (9a86b91941)
- [LPS-95279]: refactor ResolveTask (68f5442f97)
- [LPS-95279]: var name (edb5a8cdc3)
- [LPS-95279]: fix conflict between target platform and user configured
maven-publish plugin (f367d6b48e)
- [LPS-95279]: fix tests (00530d6a67)

### Dependencies
- [LPS-95279]: Update the biz.aQute.bnd dependency to version 4.2.0.
- [LPS-95279]: Update the biz.aQute.bnd.gradle dependency to version 4.2.0.
- [LPS-95279]: Update the biz.aQute.repository dependency to version 4.2.0.
- [LPS-95279]: Update the biz.aQute.resolve dependency to version 4.2.0.
- [LPS-95279]: Update the biz.aQute.bnd.gradle dependency to version 3.5.0.

## 2.0.1 - 2019-07-30

### Commits
- [LPS-98190]: fix test (ac25d5af38)
- [LPS-98190]: Fix classpath (e938e3099f)
- [LPS-98190]: fix test classpath (af3c278aab)
- [LPS-84119]: Avoid chaining on method 'stream' (188b859489)
- [LPS-97601]: Follow existing patterns (3da8ac438d)
- [LPS-97601]: fix classpath issues in tests (e8db32ee57)
- [LPS-96290]: fix tests (caebcf052f)
- [LPS-84119]: Use 'osgi.core' instead of 'org.osgi.core' (01606b6fb1)

### Dependencies
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.0.2 - 2019-08-27

### Commits
- [LPS-100448]: Auto-SF (0ff1cd4057)
- [LPS-99577]: fix classpath problem in tests (13be6aecd6)
- [LPS-93483]: try 2.1.1? (9b15f81863)
- [LPS-98877 LPS-96095]: auto SF for portlet-api (fc1fff6de9)

## 2.0.3 - 2019-09-23

### Commits
- [LPS-100448]: fix tests (e363c9e9b6)

## 2.0.4 - 2019-10-21

### Commits
- [LPS-95938]: Use bnd 4.3.0 in workspace and target platform (60bc0adb96)
- [LPS-103051]: temp rollback (9a1e210294)

### Dependencies
- [LPS-95938]: Update the biz.aQute.bnd dependency to version 4.3.0.
- [LPS-95938]: Update the biz.aQute.bnd.gradle dependency to version 4.3.0.
- [LPS-95938]: Update the biz.aQute.repository dependency to version 4.3.0.
- [LPS-95938]: Update the biz.aQute.resolve dependency to version 4.3.0.

## 2.0.5 - 2019-11-15

### Commits
- [LPS-103466]: Sort (5b5ff8dcca)
- [LPS-103809]: preop next (3768e9a4b7)
- [LPS-103466]: revert (e4f42e7a25)
- []: Revert "LPS-103466 Sort" (fffa7e7259)
- [LPS-103466]: Sort (1cd1d09425)

## 2.0.6 - 2019-11-27

### Commits
- [LPS-100515]: Update README.markdown (694b3791de)
- [LPS-100515]: Update plugins Gradle version (448efac158)

## 2.0.7 - 2019-12-24

### Commits
- [LPS-105502]: Use final when the variable is used inside a closure
(f59ebe3998)
- [LPS-105502]: Sort (fb937e7b1a)
- [LPS-105502]: Rename method (35cfb8fe71)
- [LPS-105502]: Use char (a6f8643cbf)
- [LPS-105502]: Add logging (421b88559e)
- [LPS-105502]: Sort closures alphabetically (5cdaf1b90d)
- [LPS-105889]: Rename (119e7cc77b)
- [LPS-105889]: Gradle 5 BOM platform only works for transitive configurations
(a11ab82f1e)
- [LPS-105889]: add deprecation notice (b01f914374)
- [LPS-105889]: update tests to new gradle5 BOM behavior (675b006f27)
- [LPS-105889]: Speed up ide model tests (6a0a2e3860)
- [LPS-105889]: Remove asserts that required old spring API (e5c5455ed8)
- [LPS-105889]: Remove unnecessary test (d1049fbb60)
- [LPS-105889]: Use published BOMs instead (f1d6547ec5)
- [LPS-105889]: remove eclipse warnings (8c93363308)
- [LPS-105889]: update targetPlatformIDE configuration to use gradle5 BOM support
(f21c5bcc03)
- [LPS-105889]: use native gradle 5 BOM support instead of spring
dependency-management-plugin (4283a850ef)

## 2.0.8 - 2020-01-10

### Commits
- [LPS-105889]: Update version (2d18117cbc)
- [LPS-105889]: Move logic to extension (cf7169331f)
- [LPS-105889]: Consistency (4dd3685e3b)
- [LPS-105889]: Sort alphabetically (fb5b983e7c)
- [LPS-105889]: simplify tests by removing workspace plugin (45f72dbd20)
- [LPS-105889]: add test case to make sure that configurations with
defaultDependencies are not broken (c67cf08b21)
- [LPS-105889]: remove deprecation since this is not needed again (062bc3de66)
- [LPS-105889]: add back whitelist of configurations to apply platform dependency
(ee749e80fb)
- [LPS-102243]: Partial revert (b5ee7e9c16)
- [LPS-102243]: Remove portal-test-integration and all references to it from
build and classpath (c2efa6c7f2)

## 2.0.9 - 2020-01-27

### Commits
- [LPS-107155]: semver (b11529bc71)
- [LPS-107155]: Sort alphabetically (ac17b7bc90)
- [LPS-107155]: Update description message (c03028e705)
- [LPS-107155]: Update log message (015778ec70)
- [LPS-107155]: As used (527a0e99fb)
- [LPS-107155]: Inline (ba294e048f)
- [LPS-107155]: Remove empty log message (802db0feb4)
- [LPS-107155]: Simplify parameters (2bd4e15cba)
- [LPS-107155]: Use list (b87569ce45)
- [LPS-107155]: Update exception message (7e946c4f47)
- [LPS-107155]: As used (a8ef8d13e9)
- [LPS-107155]: Rename variable (e799960ab1)
- [LPS-107155]: Simplify dependencyManagement task to text output only
(cbf7138a44)
- [LPS-107155]: Variable names (2e38d0181a)
- [LPS-107155]: Move comparator logic (edf61a52e4)
- [LPS-107155]: Use console mode plain (8c7427e792)
- [LPS-107155]: Wrap it like a burrito (a2eeb3aafb)
- [LPS-107155]: Inline (d92420cd35)
- [LPS-107155]: Remove unneeded line break (3552599562)
- [LPS-107155]: Remove unneeded final keyword (6319e914e8)
- [LPS-107155]: Update task description (bfe6162b1f)
- [LPS-107155]: Update exception message (a82af282ce)
- [LPS-107155]: fix test (17276efc5f)
- [LPS-107155]: refactor to reduce public API (0351bce6a0)
- [LPS-107155]: show that plugin now requires gradle 5.x (7a27a3415e)
- [LPS-107155]: refactor as a single test project (b0cffecf1b)
- [LPS-107155]: use gradle idiomatic APIs (bea8958646)
- [LPS-107155]: show errors in build lifecycle (595c063d11)
- [LPS-107155]: Use standard java APIs (d865302771)
- [LPS-107155]: use standard json dependency (3f96db66b2)
- [LPS-107155]: add dependencyManagement task to get all target platform
dependencies (78d2606f6b)
- [LPS-105380]: Rename exception variables (b3173da81b)

### Dependencies
- [LPS-107155]: Update the fastjson dependency to version 1.2.62.
- [LPS-107155]: Update the dom4j dependency to version 2.1.1.

## 2.1.0 - 2020-01-27

### Commits
- [LPS-107155]: Baseline (a6e47e6926)