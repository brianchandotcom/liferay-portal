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
public class DeletePermissionsPUBlogsPermissionsUPSTest extends BaseTestCase {
	public void testDeletePermissionsPUBlogsPermissionsUPS()
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
			RuntimeVariables.replace("Power"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Power User"),
			selenium.getText("//td/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[3]/a",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//tr[3]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//tr[3]/td[5]/span/a/span"));
		selenium.clickAt("//tr[3]/td[5]/span/a/span",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("The permission was deleted."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertNotEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//tr[3]/td[3]"));
	}
}