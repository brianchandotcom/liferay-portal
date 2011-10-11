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

package com.liferay.portalweb.stagingcommunity.documentlibrary.document.publishtolivenowdldocumentnopagesdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveNowDLDocumentNoPagesDockTest extends BaseTestCase {
	public void testPublishToLiveNowDLDocumentNoPagesDock()
		throws Exception {
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
						if (selenium.isElementPresent("link=Site Name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Document Library Test Page",
					RuntimeVariables.replace("Document Library Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[@class='blue yui3-skin-sam live-view controls-visible signed-in public-page dockbar-ready staging-ready']"));
				assertFalse(selenium.isElementPresent(
						"//body[@class='blue yui3-skin-sam staging local-staging controls-visible signed-in public-page dockbar-ready staging-ready']"));
				assertEquals(RuntimeVariables.replace(
						"There are no documents or media files in this folder."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("DL Document Title"));
				assertTrue(selenium.isPartialText("//li[2]/span/a", "Staging"));
				selenium.clickAt("//li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[@class='blue yui3-skin-sam staging local-staging controls-visible signed-in public-page dockbar-ready staging-ready']"));
				assertFalse(selenium.isElementPresent(
						"//body[@class='blue yui3-skin-sam live-view controls-visible signed-in public-page dockbar-ready staging-ready']"));
				selenium.clickAt("link=Document Library Test Page",
					RuntimeVariables.replace("Document Library Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("DL Document Title"),
					selenium.getText("//a[@class='document-link']"));
				assertTrue(selenium.isElementPresent(
						"//a[@id='_170_0publishNowLink']"));
				selenium.clickAt("//a[@id='_170_0publishNowLink']",
					RuntimeVariables.replace("Publish to Live Now"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@value='Change Selection']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Change Selection']",
					RuntimeVariables.replace("Change Selection"));

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
						"Note that selecting no pages from the tree reverts to implicit selection of all pages."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				selenium.clickAt("//li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//li/ul/li[1]/div/div[4]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Document Library Test Page"),
					selenium.getText("//li/ul/li[1]/div/div[4]"));
				assertTrue(selenium.isElementPresent(
						"//div[@class='aui-helper-clearfix aui-tree-node-content aui-tree-data-content aui-tree-node-content aui-tree-node-io-content aui-tree-node-check-content aui-tree-node-task-content lfr-root-node aui-tree-node-selected aui-tree-expanded']"));
				assertTrue(selenium.isElementPresent(
						"//div[@class='aui-helper-clearfix aui-tree-node-content aui-tree-data-content aui-tree-node-content aui-tree-node-io-content aui-tree-node-check-content aui-tree-node-task-content aui-tree-collapsed']"));
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"There are no selected pages. All pages will therefore be exported.")
												.equals(selenium.getText(
										"//div[@class='portlet-msg-info']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"There are no selected pages. All pages will therefore be exported."),
					selenium.getText("//div[@class='portlet-msg-info']"));

				boolean documentLibraryVisible = selenium.isVisible(
						"_88_PORTLET_DATA_20Checkbox");

				if (documentLibraryVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//input[@id='_88_rangeAll']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@id='_88_rangeAll']",
					RuntimeVariables.replace("All"));

				boolean documentLibraryChecked = selenium.isChecked(
						"_88_PORTLET_DATA_20Checkbox");

				if (documentLibraryChecked) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));
				selenium.clickAt("//input[@id='_88_PORTLET_DATA_20Checkbox']",
					RuntimeVariables.replace("Document Library"));
				assertTrue(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Publish']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));

			case 100:
				label = -1;
			}
		}
	}
}