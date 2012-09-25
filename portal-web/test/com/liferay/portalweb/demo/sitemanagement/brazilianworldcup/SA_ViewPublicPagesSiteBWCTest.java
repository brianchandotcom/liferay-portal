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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_ViewPublicPagesSiteBWCTest extends BaseTestCase {
	public void testSA_ViewPublicPagesSiteBWC() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.openWindow("http://www.able.com:8080",
					RuntimeVariables.replace("home"));
				selenium.waitForPopUp("home", RuntimeVariables.replace(""));
				selenium.selectWindow("home");
				Thread.sleep(5000);
				Thread.sleep(5000);
				assertTrue(selenium.isPartialText(
						"//a[@class='user-fullname use-dialog']", "Bruno Admin"));
				selenium.waitForVisible("//a[@class='logo custom-logo']");
				assertTrue(selenium.isVisible("//a[@class='logo custom-logo']"));
				assertTrue(selenium.isElementPresent("//img[@height='156']"));
				assertTrue(selenium.isElementPresent("//img[@width='320']"));
				assertTrue(selenium.isElementNotPresent(
						"//a[@class='logo default-logo']"));
				assertTrue(selenium.isElementPresent(
						"//body[@class='green yui3-skin-sam controls-visible signed-in public-page site dockbar-ready']"));
				assertTrue(selenium.isElementNotPresent(
						"//body[@class='blue yui3-skin-sam controls-visible signed-in public-page site dockbar-ready']"));
				assertTrue(selenium.isVisible("link=Home"));
				selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
					selenium.getText("//footer[@id='footer']"));
				assertTrue(selenium.isVisible("link=Arenas"));
				selenium.clickAt("link=Arenas",
					RuntimeVariables.replace("Arenas"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Arenas"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
					selenium.getText("//footer[@id='footer']"));
				selenium.mouseOver("link=Arenas");
				assertTrue(selenium.isVisible("link=Arena Pernambuco"));
				selenium.clickAt("link=Arena Pernambuco",
					RuntimeVariables.replace("Arena Pernambuco"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Arenas"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Arena Pernambuco"),
					selenium.getText("//nav/ul/li[4]/span/a"));
				assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
					selenium.getText("//footer[@id='footer']"));
				selenium.mouseOver("link=Arenas");
				assertTrue(selenium.isVisible("link=Arena da Baixada"));
				selenium.clickAt("link=Arena da Baixada",
					RuntimeVariables.replace("Arena da Baixada"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Arenas"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Arena da Baixada"),
					selenium.getText("//nav/ul/li[4]/span/a"));
				assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
					selenium.getText("//footer[@id='footer']"));
				selenium.mouseOver("link=Arenas");
				assertTrue(selenium.isVisible("link=Maracana"));
				selenium.clickAt("link=Maracana",
					RuntimeVariables.replace("Maracana"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Arenas"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Maracana"),
					selenium.getText("//nav/ul/li[4]/span/a"));
				assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
					selenium.getText("//footer[@id='footer']"));
				assertTrue(selenium.isElementNotPresent("link=Accommodations"));
				assertTrue(selenium.isElementNotPresent("link=Maps"));
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("World Cup - Brazil 2014"),
					selenium.getText("//strong/a/span"));
				assertEquals(RuntimeVariables.replace("Site Settings"),
					selenium.getText("link=Site Settings"));
				selenium.open("http://www.able.com:8080");
				selenium.clickAt("//nav[@id='navigation']",
					RuntimeVariables.replace("Navigation"));
				selenium.waitForElementPresent("//a[@id='addPage']");
				selenium.clickAt("//a[@id='addPage']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForVisible("//input[@type='text']");
				selenium.type("//input[@type='text']",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.clickAt("//button[contains(@id,'Save')]",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible("link=Web Content Display Test Page");
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_145_addApplication']", "More"));
				selenium.clickAt("//a[@id='_145_addApplication']",
					RuntimeVariables.replace("More"));
				selenium.waitForElementPresent(
					"//div[@title='Web Content Display']/p/a");
				selenium.clickAt("//div[@title='Web Content Display']/p/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible("//section");
				assertTrue(selenium.isVisible("//section"));
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//li[contains(@class,'manage-page')]/a");
				selenium.clickAt("//li[contains(@class,'manage-page')]/a",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForVisible("//iframe[@id='manageContentDialog']");
				selenium.selectFrame("//iframe[@id='manageContentDialog']");
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
				selenium.waitForText("//li/ul/li[1]/div/div[3]/a", "Home");

				boolean page1Present = selenium.isElementPresent(
						"//li[3]/div/div[3]/a");

				if (!page1Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"Web Content Display Test Page"),
					selenium.getText("//li[3]/div/div[3]/a"));
				selenium.clickAt("//li[3]/div/div[3]/a",
					RuntimeVariables.replace("Web Content Display Test Page"));
				Thread.sleep(5000);
				selenium.waitForVisible("//button[3]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				Thread.sleep(5000);
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");

			case 100:
				label = -1;
			}
		}
	}
}