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

package com.liferay.portalweb.socialofficeprofile.profile.addusersoexpertise;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOExpertiseTest extends BaseTestCase {
	public void testAddUserSOExpertise() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_toggle_id_users_admin_user_searchkeywords']",
					RuntimeVariables.replace("socialoffice01"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Social01"),
					selenium.getText("//a[contains(.,'Social01')]"));
				selenium.clickAt("//a[contains(.,'Social01')]",
					RuntimeVariables.replace("Social01"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText("//div[2]/h1/span"));
				selenium.waitForVisible("//a[@id='_125_projectsLink']");
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_projectsLink']", "Projects"));
				selenium.clickAt("//a[@id='_125_projectsLink']",
					RuntimeVariables.replace("Projects"));
				selenium.waitForVisible(
					"//input[@id='_125_projectsEntryTitle0']");
				selenium.type("//input[@id='_125_projectsEntryTitle0']",
					RuntimeVariables.replace("Expertise Title"));
				selenium.waitForPartialText("//select[contains(@id,'_125_projectsEntryStartDateMonth0')]",
					"January");
				selenium.select("//select[contains(@id,'_125_projectsEntryStartDateMonth0')]",
					RuntimeVariables.replace("January"));
				assertTrue(selenium.isElementPresent(
						"//input[@id='_125_projectsEntryCurrent0Checkbox']"));

				boolean currentExpertiseChecked = selenium.isChecked(
						"_125_projectsEntryCurrent0Checkbox");

				if (currentExpertiseChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_125_projectsEntryCurrent0Checkbox']",
					RuntimeVariables.replace("Enabled"));

			case 2:
				selenium.waitForVisible(
					"//textarea[@id='_125_projectsEntryDescription0']");
				selenium.type("//textarea[@id='_125_projectsEntryDescription0']",
					RuntimeVariables.replace("Expertise Description"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals("Expertise Title",
					selenium.getValue("//input[@id='_125_projectsEntryTitle0']"));
				assertEquals("January",
					selenium.getSelectedLabel(
						"//select[contains(@id,'_125_projectsEntryStartDateMonth0')]"));
				assertTrue(selenium.isChecked(
						"//input[@id='_125_projectsEntryCurrent0Checkbox']"));
				assertEquals(RuntimeVariables.replace("Expertise Description"),
					selenium.getText(
						"//textarea[@id='_125_projectsEntryDescription0']"));

			case 100:
				label = -1;
			}
		}
	}
}