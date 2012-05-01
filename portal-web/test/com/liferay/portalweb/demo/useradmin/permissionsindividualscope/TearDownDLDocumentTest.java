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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDLDocumentTest extends BaseTestCase {
	public void testTearDownDLDocument() throws Exception {
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
				selenium.clickAt("//button[@title='Icon View']",
					RuntimeVariables.replace("Icon View"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//button[contains(@class,'aui-state-active') and @title='Icon View']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible(
						"//button[contains(@class,'aui-state-active') and @title='Icon View']"));

				boolean dmlDocumentPresent = selenium.isElementPresent(
						"//div[2]/a/span[1]/img");

				if (!dmlDocumentPresent) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				selenium.clickAt("//input[@id='_20_allRowIdsCheckbox']",
					RuntimeVariables.replace("All Entries Check Box"));
				assertTrue(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Move to the Recycle Bin')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Move to the Recycle Bin')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Move to the Recycle Bin')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to move the selected entries to the Recycle Bin[\\s\\S]$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				assertEquals(RuntimeVariables.replace(
						"There are no documents or media files in this folder."),
					selenium.getText(
						"//div[contains(@class,'portlet-msg-info')]"));
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[@id='_145_mySites']/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
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
				selenium.clickAt("link=Recycle Bin",
					RuntimeVariables.replace("Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean assetPresent = selenium.isElementPresent(
						"//input[@name='_182_rowIds']");

				if (!assetPresent) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_182_allRowIds']"));
				selenium.clickAt("//input[@name='_182_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_182_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Empty the Recycle Bin']"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to empty the Recycle Bin[\\s\\S]$"));

			case 3:
			case 100:
				label = -1;
			}
		}
	}
}