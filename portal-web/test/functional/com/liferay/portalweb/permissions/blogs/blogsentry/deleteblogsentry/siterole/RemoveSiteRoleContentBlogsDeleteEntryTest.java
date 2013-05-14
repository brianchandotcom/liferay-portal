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

package com.liferay.portalweb.permissions.blogs.blogsentry.deleteblogsentry.siterole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveSiteRoleContentBlogsDeleteEntryTest extends BaseTestCase {
	public void testRemoveSiteRoleContentBlogsDeleteEntry()
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
			RuntimeVariables.replace("Siterole"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole Name"),
			selenium.getText("//tr[contains(.,'Roles Siterole Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site"),
			selenium.getText("//tr[contains(.,'Roles Siterole Name')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'Roles Siterole Name')]/td[1]/a",
			RuntimeVariables.replace("Roles Siterole Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//ul[@class='tabview-list']/li/span/a[contains(.,'Define Permissions')]"));
		selenium.clickAt("//ul[@class='tabview-list']/li/span/a[contains(.,'Define Permissions')]",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText("//section[@id='portlet_128']/div/div/div/h3"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//tr[contains(.,'Blogs')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry"),
			selenium.getText("//tr[contains(.,'Blogs')]/td[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//tr[contains(.,'Blogs')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//tr[contains(.,'Blogs')]/td[4]/span/a/span"));
		selenium.clickAt("//tr[contains(.,'Blogs')]/td[4]/span/a/span",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForConfirmation(
			"Are you sure you want to delete this? It will be deleted immediately.");
		assertEquals(RuntimeVariables.replace("The permission was deleted."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"This role does not have any permissions."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}