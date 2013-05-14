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

package com.liferay.portalweb.asset.webcontent.wcwebcontent.addwcwebcontent2displaypageap2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContent2DisplayPageAP2Test extends BaseTestCase {
	public void testAddWCWebContent2DisplayPageAP2() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]");
		assertEquals(RuntimeVariables.replace("Basic Web Content"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("WC WebContent2 Title"));
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
			RuntimeVariables.replace("WC WebContent2 Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_off']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body", "WC WebContent2 Content");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//a[@id='_15_displayPageLink']");
		assertTrue(selenium.isPartialText("//a[@id='_15_displayPageLink']",
				"Display Page"));
		selenium.clickAt("//a[@id='_15_displayPageLink']",
			RuntimeVariables.replace("Display Page"));
		selenium.waitForVisible("//button[@id='_15_chooseDisplayPage']");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//button[@id='_15_chooseDisplayPage']"));
		selenium.clickAt("//button[@id='_15_chooseDisplayPage']",
			RuntimeVariables.replace("Select"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/history_manager.js')]");
		selenium.waitForVisible(
			"//a[contains(@id,'PublicPages_layout_asset-publisher-test-page2')]");
		assertEquals(RuntimeVariables.replace("Asset Publisher Test Page2"),
			selenium.getText(
				"//a[contains(@id,'PublicPages_layout_asset-publisher-test-page2')]"));
		selenium.clickAt("//a[contains(@id,'PublicPages_layout_asset-publisher-test-page2')]",
			RuntimeVariables.replace("Asset Publisher Test Page2"));
		selenium.waitForText("//div[@id='_15_selectedPageMessage']",
			"Public Pages > Asset Publisher Test Page2");
		assertEquals(RuntimeVariables.replace(
				"Public Pages > Asset Publisher Test Page2"),
			selenium.getText("//div[@id='_15_selectedPageMessage']"));
		selenium.waitForElementNotPresent(
			"//div[3]/span/span/button[contains(@class,'aui-buttonitem-disabled')]");
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText("//div[3]/span/span/button"));
		selenium.clickAt("//div[3]/span/span/button",
			RuntimeVariables.replace("OK"));
		selenium.waitForVisible("//span[@id='_15_displayPageNameInput']");
		assertEquals(RuntimeVariables.replace(
				"Public Pages > Asset Publisher Test Page2"),
			selenium.getText("//span[@id='_15_displayPageNameInput']"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent2 Title"),
			selenium.getText(
				"//a[@class='entry-link']/span[contains(.,'WC WebContent2 Title')]"));
		selenium.clickAt("//a[@class='entry-link']/span[contains(.,'WC WebContent2 Title')]",
			RuntimeVariables.replace("WC WebContent2 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent2 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals("WC WebContent2 Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//p", "WC WebContent2 Content");
		assertEquals(RuntimeVariables.replace("WC WebContent2 Content"),
			selenium.getText("//p"));
		selenium.selectFrame("relative=top");
	}
}