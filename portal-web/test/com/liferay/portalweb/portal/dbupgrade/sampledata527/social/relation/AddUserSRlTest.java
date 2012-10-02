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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.social.relation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSRlTest extends BaseTestCase {
	public void testAddUserSRl() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users", RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_screenName']",
			RuntimeVariables.replace("socialrelationsn1"));
		selenium.type("//input[@name='_125_emailAddress']",
			RuntimeVariables.replace("socialrelationea1@liferay.com"));
		selenium.type("//input[@name='_125_firstName']",
			RuntimeVariables.replace("socialrelationfn1"));
		selenium.type("//input[@name='_125_middleName']",
			RuntimeVariables.replace("socialrelationmn1"));
		selenium.type("//input[@name='_125_lastName']",
			RuntimeVariables.replace("socialrelationln1"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("socialrelationsn1",
			selenium.getValue("//input[@name='_125_screenName']"));
		assertEquals("socialrelationea1@liferay.com",
			selenium.getValue("//input[@name='_125_emailAddress']"));
		assertEquals("socialrelationfn1",
			selenium.getValue("//input[@name='_125_firstName']"));
		assertEquals("socialrelationmn1",
			selenium.getValue("//input[@name='_125_middleName']"));
		assertEquals("socialrelationln1",
			selenium.getValue("//input[@name='_125_lastName']"));
	}
}