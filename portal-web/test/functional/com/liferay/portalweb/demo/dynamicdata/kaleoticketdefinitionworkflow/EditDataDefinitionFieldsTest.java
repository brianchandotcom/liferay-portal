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

package com.liferay.portalweb.demo.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditDataDefinitionFieldsTest extends BaseTestCase {
	public void testEditDataDefinitionFields() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
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
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_167_manageDDMStructuresLink']");
		assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
			selenium.getText("//a[@id='_167_manageDDMStructuresLink']"));
		selenium.clickAt("//a[@id='_167_manageDDMStructuresLink']",
			RuntimeVariables.replace("Manage Data Definitions"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForVisible(
			"//input[@id='_166_toggle_id_ddm_structure_searchkeywords']");
		selenium.type("//input[@id='_166_toggle_id_ddm_structure_searchkeywords']",
			RuntimeVariables.replace("Ticket Definition"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a");
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace(""));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		selenium.waitForVisible("//div[@class='diagram-builder-canvas']");
		selenium.clickAt("xPath=(//button[@id='editEvent'])[2]",
			RuntimeVariables.replace("Edit"));
		selenium.clickAt("xPath=(//button[@id='editEvent'])[3]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//tbody[@class='yui3-datatable-data']");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[1]",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//div[@class='yui3-widget-bd']/input");
		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("Summary"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[1]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible("//div[@class='yui3-widget-bd']/input");
		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("summary"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Width"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[8]/td[1]",
			RuntimeVariables.replace("Width"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[8]/td[1]",
			RuntimeVariables.replace("Width"));
		selenium.waitForVisible(
			"//div[@class='yui3-widget-bd']/div/label[3]/input");
		selenium.clickAt("//div[@class='yui3-widget-bd']/div/label[3]/input",
			RuntimeVariables.replace("Large"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("text"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Summary"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("summary"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace("Large"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[2]"));
		selenium.clickAt("xPath=(//button[@id='editEvent'])[4]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//tbody[@class='yui3-datatable-data']");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[1]",
			RuntimeVariables.replace("Field Label"));
		selenium.waitForVisible("//div[@class='yui3-widget-bd']/input");
		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("Affects Version/s"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[1]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible("//div[@class='yui3-widget-bd']/input");
		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("affect_version"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Width"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[8]/td[1]",
			RuntimeVariables.replace("Width"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[8]/td[1]",
			RuntimeVariables.replace("Width"));
		selenium.waitForVisible(
			"//div[@class='yui3-widget-bd']/div/label[3]/input");
		selenium.clickAt("//div[@class='yui3-widget-bd']/div/label[3]/input",
			RuntimeVariables.replace("Large"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("text"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Affects Version/s"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("summary"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace("Large"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[2]"));
	}
}