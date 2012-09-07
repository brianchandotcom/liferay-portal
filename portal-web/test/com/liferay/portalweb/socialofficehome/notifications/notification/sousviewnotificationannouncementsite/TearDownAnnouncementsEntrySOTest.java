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

package com.liferay.portalweb.socialofficehome.notifications.notification.sousviewnotificationannouncementsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownAnnouncementsEntrySOTest extends BaseTestCase {
	public void testTearDownAnnouncementsEntrySO() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.waitForVisible(
					"//li[contains(@class, 'selected')]/a/span");
				assertEquals(RuntimeVariables.replace("Dashboard"),
					selenium.getText(
						"//li[contains(@class, 'selected')]/a/span"));
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				selenium.waitForVisible(
					"//select[@id='_5_WAR_soportlet_tabs1']");
				assertTrue(selenium.isPartialText(
						"//select[@id='_5_WAR_soportlet_tabs1']", "All Sites"));
				selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
					RuntimeVariables.replace("All Sites"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Name"));
				Thread.sleep(5000);
				assertTrue(selenium.isPartialText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a",
						"Name"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//nav/ul/li[1]/a/span"));
				selenium.clickAt("link=Entries",
					RuntimeVariables.replace("Entries"));
				selenium.waitForPageToLoad("30000");

				boolean entry1Present = selenium.isElementPresent("link=Delete");

				if (!entry1Present) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:

				boolean entry2Present = selenium.isElementPresent("link=Delete");

				if (!entry2Present) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:

				boolean entry3Present = selenium.isElementPresent("link=Delete");

				if (!entry3Present) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:

				boolean entry4Present = selenium.isElementPresent("link=Delete");

				if (!entry4Present) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:

				boolean entry5Present = selenium.isElementPresent("link=Delete");

				if (!entry5Present) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Delete"));
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