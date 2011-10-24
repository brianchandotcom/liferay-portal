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

package com.liferay.portalweb.portlet.assetpublisher.dldocument.deletedlfolderdocumentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectExistingDLFolderDocumentAPActionsTest extends BaseTestCase {
	public void testSelectExistingDLFolderDocumentAPActions()
		throws Exception {
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
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));

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

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[@title='Select Existing']/ul/li/strong/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Select Existing"),
			selenium.getText("//span[@title='Select Existing']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Select Existing']/ul/li/strong/a",
			RuntimeVariables.replace("Select Existing"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[11]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Document Library Document"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[11]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[11]/a"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/a"));
		assertTrue(selenium.isElementPresent("//td[4]/a"));
		selenium.click(RuntimeVariables.replace("//td[1]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Document Library Document"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//td[2]/a"));
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
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//div[@class='asset-resource-info']/span/a/span"));
	}
}