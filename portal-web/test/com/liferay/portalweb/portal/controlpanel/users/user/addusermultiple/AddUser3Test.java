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

package com.liferay.portalweb.portal.controlpanel.users.user.addusermultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUser3Test extends BaseTestCase {
	public void testAddUser3() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
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
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("label=Mr."));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("selenium03"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("test03@selenium.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("selen03"));
		selenium.type("//input[@id='_125_middleName']",
			RuntimeVariables.replace("lenn"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("nium03"));
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("label=Ms."));
		selenium.select("//select[@id='_125_birthdayMonth']",
			RuntimeVariables.replace("label=August"));
		selenium.select("//select[@id='_125_birthdayDay']",
			RuntimeVariables.replace("label=5"));
		selenium.select("//select[@id='_125_birthdayYear']",
			RuntimeVariables.replace("label=1991"));
		selenium.select("//select[@id='_125_male']",
			RuntimeVariables.replace("label=Female"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");

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
		assertEquals("selenium03",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("test03@selenium.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("selen03",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("lenn", selenium.getValue("//input[@id='_125_middleName']"));
		assertEquals("nium03", selenium.getValue("//input[@id='_125_lastName']"));
	}
}