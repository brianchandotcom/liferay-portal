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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_AddEventThreeMinutesSPChristmasBlogsSPTLTest
	extends BaseTestCase {
	public void testUser_AddEventThreeMinutesSPChristmasBlogsSPTL()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean christmasPresent = selenium.isElementPresent(
						"link=Christmas");

				if (!christmasPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Christmas",
					RuntimeVariables.replace("Christmas"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Christmas Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				selenium.waitForVisible("link=Blogs Test Page");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Christmas Staging"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace(
						"Schedule publication of Christmas to Live."),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a",
					RuntimeVariables.replace(
						"Schedule publication of Christmas to Live."));
				selenium.waitForVisible("//div[4]/div[1]/a");

				boolean startDateMonthVisible = selenium.isVisible(
						"_88_schedulerStartDateMonth");

				if (startDateMonthVisible) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[4]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 3:
				Thread.sleep(5000);
				assertTrue(selenium.isVisible(
						"//select[@name='_88_schedulerStartDateMinute']"));
				selenium.keyPress("//select[@name='_88_schedulerStartDateMinute']",
					RuntimeVariables.replace("\\40"));
				selenium.keyPress("//select[@name='_88_schedulerStartDateMinute']",
					RuntimeVariables.replace("\\40"));
				selenium.keyPress("//select[@name='_88_schedulerStartDateMinute']",
					RuntimeVariables.replace("\\40"));
				selenium.waitForVisible("//input[@value='Add Event']");
				selenium.clickAt("//input[@value='Add Event']",
					RuntimeVariables.replace("Add Event"));
				selenium.waitForVisible("//th[2]");
				assertEquals(RuntimeVariables.replace("No end date"),
					selenium.getText("//tr[3]/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}