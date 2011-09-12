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

package com.liferay.portalweb.portlet.documentlibrary.folder.adddlsubfoldernameimagename;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDLFolderTest extends BaseTestCase {
	public void testTearDownDLFolder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Document Library Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Document Library Test Page",
					RuntimeVariables.replace("Document Library Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean dlFolder1Present = selenium.isElementPresent(
						"//div/a/span[1]/img");

				if (!dlFolder1Present) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//form/div[1]/div/span[1]/span/span/input[2]",
					RuntimeVariables.replace("Document Checkbox"));
				assertTrue(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[2]/ul/li/strong/a/span"));
				selenium.clickAt("//span[2]/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 2:

				boolean dlFolder2Present = selenium.isElementPresent(
						"//div/a/span[1]/img");

				if (!dlFolder2Present) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//form/div[1]/div/span[1]/span/span/input[2]",
					RuntimeVariables.replace("Document Checkbox"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[2]/ul/li/strong/a/span"));
				selenium.clickAt("//span[2]/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean dlFolder3Present = selenium.isElementPresent(
						"//div/a/span[1]/img");

				if (!dlFolder3Present) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//form/div[1]/div/span[1]/span/span/input[2]",
					RuntimeVariables.replace("Document Checkbox"));
				assertTrue(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[2]/ul/li/strong/a/span"));
				selenium.clickAt("//span[2]/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean dlFolder4Present = selenium.isElementPresent(
						"//div/a/span[1]/img");

				if (!dlFolder4Present) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//form/div[1]/div/span[1]/span/span/input[2]",
					RuntimeVariables.replace("Document Checkbox"));
				assertTrue(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[2]/ul/li/strong/a/span"));
				selenium.clickAt("//span[2]/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean dlFolder5Present = selenium.isElementPresent(
						"//div/a/span[1]/img");

				if (!dlFolder5Present) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//form/div[1]/div/span[1]/span/span/input[2]",
					RuntimeVariables.replace("Document Checkbox"));
				assertTrue(selenium.isChecked(
						"//form/div[1]/div/span[1]/span/span/input[2]"));
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[2]/ul/li/strong/a/span"));
				selenium.clickAt("//span[2]/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}