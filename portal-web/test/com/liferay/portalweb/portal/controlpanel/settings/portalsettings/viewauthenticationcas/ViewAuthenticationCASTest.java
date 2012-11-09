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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.viewauthenticationcas;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAuthenticationCASTest extends BaseTestCase {
	public void testViewAuthenticationCAS() throws Exception {
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
		selenium.clickAt("link=CAS", RuntimeVariables.replace("CAS"));
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
		assertTrue(selenium.isVisible(
				"//input[@id='_130_cas.auth.enabledCheckbox']"));
		assertEquals(RuntimeVariables.replace("Enabled"),
			selenium.getText("//label[@for='_130_cas.auth.enabledCheckbox']"));
		assertTrue(selenium.isVisible(
				"//input[@id='_130_cas.import.from.ldapCheckbox']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.import.from.ldapCheckbox']",
				"Import from LDAP"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.login.url']", "Login URL"));
		assertTrue(selenium.isVisible("//input[@id='_130_cas.login.url']"));
		assertEquals("https://localhost:8443/cas-web/login",
			selenium.getValue("//input[@id='_130_cas.login.url']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.logout.url']", "Logout URL"));
		assertTrue(selenium.isVisible("//input[@id='_130_cas.logout.url']"));
		assertEquals("https://localhost:8443/cas-web/logout",
			selenium.getValue("//input[@id='_130_cas.logout.url']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.server.name']", "Server Name"));
		assertTrue(selenium.isVisible("//input[@id='_130_cas.server.name']"));
		assertEquals("localhost:8080",
			selenium.getValue("//input[@id='_130_cas.server.name']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.server.url']", "Server URL"));
		assertTrue(selenium.isVisible("//input[@id='_130_cas.server.url']"));
		assertEquals("https://localhost:8443/cas-web",
			selenium.getValue("//input[@id='_130_cas.server.url']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.service.url']", "Service URL"));
		assertTrue(selenium.isVisible("//input[@id='_130_cas.service.url']"));
		assertTrue(selenium.isPartialText(
				"//label[@for='_130_cas.no.such.user.redirect.url']",
				"No Such User Redirect URL"));
		assertTrue(selenium.isVisible(
				"//input[@id='_130_cas.no.such.user.redirect.url']"));
		assertEquals("http://localhost:8080",
			selenium.getValue(
				"//input[@id='_130_cas.no.such.user.redirect.url']"));
		assertTrue(selenium.isVisible(
				"//input[@value='Test CAS Configuration']"));
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