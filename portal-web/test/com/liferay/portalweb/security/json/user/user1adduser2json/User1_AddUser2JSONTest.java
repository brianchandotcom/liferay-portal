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

package com.liferay.portalweb.security.json.user.user1adduser2json;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User1_AddUser2JSONTest extends BaseTestCase {
	public void testUser1_AddUser2JSON() throws Exception {
		selenium.open("/api/jsonws");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='serviceSearch']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='serviceSearch']",
			RuntimeVariables.replace("add-user"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-panel-content']/ul/li/a[contains(@href,'add-user-26')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("add-user"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/ul/li/a[contains(@href,'add-user-26')]"));
		selenium.clickAt("//div[@class='lfr-panel-content']/ul/li/a[contains(@href,'add-user-26')]",
			RuntimeVariables.replace("add-user"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='companyId']",
			RuntimeVariables.replace("1"));
		selenium.type("//input[@name='screenName']",
			RuntimeVariables.replace("usersn2"));
		selenium.clickAt("//input[@value='false' and @name='autoScreenName']",
			RuntimeVariables.replace("Auto Screen Name False"));
		selenium.type("//input[@name='emailAddress']",
			RuntimeVariables.replace("userea2@liferay.com"));
		selenium.type("//input[@name='facebookId']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@name='locale']",
			RuntimeVariables.replace("en_US"));
		selenium.type("//input[@name='firstName']",
			RuntimeVariables.replace("userfn2"));
		selenium.type("//input[@name='lastName']",
			RuntimeVariables.replace("userln2"));
		selenium.type("//input[@name='prefixId']", RuntimeVariables.replace("0"));
		selenium.type("//input[@name='suffixId']", RuntimeVariables.replace("0"));
		selenium.type("//input[@name='birthdayMonth']",
			RuntimeVariables.replace("7"));
		selenium.type("//input[@name='birthdayDay']",
			RuntimeVariables.replace("20"));
		selenium.type("//input[@name='birthdayYear']",
			RuntimeVariables.replace("1888"));
		selenium.type("//input[@name='roleIds']", RuntimeVariables.replace("15"));
		selenium.clickAt("//input[@value='false' and @name='sendEmail']",
			RuntimeVariables.replace("Send Email False"));
		selenium.clickAt("//input[@value='Invoke']",
			RuntimeVariables.replace("Invoke"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[contains(@class,'tab-active')]/span/a[contains(.,'Result')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Result"),
			selenium.getText(
				"//li[contains(@class,'tab-active')]/span/a[contains(.,'Result')]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//pre[@class='lfr-code-block']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']", "{"));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"exception\":\"com.liferay.portal.security.auth.PrincipalException\""));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']", "}"));
	}
}