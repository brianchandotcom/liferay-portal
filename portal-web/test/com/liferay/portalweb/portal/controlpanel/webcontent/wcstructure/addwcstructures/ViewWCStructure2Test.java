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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructures;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCStructure2Test extends BaseTestCase {
	public void testViewWCStructure2() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace(
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role. Or disable for all portlets."),
			selenium.getText("//div[@id='cpContextPanelTemplate']"));
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

		String structureID = selenium.getText("//tr[4]/td[2]/a");
		RuntimeVariables.setValue("structureID", structureID);
		assertEquals(RuntimeVariables.replace("${structureID}"),
			selenium.getText("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Description"),
			selenium.getText("//tr[4]/td[4]/a"));
		selenium.clickAt("//tr[4]/td[3]/a",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText(
				"//fieldset/div/div/div[contains(.,'ID')]",
				RuntimeVariables.getValue("structureID")));
		assertEquals("WC Structure2 Name",
			selenium.getValue("//input[@id='_15_name_en_US']"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"//input[@id='_15_name_en_US']/following-sibling::span/a"));
		assertEquals("WC Structure2 Description",
			selenium.getValue("//textarea[@id='_15_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"//textarea[@id='_15_description_en_US']/following-sibling::span/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//span[@id='_15_parentStructureName']"));
		assertEquals("Select",
			selenium.getValue(
				"//span[@id='_15_parentStructureName']/following-sibling::span/span/input"));
		assertEquals("Remove",
			selenium.getValue(
				"//span[@id='_15_parentStructureName']/following-sibling::span[2]/span/input"));
		assertTrue(selenium.isVisible("//input[@value='Add Row']"));
		assertTrue(selenium.isVisible("//input[@value='Launch Editor']"));
		assertTrue(selenium.isVisible("//input[@value='Download']"));
		assertEquals("Text",
			selenium.getSelectedLabel("//select[@id='_15_structure_el0_type']"));
		assertEquals("Head",
			selenium.getValue("//input[@id='_15_structure_el0_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el0_repeatable']"));
		assertEquals("Text",
			selenium.getSelectedLabel("//select[@id='_15_structure_el1_type']"));
		assertEquals("Subtitle",
			selenium.getValue("//input[@id='_15_structure_el1_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el1_repeatable']"));
		assertEquals("Text Box",
			selenium.getSelectedLabel("//select[@id='_15_structure_el2_type']"));
		assertEquals("Content",
			selenium.getValue("//input[@id='_15_structure_el2_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el2_repeatable']"));
		assertEquals("Text",
			selenium.getSelectedLabel("//select[@id='_15_structure_el3_type']"));
		assertEquals("ImageBox",
			selenium.getValue("//input[@id='_15_structure_el3_name']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_15_structure_el3_repeatable']"));
		assertEquals("Image",
			selenium.getSelectedLabel("//select[@id='_15_structure_el4_type']"));
		assertEquals("Image",
			selenium.getValue("//input[@id='_15_structure_el4_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el4_repeatable']"));
		assertEquals("Text",
			selenium.getSelectedLabel("//select[@id='_15_structure_el5_type']"));
		assertEquals("Summary",
			selenium.getValue("//input[@id='_15_structure_el5_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el5_repeatable']"));
		assertEquals("Text",
			selenium.getSelectedLabel("//select[@id='_15_structure_el6_type']"));
		assertEquals("Photographer",
			selenium.getValue("//input[@id='_15_structure_el6_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el6_repeatable']"));
		assertEquals("Documents and Media",
			selenium.getSelectedLabel("//select[@id='_15_structure_el7_type']"));
		assertEquals("Documents and Media",
			selenium.getValue("//input[@id='_15_structure_el7_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el7_repeatable']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Save and Continue']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Save and Edit Default Values']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
		selenium.selectFrame("relative=top");
	}
}