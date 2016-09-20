# Data Partitioning SQL Builder

The data partitioning SQL builder tool (partitioning tool) allows you to
physically separate a Liferay Portal instance from an already existing
*logically partitioned* Liferay Portal instance. The existing instance is
logically partitioned in that it has several companies in its database (i.e.,
the database's `Company` table has multiple rows). Using the partitioning tool,
you can move a subset of the companies to another database for a completely
different new Liferay Portal instance to use.

The tool produces one or more SQL files for you to execute on a target database.
After that, you can start the new Liferay Portal installation and configure it
to use the target database, so the portal instance uses the migrated company
data.

**Important**: This tool does not allow moving one portal instance to another
database manager system (DBMS), because the tool's output is always based on the
SQL code for the underlying DBMS. The tool is for isolating company data into
SQL files for the sole purpose of moving the data to a different database.

## Executing the Tool

To execute this tool, you must add both your JDBC driver and your database data
partitioning module (MySQL, PostgreSQL, Oracle, DB2, Sybase, SQL Server) to the
execution classpath.

Here are the tool's arguments:

* The properties file with your database configuration, following the same
  structure as in the sample files. **(Required)**

* The name of the schema you want to export. **(Required)**

* The name of the catalog you want to export. Use it only in those cases
  where the DBMS uses different object to handle catalogs and schemas,
  such as DB2. If no argument is specified, the schema name will be used.
  **(Optional)**

* The IDs of the companies/shards you want to export. A company's ID is in the
  `companyId` field. **(Required)**

* The output directory for the generated SQL files. (Required)

* Whether to have the export process generate one SQL file per table type
  (control or partitioned) for the whole database or one SQL file per table. If
  no argument is specified, only one file per table type is generated.
  **(Optional)**

The command to execute the tool should be similar to this:

    java -classpath "PATH-TO-LIBS/*:/PATH-TO-TOOL/com.liferay.portal.tools.data.partitioning.sql.builder-1.0.0.jar" com.liferay.portal.tools.data.partitioning.sql.builder.Main [-P|--properties-file] PATH-TO-DB-PROPERTIES [-S|--schema-name] SCHEMA_NAME [-K|--catalog-name] CATALOG_NAME [-C|--company-ids] COMMA-SEPARATED-COMPANY_IDS [-O|--output-dir] OUTPUT-DIR [-W|--write-file]

Remember to use a colon (:) as a file separator on Unix-like machines, and a
semicolon (;) on Windows machines.

Here are examples of executing the tool using MySQL as the DB provider:

    java -classpath "/opt/jdbc-drivers/*:com.liferay.portal.tools.data.partitioning.sql.builder-1.0.0.jar" com.liferay.portal.tools.data.partitioning.sql.builder.Main -P ~/shardingTool/mysql.properties -S lportal -C 20156 -O /tmp [-W]
    java -classpath "/opt/jdbc-drivers/*:com.liferay.portal.tools.data.partitioning.sql.builder-1.0.0.jar" -jar com.liferay.portal.tools.data.partitioning.sql.builder.Main --properties-file ~/shardingTool/mysql.properties --schema-name lportal --company-ids 20156 --output-dir /tmp [--write-file]

## Testing

To execute the partitioning tool tests, add a file for each database provider
you want to verify the tool against.

Under the `src` directory, there are sample configuration files for each
database provider; they're references for defining custom test environments.
Just copy them to the `test/resources` directory and remove the`sample-` prefix,
so test suites can find the configuration to set up a database connection for
each provider.