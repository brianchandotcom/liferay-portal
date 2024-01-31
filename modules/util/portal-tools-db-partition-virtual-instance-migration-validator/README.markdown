# DB Partition Virtual Instance Migration Validator Tool
This tool allows to validate that all the needed conditions for a successful migration are met.

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal Tools DB Partition Virtual Instance Migration Validator
    -h,--help Print help message.
    -s,--source-file <arg> Set the source file.
    -t,--target-file <arg> Set the target file.

## Execution example
    java -jar com.liferay.portal.tools.db.partition.virtual.instance.migration.validator.jar -s "/bundles/tools/portal-tools-db-partition-virtual-instance-migration-extractor/source.json" -t "/bundles/tools/portal-tools-db-partition-virtual-instance-migration-extractor/target.json"