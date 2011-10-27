/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.imagegallery.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertImportLARTest extends BaseTestCase {
	public void testAssertImportLAR() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace("Image Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		Thread.sleep(5000);
		assertTrue(selenium.isChecked("//input[@id='_86_showActionsCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_showFolderMenuCheckbox']"));
		assertTrue(selenium.isChecked("//input[@id='_86_showTabsCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_showFoldersSearchCheckbox']"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace("Image Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder1 Name"),
			selenium.getText("xPath=(//span[@class='image-title'])[1]"));
		assertEquals(RuntimeVariables.replace("MG Folder2 Name"),
			selenium.getText("xPath=(//span[@class='image-title'])[2]"));
		selenium.clickAt("xPath=(//span[@class='image-title'])[1]",
			RuntimeVariables.replace("MG Folder1 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder1 Subfolder Name"),
			selenium.getText("xPath=(//span[@class='image-title'])[1]"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace("Image Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder2 Name"),
			selenium.getText("xPath=(//span[@class='image-title'])[2]"));
		selenium.clickAt("xPath=(//span[@class='image-title'])[2]",
			RuntimeVariables.replace("MG Folder2 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder2 Subfolder Name"),
			selenium.getText("xPath=(//span[@class='image-title'])[1]"));
		selenium.clickAt("xPath=(//span[@class='image-title'])[1]",
			RuntimeVariables.replace("MG Folder2 Subfolder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder2 Subfolder Name"),
			selenium.getText("//div/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//img[@alt='MG Folder2 Subfolder Image Title - MG Folder2 Subfolder Image Description']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//img[@alt='MG Folder2 Subfolder Image Title - MG Folder2 Subfolder Image Description']"));
		assertEquals(RuntimeVariables.replace(
				"MG Folder2 Subfolder Image Title"),
			selenium.getText("//span[@class='image-title']"));
	}
}