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

package com.liferay.portalweb.portlet.managepages.page.addpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageMPTest extends BaseTestCase {
	public void testTearDownPageMP() throws Exception {
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

				boolean tree1Collapsed = selenium.isElementPresent(
						"//li[@id='layoutsTree_layoutId_0_plid_0']/div[contains(@class,'aui-tree-collapsed')]");

				if (!tree1Collapsed) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");

				boolean page1Present = selenium.isElementPresent(
						"xPath=(//a[contains(@id,'layoutsTree_layout')])[2]");

				if (!page1Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("xPath=(//a[contains(@id,'layoutsTree_layout')])[2]",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForElementPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementNotPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-dialog-iframe/aui-dialog-iframe-min.js')]");
				selenium.waitForVisible("//span[.='Copy Portlets from Page']");
				Thread.sleep(5000);
				selenium.waitForVisible("//span[.='Delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//span[.='Delete']"));
				selenium.click(RuntimeVariables.replace("//span[.='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");

				boolean page2Present = selenium.isElementPresent(
						"xPath=(//a[contains(@id,'layoutsTree_layout')])[2]");

				if (!page2Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("xPath=(//a[contains(@id,'layoutsTree_layout')])[2]",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForElementPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementNotPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-dialog-iframe/aui-dialog-iframe-min.js')]");
				selenium.waitForVisible("//span[.='Copy Portlets from Page']");
				Thread.sleep(5000);
				selenium.waitForVisible("//span[.='Delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//span[.='Delete']"));
				selenium.click(RuntimeVariables.replace("//span[.='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");

				boolean page3Present = selenium.isElementPresent(
						"xPath=(//a[contains(@id,'layoutsTree_layout')])[2]");

				if (!page3Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("xPath=(//a[contains(@id,'layoutsTree_layout')])[2]",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForElementPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementNotPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-dialog-iframe/aui-dialog-iframe-min.js')]");
				selenium.waitForVisible("//span[.='Copy Portlets from Page']");
				Thread.sleep(5000);
				selenium.waitForVisible("//span[.='Delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//span[.='Delete']"));
				selenium.click(RuntimeVariables.replace("//span[.='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");

				boolean page4Present = selenium.isElementPresent(
						"xPath=(//a[contains(@id,'layoutsTree_layout')])[2]");

				if (!page4Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("xPath=(//a[contains(@id,'layoutsTree_layout')])[2]",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForElementPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementNotPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-dialog-iframe/aui-dialog-iframe-min.js')]");
				selenium.waitForVisible("//span[.='Copy Portlets from Page']");
				Thread.sleep(5000);
				selenium.waitForVisible("//span[.='Delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//span[.='Delete']"));
				selenium.click(RuntimeVariables.replace("//span[.='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.waitForText("//a[@id='layoutsTree_layout_home']",
					"Welcome");

				boolean page5Present = selenium.isElementPresent(
						"xPath=(//a[contains(@id,'layoutsTree_layout')])[2]");

				if (!page5Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("xPath=(//a[contains(@id,'layoutsTree_layout')])[2]",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-dialog-iframe/aui-dialog-iframe-min.js')]");
				selenium.waitForElementPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForElementNotPresent(
					"//div[@id='_88_layoutsContainer']/div[contains(@class,'aui-loadingmask')]");
				selenium.waitForVisible("//span[.='Copy Portlets from Page']");
				Thread.sleep(5000);
				selenium.waitForVisible("//span[.='Delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//span[.='Delete']"));
				selenium.click(RuntimeVariables.replace("//span[.='Delete']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}