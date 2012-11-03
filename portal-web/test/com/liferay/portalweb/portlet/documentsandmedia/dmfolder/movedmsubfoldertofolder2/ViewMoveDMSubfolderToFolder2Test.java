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
public class ViewMoveDMSubfolderToFolder2Test extends BaseTestCase {
	public void testViewMoveDMSubfolderToFolder2() throws Exception {
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
		selenium.waitForText("//div[@class='entries-empty portlet-msg-info']",
			"There are no documents or media files in this folder.");
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("DM Folder1 Subfolder Name"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]",
			RuntimeVariables.replace("DM Folder2 Name"));
		selenium.waitForText("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			"DM Folder1 Subfolder Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Subfolder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
	}
}