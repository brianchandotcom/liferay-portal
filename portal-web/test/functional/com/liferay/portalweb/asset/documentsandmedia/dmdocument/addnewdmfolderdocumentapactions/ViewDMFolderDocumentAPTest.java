/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.asset.documentsandmedia.dmdocument.addnewdmfolderdocumentapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderDocumentAPTest extends BaseTestCase {
	public void testViewDMFolderDocumentAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("xPath=(//h3[@class='asset-title']/a)[1]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-meta-actions asset-actions']/span/a/span",
				"Edit"));
		assertTrue(selenium.isPartialText(
				"//div[@class='asset-resource-info']/span/a/span", "Download"));
		assertTrue(selenium.isPartialText(
				"//div[@class='asset-resource-info']/span/a/span", "(0k)"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='asset-more']/a)[1]", "Read More"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("xPath=(//h3[@class='asset-title']/a)[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='asset-more']/a)[2]", "Read More"));
		selenium.clickAt("xPath=(//div[@class='asset-more']/a)[1]",
			RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-meta-actions asset-actions']/span/a/span",
				"Edit"));
		assertEquals(RuntimeVariables.replace(
				"Automatically Extracted Metadata"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
		assertEquals(RuntimeVariables.replace("Content Encoding ISO-8859-1"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Content Encoding')]"));
		assertEquals(RuntimeVariables.replace(
				"Content Type text/plain; charset=ISO-8859-1"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Content Type')]"));
		assertTrue(selenium.isVisible(
				"//li[@class='taglib-social-bookmark-twitter']"));
		assertTrue(selenium.isVisible(
				"//li[@class='taglib-social-bookmark-facebook']"));
		assertTrue(selenium.isVisible(
				"//li[@class='taglib-social-bookmark-plusone']"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//span[@class='header-back-to']/a",
			RuntimeVariables.replace("\u00ab Back"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("xPath=(//div[@class='asset-more']/a)[2]",
			RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.waitForVisible("//button[@title='Icon View']");
		selenium.clickAt("//button[@title='Icon View']",
			RuntimeVariables.replace("Icon View"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//button[@title='Icon View' and contains(@class,'aui-state-active')]");
		assertTrue(selenium.isVisible(
				"//button[@title='Icon View' and contains(@class,'aui-state-active')]"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//a[contains(@class,'entry-link')]/span"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[@class='app-view-navigation-entry folder selected']/a/span[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//li[@class='app-view-navigation-entry folder selected']/a/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//a[contains(@class,'entry-link')]/span"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[@class='version ']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (0k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		assertTrue(selenium.isPartialText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div/div/div[contains(.,'Content Encoding')]",
				"ISO-8859-1"));
		assertTrue(selenium.isPartialText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div/div/div[contains(.,'Content Type')]",
				"text/plain"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//tr[2]/td[2]"));
		assertTrue(selenium.isVisible("//tr[2]/td[3]"));
		assertEquals(RuntimeVariables.replace("0k"),
			selenium.getText("//tr[2]/td[4]"));
	}
}