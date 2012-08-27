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

package com.liferay.portalweb.stagingsite.sites.sitepagesvariation.addsitepagesvariation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingTest extends BaseTestCase {
	public void testActivateStaging() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.click("//span[@title='Actions']/ul/li/strong/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit Settings')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Edit Settings"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit Settings')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit Settings')]/a",
					RuntimeVariables.replace("Edit Settings"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_165_stagingLink']", "Staging"));
				selenium.clickAt("//a[@id='_165_stagingLink']",
					RuntimeVariables.replace("Staging"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='_165_local']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@id='_165_local']",
					RuntimeVariables.replace("Local Live"));

				boolean enabledOnPublicPagesChecked = selenium.isChecked(
						"_165_branchingPublicCheckbox");

				if (enabledOnPublicPagesChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_165_branchingPublicCheckbox']",
					RuntimeVariables.replace("Enabled on Public Pages"));

			case 2:

				boolean enabledOnPrivatePagesChecked = selenium.isChecked(
						"_165_branchingPrivateCheckbox");

				if (enabledOnPrivatePagesChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_165_branchingPrivateCheckbox']",
					RuntimeVariables.replace("Enabled on Private Pages"));

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//span/span[contains(.,'Blogs')]/span/input[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean blogsChecked = selenium.isChecked(
						"//span/span[contains(.,'Blogs')]/span/input[2]");

				if (blogsChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Blogs')]/span/input[2]",
					RuntimeVariables.replace("Blogs"));

			case 4:

				boolean bookmarksChecked = selenium.isChecked(
						"//span/span[contains(.,'Bookmarks')]/span/input[2]");

				if (bookmarksChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Bookmarks')]/span/input[2]",
					RuntimeVariables.replace("Bookmarks"));

			case 5:

				boolean calendarChecked = selenium.isChecked(
						"//span/span[contains(.,'Calendar')]/span/input[2]");

				if (calendarChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Calendar')]/span/input[2]",
					RuntimeVariables.replace("Calendar"));

			case 6:

				boolean documentLibraryChecked = selenium.isChecked(
						"//span/span[contains(.,'Documents and Media')]/span/input[2]");

				if (documentLibraryChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Documents and Media')]/span/input[2]",
					RuntimeVariables.replace("DocumentLibrary"));

			case 7:

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Documents and Media Display')]/span/input[2]");

				if (documentLibraryDisplayChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Documents and Media Display')]/span/input[2]",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 8:

				boolean dynamicDataMappingChecked = selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]");

				if (dynamicDataMappingChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]",
					RuntimeVariables.replace("Dynamic Data Mapping"));

			case 9:

				boolean messageBoardsChecked = selenium.isChecked(
						"//span/span[contains(.,'Message Boards')]/span/input[2]");

				if (messageBoardsChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Message Boards')]/span/input[2]",
					RuntimeVariables.replace("Message Boards"));

			case 10:

				boolean pageCommentsChecked = selenium.isChecked(
						"//span/span[contains(.,'Page Comments')]/span/input[2]");

				if (pageCommentsChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Page Comments')]/span/input[2]",
					RuntimeVariables.replace("Page Comments"));

			case 11:

				boolean pageRatingsChecked = selenium.isChecked(
						"//span/span[contains(.,'Page Ratings')]/span/input[2]");

				if (pageRatingsChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Page Ratings')]/span/input[2]",
					RuntimeVariables.replace("Page Ratings"));

			case 12:

				boolean pollsChecked = selenium.isChecked(
						"//span/span[contains(.,'Polls')]/span/input[2]");

				if (pollsChecked) {
					label = 13;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Polls')]/span/input[2]",
					RuntimeVariables.replace("Polls"));

			case 13:

				boolean pollsDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Polls Display')]/span/input[2]");

				if (pollsDisplayChecked) {
					label = 14;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Polls Display')]/span/input[2]",
					RuntimeVariables.replace("Polls Display"));

			case 14:

				boolean webContentChecked = selenium.isChecked(
						"//span/span[contains(.,'Web Content')]/span/input[2]");

				if (webContentChecked) {
					label = 15;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Web Content')]/span/input[2]",
					RuntimeVariables.replace("Web Content"));

			case 15:

				boolean webContentDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Web Content Display')]/span/input[2]");

				if (webContentDisplayChecked) {
					label = 16;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Web Content Display')]/span/input[2]",
					RuntimeVariables.replace("Web Content Display"));

			case 16:

				boolean wikiChecked = selenium.isChecked(
						"//span/span[contains(.,'Wiki')]/span/input[2]");

				if (wikiChecked) {
					label = 17;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Wiki')]/span/input[2]",
					RuntimeVariables.replace("Wiki"));

			case 17:

				boolean wikiDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Wiki Display')]/span/input[2]");

				if (wikiDisplayChecked) {
					label = 18;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Wiki Display')]/span/input[2]",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 18:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Site Name (Staging)"),
					selenium.getText("//h1[@class='header-title']/span"));

			case 100:
				label = -1;
			}
		}
	}
}