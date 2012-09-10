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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.editwcsubstructuresdefaultvalues;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWCSubstructure2DefaultValuesTest extends BaseTestCase {
	public void testEditWCSubstructure2DefaultValues()
		throws Exception {
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
		selenium.clickAt("link=Structures",
			RuntimeVariables.replace("Structures"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@name='_15_keywords']");
		selenium.type("//input[@name='_15_keywords']",
			RuntimeVariables.replace("Substructure2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");

		String structureID = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("structureID", structureID);
		assertEquals(RuntimeVariables.replace("${structureID}"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Substructure2 Name"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Substructure2 Description"),
			selenium.getText("//td[4]/a"));
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a/span");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForText("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit Default Values')]/a",
			"Edit Default Values");
		assertEquals(RuntimeVariables.replace("Edit Default Values"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit Default Values')]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Edit Default Values')]/a",
			RuntimeVariables.replace("Edit Default Values"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Structure Default Values"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("WC Substructure2 Name"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText("//label[@for='_15_null_en_US']"));
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Give the Web Content a Name"));
		assertEquals(RuntimeVariables.replace("Head"),
			selenium.getText("//label[@for='Head']"));
		selenium.type("//input[@id='Head']",
			RuntimeVariables.replace("Article's Title Here"));
		assertEquals(RuntimeVariables.replace("Subtitle"),
			selenium.getText("//label[@for='Subtitle']"));
		selenium.type("//input[@id='Subtitle']",
			RuntimeVariables.replace("Article's Subtitle Here"));
		assertEquals(RuntimeVariables.replace("Content"),
			selenium.getText("//label[@for='Content']"));
		selenium.type("//textarea[@id='Content']",
			RuntimeVariables.replace("Enter Article Content"));
		assertEquals(RuntimeVariables.replace("ImageBox"),
			selenium.getText("//label[@for='ImageBox']"));
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText("//label[@for='Image']"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText("//label[@for='Summary']"));
		assertEquals(RuntimeVariables.replace("Photographer"),
			selenium.getText("//label[@for='Photographer']"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//label[@for='Documents and Media']"));
		assertEquals(RuntimeVariables.replace("Extra"),
			selenium.getText("//label[@for='Extra']"));
		selenium.clickAt("//input[@id='_15_publishButton']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}