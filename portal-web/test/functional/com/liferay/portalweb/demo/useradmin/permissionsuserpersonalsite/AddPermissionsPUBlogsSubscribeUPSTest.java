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
public class AddPermissionsPUBlogsSubscribeUPSTest extends BaseTestCase {
	public void testAddPermissionsPUBlogsSubscribeUPS()
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
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"), selenium.getText("//h3"));
		selenium.check("//tr[5]/td[1]/input");
		assertTrue(selenium.isChecked("//tr[5]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText("//tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[5]/td[4]/span/a/span"));
		selenium.clickAt("//tr[5]/td[4]/span/a/span",
			RuntimeVariables.replace("Limit Scope"));
		selenium.waitForPopUp("site", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=site");
		Thread.sleep(5000);
		selenium.waitForText("//tr[4]/td[1]/a", "User Personal Site");
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[4]/td[1]/a"));
		selenium.clickAt("//tr[4]/td[1]/a",
			RuntimeVariables.replace("User Personal Site"));
		selenium.selectWindow("null");
		selenium.waitForText("//tr[5]/td[3]/div/span/span/span",
			"User Personal Site");
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[5]/td[3]/div/span/span/span"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//tr[7]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText("//tr[7]/td[3]"));
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[7]/td[4]"));
	}
}