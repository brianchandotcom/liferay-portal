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

package com.liferay.portalweb.portlet.managepages.page.addchildpagemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddChildPage3Test extends BaseTestCase {
	public void testAddChildPage3() throws Exception {
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

				boolean welcome1Present = selenium.isVisible(
						"//a[@id='layoutsTree_layout_home']");

				if (welcome1Present) {
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
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_manage-pages-test-page']"));
				selenium.clickAt("//a[@id='layoutsTree_layout_manage-pages-test-page']",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.waitForValue("//input[@id='_88_name_en_US']",
					"Manage Pages Test Page");
				assertEquals("Manage Pages Test Page",
					selenium.getValue("//input[@id='_88_name_en_US']"));
				Thread.sleep(5000);
				selenium.waitForVisible(
					"//div[contains(.,'Add Child Page')]/span/button");
				assertEquals(RuntimeVariables.replace("Add Child Page"),
					selenium.getText(
						"//div[contains(.,'Add Child Page')]/span/button"));
				selenium.clickAt("//div[contains(.,'Add Child Page')]/span/button",
					RuntimeVariables.replace("Add Child Page"));
				selenium.waitForVisible(
					"//input[@id='_88_addLayoutName_en_US']");
				selenium.type("//input[@id='_88_addLayoutName_en_US']",
					RuntimeVariables.replace("Child Test Page3"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean welcome2Present = selenium.isElementPresent(
						"//a[@id='layoutsTree_layout_home']");

				if (welcome2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//a[@id='layoutsTree_layout_home']"));
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_manage-pages-test-page']"));

				boolean childPagePresent = selenium.isElementPresent(
						"//a[@id='layoutsTree_layout_child-test-page1']");

				if (childPagePresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 4:
				selenium.waitForText("//a[@id='layoutsTree_layout_child-test-page1']",
					"Child Test Page1");
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_child-test-page1']"));
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_child-test-page2']"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_child-test-page3']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.mouseOver("link=Manage Pages Test Page");
				selenium.waitForVisible("link=Child Test Page1");
				assertTrue(selenium.isVisible("link=Child Test Page1"));
				assertTrue(selenium.isVisible("link=Child Test Page2"));
				assertTrue(selenium.isVisible("link=Child Test Page3"));
				selenium.clickAt("link=Child Test Page3",
					RuntimeVariables.replace("Child Test Page3"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//nav[@id='breadcrumbs']/ul/li[2]/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}