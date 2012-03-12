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

package com.liferay.portalweb.properties.users.screennamenumeric.erroradduserscreennamenumberorganizationid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ErrorAddUserScreenNameNumberOrganizationIdTest extends BaseTestCase {
	public void testErrorAddUserScreenNameNumberOrganizationId()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

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
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Selenium"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Selenium"),
			selenium.getText("//td[2]//strong"));
		selenium.clickAt("//span[@title='Actions']/ul",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		String OrganizationID = selenium.getFirstNumber(
				"//fieldset[1]/div/div[3]/div");
		RuntimeVariables.setValue("OrganizationID", OrganizationID);
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace(RuntimeVariables.getValue("OrganizationID")));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("testA@selenium.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("testA"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("testA"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Please enter a valid screen name."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}