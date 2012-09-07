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
public class LoginLegacy51xTest extends BaseTestCase {
	public void testLoginLegacy51x() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.setTimeout("180000");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("link=Sign In");
		selenium.click(RuntimeVariables.replace("link=Sign In"));
		selenium.waitForPageToLoad("30000");
		selenium.type("login", RuntimeVariables.replace("test@liferay.com"));
		selenium.type("password", RuntimeVariables.replace("test"));
		selenium.click("rememberMeCheckbox");
		selenium.click(RuntimeVariables.replace(
				"document.getElementById('tabs1already-registeredTabsSection').getElementsByTagName('div')[0].getElementsByTagName('form')[0].getElementsByTagName('fieldset')[0].getElementsByTagName('div')[3].getElementsByTagName('input')[0]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//input[@value='I Agree']");
		selenium.click(RuntimeVariables.replace("//input[@value='I Agree']"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"document.getElementById('my-community-private-pages')"));
		selenium.waitForPageToLoad("30000");
	}
}