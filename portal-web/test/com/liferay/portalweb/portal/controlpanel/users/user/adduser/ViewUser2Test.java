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

package com.liferay.portalweb.portal.controlpanel.users.user.adduser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUser2Test extends BaseTestCase {
	public void testViewUser2() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("usersn2"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn2"),
					selenium.getText(
						"//tr[contains(.,'userfn2')]/td[@headers='_125_usersSearchContainer_col-first-name']/a"));
				assertEquals(RuntimeVariables.replace("userln2"),
					selenium.getText(
						"//tr[contains(.,'userln2')]/td[@headers='_125_usersSearchContainer_col-last-name']/a"));
				assertEquals(RuntimeVariables.replace("usersn2"),
					selenium.getText(
						"//tr[contains(.,'usersn2')]/td[@headers='_125_usersSearchContainer_col-screen-name']/a"));
				selenium.clickAt("//tr[contains(.,'userfn2')]/td[@headers='_125_usersSearchContainer_col-first-name']/a",
					RuntimeVariables.replace("userfn2"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn2 userln2"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText("//div[@id='_125_details']/h3"));
				assertEquals("",
					selenium.getValue("//select[@id='_125_prefixId']"));
				assertEquals("usersn2",
					selenium.getValue("//input[@id='_125_screenName']"));
				assertEquals("userea2@liferay.com",
					selenium.getValue("//input[@id='_125_emailAddress']"));
				assertEquals("userfn2",
					selenium.getValue("//input[@id='_125_firstName']"));
				assertEquals("",
					selenium.getValue("//input[@id='_125_middleName']"));
				assertEquals("userln2",
					selenium.getValue("//input[@id='_125_lastName']"));
				assertTrue(selenium.isVisible("//img[@class='avatar']"));
				assertEquals(RuntimeVariables.replace("Change"),
					selenium.getText("//span[@class='edit-logo-link']/a/span"));
				assertEquals("0",
					selenium.getValue("//select[@id='_125_birthdayMonth']"));
				assertEquals("1",
					selenium.getValue("//select[@id='_125_birthdayDay']"));
				assertEquals("1970",
					selenium.getValue("//select[@id='_125_birthdayYear']"));
				assertEquals("true",
					selenium.getValue("//select[@id='_125_male']"));
				assertEquals("",
					selenium.getValue("//input[@id='_125_jobTitle']"));
				assertEquals(RuntimeVariables.replace("userfn2 userln2"),
					selenium.getText("//span[@class='user-name']"));

			case 100:
				label = -1;
			}
		}
	}
}