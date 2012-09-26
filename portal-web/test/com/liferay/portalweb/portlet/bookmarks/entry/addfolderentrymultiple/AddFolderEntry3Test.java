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

package com.liferay.portalweb.portlet.bookmarks.entry.addfolderentrymultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolderEntry3Test extends BaseTestCase {
	public void testAddFolderEntry3() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add Bookmark"),
			selenium.getText("//div[2]/ul/li[5]/a"));
		selenium.clickAt("//div[2]/ul/li[5]/a",
			RuntimeVariables.replace("Add Bookmark"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_28_name']",
			RuntimeVariables.replace("Test3 Folder3 Entry3"));
		selenium.type("//input[@id='_28_url']",
			RuntimeVariables.replace("http://www.narutofan.com"));
		selenium.type("//textarea[@id='_28_description']",
			RuntimeVariables.replace("This is a test3 folder3 entry3."));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test1 Folder1 Entry1"),
			selenium.getText("//tr[3]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("http://www.liferay.com"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Test2 Folder2 Entry2"),
			selenium.getText("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("http://www.openqa.org"),
			selenium.getText("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Test3 Folder3 Entry3"),
			selenium.getText("//tr[5]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("http://www.narutofan.com"),
			selenium.getText("//tr[5]/td[2]/a"));
	}
}