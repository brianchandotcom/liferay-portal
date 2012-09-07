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

package com.liferay.portalweb.portal.controlpanel.users.userroles.selectorganizationrolepage2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectOrganizationRolePage2Test extends BaseTestCase {
	public void testSelectOrganizationRolePage2() throws Exception {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Search All Users",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("test01@liferay.com"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test"),
			selenium.getText("//tr[3]/td[2]/a"));
		selenium.clickAt("//tr[3]/td[2]/a", RuntimeVariables.replace("Test"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible(
			"xPath=(//div[@id='_125_roles']/span/a/span)[2]");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("xPath=(//div[@id='_125_roles']/span/a/span)[2]"));
		selenium.clickAt("xPath=(//div[@id='_125_roles']/span/a/span)[2]",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForVisible("//a[@class='next']");
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		selenium.clickAt("//a[@class='next']", RuntimeVariables.replace("Next"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization 21"),
			selenium.getText("//tr[3]/td[1]/a"));
		selenium.clickAt("//tr[3]/td[1]/a",
			RuntimeVariables.replace("Organization 21"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']"));
		assertFalse(selenium.isTextPresent("No roles were found."));
		selenium.click("link=Organization Owner");
		selenium.selectWindow("null");
		selenium.waitForText("//table[@data-searchcontainerid='_125_organizationRolesSearchContainer']/tr/td[1]",
			"Organization Owner");
		assertEquals(RuntimeVariables.replace("Organization Owner"),
			selenium.getText(
				"//table[@data-searchcontainerid='_125_organizationRolesSearchContainer']/tr/td[1]"));
		assertEquals(RuntimeVariables.replace("Organization 21"),
			selenium.getText(
				"//table[@data-searchcontainerid='_125_organizationRolesSearchContainer']/tr/td[2]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Organization Owner"),
			selenium.getText(
				"//table[@data-searchcontainerid='_125_organizationRolesSearchContainer']/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Organization 21"),
			selenium.getText(
				"//table[@data-searchcontainerid='_125_organizationRolesSearchContainer']/tbody/tr[3]/td[2]"));
	}
}