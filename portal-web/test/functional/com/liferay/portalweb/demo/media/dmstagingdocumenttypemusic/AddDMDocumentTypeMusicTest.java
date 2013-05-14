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

package com.liferay.portalweb.demo.media.dmstagingdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentTypeMusicTest extends BaseTestCase {
	public void testAddDMDocumentTypeMusic() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]");
		assertEquals(RuntimeVariables.replace("Document Types"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]",
			RuntimeVariables.replace("Document Types"));
		selenium.waitForVisible("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.selectFrame("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("link=Add");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("Music"));
		selenium.dragAndDropToObject("//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Text']",
			"//div[@class='diagram-builder-drop-container']");
		selenium.waitForVisible(
			"//div[contains(@id,'fields_field_aui')]/div/label");
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		selenium.doubleClickAt("//div[contains(@id,'fields_field_aui')]/div/label",
			RuntimeVariables.replace("Text"));
		selenium.waitForVisible("//tr[2]/td/div");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText("//tr[2]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[2]/td[2]/div"));
		selenium.doubleClickAt("//tr[2]/td[1]/div",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//input[@name='value']");
		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("Album"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[2]/td[2]/div", "Album");
		assertEquals(RuntimeVariables.replace("Album"),
			selenium.getText("//tr[2]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Album"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[5]/td[1]/div"));
		assertTrue(selenium.isPartialText("//tr[5]/td[2]/div", "text"));
		selenium.doubleClickAt("//tr[5]/td[1]/div",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible("//input[@name='value']");
		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("album"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[5]/td[2]/div", "album");
		assertEquals(RuntimeVariables.replace("album"),
			selenium.getText("//tr[5]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Select Metadata Set"),
			selenium.getText(
				"//span[contains(@class,'select-metadata')]/a/span"));
		selenium.clickAt("//span[contains(@class,'select-metadata')]/a/span",
			RuntimeVariables.replace("Select Metadata Set"));
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);
		selenium.waitForVisible(
			"//div[contains(@class,'aui-dialog-iframe-bd')]/iframe");
		selenium.selectFrame(
			"//div[contains(@class,'aui-dialog-iframe-bd')]/iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//a[contains(.,'Song Information')]");
		assertEquals(RuntimeVariables.replace("Song Information"),
			selenium.getText("//a[contains(.,'Song Information')]"));
		selenium.clickAt("//a[contains(.,'Song Information')]",
			RuntimeVariables.replace("Song Information"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"xPath=(//div[contains(@class,'aui-dialog-iframe-bd')])[2]/iframe");
		selenium.selectFrame(
			"xPath=(//div[contains(@class,'aui-dialog-iframe-bd')])[2]/iframe");
		assertEquals(RuntimeVariables.replace("Song Information"),
			selenium.getText("//table/tr/td"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isTextPresent("Music"));
		selenium.selectFrame("relative=top");
	}
}