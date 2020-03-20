# Liferay Ant Heap Dump Change Log

## 2.0.0 - 2018-03-15

### Commits
- [LPS-77425] Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)

## 1.0.3 - 2018-03-13

### Commits
- [LPS-77425] Increment all major versions (d25f48516a)
- [LPS-75938] Update ProcessUtil usages. (21a913eaf0)

### Dependencies
- [LPS-75938] Update the com.liferay.petra.process dependency to version 1.0.0.
- [LPS-75938] Update the com.liferay.portal.kernel dependency to version
default.

## 1.0.2 - 2017-10-12

### Commits
- [LPS-75121] Update usages (cffd576699)
- [LRQA-28693] Ant "taskdef" already looks in "tools/sdk" (ddf2b54259)
- [LRQA-28693] These are only used from Ant, so the dependency is "provided"
(91dbbd33d4)
- [LPS-66064] Better glob to distinguish asm-*.jar from asm-commons-*.jar
(d50ced317d)
- [LPS-66064] Remove use of "-liferay-includeresource" (bbd6b63415)
- [LPS-61099] Delete build.xml in modules (c9a7e1d370)
- [LPS-64021] Prefix directive (0eb9b8b7d8)
- [LPS-64021] Apply for "sdk" (50aea4bb04)
- [LPS-64021] Use directive instead (9c31b9fc18)
- [LPS-63797] Update Gradle (0cc5dcb882)
- [LPS-63706] Update Gradle (253b8fe054)
- [LPS-63706] Update Gradle (67a7121b87)
- [LPS-63706] Update Gradle (0708714f3c)
- [LPS-63112] Update Bnd (6ee86cf7ce)
- [LPS-63112] Update Gradle (79037eb401)
- [LPS-61088] Remove classes and resources dir from Include-Resource
(1b0e1275bc)
- [LPS-55691] Deploy directly to lib/development (7a5801f7a3)

### Dependencies
- [] Update the com.liferay.portal.kernel dependency to version 2.51.0.
- [LPS-75121] Update the com.liferay.portal.kernel dependency to version
default.
- [LRQA-28693] Update the ant dependency to version 1.9.4.
- [] Update the com.liferay.portal.kernel dependency to version 2.0.0.
- [LPS-64182] Update the com.liferay.portal.kernel dependency to version
default.
- [] Update the com.liferay.portal.kernel dependency to version 1.0.3.
- [LPS-63499] Update the com.liferay.portal.kernel dependency to version 1.0.2.
- [] Update the com.liferay.portal.kernel dependency to version 1.0.1.
- [LPS-63797] Update the com.liferay.portal.kernel dependency to version
default.

## 1.0.1 - 2015-11-16

### Commits
- [LPS-59564] Update directory layout for "sdk" modules (ea19635556)
- [LPS-58880] Set classpath for "sdk" modules (6b7295ca41)
- [LPS-59279] Use the Bnd symbolic name (2705ee0dd3)
- [LPS-51081] Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081] Remove modules' Ivy files (076b384eef)
- [LPS-51081] Simplify (47109bb8b7)
- [LPS-51081] Remove the child copySpec approach in the "deploy" task, so it
will be easier to add extra files ("osgi.runtime.dependencies") as "from"
(25c340d8be)
- [LPS-51081] Ant to Gradle (6e2ea34e39)
- [LPS-51081] Ran "ant reset-gradle init-gradle" (9ab363b842)
- [LPS-56101] Copy to lib/development, as test script needs to access it.
(936f613747)

### Dependencies
- [LPS-51081] Update the ant dependency to version 1.9.4.

[LPS-51081]: https://issues.liferay.com/browse/LPS-51081
[LPS-55691]: https://issues.liferay.com/browse/LPS-55691
[LPS-56101]: https://issues.liferay.com/browse/LPS-56101
[LPS-58880]: https://issues.liferay.com/browse/LPS-58880
[LPS-59279]: https://issues.liferay.com/browse/LPS-59279
[LPS-59564]: https://issues.liferay.com/browse/LPS-59564
[LPS-61088]: https://issues.liferay.com/browse/LPS-61088
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-63112]: https://issues.liferay.com/browse/LPS-63112
[LPS-63499]: https://issues.liferay.com/browse/LPS-63499
[LPS-63706]: https://issues.liferay.com/browse/LPS-63706
[LPS-63797]: https://issues.liferay.com/browse/LPS-63797
[LPS-64021]: https://issues.liferay.com/browse/LPS-64021
[LPS-64182]: https://issues.liferay.com/browse/LPS-64182
[LPS-66064]: https://issues.liferay.com/browse/LPS-66064
[LPS-75121]: https://issues.liferay.com/browse/LPS-75121
[LPS-75938]: https://issues.liferay.com/browse/LPS-75938
[LPS-77425]: https://issues.liferay.com/browse/LPS-77425
[LRQA-28693]: https://issues.liferay.com/browse/LRQA-28693