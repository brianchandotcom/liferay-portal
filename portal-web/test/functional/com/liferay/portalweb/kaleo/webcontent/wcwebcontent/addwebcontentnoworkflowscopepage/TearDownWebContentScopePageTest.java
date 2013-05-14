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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentnoworkflowscopepage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWebContentScopePageTest extends BaseTestCase {
	public void testTearDownWebContentScopePage() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Scope: Default"),
					selenium.getText(
						"//span[@title='Scope: Default']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Scope: Default']/ul/li/strong/a",
					RuntimeVariables.replace("Scope: Default"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content Display Page')]");
				assertEquals(RuntimeVariables.replace(
						"Web Content Display Page"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content Display Page')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content Display Page')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//span[@title='Scope: Web Content Display Page']/ul/li/strong/a",
						"Web Content Display Page"));
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
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

			case 100:
				label = -1;
			}
		}
	}
}