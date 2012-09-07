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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCStructure3Test extends BaseTestCase {
	public void testAddWCStructure3() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]");
		assertEquals(RuntimeVariables.replace("Structures"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]",
			RuntimeVariables.replace("Structures"));
		selenium.waitForVisible("//iframe[@id='_15_openStructuresView']");
		selenium.selectFrame("//iframe[@id='_15_openStructuresView']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		selenium.waitForVisible("//input[@value='Add Structure']");
		selenium.clickAt("//input[@value='Add Structure']",
			RuntimeVariables.replace("Add Structure"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_name_en_US']",
			RuntimeVariables.replace("WC Structure3 Name"));
		selenium.type("//textarea[@id='_15_description_en_US']",
			RuntimeVariables.replace("WC Structure3 Description"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el0_type']",
			RuntimeVariables.replace("Text"));
		selenium.type("//input[@id='_15_structure_el0_name']",
			RuntimeVariables.replace("Head"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el1_type']",
			RuntimeVariables.replace("Text"));
		selenium.type("//input[@id='_15_structure_el1_name']",
			RuntimeVariables.replace("Subtitle"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el2_type']",
			RuntimeVariables.replace("Text Box"));
		selenium.type("//input[@id='_15_structure_el2_name']",
			RuntimeVariables.replace("Content"));
		selenium.select("//select[@id='_15_structure_el2_index_type']",
			RuntimeVariables.replace("Searchable - Text"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el3_type']",
			RuntimeVariables.replace("Text"));
		selenium.type("//input[@id='_15_structure_el3_name']",
			RuntimeVariables.replace("ImageBox"));
		selenium.check("//input[@id='_15_structure_el3_repeatable']");
		assertTrue(selenium.isChecked(
				"//input[@id='_15_structure_el3_repeatable']"));
		selenium.clickAt("xPath=(//img[@alt='Add'])[4]",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el4_type']",
			RuntimeVariables.replace("Text"));
		selenium.type("//input[@id='_15_structure_el4_name']",
			RuntimeVariables.replace("Photographer"));
		selenium.clickAt("xPath=(//img[@alt='Add'])[4]",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el4_type']",
			RuntimeVariables.replace("Text"));
		selenium.type("//input[@id='_15_structure_el4_name']",
			RuntimeVariables.replace("Summary"));
		selenium.clickAt("xPath=(//img[@alt='Add'])[4]",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el4_type']",
			RuntimeVariables.replace("Image"));
		selenium.type("//input[@id='_15_structure_el4_name']",
			RuntimeVariables.replace("Image"));
		selenium.clickAt("//input[@value='Add Row']",
			RuntimeVariables.replace("Add Row"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_15_structure_el7_type']",
			RuntimeVariables.replace("Documents and Media"));
		selenium.type("//input[@id='_15_structure_el7_name']",
			RuntimeVariables.replace("Documents and Media"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//tr[5]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure3 Name"),
			selenium.getText("//tr[5]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure3 Description"),
			selenium.getText("//tr[5]/td[4]/a"));
		selenium.selectFrame("relative=top");
	}
}