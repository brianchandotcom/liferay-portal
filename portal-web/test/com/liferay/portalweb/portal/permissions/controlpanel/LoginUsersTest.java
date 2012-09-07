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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginUsersTest extends BaseTestCase {
	public void testLoginUsers() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.setTimeout("180000");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Sign Out");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login", RuntimeVariables.replace("ca@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login",
			RuntimeVariables.replace("member@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login",
			RuntimeVariables.replace("portlet@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login",
			RuntimeVariables.replace("publisher@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login", RuntimeVariables.replace("scope@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login",
			RuntimeVariables.replace("writer@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='I Agree']");
		selenium.clickAt("//input[@value='I Agree']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("reminderQueryAnswer");
		selenium.type("reminderQueryAnswer", RuntimeVariables.replace("Test"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sign Out", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_58_login");
		selenium.type("_58_login", RuntimeVariables.replace("test@liferay.com"));
		selenium.type("_58_password", RuntimeVariables.replace("test"));
		selenium.clickAt("//input[@value='Sign In']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}