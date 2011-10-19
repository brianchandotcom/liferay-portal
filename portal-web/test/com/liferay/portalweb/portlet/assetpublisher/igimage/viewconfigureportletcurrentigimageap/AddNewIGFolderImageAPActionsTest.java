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

package com.liferay.portalweb.portlet.assetpublisher.igimage.viewconfigureportletcurrentigimageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddNewIGFolderImageAPActionsTest extends BaseTestCase {
	public void testAddNewIGFolderImageAPActions() throws Exception {
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
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add New"),
			selenium.getText("//span[@title='Add New']/ul/li/strong/a"));
		selenium.click("//span[@title='Add New']/ul/li/strong/a");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[6]/a",
			RuntimeVariables.replace("Image"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Documents Home")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents Home",
			RuntimeVariables.replace("Documents Home"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/a/span[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("IG Folder Name"),
			selenium.getText("//div/a/span[2]"));
		selenium.clickAt("//div/a/span[2]",
			RuntimeVariables.replace("IG Folder Name"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[3]/span/span/ul/li/strong/a/span"));
		selenium.clickAt("//span[3]/span/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_20_edsl']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText("//a[@id='_20_edsl']"));
		selenium.click(RuntimeVariables.replace("//a[@id='_20_edsl']"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("IG Folder Name")
										.equals(selenium.getText(
								"link=IG Folder Name"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("IG Folder Name"),
			selenium.getText("link=IG Folder Name"));
		selenium.type("//input[@id='_20_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\assetpublisher\\dependencies\\IGImage.jpg"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("IG Folder Image Name"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
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

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("IG Folder Image Name")
										.equals(selenium.getText(
								"//h3[@class='asset-title']/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isElementPresent(
				"//img[@class='asset-small-image']"));
	}
}