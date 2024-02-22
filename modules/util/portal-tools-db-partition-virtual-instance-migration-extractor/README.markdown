# DB Partition Virtual Instance Migration Extractor Tool
This tool allows to extract from the indicated database the information needed to validate that all the needed conditions for a successful migration are met.

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal Tools DB Partition Virtual Instance Migration Extractor
    -h,--help Print help.
    -j,--jdbc-url <arg> JDBC URL.
    -p,--password <arg> Password.
    -u,--user <arg> User.
    -s,--schema-name <arg> Schema name.
    -d,--output-dir <arg> Output directory.

## Execution example
    ./db_partition_virtual_instance_migration_extractor.sh -j "jdbc:mysql://localhost:3306/lportal?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -u user -p password -s lpartition_1234 -d "/migrations/extractions"