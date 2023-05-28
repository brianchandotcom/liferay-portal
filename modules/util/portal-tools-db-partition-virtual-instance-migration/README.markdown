# DB Partition Virtual Instance Migration Tool
This tool allows to migrate a virtual instance to a new database partition in a database partitioned environment. Before starting the migration, it validates that all the needed conditions for a successful migration are met.

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal DB Partition Virtual Instance Migration
    -d,--destination-jdbc-url <arg> Set the JDBC url for the destination database.
    -dp,--destination-password <arg> Set the destination database user password.
    -dsp,--destination-schema-prefix <arg> Set the schema prefix for nondefault databases in destination database.
    -du,--destination-user <arg> Set the destination database user name.
    -h,--help Print help message.
    -s,--source-jdbc-url <arg> Set the JDBC url for the source database.
    -sp,--source-password <arg> Set the source database user password.
    -su,--source-user <arg> Set the source database user name.

## Execution example
    java -jar com.liferay.portal.tools.db.partition.virtual.instance.migration.jar -s "jdbc:mysql://localhost:3306/lpartition_xxxxx?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -su sourceDatabaseUser -sp sourceDatabasePassword -d "jdbc:mysql://localhost:3306/lportal?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -du destinationDatabaseUser -dp destinationDatabasePassword
