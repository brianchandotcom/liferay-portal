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
public class User_MergeSPChristmas2SPChristmasTest extends BaseTestCase {
	public void testUser_MergeSPChristmas2SPChristmas()
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
				"Christmas Site Pages Variation of Site Name"),
			selenium.getText("//span[@class='layout-set-branch-description']"));
		assertEquals(RuntimeVariables.replace("Manage Site Pages Variations"),
			selenium.getText("//a[@id='_170_manageLayoutSetBranches']"));
		selenium.clickAt("//a[@id='_170_manageLayoutSetBranches']",
			RuntimeVariables.replace("Manage Site Pages Variations"));
		Thread.sleep(5000);
		selenium.selectFrame(
			"//div[@class='yui3-widget-bd panel-bd dialog-bd dialog-iframe-bd']/iframe");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
		selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForText("//div[@class='lfr-menu-list unstyled']/ul/li[4]/a[contains(.,'Merge')]",
			"Merge");
		assertEquals(RuntimeVariables.replace("Merge"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a[contains(.,'Merge')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[4]/a[contains(.,'Merge')]",
			RuntimeVariables.replace("Merge"));
		Thread.sleep(5000);
		selenium.waitForVisible("//tr[3]/td[1]");
		assertEquals(RuntimeVariables.replace("Christmas"),
			selenium.getText("//tr[3]/td[1]"));
		Thread.sleep(5000);
		assertTrue(selenium.isElementPresent(
				"//a[contains(@data-layoutsetbranchname,'Christmas')]"));
		selenium.clickAt("//a[contains(@data-layoutsetbranchname,'Christmas')]",
			RuntimeVariables.replace("Select"));
		selenium.waitForConfirmation(
			"Are you sure you want to merge changes from Christmas?");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace("Site page variation was merged."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}