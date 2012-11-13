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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.viewauthenticationgeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAuthenticationGeneralTest extends BaseTestCase {
	public void testViewAuthenticationGeneral() throws Exception {
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
		selenium.clickAt("link=General", RuntimeVariables.replace("General"));
		assertEquals(RuntimeVariables.replace("Portal Settings"),
			selenium.getText("//h1[@class='portlet-title']"));
		assertEquals(RuntimeVariables.replace("Authentication"),
			selenium.getText("//div[@id='_130_authentication']/h3"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("link=General"));
		assertEquals(RuntimeVariables.replace("LDAP"),
			selenium.getText("link=LDAP"));
		assertEquals(RuntimeVariables.replace("CAS"),
			selenium.getText("link=CAS"));
		assertEquals(RuntimeVariables.replace("Facebook"),
			selenium.getText("link=Facebook"));
		assertEquals(RuntimeVariables.replace("NTLM"),
			selenium.getText("link=NTLM"));
		assertEquals(RuntimeVariables.replace("OpenID"),
			selenium.getText("link=OpenID"));
		assertEquals(RuntimeVariables.replace("Open SSO"),
			selenium.getText("link=Open SSO"));
		assertEquals(RuntimeVariables.replace("SiteMinder"),
			selenium.getText("link=SiteMinder"));
		selenium.waitForText("//label[@for='company.security.auth.type']",
			"How do users authenticate?");
		assertEquals(RuntimeVariables.replace("How do users authenticate?"),
			selenium.getText("//label[@for='company.security.auth.type']"));
		assertTrue(selenium.isVisible(
				"//select[@id='_130_company.security.auth.type']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_130_company.security.auto.loginCheckbox']"));
		assertEquals(RuntimeVariables.replace(
				"Allow users to automatically login?"),
			selenium.getText(
				"//label[@for='_130_company.security.auto.loginCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_130_company.security.send.passwordCheckbox']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_company.security.send.passwordCheckbox']",
				"Allow users to request forgotten passwords?"));
		assertTrue(selenium.isChecked(
				"//input[@id='_130_company.security.send.password.reset.linkCheckbox']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_company.security.send.password.reset.linkCheckbox']",
				"Allow users to request password reset links?"));
		assertTrue(selenium.isChecked(
				"//input[@id='_130_company.security.strangersCheckbox']"));
		assertEquals(RuntimeVariables.replace(
				"Allow strangers to create accounts?"),
			selenium.getText(
				"//label[@for='_130_company.security.strangersCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_130_company.security.strangers.with.mxCheckbox']"));
		assertEquals(RuntimeVariables.replace(
				"Allow strangers to create accounts with a company email address?"),
			selenium.getText(
				"//label[@for='_130_company.security.strangers.with.mxCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_130_company.security.strangers.verifyCheckbox']"));
		assertEquals(RuntimeVariables.replace(
				"Require strangers to verify their email address?"),
			selenium.getText(
				"//label[@for='_130_company.security.strangers.verifyCheckbox']"));
		assertTrue(selenium.isVisible("//img[@class='company-logo']"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//span[@class='company-name']"));
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='menu-group']/h3[contains(.,'Configuration')]"));
		assertTrue(selenium.isPartialText("//a[@id='_130_generalLink']",
				"General"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_authenticationLink']", "Authentication"));
		assertTrue(selenium.isPartialText("//a[@id='_130_usersLink']", "Users"));
		assertTrue(selenium.isPartialText("//a[@id='_130_mailHostNamesLink']",
				"Mail Host Names"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_emailNotificationsLink']", "Email Notifications"));
		assertTrue(selenium.isPartialText("//a[@id='_130_recycleBinLink']",
				"Recycle Bin"));
		assertEquals(RuntimeVariables.replace("Identification"),
			selenium.getText(
				"//div[@class='menu-group']/h3[contains(.,'Identification')]"));
		assertTrue(selenium.isPartialText("//a[@id='_130_addressesLink']",
				"Addresses"));
		assertTrue(selenium.isPartialText("//a[@id='_130_phoneNumbersLink']",
				"Phone Numbers"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_additionalEmailAddressesLink']",
				"Additional Email Addresses"));
		assertTrue(selenium.isPartialText("//a[@id='_130_websitesLink']",
				"Websites"));
		assertEquals(RuntimeVariables.replace("Miscellaneous"),
			selenium.getText(
				"//div[@class='menu-group']/h3[contains(.,'Miscellaneous')]"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_130_displaySettingsLink']", "Display Settings"));
		assertTrue(selenium.isPartialText("//a[@id='_130_analyticsLink']",
				"Analytics"));
		assertTrue(selenium.isPartialText("//a[@id='_130_googleAppsLink']",
				"Google Apps"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}