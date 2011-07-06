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

package com.liferay.portalweb.portal.controlpanel.settings.settingsaddress.addsettingsaddressmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAddress2Test extends BaseTestCase {
	public void testAddSettingsAddress2() throws Exception {
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
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Portal Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//a[@id='_130_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_130_addressesLink']",
			RuntimeVariables.replace("Addresses"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//span/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//span/button[1]", RuntimeVariables.replace("Add Row"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_addressStreet1_2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("_130_addressStreet1_2",
			RuntimeVariables.replace("123. Liferay Ln."));
		selenium.saveScreenShotAndSource();
		selenium.type("_130_addressCity2",
			RuntimeVariables.replace("Rays of Light"));
		selenium.saveScreenShotAndSource();
		selenium.type("_130_addressZip2", RuntimeVariables.replace("12345"));
		selenium.saveScreenShotAndSource();
		selenium.select("_130_addressTypeId2",
			RuntimeVariables.replace("label=Billing"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_addressStreet1_1")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals("123. Liferay Ln.",
			selenium.getValue("_130_addressStreet1_1"));
		assertEquals("Rays of Light", selenium.getValue("_130_addressCity1"));
		assertEquals("12345", selenium.getValue("_130_addressZip1"));
		assertEquals("Billing", selenium.getSelectedLabel("_130_addressTypeId1"));
	}
}