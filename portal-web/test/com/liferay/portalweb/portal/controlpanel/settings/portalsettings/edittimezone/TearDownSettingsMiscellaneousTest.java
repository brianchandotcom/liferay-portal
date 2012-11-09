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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.edittimezone;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSettingsMiscellaneousTest extends BaseTestCase {
	public void testTearDownSettingsMiscellaneous() throws Exception {
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
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_displaySettingsLink']", "Display Settings"));
		selenium.clickAt("//a[@id='_130_displaySettingsLink']",
			RuntimeVariables.replace("Display Settings"));
		selenium.waitForVisible("//select[@id='_130_languageId']");
		selenium.select("//select[@id='_130_languageId']",
			RuntimeVariables.replace("label=English (United States)"));
		assertTrue(selenium.isElementNotPresent(
				"//select[@id='_130_availableLanguageIds']/option"));
		selenium.select("//select[@name='_130_timeZoneId']",
			RuntimeVariables.replace("label=(UTC ) Coordinated Universal Time"));
		selenium.select("//select[@name='_130_settings--default.regular.theme.id--']",
			RuntimeVariables.replace("label=Classic"));
		selenium.select("//fieldset[3]/div/span[2]/span/span/select",
			RuntimeVariables.replace("label=Mobile"));
		selenium.select("//select[@name='_130_settings--control.panel.layout.regular.theme.id--']",
			RuntimeVariables.replace("label=Control Panel"));
		assertTrue(selenium.isPartialText("//a[@id='_130_googleAppsLink']",
				"Google Apps"));
		selenium.clickAt("//a[@id='_130_googleAppsLink']",
			RuntimeVariables.replace("Google Apps"));
		selenium.waitForVisible(
			"//input[@name='_130_settings--google.apps.username--']");
		selenium.type("//input[@name='_130_settings--google.apps.username--']",
			RuntimeVariables.replace(""));
		selenium.type("//input[@name='_130_settings--google.apps.password--']",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
	}
}