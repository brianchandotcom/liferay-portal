/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.settings.phonenumber.addsettingsphonenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsPhoneNumberTest extends BaseTestCase {
	public void testAddSettingsPhoneNumber() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_130_phoneNumbersLink']",
				"Phone Numbers"));
		selenium.clickAt("//a[@id='_130_phoneNumbersLink']",
			RuntimeVariables.replace("Phone Numbers"));
		selenium.type("//input[@id='_130_phoneNumber0']",
			RuntimeVariables.replace("123-123-1234"));
		selenium.type("//input[@id='_130_phoneExtension0']",
			RuntimeVariables.replace("123"));
		selenium.select("//select[@id='_130_phoneTypeId0']",
			RuntimeVariables.replace("label=Other"));
		selenium.clickAt("//input[@id='_130_phonePrimary0']",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("123-123-1234",
			selenium.getValue("//input[@id='_130_phoneNumber0']"));
		assertEquals("123",
			selenium.getValue("//input[@id='_130_phoneExtension0']"));
		assertEquals("Other",
			selenium.getSelectedLabel("//select[@id='_130_phoneTypeId0']"));
		assertTrue(selenium.isChecked("//input[@id='_130_phonePrimary0']"));
	}
}