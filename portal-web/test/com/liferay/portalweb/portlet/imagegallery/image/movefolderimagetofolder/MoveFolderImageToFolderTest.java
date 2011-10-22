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

package com.liferay.portalweb.portlet.imagegallery.image.movefolderimagetofolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveFolderImageToFolderTest extends BaseTestCase {
	public void testMoveFolderImageToFolder() throws Exception {
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
		selenium.clickAt("xPath=(//span[@class='image-title'])[1]",
			RuntimeVariables.replace("MG Folder1 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder1 Name"),
			selenium.getText("//div/h1/span"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[2]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder1 Name"),
			selenium.getText("//a[@id='_31_parentFolderName']"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.waitForPopUp("folder", RuntimeVariables.replace("30000"));
		selenium.selectWindow("folder");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Images Home")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Images Home",
			RuntimeVariables.replace("Images Home"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder2 Name"),
			selenium.getText("//tr[4]/td/a"));
		selenium.click("xPath=(//input[@value='Choose'])[2]");
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("MG Folder2 Name")
										.equals(selenium.getText(
								"//a[@id='_31_parentFolderName']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("MG Folder2 Name"),
			selenium.getText("//a[@id='_31_parentFolderName']"));
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
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
		selenium.clickAt("xPath=(//span[@class='image-title'])[1]",
			RuntimeVariables.replace("MG Folder1 Name"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("MG Folder1 Subfolder Name"));
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
		assertEquals(RuntimeVariables.replace("MG Folder1 Subfolder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//img[@alt='Test1 Image1 - This is Test1 Image1.']"));
		selenium.clickAt("//img[@alt='Test1 Image1 - This is Test1 Image1.']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//img[@alt='Edit']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//img[@alt='Edit']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test1 Folder1"),
			selenium.getText("_31_folderName"));
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("folder", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=folder");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Images Home")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Images Home", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.click("//tr[4]/td[4]/input");
		selenium.selectWindow("null");
		assertEquals(RuntimeVariables.replace("Test2 Folder2"),
			selenium.getText("_31_folderName"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		assertFalse(selenium.isElementPresent(
				"//img[@alt='Test1 Image1 - This is Test1 Image1.']"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//tr[4]/td[1]/a/strong", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//img[@alt='Test1 Image1 - This is Test1 Image1.']"));
	}
}