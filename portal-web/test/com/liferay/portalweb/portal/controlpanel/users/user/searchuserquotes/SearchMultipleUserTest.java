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

package com.liferay.portalweb.portal.controlpanel.users.user.searchuserquotes;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchMultipleUserTest extends BaseTestCase {
	public void testSearchMultipleUser() throws Exception {
		selenium.open("/web/guest/home/");

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
		assertEquals(RuntimeVariables.replace("Search All Users"),
			selenium.getText("//a[@id='_125_allUsersLink']"));
		selenium.clickAt("//a[@id='_125_allUsersLink']",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("john joe"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("johndoe"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("john jane"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("johndoe"));
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("jane joe"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("smith doe"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("janesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("new york chicago"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("johndoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("new york new jersey"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("janesmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("new york boston"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("janesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("chicago new jersey"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertTrue(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("janesmith"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("chicago boston"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janesmith"));
		assertTrue(selenium.isTextPresent("johndoe"));
		assertFalse(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("joesmith"));
		assertFalse(selenium.isTextPresent("janedoe"));
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("new jersey boston"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("janedoe"));
		assertTrue(selenium.isTextPresent("joesmith"));
		assertTrue(selenium.isTextPresent("janesmith"));
		assertTrue(selenium.isTextPresent("johnsmith"));
		assertFalse(selenium.isTextPresent("johndoe"));
	}
}