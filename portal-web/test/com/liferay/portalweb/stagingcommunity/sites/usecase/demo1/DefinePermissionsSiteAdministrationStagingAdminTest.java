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

package com.liferay.portalweb.stagingcommunity.sites.usecase.demo1;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsSiteAdministrationStagingAdminTest
	extends BaseTestCase {
	public void testDefinePermissionsSiteAdministrationStagingAdmin()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Staging"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Staging Admin"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("Staging Admin"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Site administration"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//input[@name='_128_allRowIds'])[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[1]"));
		selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[1]",
			RuntimeVariables.replace("Page"));
		assertTrue(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[1]"));
		assertFalse(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[2]"));
		selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[2]",
			RuntimeVariables.replace("Page Variation"));
		assertTrue(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[2]"));
		assertFalse(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[3]"));
		selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[3]",
			RuntimeVariables.replace("Site"));
		assertTrue(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[3]"));
		assertFalse(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[4]"));
		selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[4]",
			RuntimeVariables.replace("Site Pages Variation"));
		assertTrue(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[4]"));
		assertFalse(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[5]"));
		selenium.clickAt("xPath=(//input[@name='_128_allRowIds'])[5]",
			RuntimeVariables.replace("Team"));
		assertTrue(selenium.isChecked(
				"xPath=(//input[@name='_128_allRowIds'])[5]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}