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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentLocalizedTest extends BaseTestCase {
	public void testViewWCWebContentLocalized() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
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

		String webcontentID = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("webcontentID", webcontentID);
		assertEquals(RuntimeVariables.replace("${webcontentID}"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertTrue(selenium.isVisible("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[7]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[8]/span/ul/li/strong/a/span"));
		selenium.clickAt("//td[8]/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//a[contains(@id,'SearchContainer_1_menu_edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//a[contains(@id,'SearchContainer_1_menu_edit')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//a[contains(@id,'SearchContainer_1_menu_permissions')]"));
		assertEquals(RuntimeVariables.replace("View (Opens New Window)"),
			selenium.getText("//a[contains(@id,'SearchContainer_1_menu_view')]"));
		assertEquals(RuntimeVariables.replace("Copy"),
			selenium.getText("//a[contains(@id,'SearchContainer_1_menu_copy')]"));
		assertEquals(RuntimeVariables.replace("Expire"),
			selenium.getText(
				"//a[contains(@id,'SearchContainer_1_menu_expire')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//a[contains(@id,'SearchContainer_1_menu_delete')]"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC WebContent ID"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace(
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role. Or disable for all portlets."),
			selenium.getText("//div[@id='cpContextPanelTemplate']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText("//span[@class='workflow-id']",
				RuntimeVariables.getValue("webcontentID")));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//button[.='Permissions']"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText("//button[.='View History']"));
		assertEquals(RuntimeVariables.replace("Default"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace("None"),
			selenium.getText("//span[@class='template-name-label']"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertEquals(RuntimeVariables.replace("Available Translations:"),
			selenium.getText(
				"//span[@id='_15_availableTranslationsLinks']/label"));
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText("//span[@id='_15_availableTranslationsLinks']/a"));
		selenium.clickAt("//span[@id='_15_availableTranslationsLinks']/a",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.waitForText("//h1[@class='header-title']/span",
			"WC WebContent Title \u4e16\u754c\u60a8\u597d");
		assertEquals(RuntimeVariables.replace(
				"WC WebContent Title \u4e16\u754c\u60a8\u597d"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals("WC WebContent Title \u4e16\u754c\u60a8\u597d",
			selenium.getValue("//input[@id='_15_title_zh_CN']"));
		assertEquals(RuntimeVariables.replace(
				"Translating Web Content to Chinese (China)"),
			selenium.getText("//div[@id='_15_availableTranslationContainer']"));
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content' and @style='display: none;']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.waitForText("//body",
			"WC WebContent Content \u4e16\u754c\u60a8\u597d");
		assertEquals(RuntimeVariables.replace(
				"WC WebContent Content \u4e16\u754c\u60a8\u597d"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//button[@id='closethick']");
		selenium.clickAt("//button[@id='closethick']",
			RuntimeVariables.replace("X"));
		assertEquals("WC WebContent Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content' and @style='display: none;']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.waitForText("//body", "WC WebContent Content");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isChecked("//input[@id='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//span[@class='article-name']"));
		assertEquals(RuntimeVariables.replace("Content (Modified)"),
			selenium.getText("//a[@id='_15_contentLink']"));
		assertEquals(RuntimeVariables.replace("Abstract (Modified)"),
			selenium.getText("//a[@id='_15_abstractLink']"));
		assertEquals(RuntimeVariables.replace("Categorization (Modified)"),
			selenium.getText("//a[@id='_15_categorizationLink']"));
		assertEquals(RuntimeVariables.replace("Schedule (Modified)"),
			selenium.getText("//a[@id='_15_scheduleLink']"));
		assertEquals(RuntimeVariables.replace("Display Page (Modified)"),
			selenium.getText("//a[@id='_15_displayPageLink']"));
		assertEquals(RuntimeVariables.replace("Related Assets (Modified)"),
			selenium.getText("//a[@id='_15_relatedAssetsLink']"));
		assertEquals(RuntimeVariables.replace("Custom Fields (Modified)"),
			selenium.getText("//a[@id='_15_customFieldsLink']"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals("Save as Draft",
			selenium.getValue("//input[@value='Save as Draft']"));
		assertEquals("Publish", selenium.getValue("//input[@value='Publish']"));
		assertEquals("Cancel", selenium.getValue("//input[@value='Cancel']"));
	}
}