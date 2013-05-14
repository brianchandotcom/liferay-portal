/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.useradmin.usermanagementuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignUser1RolesTest extends BaseTestCase {
	public void testAssignUser1Roles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("selen01"),
			selenium.getText(
				"//div[2]/div[2]/div/div[1]/div/table/tbody/tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xpath=(//span[@title='Actions']/ul/li/strong/a)[2]"));
		selenium.clickAt("xpath=(//span[@title='Actions']/ul/li/strong/a)[2]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//div[@class='lfr-menu-list unstyled']/ul/li/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible("//div[6]/span[2]/a/span");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//div[6]/span[2]/a/span"));
		selenium.clickAt("//div[6]/span[2]/a/span",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("Users and Organizations");
		selenium.waitForVisible(
			"//form[@id='_125_fm']/div[3]/div/div/table/tbody/tr[3]/td/a");
		assertEquals(RuntimeVariables.replace("Site Administrator"),
			selenium.getText(
				"//form[@id='_125_fm']/div[3]/div/div/table/tbody/tr[3]/td/a"));
		selenium.clickAt("//form[@id='_125_fm']/div[3]/div/div/table/tbody/tr[3]/td/a",
			RuntimeVariables.replace("Site Administrator"));
		selenium.selectWindow("null");
		selenium.waitForVisible("//table/tr/td[1]");
		assertEquals(RuntimeVariables.replace("Site Administrator"),
			selenium.getText("//table/tr/td[1]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Site Administrator"),
			selenium.getText("//div[3]/div/div/table/tbody/tr[3]/td[1]"));
	}
}