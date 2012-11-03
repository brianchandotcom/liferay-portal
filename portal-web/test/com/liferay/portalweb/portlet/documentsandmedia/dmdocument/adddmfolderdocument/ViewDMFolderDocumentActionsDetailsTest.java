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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocument.adddmfolderdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderDocumentActionsDetailsTest extends BaseTestCase {
	public void testViewDMFolderDocumentActionsDetails()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='entry-thumbnail']/img");
		assertTrue(selenium.isVisible("//div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a/span[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//li[contains(@class,'folder selected')]/a/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//span[@class='aui-toolbar-content']/button[1]");
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText("//span[@class='aui-toolbar-content']/button[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//span[@class='aui-toolbar-content']/button[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//span[@class='aui-toolbar-content']/button[3]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText("//span[@class='aui-toolbar-content']/button[4]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//span[@class='aui-toolbar-content']/button[5]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText("//span[@class='aui-toolbar-content']/button[6]"));
	}
}