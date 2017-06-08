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

package com.liferay.portalweb.socialofficehome.sites.site.sofrsearchsitessitetyperestricted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOFr_SearchSitesSiteTypeRestrictedTest extends BaseTestCase {
	public void testSOFr_SearchSitesSiteTypeRestricted()
		throws Exception {
		selenium.open("/user/socialofficefriendsn/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/div/ul/li/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div/div/div/div/ul/li/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[3]/div/section/header/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"You are not a member of any sites. Search or open the directory to get started."),
			selenium.getText("//form/div[1]/ul/li"));
		assertFalse(selenium.isElementPresent(
				"//li[contains(@class, 'social-office-enabled')]"));
		assertFalse(selenium.isTextPresent("Private Site Name"));
		assertTrue(selenium.isVisible("//div/input[1]"));
		selenium.type("//div/input[1]", RuntimeVariables.replace("Restricted"));
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		assertEquals("Search", selenium.getValue("//input[@value='Search']"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[3]/a"));
		assertEquals(RuntimeVariables.replace("Join"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
	}
}