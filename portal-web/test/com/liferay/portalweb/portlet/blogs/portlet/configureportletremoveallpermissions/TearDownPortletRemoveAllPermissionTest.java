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

package com.liferay.portalweb.portlet.blogs.portlet.configureportletremoveallpermissions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPortletRemoveAllPermissionTest extends BaseTestCase {
	public void testTearDownPortletRemoveAllPermission()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Blogs Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Permissions")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Permissions",
			RuntimeVariables.replace("Permissions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@name='16_ACTION_VIEW']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isChecked("//input[@name='16_ACTION_VIEW']"));
		selenium.clickAt("//input[@name='16_ACTION_VIEW']",
			RuntimeVariables.replace("Guest View"));
		assertTrue(selenium.isChecked("//input[@name='16_ACTION_VIEW']"));
		assertFalse(selenium.isChecked(
				"//input[@name='10154_ACTION_ADD_TO_PAGE']"));
		selenium.clickAt("//input[@name='10154_ACTION_ADD_TO_PAGE']",
			RuntimeVariables.replace("Owner Add to Page"));
		assertTrue(selenium.isChecked(
				"//input[@name='10154_ACTION_ADD_TO_PAGE']"));
		assertFalse(selenium.isChecked(
				"//input[@name='10154_ACTION_CONFIGURATION']"));
		selenium.clickAt("//input[@name='10154_ACTION_CONFIGURATION']",
			RuntimeVariables.replace("Owner Configuration"));
		assertTrue(selenium.isChecked(
				"//input[@name='10154_ACTION_CONFIGURATION']"));
		assertFalse(selenium.isChecked("//input[@name='10154_ACTION_VIEW']"));
		selenium.clickAt("//input[@name='10154_ACTION_VIEW']",
			RuntimeVariables.replace("Owner View"));
		assertTrue(selenium.isChecked("//input[@name='10154_ACTION_VIEW']"));
		assertFalse(selenium.isChecked(
				"//input[@name='10154_ACTION_PERMISSIONS']"));
		selenium.clickAt("//input[@name='10154_ACTION_PERMISSIONS']",
			RuntimeVariables.replace("Owner Permissions"));
		assertTrue(selenium.isChecked(
				"//input[@name='10154_ACTION_PERMISSIONS']"));
		assertFalse(selenium.isChecked("//input[@name='10159_ACTION_VIEW']"));
		selenium.clickAt("//input[@name='10159_ACTION_VIEW']",
			RuntimeVariables.replace("Site Member Permissions"));
		assertTrue(selenium.isChecked("//input[@name='10159_ACTION_VIEW']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Your request completed successfully.")
										.equals(selenium.getText(
								"//div[@class='portlet-msg-success']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}