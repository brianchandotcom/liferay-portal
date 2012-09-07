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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewSPVariationChristmas2CopyChristmasTest
	extends BaseTestCase {
	public void testUser_ViewSPVariationChristmas2CopyChristmas()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean christmasPresent = selenium.isElementPresent(
						"link=Christmas");

				if (!christmasPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Christmas",
					RuntimeVariables.replace("Christmas"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Christmas Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-journal-content')]"));
				assertEquals(RuntimeVariables.replace("WCD Web Content Content"),
					selenium.getText(
						"//div[@class='journal-content-article']/p"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]"));
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//td[1]/a/strong"));
				selenium.clickAt("//td[1]/a/strong",
					RuntimeVariables.replace("MB Category Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//h1[@class='header-title']/span"));

				boolean thread1Visible = selenium.isVisible("//td[1]/a");

				if (thread1Visible) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div/div[5]/div/div/div[1]",
					RuntimeVariables.replace("Threads"));

			case 3:
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//tr[4]/td[1]/a"));
				selenium.clickAt("//tr[3]/td[1]/a",
					RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Body"),
					selenium.getText("//div[@class='thread-body']"));
				selenium.clickAt("//a[@id='_19_TabsBack']",
					RuntimeVariables.replace("Back to MB Category Name"));
				selenium.waitForPageToLoad("30000");

				boolean thread2Visible = selenium.isVisible("//td[1]/a");

				if (thread2Visible) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div/div[5]/div/div/div[1]",
					RuntimeVariables.replace("Threads"));

			case 4:
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//tr[4]/td[1]/a"));
				selenium.clickAt("//tr[4]/td[1]/a",
					RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Body"),
					selenium.getText("//div[@class='thread-body']"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-user-statistics')]"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-search')]"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-document-library')]"));
				assertEquals(RuntimeVariables.replace("DL Image Title"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace("DL Image Title"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//h2[@class='document-title']",
					"DL Image Title");
				assertEquals(RuntimeVariables.replace("DL Image Title"),
					selenium.getText("//h2[@class='document-title']"));
				assertTrue(selenium.isVisible(
						"//div[@class='lfr-preview-file-image-container']/img"));
				assertEquals(RuntimeVariables.replace("DL Image Description"),
					selenium.getText("//span[@class='document-description']"));
				assertEquals(RuntimeVariables.replace("Status: Approved"),
					selenium.getText(
						"xPath=(//span[@class='workflow-status'])[2]"));
				assertEquals(RuntimeVariables.replace("Download (12.9k)"),
					selenium.getText("//span[@class='download-document']"));
				assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
					selenium.getText(
						"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-journal-content')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-user-statistics')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-search')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-document-library')]"));
				selenium.waitForVisible("link=Calendar");
				selenium.clickAt("link=Calendar",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-calendar')]"));
				selenium.waitForVisible("//nav/ul/li[3]/a/span");
				assertEquals(RuntimeVariables.replace("Wiki"),
					selenium.getText("//nav/ul/li[3]/a/span"));
				selenium.clickAt("//nav/ul/li[3]/a/span",
					RuntimeVariables.replace("Wiki"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-wiki')]"));
				selenium.waitForVisible("link=White Elephant");
				selenium.clickAt("link=White Elephant",
					RuntimeVariables.replace("White Elephant"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-navigation')]"));
				selenium.waitForVisible("link=Home");
				selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Christmas"),
					selenium.getText("//li[1]/span/span/span[1]"));
				assertEquals(RuntimeVariables.replace("Christmas 2"),
					selenium.getText("//li[2]/span/span/a"));
				selenium.clickAt("//li[2]/span/span/a",
					RuntimeVariables.replace("Christmas 2"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Christmas 2 Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-journal-content')]"));
				assertEquals(RuntimeVariables.replace("WCD Web Content Content"),
					selenium.getText(
						"//div[@class='journal-content-article']/p"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-message-boards')]"));
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//td[1]/a/strong"));
				selenium.clickAt("//td[1]/a/strong",
					RuntimeVariables.replace("MB Category Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//h1[@class='header-title']/span"));

				boolean thread3Visible = selenium.isVisible("//td[1]/a");

				if (thread3Visible) {
					label = 5;

					continue;
				}

				selenium.clickAt("//div/div[5]/div/div/div[1]",
					RuntimeVariables.replace("Threads"));

			case 5:
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//tr[4]/td[1]/a"));
				selenium.clickAt("//tr[3]/td[1]/a",
					RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Body"),
					selenium.getText("//div[@class='thread-body']"));
				selenium.clickAt("//a[@id='_19_TabsBack']",
					RuntimeVariables.replace("Back to MB Category Name"));
				selenium.waitForPageToLoad("30000");

				boolean thread4Visible = selenium.isVisible("//td[1]/a");

				if (thread4Visible) {
					label = 6;

					continue;
				}

				selenium.clickAt("//div/div[5]/div/div/div[1]",
					RuntimeVariables.replace("Threads"));

			case 6:
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message2 Subject"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//tr[4]/td[1]/a"));
				selenium.clickAt("//tr[4]/td[1]/a",
					RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Body"),
					selenium.getText("//div[@class='thread-body']"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-user-statistics')]"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-search')]"));
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-document-library')]"));
				assertEquals(RuntimeVariables.replace("DL Image Title"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace("DL Image Title"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//h2[@class='document-title']",
					"DL Image Title");
				assertEquals(RuntimeVariables.replace("DL Image Title"),
					selenium.getText("//h2[@class='document-title']"));
				assertTrue(selenium.isVisible(
						"//div[@class='lfr-preview-file-image-container']/img"));
				assertEquals(RuntimeVariables.replace("DL Image Description"),
					selenium.getText("//span[@class='document-description']"));
				assertEquals(RuntimeVariables.replace("Status: Approved"),
					selenium.getText(
						"xPath=(//span[@class='workflow-status'])[2]"));
				assertEquals(RuntimeVariables.replace("Download (12.9k)"),
					selenium.getText("//span[@class='download-document']"));
				assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
					selenium.getText(
						"//div[@id='documentLibraryAssetMetadataPanel']/div[2]/div"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-journal-content')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-message-boards')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-user-statistics')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-search')]"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@id='column-2']/div/div[contains(@class,'portlet-document-library')]"));
				selenium.waitForVisible("link=Calendar");
				selenium.clickAt("link=Calendar",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-calendar')]"));
				selenium.waitForVisible("//nav/ul/li[3]/a/span");
				assertEquals(RuntimeVariables.replace("Wiki"),
					selenium.getText("//nav/ul/li[3]/a/span"));
				selenium.clickAt("//nav/ul/li[3]/a/span",
					RuntimeVariables.replace("Wiki"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-wiki')]"));
				selenium.waitForVisible("link=White Elephant");
				selenium.clickAt("link=White Elephant",
					RuntimeVariables.replace("White Elephant"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@id='column-1']/div/div[contains(@class,'portlet-navigation')]"));

			case 100:
				label = -1;
			}
		}
	}
}