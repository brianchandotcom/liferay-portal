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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebyfriends;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSOUserTest extends BaseTestCase {
	public void testTearDownSOUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/home/");

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
				assertEquals(RuntimeVariables.replace("View All Users"),
					selenium.getText("//a[@id='_125_allUsersLink']"));
				selenium.clickAt("//a[@id='_125_allUsersLink']",
					RuntimeVariables.replace("View All Users"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean basic1Visible = selenium.isVisible("link=\u00ab Basic");

				if (!basic1Visible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("socialoffice"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean user1Deactivated = selenium.isElementPresent(
						"_125_rowIds");

				if (!user1Deactivated) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean user2Deactivated = selenium.isElementPresent(
						"_125_rowIds");

				if (!user2Deactivated) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean user3Deactivated = selenium.isElementPresent(
						"_125_rowIds");

				if (!user3Deactivated) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean user4Deactivated = selenium.isElementPresent(
						"_125_rowIds");

				if (!user4Deactivated) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 6:

				boolean user5Deactivated = selenium.isElementPresent(
						"_125_rowIds");

				if (!user5Deactivated) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@name='_125_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Deactivate']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to deactivate the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 7:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("View All Users"),
					selenium.getText("//span/a/span"));
				selenium.clickAt("//span/a/span",
					RuntimeVariables.replace("View All Users"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace("Advanced \u00bb"));

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
				selenium.clickAt("//div[2]/span[2]/span/input",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);

				boolean user1Deleted = selenium.isElementPresent(
						"xPath=(//input[@name='_125_rowIds'])[1]");

				if (!user1Deleted) {
					label = 8;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_125_rowIds'])[1]",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"xPath=(//input[@value='Delete'])[1]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 8:

				boolean user2Deleted = selenium.isElementPresent(
						"xPath=(//input[@name='_125_rowIds'])[1]");

				if (!user2Deleted) {
					label = 9;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_125_rowIds'])[1]",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"xPath=(//input[@value='Delete'])[1]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 9:

				boolean user3Deleted = selenium.isElementPresent(
						"xPath=(//input[@name='_125_rowIds'])[1]");

				if (!user3Deleted) {
					label = 10;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_125_rowIds'])[1]",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"xPath=(//input[@value='Delete'])[1]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 10:

				boolean user4Deleted = selenium.isElementPresent(
						"xPath=(//input[@name='_125_rowIds'])[1]");

				if (!user4Deleted) {
					label = 11;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_125_rowIds'])[1]",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"xPath=(//input[@value='Delete'])[1]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 11:

				boolean user5Deleted = selenium.isElementPresent(
						"xPath=(//input[@name='_125_rowIds'])[2]");

				if (!user5Deleted) {
					label = 12;

					continue;
				}

				selenium.clickAt("xPath=(//input[@name='_125_rowIds'])[2]",
					RuntimeVariables.replace(""));
				selenium.click(RuntimeVariables.replace(
						"xPath=(//input[@value='Delete'])[1]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to permanently delete the selected users[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 12:
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("View All Users"),
					selenium.getText("//span/a/span"));
				selenium.clickAt("//span/a/span",
					RuntimeVariables.replace("View All Users"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 13;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 13:
			case 100:
				label = -1;
			}
		}
	}
}