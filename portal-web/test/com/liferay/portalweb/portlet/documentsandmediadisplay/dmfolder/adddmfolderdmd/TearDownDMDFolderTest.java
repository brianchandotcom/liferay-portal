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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmfolder.adddmfolderdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDMDFolderTest extends BaseTestCase {
	public void testTearDownDMDFolder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Documents and Media Display Test Page",
					RuntimeVariables.replace(
						"Documents and Media Display Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean dmdFolder1Present = selenium.isElementPresent(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span");

				if (!dmdFolder1Present) {
					label = 2;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean dmdFolder2Present = selenium.isElementPresent(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span");

				if (!dmdFolder2Present) {
					label = 3;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean dmdFolder3Present = selenium.isElementPresent(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span");

				if (!dmdFolder3Present) {
					label = 4;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean dmdFolder4Present = selenium.isElementPresent(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span");

				if (!dmdFolder4Present) {
					label = 5;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

				boolean dmdFolder5Present = selenium.isElementPresent(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span");

				if (!dmdFolder5Present) {
					label = 6;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menuButton')]/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_delete')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_delete')]",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}