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

package com.liferay.portalweb.portal.ldap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertLDAPGroupsTest extends BaseTestCase {
	public void testAssertLDAPGroups() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_authenticationLink']", "Authentication"));
		selenium.clickAt("//a[@id='_130_authenticationLink']",
			RuntimeVariables.replace("Authentication"));
		selenium.waitForVisible("link=LDAP");
		selenium.clickAt("link=LDAP", RuntimeVariables.replace("LDAP"));
		selenium.waitForVisible("//input[@id='_130_ldap.auth.enabledCheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_130_ldap.auth.enabledCheckbox']"));
		assertEquals(RuntimeVariables.replace("Test LDAP 1"),
			selenium.getText("//fieldset[2]/div/div/table/tbody/tr/td[1]"));
		selenium.clickAt("//img[@alt='Edit']", RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Test LDAP Groups']",
			RuntimeVariables.replace("Test LDAP Groups"));
		selenium.waitForVisible("//div[1]/div[2]");
		selenium.waitForText("//td[2]", "usergroup");
		assertEquals(RuntimeVariables.replace("usergroup"),
			selenium.getText("//td[2]"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[3]"));
		assertEquals(RuntimeVariables.replace("1"), selenium.getText("//td[4]"));
		System.out.println("LDAP Groups have been detected.");
		selenium.click("//button");
	}
}