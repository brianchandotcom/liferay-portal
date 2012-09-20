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

package com.liferay.portalweb.portal.controlpanel.roles.role.addsiterole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSiteRoleTest extends BaseTestCase {
	public void testViewSiteRole() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_128_keywords']",
			RuntimeVariables.replace("Siterole"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole Name"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-name_row-1']"));
		assertEquals(RuntimeVariables.replace("Site"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-type_row-1']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//span[@title='Actions']/ul[@id='_128_ocerSearchContainer_1_menu']/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul[@id='_128_ocerSearchContainer_1_menu']/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//a[@id='_128_TabsBack']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(.,'Edit')]/ul[@class='aui-tabview-list']/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[contains(.,'Define Permissions')]/ul[@class='aui-tabview-list']/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Type"),
			selenium.getText("//div/label"));
		assertTrue(selenium.isPartialText("//fieldset/div/div/div", "Site"));
		assertEquals(RuntimeVariables.replace("New Name (Required)"),
			selenium.getText("//span/label"));
		assertEquals("Roles Siterole Name",
			selenium.getValue("//input[@id='_128_name']"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText("//span[2]/span/label"));
		assertEquals("", selenium.getValue("//input[@id='_128_title_en_US']"));
		assertEquals(RuntimeVariables.replace("Description"),
			selenium.getText("//span[3]/span/label"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}