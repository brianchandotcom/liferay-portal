# Talend Components for Liferay DXP 7.1

This project contains components for working with Liferay DXP 7.1 from Talend
Open Studio.

## Prerequisites

* JDK 1.8+
* Apache Maven 3.5.4+
* Open Talend 7.2.1 M4

Download Talend Open Studio: https://www.talend.com/products/talend-open-studio/
* Direct link: https://sourceforge.net/projects/talendesb/files/Talend%20Open%20Studio%20for%20ESB/7.2.1M4/

## Compile, Test and Deploy the component to Talend Open Studio

From the current folder invoke the following command:

`mvn clean install package talend-component:deploy-in-studio -Dtalend.component.studioHome=PATH_TO_THE_STUDIO_HOME`