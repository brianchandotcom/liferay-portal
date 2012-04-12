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

package com.liferay.portalweb.socialofficehome.tasks.task.deletetaskstaskassignedtomecomment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteTasksTaskAssignedToMeCommentTest extends BaseTestCase {
	public void testDeleteTasksTaskAssignedToMeComment()
		throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//nav/ul/li[contains(.,'Tasks')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
			RuntimeVariables.replace("Tasks"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Tasks"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertEquals(RuntimeVariables.replace("Assigned to Me"),
			selenium.getText("link=Assigned to Me"));
		selenium.clickAt("link=Assigned to Me",
			RuntimeVariables.replace("Assigned to Me"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("link=Task Description"));
		selenium.clickAt("link=Task Description",
			RuntimeVariables.replace("Task Description"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Task Description")
										.equals(selenium.getText(
								"//h1[@class='header-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
			selenium.getText("//div[@class='task-data assignee']"));
		assertEquals(RuntimeVariables.replace("Task Comment"),
			selenium.getText("//div[@class='comment-body']/span"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("link=Delete"));
		selenium.clickAt("link=Delete", RuntimeVariables.replace("Delete"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (!selenium.isElementPresent(
							"//div[@class='comment-body']/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent(
				"//div[@class='comment-body']/span"));
	}
}