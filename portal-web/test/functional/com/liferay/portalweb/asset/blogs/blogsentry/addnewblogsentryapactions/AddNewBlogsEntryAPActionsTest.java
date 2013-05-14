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

package com.liferay.portalweb.asset.blogs.blogsentry.addnewblogsentryapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewBlogsEntryAPActionsTest extends BaseTestCase {
	public void testAddNewBlogsEntryAPActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add New']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add New"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Blogs Entry')]");
		assertEquals(RuntimeVariables.replace("Blogs Entry"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Blogs Entry')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Blogs Entry')]",
			RuntimeVariables.replace("Blogs Entry"));
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForVisible("//input[@id='_33_title']");
		selenium.type("//input[@id='_33_title']",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Blogs Entry Content"));
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForVisible("//input[@value='Publish']");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//h3[@class='asset-title']/a");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='asset-summary']"));
	}
}