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

package com.liferay.portalweb.portlet.documentsandmedia.portlet.configuredmportletdocumentsperpage5;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMPortletDocumentsPerPage20Test extends BaseTestCase {
	public void testViewDMPortletDocumentsPerPage20() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder Document1 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
		Thread.sleep(5000);
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-next-link'])[2]",
			RuntimeVariables.replace("Next"));
		selenium.waitForText("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			"DM Folder Document6 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document3 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document4 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document5 Title"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-prev-link'])[2]",
			RuntimeVariables.replace("Previous"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder Document1 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-last-link'])[2]",
			RuntimeVariables.replace("Last"));
		selenium.waitForText("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			"DM Folder Document6 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document3 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document4 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document5 Title"));
		selenium.clickAt("xPath=(//a[@class='aui-paginator-link aui-paginator-first-link'])[2]",
			RuntimeVariables.replace("First"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder Document1 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
		selenium.clickAt("//a[@page='2']", RuntimeVariables.replace("2"));
		selenium.waitForText("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			"DM Folder Document6 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document6 Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		assertFalse(selenium.isTextPresent("DM Folder Document1 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document2 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document3 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document4 Title"));
		assertFalse(selenium.isTextPresent("DM Folder Document5 Title"));
		selenium.clickAt("xPath=(//a[@page='1'])[2]",
			RuntimeVariables.replace("1"));
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DM Folder Document1 Title");
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document4 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document5 Title"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[5]"));
		assertFalse(selenium.isTextPresent("DM Folder Document6 Title"));
	}
}