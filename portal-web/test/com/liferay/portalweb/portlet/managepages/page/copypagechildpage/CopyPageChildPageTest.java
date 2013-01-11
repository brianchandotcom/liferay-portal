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

package com.liferay.portalweb.portlet.managepages.page.copypagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CopyPageChildPageTest extends BaseTestCase {
	public void testCopyPageChildPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.mouseOver("link=Manage Pages Test Page");
				selenium.waitForVisible("link=Child Test Page");
				selenium.clickAt("link=Child Test Page",
					RuntimeVariables.replace("Child Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Child Test Page"),
					selenium.getText("//nav[@id='breadcrumbs']/ul/li[2]/span/a"));
				assertTrue(selenium.isElementNotPresent(
						"//section[@id='portlet_58']/header/h1"));
				assertTrue(selenium.isElementNotPresent(
						"//section[@id='portlet_47']/header/h1"));
				assertFalse(selenium.isTextPresent("Sign In"));
				assertFalse(selenium.isTextPresent("Hello World"));
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

				boolean welcomePresent = selenium.isVisible(
						"//a[@id='layoutsTree_layout_home']");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[3]/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:
				selenium.waitForVisible("//a[@id='layoutsTree_layout_home']");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//a[@id='layoutsTree_layout_home']"));
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_manage-pages-test-page']"));

				boolean childPagePresent = selenium.isElementPresent(
						"//a[@id='layoutsTree_layout_child-test-page']");

				if (childPagePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:
				selenium.waitForVisible(
					"//a[@id='layoutsTree_layout_child-test-page']");
				assertEquals(RuntimeVariables.replace("Child Test Page"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_child-test-page']"));
				selenium.clickAt("//a[@id='layoutsTree_layout_child-test-page']",
					RuntimeVariables.replace("Child Test Page"));
				selenium.waitForValue("//input[@id='_88_name_en_US']",
					"Child Test Page");
				assertEquals("Child Test Page",
					selenium.getValue("//input[@id='_88_name_en_US']"));
				Thread.sleep(5000);
				selenium.select("//select[@id='_88_type']",
					RuntimeVariables.replace("Portlet"));
				selenium.waitForVisible(
					"//div[contains(.,'Copy Portlets from Page')]/span/button[4]");
				assertEquals(RuntimeVariables.replace("Copy Portlets from Page"),
					selenium.getText(
						"//div[contains(.,'Copy Portlets from Page')]/span/button[4]"));
				selenium.clickAt("//div[contains(.,'Copy Portlets from Page')]/span/button[4]",
					RuntimeVariables.replace("Copy Portlets from Page"));
				selenium.waitForVisible("//select[@id='_88_copyLayoutId']");
				assertEquals(RuntimeVariables.replace(
						"- Welcome - Manage Pages Test Page - - Child Test Page"),
					selenium.getText("//select[@id='_88_copyLayoutId']"));
				selenium.select("//select[@id='_88_copyLayoutId']",
					RuntimeVariables.replace("regexp:-\\sWelcome"));
				selenium.clickAt("//input[@value='Copy']",
					RuntimeVariables.replace("Copy"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.mouseOver("link=Manage Pages Test Page");
				selenium.waitForVisible("link=Child Test Page");
				selenium.clickAt("link=Child Test Page",
					RuntimeVariables.replace("Child Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Child Test Page"),
					selenium.getText("//nav[@id='breadcrumbs']/ul/li[2]/span/a"));
				assertEquals(RuntimeVariables.replace("Sign In"),
					selenium.getText("//section[@id='portlet_58']/header/h1"));
				assertEquals(RuntimeVariables.replace("Hello World"),
					selenium.getText("//section[@id='portlet_47']/header/h1"));

			case 100:
				label = -1;
			}
		}
	}
}