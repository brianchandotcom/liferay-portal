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

package com.liferay.portalweb.demo.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddProcessKFTest extends BaseTestCase {
	public void testAddProcessKF() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Kaleo Forms Test Page",
			RuntimeVariables.replace("Kaleo Forms Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Processes", RuntimeVariables.replace("Processes"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//div[@class='lfr-portlet-toolbar']/span[2]/a"));
		selenium.clickAt("//div[@class='lfr-portlet-toolbar']/span[2]/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_name_en_US']",
			RuntimeVariables.replace("Ticket Process"));
		selenium.type("//textarea[@id='_1_WAR_kaleoformsportlet_description_en_US']",
			RuntimeVariables.replace("Ticket Process"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_selectDDMStructure']"));
		selenium.clickAt("//a[@id='_1_WAR_kaleoformsportlet_selectDDMStructure']",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//tr[3]/td[2]/a");
		assertEquals(RuntimeVariables.replace("Ticket Definition"),
			selenium.getText("//tr[3]/td[2]/a"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("Ticket Definition"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//a[@id='_1_WAR_kaleoformsportlet_selectDDMStructureDisplay']");
		assertEquals(RuntimeVariables.replace("Ticket Definition"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_selectDDMStructureDisplay']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_selectDDMTemplate']"));
		selenium.clickAt("//a[@id='_1_WAR_kaleoformsportlet_selectDDMTemplate']",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//tr[3]/td[2]/a");
		assertEquals(RuntimeVariables.replace("Ticket Creation"),
			selenium.getText("//tr[3]/td[2]/a"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("Ticket Creation"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//a[@id='_1_WAR_kaleoformsportlet_selectDDMTemplateDisplay']");
		assertEquals(RuntimeVariables.replace("Ticket Creation"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_selectDDMTemplateDisplay']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_selectWorkflowDefinition']"));
		selenium.clickAt("//a[@id='_1_WAR_kaleoformsportlet_selectWorkflowDefinition']",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("link=Ticket Process");
		selenium.clickAt("link=Ticket Process",
			RuntimeVariables.replace("Ticket Process"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//a[@id='_1_WAR_kaleoformsportlet_selectWorkflowDefinitionDisplay']");
		assertEquals(RuntimeVariables.replace("Ticket Process (Version 1)"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_selectWorkflowDefinitionDisplay']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Assign"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_assignWorkflowTaskForms']"));
		selenium.clickAt("//a[@id='_1_WAR_kaleoformsportlet_assignWorkflowTaskForms']",
			RuntimeVariables.replace("Assign"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//div[@id='diagramNode_field_Developer']/div/div/div");
		selenium.clickAt("//div[@id='diagramNode_field_Developer']/div/div/div",
			RuntimeVariables.replace("Developer"));
		selenium.waitForVisible(
			"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]");
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			RuntimeVariables.replace("Forms"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@name='templateName']");
		selenium.clickAt("//input[@name='templateName']",
			RuntimeVariables.replace("Developer View"));
		selenium.waitForVisible("//li[@data-text='Developer View']");
		assertEquals(RuntimeVariables.replace("Developer View"),
			selenium.getText("//li[@data-text='Developer View']"));
		selenium.clickAt("//li[@data-text='Developer View']",
			RuntimeVariables.replace("Developer View"));
		selenium.waitForValue("//input[@name='templateName']", "Developer View");
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			"Developer View");
		assertEquals(RuntimeVariables.replace("Developer View"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		Thread.sleep(5000);
		selenium.clickAt("//div[@id='diagramNode_field_Code_Review']/div/div/div",
			RuntimeVariables.replace("Code Review"));
		selenium.waitForVisible(
			"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]");
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			RuntimeVariables.replace("Forms"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@name='templateName']");
		selenium.clickAt("//input[@name='templateName']",
			RuntimeVariables.replace("Code Review View"));
		selenium.waitForVisible("//li[@data-text='Code Review View']");
		assertEquals(RuntimeVariables.replace("Code Review View"),
			selenium.getText("//li[@data-text='Code Review View']"));
		selenium.clickAt("//li[@data-text='Code Review View']",
			RuntimeVariables.replace("Code Review View"));
		selenium.waitForValue("//input[@name='templateName']",
			"Code Review View");
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]/div",
			"Code Review View");
		assertEquals(RuntimeVariables.replace("Code Review View"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]/div"));
		Thread.sleep(5000);
		selenium.clickAt("//div[@id='diagramNode_field_QA']/div/div/div",
			RuntimeVariables.replace("QA"));
		selenium.waitForVisible(
			"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]");
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			RuntimeVariables.replace("Forms"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@name='templateName']");
		selenium.clickAt("//input[@name='templateName']",
			RuntimeVariables.replace("QA View"));
		selenium.waitForVisible("//li[@data-text='QA View']");
		assertEquals(RuntimeVariables.replace("QA View"),
			selenium.getText("//li[@data-text='QA View']"));
		selenium.clickAt("//li[@data-text='QA View']",
			RuntimeVariables.replace("QA View"));
		selenium.waitForValue("//input[@name='templateName']", "QA View");
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			"QA View");
		assertEquals(RuntimeVariables.replace("QA View"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		Thread.sleep(5000);
		selenium.clickAt("//div[@id='diagramNode_field_QA_Management']/div/div/div",
			RuntimeVariables.replace("QA Management"));
		selenium.waitForVisible(
			"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]");
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			RuntimeVariables.replace("Forms"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@name='templateName']");
		selenium.clickAt("//input[@name='templateName']",
			RuntimeVariables.replace("QA View"));
		selenium.waitForVisible("//li[@data-text='QA View']");
		assertEquals(RuntimeVariables.replace("QA View"),
			selenium.getText("//li[@data-text='QA View']"));
		selenium.clickAt("//li[@data-text='QA View']",
			RuntimeVariables.replace("QA View"));
		selenium.waitForValue("//input[@name='templateName']", "QA View");
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			"QA View");
		assertEquals(RuntimeVariables.replace("QA View"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		Thread.sleep(5000);
		selenium.clickAt("//div[@id='diagramNode_field_Project_Manager_Review']/div/div/div",
			RuntimeVariables.replace("Project Manager Review"));
		selenium.waitForVisible(
			"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]");
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			RuntimeVariables.replace("Forms"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@name='templateName']");
		selenium.clickAt("//input[@name='templateName']",
			RuntimeVariables.replace("Project Manager View"));
		selenium.waitForVisible("//li[@data-text='Project Manager View']");
		assertEquals(RuntimeVariables.replace("Project Manager View"),
			selenium.getText("//li[@data-text='Project Manager View']"));
		selenium.clickAt("//li[@data-text='Project Manager View']",
			RuntimeVariables.replace("Project Manager View"));
		selenium.waitForValue("//input[@name='templateName']",
			"Project Manager View");
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			"Project Manager View");
		assertEquals(RuntimeVariables.replace("Project Manager View"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Close']",
			RuntimeVariables.replace("Close"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}