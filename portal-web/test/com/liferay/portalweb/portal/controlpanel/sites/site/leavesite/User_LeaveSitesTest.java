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

package com.liferay.portalweb.portal.controlpanel.sites.site.leavesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_LeaveSitesTest extends BaseTestCase {
	public void testUser_LeaveSites() throws Exception {
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
		selenium.type("//input[@id='_29_name']",
			RuntimeVariables.replace("Test Open Community"));
		selenium.clickAt("xPath=(//input[@value='Search'])[2]",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Open Community"),
			selenium.getText("//div[1]/div/table/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//div[1]/div/table/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Leave"),
			selenium.getText("//span/a/span"));
		selenium.clickAt("//span/a/span", RuntimeVariables.replace("Leave"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
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
	}
}