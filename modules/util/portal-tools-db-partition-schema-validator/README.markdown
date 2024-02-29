# DB Partition Schema Validator Tool
This tool validates a partition in a database partitioned environment to ensure
that they only contain data associated with their proper company ID.

## Requirements
- MySQL or PostgreSQL
- Database user with DDL privileges

## Usage
```
java -jar com.liferay.portal.tools.db.partition.schema.validator.jar -d databaseName -u databaseUser -p dabatabasePassword 
```

Options:

- `-a`, `--debug` Print all log traces.
- `-d`, `--db-name <arg>` Database name.
- `-h`, `--help` Display options.
- `-j`, `--jdbc-url <arg>` JDBC URL.
- `-p`, `--password <arg>` Database user password.
- `-s`, `--schema-prefix <arg>` Schema prefix for non-default partitions. Default prefix otherwise.
- `-t`, `--db-type <mysql|postgresql>` Database type.
- `-u`, `--user <arg>` Database user name.