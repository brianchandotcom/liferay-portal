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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.enterreservedscreenname;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSettingsConfigurationTest extends BaseTestCase {
	public void testTearDownSettingsConfiguration() throws Exception {
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
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_name']",
			RuntimeVariables.replace("Liferay"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_virtualHostname']",
			RuntimeVariables.replace("localhost"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_mx']",
			RuntimeVariables.replace("liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_homeURL']",
			RuntimeVariables.replace("Home URL"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@name='_130_settings--default.landing.page.path--']",
			RuntimeVariables.replace("Default Landing Page"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@name='_130_settings--default.logout.page.path--']",
			RuntimeVariables.replace("Default Logout Page"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_legalName']",
			RuntimeVariables.replace("Liferay, Inc."));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_sicCode']",
			RuntimeVariables.replace("SIC Code"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_legalId']",
			RuntimeVariables.replace("Legal ID"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_tickerSymbol']",
			RuntimeVariables.replace("Ticker Symbol"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_legalType']",
			RuntimeVariables.replace("Legal Type"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_industry']",
			RuntimeVariables.replace("Industry"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_130_type']",
			RuntimeVariables.replace("Type"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//a[@id='_130_usersLink']", "Users"));
		selenium.clickAt("//a[@id='_130_usersLink']",
			RuntimeVariables.replace("Users"));
		selenium.clickAt("link=Reserved Credentials",
			RuntimeVariables.replace("Reserved Credentials"));
		selenium.type("//textarea[@name='_130_settings--admin.reserved.screen.names--']",
			RuntimeVariables.replace("Screen Names"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@name='_130_settings--admin.reserved.email.addresses--']",
			RuntimeVariables.replace("Email Addresses"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Default User Associations",
			RuntimeVariables.replace("Default User Associations"));
		selenium.type("//textarea[@name='_130_settings--admin.default.group.names--']",
			RuntimeVariables.replace("Sites"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@name='_130_settings--admin.default.role.names--']",
			RuntimeVariables.replace("Power User\nUser"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@name='_130_settings--admin.default.user.group.names--']",
			RuntimeVariables.replace("User Groups"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//a[@id='_130_mailHostNamesLink']",
				"Mail Host Names"));
		selenium.clickAt("//a[@id='_130_mailHostNamesLink']",
			RuntimeVariables.replace("Mail Host Names"));
		selenium.type("//textarea[@name='_130_settings--admin.mail.host.names--']",
			RuntimeVariables.replace("Mail Host Names"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_emailNotificationsLink']", "Email Notifications"));
		selenium.clickAt("//a[@id='_130_emailNotificationsLink']",
			RuntimeVariables.replace("Email Notifications"));
		selenium.type("//input[@name='_130_settings--admin.email.from.name--']",
			RuntimeVariables.replace("Joe Bloggs"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@name='_130_settings--admin.email.from.address--']",
			RuntimeVariables.replace("test@liferay.com"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
	}
}