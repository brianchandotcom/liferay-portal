# Poshi Standalone

This repository contains the minimal configuration to begin writing and running Poshi tests through gradle.

## Prerequisites

 1. Java JDK 8

## Setup

Poshi Standalone runs using a custom Gradle Wrapper made by Liferay. To create the necessary configuration files to use Poshi Standalone, run the following command in a command line window (Linux, macOS, Git/Bash for Windows) from a new directory (or your project directory):
```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/setup.sh)"
```

For Windows Command shell and PowerShell:
```
curl -fsSL -o setup.bat https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/test/poshi/poshi-standalone/setup.bat && setup.bat && del setup.bat
```

The directory will now contain the following files:
```
├── gradle
|   └── wrapper
|       ├──  gradle-wrapper.jar
|       └──  gradle-wrapper.properties
├── build.gradle
├── gradlew
├── gradlew.bat
├── poshi.properties
├── poshi-ext.properties
└── settings.gradle
```

Each `gradle` file is necessary in order to use the `gradlew` executable and typically will not need further modification. The `gradlew` (`gradlew.bat`) executable is a script that invokes a predefined version of Gradle. The first time you run the `gradlew` command, the custom Gradle binary files will be downloaded to `~/.gradle/wrapper/dists` (`%HOMEPATH%\.gradle\wrapper\dists` for Windows). Note the custom binary files will only be downloaded once per machine, and additional projects can also reference these binary files. More information about the Gradle Wrapper is available [here](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

The `*.properties` files are explained further in the next section.

## Poshi Configuration

### Poshi Properties

Poshi properties are necessary for configuring how tests are run within a particular Poshi project, and full list of properties is available [here](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown). Default properties can be set in [poshi.properties](poshi.properties) and custom user properties can be set in a `poshi-ext.properties` file.

Additionally, properties can also be set as a [Gradle JVM System Property](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_system_properties). For example:
```
./gradlew runPoshi -Dtest.name=Liferay#Smoke
```

The property load order is `poshi.properties`, then `poshi-ext.properties`, followed by Gradle JVM system properties. Each group of properties will supersede the previous (Gradle system properties will override poshi-ext.properties, poshi-ext.properties will override poshi.properties).

#### Essential Properties
Property Name | Default Value | Description
------------- | ------------- | -----------
[`portal.url`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown#portalurl) | `http://liferay.com` (from [poshi.properties](poshi.properties)) | Sets the default URL to which WebDriver opens.
[`test.base.dir.name`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown#testbasedirname) | `src/test` (from Poshi source) | Sets the path of the main directory containing Poshi files used for the test project.
[`test.name`](https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-properties.markdown#testname) | `PortalSmoke#Smoke` (from Poshi source) | Sets the test case(s) to run. The tests can be specified by the test case command name, the test case file's name, or a comma-delimited list of both that runs sequentially. To run sequentially, the tests must be configured with proper teardowns.

#### Browsers
Currently, Firefox, Google Chrome and Microsoft Edge are the only supported browsers and Chrome is set by default. To change the browser, set the following property in [`poshi.properties`](poshi.properties) or `poshi-ext.properties`:
```
browser.type=chrome
#browser.type=edge
#browser.type=firefox
```

Poshi will use the the default installation path of the specified browser. If another browser binary path is required, an additional property can be set.

##### Google Chrome
```
browser.chrome.bin.file=path/to/chrome/binary
```
##### Firefox
```
browser.firefox.bin.file=path/to/firefox/binary
```
##### Microsoft Edge (pending)
```
browser.edge.bin.file=path/to/edge/binary
```

Each respective [browser driver](https://www.selenium.dev/documentation/webdriver/getting_started/install_drivers/) is automatically downloaded based on the browser type and version.

##### Selenium Grid

If you have a local (or remote) Selenium Grid available, then use the following property:
```
selenium.remote.driver.url=http://<HOSTNAME_AND_PORT_OF_SELENIUM_GRID_SERVER>
```
##### Liferay CI

The current CI environment primarily uses Chrome 100, and that can be matched by installing Chromium 100.
* For Linux (64 bit), download `chrome-linux.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Linux_x64/972765/).
* For MacOS, download `chrome-mac.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Mac/972767/).
* For Windows (64 bit), download `chrome-win.zip` [here](https://commondatastorage.googleapis.com/chromium-browser-snapshots/index.html?prefix=Win_x64/972766/)

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

To run source formatting only on Poshi files through the [Source Formatter Gradle Plugin](https://github.com/liferay/liferay-portal/blob/master/modules/sdk/gradle-plugins-source-formatter/README.markdown):
```
gradlew formatPoshi
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
environmentOperatingSystemName=CentOS 7

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
`environmentOperatingSystemName` | `String` | CentOS 7 | The operating system name and version used in the test environment
`projectDir` | `File` | `.` | The location of the project directory
`testrayBuildName` | `String` | DXP Cloud Client Build - $(start.time) | The Testray build name
`testrayBuildSHA` | `String` | | The Testray DXP build SHA
`testrayCasePriority` | `Integer` | `1` | The priority of the test case result(s)
`testrayComponentName` | `String` | DXP Cloud Client Component | The Testray component name
`testrayProductVersion` | `String` | 1.x | The Testray product version
`testrayProjectName` | `String` | DXP Cloud Client | The Testray product name
`testrayRoutineName` | `String` | DXP Cloud Client Routine | The Testray routine name
`testrayS3BucketName` | `String` | testray-results | The name of the Testray S3 Bucket
`testrayServerURL` | `String` | https://testray.liferay.com | The URL of the Testray server
`testrayTeamName` | `String` | DXP Cloud Client Team | The Testray team name

#### Storing Attachments on Google S3

To import results into Testray with attachments the `GOOGLE_APPLICATION_CREDENTIALS` must be set as an environment variable:

```
export GOOGLE_APPLICATION_CREDENTIALS=/home/user/Downloads/service-account-file.json
```

See this [article](https://cloud.google.com/docs/authentication/getting-started) for more details on how to setup google cloud.

Your Google account needs read/write access to the bucket selected by `testrayS3BucketName`. By default the [testray-results](https://console.cloud.google.com/storage/browser/testray-results) bucket will be used. Contact IT for access.