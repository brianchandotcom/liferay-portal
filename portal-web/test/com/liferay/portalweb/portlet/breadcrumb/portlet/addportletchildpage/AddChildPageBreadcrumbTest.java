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

package com.liferay.portalweb.portlet.breadcrumb.portlet.addportletchildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddChildPageBreadcrumbTest extends BaseTestCase {
	public void testAddChildPageBreadcrumb() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//li[@id='_145_manageContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'manage-page')]/a");
				assertEquals(RuntimeVariables.replace("Page"),
					selenium.getText("//li[contains(@class,'manage-page')]/a"));
				selenium.clickAt("//li[contains(@class,'manage-page')]/a",
					RuntimeVariables.replace("Page"));
				selenium.waitForVisible("//iframe[@id='manageContentDialog']");
				selenium.selectFrame("//iframe[@id='manageContentDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/search_container.js')]");
				selenium.waitForText("//a[@class='layout-tree']", "Public Pages");
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//a[@class='layout-tree']"));

				boolean welcome1Present = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcome1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:
				selenium.waitForVisible("//li/ul/li[1]/div/div[3]/a");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
					selenium.getText("//ul/li[2]/div/div[3]/a"));
				selenium.clickAt("//ul/li[2]/div/div[3]/a",
					RuntimeVariables.replace("Breadcrumb Test Page"));
				selenium.waitForValue("//div[1]/fieldset/div/span[1]/span/span/span/input",
					"Breadcrumb Test Page");
				assertEquals("Breadcrumb Test Page",
					selenium.getValue(
						"//div[1]/fieldset/div/span[1]/span/span/span/input"));
				selenium.waitForVisible("//div/span/button[1]");
				assertEquals(RuntimeVariables.replace("Add Child Page"),
					selenium.getText("//div/span/button[1]"));
				selenium.clickAt("//div/span/button[1]",
					RuntimeVariables.replace("Add Child Page"));
				selenium.waitForVisible(
					"//input[@id='_88_addLayoutName_en_US']");
				selenium.type("//input[@id='_88_addLayoutName_en_US']",
					RuntimeVariables.replace("Child Test Page"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean welcome2Present = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcome2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:
				selenium.waitForText("//li/ul/li[1]/div/div[3]/a", "Welcome");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Breadcrumb Test Page"),
					selenium.getText("//li/ul/li[2]/div/div[3]/a"));

				boolean childPagePresent = selenium.isElementPresent(
						"//li[2]/ul/li[1]/div/div[3]/a");

				if (childPagePresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 4:
				selenium.waitForText("//li[2]/ul/li[1]/div/div[3]/a",
					"Child Test Page");
				assertEquals(RuntimeVariables.replace("Child Test Page"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.mouseOver("link=Breadcrumb Test Page");
				selenium.waitForVisible("link=Child Test Page");
				selenium.clickAt("link=Child Test Page",
					RuntimeVariables.replace("Child Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Child Test Page"),
					selenium.getText(
						"//nav[@id='breadcrumbs']/ul/li[@class='last']/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}