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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePage2PortletEntriesPerPage5Test extends BaseTestCase {
	public void testViewConfigurePage2PortletEntriesPerPage5()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page2 Name");
		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DL Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DL Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DL Folder7 Name"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-first-link')])[2]"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-prev-link')])[2]"));
		assertTrue(selenium.isElementPresent("//a[@page='1']"));
		assertTrue(selenium.isElementPresent("//a[@page='2']"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-next-link')])[2]"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-last-link')])[2]"));
		selenium.clickAt("//a[@page='2']", RuntimeVariables.replace("2"));
		selenium.waitForText("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			"DL Folder7 Name");
		assertEquals(RuntimeVariables.replace("DL Folder7 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DL Folder2 Name"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
		assertFalse(selenium.isTextPresent("DL Folder4 Name"));
		assertFalse(selenium.isTextPresent("DL Folder5 Name"));
		assertFalse(selenium.isTextPresent("DL Folder6 Name"));
		selenium.clickAt("//a[@page='1']", RuntimeVariables.replace("1"));
		selenium.waitForText("xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]",
			"DL Folder2 Name");
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DL Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DL Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DL Folder7 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-next-link')])[2]",
			RuntimeVariables.replace("Next"));
		selenium.waitForText("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			"DL Folder7 Name");
		assertEquals(RuntimeVariables.replace("DL Folder7 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DL Folder2 Name"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
		assertFalse(selenium.isTextPresent("DL Folder4 Name"));
		assertFalse(selenium.isTextPresent("DL Folder5 Name"));
		assertFalse(selenium.isTextPresent("DL Folder6 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-prev-link')])[2]",
			RuntimeVariables.replace("Previous"));
		selenium.waitForText("xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]",
			"DL Folder2 Name");
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DL Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DL Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DL Folder7 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-last-link')])[2]",
			RuntimeVariables.replace("Last"));
		selenium.waitForText("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			"DL Folder7 Name");
		assertEquals(RuntimeVariables.replace("DL Folder7 Name"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DL Folder2 Name"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
		assertFalse(selenium.isTextPresent("DL Folder4 Name"));
		assertFalse(selenium.isTextPresent("DL Folder5 Name"));
		assertFalse(selenium.isTextPresent("DL Folder6 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-first-link')])[2]",
			RuntimeVariables.replace("First"));
		selenium.waitForText("xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]",
			"DL Folder2 Name");
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DL Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DL Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DL Folder7 Name"));
	}
}