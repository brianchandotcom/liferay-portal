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

package com.liferay.portalweb.demo.devcon6100.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_ViewPrivatePagesSiteBWCTest extends BaseTestCase {
	public void testSA_ViewPrivatePagesSiteBWC() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.openWindow("http://www.baker.com:8080",
					RuntimeVariables.replace("home"));
				selenium.waitForPopUp("home", RuntimeVariables.replace(""));
				selenium.selectWindow("home");
				Thread.sleep(5000);
				Thread.sleep(5000);
				assertTrue(selenium.isPartialText(
						"//a[@class='user-fullname use-dialog']", "Bruno Admin"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[@class='logo default-logo']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible("//a[@class='logo default-logo']"));
				assertTrue(selenium.isElementPresent("//img[@height='156']"));
				assertTrue(selenium.isElementPresent("//img[@width='320']"));
				assertFalse(selenium.isElementPresent(
						"//a[@class='logo custom-logo']"));
				assertTrue(selenium.isElementPresent(
						"//body[@class='blue yui3-skin-sam controls-visible signed-in private-page site dockbar-ready']"));
				assertFalse(selenium.isElementPresent(
						"//body[@class='green yui3-skin-sam controls-visible signed-in private-page site dockbar-ready']"));
				assertTrue(selenium.isVisible("link=Accommodations"));
				selenium.clickAt("link=Accommodations",
					RuntimeVariables.replace("Accommodations"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Accommodations"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//footer[@id='footer']"));
				assertTrue(selenium.isVisible("link=Maps"));
				selenium.clickAt("link=Maps", RuntimeVariables.replace("Maps"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Maps"),
					selenium.getText("//nav/ul/li[3]/span/a"));
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//footer[@id='footer']"));
				assertFalse(selenium.isElementPresent("link=Home"));
				assertFalse(selenium.isElementPresent("link=Arenas"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("World Cup - Brazil 2014"),
					selenium.getText("//strong/a/span"));
				assertEquals(RuntimeVariables.replace("Site Settings"),
					selenium.getText("link=Site Settings"));
				selenium.open("http://www.baker.com:8080");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//nav[@id='navigation']",
					RuntimeVariables.replace("Navigation"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//a[@id='addPage']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//a[@id='addPage']",
					RuntimeVariables.replace("Add Page"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@type='text']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@type='text']",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.clickAt("//button[@id='save']",
					RuntimeVariables.replace("Save"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Web Content Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isPartialText(
						"//a[@id='_145_addApplication']", "More"));
				selenium.clickAt("//a[@id='_145_addApplication']",
					RuntimeVariables.replace("More"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@title='Web Content Display']/p/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[@title='Web Content Display']/p/a",
					RuntimeVariables.replace("Add"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//section")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible("//section"));
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//li[contains(@class,'manage-page')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//li[contains(@class,'manage-page')]/a",
					RuntimeVariables.replace("Manage Pages"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//iframe[@id='manageContentDialog']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame("//iframe[@id='manageContentDialog']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Private Pages")
												.equals(selenium.getText(
										"//a[@class='layout-tree']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean tree1Collapsed = selenium.isElementPresent(
						"//li[@id='layoutsTree_layoutId_0_plid_0']/div[contains(@class,'aui-tree-collapsed')]");

				if (!tree1Collapsed) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Accommodations")
												.equals(selenium.getText(
										"//li/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

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

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[3]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[3]"));
				selenium.click(RuntimeVariables.replace("//button[3]"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
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