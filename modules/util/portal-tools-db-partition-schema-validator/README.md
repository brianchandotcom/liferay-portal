# Database Partition Schema Validator Tool

This tool validates a partition in a database partitioned environment to ensure
that they only contain data associated with their proper company ID.

## Requirements

- MySQL or PostgreSQL
- Database user with read access to all partitions

## Usage

```
java -jar com.liferay.portal.tools.db.partition.schema.validator.jar <parameters>
```

Parameters:

- `--db-name <arg>` Set the database name.
- `--db-type <mysql|postgresql>` Set the database type.
- `--debug` Print all log traces.
- `--jdbc-url <arg>` Set the JDBC URL.
- `--password <arg>` Set the database user password.
- `--schema-prefix <arg>` Set the schema prefix.
- `--user <arg>` Set the database user name.

## Examples

```
java -jar com.liferay.portal.tools.db.partition.schema.validator.jar --db-name xyz123 --db-type mysql --password xyz123 --user xyz123
java -jar com.liferay.portal.tools.db.partition.schema.validator.jar --db-name xyz123 --db-type postgresql --password xyz123 --user xyz123 
```