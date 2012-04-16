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

package com.liferay.portalweb.socialofficeprofile.mycontacts.mycontact.filtermcnullcontact;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOFollowerTest extends BaseTestCase {
	public void testAddUserSOFollower() throws Exception {
		selenium.open("/user/joebloggs/home/");
		loadRequiredJavaScriptModules();

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
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.click(RuntimeVariables.replace("//a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("New User"),
			selenium.getText("//div[2]/h1/span"));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("socialofficefollowersn"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("socialofficefollowerea@liferay.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("socialofficefollowerfn"));
		selenium.type("//input[@id='_125_middleName']",
			RuntimeVariables.replace("socialofficefollowermn"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("socialofficefollowerln"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("socialofficefollowersn",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("socialofficefollowerea@liferay.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("socialofficefollowerfn",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("socialofficefollowermn",
			selenium.getValue("//input[@id='_125_middleName']"));
		assertEquals("socialofficefollowerln",
			selenium.getValue("//input[@id='_125_lastName']"));
		selenium.clickAt("//a[@id='_125_passwordLink']",
			RuntimeVariables.replace("Password"));

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.type("//input[@id='_125_password1']",
			RuntimeVariables.replace("password"));
		selenium.type("//input[@id='_125_password2']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		assertEquals(RuntimeVariables.replace("Power User"),
			selenium.getText("//tr[3]/td"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//div[6]/span/a/span"));
		selenium.clickAt("//div[6]/span/a/span",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_125_keywords']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Social Office"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Social Office User"),
			selenium.getText("//a"));
		selenium.click("//a");
		selenium.selectWindow("null");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Social Office User")
										.equals(selenium.getText(
								"//table/tr/td"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social Office User"),
			selenium.getText("//table/tr/td"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}