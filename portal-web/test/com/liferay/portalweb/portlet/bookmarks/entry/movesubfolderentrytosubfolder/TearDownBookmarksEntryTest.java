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

package com.liferay.portalweb.portlet.bookmarks.entry.movesubfolderentrytosubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownBookmarksEntryTest extends BaseTestCase {
	public void testTearDownBookmarksEntry() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Bookmarks Test Page");
				selenium.clickAt("link=Bookmarks Test Page",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean bookmarksEntry1Present = selenium.isElementPresent(
						"//td[5]/span/ul/li/strong/a");

				if (!bookmarksEntry1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[5]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:

				boolean bookmarksEntry2Present = selenium.isElementPresent(
						"//td[5]/span/ul/li/strong/a");

				if (!bookmarksEntry2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//td[5]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:

				boolean bookmarksEntry3Present = selenium.isElementPresent(
						"//td[5]/span/ul/li/strong/a");

				if (!bookmarksEntry3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//td[5]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:

				boolean bookmarksEntry4Present = selenium.isElementPresent(
						"//td[5]/span/ul/li/strong/a");

				if (!bookmarksEntry4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//td[5]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:

				boolean bookmarksEntry5Present = selenium.isElementPresent(
						"//td[5]/span/ul/li/strong/a");

				if (!bookmarksEntry5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//td[5]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}