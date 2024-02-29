# DB Partition Virtual Instance Migration Extractor Tool
This tool extracts the database information needed to validate that all the conditions for a successful virtual instance migration are met.

## Requirements
- MySQL
- Database user with DDL privileges

## Usage
```
./db_partition_virtual_instance_migration_extractor.sh -j jdbcURL -s schemaName -u databaseUser -p databasePassword
```

Options:

- `-d`, `--output-dir <arg>` Output directory. Folder ./extractions otherwise.
- `-h`, `--help` Display options.
- `-j`, `--jdbc-url <arg>` JDBC URL.
- `-p`, `--password <arg>` Database user password.
- `-s`, `--schema-name <arg>` Database schema name to be extracted. Default schema name otherwise.
- `-u`, `--user <arg>` Database user name.

### Execution example
```
./db_partition_virtual_instance_migration_extractor.sh -j "jdbc:mysql://localhost:3306/lportal?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -u user -p password -s lpartition_1234 -d "/migrations/extractions"
```