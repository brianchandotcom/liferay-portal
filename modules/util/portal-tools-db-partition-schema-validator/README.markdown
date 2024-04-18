# Database Partition Schema Validator Tool

This tool validates a partition in a database partitioned environment to ensure
that they only contain data associated with their proper company ID.

## Requirements

- MySQL or PostgreSQL
- Database user with DDL privileges

## Usage

```
java -jar com.liferay.portal.tools.db.partition.schema.validator.jar -d xyz123 -p xyz123 -u xyz123 
```

Options:

- `-a`, `--debug` Print all log traces.
- `-d`, `--db-name <arg>` Set the database name.
- `-h`, `--help` Print help message.
- `-j`, `--jdbc-url <arg>` Set the JDBC URL.
- `-p`, `--password <arg>` Set the database user password.
- `-s`, `--schema-prefix <arg>` Set the schema prefix.
- `-t`, `--db-type <mysql|postgresql>` Set the database type.
- `-u`, `--user <arg>` Set the database user name.