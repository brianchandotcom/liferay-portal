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

package com.liferay.portalweb.asset.documentsandmedia.dmimage.viewdmfolderimageviewcountap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletShowMetadataViewCountTest extends BaseTestCase {
	public void testConfigurePortletShowMetadataViewCount()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//nav[@class='site-breadcrumbs aui-helper-hidden lfr-hudcrumbs']");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//select[@id='_86_availableMetadataFields']");
		selenium.addSelection("//select[@id='_86_availableMetadataFields']",
			RuntimeVariables.replace("View Count"));
		selenium.waitForVisible(
			"//div[5]/div[2]/fieldset[2]/div/div/div/div/div/div/div/div[2]/div/span/span/button[2]");
		selenium.clickAt("//div[5]/div[2]/fieldset[2]/div/div/div/div/div/div/div/div[2]/div/span/span/button[2]",
			RuntimeVariables.replace("Left Arrow"));
		selenium.waitForPartialText("//select[@id='_86_currentMetadataFields']",
			"View Count");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_currentMetadataFields']", "View Count"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_currentMetadataFields']", "View Count"));
		selenium.selectFrame("relative=top");
	}
}