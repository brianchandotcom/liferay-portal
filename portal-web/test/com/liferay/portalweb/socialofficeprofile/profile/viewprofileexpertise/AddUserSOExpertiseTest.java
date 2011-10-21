/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficeprofile.profile.viewprofileexpertise;

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
				selenium.open("/user/joebloggs/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("socialofficefriendfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("socialofficefriendfn"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("socialofficefriendfn"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
					selenium.getText("//div[2]/h1/span"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_125_expertiseLink']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isPartialText(
						"//a[@id='_125_expertiseLink']", "Expertise"));
				selenium.clickAt("//a[@id='_125_expertiseLink']",
					RuntimeVariables.replace("Expertise"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_125_projectsEntryTitle0']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_125_projectsEntryTitle0']",
					RuntimeVariables.replace("Expertise Title"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText(
									"//select[@id='_125_projectsEntryStartDateMonth0']",
									"April")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("//select[@id='_125_projectsEntryStartDateMonth0']",
					RuntimeVariables.replace("April"));
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

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//textarea[@id='_125_projectsEntryDescription0']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//textarea[@id='_125_projectsEntryDescription0']",
					RuntimeVariables.replace("Expertise Description"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals("Expertise Title",
					selenium.getValue("//input[@id='_125_projectsEntryTitle0']"));
				assertEquals("April",
					selenium.getSelectedLabel(
						"//select[@id='_125_projectsEntryStartDateMonth0']"));
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