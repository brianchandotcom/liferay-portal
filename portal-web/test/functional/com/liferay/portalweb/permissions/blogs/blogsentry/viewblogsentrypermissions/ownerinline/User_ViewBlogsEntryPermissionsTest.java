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

package com.liferay.portalweb.permissions.blogs.blogsentry.viewblogsentrypermissions.ownerinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewBlogsEntryPermissionsTest extends BaseTestCase {
	public void testUser_ViewBlogsEntryPermissions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//td[contains(.,'Permissions')]/span/a/span"));
		selenium.clickAt("//td[contains(.,'Permissions')]/span/a/span",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']"));
		assertEquals(RuntimeVariables.replace("Role"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Add Discussion"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Delete Discussion"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertEquals(RuntimeVariables.replace("Update"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[6]"));
		assertEquals(RuntimeVariables.replace("Update Discussion"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[7]"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[8]"));
		assertEquals(RuntimeVariables.replace("Guest"),
			selenium.getText("//tr[contains(@class,'lfr-role-guest')]/td[1]"));
		assertTrue(selenium.isChecked(
				"//input[@id='guest_ACTION_ADD_DISCUSSION']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_DELETE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_DELETE_DISCUSSION']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_PERMISSIONS']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_UPDATE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_UPDATE_DISCUSSION']"));
		assertTrue(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
	}
}