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

package com.liferay.portalweb.asset.assetpublisher.portlet.configureportletcurrentwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletCurrentWebContentTest extends BaseTestCase {
	public void testConfigurePortletCurrentWebContent()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@title='Options']/ul/li/strong/a");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForVisible("//select[@id='_86_anyAssetType']");
		selenium.select("//select[@id='_86_anyAssetType']",
			RuntimeVariables.replace("Select More Than One..."));
		assertEquals("Select More Than One...",
			selenium.getSelectedLabel("//select[@id='_86_anyAssetType']"));
		selenium.waitForVisible("//select[@id='_86_availableClassNameIds']");
		selenium.addSelection("//select[@id='_86_availableClassNameIds']",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForVisible(
			"xPath=(//button[@title='Move selected items from Available to Selected.'])[2]");
		selenium.clickAt("xPath=(//button[@title='Move selected items from Available to Selected.'])[2]",
			RuntimeVariables.replace("Left Arrow"));
		selenium.waitForPartialText("//select[@id='_86_currentClassNameIds']",
			"Web Content");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_currentClassNameIds']", "Web Content"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_currentClassNameIds']", "Web Content"));
		selenium.selectFrame("relative=top");
	}
}