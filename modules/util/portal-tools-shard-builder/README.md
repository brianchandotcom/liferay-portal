# Partitioning Tool

## Executing the tool

To execute this tool, you must add your JDBC driver to the execution classpath,
so that the tool can successfully perform its operations.

Once classpath its satisfied, you must provide **four arguments** to the tool:

	* The properties file with your database configuration, following same
	structure than in the sample files. (Required)
	* The name of the schema you want to export. (Required)
	* The ID of the Company/Shard you want to export, described by companyId
	field. (Required)
	* The output directory for the generated SQL files, representing the INSERT
	statements to execute in the new database. (Required)

So the command to execute the tool should be similar to this:

```
java -classpath PATH-TO-LIBS -jar /PATH-TO-TOOL/com.liferay.portal.tools.shard.builder-1.0.0.jar PATH-TO-DB-PROPERTIES SCHEMA_NAME COMPANY_ID OUTPUT-DIR
```

An example for the execution of the tool, using MySQL as DB provider, could be:
```
java -classpath /opt/jdbc-drivers -jar com.liferay.portal.tools.shard.builder-1.0.0.jar ~/shardingTool/mysql.properties lportal 20156 /tmp
```

## Testing

To execute the tests, you need to add a file for each database provider you want
to verify the tool against.

We have provided sample configuration files for each database provider under
'src' directory, so you can define your custom test environment. Just copy them
to 'test/resources' directory and remove the 'sample-' prefix, so that test
suites are able to find the configuration to set up a database connection for
each provider.