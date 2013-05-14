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

package com.liferay.portalweb.asset.webcontent.wcwebcontent.addnewwcwebcontentapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewWCWebContentAPActionsTest extends BaseTestCase {
	public void testAddNewWCWebContentAPActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Add New']/ul/li/strong/a",
			RuntimeVariables.replace("Add New"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content')]");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Web Content')]",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.waitForVisible("//input[@id='_15_title_en_US']");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("WC Web Content Title"));
		selenium.waitForText("//span[@class='cke_toolbar']/span[2]/a/span",
			"Normal");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace("WC Web Content Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_off']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body", "WC Web Content Content");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[contains(@id,'editAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'editAsset')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//h3[@class='asset-title']/a");
		assertEquals(RuntimeVariables.replace("WC Web Content Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace("WC Web Content Content"),
			selenium.getText("//div[@class='asset-summary']"));
	}
}