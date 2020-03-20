# Liferay Ant JGit Change Log

## 2.0.0 - 2018-03-15

### Commits
- [LPS-77425] Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)

## 1.0.10 - 2018-03-13

### Commits
- [LPS-77425] Increment all major versions (d25f48516a)
- [LRQA-28693] Ant "taskdef" already looks in "tools/sdk" (ddf2b54259)
- [LRQA-28693] These are only used from Ant, so the dependency is "provided"
(91dbbd33d4)
- [LPS-61099] Delete build.xml in modules (c9a7e1d370)
- [LPS-63943] Revert "LPS-63943 Temporarily make the task callable via CLI"
(0a87e3ea03)
- [LPS-63943] Remove old "antJGit" code and check if the project is a leaf
(9f689ec1cc)

### Dependencies
- [LRQA-28693] Update the ant dependency to version 1.9.4.

## 1.0.9 - 2016-03-09

### Commits
- [LPS-63943] Temporarily make the task callable via CLI (484db5b29a)
- [LPS-63943] Replace only fixed versions (1e1c97c315)

## 1.0.8 - 2016-03-07

### Commits
- [LPS-63943] Update dependencies (8090944b19)
- [LPS-63943] Ignore commits based on their message (35edf21965)
- [LPS-61088] Remove classes and resources dir from Include-Resource
(1b0e1275bc)
- [LPS-55691] No need to deploy to the sdk dir anymore (dc11a3b9f7)
- [LPS-61420] Incorrect tabs and linebreaks in /modules/sdk (955b0fba88)

## 1.0.7 - 2015-11-16

### Commits
- [LPS-59564] Update directory layout for "sdk" modules (ea19635556)
- [LPS-51081] Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081] Remove modules' Ivy files (076b384eef)

## 1.0.6 - 2015-07-08

### Commits
- [LPS-56954] UpToDateUtil.hasChangedSince() should consider unknown git hash as
changed (184aa0c03b)
- [LPS-51081] Ran "ant reset-gradle init-gradle" (9ab363b842)

### Dependencies
- [LPS-51081] Update the ant dependency to version 1.9.4.
- [LPS-51081] Update the org.eclipse.jgit dependency to version
4.0.0.201505050340-m2.
- [LPS-51081] Update the slf4j-log4j12 dependency to version 1.7.2.

## 1.0.5 - 2015-06-07

### Commits
- [LPS-55937] Update task defs (d095449409)
- [LPS-55937] No longer needs GitIsCleanTask (0b24d158f4)
- [LPS-55937] GitUpToDate tasks (61d85bceaa)

## 1.0.4 - 2015-06-05

### Commits
- [LPS-55937] Calculate difference from parent if there is one. (619cc153a2)
- [LPS-55937] Don't care the body, save a bit memory (ef76d409ab)
- [LPS-55937] Follow RevWalk javadoc's advice to use parseCommit() (cee71a2b3b)
- [LPS-56105] Remove custom deployment of Liferay Ant JGit (7d0b60b2f4)
- [LPS-56105] Revert "LPS-56105 Remove custom deployment of Liferay Ant JGit"
(14d9761caf)
- [LPS-56105] Remove custom deployment of Liferay Ant JGit (37c3744394)
- [LPS-55937] Prevent from future mistakes (d6445f0909)
- [LPS-55937] Bad copy-paste (f60dae62b8)

## 1.0.3 - 2015-05-28

### Commits
- [LPS-55937] this may be used outside (531c9efcbf)
- [LPS-55937] Turn on task level result caching, assuming repository does not
change during build. (34bf12df05)
- [LPS-55937] Change the head hash logic to be: 1) If there is no ignore file,
just use the first hash under this dir. 2) If there is an ignore file, and the
first hash does not contain the ignore file, use the first hash. 3) If there is
an ignore file, and the first hash contains the ignore file, use the second hash
if it exist (regardless whether the second hash contains the ignore file or not)
4) If there is an ignore file, and the first hash contains the ignore file, but
the second hash does not exist, fallback to use the first hash, even it does
contain the ignore file. (This is ensuring the build script can always get a
hash value in order to continue running) (57bad010d0)
- [LPS-55937] The git repos does not change during build, there is no point to
recreate the dir cache on every call to get status. Let's cache it to save the
massive IO operations. (4edae571eb)
- [LPS-55937] Since we don't use git submodule at all, let's ignore it to speed
things up. (36716e688a)
- [LPS-55937] Turn on Repository caching (da5708ad87)

## 1.0.2 - 2015-05-27

### Commits
- [LPS-55937] Care for windows :) (78da8cbad7)

## 1.0.1 - 2015-05-27

### Commits
- [LPS-55937] Eagerly free repository resources (7e221f5e8a)
- [LPS-55937] Allow Java7 (0df2f048e6)

[LPS-51081]: https://issues.liferay.com/browse/LPS-51081
[LPS-55691]: https://issues.liferay.com/browse/LPS-55691
[LPS-55937]: https://issues.liferay.com/browse/LPS-55937
[LPS-56105]: https://issues.liferay.com/browse/LPS-56105
[LPS-56954]: https://issues.liferay.com/browse/LPS-56954
[LPS-59564]: https://issues.liferay.com/browse/LPS-59564
[LPS-61088]: https://issues.liferay.com/browse/LPS-61088
[LPS-61099]: https://issues.liferay.com/browse/LPS-61099
[LPS-61420]: https://issues.liferay.com/browse/LPS-61420
[LPS-63943]: https://issues.liferay.com/browse/LPS-63943
[LPS-77425]: https://issues.liferay.com/browse/LPS-77425
[LRQA-28693]: https://issues.liferay.com/browse/LRQA-28693