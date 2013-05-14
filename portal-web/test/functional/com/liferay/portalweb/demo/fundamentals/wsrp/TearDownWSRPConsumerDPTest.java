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

package com.liferay.portalweb.demo.fundamentals.wsrp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWSRPConsumerDPTest extends BaseTestCase {
	public void testTearDownWSRPConsumerDP() throws Exception {
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
				selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Consumers",
					RuntimeVariables.replace("Consumers"));
				selenium.waitForPageToLoad("30000");

				boolean consumer1Present = selenium.isElementPresent(
						"//td[2]/span/ul/li/strong/a");

				if (!consumer1Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 2:

				boolean consumer2Present = selenium.isElementPresent(
						"//td[2]/span/ul/li/strong/a");

				if (!consumer2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 3:

				boolean consumer3Present = selenium.isElementPresent(
						"//td[2]/span/ul/li/strong/a");

				if (!consumer3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 4:

				boolean consumer4Present = selenium.isElementPresent(
						"//td[2]/span/ul/li/strong/a");

				if (!consumer4Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 5:

				boolean consumer5Present = selenium.isElementPresent(
						"//td[2]/span/ul/li/strong/a");

				if (!consumer5Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}