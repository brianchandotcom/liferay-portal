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

package com.liferay.portalweb.portal.controlpanel.users.user.changeuserpassword;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ChangeUserPasswordTest extends BaseTestCase {
	public void testUser_ChangeUserPassword() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=selen01 lenn nium01");
		selenium.clickAt("link=selen01 lenn nium01",
			RuntimeVariables.replace("selen01 lenn nium01"));
		Thread.sleep(5000);
		selenium.waitForVisible("//a[@id='_2_passwordLink']");
		assertTrue(selenium.isPartialText("//a[@id='_2_passwordLink']",
				"Password"));
		selenium.clickAt("//a[@id='_2_passwordLink']",
			RuntimeVariables.replace("Password"));
		selenium.waitForVisible("//input[@id='_2_password0']");
		selenium.type("//input[@id='_2_password0']",
			RuntimeVariables.replace("asdf"));
		selenium.type("//input[@id='_2_password1']",
			RuntimeVariables.replace("test2"));
		selenium.type("//input[@id='_2_password2']",
			RuntimeVariables.replace("test2"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}