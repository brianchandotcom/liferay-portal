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

package com.liferay.portalweb.kaleo.assetpublisher.dmdocument.guestviewpendingdmfolderdocumentnoaptest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewApprovedDMFolderDocumentTest extends BaseTestCase {
	public void testViewApprovedDMFolderDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//div[@data-title='DM Folder Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Folder Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Sort By"),
			selenium.getText("//span[@title='Sort By']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		assertTrue(selenium.isVisible("//input[contains(@id,'keywords')]"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace(
				"Access these files offline using Liferay Sync."),
			selenium.getText("//div[contains(@id,'syncNotificationContent')]/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText(
				"//div[@data-title='DM Folder Document Title']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Folder Document Title']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText("//span[@class='toolbar-content']/button[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//span[@class='toolbar-content']/button[2]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//span[@class='toolbar-content']/button[3]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText("//span[@class='toolbar-content']/button[4]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//span[@class='toolbar-content']/button[5]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText("//span[@class='toolbar-content']/button[6]"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertTrue(selenium.isPartialText(
				"//span[@class='user-date']/span/span", "Uploaded by Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("xpath=(//div[@class='lfr-panel-title']/span)[1]"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText("//fieldset[contains(@class,'add-comment')]"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[@class='version ']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (0k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		assertEquals(RuntimeVariables.replace("URL"),
			selenium.getText("//a[@class='show-url-file']"));
		assertEquals(RuntimeVariables.replace("WebDAV URL"),
			selenium.getText("//a[@class='show-webdav-url-file']"));
		assertEquals(RuntimeVariables.replace(
				"Automatically Extracted Metadata"),
			selenium.getText(
				"//div[@id='documentLibraryAssetMetadataPanel']/div/div"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-panel-content']/div/div[contains(.,'Content Type')]",
				"Content Type"));
		assertEquals(RuntimeVariables.replace("Version History"),
			selenium.getText("xpath=(//div[@class='lfr-panel-title']/span)[3]"));
		assertTrue(selenium.isVisible("//div[@id='_20_ocerSearchContainer']"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText(
				"//tr[@class='results-row last portlet-section-alternate-hover']/td[contains(.,'1.0')]"));
		assertTrue(selenium.isVisible(
				"//tr[@class='results-row last portlet-section-alternate-hover']/td[2]"));
		assertEquals(RuntimeVariables.replace("0k"),
			selenium.getText(
				"//tr[@class='results-row last portlet-section-alternate-hover']/td[contains(.,'0k')]"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText(
				"//tr[@class='results-row last portlet-section-alternate-hover']/td[contains(.,'Approved')]"));
	}
}