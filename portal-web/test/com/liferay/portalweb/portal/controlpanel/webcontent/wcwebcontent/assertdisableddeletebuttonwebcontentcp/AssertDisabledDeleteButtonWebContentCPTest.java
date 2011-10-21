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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.assertdisableddeletebuttonwebcontentcp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertDisabledDeleteButtonWebContentCPTest extends BaseTestCase {
	public void testAssertDisabledDeleteButtonWebContentCP()
		throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//td[3]/a"));
		assertFalse(selenium.isChecked("//input[@name='_15_allRowIds']"));
		assertFalse(selenium.isChecked("//input[@name='_15_rowIds']"));
		assertTrue(selenium.isVisible(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Delete' and @disabled='']"));
		selenium.clickAt("//input[@name='_15_allRowIds']",
			RuntimeVariables.replace("Select All"));
		assertTrue(selenium.isChecked("//input[@name='_15_allRowIds']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (!selenium.isElementPresent(
							"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Delete' and @disabled='']"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		selenium.clickAt("//input[@name='_15_allRowIds']",
			RuntimeVariables.replace("Select All"));
		assertFalse(selenium.isChecked("//input[@name='_15_allRowIds']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Delete' and @disabled='']"));
		selenium.clickAt("//input[@name='_15_rowIds']",
			RuntimeVariables.replace("Row Entry Check Box"));
		assertTrue(selenium.isChecked("//input[@name='_15_rowIds']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (!selenium.isElementPresent(
							"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertFalse(selenium.isElementPresent(
				"//input[@value='Delete' and @disabled='']"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		selenium.clickAt("//input[@name='_15_rowIds']",
			RuntimeVariables.replace("Row Entry Check Box"));
		assertFalse(selenium.isChecked("//input[@name='_15_rowIds']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//span[contains(@class,'aui-button-disabled')]/span/input[@value='Delete']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Delete' and @disabled='']"));
	}
}