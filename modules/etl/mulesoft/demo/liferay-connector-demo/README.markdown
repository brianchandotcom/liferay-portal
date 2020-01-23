# Liferay Connector Demo
This is an Anypoint studio demo project for Liferay Connector. It contains demo
flows which demonstrate the basic usage of Liferay Connector.

## Prerequisites
* Anypoint studio 7 with Mule ESB 4.2.0 Runtime
* Mule Liferay Connector v1.0.0
* Running Liferay instance with initialized Commerce site

## How to Run
1. Import demo project into the Anypoint studio
	* In Anypoint studio navigate to File > Import > Anypoint Studio >
	Anypoint Studio project from File System
	* For project root select **liferay-connector-demo** folder,
	check **Copy project into workspace** checkbox and click **Finish**

2. Run the application, make sure Liferay instance is running too
3. There are 4 flows you can trigger to observe basic functionality of the
	Liferay connector
	* commerce-get-flow: trigger with **curl 0.0.0.0:8081/get** (or paste the
	address into web browser address bar) to see how to fetch list of (Product)
	entities from your Liferay instance

	* commerce- create-flow: trigger with **curl 0.0.0.0:8081/create** to see
	how to create a new (Product) entity

	* commerce-delete-flow: trigger with **curl 0.0.0.0:8081/delete** to see how
	to delete a (Product) entity

	* commerce-error-flow: trigger with **curl 0.0.0.0:8081/error** to
	check how to use error handlers with Liferay connector