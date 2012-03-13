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

package com.liferay.portalweb.demo.devcon6100.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsDocumentsStagingAdminTest extends BaseTestCase {
	public void testDefinePermissionsDocumentsStagingAdmin()
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
			RuntimeVariables.replace("Documents and Media"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Add Document")
										.equals(selenium.getText(
								"//tr[3]/td[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add Document"),
			selenium.getText("//tr[3]/td[2]"));
		assertFalse(selenium.isChecked("//th[1]/input"));
		selenium.clickAt("//th[1]/input",
			RuntimeVariables.replace("Document Library"));
		assertTrue(selenium.isChecked("//th[1]/input"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//div[5]/div/div/table/tbody/tr[4]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[5]/div/div/table/tbody/tr[4]/td[1]/input"));
		selenium.clickAt("//div[5]/div/div/table/tbody/tr[4]/td[1]/input",
			RuntimeVariables.replace("Delete"));
		assertTrue(selenium.isChecked(
				"//div[5]/div/div/table/tbody/tr[4]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("Update Discussion"),
			selenium.getText("//div[5]/div/div/table/tbody/tr[8]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[5]/div/div/table/tbody/tr[8]/td[1]/input"));
		selenium.clickAt("//div[5]/div/div/table/tbody/tr[8]/td[1]/input",
			RuntimeVariables.replace("Update Discussion"));
		assertTrue(selenium.isChecked(
				"//div[5]/div/div/table/tbody/tr[8]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//div[5]/div/div/table/tbody/tr[9]/td[2]"));
		assertFalse(selenium.isChecked(
				"//div[5]/div/div/table/tbody/tr[9]/td[1]/input"));
		selenium.clickAt("//div[5]/div/div/table/tbody/tr[9]/td[1]/input",
			RuntimeVariables.replace("View"));
		assertTrue(selenium.isChecked(
				"//div[5]/div/div/table/tbody/tr[9]/td[1]/input"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}