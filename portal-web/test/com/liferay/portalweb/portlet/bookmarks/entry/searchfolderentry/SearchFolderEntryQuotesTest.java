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

package com.liferay.portalweb.portlet.bookmarks.entry.searchfolderentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchFolderEntryQuotesTest extends BaseTestCase {
	public void testSearchFolderEntryQuotes() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_28_keywords']",
			RuntimeVariables.replace("\"Search Name\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark1 Search Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Bookmark1 Search Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark1 Search Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark2 Search Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Bookmark2 Search Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark2 Search Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 2."),
			selenium.getText("//div[@class='search-results']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_28_keywords']",
			RuntimeVariables.replace("\"Bookmark1 Name\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"No entries were found that matched the keywords: \"Bookmark1 Name\"."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Bookmark Folder Name"));
		assertFalse(selenium.isTextPresent("Bookmark1 Search Name"));
	}
}