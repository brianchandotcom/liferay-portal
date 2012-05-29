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

package com.liferay.portalweb.security.json.user.guestadduserjson;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AddUserJSONTest extends BaseTestCase {
	public void testGuest_AddUserJSON() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		selenium.open("/api/jsonws");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='serviceSearch']",
			RuntimeVariables.replace("add-user"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xpath=(//li[@class='lfr-api-signature']/a)[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("add-user"),
			selenium.getText("xpath=(//li[@class='lfr-api-signature']/a)[2]"));
		selenium.clickAt("xpath=(//li[@class='lfr-api-signature']/a)[2]",
			RuntimeVariables.replace("add-user"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='companyId']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@id='fieldFalse1']",
			RuntimeVariables.replace("false"));
		selenium.type("//input[@name='password1']",
			RuntimeVariables.replace("password"));
		selenium.type("//input[@name='password2']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@id='fieldFalse4']",
			RuntimeVariables.replace("false"));
		selenium.type("//input[@name='screenName']",
			RuntimeVariables.replace("usersn"));
		selenium.type("//input[@name='emailAddress']",
			RuntimeVariables.replace("userea@liferay.com"));
		selenium.type("//input[@name='facebookId']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@name='locale']",
			RuntimeVariables.replace("en_US"));
		selenium.type("//input[@name='firstName']",
			RuntimeVariables.replace("userfn"));
		selenium.type("//input[@name='lastName']",
			RuntimeVariables.replace("userln"));
		selenium.type("//input[@name='prefixId']", RuntimeVariables.replace("0"));
		selenium.type("//input[@name='suffixId']", RuntimeVariables.replace("0"));
		selenium.type("//input[@name='birthdayMonth']",
			RuntimeVariables.replace("7"));
		selenium.type("//input[@name='birthdayDay']",
			RuntimeVariables.replace("20"));
		selenium.type("//input[@name='birthdayYear']",
			RuntimeVariables.replace("1888"));
		selenium.type("//input[@name='roleIds']", RuntimeVariables.replace("15"));
		selenium.clickAt("//input[@id='fieldFalse24']",
			RuntimeVariables.replace("False"));
		selenium.clickAt("//input[@value='Invoke']",
			RuntimeVariables.replace("Invoke"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace(
				"{\n \"exception\": \"Public access denied\"\n}"),
			selenium.getText("//pre[@id='serviceOutput']"));
	}
}