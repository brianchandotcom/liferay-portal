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

package com.liferay.portalweb.portal.controlpanel.users.user.adduserdisplaysettings;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserDisplaySettingsTest extends BaseTestCase {
	public void testAddUserDisplaySettings() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("selen01"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("selen01"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("selen01"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_125_displaySettingsLink']");
		selenium.clickAt("//a[@id='_125_displaySettingsLink']",
			RuntimeVariables.replace("Display Settings"));
		selenium.waitForVisible("//select[@name='_125_timeZoneId']");
		selenium.select("//select[@name='_125_timeZoneId']",
			RuntimeVariables.replace("label=(UTC -08:00) Pacific Standard Time"));
		selenium.type("//input[@id='_125_greeting']",
			RuntimeVariables.replace("Welcome Selenium01!"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("(UTC -08:00) Pacific Standard Time",
			selenium.getSelectedLabel("//select[@name='_125_timeZoneId']"));
		assertEquals("Welcome Selenium01!",
			selenium.getValue("//input[@id='_125_greeting']"));
	}
}