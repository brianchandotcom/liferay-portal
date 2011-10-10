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

package com.liferay.portalweb.socialofficehome.sites.site.searchsitesdirectory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchSitesDirectoryTest extends BaseTestCase {
	public void testSearchSitesDirectory() throws Exception {
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("//div/div/div/div/ul/li/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[3]/div/section/header/h1/span"));
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("//form/div[2]/span/span/button[2]"));
		selenium.clickAt("//form/div[2]/span/span/button[2]",
			RuntimeVariables.replace("Directory"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//h1/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("//h1/span"));
		assertTrue(selenium.isVisible("//div/input[1]"));
		selenium.type("//div/input[1]", RuntimeVariables.replace("Open"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site1 Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		assertEquals(RuntimeVariables.replace("Open Site1 Description"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[3]"));
		assertFalse(selenium.isTextPresent("Private Site2 Name"));
		assertFalse(selenium.isTextPresent("Restricted Site3 Name"));
	}
}