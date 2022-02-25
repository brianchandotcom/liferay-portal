# Poshi Standalone

This repository contains the minimal configuration to begin writing and running Poshi tests through gradle.

## Prerequisites

 1. Java JDK 8

 1. [Gradle](https://gradle.org/install/) or [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#sec:adding_wrapper) 6.6.1 or higher.

## Poshi Configuration

### Poshi Properties

Poshi Properties are necessary for configuring how tests are run within a particular Poshi project, and full list of properties is available [here](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown). Default properties can be set in [poshi.properties](poshi.properties) and additional user properties can be set in a `poshi-ext.properties` file.

#### Essential Properties
Property Name | Default Value | Description
------------- | ------------- | -----------
[`portal.url`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown#portalurl) | `http://liferay.com` (from [poshi.properties](poshi.properties)) | Sets the default URL to which WebDriver opens.
[`test.base.dir.name`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown#testbasedirname) | `src/test` (from Poshi source) | Sets the path of the main directory containing Poshi files used for the test project.
[`test.name`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown#testname) | `PortalSmoke#Smoke` (from Poshi source) | Sets the test case(s) to run. The tests can be specified by the test case command name, the test case file's name, or a comma-delimited list of both that runs sequentially. To run sequentially, the tests must be configured with proper teardowns.

#### Google Chrome
Currently, only Google Chrome is supported and is set to use Chrome by default.

Optionally, an alternate Google Chrome binary can also be set in [`poshi.properties`](poshi.properties) or `poshi-ext.properties`. If not set, the default installation directory Google Chrome binary will be used.
```
browser.chrome.bin.file=path/to/chrome/binary
```

ChromeDriver will automatically be downloaded based on the Chrome binary version.

The current CI environment uses Chrome 86, and that can be matched by installing Chromium 86.
* For Linux (64 bit), download `chrome-linux.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Linux_x64/800217/).
* For MacOS, download `chrome-mac.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Mac/800208/).
* For Windows (64 bit), download `chrome-win.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Win_x64/800185/)

### Gradle Configuration

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

### Syntax Validation and Source Formatting

To run Poshi validation:
```
gradlew validatePoshi
```

To run source formatting through the [Source Formatter Gradle Plugin](https://github.com/liferay/liferay-portal/blob/master/modules/sdk/gradle-plugins-source-formatter/README.markdown):
```
gradlew formatSource
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

Properties can be configured for testray imports by creating a `testray-ext.properties` file.

#### Testray Import Credentials

To import results into Testray without attachments the following credentials are required:

```
testrayUserName=[liferay_user_name]@liferay.com
testrayUserPassword=[liferay_user_password]
```

#### Default Configuration

Default configurations are defined with the following properties and values:
```
environmentBrowserName=Google Chrome 86
environmentOperatingSystemName=Cent OS 7

projectDir=.

testrayBuildName=DXP Cloud Client Build - $(start.time)
testrayCasePriority=1
testrayComponentName=DXP Cloud Client Component
testrayProductVersion=1.x
testrayProjectName=DXP Cloud Client
testrayRoutineName=DXP Cloud Client Routine
testrayServerURL=https://testray.liferay.com
testrayTeamName=DXP Cloud Client Team
```

#### Property Descriptions

Property Name | Type | Default Value | Description
------------- | ---- | ------------- | -----------
`environmentBrowserName` | `String` | Google Chrome 86 | The browser name and version used in the test environment
`environmentOperatingSystemName` | `String` | Cent OS 7 | The operating system name and version used in the test environment
`projectDir` | `File` | `.` | The location of the project directory
`testrayBuildName` | `String` | DXP Cloud Client Build - $(start.time) | The Testray build name
`testrayCasePriority` | `Integer` | `1` | The priority of the test case result(s)
`testrayComponentName` | `String` | DXP Cloud Client Component | The Testray component name
`testrayProductVersion` | `String` | 1.x | The Testray product version
`testrayProjectName` | `String` | DXP Cloud Client | The Testray product name
`testrayRoutineName` | `String` | DXP Cloud Client Routine | The Testray routine name
`testrayServerURL` | `String` | https://testray.liferay.com | The URL of the Testray server
`testrayTeamName` | `String` | DXP Cloud Client Team | The Testray team name

#### Storing Attachments on Google S3

To import results into Testray with attachments the `GOOGLE_APPLICATION_CREDENTIALS` must be set as an environment variable:

```
export GOOGLE_APPLICATION_CREDENTIALS=/home/user/Downloads/service-account-file.json
```

See this [article](https://cloud.google.com/docs/authentication/getting-started) for more details on how to setup google cloud.

The specific bucket that your google account needs read/write access to is the [testray-results](https://console.cloud.google.com/storage/browser/testray-results) bucket. In order to get access please contact IT for access.