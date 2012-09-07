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
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Document Library Shortcut Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[4]/td[1]/a[2]/strong",
			RuntimeVariables.replace("Test2 Folder2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText("//div[2]/ul/li[6]/a"));
		selenium.clickAt("//div[2]/ul/li[6]/a",
			RuntimeVariables.replace("Add Shortcut"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div/span[2]/span/input",
			RuntimeVariables.replace("Community"));
		selenium.waitForPopUp("toGroup", RuntimeVariables.replace("30000"));
		selenium.selectWindow("toGroup");
		selenium.click("link=Document Library Shortcut Community");
		selenium.selectWindow("null");
		selenium.waitForText("_20_toGroupName",
			"Document Library Shortcut Community");
		assertEquals(RuntimeVariables.replace(
				"Document Library Shortcut Community"),
			selenium.getText("_20_toGroupName"));
		selenium.clickAt("_20_selectToFileEntryButton",
			RuntimeVariables.replace("Document"));
		selenium.waitForPopUp("toGroup", RuntimeVariables.replace("30000"));
		selenium.selectWindow("toGroup");
		selenium.click(RuntimeVariables.replace("link=Test1 Folder1"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//td[1]/a");
		selenium.selectWindow("null");
		selenium.waitForText("_20_toFileEntryTitle", "Test1 Document1.txt");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("_20_toFileEntryTitle"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Your request processed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForText("//a/span/span", "Test1 Document1.txt");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//a/span/span"));
		selenium.waitForText("//a/div", "This is test1 document1.");
		assertEquals(RuntimeVariables.replace("This is test1 document1."),
			selenium.getText("//a/div"));
	}
}