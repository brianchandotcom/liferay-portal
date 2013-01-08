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

package com.liferay.portalweb.socialoffice.users.user.editsouserpassword;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditSOUserPasswordTest extends BaseTestCase {
	public void testEditSOUserPassword() throws Exception {
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
					RuntimeVariables.replace("socialoffice01"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Social01"),
					selenium.getText("//td[contains(.,'Social01')]/a"));
				selenium.clickAt("//td[contains(.,'Social01')]/a",
					RuntimeVariables.replace("Social01"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_passwordLink']", "Password"));
				selenium.clickAt("//a[@id='_125_passwordLink']",
					RuntimeVariables.replace("Password"));
				selenium.waitForVisible("//input[@id='_125_password1']");
				selenium.type("//input[@id='_125_password1']",
					RuntimeVariables.replace("password"));
				selenium.type("//input[@id='_125_password2']",
					RuntimeVariables.replace("password"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.open("/web/guest/home/");

				boolean socialOfficeSignOut1Present = selenium.isElementPresent(
						"//li[@id='_145_userMenu']");

				if (!socialOfficeSignOut1Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertTrue(selenium.isVisible("//li[@id='_145_userMenu']"));
				selenium.mouseOver("//li[@id='_145_userMenu']");

			case 3:
				selenium.waitForVisible("link=Sign Out");
				selenium.clickAt("link=Sign Out",
					RuntimeVariables.replace("Sign Out"));
				selenium.waitForPageToLoad("30000");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Sign In");
				selenium.clickAt("link=Sign In",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_58_login']",
					RuntimeVariables.replace("socialoffice01@liferay.com"));
				selenium.type("//input[@id='_58_password']",
					RuntimeVariables.replace("password"));
				assertFalse(selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']"));
				selenium.clickAt("//input[@id='_58_rememberMeCheckbox']",
					RuntimeVariables.replace("Remember Me"));
				assertTrue(selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']"));
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='password1']",
					RuntimeVariables.replace("test"));
				selenium.type("//input[@id='password2']",
					RuntimeVariables.replace("test"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='reminderQueryAnswer']",
					RuntimeVariables.replace("test"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.open("/web/guest/home/");

				boolean socialOfficeSignOut2Present = selenium.isElementPresent(
						"//li[@id='_145_userMenu']");

				if (!socialOfficeSignOut2Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
				assertTrue(selenium.isVisible("//li[@id='_145_userMenu']"));
				selenium.mouseOver("//li[@id='_145_userMenu']");

			case 4:
				selenium.waitForVisible("link=Sign Out");
				selenium.clickAt("link=Sign Out",
					RuntimeVariables.replace("Sign Out"));
				selenium.waitForPageToLoad("30000");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Sign In");
				selenium.clickAt("link=Sign In",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_58_login']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.type("//input[@id='_58_password']",
					RuntimeVariables.replace("test"));
				assertFalse(selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']"));
				selenium.clickAt("//input[@id='_58_rememberMeCheckbox']",
					RuntimeVariables.replace("Remember Me"));
				assertTrue(selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']"));
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}