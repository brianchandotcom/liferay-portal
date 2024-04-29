# Database Partition Migration Validator Tool

This tool allows the export and validation of the database information required for a successful partition migration.

## Requirements

- MySQL
- Database user with DDL privileges

## Usage

```
./db_partition_migration_validator.sh <OPERATION_MODE> [OPERATION_PARAMETERS]
```

Operation mode:

- `--export` Export database.
- `--help` Print help message.
- `--validate` Validate two databases.

Data Export parameters:

- `--output-dir <arg>` Set the output directory.
- `--jdbc-url <arg>` Set the JDBC URL.
- `--password <arg>` Set the database user password.
- `--schema-name <arg>` Set the database schema name.
- `--user <arg>` Set the database user name.

Data Validation parameters:

- `--source-file <arg>` Set the path to the source file.
- `--target-file <arg>` Set the path to the target file.

### Execution examples

Data Export for the source database partition example:

```
./db_partition_migration_validator.sh --export --jdbc-url "jdbc:mysql://localhost:3306/sourceSchema" --user xyz --password xyz123 --schema-name lpartition_1234 --output-dir sourceExports
```

Data Export for the target example:

```
./db_partition_migration_validator.sh --export --jdbc-url "jdbc:mysql://localhost:3306/targetSchema" --user xyz --password xyz123 --output-dir targetExports
```

Data Validation example:

```
./db_partition_migration_validator.sh --validate --source-file sourceExports/source.json --target-file targetExports/target.json
```