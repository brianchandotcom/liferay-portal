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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder.deletedmsubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDMSubfolderTest extends BaseTestCase {
	public void testDeleteDMSubfolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a[2]"));
		assertFalse(selenium.isChecked(
				"//input[@id='_20_rowIdsFolderCheckbox']"));
		selenium.clickAt("//input[@id='_20_rowIdsFolderCheckbox']",
			RuntimeVariables.replace("Entry Check Box"));
		assertTrue(selenium.isChecked("//input[@id='_20_rowIdsFolderCheckbox']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='portlet-msg-success taglib-trash-undo']",
			"The selected item was moved to the Recycle Bin. Undo");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']"));
		assertFalse(selenium.isTextPresent("DM Subfolder Name"));
	}
}