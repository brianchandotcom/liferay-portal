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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolder2SubfolderImage4ViewableBySiteMemberTest
	extends BaseTestCase {
	public void testAddFolder2SubfolderImage4ViewableBySiteMember()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Documents and Media Test Page");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("DL Folder 2 Name"),
					selenium.getText(
						"xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
				selenium.clickAt("xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]",
					RuntimeVariables.replace("DL Folder 2 Name"));
				selenium.waitForText("//li[@class='folder selected']/a/span[2]",
					"DL Folder 2 Name");
				assertEquals(RuntimeVariables.replace("DL Folder 2 Name"),
					selenium.getText("//li[@class='folder selected']/a/span[2]"));
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 SubFolder Name"),
					selenium.getText(
						"xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
				selenium.clickAt("xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]",
					RuntimeVariables.replace("DL Folder 2 SubFolder Name"));
				selenium.waitForText("//li[@class='folder selected']/a/span[2]",
					"DL Folder 2 SubFolder Name");
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 SubFolder Name"),
					selenium.getText("//li[@class='folder selected']/a/span[2]"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a");
				assertEquals(RuntimeVariables.replace("Basic Document"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[4]/a",
					RuntimeVariables.replace("Basic Document"));
				selenium.waitForPageToLoad("30000");
				selenium.uploadCommonFile("//input[@id='_20_file']",
					RuntimeVariables.replace("Document_4.jpg"));
				selenium.type("//input[@id='_20_title']",
					RuntimeVariables.replace(
						"DL Folder 2 SubFolder Image 4 Title"));
				selenium.type("//textarea[@id='_20_description']",
					RuntimeVariables.replace(
						"DL Folder 2 SubFolder Image 4 Description"));

				boolean categorizationVisible = selenium.isVisible(
						"//input[@title='Add Tags']");

				if (categorizationVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='dlFileEntryCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));

			case 2:
				selenium.waitForVisible("//input[@title='Add Tags']");
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("frog"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//button[@id='add']"));
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.select("//select[@id='_20_inputPermissionsViewRole']",
					RuntimeVariables.replace("Site Members"));
				assertEquals("Site Members",
					selenium.getSelectedLabel(
						"//select[@id='_20_inputPermissionsViewRole']"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForText("//div[@class='portlet-msg-success']",
					"Your request completed successfully.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 SubFolder Image 4 Title"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace(
						"DL Folder 2 SubFolder Image 4 Title"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//h2[@class='document-title']",
					"DL Folder 2 SubFolder Image 4 Title");
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 SubFolder Image 4 Title"),
					selenium.getText("//h2[@class='document-title']"));
				assertEquals(RuntimeVariables.replace("frog"),
					selenium.getText("//span[@class='tag']"));
				assertTrue(selenium.isVisible(
						"//div[@class='lfr-preview-file-image-container']/img"));
				assertEquals(RuntimeVariables.replace(
						"DL Folder 2 SubFolder Image 4 Description"),
					selenium.getText("//span[@class='document-description']"));
				assertEquals(RuntimeVariables.replace("Status: Approved"),
					selenium.getText("//span[@class='workflow-status']"));
				assertEquals(RuntimeVariables.replace("Download (21k)"),
					selenium.getText("//span[@class='download-document']"));
				assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
					selenium.getText(
						"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div"));

			case 100:
				label = -1;
			}
		}
	}
}