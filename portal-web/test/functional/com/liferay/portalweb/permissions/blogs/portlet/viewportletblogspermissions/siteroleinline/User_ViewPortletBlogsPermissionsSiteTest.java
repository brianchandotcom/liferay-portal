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

package com.liferay.portalweb.permissions.blogs.portlet.viewportletblogspermissions.siteroleinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewPortletBlogsPermissionsSiteTest extends BaseTestCase {
	public void testUser_ViewPortletBlogsPermissionsSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		selenium.clickAt("//input[@value='Permissions']",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']"));
		assertEquals(RuntimeVariables.replace("Role"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Add Entry"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace(
				"Add Application Display Template"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertEquals(RuntimeVariables.replace("Guest"),
			selenium.getText("//tr[contains(@class,'lfr-role-guest')]/td[1]"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_ADD_ENTRY']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_ADD_PORTLET_DISPLAY_TEMPLATE']"));
		assertFalse(selenium.isChecked(
				"//input[@id='guest_ACTION_PERMISSIONS']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_SUBSCRIBE']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
	}
}