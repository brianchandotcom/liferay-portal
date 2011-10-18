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

package com.liferay.portalweb.plugins.kaleo.mysubmissions.workflowtask.viewtaskwebcontentassignedtouser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewTaskWebContentAssignedToUserTest extends BaseTestCase {
	public void testViewTaskWebContentAssignedToUser()
		throws Exception {
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
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[6]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Web Content Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Single Approver: Web Content Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Withdraw Submission"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"Task initially assigned to the Site Owner role."),
			selenium.getText("//div[@class='task-activity task-type-1']/div[2]"));
		assertEquals(RuntimeVariables.replace("Assigned initial task."),
			selenium.getText("//div[@class='task-activity task-type-1']/div[3]"));
		assertEquals(RuntimeVariables.replace(
				"userfn userln assigned the task to himself."),
			selenium.getText(
				"//div[@class='task-activity task-type-1'][2]/div[2]"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset/div/a"));
	}
}