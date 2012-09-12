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

package com.liferay.portalweb.demo.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderMusicTest extends BaseTestCase {
	public void testViewDMFolderMusic() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.waitForVisible(
					"//li[@class='folder selected']/a/span[@class='entry-title']");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("DM Music Title"),
					selenium.getText(
						"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
				selenium.clickAt("//div[@data-title='DM Music Title']/a/span[@class='entry-title']",
					RuntimeVariables.replace("DM Music Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("DM Music Title"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace("Download"),
					selenium.getText("//button[.='Download']/span[2]"));
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//button[.='Edit']/span[2]"));
				assertEquals(RuntimeVariables.replace("Move"),
					selenium.getText("//button[.='Move']/span[2]"));
				assertEquals(RuntimeVariables.replace("Checkout"),
					selenium.getText("//button[.='Checkout']/span[2]"));
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText("//button[.='Permissions']/span[2]"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[.='Delete']/span[2]"));
				assertEquals(RuntimeVariables.replace("DM Music Title"),
					selenium.getText("//h2[@class='document-title']"));
				assertEquals(RuntimeVariables.replace("DM Music Description"),
					selenium.getText("//span[@class='document-description']"));
				assertEquals(RuntimeVariables.replace("mp3"),
					selenium.getText("//span[@class='tag' and .='mp3']"));
				assertEquals(RuntimeVariables.replace("music"),
					selenium.getText("//span[@class='tag' and .='music']"));
				assertEquals(RuntimeVariables.replace("Version 1.2"),
					selenium.getText("//h3[contains(@class,'version')]"));
				assertEquals(RuntimeVariables.replace(
						"Last Updated by Joe Bloggs"),
					selenium.getText(
						"//div[contains(@class,'lfr-asset-author')]"));
				assertEquals(RuntimeVariables.replace("Status: Approved"),
					selenium.getText("//span[@class='workflow-status']"));
				assertEquals(RuntimeVariables.replace("DM Music Description"),
					selenium.getText(
						"//blockquote[@class='lfr-asset-description']"));
				assertEquals(RuntimeVariables.replace("Download (4,429.6k)"),
					selenium.getText(
						"//span[@class='download-document']/span/a/span"));
				assertEquals(RuntimeVariables.replace("Song Information"),
					selenium.getText(
						"//div[@id='documentLibraryMetadataPanel']/div/div/span"));

				boolean songInformationCollapsed = selenium.isElementPresent(
						"//div[@id='documentLibraryMetadataPanel' and contains(@class,'lfr-collapsed')]/div/div/span[contains(.,'Song Information')]");

				if (!songInformationCollapsed) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='documentLibraryMetadataPanel']/div/div/span",
					RuntimeVariables.replace("Song Information"));

			case 2:
				assertEquals(RuntimeVariables.replace("Artist DM Music Artist"),
					selenium.getText(
						"//div[@id='documentLibraryMetadataPanel']/div[2]/div[1]"));
				assertEquals(RuntimeVariables.replace("Track Number 1"),
					selenium.getText(
						"//div[@id='documentLibraryMetadataPanel']/div[2]/div[2]"));
				assertEquals(RuntimeVariables.replace("Music"),
					selenium.getText(
						"xPath=(//div[@id='documentLibraryMetadataPanel'])[2]/div/div/span"));

				boolean musicCollapsed = selenium.isElementPresent(
						"//div[@id='documentLibraryMetadataPanel' and contains(@class,'lfr-collapsed')]/div/div/span[contains(.,'Music')]");

				if (!musicCollapsed) {
					label = 3;

					continue;
				}

				selenium.clickAt("xPath=(//div[@id='documentLibraryMetadataPanel'])[2]/div/div/span",
					RuntimeVariables.replace("Song Information"));

			case 3:
				assertEquals(RuntimeVariables.replace("Album DM Music Album"),
					selenium.getText(
						"xPath=(//div[@id='documentLibraryMetadataPanel'])[2]/div[2]/div"));
				assertEquals(RuntimeVariables.replace(
						"Automatically Extracted Metadata"),
					selenium.getText(
						"//div[@id='documentLibraryAssetMetadataPanel']/div/div/span"));

				boolean automaticallyExtractedMetadataCollapsed = selenium.isElementPresent(
						"//div[@id='documentLibraryAssetMetadataPanel' and contains(@class,'lfr-collapsed')]/div/div/span");

				if (!automaticallyExtractedMetadataCollapsed) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[@id='documentLibraryAssetMetadataPanel' and contains(@class,'lfr-collapsed')]/div/div/span",
					RuntimeVariables.replace("Song Information"));

			case 4:
				selenium.waitForVisible(
					"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div");
				assertTrue(selenium.isPartialText(
						"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div[1]",
						"Content Type audio/mpeg"));

			case 100:
				label = -1;
			}
		}
	}
}