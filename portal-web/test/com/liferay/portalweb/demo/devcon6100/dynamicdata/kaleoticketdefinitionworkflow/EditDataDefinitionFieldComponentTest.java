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

package com.liferay.portalweb.demo.devcon6100.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditDataDefinitionFieldComponentTest extends BaseTestCase {
	public void testEditDataDefinitionFieldComponent()
		throws Exception {
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//a[@id='_167_manageDDMStructuresLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
			selenium.getText("//a[@id='_167_manageDDMStructuresLink']"));
		selenium.clickAt("//a[@id='_167_manageDDMStructuresLink']",
			RuntimeVariables.replace("Manage Data Definitions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[@id='_166_toggle_id_ddm_structure_searchkeywords']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_166_toggle_id_ddm_structure_searchkeywords']",
			RuntimeVariables.replace("Ticket Definition"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[@title='Actions']/ul/li/strong/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		Thread.sleep(5000);
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='aui-diagram-builder-canvas']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("xPath=(//button[@id='editEvent'])[2]",
			RuntimeVariables.replace("Edit"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tbody[@class='yui3-datatable-data']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[1]",
			RuntimeVariables.replace("Field Label"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='yui3-widget-bd']/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("Component"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[1]",
			RuntimeVariables.replace("Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='yui3-widget-bd']/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//div[@class='yui3-widget-bd']/input",
			RuntimeVariables.replace("component_name"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[8]/td[1]",
			RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='yui3-widget-bd']/div/div[2]/input[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add option"),
			selenium.getText(
				"//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']"));
		selenium.clickAt("//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']",
			RuntimeVariables.replace("Add option"));
		selenium.clickAt("//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']",
			RuntimeVariables.replace("Add option"));
		selenium.clickAt("//a[@class='aui-celleditor-edit-link aui-celleditor-edit-add-option']",
			RuntimeVariables.replace("Add option"));
		assertTrue(selenium.isVisible(
				"//div[@class='yui3-widget-bd']/div[2]/div[7]/input"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[2]/input[1]",
			RuntimeVariables.replace("Blog"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[2]/input[2]",
			RuntimeVariables.replace("Blog"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[3]/input[1]",
			RuntimeVariables.replace("Bookmarks"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[3]/input[2]",
			RuntimeVariables.replace("Bookmarks"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[4]/input[1]",
			RuntimeVariables.replace("Documents and Media"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[4]/input[2]",
			RuntimeVariables.replace("Documents and Media"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[5]/input[1]",
			RuntimeVariables.replace("Kaleo"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[5]/input[2]",
			RuntimeVariables.replace("Kaleo"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[6]/input[1]",
			RuntimeVariables.replace("DDL"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[6]/input[2]",
			RuntimeVariables.replace("DDL"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[7]/input[1]",
			RuntimeVariables.replace("Javascript"));
		selenium.type("//div[@class='yui3-widget-bd']/div/div[7]/input[2]",
			RuntimeVariables.replace("Javascript"));
		selenium.clickAt("//div[@class='yui3-widget-ft']/span/span/button",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("select"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Component"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("component_name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"Blog, Bookmarks, Documents and Media, Kaleo, DDL, Javascript"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[2]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[9]/td[2]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}