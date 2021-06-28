# DB Partition Schema Validator Tool
This tool validates DB Partition schemas to be sure that all of them only contains data associated to the proper companyId

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal DB Partition Schema Validator
    -a,--debug Print all log traces.
    -d,--db-schema <arg> Default database schema name.
    -h,--help Print help message.
    -j,--jdbc-url <arg> JDBC url.
    -p,--password <arg> Database user password.
    -s,--schema-prefix <arg> Schema prefix for non-default databases.
    -u,--user <arg> Database user name.

## Execution example
    java -jar com.liferay.portal.tools.db.partition.schema.validator.jar -d myDefaultSchema -u myDatabaseUser -p myDabatabasePassword