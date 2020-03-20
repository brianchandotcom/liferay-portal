# Liferay Gradle Plugins Whip Change Log

## 1.0.8 - 2020-03-04

### Commits
- [LPS-100515] Update README.markdown (694b3791de)
- [LPS-100515] Update plugins Gradle version (448efac158)
- [LPS-84119] This should be good enough, @petershin please verify thx
(b26555d685)
- [LPS-84119] Partial revert of d4373b5 (da5c0c6f0f)
- [LPS-84119] Use ListUtil.toList (d4373b510d)
- [LPS-84119] Revert "LPS-84119 Reduce lines of code by using Arrays.asList"
(c094bb8e03)
- [LPS-84119] Reduce lines of code by using Arrays.asList (1c431f24b2)
- [LPS-85609] Update readme (c182ff396d)
- [LPS-85609] Simplify gradleTest (a8b0feff31)
- [LPS-85609] Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-106149] Update the com.liferay.gradle.util dependency to version 1.0.35.
- [LPS-96247] Update the com.liferay.gradle.util dependency to version 1.0.34.

## 1.0.7 - 2018-11-19

### Dependencies
- [LPS-87466] Update the com.liferay.gradle.util dependency to version 1.0.33.

## 1.0.6 - 2018-11-16

### Commits
- [LPS-87192] Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192] Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609] Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609] Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609] Update supported Gradle versions (d79b89682b)
- [LPS-86589] Update readme (4280a3d596)
- [LPS-86589] Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)
- [LPS-71117] Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117] Update supported Gradle versions in READMEs (fdcc16c0d4)
- [LPS-77425] Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425] Increment all major versions (d25f48516a)
- [LPS-67658] Fix broken link (fab68bd47b)
- [LRDOCS-4319] Update Gradle plugin README intro descriptions for consistency
(72104bde58)
- [LRDOCS-4319] Update Gradle plugin BND descriptions for consistency
(e1495e8e8d)
- [LPS-76644] Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644] Add description to Gradle plugins (5cb7b30e6f)
- [LPS-74807] Not needed, since portal-test already embeds JUnit (0587a30bda)
- [LPS-74807] Use fixed versions to avoid adding new Petra dependencies
(12c4810aba)

### Dependencies
- [LPS-87466] Update the com.liferay.gradle.util dependency to version 1.0.32.
- [LPS-84094] Update the com.liferay.gradle.util dependency to version 1.0.31.
- [LPS-84094] Update the com.liferay.gradle.util dependency to version 1.0.30.
- [LPS-77425] Update the com.liferay.gradle.util dependency to version 1.0.29.

## 1.0.5 - 2017-09-19

### Commits
- [LPS-74490] Use previous Whip version until new portal-test is released
(350be97177)
- [LPS-66709] Update supported Gradle versions in READMEs (06e315582b)
- [LPS-70677] No need to look into the local Maven repository during testing
(452be84220)
- [LPS-70060] Test plugins with Gradle 3.3 (09bed59a42)
- [LPS-66709] README typo (283446e516)
- [LPS-66709] Add supported Gradle versions in READMEs (e0d9458520)
- [LPS-69259] Test plugins with Gradle 3.2.1 (72873ed836)
- [LPS-69259] Test plugins with Gradle 3.2 (dec6105d3d)
- [LPS-68231] Test plugins with Gradle 3.1 (49ec4cdbd8)
- [LPS-67658] Convert gradle-plugins-whip sample into a smoke test (9a8e39de94)
- [LPS-67658] Configure GradleTest in gradle-plugins-whip (f77627bff2)
- [LPS-67658] Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658] These plugins must work with Gradle 2.5+ (5b963e363d)
- [LPS-66709] Minor clarification in README (55f96956df)
- [LPS-66709] Edit method/property descriptions in README (eb4bad4664)
- [LPS-66709] Edit README (cfc123187e)
- [LPS-66709] README for gradle-plugins-whip (714cbdc237)
- [LPS-66709] Use module dependencies in sample (1fd6edb373)

### Dependencies
- [LPS-73584] Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584] Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-72914] Update the com.liferay.gradle.util dependency to version 1.0.27.

## 1.0.4 - 2016-06-16

### Commits
- [LPS-64816] Update Gradle plugin samples (3331002e5d)
- [LPS-61099] Delete build.xml in modules (c9a7e1d370)
- [LPS-63943] This is done automatically now (f1e42382d9)
- [LPS-63112] Update Gradle (79037eb401)
- [LPS-62883] Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848] An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61088] Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-65749] Update the com.liferay.gradle.util dependency to version 1.0.26.
- [LPS-65086] Update the com.liferay.gradle.util dependency to version 1.0.25.

## 1.0.3 - 2015-11-16

### Commits
- [LPS-59564] Update directory layout for "sdk" modules (ea19635556)
- [LPS-51081] Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081] Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081] Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-58467] Update the com.liferay.gradle.util dependency to version 1.0.19.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.2 - 2015-07-27

### Commits
- [LPS-51081] Update to Gradle 2.5 (3aa4c1f053)
- [LPS-51801] Use Gradle API to grab dependencies from the local Gradle
installation (48f775db44)
- [LPS-51081] Ran "ant reset-gradle init-gradle" (9ab363b842)

### Dependencies
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081] Update the com.liferay.gradle.util dependency to version 1.0.11.
- [LPS-51081] Update the org.gradle.gradle-base-services dependency to version
2.4.
- [LPS-51081] Update the org.gradle.gradle-base-services-groovy dependency to
version 2.4.
- [LPS-51081] Update the org.gradle.gradle-core dependency to version 2.4.
- [LPS-51081] Update the org.gradle.gradle-plugins dependency to version 2.4.
- [LPS-51081] Update the org.gradle.gradle-reporting dependency to version 2.4.
- [LPS-51081] Update the groovy-all dependency to version 2.3.10.

## 1.0.1 - 2015-06-20

### Commits
- [LPS-51081] Consistency with Gradle 2.4 dependencies (6d4008a98c)
- [LPS-51081] Better API (5875e8230d)

[LPS-51081]: https://issues.liferay.com/browse/LPS-51081
[LPS-51801]: https://issues.liferay.com/browse/LPS-51801
[LPS-58467]: https://issues.liferay.com/browse/LPS-58467
[LPS-59564]: https://issues.liferay.com/browse/LPS-59564
[LPS-61088]: https://issues.liferay.com/browse/LPS-61088
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-61848]: https://issues.liferay.com/browse/LPS-61848
[LPS-62883]: https://issues.liferay.com/browse/LPS-62883
[LPS-63112]: https://issues.liferay.com/browse/LPS-63112
[LPS-63943]: https://issues.liferay.com/browse/LPS-63943
[LPS-64816]: https://issues.liferay.com/browse/LPS-64816
[LPS-65086]: https://issues.liferay.com/browse/LPS-65086
[LPS-65749]: https://issues.liferay.com/browse/LPS-65749
[LPS-66709]: https://issues.liferay.com/browse/LPS-66709
[LPS-67658]: https://issues.liferay.com/browse/LPS-67658
[LPS-68231]: https://issues.liferay.com/browse/LPS-68231
[LPS-69259]: https://issues.liferay.com/browse/LPS-69259
[LPS-70060]: https://issues.liferay.com/browse/LPS-70060
[LPS-70677]: https://issues.liferay.com/browse/LPS-70677
[LPS-71117]: https://issues.liferay.com/browse/LPS-71117
[LPS-72914]: https://issues.liferay.com/browse/LPS-72914
[LPS-73584]: https://issues.liferay.com/browse/LPS-73584
[LPS-74490]: https://issues.liferay.com/browse/LPS-74490
[LPS-74807]: https://issues.liferay.com/browse/LPS-74807
[LPS-76644]: https://issues.liferay.com/browse/LPS-76644
[LPS-77425]: https://issues.liferay.com/browse/LPS-77425
[LPS-84094]: https://issues.liferay.com/browse/LPS-84094
[LPS-84119]: https://issues.liferay.com/browse/LPS-84119
[LPS-85609]: https://issues.liferay.com/browse/LPS-85609
[LPS-86589]: https://issues.liferay.com/browse/LPS-86589
[LPS-87192]: https://issues.liferay.com/browse/LPS-87192
[LPS-87466]: https://issues.liferay.com/browse/LPS-87466
[LPS-96247]: https://issues.liferay.com/browse/LPS-96247
[LPS-100515]: https://issues.liferay.com/browse/LPS-100515
[LPS-106149]: https://issues.liferay.com/browse/LPS-106149
[LRDOCS-4319]: https://issues.liferay.com/browse/LRDOCS-4319