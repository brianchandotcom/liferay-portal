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

package com.liferay.portalweb.portal.controlpanel.organizations.organization.deleteorganizationassignmembers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteOrganizationAssignMembersTest extends BaseTestCase {
	public void testViewDeleteOrganizationAssignMembers()
		throws Exception {
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
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Organization Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//tr[contains(.,'Organization Name')]/td[2]/a/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'Organization Name')]/td[2]/a/strong",
			RuntimeVariables.replace("Organization Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Users and Organizations"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("Browse"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button view-button current']/a[contains(.,'Browse')]"));
		assertEquals(RuntimeVariables.replace("View Organizations"),
			selenium.getText(
				"//span[contains(@class,'lfr-toolbar-button view-button')]/a[contains(.,'View Organizations')]"));
		assertEquals(RuntimeVariables.replace("View Users"),
			selenium.getText(
				"//span[contains(@class,'lfr-toolbar-button view-button')]/a[contains(.,'View Users')]"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Export All Users"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button export-button']/a[contains(.,'Export All Users')]"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"\u00ab Back to Users and Organizations Home"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("1 User"),
			selenium.getText("//div[@id='usersAdminUsersPanel']/div/div/span"));
		assertTrue(selenium.isVisible("//input[@value='Deactivate']"));
		assertFalse(selenium.isChecked(
				"//tr[@class='portlet-section-header results-header']/th[1]/input"));
		assertEquals(RuntimeVariables.replace("First Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Last Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Screen Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("Job Title"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertEquals(RuntimeVariables.replace("Organization Roles"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[6]"));
		assertEquals(RuntimeVariables.replace("User Groups"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[7]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[8]"));
		assertFalse(selenium.isChecked("//tr[contains(.,'userfn')]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("userfn"),
			selenium.getText("//tr[contains(.,'userfn')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("userln"),
			selenium.getText("//tr[contains(.,'userfn')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//tr[contains(.,'userfn')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'userfn')]/td[5]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'userfn')]/td[6]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'userfn')]/td[7]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		assertTrue(selenium.isVisible("//div[@class='lfr-asset-summary']/img"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//div[@class='lfr-asset-summary']/div/h4"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Assign Organization Roles"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Organization Roles')]"));
		assertEquals(RuntimeVariables.replace("Assign Users"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
		assertEquals(RuntimeVariables.replace("Add User"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add User')]"));
		assertEquals(RuntimeVariables.replace("Add Regular Organization"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Regular Organization')]"));
		assertEquals(RuntimeVariables.replace("Add Location"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Location')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
	}
}