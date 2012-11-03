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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder.movedmsubfoldertofolder2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveDMSubfolderToFolder2Test extends BaseTestCase {
	public void testMoveDMSubfolderToFolder2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			RuntimeVariables.replace("DM Folder1 Name"));
		selenium.waitForText("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			"DM Folder1 Subfolder Name");
		assertFalse(selenium.isChecked(
				"//form/div[1]/div/div/span[1]/span/span/input[2]"));
		selenium.clickAt("//form/div[1]/div/div/span[1]/span/span/input[2]",
			RuntimeVariables.replace("Document Checkbox"));
		assertTrue(selenium.isChecked(
				"//form/div[1]/div/div/span[1]/span/span/input[2]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a");
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//a[@id='_20_folderName']"));
		assertEquals(RuntimeVariables.replace("DM Folder1 Subfolder Name"),
			selenium.getText("//span[@class='folder-title']"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Documents and Media");
		selenium.waitForVisible("//tr[4]/td[1]/a");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText("//tr[4]/td[1]/a"));
		selenium.click(RuntimeVariables.replace("//tr[4]/td[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='Choose This Folder']");
		selenium.selectWindow("null");
		selenium.waitForText("//a[@id='_20_folderName']", "DM Folder2 Name");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText("//a[@id='_20_folderName']"));
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}