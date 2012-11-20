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
public class SavePageTypeWebContentTest extends BaseTestCase {
	public void testSavePageTypeWebContent() throws Exception {
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
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//button[@title='List View']",
					RuntimeVariables.replace("List View"));
				selenium.waitForVisible(
					"//tr[contains(.,'WC WebContent Title')]/td[2]");

				String articleID = selenium.getText(
						"//tr[contains(.,'WC WebContent Title')]/td[2]");
				RuntimeVariables.setValue("articleID", articleID);
				selenium.clickAt("//button[@title='Icon View']",
					RuntimeVariables.replace("Icon View"));
				selenium.waitForVisible("//div[@class='entry-thumbnail']/img");
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
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText(
						"//a[@id='layoutsTree_layout_manage-pages-test-page']"));
				selenium.clickAt("//a[@id='layoutsTree_layout_manage-pages-test-page']",
					RuntimeVariables.replace("Manage Pages Test Page"));
				Thread.sleep(5000);
				selenium.waitForVisible("//div[@id='_88_layoutToolbar']/span");
				assertTrue(selenium.isVisible("//select[@id='_88_type']"));
				assertEquals("Portlet",
					selenium.getSelectedLabel("//select[@id='_88_type']"));
				selenium.select("//select[@id='_88_type']",
					RuntimeVariables.replace("Web Content"));
				assertEquals("Web Content",
					selenium.getSelectedLabel("//select[@id='_88_type']"));
				selenium.waitForVisible("//input[@id='_88_article-id']");
				selenium.type("//input[@id='_88_article-id']",
					RuntimeVariables.replace(RuntimeVariables.getValue(
							"articleID")));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Manage Pages Test Page",
					RuntimeVariables.replace("Manage Pages Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("WC WebContent Content"),
					selenium.getText("//p"));

			case 100:
				label = -1;
			}
		}
	}
}