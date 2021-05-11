# Talend Job Designs for Liferay

This project contains Talend Open Studio workspace for working with Liferay Talend job designs.

Open Talend 7.1.1

## Prerequisites

* JDK 1.8+
* Apache Maven 3.3+
* Open Talend 7.1.1
	* Components API v0.25.3

Download Talend Open Studio: https://www.talend.com/products/talend-open-studio/
* Direct link: https://download-mirror2.talend.com/esb/release/V7.1.1/TOS_ESB-20181026_1147-V7.1.1.zip
* All releases link: https://www.talend.com/products/data-integration-manuals-release-notes/

## Talend Open Studio

### Import Job Design Sources In New Workspace

* start talend open studio 7.1.1 DI or ESB
* In welcome form select Import an existing project
* Click Select
* Import form is shown
* Enter Project name
* Choose radio button option Select root directory
* Click Browse button
* Select `repo_home`/modules/etl/talend/talend-job-designs/tos-711-liferay
* Click Import

Talend Open Studio would import sources into new workspace. If one would like to contribute to existing job designs this is workflow:
* apply changes to job design in TOS
* test job design
* copy updated `*.item` files back to Liferay repository
* create commit
* pull request should be sent to `liferay-commerce` github user
* notify Data Integration team with JIRA ticket