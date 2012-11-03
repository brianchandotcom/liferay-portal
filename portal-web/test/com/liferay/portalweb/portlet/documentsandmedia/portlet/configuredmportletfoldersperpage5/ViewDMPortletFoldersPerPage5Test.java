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

package com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletfoldersperpage5;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMPortletFoldersPerPage5Test extends BaseTestCase {
	public void testViewDMPortletFoldersPerPage5() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder6 Name"));
		Thread.sleep(5000);
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-next-link'])[2]",
			RuntimeVariables.replace("Next"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder6 Name");
		assertEquals(RuntimeVariables.replace("DM Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertFalse(selenium.isTextPresent("DM Folder1 Name"));
		assertFalse(selenium.isTextPresent("DM Folder2 Name"));
		assertFalse(selenium.isTextPresent("DM Folder3 Name"));
		assertFalse(selenium.isTextPresent("DM Folder4 Name"));
		assertFalse(selenium.isTextPresent("DM Folder5 Name"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-prev-link'])[2]",
			RuntimeVariables.replace("Previous"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder1 Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder6 Name"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-last-link'])[2]",
			RuntimeVariables.replace("Last"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder6 Name");
		assertEquals(RuntimeVariables.replace("DM Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertFalse(selenium.isTextPresent("DM Folder1 Name"));
		assertFalse(selenium.isTextPresent("DM Folder2 Name"));
		assertFalse(selenium.isTextPresent("DM Folder3 Name"));
		assertFalse(selenium.isTextPresent("DM Folder4 Name"));
		assertFalse(selenium.isTextPresent("DM Folder5 Name"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-first-link'])[2]",
			RuntimeVariables.replace("First"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder1 Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder6 Name"));
		selenium.clickAt("//a[@page='2']", RuntimeVariables.replace("2"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder6 Name");
		assertEquals(RuntimeVariables.replace("DM Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertFalse(selenium.isTextPresent("DM Folder1 Name"));
		assertFalse(selenium.isTextPresent("DM Folder2 Name"));
		assertFalse(selenium.isTextPresent("DM Folder3 Name"));
		assertFalse(selenium.isTextPresent("DM Folder4 Name"));
		assertFalse(selenium.isTextPresent("DM Folder5 Name"));
		selenium.clickAt("//a[@page='1']", RuntimeVariables.replace("1"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder1 Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
	}
}