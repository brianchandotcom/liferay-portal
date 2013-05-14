/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignRoleSiteAdministratorSoccerAdminTest extends BaseTestCase {
	public void testAssignRoleSiteAdministratorSoccerAdmin()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_134_name']",
			RuntimeVariables.replace("World Cup"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("World Cup - Brazil 2014"),
			selenium.getText("//td/a"));
		selenium.clickAt("//td/a",
			RuntimeVariables.replace("World Cup - Brazil 2014"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Site Memberships",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Site Roles to"),
			selenium.getText(
				"//span[contains(.,'Add Site Roles to')]/ul/li/strong/a"));
		selenium.clickAt("//span[contains(.,'Add Site Roles to')]/ul/li/strong/a",
			RuntimeVariables.replace("Add Site Roles to"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Users')]/a");
		assertEquals(RuntimeVariables.replace("Users"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Users')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Users')]/a",
			RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@name='_174_keywords']"));
		selenium.type("//input[@name='_174_keywords']",
			RuntimeVariables.replace("Administrator"));
		assertEquals(RuntimeVariables.replace("Site Administrator"),
			selenium.getText("//td/a"));
		selenium.clickAt("//td/a",
			RuntimeVariables.replace("Site Administrator"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Available"),
			selenium.getText("link=Available"));
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@name='_174_keywords']"));
		selenium.type("//input[@name='_174_keywords']",
			RuntimeVariables.replace("socceradmin"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bruno Admin"),
			selenium.getText("//tr[3]/td[2]"));
		assertFalse(selenium.isChecked("//td/input"));
		selenium.clickAt("//td/input",
			RuntimeVariables.replace("Bruno Admin checkbox"));
		assertTrue(selenium.isChecked("//td/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}