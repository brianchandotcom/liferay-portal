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

package com.liferay.portalweb.plugins.samplelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeletePageTest extends BaseTestCase {
	public void testDeletePage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
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
				selenium.waitForVisible("//a[@class='layout-tree']");

				boolean welcomePresent = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcomePresent) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isVisible(
						"xPath=(//div[@id='_88_layoutsTreeOutput'])/ul/li/div/div[1]"));

			case 2:
				selenium.waitForText("//a[contains(text(),'Welcome')]",
					"Welcome");

				boolean page1Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page1Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForNotValue("//input[@id='_88_friendlyURL']",
					"/home");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				selenium.selectFrame("relative=top");
				selenium.waitForVisible("//iframe[@id='manageContentDialog']");
				selenium.selectFrame("//iframe[@id='manageContentDialog']");
				Thread.sleep(1000);
				selenium.waitForVisible("//a[@class='layout-tree']");
				assertTrue(selenium.isVisible(
						"xPath=(//div[@id='_88_layoutsTreeOutput'])/ul/li/div/div[1]"));
				selenium.waitForText("//a[contains(text(),'Welcome')]",
					"Welcome");

				boolean page2Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page2Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForNotValue("//input[@id='_88_friendlyURL']",
					"/home");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isVisible(
						"//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]"));
				selenium.waitForText("//a[contains(text(),'Welcome')]",
					"Welcome");

				boolean page3Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page3Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForNotValue("//input[@id='_88_friendlyURL']",
					"/home");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isVisible(
						"//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]"));
				selenium.waitForText("//a[contains(text(),'Welcome')]",
					"Welcome");

				boolean page4Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page4Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForNotValue("//input[@id='_88_friendlyURL']",
					"/home");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));
				assertTrue(selenium.isVisible(
						"//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]"));
				selenium.waitForText("//a[contains(text(),'Welcome')]",
					"Welcome");

				boolean page5Present = selenium.isElementPresent(
						"//li[2]/div/div[3]/a");

				if (!page5Present) {
					label = 7;

					continue;
				}

				selenium.clickAt("//li[2]/div/div[3]/a",
					RuntimeVariables.replace("Page Name"));
				selenium.waitForNotValue("//input[@id='_88_friendlyURL']",
					"/home");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]"));
				selenium.clickAt("//div[@class='lfr-header-row title']/div/div[2]/span/button/span[contains(.,'Delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected page[\\s\\S]$"));

			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				assertFalse(selenium.isTextPresent("link=Sample LAR Test Page"));

			case 100:
				label = -1;
			}
		}
	}
}