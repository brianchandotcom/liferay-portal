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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.gmailviewpmmessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOFriendGmailTest extends BaseTestCase {
	public void testAddUserSOFriendGmail() throws Exception {
		selenium.open("/user/joebloggs/home/");

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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace("//a"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("New User"),
			selenium.getText("//div[2]/h1/span"));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("socialofficefriendsn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("liferay.qa.testing.trunk@gmail.com"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("socialofficefriendfn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_middleName']",
			RuntimeVariables.replace("socialofficefriendmn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("socialofficefriendln"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("socialofficefriendsn",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("liferay.qa.testing.trunk@gmail.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("socialofficefriendfn",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("socialofficefriendmn",
			selenium.getValue("//input[@id='_125_middleName']"));
		assertEquals("socialofficefriendln",
			selenium.getValue("//input[@id='_125_lastName']"));
		selenium.clickAt("//a[@id='_125_passwordLink']",
			RuntimeVariables.replace("Password"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_125_password1']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_password1']",
			RuntimeVariables.replace("password"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_password2']",
			RuntimeVariables.replace("password"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}