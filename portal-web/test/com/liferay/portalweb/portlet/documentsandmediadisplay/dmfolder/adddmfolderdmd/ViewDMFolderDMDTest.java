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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderDMDTest extends BaseTestCase {
	public void testViewDMFolderDMD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Display Test Page",
			RuntimeVariables.replace("Documents and Media Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[1]/th[1]"));
		assertEquals(RuntimeVariables.replace("# of Folders"),
			selenium.getText("//tr[1]/th[2]"));
		assertEquals(RuntimeVariables.replace("# of Documents"),
			selenium.getText("//tr[1]/th[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//tr[3]/td[1]/a[2]/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[3]/td[3]/a"));
		selenium.clickAt("//tr[3]/td[1]/a[2]/strong",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-asset-icon lfr-asset-date']", "Last Updated"));
		assertEquals(RuntimeVariables.replace("0 Subfolders"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-subfolders']"));
		assertEquals(RuntimeVariables.replace("0 Documents"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-items last']"));
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-avatar']/img"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//div[@class='lfr-asset-name']/h4"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[1]/a"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[3]/a"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[4]/a"));
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[5]/a"));
		assertEquals(RuntimeVariables.replace("Multiple Documents"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[6]/a"));
		assertEquals(RuntimeVariables.replace("Add Document"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[7]/a"));
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[8]/a"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[9]/a"));
	}
}