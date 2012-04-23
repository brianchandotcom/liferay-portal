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

package com.liferay.portalweb.portal.dbupgrade.sampledata610.documentlibrary.shortcut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddShortcutTest extends BaseTestCase {
	public void testAddShortcut() throws Exception {
		selenium.open("/web/document-library-shortcut-community/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Document Library Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace("Document Library Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Test2 Folder2"),
			selenium.getText("//div[3]/a/span[2]"));
		selenium.clickAt("//div[3]/a/span[2]",
			RuntimeVariables.replace("Test2 Folder2"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[@class='folder selected']/a/span[contains(.,'Test2 Folder2')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Test2 Folder2"),
			selenium.getText(
				"//li[@class='folder selected']/a/span[contains(.,'Test2 Folder2')]"));
		selenium.clickAt("//h1[@class='portlet-title']",
			RuntimeVariables.replace("Documents and Media"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//div[@id='column-1_shim']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a",
			RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Shortcut')]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Shortcut')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Shortcut')]/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("//div/span[2]/span/input",
			RuntimeVariables.replace("Select Site"));
		selenium.waitForPopUp("Document Library",
			RuntimeVariables.replace("30000"));
		selenium.selectWindow("Document Library");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"link=Document Library Shortcut Community")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Document Library Shortcut Community",
			RuntimeVariables.replace("Document Library Shortcut Community"));
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span[@id='_20_toGroupName']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Document Library Shortcut Community"),
			selenium.getText("//span[@id='_20_toGroupName']"));
		selenium.clickAt("//input[@id='_20_selectToFileEntryButton']",
			RuntimeVariables.replace("Select Document"));
		selenium.waitForPopUp("Document Library",
			RuntimeVariables.replace("30000"));
		selenium.selectWindow("Document Library");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Test1 Folder1")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click(RuntimeVariables.replace("link=Test1 Folder1"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Test1 Document1.txt\nThis is test1 document1."),
			selenium.getText("//td[1]/a"));
		selenium.click("//td[1]/a");
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span[@id='_20_toFileEntryTitle']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//span[@id='_20_toFileEntryTitle']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
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
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//div[2]/a/span[2]"));
	}
}