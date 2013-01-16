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

package com.liferay.portalweb.socialoffice.users.user.selectregularrolessouserroles;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectRegularRolesSOUserRolesTest extends BaseTestCase {
	public void testSelectRegularRolesSOUserRoles() throws Exception {
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
				selenium.clickAt("link=Roles",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@id='_128_keywords']");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Social Office"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText("//td[contains(.,'Social Office User')]/a"));
				selenium.clickAt("//td[contains(.,'Social Office User')]/a",
					RuntimeVariables.replace("Social Office User"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText("//a[contains(.,'Assign Members')]"));
				selenium.clickAt("//a[contains(.,'Assign Members')]",
					RuntimeVariables.replace("Assign Members"));
				selenium.waitForVisible("//a[contains(.,'Available')]");
				assertEquals(RuntimeVariables.replace("Available"),
					selenium.getText("//a[contains(.,'Available')]"));
				selenium.clickAt("//a[contains(.,'Available')]",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@id='_128_toggle_id_users_admin_user_searchkeywords']",
					RuntimeVariables.replace("socialoffice01"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//td[@id='_128_usersSearchContainer_col-rowChecker_row-socialoffice01']/input"));

				boolean userChecked = selenium.isChecked(
						"//td[@id='_128_usersSearchContainer_col-rowChecker_row-socialoffice01']/input");

				if (userChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//td[@id='_128_usersSearchContainer_col-rowChecker_row-socialoffice01']/input",
					RuntimeVariables.replace(
						"Social01 Office01 User01 Checkbox"));

			case 3:
				assertTrue(selenium.isChecked(
						"//td[@id='_128_usersSearchContainer_col-rowChecker_row-socialoffice01']/input"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}