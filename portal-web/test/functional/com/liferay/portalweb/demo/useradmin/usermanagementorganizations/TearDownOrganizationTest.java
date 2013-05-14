/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.useradmin.usermanagementorganizations;

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
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Test*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization1Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!organization1Present) {
					label = 11;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[4]/span/ul/li/strong/a"));
				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 1"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization1Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!suborganization1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 2:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Test*"));
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
					RuntimeVariables.replace("Test*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization2Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!organization2Present) {
					label = 10;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[4]/span/ul/li/strong/a"));
				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 2"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization2Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!suborganization2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 3:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Test*"));
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
					RuntimeVariables.replace("Test*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization3Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!organization3Present) {
					label = 9;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[4]/span/ul/li/strong/a"));
				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 3"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization3Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!suborganization3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 4:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Test*"));
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
					RuntimeVariables.replace("Test*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization4Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!organization4Present) {
					label = 8;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[4]/span/ul/li/strong/a"));
				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 4"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization4Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!suborganization4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 5:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Test*"));
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
					RuntimeVariables.replace("Test*"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Search']"));
				selenium.waitForPageToLoad("30000");

				boolean organization5Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!organization5Present) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[4]/span/ul/li/strong/a"));
				selenium.clickAt("//td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Assign Users"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[@id='_125_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent("//a[2]/strong"));
				selenium.clickAt("//a[2]/strong",
					RuntimeVariables.replace("Organization Entry 5"));
				selenium.waitForPageToLoad("30000");

				boolean suborganization5Present = selenium.isElementPresent(
						"//td[4]/span/ul/li/strong/a");

				if (!suborganization5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 6:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Test*"));
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