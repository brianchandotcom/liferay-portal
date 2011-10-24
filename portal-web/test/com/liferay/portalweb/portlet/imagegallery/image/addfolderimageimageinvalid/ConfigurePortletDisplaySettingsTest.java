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

package com.liferay.portalweb.portlet.imagegallery.image.addfolderimageimageinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDisplaySettingsTest extends BaseTestCase {
	public void testConfigurePortletDisplaySettings() throws Exception {
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
						if (selenium.isVisible("link=Image Gallery Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Image Gallery Test Page",
					RuntimeVariables.replace("Image Gallery Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//strong/a"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Options"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
					RuntimeVariables.replace("Configuration"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_86_showActionsCheckbox']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean showActionsChecked = selenium.isChecked(
						"_86_showActionsCheckbox");

				if (showActionsChecked) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_showActionsCheckbox']"));
				selenium.click("//input[@id='_86_showActionsCheckbox']");
				assertTrue(selenium.isChecked(
						"//input[@id='_86_showActionsCheckbox']"));

			case 2:

				boolean showFolderMenuChecked = selenium.isChecked(
						"_86_showFolderMenuCheckbox");

				if (showFolderMenuChecked) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_showFolderMenuCheckbox']"));
				selenium.click("//input[@id='_86_showFolderMenuCheckbox']");
				assertTrue(selenium.isChecked(
						"//input[@id='_86_showFolderMenuCheckbox']"));

			case 3:

				boolean showNavigationLinksChecked = selenium.isChecked(
						"_86_showTabsCheckbox");

				if (showNavigationLinksChecked) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_showTabsCheckbox']"));
				selenium.click("//input[@id='_86_showTabsCheckbox']");
				assertTrue(selenium.isChecked(
						"//input[@id='_86_showTabsCheckbox']"));

			case 4:

				boolean showSearchChecked = selenium.isChecked(
						"_86_showFoldersSearchCheckbox");

				if (showSearchChecked) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_showFoldersSearchCheckbox']"));
				selenium.click("//input[@id='_86_showFoldersSearchCheckbox']");
				assertTrue(selenium.isChecked(
						"//input[@id='_86_showFoldersSearchCheckbox']"));

			case 5:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Image Gallery Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Image Gallery Test Page",
					RuntimeVariables.replace("Image Gallery Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible("//input[@id='_31_keywords1']"));
				assertTrue(selenium.isVisible(
						"//div[@class='top-links-navigation']"));
				assertTrue(selenium.isVisible(
						"//div[@class='lfr-asset-summary']"));
				assertTrue(selenium.isVisible(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[2]/a"));

			case 100:
				label = -1;
			}
		}
	}
}