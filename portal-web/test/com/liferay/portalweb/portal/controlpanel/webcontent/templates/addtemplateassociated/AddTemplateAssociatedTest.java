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

package com.liferay.portalweb.portal.controlpanel.webcontent.templates.addtemplateassociated;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateAssociatedTest extends BaseTestCase {
	public void testAddTemplateAssociated() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Templates", RuntimeVariables.replace("Templates"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Template']",
			RuntimeVariables.replace("Add Template"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_15_name_en_US']",
			RuntimeVariables.replace("Web Content Template Name Associated"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_15_description_en_US']",
			RuntimeVariables.replace(
				"Web Content Template Description Associated"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace(
				"Web Content Structure Name\nWeb Content Structure Description"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace(
				"Web Content Structure Name\nWeb Content Structure Description"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Web Content Structure Name")
										.equals(selenium.getText(
								"//a[@id='_15_structureName']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Web Content Structure Name"),
			selenium.getText("//a[@id='_15_structureName']"));
		selenium.clickAt("//input[@value='Launch Editor']",
			RuntimeVariables.replace("Launch Editor"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//textarea[@id='_15_xslContent']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_15_xslContent']",
			RuntimeVariables.replace(
				"<h1>$title.getData()</h1>\n\n<a href=\"$ltp.getUrl()\">See Page</a>"));
		selenium.saveScreenShotAndSource();
		selenium.click("//input[@value='Update']");
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isElementPresent("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace(
				"Web Content Template Name Associated"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"Web Content Template Description Associated"),
			selenium.getText("//tr[4]/td[4]/a"));
	}
}