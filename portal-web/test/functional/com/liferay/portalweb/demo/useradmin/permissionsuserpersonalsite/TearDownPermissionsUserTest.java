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
public class TearDownPermissionsUserTest extends BaseTestCase {
	public void testTearDownPermissionsUser() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText("//tr[13]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[13]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[13]/td[4]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("bbb"));
		selenium.keyPress("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("\\13"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"), selenium.getText("//h3"));
		selenium.check("//tr[3]/td[1]/input");
		assertTrue(selenium.isChecked("//tr[3]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("Add to Page"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[3]/td[4]/span/a/span"));
		selenium.clickAt("//tr[3]/td[4]/span/a/span",
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
		selenium.waitForText("//tr[3]/td[3]/div/span/span/span",
			"User Personal Site");
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[3]/td[3]/div/span/span/span"));
		selenium.check("//tr[6]/td[1]/input");
		assertTrue(selenium.isChecked("//tr[6]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[6]/td[4]/span/a/span"));
		selenium.clickAt("//tr[6]/td[4]/span/a/span",
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
		selenium.waitForText("//tr[6]/td[3]/div/span/span/span",
			"User Personal Site");
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[6]/td[3]/div/span/span/span"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"), selenium.getText("//h3"));
		selenium.check("//tr[3]/td[1]/input");
		assertTrue(selenium.isChecked("//tr[3]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("Add Entry"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[3]/td[4]/span/a/span"));
		selenium.clickAt("//tr[3]/td[4]/span/a/span",
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
		selenium.waitForText("//tr[3]/td[3]/div/span/span/span",
			"User Personal Site");
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[3]/td[3]/div/span/span/span"));
		selenium.check("//tr[4]/td[1]/input");
		assertTrue(selenium.isChecked("//tr[4]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[4]/td[4]/span/a/span"));
		selenium.clickAt("//tr[4]/td[4]/span/a/span",
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
		selenium.waitForText("//tr[4]/td[3]/div/span/span/span",
			"User Personal Site");
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[4]/td[3]/div/span/span/span"));
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
	}
}