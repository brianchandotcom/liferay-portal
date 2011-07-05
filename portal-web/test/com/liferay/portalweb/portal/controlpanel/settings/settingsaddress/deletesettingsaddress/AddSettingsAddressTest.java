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

package com.liferay.portalweb.portal.controlpanel.settings.settingsaddress.deletesettingsaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAddressTest extends BaseTestCase {
	public void testAddSettingsAddress() throws Exception {
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
		selenium.type("_130_addressStreet1_0",
			RuntimeVariables.replace("123. Liferay Ln."));
		selenium.saveScreenShotAndSource();
		selenium.type("_130_addressCity0",
			RuntimeVariables.replace("Rays of Light"));
		selenium.saveScreenShotAndSource();
		selenium.type("_130_addressZip0", RuntimeVariables.replace("12345"));
		selenium.saveScreenShotAndSource();
		selenium.select("_130_addressCountryId0",
			RuntimeVariables.replace("label=United States"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText("_130_addressRegionId0", "California")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.select("_130_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		selenium.select("_130_addressTypeId0",
			RuntimeVariables.replace("label=Billing"));
		selenium.clickAt("_130_addressMailing0Checkbox",
			RuntimeVariables.replace(""));
		selenium.clickAt("_130_addressPrimary0", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_130_addressStreet1_0")) {
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
			selenium.getText("//section/div/div/div/div"));
		assertEquals("123. Liferay Ln.",
			selenium.getValue("_130_addressStreet1_0"));
		assertEquals("Rays of Light", selenium.getValue("_130_addressCity0"));
		assertEquals("12345", selenium.getValue("_130_addressZip0"));
		assertEquals("Billing", selenium.getSelectedLabel("_130_addressTypeId0"));
		assertEquals("United States",
			selenium.getSelectedLabel("_130_addressCountryId0"));
		assertEquals("California",
			selenium.getSelectedLabel("_130_addressRegionId0"));
	}
}