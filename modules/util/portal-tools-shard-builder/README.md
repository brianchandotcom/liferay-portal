# Partitioning Tool

## Executing the tool

To execute this tool, you must add your JDBC driver to the execution classpath,
so that the tool can successfully perform its operations.

Once the classpath is satisfied, you must provide **four or five arguments** to the
tool:

* The properties file with your database configuration, following the same
    structure as in the sample files. (Required)
    
* The name of the schema you want to export. (Required)

* The ID of the Company/Shard you want to export, described by `companyId`
    field. (Required)
	
* The output directory for the generated SQL files, representing the `INSERT`
    statements to execute in the new database. (Required)
	
* Whether the export process generates one SQL file per table type (control or
    partitioned) for the whole database or one SQL file per table. If no
    argument is specified, only one file per table type will be generated.
    (Optional)

The command to execute the tool should be similar to this:

    java -classpath PATH-TO-LIBS -jar /PATH-TO-TOOL/com.liferay.portal.tools.shard.builder-1.0.0.jar [-P|--properties-file] PATH-TO-DB-PROPERTIES [-S|--schema-name] SCHEMA_NAME [-C|--company-ids] COMMA-SEPARATED-COMPANY_IDS [-O|--output-dir] OUTPUT-DIR [-W|--write-file]

Here are examples of executing the tool using MySQL as the DB provider:

    java -classpath /opt/jdbc-drivers -jar com.liferay.portal.tools.shard.builder-1.0.0.jar -P ~/shardingTool/mysql.properties -S lportal -C 20156 -O /tmp [-W]
    java -classpath /opt/jdbc-drivers -jar com.liferay.portal.tools.shard.builder-1.0.0.jar --properties-file ~/shardingTool/mysql.properties --schema-name lportal --company-ids 20156 --output-dir /tmp [--write-file]

## Testing

To execute the tests, add a file for each database provider you want to verify
the tool against.

Under the `src` directory, we have provided sample configuration files for each
database provider; they're references for defining custom test environments.
Just copy them to the `test/resources` directory and remove the`sample-` prefix,
so test suites are able to find the configuration to set up a database
connection for each provider.