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
						"//td[contains(@id,'foldersSearchContainer_col-name_row-1')]");

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
					"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean dmdFolder2Present = selenium.isElementPresent(
						"//td[contains(@id,'foldersSearchContainer_col-name_row-1')]");

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
					"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean dmdFolder3Present = selenium.isElementPresent(
						"//td[contains(@id,'foldersSearchContainer_col-name_row-1')]");

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
					"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean dmdFolder4Present = selenium.isElementPresent(
						"//td[contains(@id,'foldersSearchContainer_col-name_row-1')]");

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
					"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean dmdFolder5Present = selenium.isElementPresent(
						"//td[contains(@id,'foldersSearchContainer_col-name_row-1')]");

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
					"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]"));
				selenium.clickAt("//a[contains(@id,'foldersSearchContainer_1_menu_move-to-the-recycle-bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				assertEquals(RuntimeVariables.replace(
						"There are no documents or media files in this folder."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Recycle Bin",
					RuntimeVariables.replace("Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean recycleBinPresent = selenium.isElementPresent(
						"//form[@id='_182_emptyForm']/a");

				if (!recycleBinPresent) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
					selenium.getText("//form[@id='_182_emptyForm']/a"));
				selenium.clickAt("//form[@id='_182_emptyForm']/a",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to empty the Recycle Bin[\\s\\S]$"));

			case 7:
			case 100:
				label = -1;
			}
		}
	}
}