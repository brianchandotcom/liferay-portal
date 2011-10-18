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

package com.liferay.portalweb.portal.controlpanel.bookmarks;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNullURLTest extends BaseTestCase {
	public void testAddNullURL() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
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
		selenium.clickAt("link=Bookmarks", RuntimeVariables.replace("Bookmarks"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("Edited Test Folder"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[5]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add Bookmark"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[5]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[5]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_28_name']",
			RuntimeVariables.replace("Null Entry Test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("(Required)"),
			selenium.getText("//label/span"));
	}
}