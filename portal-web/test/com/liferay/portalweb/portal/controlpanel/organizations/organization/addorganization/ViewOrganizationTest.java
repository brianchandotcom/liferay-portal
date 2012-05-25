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

package com.liferay.portalweb.portal.controlpanel.organizations.organization.addorganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrganizationTest extends BaseTestCase {
	public void testViewOrganization() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Organization Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//tr[contains(.,'Organization Name')]/td[@headers='_125_organizationsSearchContainer_col-name']/a/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText(
				"//tr[contains(.,'Regular Organization')]/td[@headers='_125_organizationsSearchContainer_col-type']/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText("//div[@id='_125_details']/h3"));
		assertEquals(RuntimeVariables.replace("Name (Required)"),
			selenium.getText("//label[@for='_125_']"));
		assertEquals("Organization Name",
			selenium.getValue("//input[@id='_125_name']"));
		assertEquals(RuntimeVariables.replace("Type"),
			selenium.getText(
				"//div[@id='_125_details']/fieldset/div/div/div/label"));
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_details']/fieldset/div/div/div",
				"Regular Organization"));
		assertEquals(RuntimeVariables.replace("Site ID"),
			selenium.getText(
				"//div[@id='_125_details']/fieldset/div/div[3]/div/label"));
		assertTrue(selenium.isVisible("//img[@id='igva_avatar']"));
		assertEquals(RuntimeVariables.replace("Change"),
			selenium.getText("//div[@class='portrait-icons']/span/a/span"));
		assertTrue(selenium.isVisible(
				"//div[@class='portrait-icons']/span/a/span"));
		assertEquals(RuntimeVariables.replace("Parent Organization"),
			selenium.getText("//div[@id='_125_details']/h3[2]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//span[@class='modify-link']/a/span"));
		assertTrue(selenium.isVisible("//span[@class='modify-link']/a/span"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//span[@class='organization-name']"));
	}
}