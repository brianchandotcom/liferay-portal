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

package com.liferay.portalweb.portlet.managepages.page.setdisplayorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SetDisplayOrderTest extends BaseTestCase {
	public void testSetDisplayOrder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.mouseOver("link=Manage Pages Test Page");
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[1]"));
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[2]"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[3]"));
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				selenium.waitForElementPresent(
					"//li[contains(@class,'manage-page')]/a");
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//li[@id='_145_manageContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
				selenium.waitForVisible("//a[@title='Manage Page']");
				assertEquals(RuntimeVariables.replace("Page"),
					selenium.getText("//a[@title='Manage Page']"));
				selenium.clickAt("//a[@title='Manage Page']",
					RuntimeVariables.replace("Page"));
				selenium.waitForVisible("//iframe[@id='manageContentDialog']");
				selenium.selectFrame("//iframe[@id='manageContentDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/search_container.js')]");
				selenium.waitForText("//a[@class='layout-tree']", "Public Pages");
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//a[@class='layout-tree']"));

				boolean welcomePresent = selenium.isVisible(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:
				selenium.waitForText("//li/ul/li[1]/div/div[3]/a", "Welcome");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText("//li/ul/li[2]/div/div[3]/a"));
				selenium.clickAt("//li/ul/li[2]/div/div[3]/a",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.waitForValue("//div[1]/fieldset/div/span[1]/span/span/span/input",
					"Manage Pages Test Page");
				assertEquals("Manage Pages Test Page",
					selenium.getValue(
						"//div[1]/fieldset/div/span[1]/span/span/span/input"));

				boolean childPagePresent = selenium.isElementPresent(
						"//li[2]/ul/li[1]/div/div[3]/a");

				if (childPagePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:
				selenium.waitForVisible("//li[2]/ul/li[1]/div/div[3]/a");
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[2]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[3]/div/div[3]/a"));
				selenium.mouseOver("//li[2]/ul/li[1]/div/div[3]/a");
				Thread.sleep(5000);
				selenium.mouseDown("//li[2]/ul/li[1]/div/div[3]/a");
				selenium.waitForVisible(
					"//div[contains(@class, 'yui3-dd-drop-active-valid')]");
				selenium.mouseMoveAt("//li[2]/ul/li[3]/div/div[3]/a",
					RuntimeVariables.replace("0,15"));
				selenium.mouseMoveAt("//li[2]/ul/li[3]/div/div[3]/a",
					RuntimeVariables.replace("0,15"));
				selenium.waitForVisible(
					"//div[contains(@class,'aui-tree-drag-insert-below')]");
				selenium.mouseUp("//li[2]/ul/li[3]/div/div[3]/a");
				selenium.waitForText("//li[2]/ul/li[1]/div/div[3]/a",
					"Child Test Page2");
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[2]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[3]/div/div[3]/a"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.mouseOver("link=Manage Pages Test Page");
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[1]"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[2]"));
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[3]"));

			case 100:
				label = -1;
			}
		}
	}
}