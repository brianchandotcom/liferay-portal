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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletShowFolderMenuTest extends BaseTestCase {
	public void testConfigurePortletShowFolderMenu() throws Exception {
		selenium.open("/web/web-content-image-association-community/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Image Gallery Page",
			RuntimeVariables.replace("Image Gallery Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//script[contains(@src,'/liferay/navigation_interaction.js')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
		selenium.click(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[@id='_86_showFolderMenuCheckbox']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isChecked(
				"//input[@id='_86_showFolderMenuCheckbox']"));
		selenium.clickAt("//input[@id='_86_showFolderMenuCheckbox']",
			RuntimeVariables.replace("Show Folder Menu"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_showFolderMenuCheckbox']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_showFolderMenuCheckbox']"));
	}
}