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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.viewauthenticationldap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAuthenticationLDAPTest extends BaseTestCase {
	public void testViewAuthenticationLDAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
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
		selenium.clickAt("link=LDAP", RuntimeVariables.replace("LDAP"));
		selenium.waitForElementPresent(
			"//input[@name='_130_settings--ldap.auth.enabled--Checkbox']");
		assertTrue(selenium.isElementPresent(
				"//input[@name='_130_settings--ldap.auth.enabled--Checkbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@name='_130_settings--ldap.auth.required--Checkbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_130_ldapImportEnabledCheckbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_130_ldapExportEnabledCheckbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@name='_130_settings--ldap.password.policy.enabled--Checkbox']"));
		selenium.clickAt("//input[@id='_130_addButton']",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@name='_130_defaultLdap' and @value='microsoft']",
			RuntimeVariables.replace("Microsoft Active Directory Server Button"));
		selenium.clickAt("//div[2]/span/span/input",
			RuntimeVariables.replace("Reset Values"));
		assertEquals("ldap://localhost:389",
			selenium.getValue("//input[@id='_130_ldap.base.provider.url.0']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Test LDAP Connection']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Test LDAP Users']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Test LDAP Groups']"));
	}
}