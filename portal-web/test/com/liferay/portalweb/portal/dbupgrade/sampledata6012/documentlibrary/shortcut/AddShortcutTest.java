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

package com.liferay.portalweb.portal.dbupgrade.sampledata6012.documentlibrary.shortcut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddShortcutTest extends BaseTestCase {
	public void testAddShortcut() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-shortcut-community/");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace("Document Library Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test2 Folder2"),
			selenium.getText("//a/strong[contains(.,'Test2 Folder2')]"));
		selenium.clickAt("//a/strong[contains(.,'Test2 Folder2')]",
			RuntimeVariables.replace("Test2 Folder2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText("//li/a[contains(.,'Add Shortcut')]"));
		selenium.clickAt("//li/a[contains(.,'Add Shortcut')]",
			RuntimeVariables.replace("Add Shortcut"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("xpath=(//input[@value='Select'])[1]",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Document Library");
		selenium.waitForVisible("link=Document Library Shortcut Community");
		selenium.click("link=Document Library Shortcut Community");
		selenium.selectWindow("null");
		selenium.waitForText("//span[@id='_20_toGroupName']",
			"Document Library Shortcut Community");
		assertEquals(RuntimeVariables.replace(
				"Document Library Shortcut Community"),
			selenium.getText("//span[@id='_20_toGroupName']"));
		selenium.clickAt("xpath=(//input[@value='Select'])[2]",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Document Library");
		selenium.waitForVisible("link=Test1 Folder1");
		selenium.click(RuntimeVariables.replace("link=Test1 Folder1"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//td[1]/a", "Test1 Document1.txt"));
		selenium.click("//td[1]/a");
		selenium.selectWindow("null");
		selenium.waitForText("//span[@id='_20_toFileEntryTitle']",
			"Test1 Document1.txt");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//span[@id='_20_toFileEntryTitle']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//a/span/span[contains(.,'Test1 Document1.txt')]"));
		assertEquals(RuntimeVariables.replace("This is test1 document1."),
			selenium.getText("//a/div[contains(.,'This is test1 document1.')]"));
	}
}