# DB Partition Virtual Instance Migration Validator Tool
This tool allows to validate that all the needed conditions for a successful migration are met.

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal Tools DB Partition Virtual Instance Migration Validator
    -h,--help Print help.
    -s,--source-file <arg> Source file.
    -t,--target-file <arg> Target file.

## Execution example
    ./db_partition_virtual_instance_migrator_validator.sh -s "/bundles/tools/portal-tools-db-partition-virtual-instance-migration-extractor/source.json" -t "/bundles/tools/portal-tools-db-partition-virtual-instance-migration-extractor/target.json"