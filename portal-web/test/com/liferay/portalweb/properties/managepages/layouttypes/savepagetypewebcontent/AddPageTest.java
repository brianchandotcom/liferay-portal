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

package com.liferay.portalweb.properties.managepages.layouttypes.savepagetypewebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageTest extends BaseTestCase {
	public void testAddPage() throws Exception {
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
				selenium.waitForVisible("//a[@title='Manage Page']");
				selenium.clickAt("//a[@title='Manage Page']",
					RuntimeVariables.replace("Page"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/util_window.js')]");
				selenium.waitForElementPresent(
					"//iframe[contains(@id,'manageContentDialog')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'manageContentDialog')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/search_container.js')]");
				selenium.waitForText("//a[@class='layout-tree']", "Public Pages");
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//a[@class='layout-tree']"));

				boolean welcomePresent = selenium.isElementPresent(
						"//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//a[@id='layoutsTree_layout_home']"));
				selenium.clickAt("//a[@class='layout-tree']",
					RuntimeVariables.replace("Public Pages"));
				selenium.waitForVisible(
					"//div[@id='_88_layoutSetToolbar']/span/button[contains(.,'Add Page')]");
				assertEquals(RuntimeVariables.replace("Add Page"),
					selenium.getText(
						"//div[@id='_88_layoutSetToolbar']/span/button[contains(.,'Add Page')]"));
				selenium.clickAt("//div[@id='_88_layoutSetToolbar']/span/button[contains(.,'Add Page')]",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForVisible(
					"//input[@id='_88_addLayoutName_en_US']");
				selenium.type("//input[@id='_88_addLayoutName_en_US']",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean pagePresent = selenium.isElementPresent(
						"//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]");

				if (pagePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:
				selenium.waitForText("//a[@id='layoutsTree_layout_manage-pages-test-page']",
					"Manage Pages Test Page");
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_manage-pages-test-page']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Manage Pages Test Page",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText(
						"//nav[@id='breadcrumbs']/ul/li[@class='last']/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}