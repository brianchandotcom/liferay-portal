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

package com.liferay.portalweb.asset.assetpublisher.portlet.configureportletassetselectionmanual;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAssetSelectionManualTest extends BaseTestCase {
	public void testConfigurePortletAssetSelectionManual()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Asset Publisher Test Page",
					RuntimeVariables.replace("Asset Publisher Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
				selenium.waitForVisible(
					"//input[@id='_86_selectionStyleManual']");

				boolean manualNotChecked = selenium.isChecked(
						"//input[@id='_86_selectionStyleManual']");

				if (manualNotChecked) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_selectionStyleManual']"));
				selenium.clickAt("//input[@id='_86_selectionStyleManual']",
					RuntimeVariables.replace("Manual"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_selectionStyleManual']"));
				assertTrue(selenium.isVisible(
						"//span[@class='legend' and contains(.,'Assets')]"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}