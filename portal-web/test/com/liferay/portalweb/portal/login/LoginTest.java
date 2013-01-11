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

package com.liferay.portalweb.portal.login;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginTest extends BaseTestCase {
	public void testLogin() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.setDefaultTimeout();
		selenium.setDefaultTimeoutImplicit();
		selenium.open("/web/guest/home");
		selenium.waitForVisible("link=Sign In");
		selenium.clickAt("link=Sign In", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("_58_login");
		selenium.type("_58_login", RuntimeVariables.replace("test@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.waitForVisible("_58_rememberMeCheckbox");
		selenium.click("_58_rememberMeCheckbox");
		selenium.waitForVisible("//input[@value='Sign In']");
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.click("//input[@value='I Agree']");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.waitForVisible("//input[@value='Save']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.setBrowserOption();
	}
}