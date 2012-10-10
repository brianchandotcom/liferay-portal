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

package com.liferay.portalweb.socialoffice.setup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectRegularRolesSOAdminTest extends BaseTestCase {
	public void testSelectRegularRolesSOAdmin() throws Exception {
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
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
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
				selenium.waitForVisible(
					"//input[@id='_125_toggle_id_users_admin_user_searchkeywords']");
				selenium.type("//input[@id='_125_toggle_id_users_admin_user_searchkeywords']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible("//td[contains(.,'Joe')]/a");
				assertEquals(RuntimeVariables.replace("Joe"),
					selenium.getText("//td[contains(.,'Joe')]/a"));
				selenium.clickAt("//td[contains(.,'Joe')]/a",
					RuntimeVariables.replace("Joe"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']",
						"Roles"));
				selenium.clickAt("//a[@id='_125_rolesLink']",
					RuntimeVariables.replace("Roles"));
				selenium.waitForVisible("//tr[contains(., 'Administrator')]/td");
				assertEquals(RuntimeVariables.replace("Administrator"),
					selenium.getText("//tr[contains(., 'Administrator')]/td"));
				assertEquals(RuntimeVariables.replace("Power User"),
					selenium.getText("//tr[contains(., 'Power User')]/td"));
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText("//div[6]/span/a/span"));
				selenium.clickAt("//div[6]/span/a/span",
					RuntimeVariables.replace("Select"));
				Thread.sleep(5000);
				selenium.selectWindow("title=Users and Organizations");
				selenium.waitForText("//h1[@class='header-title']/span", "Roles");
				assertEquals(RuntimeVariables.replace("Roles"),
					selenium.getText("//h1[@class='header-title']/span"));
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("Social Office"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText("link=Social Office User"));
				selenium.click("link=Social Office User");
				selenium.selectWindow("null");
				selenium.waitForText("//tr[contains(., 'Social Office User')]/td",
					"Social Office User");
				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText(
						"//tr[contains(., 'Social Office User')]/td"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Administrator"),
					selenium.getText("//tr[contains(., 'Administrator')]/td"));
				assertEquals(RuntimeVariables.replace("Power User"),
					selenium.getText("//tr[contains(., 'Power User')]/td"));
				assertEquals(RuntimeVariables.replace("Social Office User"),
					selenium.getText(
						"//tr[contains(., 'Social Office User')]/td"));
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent(
					"//li[contains(.,'Dashboard')]/a");
				assertEquals(RuntimeVariables.replace("Dashboard"),
					selenium.getText("//li[contains(.,'Dashboard')]/a"));
				selenium.clickAt("//li[contains(.,'Dashboard')]/a",
					RuntimeVariables.replace("Dashboard"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Dashboard"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Dashboard')]/a/span"));

			case 100:
				label = -1;
			}
		}
	}
}