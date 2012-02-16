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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.social.request;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSRqTest extends BaseTestCase {
	public void testAddUserSRq() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Users", RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_125_screenName']",
			RuntimeVariables.replace("socialrequestsn1"));
		selenium.type("//input[@name='_125_emailAddress']",
			RuntimeVariables.replace("socialrequestea1@liferay.com"));
		selenium.type("//input[@name='_125_firstName']",
			RuntimeVariables.replace("socialrequestfn1"));
		selenium.type("//input[@name='_125_middleName']",
			RuntimeVariables.replace("socialrequestmn1"));
		selenium.type("//input[@name='_125_lastName']",
			RuntimeVariables.replace("socialrequestln1"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("socialrequestsn1",
			selenium.getValue("//input[@name='_125_screenName']"));
		assertEquals("socialrequestea1@liferay.com",
			selenium.getValue("//input[@name='_125_emailAddress']"));
		assertEquals("socialrequestfn1",
			selenium.getValue("//input[@name='_125_firstName']"));
		assertEquals("socialrequestmn1",
			selenium.getValue("//input[@name='_125_middleName']"));
		assertEquals("socialrequestln1",
			selenium.getValue("//input[@name='_125_lastName']"));
	}
}