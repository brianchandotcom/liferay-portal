/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.sites.sitepageportlet.addsitepublicpageportletstaging;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingSitesTest extends BaseTestCase {
	public void testActivateStagingSites() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
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
					RuntimeVariables.replace("Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[7]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Edit Settings"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_165_stagingLink']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isPartialText(
						"//a[@id='_165_stagingLink']", "Staging"));
				selenium.clickAt("//a[@id='_165_stagingLink']",
					RuntimeVariables.replace("Staging"));
				selenium.clickAt("//input[@id='_165_local']",
					RuntimeVariables.replace("Local Live"));
				assertFalse(selenium.isChecked(
						"//input[@id='_165_branchingPublicCheckbox']"));
				selenium.clickAt("//input[@id='_165_branchingPublicCheckbox']",
					RuntimeVariables.replace("Enabled on Public Pages"));
				assertTrue(selenium.isChecked(
						"//input[@id='_165_branchingPublicCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_165_branchingPrivateCheckbox']"));
				selenium.clickAt("//input[@id='_165_branchingPrivateCheckbox']",
					RuntimeVariables.replace("Enabled on Private Pages"));
				assertTrue(selenium.isChecked(
						"//input[@id='_165_branchingPrivateCheckbox']"));

				boolean blogsChecked = selenium.isChecked(
						"_165_staged-portlet_161Checkbox");

				if (blogsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_161Checkbox']",
					RuntimeVariables.replace("Blogs"));

			case 2:

				boolean dynamicDataMappingChecked = selenium.isChecked(
						"_165_staged-portlet_166Checkbox");

				if (dynamicDataMappingChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_166Checkbox']",
					RuntimeVariables.replace("Dynamic Data Mapping"));

			case 3:

				boolean messageBoardsChecked = selenium.isChecked(
						"_165_staged-portlet_162Checkbox");

				if (messageBoardsChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_162Checkbox']",
					RuntimeVariables.replace("Message Boards"));

			case 4:

				boolean pageCommentsChecked = selenium.isChecked(
						"_165_staged-portlet_107Checkbox");

				if (pageCommentsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_107Checkbox']",
					RuntimeVariables.replace("Page Comments"));

			case 5:

				boolean pageRatingsChecked = selenium.isChecked(
						"_165_staged-portlet_108Checkbox");

				if (pageRatingsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_108Checkbox']",
					RuntimeVariables.replace("Page Ratings"));

			case 6:

				boolean pollsChecked = selenium.isChecked(
						"_165_staged-portlet_25Checkbox");

				if (pollsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_25Checkbox']",
					RuntimeVariables.replace("Polls"));

			case 7:

				boolean wikiChecked = selenium.isChecked(
						"_165_staged-portlet_36Checkbox");

				if (wikiChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_36Checkbox']",
					RuntimeVariables.replace("Wiki"));

			case 8:

				boolean wikiDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_54Checkbox");

				if (wikiDisplayChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_54Checkbox']",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 9:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}