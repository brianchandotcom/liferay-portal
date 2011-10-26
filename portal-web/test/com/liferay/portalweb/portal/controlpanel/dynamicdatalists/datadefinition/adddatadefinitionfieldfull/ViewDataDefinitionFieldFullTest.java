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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinitionfieldfull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDataDefinitionFieldFullTest extends BaseTestCase {
	public void testViewDataDefinitionFieldFull() throws Exception {
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//a[@id='_167_manageDDMStructuresLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
			selenium.getText("//a[@id='_167_manageDDMStructuresLink']"));
		selenium.clickAt("//a[@id='_167_manageDDMStructuresLink']",
			RuntimeVariables.replace("Manage Data Definitions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[@title='Actions']/ul/li/strong/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Data Definition Field Full"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[1]/div/label"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[2]/div/label"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[3]/div/label"));
		assertEquals(RuntimeVariables.replace("Document Library"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[4]/div/label"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[5]/div/label"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[6]/div/label"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[7]/div/label"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[8]/div/label"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[9]/div/label"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[10]/div/label"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[@class='aui-diagram-builder-drop-container']/div[11]/div/label"));
		selenium.selectFrame("relative=top");
	}
}