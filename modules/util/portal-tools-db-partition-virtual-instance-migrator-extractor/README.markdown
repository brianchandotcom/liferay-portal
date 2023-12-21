# DB Partition Virtual Instance Migrator Extractor Tool
This tool allows to extract from the indicated database the information needed to validate that all the needed conditions for a successful migration are met.

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal Tools DB Partition Virtual Instance Migrator Extractor
    -h,--help Print help message.
    -url,--jdbc-url <arg> Set the JDBC URL.
    -user,--password <arg> Set the password.
    -pass,--user <arg> Set the user.
    -path,--path <arg> Set the output directory.

## Execution example
    java -jar com.liferay.portal.tools.db.partition.virtual.instance.migrator.extractor.jar -url "jdbc:mysql://localhost:3306/lpartition_xxxxx?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -user sourceUser -pass sourcePassword