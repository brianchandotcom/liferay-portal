# DB Partition Schema Validator Tool
This tool validates DB Partition schemas to be sure that all of them only contains data associated to the proper companyId

## Requirements:
    - MySQL or PostgreSQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal DB Partition Schema Validato
    -a,--debug Print all log traces
    -d,--db-name <arg> Database name
    -h,--help Print help message
    -j,--jdbc-url <arg> JDBC URL
    -p,--password <arg> Database user password
    -s,--schema-prefix <arg> Schema prefix for non-default partitions
    -t,--db-type Database type [mysql or postgresql]
    -u,--user <arg> Database user name

## Execution example
    java -jar com.liferay.portal.tools.db.partition.schema.validator.jar -d myDatabaseName -u myDatabaseUser -p myDabatabasePassword