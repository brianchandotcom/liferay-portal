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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentLocalizedTest extends BaseTestCase {
	public void testAddWebContentLocalized() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
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
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("link=Web Content"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//div/span/ul/li/strong/a/span"));
		selenium.clickAt("//div/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test Localized Structure"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Hello World Localized Article"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked("//span[2]/div/span/span/span/input[2]"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//span[2]/div/span/span/span/input[2]",
			RuntimeVariables.replace("Localizable"));
		assertTrue(selenium.isChecked("//span[2]/div/span/span/span/input[2]"));
		selenium.saveScreenShotAndSource();
		assertFalse(selenium.isChecked(
				"//li[2]/span[2]/div/span/span/span/input[2]"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//li[2]/span[2]/div/span/span/span/input[2]",
			RuntimeVariables.replace("Localizable"));
		assertTrue(selenium.isChecked(
				"//li[2]/span[2]/div/span/span/span/input[2]"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save as Draft']",
			RuntimeVariables.replace("Save as Draft"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.type("//input[@id='page-name']",
			RuntimeVariables.replace("Hello World Page Name"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='page-description']",
			RuntimeVariables.replace("Hello World Page Description"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//a[@id='_15_changeLanguageId']",
			RuntimeVariables.replace("Change"));
		assertTrue(selenium.getConfirmation()
						   .matches("^Changing the default language will delete all unsaved content. Do you want to proceed[\\s\\S]$"));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@name='_15_defaultLanguageId']",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);
		selenium.type("//input[@id='page-name']",
			RuntimeVariables.replace("\u4e16\u754c\u60a8\u597d Page Name"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='page-description']",
			RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Hello World Localized Article")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"link=Hello World Localized Article"));
	}
}