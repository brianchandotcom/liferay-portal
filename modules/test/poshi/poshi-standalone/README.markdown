# Poshi Standalone
This repository contains the minimal configuration to begin writing and running Poshi tests through gradle.

## Prerequisites

 1. Java JDK 8

 1. [Gradle](https://gradle.org/install/) 6.6.1 or a [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:adding_wrapper) binary

## Poshi Configuration

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

Optionally, other Google Chrome binary can also be set in [Poshi Properties file](#poshi-properties-files). If not set the default Google Chrome binary will be used.
```
browser.chrome.bin.file=path/to/chrome/binary
```

It is recommended that Google Chrome 86 is used in order to match the current CI environment, and that can be down by installing Chromium 86.
* For Linux (64 bit), download `chrome-linux.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Linux_x64/800217/).
* For MacOS, download `chrome-mac.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Mac/800208/).
* For Windows (64 bit), download `chrome-win.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Win_x64/800185/)

### Gradle Configuration

#### ChromeDriver Version

Note that the ChromeDriver version and the version of Google Chrome must match. By default, the expected Google Chrome version is Google Chrome 86 so the ChromeDriver version is set to `86.0.4240.22`

To use a custom ChromeDriver version with a more recent version of Google Chrome, other versions can be found at [https://chromedriver.chromium.org/downloads](https://chromedriver.chromium.org/downloads) and must correspond to the Chrome browser being used. For example, for Chrome 95, use ChromeDriver `95.0.4638.17`. The ChromeDriver version can be set by adding the following to the bottom of [build.gradle](build.gradle):
```
webdriverBinaries {
	chromedriver = "95.0.4638.17"
}
```

All Chrome versions after Chrome 73 must use the corresponding ChromeDriver version found in the previous link.

#### Poshi Runner Version
To change the Poshi Runner version, add the following to the bottom of [build.gradle](build.gradle):
```
poshiRunner {
	version = "1.0.XXX"
}
```

For updated and tested versions, please see the [Poshi Runner Change Log](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/CHANGELOG.markdown)

## Using Poshi

To see available tasks (under "Verification tasks"):
```
gradlew tasks
```

### Syntax and Usage Validation
To run Poshi validation:
```
gradlew validatePoshi
```

### Running a test
To run a test, use the following command:
```
gradlew runPoshi
```

The test name must be set in `poshi.properties` or `poshi-ext.properties`:
```
test.name=TestCaseFileName#TestCaseName
```
## Testray Configuration

### Importing Testray Results
To import the results into Testray use the following command:
```
gradlew importTestrayResults
```

### Configuring Testray Properties

Default properties have been set in [testray.properties](testray.properties) and additional properties can be set in a `testray-ext.properties` file.

#### Testray Import Credentials

The Testray Server URL can be set using the following:

```
testrayServerURL=https://testray.liferay.com
```

To import results into Testray without attachments the following credentials are required:

```
testrayUserName=[liferay_user_name]@liferay.com
testrayUserPassword=[liferay_user_password]
```

To import results into Testray with attachments the `GOOGLE_APPLICATION_CREDENTIALS` must be set as an environment variable:

```
export GOOGLE_APPLICATION_CREDENTIALS=/home/user/Downloads/service-account-file.json
```

See this [article](https://cloud.google.com/docs/authentication/getting-started) for more details on how to setup google cloud.

The specific bucket that your google account needs read/write access to is the [testray-results](https://console.cloud.google.com/storage/browser/testray-results) bucket. In order to get access please contact IT for access.

#### Testray Import Data

The Testray Project Name can be set using the following:

```
testrayProjectName=DXP Cloud Client
```

The Testray Routine Name can be set using the following:

```
testrayRoutineName=DXP Cloud Client Routine
```

The Testray Build Name can be set using the following:

```
testrayBuildName=DXP Cloud Client Build - $(start.time)
```

The Testray Team Name can be set using the following:

```
testrayTeamName=DXP Cloud Client Team
```

The Testray Component Name can be set using the following:

```
testrayComponentName=DXP Cloud Client Component
```

The Testray Product Version can be set using the following:

```
testrayProductVersion=1.x
```

The Testray Environment Information can be set using the following:

```
environmentBrowserName=Google Chrome 86
environmentOperatingSystemName=OSX 12.2 64-Bit
```