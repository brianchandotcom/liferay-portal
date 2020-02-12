# Liferay Gradle Plugins Source Formatter Change Log

## 1.0.1 - 2015-05-07

### Commits
- [LPS-51081]: Source Formatter requires relative paths (114d4aa97a)
- [LPS-51081]: Ran "ant setup-eclipse" (304770f9d9)

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
- [LPS-55111]: Source formatting (98ea4a5816)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.14.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.13.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.12.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.11.

## 1.0.3 - 2015-09-02

### Commits
- [LPS-58330]: Missing = (a003ef9ba9)
- [LPS-58330]: Add logic to keep track of untracked/modified files (0652af9a42)
- [LPS-51081]: Remove modules' Eclipse project files (b3f19f9012)
- [LPS-51081]: Replace modules' Ant files with Gradle alternatives (9e60160a85)
- [LPS-51081]: Remove modules' Ivy files (076b384eef)

### Dependencies
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.18.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.17.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.16.
- [LPS-51081]: Update the com.liferay.gradle.util dependency to version 1.0.15.

## 1.0.5 - 2015-09-03

### Commits
- [LPS-58330]: The portal tool dependencies are only used to embed single *Args
classes, so they should be considered "provided" (da7c77ffbc)

## 1.0.6 - 2015-09-03

### Commits
- [LPS-58330]: Add logic to keep track of files modified by latest author
(f550d8c870)

## 1.0.7 - 2015-09-08

### Commits
- [LPS-58330]: Add logic to keep track of files modified by latest author
(34980c86ec)

## 1.0.8 - 2015-11-16

### Commits
- [LPS-59564]: Update directory layout for "sdk" modules (ea19635556)

### Dependencies
- [LPS-58467]: Update the com.liferay.gradle.util dependency to version 1.0.19.

## 1.0.9 - 2016-01-21

### Commits
- [LPS-62412]: Configure "formatSource" from system properties (3785917fad)
- [LPS-62412]: Add missing setters (a7a5669c96)
- [LPS-62412]: Allow custom classpath (7a32d9e147)
- [LPS-62412]: Project dir is already the default working dir (596371901e)
- [LPS-62412]: Allow custom args (1347bef113)
- [LPS-62412]: Allow custom main class (d7f5d7a22b)
- [LPS-62412]: Fix wrong argument names (4c2f95cd93)
- [LPS-61088]: Remove classes and resources dir from Include-Resource
(1b0e1275bc)

### Dependencies
- [LPS-62412]: Update the com.liferay.gradle.util dependency to version 1.0.23.

## 1.0.10 - 2016-05-16

### Commits
- [LPS-65716 LPS-65799]: Use SourceFormatterArgs so this works for both ant and
gradle (0643834abf)
- [LPS-64816]: Update Gradle plugin samples (3331002e5d)
- [LPS-61099]: Delete build.xml in modules (c9a7e1d370)
- [LPS-64021]: Prefix directive (0eb9b8b7d8)
- [LPS-64021]: Apply for "sdk" (50aea4bb04)
- [LPS-64021]: Use directive instead (9c31b9fc18)
- [LPS-63943]: This is done automatically now (f1e42382d9)
- [LPS-62883]: Update gradle-plugins/build.gradle (20fc2457e6)
- [LPS-61848]: An empty settings.gradle is enough (2e5eb90e23)
- [LPS-61420]: Fit on single line (9cc5731c19)

### Dependencies
- [LPS-65716]: Update the com.liferay.source.formatter dependency to version
1.0.214.
- [LPS-65636]: Update the com.liferay.source.formatter dependency to version
1.0.213.
- [LPS-65799]: Update the com.liferay.source.formatter dependency to version
1.0.212.
- [LPS-65799]: Update the com.liferay.source.formatter dependency to version
1.0.211.
- [LPS-64186]: Update the com.liferay.source.formatter dependency to version
1.0.210.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.209.
- [LPS-65799]: Update the com.liferay.source.formatter dependency to version
1.0.208.
- [LPS-65323]: Update the com.liferay.source.formatter dependency to version
1.0.207.
- [LPS-65753]: Update the com.liferay.source.formatter dependency to version
1.0.206.
- [LPS-65362]: Update the com.liferay.source.formatter dependency to version
1.0.205.
- [LPS-65716]: Update the com.liferay.source.formatter dependency to version
1.0.204.
- [LPS-65636]: Update the com.liferay.source.formatter dependency to version
1.0.203.
- [LPS-65528]: Update the com.liferay.source.formatter dependency to version
1.0.202.
- [LPS-65528]: Update the com.liferay.source.formatter dependency to version
1.0.201.
- [LPS-65528]: Update the com.liferay.source.formatter dependency to version
1.0.200.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.199.
- [LPS-65323]: Update the com.liferay.source.formatter dependency to version
1.0.198.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.197.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.196.
- [LPS-63740]: Update the com.liferay.source.formatter dependency to version
1.0.195.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.194.
- [LPS-65220]: Update the com.liferay.source.formatter dependency to version
1.0.193.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.192.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.191.
- [LPS-65135]: Update the com.liferay.source.formatter dependency to version
1.0.190.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.189.
- [LPS-65086]: Update the com.liferay.gradle.util dependency to version 1.0.25.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.188.
- [LPS-64021]: Update the com.liferay.source.formatter dependency to version
1.0.187.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.186.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.185.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.184.
- [LPS-61443]: Update the com.liferay.source.formatter dependency to version
1.0.183.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.182.
- [LPS-62989]: Update the com.liferay.source.formatter dependency to version
1.0.181.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.180.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.179.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.178.
- [LPS-64761]: Update the com.liferay.source.formatter dependency to version
1.0.177.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.176.
- [LPS-64668]: Update the com.liferay.source.formatter dependency to version
1.0.175.
- [LPS-64654 LPS-64619]: Update the com.liferay.source.formatter dependency to
version 1.0.174.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.173.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.172.
- [LPS-62493]: Update the com.liferay.source.formatter dependency to version
1.0.171.

## 1.0.11 - 2016-06-14

### Commits
- [LPS-55111]: Add missing getters and setters for string properties
(d98c3ce8e5)
- [LPS-66064]: Better glob to distinguish asm-*.jar from asm-commons-*.jar
(d50ced317d)
- [LPS-66064]: Remove use of "-liferay-includeresource" (bbd6b63415)

### Dependencies
- [LPS-66324]: Update the com.liferay.source.formatter dependency to version
1.0.230.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.229.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.228.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.227.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.226.
- [LPS-66242]: Update the com.liferay.source.formatter dependency to version
1.0.225.
- [LPS-66207]: Update the com.liferay.source.formatter dependency to version
1.0.224.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.223.
- [LPS-65693]: Update the com.liferay.source.formatter dependency to version
1.0.222.
- [LPS-66064]: Update the com.liferay.source.formatter dependency to version
1.0.221.
- [LPS-65810]: Update the com.liferay.source.formatter dependency to version
1.0.220.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.219.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.218.
- [LPS-65685]: Update the com.liferay.source.formatter dependency to version
1.0.217.
- [LPS-61420]: Update the com.liferay.source.formatter dependency to version
1.0.216.
- [LPS-65810]: Update the com.liferay.source.formatter dependency to version
1.0.215.

## 1.0.12 - 2016-06-16

### Dependencies
- [LPS-65749]: Update the com.liferay.gradle.util dependency to version 1.0.26.

## 1.0.13 - 2016-06-18

### Commits
- [LRDOCS-2647]: Quote the string default values (566d99a3f0)
- [LRDOCS-2647]: Add JavaExec properties default values (8b38e42e79)
- [LRDOCS-2647]: Consistency (4c16adb1b0)
- [LRDOCS-2647]: Add header (c42231539f)
- [LPS-55111]: Use task description from README (d0f2788769)
- [LPS-55111]: Sync the README version (0c967e1362)
- [LPS-55111]: Automatically sync the README version before publishing
(b232ea3d05)
- [LRDOCS-2647]: There is more than one transitive dependency (1efc77ad51)
- [LRDOCS-2647]: Escape slash character to satisfy Markdown conversion to HTML
(412daa343a)
- [LRDOCS-2647]: Revert to using code syntax highlighting (b874c319b7)
- [LRDOCS-2647]: Edit SF Gradle plugin README (3b8b3c68bb)
- [LPS-55111]: README for gradle-plugins-source-formatter (2da6f7a2de)

## 1.0.14 - 2016-07-05

### Commits
- [LPS-66962]: Make git working branch configurable (8d2a230014)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.236.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.235.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.234.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.233.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.232.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.231.

## 1.0.15 - 2016-07-07

### Commits
- [LPS-67029]: Add "checkSourceFormatting" task (aa9c17b4cc)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.238.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.237.

## 1.0.16 - 2016-07-11

### Commits
- [LPS-66709]: Explain "checkSourceFormatting" task in the README (3dcba273d2)
- [LPS-66709]: Fix gradle-plugins-source-formatter README with latest changes
(bad58a9cd6)
- [LPS-66709]: Fix README versions (3c20581b4e)
- [LPS-66709]: Use boolean pattern in gradle-plugins-source-formatter README
(a6a2b1f6bb)
- [LPS-66709]: Inline code in the README of gradle-plugins-source-formatter
(7b379b5edf)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.240.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.239.

## 1.0.17 - 2016-07-20

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.244.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.243.
- [LPS-67111]: Update the com.liferay.source.formatter dependency to version
1.0.242.
- [LPS-67042]: Update the com.liferay.source.formatter dependency to version
1.0.241.

## 1.0.18 - 2016-07-27

### Commits
- [LPS-67029]: Update README with example (af4b64c97f)
- [LPS-67029]: Remove dependency on "check" task (8805e5b8ec)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.248.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.247.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.246.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.245.

## 1.0.20 - 2016-08-16

### Commits
- [LPS-67544]: Fix Gradle scripts (1a4df141fb)
- [LPS-67029]: Fix link in README (13f3813a8f)

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.258.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.257.
- [LPS-65786]: Update the com.liferay.source.formatter dependency to version
1.0.256.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.255.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.254.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.253.
- [LPS-67111]: Update the com.liferay.source.formatter dependency to version
1.0.252.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.251.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.250.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.249.

## 1.0.21 - 2016-09-06

### Commits
- [LPS-67996]: Add property source.formatter.include.subrepositories
(24521bdfc5)
- [LPS-67658]: Need "compileOnly" to keep dependencies out of "compile"
(4a3cd0bc9d)
- [LPS-67658]: These plugins must work with Gradle 2.5+ (5b963e363d)

### Dependencies
- [LPS-67996]: Update the com.liferay.source.formatter dependency to version
1.0.272.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.271.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.270.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.269.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.268.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.267.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.266.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.265.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.264.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.263.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.262.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.261.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.260.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.259.

## 1.0.22 - 2016-09-26

### Commits
- [LPS-66853]: Add documentation to readme (dfb082d5d0)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.286.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.285.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.284.
- [LPS-68297]: Update the com.liferay.source.formatter dependency to version
1.0.283.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.282.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.281.
- [LPS-68131]: Update the com.liferay.source.formatter dependency to version
1.0.280.
- [LPS-68165]: Update the com.liferay.source.formatter dependency to version
1.0.279.
- [LPS-68131]: Update the com.liferay.source.formatter dependency to version
1.0.278.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.277.
- [LPS-67165]: Update the com.liferay.source.formatter dependency to version
1.0.276.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.275.
- [LPS-68035]: Update the com.liferay.source.formatter dependency to version
1.0.274.
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.273.

## 1.0.23 - 2016-09-27

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.287.

## 1.0.24 - 2016-09-28

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.288.

## 1.0.25 - 2016-09-30

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.289.

## 1.0.26 - 2016-10-04

### Dependencies
- [LPS-68504]: Update the com.liferay.source.formatter dependency to version
1.0.290.

## 1.0.27 - 2016-10-07

### Commits
- [LPS-66709]: Edit READMEs (2072601ff5)
- [LPS-66709]: Add links to portal tools (8baf0882de)

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.293.
- [LPS-68415]: Update the com.liferay.source.formatter dependency to version
1.0.292.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.291.

## 1.0.28 - 2016-10-11

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.294.

## 1.0.29 - 2016-10-11

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.295.

## 1.0.30 - 2016-10-12

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.296.

## 1.0.31 - 2016-10-13

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.297.

## 1.0.32 - 2016-10-13

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.298.

## 1.0.33 - 2016-10-17

### Commits
- [LPS-66709]: Fix typo (887aefbdca)
- [LPS-66709]: Add missing task property (bba930174a)
- [LPS-66709]: Add command-line arguments in the READMEs (4c6dc97741)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.299.

## 1.0.34 - 2016-10-18

### Dependencies
- [LPS-68817]: Update the com.liferay.source.formatter dependency to version
1.0.300.

## 1.0.35 - 2016-10-18

### Dependencies
- [LPS-68779]: Update the com.liferay.source.formatter dependency to version
1.0.301.

## 1.0.36 - 2016-10-19

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.302.

## 1.0.37 - 2016-10-20

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.303.

## 1.0.38 - 2016-10-24

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.304.

## 1.0.40 - 2016-10-25

### Dependencies
- [LPS-52675]: Update the com.liferay.source.formatter dependency to version
1.0.305.

## 1.0.41 - 2016-10-26

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.306.

## 1.0.42 - 2016-10-28

### Dependencies
- [LPS-68995]: Update the com.liferay.source.formatter dependency to version
1.0.308.

## 1.0.43 - 2016-10-31

### Dependencies
- [LPS-68848]: Update the com.liferay.source.formatter dependency to version
1.0.309.

## 1.0.44 - 2016-11-01

### Dependencies
- [LPS-68923]: Update the com.liferay.source.formatter dependency to version
1.0.310.

## 1.0.45 - 2016-11-02

### Dependencies
- [LPS-68923]: Update the com.liferay.source.formatter dependency to version
1.0.311.

## 1.0.46 - 2016-11-03

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.312.

## 1.0.47 - 2016-11-03

### Dependencies
- [LPS-68923]: Update the com.liferay.source.formatter dependency to version
1.0.313.

## 1.0.48 - 2016-11-17

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.314.

## 1.0.49 - 2016-11-21

### Dependencies
- [LPS-69271]: Update the com.liferay.source.formatter dependency to version
1.0.315.

## 1.0.50 - 2016-11-22

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.316.

## 1.0.51 - 2016-11-23

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.317.

## 1.0.52 - 2016-11-24

### Dependencies
- [LPS-69271]: Update the com.liferay.source.formatter dependency to version
1.0.318.

## 1.0.53 - 2016-11-28

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.319.

## 1.0.54 - 2016-12-01

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.320.

## 1.0.55 - 2016-12-01

### Commits
- [LPS-66709]: README typo (283446e516)
- [LPS-66709]: Add supported Gradle versions in READMEs (e0d9458520)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.321.

## 1.0.56 - 2016-12-03

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.322.

## 1.0.57 - 2016-12-05

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.323.

## 1.0.58 - 2016-12-08

### Dependencies
- [LPS-69271]: Update the com.liferay.source.formatter dependency to version
1.0.324.

## 1.0.59 - 2016-12-14

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.325.

## 1.0.60 - 2016-12-19

### Commits
- [LPS-69730]: Recommit (a4ace881cf)
- [LPS-69730]: Revert (1182979b8c)

### Dependencies
- [LPS-69730]: Update the com.liferay.source.formatter dependency to version
1.0.331.
- [LPS-69730]: Update the com.liferay.source.formatter dependency to version
1.0.325.
- [LPS-69730]: Update the com.liferay.source.formatter dependency to version
1.0.331.
- [LPS-69730]: Update the com.liferay.source.formatter dependency to version
1.0.328.
- [LPS-69730]: Update the com.liferay.source.formatter dependency to version
1.0.327.
- [LPS-69730]: Update the com.liferay.source.formatter dependency to version
1.0.326.

## 1.0.66 - 2016-12-20

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.332.

## 1.0.67 - 2016-12-21

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.333.

## 1.0.68 - 2016-12-29

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.334.

## 1.0.69 - 2016-12-29

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.335.

## 1.0.70 - 2017-01-02

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.336.

## 1.0.71 - 2017-01-06

### Dependencies
- [LPS-69706]: Update the com.liferay.source.formatter dependency to version
1.0.338.
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.337.

## 1.0.72 - 2017-01-10

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.339.

## 1.0.73 - 2017-01-10

### Dependencies
- [LPS-70084]: Update the com.liferay.source.formatter dependency to version
1.0.340.

## 1.0.74 - 2017-01-12

### Dependencies
- [LPS-69980]: Update the com.liferay.source.formatter dependency to version
1.0.341.

## 1.0.75 - 2017-01-26

### Dependencies
- [LPS-70274]: Update the com.liferay.source.formatter dependency to version
1.0.342.

## 1.0.76 - 2017-01-29

### Dependencies
- [LPS-70336]: Update the com.liferay.source.formatter dependency to version
1.0.343.

## 1.0.77 - 2017-01-30

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.344.

## 1.0.78 - 2017-01-31

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.345.

## 1.0.79 - 2017-02-02

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.346.

## 1.0.80 - 2017-02-02

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.347.

## 1.0.81 - 2017-02-03

### Dependencies
- [LPS-69271]: Update the com.liferay.source.formatter dependency to version
1.0.348.

## 1.0.82 - 2017-02-08

### Dependencies
- [LPS-70515]: Update the com.liferay.source.formatter dependency to version
1.0.349.

## 1.0.83 - 2017-02-09

### Dependencies
- [LPS-70451]: Update the com.liferay.source.formatter dependency to version
1.0.350.

## 1.0.84 - 2017-02-12

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.351.

## 1.0.85 - 2017-02-12

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.352.

## 1.0.86 - 2017-02-13

### Dependencies
- [LPS-70618]: Update the com.liferay.source.formatter dependency to version
1.0.353.

## 1.0.87 - 2017-02-16

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.354.

## 1.0.88 - 2017-02-17

### Dependencies
- [LPS-70707]: Update the com.liferay.source.formatter dependency to version
1.0.355.

## 1.0.89 - 2017-02-22

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.356.

## 1.0.90 - 2017-02-23

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.357.

## 1.0.91 - 2017-02-25

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.358.

## 1.0.92 - 2017-02-28

### Commits
- [LPS-70941]: Make showing documentation by automatically launching the markdown
file when running SF configurable (ad37297cb9)

### Dependencies
- [LPS-70941]: Update the com.liferay.source.formatter dependency to version
1.0.359.

## 1.0.93 - 2017-03-01

### Commits
- [LPS-70941]: Update readme (159999ad40)

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.360.

## 1.0.94 - 2017-03-02

### Commits
- [LPS-62970]: Add tasks to new "formatting" group (32a4875d06)

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.361.

## 1.0.95 - 2017-03-02

### Dependencies
- [LPS-71005]: Update the com.liferay.source.formatter dependency to version
1.0.362.

## 1.0.96 - 2017-03-06

### Dependencies
- [LPS-71005]: Update the com.liferay.source.formatter dependency to version
1.0.363.

## 1.0.97 - 2017-03-09

### Commits
- [LPS-67573]: Enable semantic versioning check on CI (36750689a4)
- [LPS-66709]: Update supported Gradle versions in READMEs (06e315582b)

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.364.

## 1.0.98 - 2017-03-09

### Dependencies
- [LPS-66853]: Update the com.liferay.source.formatter dependency to version
1.0.365.

## 1.0.99 - 2017-03-11

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.366.

## 1.0.100 - 2017-03-13

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.367.

## 1.0.101 - 2017-03-15

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.368.

## 1.0.102 - 2017-03-16

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.369.

## 1.0.103 - 2017-03-21

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.370.

## 1.0.104 - 2017-03-22

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.371.

## 1.0.105 - 2017-03-24

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.372.

## 1.0.106 - 2017-03-27

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.373.

## 1.0.107 - 2017-03-28

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.374.

## 1.0.108 - 2017-03-30

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.375.

## 1.0.109 - 2017-03-30

### Dependencies
- [LPS-71558]: Update the com.liferay.source.formatter dependency to version
1.0.376.

## 1.0.110 - 2017-04-03

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.377.

## 1.0.112 - 2017-04-04

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.379.

## 1.0.113 - 2017-04-05

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.380.

## 1.0.114 - 2017-04-06

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.381.

## 1.0.115 - 2017-04-11

### Dependencies
- [LPS-71555]: Update the com.liferay.source.formatter dependency to version
1.0.382.

## 1.0.116 - 2017-04-12

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.383.

## 1.0.117 - 2017-04-14

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.384.

## 1.0.118 - 2017-04-17

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.385.

## 1.0.119 - 2017-04-18

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.386.

## 1.0.120 - 2017-04-19

### Dependencies
- [LPS-72030]: Update the com.liferay.source.formatter dependency to version
1.0.387.

## 1.0.121 - 2017-04-20

### Dependencies
- [LPS-72030]: Update the com.liferay.source.formatter dependency to version
1.0.388.

## 1.0.122 - 2017-04-25

### Commits
- [LPS-71164]: Deprecate methods instead of removing (d9754666db)
- [LPS-71164]: Fix compile (d75971b9c2)
- [LPS-71164]: Remove legacy property source.use.properties (c71e9a4522)

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.389.

## 1.0.123 - 2017-05-03

### Commits
- [LPS-72252]: Remove calls to removed methods from deprecated methods
(f7d5331f27)
- [LPS-72252]: Remove/deprecate property "source.copyright.file" (d6c0cef610)

### Dependencies
- [LPS-72326]: Update the com.liferay.source.formatter dependency to version
1.0.390.

## 1.0.124 - 2017-05-03

### Commits
- [LPS-72252]: Update readme (102bc147bc)
- [LPS-67573]: Use Gradle built-in method (10a9e6f017)
- [LPS-67573]: Make methods private to reduce API surface (9125d4f582)
- [LPS-72252]: Remove deprecated methods (1be7e145f6)

## 2.0.1 - 2017-05-05

### Dependencies
- [LPS-72354]: Update the com.liferay.source.formatter dependency to version
1.0.391.

## 2.0.2 - 2017-05-09

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.392.

## 2.0.3 - 2017-05-11

### Dependencies
- [LPS-71164]: Update the com.liferay.source.formatter dependency to version
1.0.393.

## 2.0.4 - 2017-05-13

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.394.

## 2.0.5 - 2017-05-15

### Dependencies
- [LPS-72562]: Update the com.liferay.source.formatter dependency to version
1.0.395.

## 2.0.6 - 2017-05-16

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.396.

## 2.0.7 - 2017-05-19

### Dependencies
- [LPS-72656]: Update the com.liferay.source.formatter dependency to version
1.0.397.

## 2.0.8 - 2017-05-23

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.398.

## 2.0.9 - 2017-05-23

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.399.

## 2.0.10 - 2017-05-25

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.400.

## 2.0.11 - 2017-05-30

### Dependencies
- [LPS-69661]: Update the com.liferay.source.formatter dependency to version
1.0.401.

## 2.0.12 - 2017-05-31

### Dependencies
- [LPS-72606]: Update the com.liferay.source.formatter dependency to version
1.0.402.

## 2.0.13 - 2017-06-04

### Dependencies
- [LPS-72858]: Update the com.liferay.source.formatter dependency to version
1.0.403.

## 2.0.14 - 2017-06-08

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.404.

## 2.0.15 - 2017-06-13

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.405.
- [LPS-72914]: Update the com.liferay.gradle.util dependency to version 1.0.27.

## 2.0.16 - 2017-06-13

### Dependencies
- [LPS-73058]: Update the com.liferay.source.formatter dependency to version
1.0.406.

## 2.0.18 - 2017-06-15

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.407.

## 2.0.19 - 2017-06-16

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.408.

## 2.0.20 - 2017-06-19

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.409.

## 2.0.22 - 2017-06-19

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.410.

## 2.0.23 - 2017-06-23

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.411.

## 2.0.24 - 2017-06-27

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.412.

## 2.0.25 - 2017-06-30

### Dependencies
- [LPS-65930]: Update the com.liferay.source.formatter dependency to version
1.0.413.

## 2.0.26 - 2017-07-04

### Dependencies
- [LPS-73383]: Update the com.liferay.source.formatter dependency to version
1.0.414.

## 2.0.27 - 2017-07-06

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.416.
- [LPS-73467]: Update the com.liferay.source.formatter dependency to version
1.0.415.

## 2.0.28 - 2017-07-07

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.417.

## 2.0.29 - 2017-07-10

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.418.

## 2.0.30 - 2017-07-11

### Commits
- [LPS-73489]: Update readme (72edf6d898)
- [LPS-73489]: Exclude "formatSource" task if it already runs from a parent
(923333da49)

### Dependencies
- [LPS-73261]: Update the com.liferay.source.formatter dependency to version
1.0.420.

## 2.0.32 - 2017-07-11

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.421.

## 2.0.33 - 2017-07-13

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.28.
- [LPS-69673]: Update the com.liferay.source.formatter dependency to version
1.0.422.

## 2.0.34 - 2017-07-13

### Dependencies
- [LPS-73584]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-73584]: Update the com.liferay.source.formatter dependency to version
1.0.423.

## 2.0.35 - 2017-07-14

### Dependencies
- [LPS-73470]: Update the com.liferay.source.formatter dependency to version
1.0.424.

## 2.0.36 - 2017-07-18

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.425.

## 2.0.37 - 2017-07-19

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.426.

## 2.0.38 - 2017-07-19

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.427.

## 2.0.39 - 2017-07-20

### Dependencies
- [LPS-73600]: Update the com.liferay.source.formatter dependency to version
1.0.428.

## 2.0.40 - 2017-07-21

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.429.

## 2.0.41 - 2017-07-24

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.430.

## 2.0.42 - 2017-07-26

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.431.

## 2.0.43 - 2017-07-27

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.432.

## 2.0.44 - 2017-07-31

### Dependencies
- [LPS-73655]: Update the com.liferay.source.formatter dependency to version
1.0.433.

## 2.0.45 - 2017-08-03

### Dependencies
- [LPS-73935]: Update the com.liferay.source.formatter dependency to version
1.0.434.

## 2.0.46 - 2017-08-04

### Dependencies
- [LPS-74034]: Update the com.liferay.source.formatter dependency to version
1.0.435.

## 2.0.47 - 2017-08-07

### Dependencies
- [LPS-74063]: Update the com.liferay.source.formatter dependency to version
1.0.436.

## 2.0.48 - 2017-08-09

### Dependencies
- [LPS-74104]: Update the com.liferay.source.formatter dependency to version
1.0.437.

## 2.0.49 - 2017-08-09

### Dependencies
- [LPS-74088]: Update the com.liferay.source.formatter dependency to version
1.0.438.

## 2.0.50 - 2017-08-11

### Dependencies
- [LPS-73967]: Update the com.liferay.source.formatter dependency to version
1.0.439.

## 2.0.51 - 2017-08-15

### Dependencies
- [LPS-74139]: Update the com.liferay.source.formatter dependency to version
1.0.440.

## 2.0.52 - 2017-08-15

### Dependencies
- [LPS-74139]: Update the com.liferay.source.formatter dependency to version
1.0.441.

## 2.0.53 - 2017-08-16

### Dependencies
- [LPS-74139]: Update the com.liferay.source.formatter dependency to version
1.0.442.

## 2.0.54 - 2017-08-17

### Dependencies
- [LPS-74222]: Update the com.liferay.source.formatter dependency to version
1.0.443.

## 2.0.55 - 2017-08-18

### Dependencies
- [LPS-74126]: Update the com.liferay.source.formatter dependency to version
1.0.444.

## 2.0.56 - 2017-08-22

### Dependencies
- [LPS-74265]: Update the com.liferay.source.formatter dependency to version
1.0.445.

## 2.0.57 - 2017-08-24

### Commits
- [LPS-74314]: Update readme (f07ee04a1f)
- [LPS-74314]: Set showing status updates to true for gradle task formatSource
(b95370a8bf)
- [LPS-74314]: Make showing status updates configurable, set to false by default
(14d3c1a05c)
- [LPS-70941]: Default value for showDocumentation has been changed to false
(a8d68958a5)

## 2.1.0 - 2017-08-24

### Dependencies
- [LPS-74314]: Update the com.liferay.source.formatter dependency to version
1.0.446.

## 2.1.1 - 2017-08-24

### Dependencies
- [LPS-74298]: Update the com.liferay.source.formatter dependency to version
1.0.447.

## 2.1.2 - 2017-08-28

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.448.

## 2.1.3 - 2017-08-28

### Dependencies
- [LPS-74369]: Update the com.liferay.source.formatter dependency to version
1.0.449.

## 2.1.4 - 2017-08-29

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.450.

## 2.1.5 - 2017-08-29

### Dependencies
- [LPS-74433]: Update the com.liferay.source.formatter dependency to version
1.0.451.

## 2.1.6 - 2017-08-31

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.452.

## 2.1.7 - 2017-09-01

### Dependencies
- [LPS-74475]: Update the com.liferay.source.formatter dependency to version
1.0.453.

## 2.1.9 - 2017-09-06

### Dependencies
- [LPS-74538]: Update the com.liferay.source.formatter dependency to version
1.0.454.

## 2.1.10 - 2017-09-07

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.455.

## 2.1.11 - 2017-09-08

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.456.

## 2.1.12 - 2017-09-10

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.457.

## 2.1.13 - 2017-09-11

### Commits
- [LPS-74614]: Update readme (c4a3030e60)
- [LPS-74614]: Make showing debug information configurable, set to false by
default (24ae92076e)

## 2.2.0 - 2017-09-11

### Dependencies
- [LPS-74614]: Update the com.liferay.source.formatter dependency to version
1.0.458.

## 2.2.1 - 2017-09-11

### Dependencies
- [LPS-74373]: Update the com.liferay.source.formatter dependency to version
1.0.459.

## 2.2.2 - 2017-09-12

### Dependencies
- [LPS-74637]: Update the com.liferay.source.formatter dependency to version
1.0.460.

## 2.2.3 - 2017-09-13

### Dependencies
- [LPS-74657]: Update the com.liferay.source.formatter dependency to version
1.0.461.

## 2.2.4 - 2017-09-14

### Dependencies
- [LPS-74614]: Update the com.liferay.source.formatter dependency to version
1.0.462.

## 2.2.5 - 2017-09-18

### Dependencies
- [LPS-74637]: Update the com.liferay.source.formatter dependency to version
1.0.463.

## 2.2.6 - 2017-09-19

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.464.

## 2.2.7 - 2017-09-19

### Dependencies
- [LPS-74657]: Update the com.liferay.source.formatter dependency to version
1.0.465.

## 2.2.8 - 2017-09-19

### Dependencies
- [LPS-74657]: Update the com.liferay.source.formatter dependency to version
1.0.466.

## 2.2.9 - 2017-09-19

### Dependencies
- [LPS-74738]: Update the com.liferay.source.formatter dependency to version
1.0.467.

## 2.2.10 - 2017-09-20

### Dependencies
- [LPS-74657]: Update the com.liferay.source.formatter dependency to version
1.0.468.

## 2.2.11 - 2017-09-26

### Dependencies
- [LPS-74749]: Update the com.liferay.source.formatter dependency to version
1.0.469.

## 2.2.12 - 2017-09-27

### Dependencies
- [LPS-74867]: Update the com.liferay.source.formatter dependency to version
1.0.470.

## 2.2.13 - 2017-10-04

### Commits
- [LPS-74314]: Update readme (ce2817c898)
- [LPS-74314]: The "source.base.dir" needs to end with a slash (bb46919471)
- [LPS-74314]: Read file extensions and names from system properties
(d09d05adb0)
- [LPS-74314]: Allow to pass file extensions to SF via Gradle (fcd879fdf2)

## 2.3.0 - 2017-10-04

### Dependencies
- [LPS-74314]: Update the com.liferay.source.formatter dependency to version
1.0.471.

## 2.3.1 - 2017-10-05

### Dependencies
- [LPS-74872]: Update the com.liferay.source.formatter dependency to version
1.0.472.

## 2.3.2 - 2017-10-08

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.473.

## 2.3.3 - 2017-10-10

### Dependencies
- [LPS-74849]: Update the com.liferay.source.formatter dependency to version
1.0.474.

## 2.3.4 - 2017-10-16

### Dependencies
- [LPS-75254]: Update the com.liferay.source.formatter dependency to version
1.0.475.

## 2.3.5 - 2017-10-17

### Dependencies
- [LPS-74849]: Update the com.liferay.source.formatter dependency to version
1.0.476.

## 2.3.6 - 2017-10-17

### Dependencies
- [LPS-75100]: Update the com.liferay.source.formatter dependency to version
1.0.477.

## 2.3.7 - 2017-10-18

### Dependencies
- [LPS-74849]: Update the com.liferay.source.formatter dependency to version
1.0.478.

## 2.3.8 - 2017-10-18

### Dependencies
- [LPS-74849]: Update the com.liferay.source.formatter dependency to version
1.0.479.

## 2.3.9 - 2017-10-20

### Dependencies
- [LPS-75254]: Update the com.liferay.source.formatter dependency to version
1.0.480.

## 2.3.10 - 2017-10-22

### Dependencies
- [LPS-74457]: Update the com.liferay.source.formatter dependency to version
1.0.481.

## 2.3.11 - 2017-10-23

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.482.

## 2.3.12 - 2017-10-24

### Dependencies
- [LPS-75430]: Update the com.liferay.source.formatter dependency to version
1.0.483.

## 2.3.13 - 2017-10-24

### Dependencies
- [LPS-74457]: Update the com.liferay.source.formatter dependency to version
1.0.484.

## 2.3.14 - 2017-10-25

### Dependencies
- [LPS-74849]: Update the com.liferay.source.formatter dependency to version
1.0.485.

## 2.3.15 - 2017-10-26

### Dependencies
- [LPS-75323]: Update the com.liferay.source.formatter dependency to version
1.0.486.

## 2.3.16 - 2017-10-31

### Dependencies
- [LPS-75488]: Update the com.liferay.source.formatter dependency to version
1.0.487.

## 2.3.17 - 2017-11-01

### Dependencies
- [LPS-75613]: Update the com.liferay.source.formatter dependency to version
1.0.488.

## 2.3.18 - 2017-11-06

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.489.

## 2.3.19 - 2017-11-07

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.490.

## 2.3.20 - 2017-11-07

### Dependencies
- [LPS-74457]: Update the com.liferay.source.formatter dependency to version
1.0.491.

## 2.3.21 - 2017-11-08

### Dependencies
- [LPS-74457]: Update the com.liferay.source.formatter dependency to version
1.0.492.

## 2.3.22 - 2017-11-09

### Dependencies
- [LPS-75610]: Update the com.liferay.source.formatter dependency to version
1.0.493.

## 2.3.23 - 2017-11-12

### Dependencies
- [LPS-75798]: Update the com.liferay.source.formatter dependency to version
1.0.494.

## 2.3.24 - 2017-11-14

### Dependencies
- [LPS-74526]: Update the com.liferay.source.formatter dependency to version
1.0.495.

## 2.3.25 - 2017-11-14

### Dependencies
- [LPS-75798]: Update the com.liferay.source.formatter dependency to version
1.0.496.

## 2.3.26 - 2017-11-15

### Dependencies
- [LPS-75798]: Update the com.liferay.source.formatter dependency to version
1.0.497.

## 2.3.27 - 2017-11-16

### Dependencies
- [LPS-75798]: Update the com.liferay.source.formatter dependency to version
1.0.498.

## 2.3.28 - 2017-11-21

### Dependencies
- [LPS-74457]: Update the com.liferay.source.formatter dependency to version
1.0.499.

## 2.3.29 - 2017-11-24

### Dependencies
- [LPS-76110]: Update the com.liferay.source.formatter dependency to version
1.0.500.

## 2.3.30 - 2017-11-27

### Dependencies
- [LPS-72912]: Update the com.liferay.source.formatter dependency to version
1.0.501.

## 2.3.31 - 2017-11-28

### Dependencies
- [LPS-72912]: Update the com.liferay.source.formatter dependency to version
1.0.502.

## 2.3.32 - 2017-11-28

### Dependencies
- [LPS-75901]: Update the com.liferay.source.formatter dependency to version
1.0.503.

## 2.3.33 - 2017-11-29

### Dependencies
- [LPS-75855]: Update the com.liferay.source.formatter dependency to version
1.0.504.

## 2.3.34 - 2017-11-29

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.505.

## 2.3.35 - 2017-11-30

### Dependencies
- [LPS-75798]: Update the com.liferay.source.formatter dependency to version
1.0.506.

## 2.3.36 - 2017-12-01

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.507.

## 2.3.37 - 2017-12-01

### Dependencies
- [LPS-75798]: Update the com.liferay.source.formatter dependency to version
1.0.508.

## 2.3.38 - 2017-12-05

### Dependencies
- [LPS-76256]: Update the com.liferay.source.formatter dependency to version
1.0.509.

## 2.3.39 - 2017-12-05

### Dependencies
- [LPS-76226]: Update the com.liferay.source.formatter dependency to version
1.0.510.

## 2.3.40 - 2017-12-06

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.511.

## 2.3.41 - 2017-12-07

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.512.

## 2.3.42 - 2017-12-07

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.513.

## 2.3.43 - 2017-12-10

### Dependencies
- [LPS-76326]: Update the com.liferay.source.formatter dependency to version
1.0.514.

## 2.3.44 - 2017-12-12

### Dependencies
- [LPS-72912]: Update the com.liferay.source.formatter dependency to version
1.0.515.

## 2.3.45 - 2017-12-12

### Dependencies
- [LPS-76018]: Update the com.liferay.source.formatter dependency to version
1.0.516.

## 2.3.46 - 2017-12-12

### Dependencies
- [LPS-76018]: Update the com.liferay.source.formatter dependency to version
1.0.517.

## 2.3.47 - 2017-12-13

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.518.

## 2.3.48 - 2017-12-13

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.519.

## 2.3.49 - 2017-12-14

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.520.

## 2.3.50 - 2017-12-19

### Dependencies
- [LPS-76601]: Update the com.liferay.source.formatter dependency to version
1.0.521.

## 2.3.51 - 2017-12-19

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.522.

## 2.3.52 - 2017-12-20

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.523.

## 2.3.53 - 2017-12-21

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.524.

## 2.3.54 - 2017-12-24

### Dependencies
- [LPS-67352]: Update the com.liferay.source.formatter dependency to version
1.0.525.

## 2.3.55 - 2017-12-26

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.526.

## 2.3.56 - 2017-12-29

### Dependencies
- [LPS-76732]: Update the com.liferay.source.formatter dependency to version
1.0.527.

## 2.3.57 - 2018-01-02

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.528.

## 2.3.58 - 2018-01-04

### Commits
- [LPS-76644]: Enable Gradle plugins publishing (8bfdfd53d7)
- [LPS-76644]: Add description to Gradle plugins (5cb7b30e6f)

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.529.

## 2.3.59 - 2018-01-04

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.530.

## 2.3.60 - 2018-01-08

### Dependencies
- [LPS-76840]: Update the com.liferay.source.formatter dependency to version
1.0.531.

## 2.3.61 - 2018-01-09

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.532.

## 2.3.62 - 2018-01-10

### Dependencies
- [LPS-76226]: Update the com.liferay.source.formatter dependency to version
1.0.533.

## 2.3.63 - 2018-01-11

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.534.

## 2.3.64 - 2018-01-11

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.535.

## 2.3.65 - 2018-01-11

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.536.

## 2.3.66 - 2018-01-14

### Dependencies
- [LPS-77110]: Update the com.liferay.source.formatter dependency to version
1.0.537.

## 2.3.67 - 2018-01-22

### Dependencies
- [LPS-77286]: Update the com.liferay.source.formatter dependency to version
1.0.538.

## 2.3.68 - 2018-01-23

### Commits
- [LPS-74544]: Suppress SF status updates for checkSourceFormatting task
(8790ac7d29)

### Dependencies
- [LPS-77402]: Update the com.liferay.source.formatter dependency to version
1.0.539.

## 2.3.69 - 2018-01-23

### Commits
- [LPS-77402]: Update changelog (710a443f0d)

### Dependencies
- [LPS-77400]: Update the com.liferay.source.formatter dependency to version
1.0.540.

## 2.3.70 - 2018-01-23

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.541.

## 2.3.71 - 2018-01-25

### Dependencies
- [LPS-77143]: Update the com.liferay.source.formatter dependency to version
1.0.542.

## 2.3.72 - 2018-01-26

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.543.

## 2.3.73 - 2018-01-29

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.544.

## 2.3.74 - 2018-01-30

### Dependencies
- [LPS-77630]: Update the com.liferay.source.formatter dependency to version
1.0.545.

## 2.3.75 - 2018-02-05

### Dependencies
- [LPS-77795]: Update the com.liferay.source.formatter dependency to version
1.0.547.
- [LPS-77459]: Update the com.liferay.source.formatter dependency to version
1.0.546.

## 2.3.76 - 2018-02-06

### Dependencies
- [LPS-77836]: Update the com.liferay.source.formatter dependency to version
1.0.548.

## 2.3.77 - 2018-02-08

### Dependencies
- [LPS-77886]: Update the com.liferay.source.formatter dependency to version
1.0.549.

## 2.3.78 - 2018-02-11

### Commits
- [LPS-77916]: force release (93f544977c)

### Dependencies
- [LPS-77916]: Update the com.liferay.source.formatter dependency to version
1.0.550.

## 2.3.79 - 2018-02-12

### Dependencies
- [LPS-68297]: Update the com.liferay.source.formatter dependency to version
1.0.551.

## 2.3.80 - 2018-02-14

### Dependencies
- [LPS-78033]: Update the com.liferay.source.formatter dependency to version
1.0.552.

## 2.3.81 - 2018-02-15

### Dependencies
- [LPS-78038]: Update the com.liferay.source.formatter dependency to version
1.0.553.

## 2.3.82 - 2018-02-20

### Dependencies
- [LPS-78071]: Update the com.liferay.source.formatter dependency to version
1.0.554.

## 2.3.83 - 2018-02-21

### Dependencies
- [LPS-78033]: Update the com.liferay.source.formatter dependency to version
1.0.555.

## 2.3.84 - 2018-02-22

### Dependencies
- [LPS-78150]: Update the com.liferay.source.formatter dependency to version
1.0.556.

## 2.3.85 - 2018-02-26

### Dependencies
- [LPS-78231]: Update the com.liferay.source.formatter dependency to version
1.0.557.

## 2.3.86 - 2018-02-26

### Dependencies
- [LPS-75323]: Update the com.liferay.source.formatter dependency to version
1.0.558.

## 2.3.87 - 2018-03-02

### Dependencies
- [LPS-78312]: Update the com.liferay.source.formatter dependency to version
1.0.559.

## 2.3.88 - 2018-03-05

### Dependencies
- [LPS-78312]: Update the com.liferay.source.formatter dependency to version
1.0.560.

## 2.3.89 - 2018-03-07

### Dependencies
- [LPS-78050]: Update the com.liferay.source.formatter dependency to version
1.0.561.

## 2.3.90 - 2018-03-07

### Dependencies
- [LPS-77425]: Update the com.liferay.source.formatter dependency to version
1.0.562.

## 2.3.91 - 2018-03-07

### Dependencies
- [LPS-78459]: Update the com.liferay.source.formatter dependency to version
1.0.563.

## 2.3.92 - 2018-03-08

### Dependencies
- [LPS-78459]: Update the com.liferay.source.formatter dependency to version
1.0.564.

## 2.3.93 - 2018-03-10

### Dependencies
- [LPS-78288]: Update the com.liferay.source.formatter dependency to version
1.0.565.

## 2.3.94 - 2018-03-12

### Dependencies
- [LPS-78308]: Update the com.liferay.source.formatter dependency to version
1.0.566.

## 2.3.95 - 2018-03-12

### Dependencies
- [LPS-78269]: Update the com.liferay.source.formatter dependency to version
1.0.567.

## 2.3.96 - 2018-03-13

### Dependencies
- [LPS-78767]: Update the com.liferay.source.formatter dependency to version
1.0.568.

## 2.3.97 - 2018-03-13

### Dependencies
- [LPS-78767]: Update the com.liferay.source.formatter dependency to version
1.0.569.

## 2.3.98 - 2018-03-16

### Commits
- [LPS-77425]: Partial revert of d25f48516a9ad080bcbd50e228979853d3f2dda5
(60d3a950d6)
- [LPS-77425]: Increment all major versions (d25f48516a)

### Dependencies
- [LPS-78772]: Update the com.liferay.source.formatter dependency to version
1.0.570.
- [LPS-77425]: Update the com.liferay.gradle.util dependency to version 1.0.29.
- [LPS-77425]: Update the com.liferay.source.formatter dependency to version
1.0.569.

## 2.3.99 - 2018-03-17

### Dependencies
- [LPS-78772]: Update the com.liferay.source.formatter dependency to version
1.0.571.

## 2.3.100 - 2018-03-18

### Dependencies
- [LPS-78911]: Update the com.liferay.source.formatter dependency to version
1.0.572.

## 2.3.101 - 2018-03-19

### Dependencies
- [LPS-78772]: Update the com.liferay.source.formatter dependency to version
1.0.573.

## 2.3.102 - 2018-03-19

### Dependencies
- [LPS-78772]: Update the com.liferay.source.formatter dependency to version
1.0.574.

## 2.3.103 - 2018-03-20

### Dependencies
- [LPS-78772]: Update the com.liferay.source.formatter dependency to version
1.0.575.

## 2.3.104 - 2018-03-20

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.576.

## 2.3.105 - 2018-03-20

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.577.

## 2.3.106 - 2018-03-23

### Dependencies
- [LPS-78911]: Update the com.liferay.source.formatter dependency to version
1.0.578.

## 2.3.107 - 2018-03-26

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.579.

## 2.3.108 - 2018-03-26

### Dependencies
- [LPS-79191]: Update the com.liferay.source.formatter dependency to version
1.0.580.

## 2.3.109 - 2018-03-27

### Dependencies
- [LPS-79192]: Update the com.liferay.source.formatter dependency to version
1.0.581.

## 2.3.110 - 2018-03-27

### Dependencies
- [LPS-77577]: Update the com.liferay.source.formatter dependency to version
1.0.582.

## 2.3.111 - 2018-03-27

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.583.

## 2.3.112 - 2018-03-27

### Dependencies
- [LPS-79131]: Update the com.liferay.source.formatter dependency to version
1.0.584.

## 2.3.113 - 2018-03-27

### Dependencies
- [LPS-79226]: Update the com.liferay.source.formatter dependency to version
1.0.585.

## 2.3.114 - 2018-03-29

### Dependencies
- [LPS-79286]: Update the com.liferay.source.formatter dependency to version
1.0.586.

## 2.3.115 - 2018-03-30

### Dependencies
- [LPS-79282]: Update the com.liferay.source.formatter dependency to version
1.0.587.

## 2.3.116 - 2018-03-31

### Dependencies
- [LPS-79248]: Update the com.liferay.source.formatter dependency to version
1.0.588.

## 2.3.117 - 2018-04-02

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.589.

## 2.3.118 - 2018-04-02

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.590.

## 2.3.119 - 2018-04-02

### Dependencies
- [LPS-75010]: Update the com.liferay.source.formatter dependency to version
1.0.591.

## 2.3.120 - 2018-04-03

### Dependencies
- [LPS-79357]: Update the com.liferay.source.formatter dependency to version
1.0.592.

## 2.3.121 - 2018-04-04

### Dependencies
- [LPS-79360]: Update the com.liferay.source.formatter dependency to version
1.0.593.

## 2.3.122 - 2018-04-04

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.594.

## 2.3.123 - 2018-04-06

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.595.
- [LPS-75049]: Update the com.liferay.source.formatter dependency to version
1.0.594.

## 2.3.124 - 2018-04-10

### Dependencies
- [LPS-78308]: Update the com.liferay.source.formatter dependency to version
1.0.596.

## 2.3.125 - 2018-04-10

### Dependencies
- [LPS-78911]: Update the com.liferay.source.formatter dependency to version
1.0.597.

## 2.3.126 - 2018-04-11

### Dependencies
- [LPS-75010]: Update the com.liferay.source.formatter dependency to version
1.0.598.

## 2.3.127 - 2018-04-12

### Commits
- [LPS-79576]: Reverted, but includes changes for LPS-78459 and LPS-74544
(a7f8d0ea5e)
- [LPS-79576]: Release, use this jar's 1.0.599, will revert in next commit and
republish (78ca71d645)

### Dependencies
- [LPS-79576]: Update the com.liferay.source.formatter dependency to version
1.0.600.
- [LPS-79576]: Update the com.liferay.source.formatter dependency to version
1.0.599.

## 2.3.128 - 2018-04-12

### Dependencies
- [LPS-79576]: Update the com.liferay.source.formatter dependency to version
1.0.601.

## 2.3.129 - 2018-04-12

### Dependencies
- [LPS-79562]: Update the com.liferay.source.formatter dependency to version
1.0.602.

## 2.3.130 - 2018-04-13

### Dependencies
- [LPS-79576]: Update the com.liferay.source.formatter dependency to version
1.0.603.

## 2.3.131 - 2018-04-16

### Dependencies
- [LPS-79576]: Update the com.liferay.source.formatter dependency to version
1.0.604.

## 2.3.132 - 2018-04-16

### Dependencies
- [LPS-79576]: Update the com.liferay.source.formatter dependency to version
1.0.605.

## 2.3.133 - 2018-04-17

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.606.

## 2.3.134 - 2018-04-18

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.607.

## 2.3.135 - 2018-04-18

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.608.

## 2.3.136 - 2018-04-19

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.609.

## 2.3.137 - 2018-04-20

### Dependencies
- [LPS-79919]: Update the com.liferay.source.formatter dependency to version
1.0.610.

## 2.3.138 - 2018-04-20

### Dependencies
- [LPS-72705]: Update the com.liferay.source.formatter dependency to version
1.0.611.

## 2.3.139 - 2018-04-22

### Dependencies
- [LPS-75049]: Update the com.liferay.source.formatter dependency to version
1.0.612.

## 2.3.140 - 2018-04-24

### Dependencies
- [LPS-80064]: Update the com.liferay.source.formatter dependency to version
1.0.613.

## 2.3.141 - 2018-04-25

### Dependencies
- [LPS-79963]: Update the com.liferay.source.formatter dependency to version
1.0.614.

## 2.3.142 - 2018-04-29

### Commits
- [LPS-78741]: Readme fixes (a865c2bcf4)

### Dependencies
- [LPS-79755]: Update the com.liferay.source.formatter dependency to version
1.0.615.

## 2.3.143 - 2018-04-30

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.616.

## 2.3.144 - 2018-05-01

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.617.

## 2.3.145 - 2018-05-02

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.618.

## 2.3.146 - 2018-05-02

### Dependencies
- [LPS-80332]: Update the com.liferay.source.formatter dependency to version
1.0.619.

## 2.3.147 - 2018-05-03

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.620.

## 2.3.148 - 2018-05-04

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.621.

## 2.3.149 - 2018-05-06

### Dependencies
- [LPS-80517]: Update the com.liferay.source.formatter dependency to version
1.0.622.

## 2.3.150 - 2018-05-06

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.623.

## 2.3.151 - 2018-05-07

### Dependencies
- [LPS-78312]: Update the com.liferay.source.formatter dependency to version
1.0.624.

## 2.3.152 - 2018-05-10

### Dependencies
- [LPS-80332]: Update the com.liferay.source.formatter dependency to version
1.0.625.

## 2.3.153 - 2018-05-10

### Dependencies
- [LPS-80332]: Update the com.liferay.source.formatter dependency to version
1.0.626.

## 2.3.154 - 2018-05-13

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.627.

## 2.3.155 - 2018-05-14

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.628.

## 2.3.156 - 2018-05-14

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.629.

## 2.3.157 - 2018-05-15

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.630.

## 2.3.158 - 2018-05-15

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.631.

## 2.3.159 - 2018-05-16

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.632.

## 2.3.160 - 2018-05-17

### Dependencies
- [LPS-79240]: Update the com.liferay.source.formatter dependency to version
1.0.633.

## 2.3.161 - 2018-05-17

### Dependencies
- [LPS-81106]: Update the com.liferay.source.formatter dependency to version
1.0.634.

## 2.3.162 - 2018-05-17

### Dependencies
- [LPS-80517]: Update the com.liferay.source.formatter dependency to version
1.0.635.

## 2.3.163 - 2018-05-19

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.636.

## 2.3.164 - 2018-05-21

### Dependencies
- [LPS-79963]: Update the com.liferay.source.formatter dependency to version
1.0.637.

## 2.3.165 - 2018-05-21

### Dependencies
- [LPS-80777]: Update the com.liferay.source.formatter dependency to version
1.0.638.

## 2.3.166 - 2018-05-22

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.639.

## 2.3.167 - 2018-05-23

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.640.

## 2.3.168 - 2018-05-23

### Dependencies
- [LPS-79709]: Update the com.liferay.source.formatter dependency to version
1.0.641.

## 2.3.169 - 2018-05-23

### Commits
- [LPS-74544]: prep enxt (d95903e968)

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.642.

## 2.3.170 - 2018-05-24

### Dependencies
- [LPS-80517]: Update the com.liferay.source.formatter dependency to version
1.0.643.

## 2.3.171 - 2018-05-28

### Dependencies
- [LPS-81555]: Update the com.liferay.source.formatter dependency to version
1.0.644.

## 2.3.172 - 2018-05-29

### Dependencies
- [LPS-81106]: Update the com.liferay.source.formatter dependency to version
1.0.645.

## 2.3.173 - 2018-05-29

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.646.

## 2.3.174 - 2018-05-30

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.647.

## 2.3.175 - 2018-05-31

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.648.

## 2.3.176 - 2018-05-31

### Dependencies
- [LPS-81795]: Update the com.liferay.source.formatter dependency to version
1.0.649.

## 2.3.177 - 2018-06-01

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.650.

## 2.3.178 - 2018-06-04

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.651.

## 2.3.179 - 2018-06-04

### Dependencies
- [LPS-79919]: Update the com.liferay.source.formatter dependency to version
1.0.652.

## 2.3.180 - 2018-06-05

### Dependencies
- [LPS-82001]: Update the com.liferay.source.formatter dependency to version
1.0.653.

## 2.3.181 - 2018-06-06

### Dependencies
- [LPS-82001]: Update the com.liferay.source.formatter dependency to version
1.0.654.

## 2.3.182 - 2018-06-07

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.655.

## 2.3.183 - 2018-06-11

### Dependencies
- [LPS-82121]: Update the com.liferay.source.formatter dependency to version
1.0.656.

## 2.3.184 - 2018-06-11

### Dependencies
- [LPS-82121]: Update the com.liferay.source.formatter dependency to version
1.0.657.

## 2.3.185 - 2018-06-11

### Dependencies
- [LPS-77875]: Update the com.liferay.source.formatter dependency to version
1.0.658.

## 2.3.186 - 2018-06-11

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.659.

## 2.3.187 - 2018-06-13

### Dependencies
- [LPS-82343]: Update the com.liferay.source.formatter dependency to version
1.0.660.

## 2.3.188 - 2018-06-14

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.661.

## 2.3.189 - 2018-06-15

### Dependencies
- [LPS-82469]: Update the com.liferay.source.formatter dependency to version
1.0.662.

## 2.3.190 - 2018-06-15

### Dependencies
- [LPS-76226]: Update the com.liferay.source.formatter dependency to version
1.0.663.

## 2.3.191 - 2018-06-18

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.664.

## 2.3.192 - 2018-06-19

### Dependencies
- [LPS-82420]: Update the com.liferay.source.formatter dependency to version
1.0.665.

## 2.3.193 - 2018-06-20

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.666.

## 2.3.194 - 2018-06-20

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.667.

## 2.3.195 - 2018-06-21

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.668.

## 2.3.196 - 2018-06-25

### Dependencies
- [LPS-82828]: Update the com.liferay.source.formatter dependency to version
1.0.669.

## 2.3.197 - 2018-06-26

### Dependencies
- [LPS-82828]: Update the com.liferay.source.formatter dependency to version
1.0.670.

## 2.3.198 - 2018-06-27

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.671.

## 2.3.199 - 2018-06-28

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.672.

## 2.3.200 - 2018-06-29

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.673.

## 2.3.201 - 2018-07-02

### Dependencies
- [LPS-79679]: Update the com.liferay.source.formatter dependency to version
1.0.674.

## 2.3.202 - 2018-07-03

### Dependencies
- [LPS-82828]: Update the com.liferay.source.formatter dependency to version
1.0.675.

## 2.3.203 - 2018-07-11

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.676.

## 2.3.204 - 2018-07-11

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.677.

## 2.3.205 - 2018-07-13

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.678.

## 2.3.206 - 2018-07-13

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.679.

## 2.3.207 - 2018-07-13

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.680.

## 2.3.208 - 2018-07-14

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.681.

## 2.3.209 - 2018-07-16

### Dependencies
- [LPS-77699]: Update the com.liferay.source.formatter dependency to version
1.0.682.

## 2.3.210 - 2018-07-16

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.683.

## 2.3.211 - 2018-07-16

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.684.

## 2.3.212 - 2018-07-17

### Dependencies
- [LPS-77699]: Update the com.liferay.source.formatter dependency to version
1.0.685.

## 2.3.213 - 2018-07-17

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.686.

## 2.3.214 - 2018-07-18

### Dependencies
- [LPS-83576]: Update the com.liferay.source.formatter dependency to version
1.0.687.

## 2.3.215 - 2018-07-18

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.688.

## 2.3.216 - 2018-07-19

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.689.

## 2.3.217 - 2018-07-24

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.690.

## 2.3.218 - 2018-07-24

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.691.

## 2.3.219 - 2018-07-25

### Dependencies
- [LPS-83705]: Update the com.liferay.source.formatter dependency to version
1.0.692.

## 2.3.220 - 2018-07-27

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.693.

## 2.3.221 - 2018-07-30

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.694.

## 2.3.222 - 2018-07-31

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.695.

## 2.3.223 - 2018-08-01

### Dependencies
- [LPS-84039]: Update the com.liferay.source.formatter dependency to version
1.0.696.

## 2.3.224 - 2018-08-01

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.697.

## 2.3.225 - 2018-08-01

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.698.

## 2.3.226 - 2018-08-02

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.699.

## 2.3.227 - 2018-08-05

### Commits
- [LPS-74544]: just published to test out
https://github.com/brianchandotcom/liferay-portal/pull/61854 (68c7d50e6c)

### Dependencies
- [LPS-83705]: Update the com.liferay.source.formatter dependency to version
1.0.701.
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.700.

## 2.3.228 - 2018-08-06

### Dependencies
- [LPS-78033]: Update the com.liferay.source.formatter dependency to version
1.0.702.

## 2.3.229 - 2018-08-06

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.703.

## 2.3.230 - 2018-08-06

### Dependencies
- [LPS-84213]: Update the com.liferay.source.formatter dependency to version
1.0.704.

## 2.3.231 - 2018-08-06

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.705.

## 2.3.232 - 2018-08-07

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.706.

## 2.3.233 - 2018-08-08

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.707.

## 2.3.234 - 2018-08-09

### Dependencies
- [LPS-84307]: Update the com.liferay.source.formatter dependency to version
1.0.708.

## 2.3.235 - 2018-08-10

### Dependencies
- [LPS-84039]: Update the com.liferay.source.formatter dependency to version
1.0.709.

## 2.3.236 - 2018-08-13

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.710.

## 2.3.237 - 2018-08-15

### Dependencies
- [LPS-84039]: Update the com.liferay.source.formatter dependency to version
1.0.711.

## 2.3.238 - 2018-08-22

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.712.

## 2.3.239 - 2018-08-23

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.713.

## 2.3.240 - 2018-08-27

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.714.

## 2.3.241 - 2018-08-27

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.715.

## 2.3.242 - 2018-08-29

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.716.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.30.

## 2.3.243 - 2018-08-30

### Dependencies
- [LPS-84756]: Update the com.liferay.source.formatter dependency to version
1.0.717.
- [LPS-84094]: Update the com.liferay.gradle.util dependency to version 1.0.31.

## 2.3.244 - 2018-09-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.718.

## 2.3.245 - 2018-09-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.719.

## 2.3.246 - 2018-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.720.

## 2.3.247 - 2018-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.721.

## 2.3.248 - 2018-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.722.

## 2.3.249 - 2018-09-05

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.723.

## 2.3.250 - 2018-09-06

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.724.

## 2.3.251 - 2018-09-10

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.725.

## 2.3.252 - 2018-09-10

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.726.

## 2.3.253 - 2018-09-11

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.727.

## 2.3.254 - 2018-09-11

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.728.

## 2.3.255 - 2018-09-11

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.729.

## 2.3.256 - 2018-09-12

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.730.

## 2.3.259 - 2018-09-17

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.732.

## 2.3.260 - 2018-09-17

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.733.

## 2.3.261 - 2018-09-18

### Commits
- [LPS-74544]: Add gradleTest (7fd329ea62)

### Dependencies
- [LPS-85035]: Update the com.liferay.source.formatter dependency to version
1.0.734.

## 2.3.262 - 2018-09-18

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.735.

## 2.3.263 - 2018-09-18

### Commits
- [LPS-71117]: Test plugins with Gradle up to 3.5.1 (c3e12d1cf3)
- [LPS-71117]: Update supported Gradle versions in READMEs (fdcc16c0d4)

### Dependencies
- [LPS-85296]: Update the com.liferay.source.formatter dependency to version
1.0.736.

## 2.3.264 - 2018-09-19

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.737.

## 2.3.265 - 2018-09-20

### Commits
- [LPS-71117]: Workaround for StringIndexOutOfBoundsException (bug in Gradle
3.5.1) (02bbe5aa4d)

### Dependencies
- [LPS-71117]: Update the com.liferay.source.formatter dependency to version
1.0.738.

## 2.3.266 - 2018-09-24

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.739.

## 2.3.267 - 2018-09-25

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.740.

## 2.3.268 - 2018-09-25

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.741.

## 2.3.269 - 2018-10-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.743.
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.742.

## 2.3.270 - 2018-10-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.744.

## 2.3.271 - 2018-10-04

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.745.

## 2.3.272 - 2018-10-07

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.746.

## 2.3.273 - 2018-10-08

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.747.

## 2.3.274 - 2018-10-09

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.748.

## 2.3.275 - 2018-10-09

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.749.

## 2.3.276 - 2018-10-15

### Dependencies
- [LPS-86362]: Update the com.liferay.source.formatter dependency to version
1.0.750.

## 2.3.277 - 2018-10-15

### Dependencies
- [LPS-86324]: Update the com.liferay.source.formatter dependency to version
1.0.751.

## 2.3.278 - 2018-10-17

### Dependencies
- [LPS-86413]: Update the com.liferay.source.formatter dependency to version
1.0.752.

## 2.3.279 - 2018-10-18

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.753.

## 2.3.280 - 2018-10-18

### Dependencies
- [LPS-86493]: Update the com.liferay.source.formatter dependency to version
1.0.754.

## 2.3.281 - 2018-10-22

### Commits
- [LPS-86589]: Update readme (4280a3d596)
- [LPS-86589]: Test Gradle plugins from Gradle 2.14.1 to 3.5.1 (6df521a506)

### Dependencies
- [LPS-86556]: Update the com.liferay.source.formatter dependency to version
1.0.755.

## 2.3.282 - 2018-11-13

### Commits
- [LPS-87293]: Print standard output to the console (bdcf435369)
- [LPS-87293]: Simplify (no logic changes) (14557852fd)
- [LPS-87192]: Set the Eclipse task property gradleVersion (040b2abdee)
- [LPS-87192]: Add variable gradleVersion (no logic changes) (2f7c0b2fe4)
- [LPS-85609]: Fix for CI (test only 4.10.2) (4eed005731)
- [LPS-85609]: Test plugins up to Gradle 4.10.2 (60905bc960)
- [LPS-85609]: Update supported Gradle versions (d79b89682b)

### Dependencies
- [LPS-87293]: Update the com.liferay.source.formatter dependency to version
1.0.756.

## 2.3.283 - 2018-11-16

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.32.

## 2.3.284 - 2018-11-19

### Dependencies
- [LPS-87466]: Update the com.liferay.gradle.util dependency to version 1.0.33.

## 2.3.285 - 2018-11-19

### Dependencies
- [LPS-87503]: Update the com.liferay.source.formatter dependency to version
1.0.757.

## 2.3.286 - 2018-11-20

### Commits
- [LPS-85609]: Use Gradle 4.10.2 (9aa90f8961)

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.758.

## 2.3.287 - 2018-11-28

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.759.

## 2.3.288 - 2018-12-03

### Dependencies
- [LPS-85828]: Update the com.liferay.source.formatter dependency to version
1.0.760.

## 2.3.289 - 2018-12-04

### Dependencies
- [LPS-88171]: Update the com.liferay.source.formatter dependency to version
1.0.761.

## 2.3.290 - 2018-12-04

### Dependencies
- [LPS-87471]: Update the com.liferay.source.formatter dependency to version
1.0.762.

## 2.3.291 - 2018-12-05

### Dependencies
- [LPS-88186]: Update the com.liferay.source.formatter dependency to version
1.0.763.

## 2.3.292 - 2018-12-05

### Dependencies
- [LPS-88223]: Update the com.liferay.source.formatter dependency to version
1.0.764.

## 2.3.293 - 2018-12-06

### Dependencies
- [LPS-88186]: Update the com.liferay.source.formatter dependency to version
1.0.765.

## 2.3.294 - 2018-12-07

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.766.

## 2.3.295 - 2018-12-10

### Dependencies
- [LPS-88171]: Update the com.liferay.source.formatter dependency to version
1.0.767.

## 2.3.297 - 2018-12-13

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.768.

## 2.3.298 - 2019-01-02

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.769.

## 2.3.299 - 2019-01-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.770.

## 2.3.300 - 2019-01-08

### Commits
- [LPS-85609]: Update readme (c182ff396d)
- [LPS-85609]: Simplify gradleTest (a8b0feff31)

### Dependencies
- [LPS-87469]: Update the com.liferay.source.formatter dependency to version
1.0.771.

## 2.3.301 - 2019-01-11

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.772.

## 2.3.302 - 2019-01-13

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.773.

## 2.3.303 - 2019-01-16

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.774.

## 2.3.304 - 2019-01-17

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.775.

## 2.3.305 - 2019-01-22

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.776.

## 2.3.306 - 2019-01-23

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.777.

## 2.3.307 - 2019-01-28

### Dependencies
- [LPS-89415]: Update the com.liferay.source.formatter dependency to version
1.0.778.

## 2.3.308 - 2019-01-31

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.779.

## 2.3.309 - 2019-02-04

### Dependencies
- [LPS-89415]: Update the com.liferay.source.formatter dependency to version
1.0.780.

## 2.3.310 - 2019-02-12

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.781.

## 2.3.311 - 2019-02-12

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.782.

## 2.3.312 - 2019-02-13

### Dependencies
- [LPS-86853]: Update the com.liferay.source.formatter dependency to version
1.0.783.

## 2.3.313 - 2019-02-13

### Dependencies
- [LPS-90378]: Update the com.liferay.source.formatter dependency to version
1.0.784.

## 2.3.314 - 2019-02-14

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.785.

## 2.3.315 - 2019-02-15

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.786.

## 2.3.316 - 2019-02-18

### Dependencies
- [LPS-90380]: Update the com.liferay.source.formatter dependency to version
1.0.787.

## 2.3.317 - 2019-02-19

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.788.

## 2.3.318 - 2019-02-20

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.789.

## 2.3.319 - 2019-02-20

### Dependencies
- [LPS-89637]: Update the com.liferay.source.formatter dependency to version
1.0.790.

## 2.3.320 - 2019-02-22

### Dependencies
- [LPS-74544]: Update the com.liferay.source.formatter dependency to version
1.0.791.

## 2.3.321 - 2019-02-28

### Dependencies
- [LPS-91420]: Update the com.liferay.source.formatter dependency to version
1.0.792.

## 2.3.322 - 2019-03-04

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.793.

## 2.3.323 - 2019-03-04

### Dependencies
- [LPS-91420]: Update the com.liferay.source.formatter dependency to version
1.0.794.

## 2.3.324 - 2019-03-05

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.795.

## 2.3.325 - 2019-03-06

### Dependencies
- [LPS-89415]: Update the com.liferay.source.formatter dependency to version
1.0.796.

## 2.3.326 - 2019-03-07

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.797.

## 2.3.327 - 2019-03-10

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.798.

## 2.3.328 - 2019-03-11

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.799.

## 2.3.329 - 2019-03-12

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.800.

## 2.3.330 - 2019-03-15

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.801.

## 2.3.331 - 2019-03-15

### Dependencies
- [LPS-91420]: Update the com.liferay.source.formatter dependency to version
1.0.802.

## 2.3.332 - 2019-03-18

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.803.

## 2.3.333 - 2019-03-19

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.804.

## 2.3.334 - 2019-03-19

### Dependencies
- [LPS-92311]: Update the com.liferay.source.formatter dependency to version
1.0.805.

## 2.3.335 - 2019-03-21

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.806.

## 2.3.336 - 2019-03-21

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.807.

## 2.3.337 - 2019-03-22

### Dependencies
- [LPS-91549]: Update the com.liferay.source.formatter dependency to version
1.0.808.

## 2.3.338 - 2019-03-25

### Dependencies
- [LPS-92223]: Update the com.liferay.source.formatter dependency to version
1.0.809.

## 2.3.339 - 2019-03-26

### Dependencies
- [LPS-91420]: Update the com.liferay.source.formatter dependency to version
1.0.810.

## 2.3.340 - 2019-03-27

### Dependencies
- [LPS-92568]: Update the com.liferay.source.formatter dependency to version
1.0.811.

## 2.3.341 - 2019-03-27

### Dependencies
- [LPS-88911]: Update the com.liferay.source.formatter dependency to version
1.0.812.

## 2.3.342 - 2019-03-27

### Commits
- [LPS-91772]: this is a publish of a new SF to revert the old one (aa59ffa0ea)

### Dependencies
- [LPS-91772]: Update the com.liferay.source.formatter dependency to version
1.0.813.

## 2.3.343 - 2019-03-28

### Dependencies
- [LPS-91420]: Update the com.liferay.source.formatter dependency to version
1.0.814.

## 2.3.344 - 2019-03-28

### Dependencies
- [LPS-91967]: Update the com.liferay.source.formatter dependency to version
1.0.815.

## 2.3.345 - 2019-03-31

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.816.

## 2.3.346 - 2019-04-01

### Dependencies
- [LPS-91772]: Update the com.liferay.source.formatter dependency to version
1.0.817.

## 2.3.347 - 2019-04-05

### Dependencies
- [LPS-86596]: Update the com.liferay.source.formatter dependency to version
1.0.818.

## 2.3.348 - 2019-04-07

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.819.

## 2.3.349 - 2019-04-09

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.820.

## 2.3.350 - 2019-04-11

### Dependencies
- [LPS-93506]: Update the com.liferay.source.formatter dependency to version
1.0.821.

## 2.3.351 - 2019-04-11

### Dependencies
- [LPS-93707]: Update the com.liferay.source.formatter dependency to version
1.0.822.

## 2.3.352 - 2019-04-14

### Dependencies
- [LPS-91295]: Update the com.liferay.source.formatter dependency to version
1.0.823.

## 2.3.353 - 2019-04-15

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.824.

## 2.3.354 - 2019-04-16

### Dependencies
- [LPS-93265]: Update the com.liferay.source.formatter dependency to version
1.0.825.

## 2.3.355 - 2019-04-17

### Dependencies
- [LPS-94033]: Update the com.liferay.source.formatter dependency to version
1.0.826.

## 2.3.356 - 2019-04-18

### Dependencies
- [LPS-88911]: Update the com.liferay.source.formatter dependency to version
1.0.827.

## 2.3.357 - 2019-04-22

### Commits
- [LPS-94523]: Not able to publish this jar, try upping it a version
(bb63d16e20)

### Dependencies
- [LPS-94523]: Update the com.liferay.source.formatter dependency to version
1.0.829.
- [LPS-94523]: Update the com.liferay.source.formatter dependency to version
1.0.828.

## 2.3.359 - 2019-04-22

### Dependencies
- [LPS-94466]: Update the com.liferay.source.formatter dependency to version
1.0.830.

## 2.3.360 - 2019-04-23

### Dependencies
- [LPS-93513]: Update the com.liferay.source.formatter dependency to version
1.0.831.

## 2.3.361 - 2019-04-25

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.832.

## 2.3.362 - 2019-04-26

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.833.

## 2.3.363 - 2019-04-29

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.834.

## 2.3.364 - 2019-04-30

### Dependencies
- [LPS-93505]: Update the com.liferay.source.formatter dependency to version
1.0.835.

## 2.3.365 - 2019-05-01

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.836.

## 2.3.366 - 2019-05-01

### Dependencies
- [LPS-94764]: Update the com.liferay.source.formatter dependency to version
1.0.837.

## 2.3.367 - 2019-05-01

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.838.

## 2.3.368 - 2019-05-02

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.839.

## 2.3.369 - 2019-05-06

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.840.

## 2.3.370 - 2019-05-06

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.841.

## 2.3.371 - 2019-05-07

### Dependencies
- [LPS-91241]: Update the com.liferay.source.formatter dependency to version
1.0.842.

## 2.3.372 - 2019-05-08

### Dependencies
- [LPS-94948]: Update the com.liferay.source.formatter dependency to version
1.0.843.

## 2.3.373 - 2019-05-09

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.844.

## 2.3.374 - 2019-05-09

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.845.

## 2.3.375 - 2019-05-13

### Dependencies
- [LPS-84119 LPS-91420]: Update the com.liferay.source.formatter dependency to
version 1.0.846.

## 2.3.376 - 2019-05-14

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.847.

## 2.3.377 - 2019-05-15

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.848.

## 2.3.378 - 2019-05-15

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.849.

## 2.3.379 - 2019-05-16

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.850.

## 2.3.380 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.851.

## 2.3.381 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.852.

## 2.3.382 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.853.

## 2.3.383 - 2019-05-20

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.854.

## 2.3.384 - 2019-05-21

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.855.

## 2.3.385 - 2019-05-22

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.856.

## 2.3.386 - 2019-05-23

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.857.

## 2.3.387 - 2019-05-23

### Dependencies
- [LPS-95915]: Update the com.liferay.source.formatter dependency to version
1.0.858.

## 2.3.388 - 2019-05-27

### Dependencies
- [LPS-96091]: Update the com.liferay.source.formatter dependency to version
1.0.859.

## 2.3.389 - 2019-05-30

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.860.

## 2.3.390 - 2019-06-01

### Dependencies
- [LPS-96206]: Update the com.liferay.source.formatter dependency to version
1.0.862.
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.861.

## 2.3.391 - 2019-06-03

### Dependencies
- [LPS-96376]: Update the com.liferay.source.formatter dependency to version
1.0.863.

## 2.3.392 - 2019-06-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.864.

## 2.3.393 - 2019-06-04

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.865.

## 2.3.394 - 2019-06-05

### Dependencies
- [LPS-96198]: Update the com.liferay.source.formatter dependency to version
1.0.866.

## 2.3.395 - 2019-06-10

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.867.

## 2.3.396 - 2019-06-11

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.868.

## 2.3.397 - 2019-06-11

### Dependencies
- [LPS-93510]: Update the com.liferay.source.formatter dependency to version
1.0.869.

## 2.3.398 - 2019-06-12

### Dependencies
- [LPS-96566]: Update the com.liferay.source.formatter dependency to version
1.0.870.

## 2.3.399 - 2019-06-13

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.871.

## 2.3.400 - 2019-06-17

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.872.

## 2.3.401 - 2019-06-17

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.873.

## 2.3.402 - 2019-06-19

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.874.

## 2.3.403 - 2019-06-23

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.875.
- [LPS-96247]: Update the com.liferay.gradle.util dependency to version 1.0.34.

## 2.3.404 - 2019-06-24

### Dependencies
- [LPS-96911]: Update the com.liferay.source.formatter dependency to version
1.0.876.

## 2.3.405 - 2019-06-25

### Dependencies
- [RELEASE-1607]: Update the com.liferay.source.formatter dependency to version
1.0.877.

## 2.3.406 - 2019-06-26

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.878.

## 2.3.407 - 2019-06-26

### Dependencies
- [LPS-93507]: Update the com.liferay.source.formatter dependency to version
1.0.879.

## 2.3.408 - 2019-07-01

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.880.

## 2.3.409 - 2019-07-01

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.881.

## 2.3.410 - 2019-07-01

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.882.

## 2.3.411 - 2019-07-04

### Dependencies
- [LPS-94301]: Update the com.liferay.source.formatter dependency to version
1.0.883.

## 2.3.412 - 2019-07-08

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.884.

## 2.3.413 - 2019-07-08

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.885.

## 2.3.414 - 2019-07-11

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.886.

## 2.3.415 - 2019-07-15

### Dependencies
- [LPS-98198]: Update the com.liferay.source.formatter dependency to version
1.0.887.

## 2.3.416 - 2019-07-16

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.888.

## 2.3.417 - 2019-07-18

### Dependencies
- [LPS-98409]: Update the com.liferay.source.formatter dependency to version
1.0.889.

## 2.3.418 - 2019-07-21

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.890.

## 2.3.419 - 2019-07-22

### Dependencies
- [LPS-98024]: Update the com.liferay.source.formatter dependency to version
1.0.891.

## 2.3.420 - 2019-07-25

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.892.

## 2.3.421 - 2019-07-26

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.893.

## 2.3.422 - 2019-07-29

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.894.

## 2.3.423 - 2019-07-30

### Dependencies
- [LPS-98190]: Update the com.liferay.source.formatter dependency to version
1.0.895.

## 2.3.424 - 2019-07-30

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.896.

## 2.3.425 - 2019-08-03

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.897.

## 2.3.426 - 2019-08-05

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.898.

## 2.3.427 - 2019-08-07

### Dependencies
- [LPS-99437]: Update the com.liferay.source.formatter dependency to version
1.0.899.

## 2.3.428 - 2019-08-07

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.900.

## 2.3.429 - 2019-08-15

### Dependencies
- [LPS-98468]: Update the com.liferay.source.formatter dependency to version
1.0.901.

## 2.3.430 - 2019-08-19

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.902.

## 2.3.431 - 2019-08-20

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.903.

## 2.3.432 - 2019-08-21

### Dependencies
- [LPS-99917]: Update the com.liferay.source.formatter dependency to version
1.0.904.

## 2.3.433 - 2019-08-21

### Dependencies
- [LPS-99657]: Update the com.liferay.source.formatter dependency to version
1.0.905.

## 2.3.434 - 2019-08-27

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.907.
- [LPS-100170]: Update the com.liferay.source.formatter dependency to version
1.0.906.

## 2.3.435 - 2019-08-29

### Dependencies
- [LPS-99898]: Update the com.liferay.source.formatter dependency to version
1.0.908.

## 2.3.436 - 2019-09-02

### Dependencies
- [LPS-99898]: Update the com.liferay.source.formatter dependency to version
1.0.909.

## 2.3.437 - 2019-09-04

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.910.

## 2.3.438 - 2019-09-05

### Dependencies
- [LPS-99898]: Update the com.liferay.source.formatter dependency to version
1.0.911.

## 2.3.439 - 2019-09-11

### Dependencies
- [LPS-101089]: Update the com.liferay.source.formatter dependency to version
1.0.912.

## 2.3.440 - 2019-09-13

### Dependencies
- [LPS-101549]: Update the com.liferay.source.formatter dependency to version
1.0.913.

## 2.3.441 - 2019-09-16

### Dependencies
- [LPS-86806]: Update the com.liferay.source.formatter dependency to version
1.0.914.

## 2.3.442 - 2019-09-18

### Dependencies
- [LPS-93513]: Update the com.liferay.source.formatter dependency to version
1.0.915.

## 2.3.443 - 2019-09-21

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.916.

## 2.3.444 - 2019-09-24

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.917.

## 2.3.445 - 2019-09-27

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.918.

## 2.3.446 - 2019-10-03

### Commits
- [LPS-102700]: Fix bnd error (include literal dot) (d65985bae3)

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.919.

## 2.3.447 - 2019-10-03

### Dependencies
- [LPS-101108]: Update the com.liferay.source.formatter dependency to version
1.0.920.

## 2.3.448 - 2019-10-08

### Dependencies
- [LRCI-642]: Update the com.liferay.source.formatter dependency to version
1.0.921.

## 2.3.449 - 2019-10-13

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.922.

## 2.3.450 - 2019-10-15

### Dependencies
- [LPS-100969]: Update the com.liferay.source.formatter dependency to version
1.0.923.

## 2.3.451 - 2019-10-21

### Dependencies
- [LPS-103170]: Update the com.liferay.source.formatter dependency to version
1.0.924.

## 2.3.452 - 2019-10-21

### Dependencies
- [LPS-102481]: Update the com.liferay.source.formatter dependency to version
1.0.925.

## 2.3.453 - 2019-10-21

### Dependencies
- [LPS-103461]: Update the com.liferay.source.formatter dependency to version
1.0.926.

## 2.3.454 - 2019-10-21

### Dependencies
- [LPS-103461]: Update the com.liferay.source.formatter dependency to version
1.0.927.

## 2.3.455 - 2019-10-21

### Dependencies
- [LPS-98640]: Update the com.liferay.source.formatter dependency to version
1.0.928.

## 2.3.456 - 2019-10-23

### Dependencies
- [LPS-103170]: Update the com.liferay.source.formatter dependency to version
1.0.929.

## 2.3.457 - 2019-10-29

### Dependencies
- [LPS-103843]: Update the com.liferay.source.formatter dependency to version
1.0.930.

## 2.3.458 - 2019-10-30

### Dependencies
- [LPS-103466]: Update the com.liferay.source.formatter dependency to version
1.0.931.

## 2.3.459 - 2019-10-30

### Commits
- [LPS-103466]: revert (e4f42e7a25)

### Dependencies
- [LPS-103466]: Update the com.liferay.source.formatter dependency to version
1.0.932.

## 2.3.460 - 2019-11-01

### Dependencies
- [LPS-103252]: Update the com.liferay.source.formatter dependency to version
1.0.933.

## 2.3.461 - 2019-11-04

### Dependencies
- [LPS-103872]: Update the com.liferay.source.formatter dependency to version
1.0.934.

## 2.3.462 - 2019-11-04

### Dependencies
- [LPS-103466]: Update the com.liferay.source.formatter dependency to version
1.0.935.

## 2.3.463 - 2019-11-05

### Commits
- [LPS-103252]: regen (f509144a7d)

### Dependencies
- [LPS-103252]: Update the com.liferay.source.formatter dependency to version
1.0.936.

## 2.3.464 - 2019-11-07

### Dependencies
- [LPS-102481]: Update the com.liferay.source.formatter dependency to version
1.0.937.

## 2.3.465 - 2019-11-13

### Dependencies
- [LPS-104435]: Update the com.liferay.source.formatter dependency to version
1.0.938.

## 2.3.466 - 2019-11-14

### Dependencies
- [LPS-104606]: Update the com.liferay.source.formatter dependency to version
1.0.939.

## 2.3.467 - 2019-11-18

### Dependencies
- [LPS-104679]: Update the com.liferay.source.formatter dependency to version
1.0.940.

## 2.3.469 - 2019-11-19

### Dependencies
- [LPS-104435]: Update the com.liferay.source.formatter dependency to version
1.0.941.

## 2.3.470 - 2019-11-20

### Dependencies
- [LPS-104606]: Update the com.liferay.source.formatter dependency to version
1.0.942.

## 2.3.471 - 2019-11-20

### Dependencies
- [LPS-103252]: Update the com.liferay.source.formatter dependency to version
1.0.943.

## 2.3.472 - 2019-11-21

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.944.

## 2.3.473 - 2019-11-25

### Dependencies
- [LPS-84119]: Update the com.liferay.source.formatter dependency to version
1.0.945.

## 2.3.474 - 2019-11-27

### Commits
- [LPS-100515]: Update README.markdown (694b3791de)
- [LPS-100515]: Update plugins Gradle version (448efac158)

### Dependencies
- [LPS-104679]: Update the com.liferay.source.formatter dependency to version
1.0.946.

## 2.3.475 - 2019-12-03

### Dependencies
- [LPS-103252]: Update the com.liferay.source.formatter dependency to version
1.0.947.

## 2.3.476 - 2019-12-09

### Dependencies
- [LPS-105290 LPS-105237]: Update the com.liferay.source.formatter dependency to
version 1.0.948.

## 2.3.477 - 2019-12-20

### Dependencies
- [LPS-106079]: Update the com.liferay.source.formatter dependency to version
1.0.949.

## 2.3.478 - 2019-12-24

### Dependencies
- [LPS-105380]: Update the com.liferay.source.formatter dependency to version
1.0.950.

## 2.3.479 - 2019-12-24

### Dependencies
- [LPS-105380]: Update the com.liferay.source.formatter dependency to version
1.0.951.

## 2.3.480 - 2020-01-06

### Commits
- [LPS-105380]: Fix incorrect double space (d735e6aff5)

### Dependencies
- [LPS-105380]: Update the com.liferay.source.formatter dependency to version
1.0.952.

## 2.3.481 - 2020-01-07

### Commits
- [LPS-106315 LPS-94003]: publish SF required changes (43df93d5ff)

### Dependencies
- [LPS-106315 LPS-94003]: Update the com.liferay.source.formatter dependency to
version 1.0.953.

## 2.3.482 - 2020-01-11

### Dependencies
- [LPS-104435]: Update the com.liferay.source.formatter dependency to version
1.0.954.

## 2.3.483 - 2020-01-15

### Dependencies
- [LPS-104435]: Update the com.liferay.source.formatter dependency to version
1.0.955.

## 2.3.484 - 2020-01-17

### Commits
- [LPS-105380]: Rename exception variables (b3173da81b)

### Dependencies
- [LPS-105380]: Update the com.liferay.source.formatter dependency to version
1.0.956.

## 2.3.485 - 2020-01-20

### Dependencies
- [LPS-107321]: Update the com.liferay.source.formatter dependency to version
1.0.957.

## 2.3.486 - 2020-01-28

### Dependencies
- [LPS-104679]: Update the com.liferay.source.formatter dependency to version
1.0.958.

## 2.3.487 - 2020-02-06

### Dependencies
- [LPS-107918]: Update the com.liferay.source.formatter dependency to version
1.0.959.