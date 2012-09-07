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

package com.liferay.portalweb.security.json.user.adduserjson;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserJSONTest extends BaseTestCase {
	public void testAddUserJSON() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/api/jsonws");
		selenium.type("//input[@id='serviceSearch']",
			RuntimeVariables.replace("add-user"));
		selenium.waitForVisible(
			"//div[@class='lfr-panel-content']/ul/li/a[contains(@href,'add-user-26')]");
		assertEquals(RuntimeVariables.replace("add-user"),
			selenium.getText(
				"//div[@class='lfr-panel-content']/ul/li/a[contains(@href,'add-user-26')]"));
		selenium.clickAt("//div[@class='lfr-panel-content']/ul/li/a[contains(@href,'add-user-26')]",
			RuntimeVariables.replace("add-user"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='companyId']",
			RuntimeVariables.replace("1"));
		selenium.type("//input[@name='screenName']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='false' and @name='autoScreenName']",
			RuntimeVariables.replace("Auto Screen Name False"));
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
			RuntimeVariables.replace("0"));
		selenium.type("//input[@name='birthdayDay']",
			RuntimeVariables.replace("1"));
		selenium.type("//input[@name='birthdayYear']",
			RuntimeVariables.replace("1970"));
		selenium.type("//input[@name='roleIds']", RuntimeVariables.replace("15"));
		selenium.clickAt("//input[@value='false' and @name='sendEmail']",
			RuntimeVariables.replace("Send Email False"));
		selenium.clickAt("//input[@value='Invoke']",
			RuntimeVariables.replace("Invoke"));
		selenium.waitForVisible(
			"//li[contains(@class,'tab-active')]/span/a[contains(.,'Result')]");
		assertEquals(RuntimeVariables.replace("Result"),
			selenium.getText(
				"//li[contains(@class,'tab-active')]/span/a[contains(.,'Result')]"));
		selenium.waitForVisible("//pre[@class='lfr-code-block']");
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']", "{"));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"agreedToTermsOfUse\":false,"));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"companyId\":1,"));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"facebookId\":0,"));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"firstName\":\"userfn\","));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"greeting\":\"Welcome userfn userln!\","));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"lastName\":\"userln\","));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']",
				"\"screenName\":\"usersn\","));
		assertTrue(selenium.isPartialText("//pre[@class='lfr-code-block']", "}"));
	}
}