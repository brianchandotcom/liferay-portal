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

package com.liferay.portalweb.demo.useradmin.permissionssitetemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineScopedRegularRolePermissionsTest extends BaseTestCase {
	public void testDefineScopedRegularRolePermissions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("RegularRole"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("RegularRole Name"),
			selenium.getText("//tr[3]/td/a"));
		selenium.clickAt("//tr[3]/td/a",
			RuntimeVariables.replace("RegularRole Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("b"));
		selenium.keyPress("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("\\13"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"), selenium.getText("//h3"));
		assertFalse(selenium.isChecked(
				"//input[contains(@value,'BlogsEntryDELETE')]"));
		selenium.clickAt("//input[contains(@value,'BlogsEntryDELETE')]",
			RuntimeVariables.replace("Blogs Entry Delete"));
		assertTrue(selenium.isChecked(
				"//input[contains(@value,'BlogsEntryDELETE')]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText(
				"//div[2]/div/div/table/tbody/tr[4]/td[4]/span/a/span"));
		selenium.clickAt("//div[2]/div/div/table/tbody/tr[4]/td[4]/span/a/span",
			RuntimeVariables.replace("Limit Scope"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Roles");
		selenium.waitForVisible("//input[@id='_128_name']");
		selenium.type("//input[@id='_128_name']",
			RuntimeVariables.replace("Site"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//tr[contains(.,'Site Name')]/td/a"));
		selenium.clickAt("//tr[contains(.,'Site Name')]/td/a",
			RuntimeVariables.replace("Site Name"));
		selenium.selectWindow("null");
		selenium.waitForVisible("//span[@class='permission-scopes']/span/span");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//span[@class='permission-scopes']/span/span"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}