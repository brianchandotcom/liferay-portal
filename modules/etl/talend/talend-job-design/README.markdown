# Talend Job Designs for Liferay

This project contains a Talend Open Studio workspace for working with Liferay Talend job designs.

## Prerequisites

* Apache Maven 3.3+
* JDK 1.8+
* Talend Open Studio 7.1.1
	* Components API v0.25.3

Talend Open Studio is available at these locations:
* [Talend Open Studio home](https://www.talend.com/products/talend-open-studio/)
* [7.1.1 Direct link](https://download-mirror2.talend.com/esb/release/V7.1.1/TOS_ESB-20181026_1147-V7.1.1.zip)

> Notes for all Talend releases are here: <https://www.talend.com/products/data-integration-manuals-release-notes/>

## Importing Liferay Talend Job Design Sources Into a Workspace

1. Start Talend Open Studio 7.1.1 DI or ESB.
1. In the Welcome form, select *Import an existing project*.
1. Click *Select*. The Import form appears.
1. In the Import form, enter a *Project name*.
1. Choose the *Select root directory* radio button.
1. Click *Browse* and select `[repository home]/modules/etl/talend/talend-job-designs/tos-711-liferay`.
1. Click *Import*.

Talend Open Studio imports the Liferay Talend job design sources into your workspace.

## Contributing to Job Designs

Here's how to contribute to Liferay Talend job designs:

1. In Talend Open Studio, apply changes to a job design.
1. Test the job design.
1. Copy all modified `*.item` files back to Liferay repository.
1. Commit the `*.item` files.
1. Send a pull request to the `liferay-commerce` GitHub user at <https://github.com/liferay/liferay-portal>.
1. In [JIRA](https://issues.liferay.com/projects/COMMERCE/issues), notify the Data Intagration team about your contribution by creating a *Liferay Commerce (COMMERCE)* project ticket.