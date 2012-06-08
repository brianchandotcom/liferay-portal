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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentTest extends BaseTestCase {
	public void testViewWCWebContent() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isVisible("//tr[1]/th[1]/input"));
		assertEquals(RuntimeVariables.replace("ID"),
			selenium.getText("//tr[1]/th[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText("//tr[1]/th[3]"));
		assertEquals(RuntimeVariables.replace("Status"),
			selenium.getText("//tr[1]/th[4]"));
		assertEquals(RuntimeVariables.replace("Modified Date"),
			selenium.getText("//tr[1]/th[5]/span/a"));
		assertEquals(RuntimeVariables.replace("Display Date"),
			selenium.getText("//tr[1]/th[6]/span/a"));
		assertEquals(RuntimeVariables.replace("Author"),
			selenium.getText("//tr[1]/th[7]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[1]/th[8]"));
		assertTrue(selenium.isVisible("//tr[3]/td[1]/input"));
		assertTrue(selenium.isVisible("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[3]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[5]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[7]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[8]/span/span/ul/li/strong/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//a[@id='_15_TabsBack']"));
		assertTrue(selenium.isPartialText("//span[@class='workflow-id']", "ID:"));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@class='taglib-icon-help']/img)[1]"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText(
				"xPath=(//span[@class='taglib-icon-help']/span)[1]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@id='_15_articleToolbar']/span/button[1]/span[2]"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText(
				"//div[@id='_15_articleToolbar']/span/button[2]/span[2]"));
		assertEquals(RuntimeVariables.replace("Structure:"),
			selenium.getText(
				"//div[@class='aui-column-content article-structure-content ']/label"));
		assertEquals(RuntimeVariables.replace("Default"),
			selenium.getText(
				"//div[@class='aui-column-content article-structure-content ']/fieldset/div/div/span"));
		assertTrue(selenium.isVisible("//a[@id='_15_editStructureLink']/img"));
		assertTrue(selenium.isVisible(
				"//a[@id='_15_changeStructureButton']/img"));
		assertEquals(RuntimeVariables.replace("Template:"),
			selenium.getText(
				"//div[@class='aui-column-content article-template-content ']/label"));
		assertEquals(RuntimeVariables.replace("None"),
			selenium.getText(
				"//div[@class='aui-column-content article-template-content ']/fieldset/div/div/span"));
		assertTrue(selenium.isVisible("//a[@id='_15_selectTemplateLink']/img"));
		assertEquals(RuntimeVariables.replace("Default Language"),
			selenium.getText(
				"//td[@class='article-translation-toolbar journal-metadata']/div/label"));
		assertTrue(selenium.isVisible(
				"//span[@id='_15_textLanguageId']/img[contains(@src,'en_US.png')]"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@class='taglib-icon-help']/img)[2]"));
		assertEquals(RuntimeVariables.replace(
				"The default language is the language that will appear when there are no translations available in the user's language."),
			selenium.getText(
				"xPath=(//span[@class='taglib-icon-help']/span)[2]"));
		assertEquals(RuntimeVariables.replace("Change"),
			selenium.getText("//a[@id='_15_changeLanguageId']"));
		assertEquals(RuntimeVariables.replace("Add Translation"),
			selenium.getText(
				"//span[@title='Add Translation']/ul/li/strong/a/span"));
		assertEquals("WC WebContent Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isChecked("//input[@id='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("Searchable"),
			selenium.getText("//div[2]/span/span/label"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//div[@class='article-info']/div/span"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[1]/a", "Content"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[2]/a", "Abstract"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[3]/a", "Categorization"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[4]/a", "Schedule"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[5]/a", "Display Page"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[6]/a", "Related Assets"));
		assertTrue(selenium.isPartialText(
				"//div[@class='menu-group']/ul/li[7]/a", "Custom Fields"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible("//input[@value='Save as Draft']"));
		assertTrue(selenium.isVisible("//input[@value='Publish']"));
		assertTrue(selenium.isVisible("xPath=(//input[@value='Cancel'])[2]"));
	}
}