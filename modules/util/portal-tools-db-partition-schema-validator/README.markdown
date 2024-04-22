# Database Partition Schema Validator Tool

This tool validates a partition in a database partitioned environment to ensure
that they only contain data associated with their proper company ID.

## Requirements

- MySQL or PostgreSQL
- Database user with DDL privileges

## Usage

```
java -jar com.liferay.portal.tools.db.partition.schema.validator.jar --db-name xyz123 --password xyz123 --user xyz123 
```

Options:

- `--debug` Print all log traces. (Optional).
- `--db-name <arg>` Set the database name.
- `--help` Print help message. (Optional).
- `--jdbc-url <arg>` Set the JDBC URL. (Optional. Default value: default URL for db-name pointing to localhost).
- `--password <arg>` Set the database user password.
- `--schema-prefix <arg>` Set the schema prefix. (Optional. Default value: lpartition_).
- `--db-type <mysql|postgresql>` Set the database type.
- `--user <arg>` Set the database user name.