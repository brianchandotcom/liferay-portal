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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinition;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDataDefinitionTest extends BaseTestCase {
	public void testTearDownDataDefinition() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.waitForVisible(
					"//a[@id='_167_manageDDMStructuresLink']");
				assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
					selenium.getText("//a[@id='_167_manageDDMStructuresLink']"));
				selenium.clickAt("//a[@id='_167_manageDDMStructuresLink']",
					RuntimeVariables.replace("Manage Data Definitions"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				Thread.sleep(1000);

				boolean actionsVisible1 = selenium.isElementPresent(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span");

				if (!actionsVisible1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				Thread.sleep(1000);

				boolean actionsVisible2 = selenium.isElementPresent(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span");

				if (!actionsVisible2) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				Thread.sleep(1000);

				boolean actionsVisible3 = selenium.isElementPresent(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span");

				if (!actionsVisible3) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				Thread.sleep(1000);

				boolean actionsVisible4 = selenium.isElementPresent(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span");

				if (!actionsVisible4) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				Thread.sleep(1000);

				boolean actionsVisible5 = selenium.isElementPresent(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span");

				if (!actionsVisible5) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//tr[contains(.,'Data Definition')]//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}