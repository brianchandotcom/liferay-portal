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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsBlogsManagePagesPowerUserTest extends BaseTestCase {
	public void testDefinePermissionsBlogsManagePagesPowerUser()
		throws Exception {
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
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Power"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Power User"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("Power User"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Blogs"));
		selenium.waitForText("//tr[4]/td[2]", "Add Entry");
		assertEquals(RuntimeVariables.replace("Add Entry"),
			selenium.getText("//tr[4]/td[2]"));
		assertTrue(selenium.isPartialText(
				"//span[@id='_128_groupHTMLcom.liferay.portlet.blogsADD_ENTRY']",
				"User Personal Site"));
		selenium.clickAt("//span[@id='_128_groupHTMLcom.liferay.portlet.blogsADD_ENTRY']/span/a",
			RuntimeVariables.replace("x"));
		selenium.waitForText("//span[@id='_128_groupHTMLcom.liferay.portlet.blogsADD_ENTRY']",
			"Portal");
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText(
				"//span[@id='_128_groupHTMLcom.liferay.portlet.blogsADD_ENTRY']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Sites"));
		selenium.waitForText("//tr[14]/td[2]", "Manage Pages");
		assertEquals(RuntimeVariables.replace("Manage Pages"),
			selenium.getText("//tr[14]/td[2]"));
		assertTrue(selenium.isPartialText(
				"//span[@id='_128_groupHTMLcom.liferay.portal.model.GroupMANAGE_LAYOUTS']",
				"User Personal Site"));
		selenium.clickAt("//span[@id='_128_groupHTMLcom.liferay.portal.model.GroupMANAGE_LAYOUTS']/span/a",
			RuntimeVariables.replace("x"));
		selenium.waitForText("//span[@id='_128_groupHTMLcom.liferay.portal.model.GroupMANAGE_LAYOUTS']",
			"Portal");
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText(
				"//span[@id='_128_groupHTMLcom.liferay.portal.model.GroupMANAGE_LAYOUTS']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}