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

package com.liferay.portalweb.portlet.assetpublisher.dldocument.viewconfigureportletcurrentdldocumentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewDLFolderDocumentAPActionsTest extends BaseTestCase {
	public void testAddNewDLFolderDocumentAPActions() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Asset Publisher Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Asset Publisher Test Page",
					RuntimeVariables.replace("Asset Publisher Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add New"),
					selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Add New']/ul/li/strong/a",
					RuntimeVariables.replace("Add New"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Document Library Document"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a",
					RuntimeVariables.replace("Document Library Document"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Select']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				selenium.waitForPopUp("folder",
					RuntimeVariables.replace("30000"));
				selenium.selectWindow("name=folder");
				Thread.sleep(5000);

				boolean choose1Present = selenium.isElementPresent(
						"//td[4]/input");

				if (choose1Present) {
					label = 2;

					continue;
				}

				selenium.close();
				selenium.selectWindow("null");

			case 2:

				boolean choose2Present = selenium.isElementPresent(
						"//td[4]/input");

				if (!choose2Present) {
					label = 3;

					continue;
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Choose']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//input[@value='Choose']");

			case 3:
				selenium.selectWindow("null");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("DM Folder Name")
												.equals(selenium.getText(
										"//a[@id='_20_folderName']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText("//a[@id='_20_folderName']"));
				selenium.type("//input[@id='_20_file']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\assetpublisher\\dependencies\\DLDocument.txt"));
				selenium.type("//input[@id='_20_title']",
					RuntimeVariables.replace("DM Folder Document Title"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("DM Folder Document Title")
												.equals(selenium.getText(
										"//h3[@class='asset-title']/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"DM Folder Document Title"),
					selenium.getText("//h3[@class='asset-title']/a"));
				assertEquals(RuntimeVariables.replace(
						"DM Folder Document Title"),
					selenium.getText(
						"//div[@class='asset-resource-info']/span/a/span"));

			case 100:
				label = -1;
			}
		}
	}
}