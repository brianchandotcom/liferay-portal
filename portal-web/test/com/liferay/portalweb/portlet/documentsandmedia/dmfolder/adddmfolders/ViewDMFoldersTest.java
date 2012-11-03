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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolders;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFoldersTest extends BaseTestCase {
	public void testViewDMFolders() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li/span/a"));
		assertEquals(RuntimeVariables.replace(
				"Access these files offline using Liferay Sync."),
			selenium.getText("//div[@id='_20_syncNotificationContent']/a"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='entry-thumbnail']/img)[1]"));
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='entry-thumbnail']/img)[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='entry-thumbnail']/img)[3]"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[1]",
			RuntimeVariables.replace("DM Folder1 Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DM Folder1 Name");
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[2]",
			RuntimeVariables.replace("DM Folder2 Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DM Folder2 Name");
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("DM Folder2 Name"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]"));
		selenium.clickAt("xPath=(//a[contains(@class,'entry-link')]/span[@class='entry-title'])[3]",
			RuntimeVariables.replace("DM Folder3 Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DM Folder3 Name");
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("DM Folder3 Name"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
	}
}