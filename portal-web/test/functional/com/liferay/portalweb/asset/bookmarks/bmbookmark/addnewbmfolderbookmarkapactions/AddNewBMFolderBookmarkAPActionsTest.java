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

package com.liferay.portalweb.asset.bookmarks.bmbookmark.addnewbmfolderbookmarkapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewBMFolderBookmarkAPActionsTest extends BaseTestCase {
	public void testAddNewBMFolderBookmarkAPActions() throws Exception {
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
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Bookmarks Entry')]");
		assertEquals(RuntimeVariables.replace("Bookmarks Entry"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Bookmarks Entry')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Bookmarks Entry')]",
			RuntimeVariables.replace("Bookmarks Entry"));
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible("//input[@value='Select']");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(1000);
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[contains(@id,'selectFolder')]");
		selenium.selectFrame("//iframe[contains(@id,'selectFolder')]");
		selenium.waitForVisible("//tr[contains(.,'BM Folder Name')]/td[1]/a");
		assertEquals(RuntimeVariables.replace("BM Folder Name"),
			selenium.getText("//tr[contains(.,'BM Folder Name')]/td[1]/a"));
		selenium.waitForVisible(
			"//tr[contains(.,'BM Folder Name')]/td[4]/span/span/input[@value='Choose']");
		selenium.click(
			"//tr[contains(.,'BM Folder Name')]/td[4]/span/span/input[@value='Choose']");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForText("//a[@id='_28_folderName']", "BM Folder Name");
		assertEquals(RuntimeVariables.replace("BM Folder Name"),
			selenium.getText("//a[@id='_28_folderName']"));
		selenium.type("//input[@id='_28_name']",
			RuntimeVariables.replace("BM Folder Bookmark Name"));
		selenium.type("//input[@id='_28_url']",
			RuntimeVariables.replace("http://www.liferay.com"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		Thread.sleep(1000);
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//h3[@class='asset-title']/a");
		assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
		selenium.clickAt("//h3[@class='asset-title']/a",
			RuntimeVariables.replace("BM Folder Bookmark Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"BM Folder Bookmark Name (http://www.liferay.com)(Opens New Window)"),
			selenium.getText("//div[@class='asset-content']/a"));
	}
}