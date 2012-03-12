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

package com.liferay.portalweb.demo.components.blogs.commentnotification;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SignInGmailTest extends BaseTestCase {
	public void testSignInGmail() throws Exception {
		selenium.open("http://mail.google.com/");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='Email']",
			RuntimeVariables.replace("liferay.qa@gmail.com"));
		selenium.type("//input[@id='Passwd']",
			RuntimeVariables.replace("loveispatient"));
		selenium.clickAt("//input[@id='signIn']",
			RuntimeVariables.replace("Sign In"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
	}
}