## BNDSchemaVersionCheck

Web modules (modules ending with `-web`) should not have the header
`Liferay-Require-SchemaVersion` included, because this header is not processed
in web modules since the web parts of an application do not have an associated
schema.

If the BND file of the module contains `Liferay-Service: true` and a service.xml
is present in the module, the header `Liferay-Require-SchemaVersion` is
required.