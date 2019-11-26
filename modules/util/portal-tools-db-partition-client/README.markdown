# Data Partitioning

From 7.3, Liferay provides several tools to facilitate the configuration of data
partitioning by Liferay Virtual Instance (Company) at database level.

## Introduction to Data Partitioning

There are two types of data partitioning:

- **Logical Partitioning:** Liferay provides this feature via its portal
instance concept. A portal instance is a logical set of data grouped by the
*companyId* column. This facility provides data security at the portal level.
Essentially, a user (e.g. Portal Administrator could never see data from a
portal instance for which they do not have access. Data for all instances would
remain in a single database instance (aka schema, tablespace, etc)
- **Physical Partitioning:** it allows to store in different partitions the data
separated logically. This feature is provided by the database vendors at table
level.

From Liferay 7.3, we can easily combine both solutions having logical and
physical horizontal partitioning by the *companyId* column.

## Getting started

Before starting, you will need to analyze and verify the current official
documentation offered by your database's vendor to enable data partitioning.

The following links provide that information but be sure that they apply to the
database version you are currently using:

- **DB2 11.1:** https://www.ibm.com/support/knowledgecenter/en/SSEPGG_11.1.0/com.ibm.db2.luw.admin.partition.doc/doc/c0021560.html
- **MariaDB:** https://mariadb.com/kb/en/library/partitioning-tables/
- **MS SQLServer 2017:** https://docs.microsoft.com/en-us/sql/relational-databases/partitions/partitioned-tables-and-indexes?view=sql-server-2017
- **Mysql 8:** https://dev.mysql.com/doc/refman/8.0/en/partitioning-overview.html
- **Oracle:** https://www.oracle.com/database/technologies/partitioning.html
- **PostgreSQL 10:** https://www.postgresql.org/docs/10/ddl-partitioning.html

---
**The Data Physical Partitioning feature is provided by the different database
vendors so Liferay is not responsible of the maintenance of this feature and its
documentation**
---

### Activate data physical partitioning

To be able to configure data partitioning we just need to have a Liferay
database initialized. Once we have the Liferay database we can activate data
partitioning.

In this step we will configure the database to use different partitions per
every Company. To achieve we can follow these steps:

1. Ensure that your database supports Data Partitioning

2. Download and install Liferay's Partition Client

3. Run `validate.sh` in Unix or `validate.bat` in Windows environments and
analyze the results to remove orphaned data belonging to non-existed companyIds.
Run the scripts again after the data is valid. Take into account that
*companyId* 0 is required for Liferay for internal purposes.

4. Shutdown your Liferay server (previous steps don't require the server up
either)

5. [optional] Some databases require having the partition key (*companyId*) in
every unique index including primary keys. If that's the case, Run
`updateindexes.sh` or `updateindexes.bat` to add the *companyId* column to the
required indexes in the tables to partition.

6. Enable data partitioning at database level following your database vendor
instructions

7. Add the following properties to your portal-ext.properties:
	- **database.partitioning.enabled=true**
	- [optional] **database.unique.indexes.add.companyId=true** (only if having
	the *companyId* in unique indexes is required)

---
**Make a database backup before activating data partitioning or modifying the
data partitioning is required**
---

### Add a new virtual instance (Company)

The creation of a new virtual instance (Company) once partitioning is enabled
maybe require define the partition for that Company beforehand. For example,
this is what it happens if we try to insert a record in a partitioned table
where the partition for the new *companyId* has not been defined:
```
Error Code: 1526. Table has no partition for value xxx
```
For that reason, Liferay provides two ways to reserve a *companyId* before
creating any record related to that Company so a new partition based on that key
can be configured in every table in advance:

#### Creating a new virtual instance via Control Panel

1. Go to Control Panel/Configuration/Virtual Instances

2. Click on add virtual instance without creating it. You will get a message
providing the a new *companyId* before creating the Company.

3. Use that *companyId* to define the new partition. You can shutdown the
Liferay's server if needed.

4. Go to Control Panel/Configuration/Virtual Instances and create the new
virtual instance.

#### Creting a new virtual instance via command line

1. Download and install Liferay's Partition Client

2. Run `nextcompanyid.sh` in Unix or `nextcompanyid.bat` in Windows environments

3. Use that *companyId* to define the new partition. You can shutdown the
Liferay's server if needed.

4. Call the method *addCompany* using the remote Liferay Company services or go
to to Control Panel/Configuration/Virtual Instances and create the new virtual
instance.

---
**In both cases it's required that the Liferay's server has been started up
with the property database.partitioning.enabled set to true to initialize the
*companyId* counter for data partitioning. The *companyId* is reserved until the
new virtual instance is created (new Company record)**
---

### Add a new database table

All data partitioning solutions provided by the different Database vendors
define partitions at table level. For that reason, if you need to define
partitions for your new table you have to follow these steps:

1. Be sure that the table contains the *companyId*. Using Service Builder will
facilitate this task since the field will be automatically populated for every
new record.

2. [optional] Some databases require having the partition key (*companyId*) in
every unique index including primary keys. If that's the case, you can run
`updateindexes.sh` or `updateindexes.bat` to add the *companyId* column to the
required indexes in that table.

3. Define the partitions for that table at database level.

### Add a new unique index

If you add a new unique index for a table which is partitioned you can get an
error if it doesn't include the *companyId* column depending on your database
vendor.

If you use the Liferay framework to create the index you just need to configure
the property database.unique.indexes.add.*companyId* to true and Liferay will
add the *companyId* column at the end of the index definition for you.

### Remove a virtual instance (Company)

You can remove virtual instances from Control Panel/Configuration/Virtual
Instances or using the Liferay API as usual.

After that you will need to remove the partitions associated to that *companyId*
at database level.

### Migrating custom developments

If you come from previous versions of Liferay and you want to partition your
custom tables, you will need:

1. Add an upgrade process step to add the *companyId* column.

2. Add another upgrade process step to populate the *companyId* in the records
created previously.

Using Service Builder and the Liferay Upgrade framework is highly recommended
since we provide different base classes to complete this upgrade processes.

## DB Partition Client

The DB Partition Client is a standalone tool used for facilitating the
configuration of data partitioning by the *companyId* field at database level.

### Installation

To install and execute the DB Partition Client you just need to follow these
steps:

1. Download the latest version of the DB Upgrade Client.

2. Unzip the client

3. Copy your jdbc driver

### Usage

This tool includes three scripts (mentioned previously) which will help you
during the partitioning configuration of Liferay databases:

- **validate:** it checks if the database has records associated to non-existed
companies.

- **updateindexes:** add the *companyId* field add to unique indexes as the last
index column if the index does not already have it.

- **nextcompanyid:** it reserves and provides a new *companyId* until the new
virtual instance (Company) is created.

To configure the Partition Client you can provide the database connection
beforehand using the portal-partition.properties or the tool will ask you for
this information.

For the Update Indexes command you can also configure the property
*update.unique.indexes.tables* with the tables to update separated by commas.