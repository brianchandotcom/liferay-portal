# Mule Liferay Connector

This project contains Liferay Mule connectors that allow you to use Liferay APIs from Anypoint Studio or Anypoint Platform.

## Prerequisites

* JDK 1.8+
* Apache Maven, version 3.3.9+
* Anypoint Studio, version 7.3+
    * Installation Instructions: https://docs.mulesoft.com/studio/7.3/to-download-and-install-studio

## Build

* For the SDK to work, you must pass the command line parameter -parameters to the Java compiler
	* If you are using IntelliJ to develop connector, go to `Preferences > Build, Execution, Deployment >
	Compiler > Java Compiler` and add `-parameters` in the `Additional command line parameters` section
* Execute `mvn clean install` from `modules/etl/mulesoft` folder
    * Build Instructions: https://docs.mulesoft.com/mule-sdk/1.1/getting-started

## Deploy to Anypoint Studio
Add this dependency to your Anypoint Studio application pom.xml

```
<groupId>com.liferay</groupId>
<artifactId>com.liferay.mule</artifactId>
<version>1.0.0-SNAPSHOT</version>
<classifier>mule-plugin</classifier>
```