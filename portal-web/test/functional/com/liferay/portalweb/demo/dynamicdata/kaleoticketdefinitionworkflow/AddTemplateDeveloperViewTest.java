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
public class AddTemplateDeveloperViewTest extends BaseTestCase {
	public void testAddTemplateDeveloperView() throws Exception {
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
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible(
			"//input[@id='_166_toggle_id_ddm_structure_searchkeywords']");
		selenium.type("//input[@id='_166_toggle_id_ddm_structure_searchkeywords']",
			RuntimeVariables.replace("Ticket Definition"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a/span");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Templates')]/a");
		assertEquals(RuntimeVariables.replace("Manage Templates"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Templates')]/a"));
		selenium.click(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Templates')]/a");
		selenium.waitForVisible("link=Add Detail Template");
		selenium.clickAt("link=Add Detail Template",
			RuntimeVariables.replace("Add Detail Template"));
		selenium.waitForVisible("//div[@class='diagram-builder-canvas']");
		selenium.type("//input[@id='_166_name_en_US']",
			RuntimeVariables.replace("Developer View"));
		selenium.select("//select[@id='_166_mode']",
			RuntimeVariables.replace("Edit"));
		assertEquals(RuntimeVariables.replace("Tested Revision"),
			selenium.getText(
				"//div[@class='diagram-builder-canvas']/div/div[7]/div[1]/label"));
		selenium.clickAt("//div[@class='diagram-builder-canvas']/div/div[7]",
			RuntimeVariables.replace("Tested Revision"));
		selenium.click(
			"//div[@class='diagram-builder-canvas']/div/div[7]/div[3]/span/span/button[2]");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace("Tested Status"),
			selenium.getText(
				"//div[@class='diagram-builder-canvas']/div/div[7]/div[1]/label"));
		selenium.clickAt("//div[@class='diagram-builder-canvas']/div/div[7]",
			RuntimeVariables.replace("Tested Status"));
		selenium.click(
			"//div[@class='diagram-builder-canvas']/div/div[7]/div[2]/span/span/button[2]");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace("Pull Request URL"),
			selenium.getText(
				"//div[@class='diagram-builder-canvas']/div/div[7]/div[1]/label"));
		selenium.clickAt("//div[@class='diagram-builder-canvas']/div/div[7]",
			RuntimeVariables.replace("Pull Request URL"));
		selenium.waitForVisible("xPath=(//button[@id='editEvent'])[7]");
		selenium.clickAt("xPath=(//button[@id='editEvent'])[7]",
			RuntimeVariables.replace("Edit"));
		assertEquals(RuntimeVariables.replace("Required"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[1]",
			RuntimeVariables.replace("Required"));
		selenium.waitForVisible(
			"//div[@class='yui3-widget-bd']/div/label[1]/input");
		selenium.clickAt("//div[@class='yui3-widget-bd']/div/label[1]/input",
			RuntimeVariables.replace("Yes"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}