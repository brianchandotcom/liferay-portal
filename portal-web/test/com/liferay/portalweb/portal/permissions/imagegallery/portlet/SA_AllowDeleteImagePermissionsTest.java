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

package com.liferay.portalweb.portal.permissions.imagegallery.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_AllowDeleteImagePermissionsTest extends BaseTestCase {
	public void testSA_AllowDeleteImagePermissions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Media Gallery Temporary Folder"),
			selenium.getText("//a[@title='Media Gallery Temporary Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Temporary Folder - ']",
			RuntimeVariables.replace("Media Gallery Temporary Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Portlet Permissions Image 2 Test Title Edited"),
			selenium.getText(
				"//a[@title='Portlet Permissions Image 2 Test Title Edited - ']"));
		selenium.clickAt("//a[@title='Portlet Permissions Image 2 Test Title Edited - ']",
			RuntimeVariables.replace("Portlet Permissions Image 2 Test Title"));
		selenium.waitForVisible("//img[@title='Permissions']");
		selenium.clickAt("//img[@title='Permissions']",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//input[@id='portlet_ACTION_DELETE']"));
		selenium.clickAt("//input[@id='portlet_ACTION_DELETE']",
			RuntimeVariables.replace("Portlet Delete"));
		assertTrue(selenium.isChecked("//input[@id='portlet_ACTION_DELETE']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked("//input[@id='portlet_ACTION_DELETE']"));
	}
}