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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.delete;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginUsersTest extends BaseTestCase {
	public void testLoginUsers() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Welcome", RuntimeVariables.replace("Welcome"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@id='_58_login']");
		selenium.type("//input[@id='_58_login']",
			RuntimeVariables.replace("member@liferay.com"));
		selenium.type("//input[@id='_58_password']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace("Sign In"));
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace("I Agree"));
		selenium.waitForVisible("//input[@id='password1']");
		selenium.type("//input[@id='password1']",
			RuntimeVariables.replace("test"));
		selenium.type("//input[@id='password2']",
			RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//input[@id='reminderQueryAnswer']");
		selenium.type("//input[@id='reminderQueryAnswer']",
			RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForTextPresent("You are signed in as Member Liferay.");
		assertTrue(selenium.isTextPresent(
				"You are signed in as Member Liferay."));
		selenium.waitForVisible("link=Sign Out");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace("Sign Out"));
		selenium.waitForPageToLoad("30000");
	}
}