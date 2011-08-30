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

package com.liferay.portalweb.socialofficeprofile.profile.addusersoinstantmessenger;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOInstantMessengerTest extends BaseTestCase {
	public void testAddUserSOInstantMessenger() throws Exception {
		selenium.open("/user/joebloggs/home/");

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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("socialofficefriendfn"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("socialofficefriendfn"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("socialofficefriendfn"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
			selenium.getText("//div[2]/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_125_instantMessengerLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_instantMessengerLink']", "Instant Messenger"));
		selenium.clickAt("//a[@id='_125_instantMessengerLink']",
			RuntimeVariables.replace("Instant Messenger"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_125_aimSn']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_aimSn']",
			RuntimeVariables.replace("socialofficesn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_icqSn']",
			RuntimeVariables.replace("socialofficesn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_jabberSn']",
			RuntimeVariables.replace("socialofficesn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_msnSn']",
			RuntimeVariables.replace("socialofficesn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_skypeSn']",
			RuntimeVariables.replace("socialofficesn"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_125_ymSn']",
			RuntimeVariables.replace("socialofficesn"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

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
		assertEquals("socialofficesn",
			selenium.getValue("//input[@id='_125_aimSn']"));
		assertEquals("socialofficesn",
			selenium.getValue("//input[@id='_125_icqSn']"));
		assertEquals("socialofficesn",
			selenium.getValue("//input[@id='_125_jabberSn']"));
		assertEquals("socialofficesn",
			selenium.getValue("//input[@id='_125_msnSn']"));
		assertEquals("socialofficesn",
			selenium.getValue("//input[@id='_125_skypeSn']"));
		assertEquals("socialofficesn",
			selenium.getValue("//input[@id='_125_ymSn']"));
	}
}