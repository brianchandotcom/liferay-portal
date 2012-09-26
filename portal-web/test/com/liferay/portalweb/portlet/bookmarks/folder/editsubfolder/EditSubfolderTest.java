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

package com.liferay.portalweb.portlet.bookmarks.folder.editsubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditSubfolderTest extends BaseTestCase {
	public void testEditSubfolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Folder"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong", RuntimeVariables.replace("Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Subfolder"),
			selenium.getText("//td[1]/a/strong"));
		assertTrue(selenium.isPartialText("//td[1]/a",
				"This is a test subfolder."));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//td[4]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_28_name']",
			RuntimeVariables.replace("Test Subfolder Edited"));
		selenium.type("//textarea[@id='_28_description']",
			RuntimeVariables.replace("This is a test subfolder. Edited."));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test Subfolder Edited"),
			selenium.getText("//td[1]/a/strong"));
		assertTrue(selenium.isPartialText("//td[1]/a",
				"This is a test subfolder. Edited."));
	}
}