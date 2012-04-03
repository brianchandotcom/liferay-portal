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

package com.liferay.portalweb.portlet.documentlibrarydisplay.document.adddldocumentdld;

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
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
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
				selenium.clickAt("link=Documents and Media",
					RuntimeVariables.replace("Documents and Media"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean iconViewNotSelected = selenium.isElementPresent(
						"//button[contains(@class,'item-focused') and @title='Icon View']");

				if (iconViewNotSelected) {
					label = 2;

					continue;
				}

				selenium.clickAt("//button[@title='Icon View']",
					RuntimeVariables.replace("Icon View"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//button[contains(@class,'item-focused') and @title='Icon View']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:

				boolean dlDocumentPresent = selenium.isElementPresent(
						"//span[@class='document-thumbnail']/img");

				if (!dlDocumentPresent) {
					label = 3;

					continue;
				}

				Thread.sleep(5000);
				assertFalse(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				selenium.clickAt("//input[@id='_20_allRowIdsCheckbox']",
					RuntimeVariables.replace("All Entries Check Box"));
				assertTrue(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@id='_20_entries']/div[contains(@class,'selected')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

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
									"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-info']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"There are no documents or media files in this folder."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}