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

package com.liferay.portalweb.portal.controlpanel.passwordpolicies;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPasswordPoliciesTest extends BaseTestCase {
	public void testAddPasswordPolicies() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Password Policies",
			RuntimeVariables.replace("Password Policies"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Add");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='_129_name']");
		selenium.type("//input[@id='_129_name']",
			RuntimeVariables.replace("Test"));
		selenium.type("//textarea[@id='_129_description']",
			RuntimeVariables.replace("This is a test password policy!"));
		selenium.clickAt("//input[@id='_129_changeableCheckbox']",
			RuntimeVariables.replace("Changeable Checkbox"));
		selenium.waitForElementPresent(
			"//input[@id='_129_changeRequiredCheckbox']");
		selenium.clickAt("//input[@id='_129_changeRequiredCheckbox']",
			RuntimeVariables.replace("Change Required Checkbox"));
		selenium.select("//select[@id='_129_minAge']",
			RuntimeVariables.replace("label=1 Week"));
		selenium.clickAt("//input[@id='_129_checkSyntaxCheckbox']",
			RuntimeVariables.replace("Syntax Checking Enabled Checkbox"));
		selenium.waitForElementPresent(
			"//input[@id='_129_allowDictionaryWordsCheckbox']");
		selenium.clickAt("//input[@id='_129_allowDictionaryWordsCheckbox']",
			RuntimeVariables.replace("Allow Dictionary Words Checkbox"));
		selenium.type("//input[@id='_129_minLength']",
			RuntimeVariables.replace("5"));
		selenium.clickAt("//input[@id='_129_historyCheckbox']",
			RuntimeVariables.replace("History Enabled Checkbox"));
		selenium.waitForElementPresent("//select[@id='_129_historyCount']");
		selenium.select("//select[@id='_129_historyCount']",
			RuntimeVariables.replace("label=4"));
		selenium.clickAt("//input[@id='_129_expireableCheckbox']",
			RuntimeVariables.replace("Expiration Enabled Checkbox"));
		selenium.waitForElementPresent("//select[@id='_129_maxAge']");
		selenium.select("//select[@id='_129_maxAge']",
			RuntimeVariables.replace("label=4 Weeks"));
		selenium.select("//select[@id='_129_warningTime']",
			RuntimeVariables.replace("label=2 Days"));
		selenium.type("//input[@id='_129_graceLimit']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@id='_129_lockoutCheckbox']",
			RuntimeVariables.replace("Lockout Enabled Checkbox"));
		selenium.waitForElementPresent("//input[@id='_129_maxFailure']");
		selenium.type("//input[@id='_129_maxFailure']",
			RuntimeVariables.replace("3"));
		selenium.select("//select[@id='_129_resetFailureCount']",
			RuntimeVariables.replace("label=10 Minutes"));
		selenium.select("//select[@id='_129_lockoutDuration']",
			RuntimeVariables.replace("label=5 Minutes"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isElementPresent("link=Test"));
		assertTrue(selenium.isElementPresent(
				"link=This is a test password policy!"));
	}
}