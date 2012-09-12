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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineMemberRoleTest extends BaseTestCase {
	public void testDefineMemberRole() throws Exception {
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
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Member"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Member"),
			selenium.getText("//tr[3]/td/a"));
		selenium.clickAt("//tr[3]/td/a", RuntimeVariables.replace("Member"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Define Permissions",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("label=Media Gallery"));
		selenium.waitForPageToLoad("30000");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_DOCUMENT']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_FOLDER']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryADD_SHORTCUT']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryPERMISSIONS']");
		selenium.uncheck(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibraryVIEW']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("label=Media Gallery"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isChecked("//tr[3]/td/input"));
		assertTrue(selenium.isChecked("//tr[11]/td/input"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("index=55"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Media Gallery"),
			selenium.getText("//h3"));
		selenium.uncheck("//input[@value='31ADD_TO_PAGE']");
		selenium.uncheck("//input[@value='31CONFIGURATION']");
		selenium.uncheck("//input[@value='31PERMISSIONS']");
		selenium.check("//input[@value='31VIEW']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("index=55"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Media Gallery"),
			selenium.getText("//h3"));
		assertTrue(selenium.isChecked("//tr[6]/td/input"));
	}
}