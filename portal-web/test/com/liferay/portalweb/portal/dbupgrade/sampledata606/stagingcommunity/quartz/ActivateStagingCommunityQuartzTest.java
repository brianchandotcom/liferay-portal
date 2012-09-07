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

package com.liferay.portalweb.portal.dbupgrade.sampledata606.stagingcommunity.quartz;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingCommunityQuartzTest extends BaseTestCase {
	public void testActivateStagingCommunityQuartz() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace("Communities"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace(
						"Community Staging Community Quartz"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText("//tr[3]/td[1]",
						"Community Staging Community Quartz"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//strong/a"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Manage Pages"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Settings"),
					selenium.getText(
						"//li[@id='_134_tabs1settingsTabsId']/span/span/a"));
				selenium.clickAt("//li[@id='_134_tabs1settingsTabsId']/span/span/a",
					RuntimeVariables.replace("Settings"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Staging",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@name='_134_stagingType']",
					RuntimeVariables.replace("label=Local Live"));

				boolean blogsChecked = selenium.isChecked(
						"_134_staged-portlet_33Checkbox");

				if (blogsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_33Checkbox']",
					RuntimeVariables.replace("Blogs"));

			case 2:

				boolean bookmarksChecked = selenium.isChecked(
						"_134_staged-portlet_28Checkbox");

				if (bookmarksChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_28Checkbox']",
					RuntimeVariables.replace("Bookmarks"));

			case 3:

				boolean calendarChecked = selenium.isChecked(
						"_134_staged-portlet_8Checkbox");

				if (calendarChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_8Checkbox']",
					RuntimeVariables.replace("Calendar"));

			case 4:

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_110Checkbox");

				if (documentLibraryDisplayChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_110Checkbox']",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 5:

				boolean messageBoardsChecked = selenium.isChecked(
						"_134_staged-portlet_19Checkbox");

				if (messageBoardsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_19Checkbox']",
					RuntimeVariables.replace("Message Boards"));

			case 6:

				boolean pageCommentsChecked = selenium.isChecked(
						"_134_staged-portlet_107Checkbox");

				if (pageCommentsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_107Checkbox']",
					RuntimeVariables.replace("Page Comments"));

			case 7:

				boolean pageRatingsChecked = selenium.isChecked(
						"_134_staged-portlet_108Checkbox");

				if (pageRatingsChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_108Checkbox']",
					RuntimeVariables.replace("Page Ratings"));

			case 8:

				boolean pollsDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_59Checkbox");

				if (pollsDisplayChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_59Checkbox']",
					RuntimeVariables.replace("Polls Display"));

			case 9:

				boolean webContentDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_56Checkbox");

				if (webContentDisplayChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_56Checkbox']",
					RuntimeVariables.replace("Web Content Display"));

			case 10:

				boolean wikiChecked = selenium.isChecked(
						"_134_staged-portlet_36Checkbox");

				if (wikiChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_36Checkbox']",
					RuntimeVariables.replace("Wiki"));

			case 11:

				boolean wikiDisplayChecked = selenium.isChecked(
						"_134_staged-portlet_54Checkbox");

				if (wikiDisplayChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//input[@id='_134_staged-portlet_54Checkbox']",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 12:
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to activate local staging for Community Staging Community Quartz[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"Community Staging Community Quartz (Staging)"),
					selenium.getText("//li[4]/span"));

			case 100:
				label = -1;
			}
		}
	}
}