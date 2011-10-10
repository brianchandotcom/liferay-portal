/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.stagingorganization.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsRoleCCOrganizationTest extends BaseTestCase {
	public void testDefinePermissionsRoleCCOrganization()
		throws Exception {
		selenium.open("/web/guest/home/");

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

		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Content Creator"),
			selenium.getText("//tr[10]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Organization"),
			selenium.getText("//tr[10]/td[2]/a"));
		assertEquals(RuntimeVariables.replace(
				"This is the Content Creator Role."),
			selenium.getText("//tr[10]/td[3]/a"));
		selenium.clickAt("//tr[10]/td[4]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Content Creator"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Add Permissions (Changing the value of this field will reload the page.)"),
			selenium.getText("//label[@for='_128_add-permissions']"));
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_add-permissions",
			RuntimeVariables.replace("label=Organization Administration"));
		selenium.waitForPageToLoad("30000");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationASSIGN_MEMBERS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationASSIGN_USER_ROLES']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationDELETE']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_ANNOUNCEMENTS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_ARCHIVED_SETUPS']");
		selenium.check(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_LAYOUTS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_STAGING']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_SUBORGANIZATIONS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_TEAMS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationMANAGE_USERS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationPERMISSIONS']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationPUBLISH_STAGING']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationUPDATE']");
		selenium.uncheck(
			"//input[@value='com.liferay.portal.model.OrganizationVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//section/div/div/div/div[3]"));
	}
}