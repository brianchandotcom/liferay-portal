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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_RenameMainSPVariationSeasonTest extends BaseTestCase {
	public void testUser_RenameMainSPVariationSeason()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'local-staging')]"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace(
				"Main Site Pages Variation of Site Name"),
			selenium.getText("//span[@class='layout-set-branch-description']"));
		Thread.sleep(5000);
		selenium.clickAt("//strong/a",
			RuntimeVariables.replace("Staging dropdown"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Manage Site Pages Variations"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[3]/a",
			RuntimeVariables.replace("Manage Site Pages Variations"));
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a/span");
		Thread.sleep(5000);
		selenium.selectFrame(
			"//div[@class='yui3-widget-bd panel-bd dialog-bd dialog-iframe-bd']/iframe");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForText("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a[contains(.,'Edit')]",
			"Edit");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//input[@id='_170_name']");
		selenium.type("//input[@id='_170_name']",
			RuntimeVariables.replace("Season"));
		selenium.type("//textarea[@id='_170_description']",
			RuntimeVariables.replace("Season Site Pages Variation of Site Name"));
		selenium.clickAt("//input[@value='Update']",
			RuntimeVariables.replace("Update"));
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Site page variation was updated.");
		assertEquals(RuntimeVariables.replace(
				"Site page variation was updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText("//td/strong", "Season"));
		selenium.selectFrame("relative=top");
		selenium.waitForText("//span[@class='layout-set-branch-description']",
			"Season Site Pages Variation of Site Name");
		assertEquals(RuntimeVariables.replace(
				"Season Site Pages Variation of Site Name"),
			selenium.getText("//span[@class='layout-set-branch-description']"));
	}
}