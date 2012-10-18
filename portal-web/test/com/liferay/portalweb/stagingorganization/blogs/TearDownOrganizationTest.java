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

package com.liferay.portalweb.stagingorganization.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownOrganizationTest extends BaseTestCase {
	public void testTearDownOrganization() throws Exception {
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
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!organization1Present) {
					label = 16;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]",
					RuntimeVariables.replace("Assign Users"));
				selenium.waitForPageToLoad("30000");

				boolean user1Visible = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!user1Visible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 2:
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 1"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!suborganization1Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));

			case 3:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@id='_125_keywords']");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Row"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!organization2Present) {
					label = 15;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]",
					RuntimeVariables.replace("Assign Users"));
				selenium.waitForPageToLoad("30000");

				boolean user2Visible = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!user2Visible) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 4:
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 2"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!suborganization2Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));

			case 5:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Row"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!organization3Present) {
					label = 14;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]",
					RuntimeVariables.replace("Assign Users"));
				selenium.waitForPageToLoad("30000");

				boolean user3Visible = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!user3Visible) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 6:
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 3"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!suborganization3Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));

			case 7:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Row"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!organization4Present) {
					label = 13;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]",
					RuntimeVariables.replace("Assign Users"));
				selenium.waitForPageToLoad("30000");

				boolean user4Visible = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!user4Visible) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 8:
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 4"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!suborganization4Present) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));

			case 9:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Row"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!organization5Present) {
					label = 12;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Assign Users')]",
					RuntimeVariables.replace("Assign Users"));
				selenium.waitForPageToLoad("30000");

				boolean user5Visible = selenium.isVisible(
						"//input[@name='_125_allRowIds']");

				if (!user5Visible) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 10:
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 5"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!suborganization5Present) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));

			case 11:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Selenium"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Row"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 100:
				label = -1;
			}
		}
	}
}