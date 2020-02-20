# Liferay Gradle Plugins Baseline Change Log

## 2.2.1 - 2019-10-14

### Commits
- [LPS-100937]: baseline (e4620f0ef6)
- [LPS-100937]: Catch exception and add warn message (f395ba403f)

## 2.2.0 - 2019-10-03

### Dependencies
- [LPS-102700]: Update the com.liferay.ant.bnd dependency to version 3.2.3.

## 2.1.8 - 2019-10-03

### Commits
- [LPS-102700]: Fix baseline (could not find method getBundleTask) (8d403e3aa4)

## 2.1.7 - 2019-09-27

### Dependencies
- [LPS-101947]: Update the com.liferay.ant.bnd dependency to version 3.2.2.

## 2.1.6 - 2019-09-17

### Dependencies
- [LPS-101450]: Update the com.liferay.ant.bnd dependency to version 3.2.1.

## 2.1.5 - 2019-09-10

### Commits
- [LPS-85677]: Logging (d09d134be8)

## 2.1.4 - 2019-09-03

### Commits
- [LPS-100937]: Check Maven metadata only for major version changes (94e211f09c)

## 2.1.3 - 2019-09-03

### Commits
- [LPS-100937]: Skip checking Maven metadata for the baseline task (805ff6efcf)

## 2.1.2 - 2019-08-08

### Dependencies
- [LPS-98937]: Update the com.liferay.ant.bnd dependency to version 3.2.0.

## 2.1.1 - 2019-07-30

### Commits
- [LPS-98190]: ant-bnd (68c493792e)
- [LPS-0]: SF. Space character correction for build scripts & READMEs
(9dd5d12c9a)

### Dependencies
- [LPS-98190]: Update the com.liferay.ant.bnd dependency to version 3.1.0.
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.1.0 - 2019-05-09

### Dependencies
- [LPS-95330]: Update the com.liferay.ant.bnd dependency to version 3.0.7.

## 2.0.10 - 2019-05-09

### Commits
- [LPS-95330]: Semantic versioning (44339d852a)
- [LPS-95330]: Move Baseline/BaselineProcessor to where it is really used.
(6cbfce8baf)

## 2.0.9 - 2019-01-08

### Commits
- [LPS-88903]: apply new version (5d2dd424c7)
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)
- [LPS-86583]: Readme link typo (6577116b62)

### Dependencies
- [LPS-88903]: Update the com.liferay.ant.bnd dependency to version 3.0.6.

## 2.0.8 - 2018-12-20

### Dependencies
- [LPS-88382]: Update the com.liferay.ant.bnd dependency to version 3.0.5.

## 2.0.7 - 2018-12-20

### Commits
- [LPS-88382]: release temp revert (191c822378)
- [LPS-88382]: Temp revert (b222b78d1c)

### Dependencies
- [LPS-88382]: Update the com.liferay.ant.bnd dependency to version 3.0.3.

## 2.0.6 - 2018-12-19

### Dependencies
- [LPS-88382]: Update the com.liferay.ant.bnd dependency to version 3.0.4.

## 2.0.5 - 2018-11-27

### Dependencies
- [LPS-87839]: Update the com.liferay.ant.bnd dependency to version 3.0.3.

## 2.0.4 - 2018-11-26

### Dependencies
- [LPS-87839]: Update the com.liferay.ant.bnd dependency to version 3.0.2.

## 2.0.3 - 2018-11-22

### Commits
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)
- [LPS-87503]: Fix gradleTest (405c07ff22)

### Dependencies
- [LPS-87776]: Update the com.liferay.ant.bnd dependency to version 3.0.1.
- [LPS-87503]: Update the com.liferay.ant.bnd dependency to version 3.0.0.

### Description
- [LPS-87776]: Allow the `baseline` task to update the `Bundle-Version` header
when all the `packageinfo` files are correct.

## 2.0.2 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.0.1 - 2018-11-16

### Commits
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)
- [LPS-85609]: Fix for Gradle 4.0.2 (801a24514a)

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 2.0.0 - 2018-10-29

### Commits
- [LPS-86583]: Update readme (f683cd1a7e)

### Description
- [LPS-86583]: Add the property `baselineConfiguration` which contains the
configuration with exactly one dependency to the previously released version of
the project for baselining.

## 1.3.10 - 2018-10-29

### Commits
- [LPS-86583]: Resolve dynamic dependency during the execution phase
(9593e95c96)
- [LPS-86583]: Use the baseline configuration to resolve the old jar
(9c95fc9a73)
- [LPS-86583]: Add the baseline configuration to the baseline task (701921dd54)
- [LPS-86583]: Rename variable to baselineConfiguration (cd106ee441)

## 1.3.9 - 2018-10-24

### Commits
- [LPS-86583]: Improve performance (avoid resolving dependencies when possible)
(9ed9a3f2a8)

### Description
- [LPS-86583]: Improve performance of the configuration phase for the `baseline`
task.

## 1.3.8 - 2018-10-22

### Commits
- [LPS-86583]: Skip the baseline task if the project was never published
(fa9458e696)
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)

### Description
- [LPS-86583]: Skip the `baseline` task if the project was never published.

## 1.3.7 - 2018-10-16

### Commits
- [LPS-85678]: ant-bnd (65a1765907)

### Dependencies
- [LPS-85678]: Update the com.liferay.ant.bnd dependency to version 2.0.59.

### Description
- [LPS-85678]: Update the `baseline` task to ignore the specified baseline
warnings recursively:
	- `EXCESSIVE-VERSION-INCREASE-RECURSIVE`
	- `PACKAGE ADDED-MISSING-PACKAGEINFO-RECURSIVE`
	- `PACKAGE-REMOVED-RECURSIVE`
	- `PACKAGE-REMOVED-UNNECESSARY-PACKAGEINFO-RECURSIVE`
	- `VERSION-INCREASE-REQUIRED-RECURSIVE`
	- `VERSION-INCREASE-SUGGESTED-RECURSIVE`

## 1.3.6 - 2018-10-16

### Commits
- [LPS-86332]: ant-bnd (36fe115bcd)

### Dependencies
- [LPS-86332]: Update the com.liferay.ant.bnd dependency to version 2.0.58.

### Description
- [LPS-85678]: Check the content of the `.lfrbuild-packageinfo` file to ignore
specified baseline warnings:
	- `EXCESSIVE-VERSION-INCREASE`
	- `PACKAGE ADDED-MISSING-PACKAGEINFO`
	- `PACKAGE-REMOVED`
	- `PACKAGE-REMOVED-UNNECESSARY-PACKAGEINFO`
	- `VERSION-INCREASE-REQUIRED`
	- `VERSION-INCREASE-SUGGESTED`

## 1.3.5 - 2018-10-05

### Dependencies
- [LPS-80388]: Update the com.liferay.ant.bnd dependency to version 2.0.57.

## 1.3.4 - 2018-09-24

### Dependencies
- [LPS-85678]: Update the com.liferay.ant.bnd dependency to version 2.0.56.

### Description
- [LPS-85678]: Check the content of the `.lfrbuild-packageinfo` file to ignore
specified baseline warnings.

## 1.3.3 - 2018-09-24

### Commits
- [LPS-85677]: Wordsmith (170cacf1a3)
- [LPS-85677]: (514f85f32d)
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)

### Dependencies
- [LPS-80388]: Update the com.liferay.ant.bnd dependency to version 2.0.55.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

### Description
- [LPS-85677]: Display the file location for the jars in the log when semantic
versioning is incorrect.

## 1.3.2 - 2018-08-23

### Dependencies
- [LPS-83067]: Update the com.liferay.ant.bnd dependency to version 2.0.54.

### Description
- [LPS-83067]: Only update the `packageinfo` file when the suggested version and
the newer version do not match.

## 1.3.1 - 2018-07-02

### Dependencies
- [LPS-83067]: Update the com.liferay.ant.bnd dependency to version 2.0.53.

## 1.3.0 - 2018-06-28

### Dependencies
- [LPS-83067]: Update the com.liferay.ant.bnd dependency to version 2.0.52.

### Description
- [LPS-83067]: Ignore excessive package version increase warnings by setting the
`BaselineTask`'s `ignoreExcessiveVersionIncreases` property to `true`.
- [LPS-83067]: Automatically ignore excessive package version increase warnings
when checking against older versions.

## 1.2.3 - 2018-06-28

### Commits
- [LPS-83067]: Update readme (70fa823b34)
- [LPS-83067]: Ignore excessive version increases when testing older releases
(347c502b0a)
- [LPS-83067]: Expose new property in Gradle (a2778ce0ae)
- [LPS-77441]: Fix readme (4ac59b4379)
- [LPS-79679]: Apply (04e9ee35fb)

### Dependencies
- [LPS-82534]: Update the com.liferay.ant.bnd dependency to version 2.0.51.

## 1.2.2 - 2018-04-03

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)
- [LPS-77441]: Edit baseline README (3a14c78d03)

### Dependencies
- [LPS-74110]: Update the com.liferay.ant.bnd dependency to version 2.0.50.
- [LRQA-39761]: Update the com.liferay.ant.bnd dependency to version 2.0.49.
- [LPS-77425]: Update the com.liferay.ant.bnd dependency to version 2.0.48.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-78571]: Update the com.liferay.ant.bnd dependency to version 2.0.48.
- [LPS-78571]: Update the com.liferay.ant.bnd dependency to version 2.0.47.
- [LPS-76997]: Update the com.liferay.ant.bnd dependency to version 2.0.46.
- [LPS-76926]: Update the com.liferay.ant.bnd dependency to version 2.0.45.
- [LPS-76926]: Update the com.liferay.ant.bnd dependency to version 2.0.44.

## 1.2.1 - 2018-02-01

### Commits
- [LPS-77350]: ant bnd (fd103fcae0)

### Dependencies
- [LPS-77350]: Update the com.liferay.ant.bnd dependency to version 2.0.43.

## 1.2.0 - 2018-01-29

### Commits
- [LPS-77441]: Update readme (14e10d12ab)

### Description
- [LPS-77441]: Allow the semantic versioning check to run using multiple
versions as baseline.
- [LPS-77441]: Check whether the baseline comes from the local Maven cache for
all `BaselineTask` instances, not just the `baseline` task.
- [LPS-77441]: Add the ability to set the default value of the `reportDiff`
property for all `BaselineTask` instances by setting the project property
`baseline.jar.report.level` to `"diff"` or `"persist"`.
- [LPS-77441]: Add the ability to set the default value of the
`reportOnlyDirtyPackages` for all `BaselineTask` instances by setting the
project property `baseline.jar.report.only.dirty.packages`.

## 1.1.10 - 2018-01-29

### Commits
- [LPS-77441]: Configure reporting levels directly in the baseline plugin
(c647e81b72)
- [LPS-77441]: Support lowest major version (f7063f50c6)
- [LPS-77441]: Extract common baseline task creation logic (dec5e78444)
- [LPS-77441]: Enforce the "allowMavenLocal" on all baseline tasks (5ba9e62a64)
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

## 1.1.9 - 2017-12-01

### Dependencies
- [LPS-76224]: Update the com.liferay.ant.bnd dependency to version 2.0.42.

## 1.1.8 - 2017-10-04

### Dependencies
- [LPS-74110]: Update the com.liferay.ant.bnd dependency to version 2.0.40.
- [LPS-74110]: Update the com.liferay.ant.bnd dependency to version 2.0.41.
- [LPS-73481]: Update the com.liferay.ant.bnd dependency to version 2.0.40.
- [LPS-74016]: Update the com.liferay.ant.bnd dependency to version 2.0.39.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.1.7 - 2017-05-19

### Dependencies
- [LPS-72572]: Update the com.liferay.ant.bnd dependency to version 2.0.38.

## 1.1.6 - 2017-04-28

### Dependencies
- [LPS-71728]: Update the com.liferay.ant.bnd dependency to version 2.0.37.

## 1.1.5 - 2017-04-27

### Dependencies
- [LPS-71728]: Update the com.liferay.ant.bnd dependency to version 2.0.36.
- [LPS-64098]: Update the com.liferay.ant.bnd dependency to version 2.0.35.

## 1.1.4 - 2017-03-28

### Commits
- [LPS-71535]: Test log file on "smoke" Gradle test too (923e987f32)
- [LPS-71535]: Add Gradle test for compatibility with Bnd Builder Plugin
(fa63d4725d)
- [LPS-71535]: Make it compatible with "biz.aQute.bnd.builder" (a5d96fb52d)
- [LPS-71264]: Fix Gradle test (1f6a2c79b1)

## 1.1.3 - 2017-03-15

### Commits
- [LPS-71118]: increment for the future (1776cb2ad4)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)
- [LPS-67573]: Enable semantic versioning check on CI (63d7f4993f)

### Dependencies
- [LPS-71118]: Update the com.liferay.ant.bnd dependency to version 2.0.34.

## 1.1.2 - 2017-01-31

### Commits
- [LPS-70060]: Test plugins with Gradle 3.3 (09bed59a42)

### Dependencies
- [LPS-70379]: Update the com.liferay.ant.bnd dependency to version 2.0.33.

## 1.1.1 - 2017-01-04

### Commits
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)

### Dependencies
- [LPS-69899]: Update the com.liferay.ant.bnd dependency to version 2.0.32.

## 1.1.0 - 2016-11-30

### Commits
- [LPS-69470]: Update readme (f2f3d4eb9f)

### Dependencies
- [LPS-69470]: Update the com.liferay.ant.bnd dependency to version 2.0.31.

### Description
- [LPS-69470]: Add the property `forceCalculatedVersion` to all tasks that
extend `BaselineTask`. If `true`, the baseline check will fail if the
`Bundle-Version` has been excessively increased.

## 1.0.1 - 2016-11-30

### Commits
- [LPS-69470]: Add option to Gradle plugin (c63268ec9d)
- [LPS-66762]: Fix gradle-plugins-baseline's smoke test (89790024b3)
- [LPS-66762]: Not needed (81dd3b37d2)
- [LPS-69259]: Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-66762]: Edit Gradle Baseline plugin README (04de22ed5e)