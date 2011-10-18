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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationservice.addorganizationservicemultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationService3Test extends BaseTestCase {
	public void testAddOrganizationService3() throws Exception {
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
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Selenium"),
			selenium.getText("//a[2]/strong"));
		selenium.clickAt("//a[2]/strong", RuntimeVariables.replace("Selenium"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@id='_125_servicesLink']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[@id='_125_servicesLink']",
			RuntimeVariables.replace("Services"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/span/span/button[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[2]/span/span/button[1]",
			RuntimeVariables.replace("Add Row"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//select[@id='_125_orgLaborTypeId3']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[@id='_125_orgLaborTypeId3']",
			RuntimeVariables.replace("label=Training"));
		selenium.select("//select[@id='_125_sunOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_sunClose3']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_monOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_monClose3']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_tueOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_tueClose3']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_wedOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_wedClose3']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_thuOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_thuClose3']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_friOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_friClose3']",
			RuntimeVariables.replace("label=05:00"));
		selenium.select("//select[@id='_125_satOpen3']",
			RuntimeVariables.replace("label=09:00"));
		selenium.select("//select[@id='_125_satClose3']",
			RuntimeVariables.replace("label=05:00"));
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
		assertEquals("Training",
			selenium.getSelectedLabel("//select[@id='_125_orgLaborTypeId2']"));
	}
}