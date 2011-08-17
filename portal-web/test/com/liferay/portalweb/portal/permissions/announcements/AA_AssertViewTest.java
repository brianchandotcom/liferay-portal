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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AA_AssertViewTest extends BaseTestCase {
	public void testAA_AssertView() throws Exception {
		selenium.open("/web/site-name/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Announcements Permissions Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Announcements Permissions Page",
			RuntimeVariables.replace("Announcements Permissions Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test AA Announcement"),
			selenium.getText("xPath=(//h3[@class='entry-title'])[1]/a"));
		assertEquals(RuntimeVariables.replace("Test General Announcement"),
			selenium.getText("xPath=(//h3[@class='entry-title'])[2]/a"));
		assertEquals(RuntimeVariables.replace("Announcements Administrator"),
			selenium.getText("xPath=(//span[@class='entry-scope'])[1]"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("xPath=(//span[@class='entry-scope'])[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//p[@class=' entry-content entry-type-general'])[1]",
				"This is a test AA Announcement."));
		assertTrue(selenium.isPartialText(
				"xPath=(//p[@class=' entry-content entry-type-general'])[2]",
				"This is a test General Announcement."));
		selenium.clickAt("link=Manage Entries",
			RuntimeVariables.replace("Manage Entries"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_84_distributionScope']",
			RuntimeVariables.replace("label=Announcements Administrator"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test AA Announcement"),
			selenium.getText("//tr[3]/td/a"));
		selenium.select("//select[@id='_84_distributionScope']",
			RuntimeVariables.replace("label=Guest"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test Guest Announcement"),
			selenium.getText("//tr[3]/td/a"));
		selenium.select("//select[@id='_84_distributionScope']",
			RuntimeVariables.replace("label=Site Member"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test Member Announcement"),
			selenium.getText("//tr[3]/td/a"));
	}
}