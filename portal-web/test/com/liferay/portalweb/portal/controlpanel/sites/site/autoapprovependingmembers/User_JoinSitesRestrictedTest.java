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

package com.liferay.portalweb.portal.controlpanel.sites.site.autoapprovependingmembers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_JoinSitesRestrictedTest extends BaseTestCase {
	public void testUser_JoinSitesRestricted() throws Exception {
		selenium.open("/user/selenium01/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=My Sites")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=My Sites", RuntimeVariables.replace("My Sites"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No sites were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.open("/user/selenium01/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Available Sites")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Available Sites",
			RuntimeVariables.replace("Available Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_29_name']",
			RuntimeVariables.replace("Test Restricted Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Test Restricted Community\nThis is an restricted test community!"),
			selenium.getText("//div[1]/div/table/tbody/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//div[1]/div/table/tbody/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("Request Membership"),
			selenium.getText("//tr[4]/td[4]/span/a/span"));
		selenium.clickAt("//tr[4]/td[4]/span/a/span",
			RuntimeVariables.replace("Request Membership"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//textarea[@id='_29_comments']",
			RuntimeVariables.replace(
				"I want to join this really cool community please."));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("xPath=(//div[@class='portlet-msg-success'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Your request was sent. You will receive a reply by email."),
			selenium.getText("xPath=(//div[@class='portlet-msg-success'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"Test Restricted Community\nThis is an restricted test community!"),
			selenium.getText("//div[1]/div/table/tbody/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//div[1]/div/table/tbody/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("Membership Requested"),
			selenium.getText("//td[4]/span/span"));
	}
}