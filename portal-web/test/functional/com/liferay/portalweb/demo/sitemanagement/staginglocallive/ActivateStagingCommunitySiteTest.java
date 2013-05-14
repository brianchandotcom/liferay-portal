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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingCommunitySiteTest extends BaseTestCase {
	public void testActivateStagingCommunitySite() throws Exception {
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
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
				assertEquals(RuntimeVariables.replace("Edit Settings"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
					RuntimeVariables.replace("Edit Settings"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_165_stagingLink']", "Staging"));
				selenium.clickAt("//a[@id='_165_stagingLink']",
					RuntimeVariables.replace("Staging"));
				selenium.waitForVisible("//input[@id='_165_local']");
				selenium.clickAt("//input[@id='_165_local']",
					RuntimeVariables.replace("Local Live"));

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_20Checkbox");

				if (documentLibraryDisplayChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_20Checkbox']",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 2:

				boolean messageBoardsChecked = selenium.isChecked(
						"_165_staged-portlet_19Checkbox");

				if (!messageBoardsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_19Checkbox']",
					RuntimeVariables.replace("Message Boards"));

			case 3:

				boolean webContentChecked = selenium.isChecked(
						"_165_staged-portlet_15Checkbox");

				if (webContentChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_15Checkbox']",
					RuntimeVariables.replace("Web Content"));

			case 4:
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