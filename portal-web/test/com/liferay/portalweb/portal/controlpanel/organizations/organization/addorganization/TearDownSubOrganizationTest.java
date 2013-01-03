/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSubOrganizationTest extends BaseTestCase {
	public void testTearDownSubOrganization() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Suborganization*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean subOrganization1Present = selenium.isElementPresent(
						"//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong");

				if (!subOrganization1Present) {
					label = 11;

					continue;
				}

				selenium.clickAt("//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong",
					RuntimeVariables.replace("Suborganization"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Current",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");

				boolean users1Found = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!users1Found) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Suborganization*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean subOrganization2Present = selenium.isElementPresent(
						"//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong");

				if (!subOrganization2Present) {
					label = 10;

					continue;
				}

				selenium.clickAt("//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong",
					RuntimeVariables.replace("Suborganization"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Current",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");

				boolean users2Found = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!users2Found) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Suborganization*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean subOrganization3Present = selenium.isElementPresent(
						"//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong");

				if (!subOrganization3Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong",
					RuntimeVariables.replace("Suborganization"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Current",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");

				boolean users3Found = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!users3Found) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 4:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Suborganization*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean subOrganization4Present = selenium.isElementPresent(
						"//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong");

				if (!subOrganization4Present) {
					label = 8;

					continue;
				}

				selenium.clickAt("//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong",
					RuntimeVariables.replace("Suborganization"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Current",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");

				boolean users4Found = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!users4Found) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 5:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Suborganization*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean subOrganization5Present = selenium.isElementPresent(
						"//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong");

				if (!subOrganization5Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("//td[@id='_125_organizationsSearchContainer_col-name_row-1']/a/strong",
					RuntimeVariables.replace("Suborganization"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Current",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");

				boolean users5Found = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!users5Found) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 6:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 100:
				label = -1;
			}
		}
	}
}