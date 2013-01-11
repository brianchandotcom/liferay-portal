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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_DefineAARoleTest extends BaseTestCase {
	public void testSA_DefineAARole() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
					RuntimeVariables.replace("Announcements"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Announcements Administrator"),
					selenium.getText(
						"//tr[contains(.,'Announcements Administrator')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Announcements Administrator')]/td[1]/a",
					RuntimeVariables.replace("Announcements Administrator"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");

				boolean manage1AnnouncementsCheckbox = selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleMANAGE_ANNOUNCEMENTS']");

				if (manage1AnnouncementsCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleMANAGE_ANNOUNCEMENTS']",
					RuntimeVariables.replace("Manage Announcements Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.RoleMANAGE_ANNOUNCEMENTS']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");

				boolean manage2AnnouncementsCheckbox = selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ANNOUNCEMENTS']");

				if (manage2AnnouncementsCheckbox) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ANNOUNCEMENTS']",
					RuntimeVariables.replace("Manage Announcements Checkbox"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_ANNOUNCEMENTS']"));

				boolean manageArchivedSetupsCheckbox = selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']");

				if (manageArchivedSetupsCheckbox) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']",
					RuntimeVariables.replace("Manage Archived Setups Checkbox"));

			case 4:
				assertTrue(selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");

				boolean manage3AnnouncementsCheckbox = selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupMANAGE_ANNOUNCEMENTS']");

				if (manage3AnnouncementsCheckbox) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupMANAGE_ANNOUNCEMENTS']",
					RuntimeVariables.replace("Manage Announcements Checkbox"));

			case 5:
				assertTrue(selenium.isChecked(
						"//input[@name='_128_rowIds' and @value='com.liferay.portal.model.UserGroupMANAGE_ANNOUNCEMENTS']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Announcements"));
				selenium.waitForPageToLoad("30000");

				boolean select1AllCheckbox = selenium.isChecked(
						"//input[@name='_128_allRowIds']");

				if (select1AllCheckbox) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_128_allRowIds']",
					RuntimeVariables.replace("Select All Checkbox"));

			case 6:
				assertTrue(selenium.isChecked("//input[@name='_128_allRowIds']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("index=34"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Announcements"),
					selenium.getText("//form/h3"));

				boolean select2AllCheckbox = selenium.isChecked(
						"//input[@name='_128_allRowIds']");

				if (select2AllCheckbox) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@name='_128_allRowIds']",
					RuntimeVariables.replace("Select All Checkbox"));

			case 7:
				assertTrue(selenium.isChecked("//input[@name='_128_allRowIds']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}