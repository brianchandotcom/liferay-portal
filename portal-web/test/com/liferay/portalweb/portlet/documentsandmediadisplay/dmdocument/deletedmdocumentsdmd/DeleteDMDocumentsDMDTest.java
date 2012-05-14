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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.deletedmdocumentsdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDMDocumentsDMDTest extends BaseTestCase {
	public void testDeleteDMDocumentsDMD() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Documents and Media Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean dmDocument1Present = selenium.isElementPresent(
						"//span[@class='entry-title']");

				if (!dmDocument1Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to move this to the Recycle Bin?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean dmDocument2Present = selenium.isElementPresent(
						"//span[@class='entry-title']");

				if (!dmDocument2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to move this to the Recycle Bin?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean dmDocument3Present = selenium.isElementPresent(
						"//span[@class='entry-title']");

				if (!dmDocument3Present) {
					label = 4;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'objectsSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to move this to the Recycle Bin?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
			case 3:
			case 4:
				Thread.sleep(5000);
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[contains(@class,'control-panel')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Control Panel"),
					selenium.getText("//li[contains(@class,'control-panel')]/a"));
				selenium.clickAt("//li[contains(@class,'control-panel')]/a",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Recycle Bin"),
					selenium.getText("//a[@id='_160_portlet_182']"));
				selenium.clickAt("//a[@id='_160_portlet_182']",
					RuntimeVariables.replace("Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				Thread.sleep(5000);
				assertTrue(selenium.isVisible(
						"//input[@value='Empty the Recycle Bin']"));
				selenium.clickAt("//input[@value='Empty the Recycle Bin']",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to empty the Recycle Bin[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"The Recycle Bin is empty."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}