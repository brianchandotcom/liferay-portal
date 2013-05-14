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
public class AddDMMetadataSetSongInformationTest extends BaseTestCase {
	public void testAddDMMetadataSetSongInformation() throws Exception {
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
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Metadata Sets')]");
		assertEquals(RuntimeVariables.replace("Metadata Sets"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Metadata Sets')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Metadata Sets')]",
			RuntimeVariables.replace("Metadata Sets"));
		selenium.waitForVisible(
			"//iframe[contains(@src,'DLFileEntryMetadata')]");
		selenium.selectFrame("//iframe[contains(@src,'DLFileEntryMetadata')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("link=Add");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_166_name_en_US']",
			RuntimeVariables.replace("Song Information"));
		selenium.dragAndDropToObject("//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Text']",
			"//div[@class='diagram-builder-drop-container']");
		selenium.waitForVisible(
			"//div[contains(@id,'fields_field_aui')]/div/label");
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		selenium.doubleClickAt("//div[contains(@id,'fields_field_aui')]/div/label",
			RuntimeVariables.replace("Text"));
		selenium.waitForVisible("//tr[2]/td[1]/div");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText("//tr[2]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[2]/td[2]/div"));
		selenium.doubleClickAt("//tr[2]/td[1]/div",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//input[@name='value']");
		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("Artist"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[2]/td[2]/div", "Artist");
		assertEquals(RuntimeVariables.replace("Artist"),
			selenium.getText("//tr[2]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Artist"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		assertEquals(RuntimeVariables.replace("Required"),
			selenium.getText("//tr[4]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//tr[4]/td[2]/div"));
		selenium.doubleClickAt("//tr[4]/td[1]/div",
			RuntimeVariables.replace("Required"));
		selenium.waitForVisible("//input[@type='radio' and @value='true']");
		selenium.clickAt("//input[@type='radio' and @value='true']",
			RuntimeVariables.replace("Yes"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[4]/td[2]/div", "Yes");
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[4]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("*"),
			selenium.getText("//span[@class='form-builder-required']"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[5]/td[1]/div"));
		assertTrue(selenium.isPartialText("//tr[5]/td[2]/div", "text"));
		selenium.doubleClickAt("//tr[5]/td[1]/div",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible("//input[@name='value']");
		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("artist"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[5]/td[2]/div", "artist");
		assertEquals(RuntimeVariables.replace("artist"),
			selenium.getText("//tr[5]/td[2]/div"));
		selenium.clickAt("link=Fields", RuntimeVariables.replace("Fields"));
		selenium.waitForVisible(
			"//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Number']");
		selenium.dragAndDropToObject("//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Number']",
			"//div[@class='diagram-builder-drop-container']");
		selenium.waitForVisible(
			"xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]");
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]"));
		selenium.doubleClickAt("xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]",
			RuntimeVariables.replace("Number"));
		selenium.waitForVisible("//tr[2]/td[1]/div");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText("//tr[2]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//tr[2]/td[2]/div"));
		selenium.doubleClickAt("//tr[2]/td[1]/div",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//input[@name='value']");
		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("Track Number"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[2]/td[2]/div", "Track Number");
		assertEquals(RuntimeVariables.replace("Track Number"),
			selenium.getText("//tr[2]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Track Number"),
			selenium.getText(
				"xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[5]/td[1]/div"));
		assertTrue(selenium.isPartialText("//tr[5]/td[2]/div", "number"));
		selenium.doubleClickAt("//tr[5]/td[1]/div",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//input[@name='value']");
		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("track"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));
		selenium.waitForText("//tr[5]/td[2]/div", "track");
		assertEquals(RuntimeVariables.replace("track"),
			selenium.getText("//tr[5]/td[2]/div"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isTextPresent("Song Information"));
		selenium.selectFrame("relative=top");
	}
}