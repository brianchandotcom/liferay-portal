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

package com.liferay.portalweb.portal.controlpanel.settings.settingsphonenumber.deletesettingsphonenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsPhoneNumberTest extends BaseTestCase {
	public void testAddSettingsPhoneNumber() throws Exception {
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
		assertTrue(selenium.isPartialText("//a[@id='_130_phoneNumbersLink']",
				"Phone Numbers"));
		selenium.clickAt("//a[@id='_130_phoneNumbersLink']",
			RuntimeVariables.replace("Phone Numbers"));
		selenium.type("_130_phoneNumber0",
			RuntimeVariables.replace("123-123-1234"));
		selenium.saveScreenShotAndSource();
		selenium.type("_130_phoneExtension0", RuntimeVariables.replace("123"));
		selenium.saveScreenShotAndSource();
		selenium.select("_130_phoneTypeId0",
			RuntimeVariables.replace("label=Other"));
		selenium.clickAt("_130_phonePrimary0", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div"));
		assertEquals("1231231234", selenium.getValue("_130_phoneNumber0"));
		assertEquals("123", selenium.getValue("_130_phoneExtension0"));
		assertEquals("Other", selenium.getSelectedLabel("_130_phoneTypeId0"));
		assertTrue(selenium.isChecked("_130_phonePrimary0"));
		selenium.saveScreenShotAndSource();
	}
}