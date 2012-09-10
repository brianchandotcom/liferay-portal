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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcsubstructures;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCSubstructure1Test extends BaseTestCase {
	public void testViewWCSubstructure1() throws Exception {
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
			RuntimeVariables.replace("Substructure1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");

		String structureID = selenium.getText("//td[2]/a");
		RuntimeVariables.setValue("structureID", structureID);
		assertEquals(RuntimeVariables.replace("${structureID}"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Substructure1 Name"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Substructure1 Description"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("WC Substructure1 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isPartialText(
				"//div[@id='cpContextPanelTemplate']",
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role."));
		assertEquals(RuntimeVariables.replace("WC Substructure1 Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"xPath=(//span[contains(@class,'flag-selector')]/a)[1]"));
		assertTrue(selenium.isPartialText(
				"//fieldset/div/div/div[contains(.,'ID')]",
				RuntimeVariables.getValue("structureID")));
		assertEquals("WC Substructure1 Name",
			selenium.getValue("//input[@id='_15_name_en_US']"));
		assertEquals("WC Substructure1 Description",
			selenium.getValue("//textarea[@id='_15_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"xPath=(//span[contains(@class,'flag-selector')]/a)[2]"));
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//a[@id='_15_parentStructureName']"));
		assertEquals("Select", selenium.getValue("//input[@value='Select']"));
		assertEquals("Remove", selenium.getValue("//input[@value='Remove']"));
		assertTrue(selenium.isVisible("//input[@value='Add Row']"));
		assertTrue(selenium.isVisible("//input[@value='Launch Editor']"));
		assertTrue(selenium.isVisible("//input[@value='Download']"));
		assertEquals("Text",
			selenium.getSelectedLabel("//select[@id='_15_structure_el0_type']"));
		assertEquals("Extra",
			selenium.getValue("//input[@id='_15_structure_el0_name']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_15_structure_el0_repeatable']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Save and Continue']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Save and Edit Default Values']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}