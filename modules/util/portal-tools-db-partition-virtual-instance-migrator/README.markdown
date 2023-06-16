# DB Partition Virtual Instance Migrator Tool
This tool allows to migrate a virtual instance to a new partition in a database partitioned environment. Before starting the migration, it validates that all the needed conditions for a successful migration are met.

## Requirements:
    - MySQL
    - Database user with DDL privileges

## Usage
    usage: Liferay Portal Tools DB Partition Virtual Instance Migrator
    -h,--help Print help message.
    -s,--source-jdbc-url <arg> Set the source JDBC URL.
    -sp,--source-password <arg> Set the source password.
    -su,--source-user <arg> Set the source user.
    -t,--target-jdbc-url <arg> Set the target JDBC URL.
    -tp,--target-password <arg> Set the target password.
    -tsp,--target-schema-prefix <arg> Set the target schema prefix.
    -tu,--target-user <arg> Set the target user.

## Execution example
    java -jar com.liferay.portal.tools.db.partition.virtual.instance.migrator.jar -s "jdbc:mysql://localhost:3306/lpartition_xxxxx?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -su sourceUser -sp sourcePassword -t "jdbc:mysql://localhost:3306/lportal?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&useTimezone=true&serverTimezone=GMT" -tu targetUser -tp targetPassword