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

package com.liferay.portalweb.asset.assetpublisher.portlet.configureportletassetselectiondynamic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAssetSelectionDynamicTest extends BaseTestCase {
	public void testConfigurePortletAssetSelectionDynamic()
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
					"//input[@id='_86_selectionStyleDynamic']");

				boolean dynamicNotChecked = selenium.isChecked(
						"//input[@id='_86_selectionStyleDynamic']");

				if (dynamicNotChecked) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_86_selectionStyleDynamic']"));
				selenium.clickAt("//input[@id='_86_selectionStyleDynamic']",
					RuntimeVariables.replace("Dynamic"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_selectionStyleDynamic']"));
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[1]/span"));
				assertEquals(RuntimeVariables.replace("Filter"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[2]/span"));
				assertEquals(RuntimeVariables.replace("Custom User Attributes"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[3]/span"));
				assertEquals(RuntimeVariables.replace("Ordering and Grouping"),
					selenium.getText(
						"xPath=(//div[@class='lfr-panel-title'])[4]/span"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}