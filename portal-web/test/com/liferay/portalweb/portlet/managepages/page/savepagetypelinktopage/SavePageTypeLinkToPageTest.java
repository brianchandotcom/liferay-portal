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

package com.liferay.portalweb.portlet.managepages.page.savepagetypelinktopage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SavePageTypeLinkToPageTest extends BaseTestCase {
	public void testSavePageTypeLinkToPage() throws Exception {
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
				selenium.clickAt("//a[@id='layoutsTree_layout_manage-pages-test-page']",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.waitForValue("//input[@id='_88_name_en_US']",
					"Manage Pages Test Page");
				assertEquals("Manage Pages Test Page",
					selenium.getValue("//input[@id='_88_name_en_US']"));
				Thread.sleep(5000);
				selenium.waitForPartialText("//select[@id='_88_type']",
					"Link to Page");
				selenium.select("//select[@id='_88_type']",
					RuntimeVariables.replace("Link to Page"));
				selenium.waitForVisible("//select[@id='_88_linkToLayoutId']");
				selenium.waitForText("//select[@id='_88_linkToLayoutId']",
					"- Welcome - Manage Pages Test Page");
				selenium.select("//select[@id='_88_linkToLayoutId']",
					RuntimeVariables.replace("regexp:-\\sWelcome"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Save']"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Manage Pages Test Page",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//nav[@id='breadcrumbs']/ul/li/span/a"));
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