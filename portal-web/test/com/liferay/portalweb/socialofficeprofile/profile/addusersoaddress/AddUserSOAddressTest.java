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

package com.liferay.portalweb.socialofficeprofile.profile.addusersoaddress;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOAddressTest extends BaseTestCase {
	public void testAddUserSOAddress() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
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
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[2]/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_125_addressesLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_125_addressesLink']",
			RuntimeVariables.replace("Addresses"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[contains(@id,'addressStreet1')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[contains(@id,'addressStreet1')]",
			RuntimeVariables.replace("123 Liferay Ln."));
		selenium.select("//select[contains(@id,'addressCountry')]",
			RuntimeVariables.replace("label=United States"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[contains(@id,'addressRegion')]",
							"California")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[contains(@id,'addressRegion')]",
			RuntimeVariables.replace("label=California"));
		selenium.type("//input[contains(@id,'addressZip')]",
			RuntimeVariables.replace("91234"));
		selenium.type("//input[contains(@id,'addressCity')]",
			RuntimeVariables.replace("Ray of Light"));
		selenium.select("//select[contains(@id,'addressType')]",
			RuntimeVariables.replace("label=Personal"));
		selenium.clickAt("//input[contains(@id,'addressMailing0Checkbox')]",
			RuntimeVariables.replace("Mailing Checkbox"));
		selenium.clickAt("//input[contains(@id,'addressPrimary')]",
			RuntimeVariables.replace("Primary Button"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
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
		assertEquals("123 Liferay Ln.",
			selenium.getValue("//input[contains(@id,'addressStreet1')]"));
		assertEquals("Personal",
			selenium.getSelectedLabel("//select[contains(@id,'addressType')]"));
		assertEquals("91234",
			selenium.getValue("//input[contains(@id,'addressZip')]"));
		assertEquals("Ray of Light",
			selenium.getValue("//input[contains(@id,'addressCity')]"));
		assertEquals("United States",
			selenium.getSelectedLabel(
				"//select[contains(@id,'addressCountry')]"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[contains(@id,'addressRegion')]"));
		assertTrue(selenium.isChecked("//input[contains(@id,'addressPrimary')]"));
		assertTrue(selenium.isChecked(
				"//input[contains(@id,'addressMailing0Checkbox')]"));
	}
}