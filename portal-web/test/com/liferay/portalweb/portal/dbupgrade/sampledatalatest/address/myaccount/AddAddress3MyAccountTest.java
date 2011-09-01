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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress3MyAccountTest extends BaseTestCase {
	public void testAddAddress3MyAccount() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_2_addressesLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//a[@id='_2_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_2_addressesLink']",
			RuntimeVariables.replace("Addresses"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_2_addressStreet1_0']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/div/span/span/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/div/span/span/button[1]",
			RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_2_addressStreet1_3']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_2_addressStreet1_3']",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_2_addressTypeId3']",
			RuntimeVariables.replace("label=Business"));
		selenium.type("//input[@id='_2_addressStreet2_3']",
			RuntimeVariables.replace("Ste 12"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_2_addressZip3']",
			RuntimeVariables.replace("91789"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_2_addressStreet3_3']",
			RuntimeVariables.replace("Walnut"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_2_addressCity3']",
			RuntimeVariables.replace("Los Angeles"));
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[@id='_2_addressCountryId3']",
							"United States")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_2_addressCountryId3']",
			RuntimeVariables.replace("label=United States"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isPartialText(
							"//select[@id='_2_addressRegionId3']", "California")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_2_addressRegionId3']",
			RuntimeVariables.replace("label=California"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if ("United States".equals(selenium.getSelectedLabel(
								"//select[@id='_2_addressCountryId2']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if ("California".equals(selenium.getSelectedLabel(
								"//select[@id='_2_addressRegionId2']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("//input[@id='_2_addressStreet1_2']"));
		assertEquals("Business",
			selenium.getSelectedLabel("//select[@id='_2_addressTypeId2']"));
		assertEquals("Ste 12",
			selenium.getValue("//input[@id='_2_addressStreet2_2']"));
		assertEquals("91789", selenium.getValue("//input[@id='_2_addressZip2']"));
		assertEquals("Walnut",
			selenium.getValue("//input[@id='_2_addressStreet3_2']"));
		assertEquals("Los Angeles",
			selenium.getValue("//input[@id='_2_addressCity2']"));
		assertEquals("United States",
			selenium.getSelectedLabel("//select[@id='_2_addressCountryId2']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_2_addressRegionId2']"));
	}
}