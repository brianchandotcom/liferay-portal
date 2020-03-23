# Mule Liferay Connector

This project contains Liferay Mule connector that enables you to
connect Liferay to other platforms and services in the Mulesoft ecosystem using
Anypoint Studio or Anypoint Design Center.

## Prerequisites

* JDK 1.8+
* Apache Maven, version 3.3.9+
* Anypoint Studio, version 7.4+
    * Installation Instructions: https://docs.mulesoft.com/studio/7.3/to-download-and-install-studio

## Using Liferay Connector

Liferay Connector is available on
[Anypoint Exchange](https://www.mulesoft.com/exchange/com.liferay/com.liferay.mule)
and can be accessed both from Anypoint Studio or Anypoint Design Center.

If you want to build connector from source code see the following section.

## Liferay Connector Development

### Build
To build new connector from the source code follow the next steps:
* Import source code as a new Maven project to IDE of your choice
* For the SDK to work, you must pass the command line parameter -parameters to the Java compiler
	* If you are using IntelliJ to develop connector, go to `Preferences > Build, Execution, Deployment >
	Compiler > Java Compiler` and add `-parameters` in the `Additional command line parameters` section
* Execute `mvn clean install` from `modules/etl/mulesoft` folder
    * Build Instructions: https://docs.mulesoft.com/mule-sdk/1.1/getting-started

### Deploy to Anypoint Studio
Once you have built the connector, add this dependency to the pom.xml file in
the root of your Anypoint Studio project:

```
<groupId>com.liferay</groupId>
<artifactId>com.liferay.mule</artifactId>
<version>CONNECTOR_VERSION</version>
<classifier>mule-plugin</classifier>
```