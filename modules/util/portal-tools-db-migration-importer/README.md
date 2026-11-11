# Database Migration Importer Tool

This tool imports the database schema and data into a PostgreSQL database.

## Requirements

- Source database must be DB2, MariaDB, MySQL, Oracle, or SQL Server
- Target database must be PostgreSQL
- Database user with read access to all partitions
- Previous database schema export from the Server Administration Database Migration section (or the Headless Admin Server API) to generate the SQL schema files

## Usage

On Unix:

```
./db_migration_importer.sh <parameters>
```

On Windows:

```
db_migration_importer.bat <parameters>
```

Import parameters:

- `--jdbc-batch-size <arg>` Set the JDBC batch size. The default value is 2500.
- `--jdbc-fetch-size <arg>` Set the JDBC result set fetch size. The default value is 2500.
- `--path <arg>` Set the path of the source SQL files.
- `--source-jdbc-url <arg>` Set the source JDBC URL.
- `--source-password <arg>` Set the source database user password.
- `--source-user <arg>` Set the source database user.
- `--target-jdbc-url <arg>` Set the target JDBC URL.
- `--target-password <arg>` Set the target database user password.
- `--target-user <arg>` Set the target database user.

MySQL sources:

- A MySQL source is read with the MariaDB driver, and a `jdbc:mysql:` URL is changed to `jdbc:mariadb:`.
- Options that only MySQL Connector/J supports, such as `serverTimezone`, are ignored.
- Add `sslMode=trust` to the source JDBC URL to encrypt the connection without verifying the server certificate, or `sslMode=verify-full` to encrypt it and verify a certificate issued for the server's hostname by a CA that the JVM trusts or that `serverSslCert` points to.
- `useSSL=true` is read as `sslMode=verify-full`, so it fails with the self signed certificate that MySQL generates.
- Before the server has cached the password of a `caching_sha2_password` user, for example for a new user or after a server restart, the user can log in over an encrypted connection or with `allowPublicKeyRetrieval=true`.
- Zero dates are copied as null.

## Examples

On Unix:

```
./db_migration_importer.sh --path "/directory" --source-jdbc-url "jdbc:mysql://localhost:3306/schema?sslMode=trust" --source-password "xyz123" --source-user "xyz123" --target-jdbc-url "jdbc:postgresql://localhost:5432/schema" --target-password "xyz321" --target-user "xyz321"
./db_migration_importer.sh --jdbc-batch-size 600 --jdbc-fetch-size 1600 --path "/directory" --source-jdbc-url "jdbc:mysql://localhost:3306/schema?sslMode=trust" --source-password "xyz123" --source-user "xyz123" --target-jdbc-url "jdbc:postgresql://localhost:5432/schema" --target-password "xyz321" --target-user "xyz321"
```

On Windows:

```
db_migration_importer.bat --path "C:\directory" --source-jdbc-url "jdbc:mysql://localhost:3306/schema?sslMode=trust" --source-password "xyz123" --source-user "xyz123" --target-jdbc-url "jdbc:postgresql://localhost:5432/schema" --target-password "xyz321" --target-user "xyz321"
db_migration_importer.bat --jdbc-batch-size 600 --jdbc-fetch-size 1600 --path "C:\directory" --source-jdbc-url "jdbc:mysql://localhost:3306/schema?sslMode=trust" --source-password "xyz123" --source-user "xyz123" --target-jdbc-url "jdbc:postgresql://localhost:5432/schema" --target-password "xyz321" --target-user "xyz321"
```