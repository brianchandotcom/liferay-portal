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

package com.liferay.portalweb.plugins.kaleo.myworkflowtasks.workflowtask.viewwebcontenttaskactivitiesassignedtome;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentTaskActivitiesAssignedToMeTest extends BaseTestCase {
	public void testViewWebContentTaskActivitiesAssignedToMe()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=My Workflow Tasks",
			RuntimeVariables.replace("My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/div[2]/div[1]/div/span",
			RuntimeVariables.replace("Activities"));
		assertEquals(RuntimeVariables.replace(
				"Task initially assigned to the Site Owner role."),
			selenium.getText("//div[2]/div[2]/div[1]/div[2]"));
		assertEquals(RuntimeVariables.replace("Assigned initial task."),
			selenium.getText("//div[2]/div[2]/div[1]/div[3]"));
		assertEquals(RuntimeVariables.replace(
				"Joe Bloggs assigned the task to himself."),
			selenium.getText("//div[2]/div[2]/div[2]/div[2]/div[2]"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/div[3]/div[1]/div",
			RuntimeVariables.replace("Activities"));
		assertEquals(RuntimeVariables.replace(
				"Task initially assigned to the Site Owner role."),
			selenium.getText("//div[3]/div[2]/div[1]/div[2]"));
		assertEquals(RuntimeVariables.replace("Assigned initial task."),
			selenium.getText("//div[3]/div[2]/div[1]/div[3]"));
		assertEquals(RuntimeVariables.replace(
				"Joe Bloggs assigned the task to himself."),
			selenium.getText("//div[3]/div[2]/div[2]/div[2]"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Workflow", RuntimeVariables.replace("Workflow"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Submissions",
			RuntimeVariables.replace("Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/div[3]/div[1]/div",
			RuntimeVariables.replace("Activities"));
		assertEquals(RuntimeVariables.replace(
				"Task initially assigned to the Site Owner role."),
			selenium.getText("//div[3]/div[2]/div[1]/div[2]"));
		assertEquals(RuntimeVariables.replace("Assigned initial task."),
			selenium.getText("//div[3]/div[2]/div[1]/div[3]"));
		assertEquals(RuntimeVariables.replace(
				"Joe Bloggs assigned the task to himself."),
			selenium.getText("//div[3]/div[2]/div[2]/div[2]"));
	}
}