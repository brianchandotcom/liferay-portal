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

package com.liferay.portalweb.portal.controlpanel.users.user.changeuserpasswordinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ChangeUserPasswordInvalidTest extends BaseTestCase {
	public void testUser_ChangeUserPasswordInvalid() throws Exception {
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
			RuntimeVariables.replace(""));
		selenium.type("//input[@id='_2_password1']",
			RuntimeVariables.replace("test2"));
		selenium.type("//input[@id='_2_password2']",
			RuntimeVariables.replace("test2"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("xPath=(//div[@class='portlet-msg-error'])[1]",
			"Your request failed to complete.");
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"That password is invalid. Please enter in a different password."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}