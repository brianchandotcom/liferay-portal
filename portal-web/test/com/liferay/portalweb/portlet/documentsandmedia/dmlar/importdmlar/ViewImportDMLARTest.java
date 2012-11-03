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

package com.liferay.portalweb.portlet.documentsandmedia.dmlar.importdmlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewImportDMLARTest extends BaseTestCase {
	public void testViewImportDMLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			"DL Folder1 Name");
		assertEquals(RuntimeVariables.replace("DL Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Name Edit"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			RuntimeVariables.replace("DL Folder1 Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a[2]",
			"DL Folder1 Name");
		assertEquals(RuntimeVariables.replace("DL Folder1 Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder1 Subfolder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder1 Subfolder Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DL Folder1 Subfolder Name");
		assertEquals(RuntimeVariables.replace("DL Folder1 Subfolder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertEquals(RuntimeVariables.replace(
				"DL Folder1 Subfolder Document Title Edit"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DL Folder1 Subfolder Document Title Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//h2[@class='document-title']",
			"DL Folder1 Subfolder Document Title Edit");
		assertEquals(RuntimeVariables.replace(
				"DL Folder1 Subfolder Document Title Edit"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace(
				"DL Folder1 Subfolder Document Description Edit"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Download (1k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DL Folder2 Name Edit"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]",
			RuntimeVariables.replace("DL Folder2 Name Edit"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a[2]",
			"DL Folder2 Name Edit");
		assertEquals(RuntimeVariables.replace("DL Folder2 Name Edit"),
			selenium.getText("//li[contains(@class,'folder selected')]/a[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Subfolder Name Edit"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
	}
}