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

package com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletavailableigimageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewIGFolderImageAPActionsTest extends BaseTestCase {
	public void testAddNewIGFolderImageAPActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		selenium.click("//span[@title='Add New']/ul/li/strong/a");
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a",
			RuntimeVariables.replace("Basic Document"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@value='Select']");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.waitForPopUp("folder", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=folder");
		selenium.selectWindow("name=folder");
		Thread.sleep(5000);
		selenium.waitForText("//a", "IG Folder Name");
		assertEquals(RuntimeVariables.replace("IG Folder Name"),
			selenium.getText("//a"));
		selenium.click("//input[@value='Choose']");
		selenium.selectWindow("null");
		selenium.waitForText("//a[@id='_20_folderName']", "IG Folder Name");
		assertEquals(RuntimeVariables.replace("IG Folder Name"),
			selenium.getText("//a[@id='_20_folderName']"));
		selenium.type("//input[@id='_20_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\assetpublisher\\dependencies\\IGImage.jpg"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("IG Folder Image Name"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h3[@class='asset-title']/a",
			"IG Folder Image Name");
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='asset-resource-info']/div/img"));
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//div[@class='asset-resource-info']/div"));
	}
}