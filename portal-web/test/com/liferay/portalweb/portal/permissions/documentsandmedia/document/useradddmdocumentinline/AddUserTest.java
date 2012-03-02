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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.useradddmdocumentinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserTest extends BaseTestCase {
	public void testAddUser() throws Exception {
		selenium.open("/web/guest/home/");
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
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("label=Mr."));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("usersn1"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("userea1@liferay.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("userfn1"));
		selenium.type("//input[@id='_125_middleName']",
			RuntimeVariables.replace("usermn1"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("userln1"));
		selenium.select("//select[@id='_125_suffixId']",
			RuntimeVariables.replace("label=Phd."));
		selenium.select("//select[@id='_125_birthdayMonth']",
			RuntimeVariables.replace("label=April"));
		selenium.select("//select[@id='_125_birthdayDay']",
			RuntimeVariables.replace("label=10"));
		selenium.select("//select[@id='_125_birthdayYear']",
			RuntimeVariables.replace("label=1986"));
		selenium.select("//select[@id='_125_male']",
			RuntimeVariables.replace("label=Male"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("usersn1",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("userea1@liferay.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("userfn1",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("usermn1",
			selenium.getValue("//input[@id='_125_middleName']"));
		assertEquals("userln1",
			selenium.getValue("//input[@id='_125_lastName']"));
	}
}