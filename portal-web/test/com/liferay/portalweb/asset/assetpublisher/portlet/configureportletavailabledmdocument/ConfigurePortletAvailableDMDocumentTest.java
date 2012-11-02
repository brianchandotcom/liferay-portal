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

package com.liferay.portalweb.asset.assetpublisher.portlet.configureportletavailabledmdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAvailableDMDocumentTest extends BaseTestCase {
	public void testConfigurePortletAvailableDMDocument()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
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
		selenium.waitForVisible("//select[@id='_86_anyAssetType']");
		selenium.select("//select[@id='_86_anyAssetType']",
			RuntimeVariables.replace("Select More Than One..."));
		assertEquals("Select More Than One...",
			selenium.getSelectedLabel("//select[@id='_86_anyAssetType']"));
		selenium.waitForVisible("//select[@id='_86_currentClassNameIds']");
		selenium.addSelection("//select[@id='_86_currentClassNameIds']",
			RuntimeVariables.replace("Documents and Media Document"));
		selenium.waitForVisible(
			"xPath=(//button[@title='Move selected items from Selected to Available.'])[2]");
		selenium.clickAt("xPath=(//button[@title='Move selected items from Selected to Available.'])[2]",
			RuntimeVariables.replace("Right Arrow"));
		selenium.waitForText("//select[@id='_86_availableClassNameIds']",
			"Documents and Media Document");
		assertEquals(RuntimeVariables.replace("Documents and Media Document"),
			selenium.getText("//select[@id='_86_availableClassNameIds']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Documents and Media Document"),
			selenium.getText("//select[@id='_86_availableClassNameIds']"));
		selenium.selectFrame("relative=top");
	}
}