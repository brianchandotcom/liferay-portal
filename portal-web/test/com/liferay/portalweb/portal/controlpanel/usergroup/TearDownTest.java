/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.usergroup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownTest extends BaseTestCase {
	public void testTearDown() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean usersPresent = selenium.isElementPresent(
						"//form/div[2]/div/div/div/div/div[2]");

				if (!usersPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='usersAdminUsersPanel']/div[2]/div/div/table/tbody/tr/th/input",
					RuntimeVariables.replace("Select All"));
				selenium.click("//input[@value='Deactivate']");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				selenium.clickAt("link=View All Users",
					RuntimeVariables.replace("View All Users"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean advancedVisible = selenium.isVisible(
						"link=Advanced \u00bb");

				if (!advancedVisible) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace("Advanced"));

			case 3:

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//select[@id='_125_status']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.select("//select[@id='_125_status']",
					RuntimeVariables.replace("Inactive"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("Select All"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("No users were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=View", RuntimeVariables.replace("View"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("User Groups"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean userGroup1Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup1Present) {
					label = 8;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=View", RuntimeVariables.replace("View"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("User Groups"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean userGroup2Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup2Present) {
					label = 7;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=View", RuntimeVariables.replace("View"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("User Groups"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean userGroup3Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup3Present) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=View", RuntimeVariables.replace("View"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("User Groups"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean userGroup4Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup4Present) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=View", RuntimeVariables.replace("View"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("User Groups"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean userGroup5Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup5Present) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.saveScreenShotAndSource();
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 100:
				label = -1;
			}
		}
	}
}