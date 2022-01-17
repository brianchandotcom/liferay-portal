# poshi-standalone
This repository contains the minimal configuration to begin writing and running Poshi tests through gradle.

## Prerequisites

 1. Java JDK 8

 1. [Gradle](https://gradle.org/install/) 6.6.1 or a [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:adding_wrapper) binary

## Configuration

### Poshi Properties

Poshi Properties are necessary for configuring how tests are run within a particular Poshi project, and full list of properties is available [here](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown). Default properties have been set in [poshi.properties](poshi.properties) and additional properties can be set in a `poshi-ext.properties` file.

The base url for tests should be set for a project and is set by default in [poshi.properties](poshi.properties) to:
```
portal.url=https://www.liferay.com/
```

#### Google Chrome
Currently, only Google Chrome is supported and is set by default in [poshi.properties](poshi.properties) to:
```
browser.type=chrome
```

Additionally, the Chrome binary can also be set in [Poshi Properties file](#poshi-properties-files)
```
browser.chrome.bin.file=path/to/chrome/binary # Optional, if not set the default Google Chrome binary will be used
```

### Gradle Properties

#### Base Directory
To determine which Poshi files are used for a project the `baseDir` property must be set. This can be set in [gradle.properties](gradle.properties) and is by default set to:
```
baseDir=src/testFunctional/samples/liferay
```

#### ChromeDriver Version

Note the ChromeDriver version must be set based off of the version of Google Chrome being used. ChromeDriver versions can be found at https://chromedriver.chromium.org/downloads and must correspond to the Chrome browser being used. This version can be set in [gradle.properties](gradle.properties) and is by default set to:
```
chromeDriverVersion=86.0.4240.22
```

All Chrome versions after Chrome 73 must use the corresponding ChromeDriver version found in the previous link.

#### Poshi Runner Version
To change the Poshi Runner version, update the `poshiRunnerVersion` property in [gradle.properties](gradle.properties).

For updated and tested versions, please see the [Poshi Runner Change Log](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/CHANGELOG.markdown)

## Using Poshi

To see available tasks (under "Verification tasks"):
```
./gradlew tasks
```

### Syntax and Usage Validation
To run Poshi validation:
```
gradlew validatePoshi
```

### Running a test
To run a test, use the following command:
```
gradlew runPoshi -DtestNames=Liferay#Smoke
```