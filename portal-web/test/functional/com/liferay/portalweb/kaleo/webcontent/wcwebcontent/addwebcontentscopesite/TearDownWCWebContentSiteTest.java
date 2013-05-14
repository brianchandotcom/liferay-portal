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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWCWebContentSiteTest extends BaseTestCase {
	public void testTearDownWCWebContentSite() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//span[@title='Liferay']/ul/li/strong/a/span");
				selenium.clickAt("//span[@title='Liferay']/ul/li/strong/a/span",
					RuntimeVariables.replace("Scope Selector"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//span[@title='Site Name']/ul/li/strong/a/span",
					"Site Name");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText(
						"//span[@title='Site Name']/ul/li/strong/a/span"));
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				boolean webContentPresent = selenium.isElementPresent(
						"//div[@class='entry-thumbnail']");

				if (!webContentPresent) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_15_allRowIdsCheckbox']"));
				selenium.clickAt("//input[@id='_15_allRowIdsCheckbox']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked(
						"//input[@id='_15_allRowIdsCheckbox']"));
				selenium.waitForVisible(
					"//span[@title='Actions']/ul/li/strong/a/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"No Web Content was found."),
					selenium.getText(
						"//div[@class='entries-empty portlet-msg-info']"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//span[@title='Liferay']/ul/li/strong/a/span");
				selenium.clickAt("//span[@title='Liferay']/ul/li/strong/a/span",
					RuntimeVariables.replace("Scope Selector"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//span[@title='Site Name']/ul/li/strong/a/span",
					"Site Name");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText(
						"//span[@title='Site Name']/ul/li/strong/a/span"));
				selenium.clickAt("link=Recycle Bin",
					RuntimeVariables.replace("Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean recycleBinNotEmpty = selenium.isElementPresent(
						"//a[@class='trash-empty-link']");

				if (!recycleBinNotEmpty) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
					selenium.getText("//a[@class='trash-empty-link']"));
				selenium.clickAt("//a[@class='trash-empty-link']",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to empty the Recycle Bin?");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				assertEquals(RuntimeVariables.replace(
						"The Recycle Bin is empty."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}