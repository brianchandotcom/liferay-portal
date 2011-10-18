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
					if (second >= 90) {
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

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@name='_125_keywords']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("selenium"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean usersPresent = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!usersPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace("Advanced \u00bb"));

				for (int second = 0;; second++) {
					if (second >= 90) {
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

				selenium.select("//select[@id='_125_status']",
					RuntimeVariables.replace("Inactive"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean users2Present = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!users2Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace(""));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("No users were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 4:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
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

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");

				boolean userGroup1Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup1Present) {
					label = 9;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_127_allRowIds']"));
				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_127_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean userGroup2Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup2Present) {
					label = 8;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_127_allRowIds']"));
				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_127_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean userGroup3Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup3Present) {
					label = 7;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_127_allRowIds']"));
				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_127_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean userGroup4Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup4Present) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_127_allRowIds']"));
				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_127_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean userGroup5Present = selenium.isElementPresent(
						"//td[2]/a");

				if (!userGroup5Present) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_127_allRowIds']"));
				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_127_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 100:
				label = -1;
			}
		}
	}
}