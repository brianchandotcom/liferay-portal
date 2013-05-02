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

package com.liferay.portalweb.demo.useradmin.permissionsuserpersonalsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewOffPermissionsBlogsUSPTest extends BaseTestCase {
	public void testUser_ViewOffPermissionsBlogsUSP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/usersn/home/");
		selenium.waitForVisible("xPath=(//h1[@class='portlet-title'])[3]");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("xPath=(//h1[@class='portlet-title'])[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='subscribe']/span[2]/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Add Blog Entry']"));
		assertTrue(selenium.isElementNotPresent("//input[@value='Permissions']"));
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_addContent']/a/span");
		selenium.waitForVisible("//li[contains(@class,'add-application')]/a");
		assertEquals(RuntimeVariables.replace("Content and Applications"),
			selenium.getText("//li[contains(@class,'add-application')]/a"));
		selenium.clickAt("//li[contains(@class,'add-application')]/a",
			RuntimeVariables.replace("Content and Applications"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/autocomplete-filters/autocomplete-filters-min.js')]");
		assertEquals(RuntimeVariables.replace("Applications"),
			selenium.getText(
				"//div[@id='_145_addPanelContainer']/ul/li/span/a[contains(.,'Applications')]"));
		selenium.clickAt("//div[@id='_145_addPanelContainer']/ul/li/span/a[contains(.,'Applications')]",
			RuntimeVariables.replace("Applications"));
		selenium.waitForVisible("//input[@id='_145_searchApplication']");
		selenium.sendKeys("//input[@id='_145_searchApplication']",
			RuntimeVariables.replace("b"));
		assertTrue(selenium.isElementNotPresent("//div[@data-title='Blogs']"));
	}
}