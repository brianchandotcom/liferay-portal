# poshi-standalone
This repository contains the minimal configuration to begin writing and running Poshi tests through gradle.

## Prerequisites

 1. Java JDK 8

 2. [Gradle](https://gradle.org/install/) 6.6.1 or a [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:adding_wrapper) binary

## Configuration

### Gradle Properties

#### ChromeDriver Version

Note the ChromeDriver version must be set based off of the version of Google Chrome being used. ChromeDriver versions can be found at https://chromedriver.chromium.org/downloads and must correspond to the Chrome browser being used. This version can be set in [gradle.properties](gradle.properties) and is by default set to:
```
chromeDriverVersion=86.0.4240.22
```

All Chrome versions after Chrome 73 must use the corresponding ChromeDriver version found in the previous link.

#### Poshi Runner Version
To change the Poshi Runner version, update the `poshiRunnerVersion` property in [gradle.properties](gradle.properties).

For updated and tested versions, please see the [Poshi Runner Change Log](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/CHANGELOG.markdown)

#### Poshi Properties Files

Create a file (e.g. `poshi-ext.properties`) that will be used to set and override default Poshi properties.

In [gradle.properties](gradle.properties), the following files are set by default:
```
poshiRunnerExtPropertyFileNames=poshi.properties,poshi-ext.properties
```

### Poshi Properties

Poshi Properties are necessary for configuring how tests are run within a particular Poshi project, and full list of properties is available [here](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown).

`test.base.dir.name` is required to set the base directory for Poshi files and is set by default in [poshi.properties](poshi.properties) to:
```
test.base.dir.name=src/testFunctional/samples/liferay
```

Additionally, the base url for tests must also be set for a project and is set by default in [poshi.properties](poshi.properties) to:
```
portal.url=https://www.liferay.com/
```

#### Configuration for Chrome and ChromeDriver
Currently, only Google Chrome is supported and is set by default in [poshi.properties](poshi.properties) to:
```
browser.type=chrome
```

Additionally, the Chrome binary can also be set in [Poshi Properties file](#poshi-properties-files)

```
browser.chrome.bin.file=path/to/chrome/binary # Optional, if not set the default Google Chrome binary will be used
```

To set the ChromeDriver version, see [ChromeDriver Version](#chromedriver-version)

## Using Poshi
Poshi can be used using the following command syntax:
```
./gradlew <task> -D<propertyValue>
```

To see available tasks (under "Poshi tasks"):
```
./gradlew tasks
```

### Running a test
To run standalone poshi in conjunction with this properties file, you can use the command
```
gradlew runPoshi -Dtest.name=Liferay#Smoke
```