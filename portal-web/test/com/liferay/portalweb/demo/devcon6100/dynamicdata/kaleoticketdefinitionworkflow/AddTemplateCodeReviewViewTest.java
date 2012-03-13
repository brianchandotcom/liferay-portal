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
public class AddTemplateCodeReviewViewTest extends BaseTestCase {
	public void testAddTemplateCodeReviewView() throws Exception {
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
							"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Manage Templates"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Add Detail Template")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Add Detail Template",
			RuntimeVariables.replace("Add Detail Template"));

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

		selenium.type("//input[@id='_166_name_en_US']",
			RuntimeVariables.replace("Code Review View"));
		selenium.select("//select[@id='_166_mode']",
			RuntimeVariables.replace("Edit"));
		assertEquals(RuntimeVariables.replace("Tested Revision"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-canvas']/div/div[7]/div[1]/label"));
		selenium.clickAt("//div[@class='aui-diagram-builder-canvas']/div/div[7]",
			RuntimeVariables.replace("Tested Revision"));
		selenium.click(
			"//div[@class='aui-diagram-builder-canvas']/div/div[7]/div[3]/span/span/button[2]");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace("Tested Status"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-canvas']/div/div[7]/div[1]/label"));
		selenium.clickAt("//div[@class='aui-diagram-builder-canvas']/div/div[7]",
			RuntimeVariables.replace("Tested Status"));
		selenium.click(
			"//div[@class='aui-diagram-builder-canvas']/div/div[7]/div[2]/span/span/button[2]");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}