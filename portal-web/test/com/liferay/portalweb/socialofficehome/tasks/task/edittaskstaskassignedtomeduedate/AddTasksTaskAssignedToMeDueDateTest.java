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

package com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtomeduedate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTasksTaskAssignedToMeDueDateTest extends BaseTestCase {
	public void testAddTasksTaskAssignedToMeDueDate() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertEquals(RuntimeVariables.replace("No tasks were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("Add Task"),
					selenium.getText("link=Add Task"));
				selenium.clickAt("link=Add Task",
					RuntimeVariables.replace("Add Task"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//h1[@class='header-title']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Add Task"),
					selenium.getText("//h1[@class='header-title']"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@class='input-task-description lfr-input-text']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@class='input-task-description lfr-input-text']",
					RuntimeVariables.replace("Task Description"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_assigneeUserId']",
					RuntimeVariables.replace("label=Joe Bloggs"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_priority']",
					RuntimeVariables.replace("label=Normal"));

				boolean neverDueChecked = selenium.isChecked(
						"_1_WAR_tasksportlet_neverDueCheckbox");

				if (!neverDueChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']",
					RuntimeVariables.replace("Never Due"));

			case 2:
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateMonth']",
					RuntimeVariables.replace("label=March"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateDay']",
					RuntimeVariables.replace("label=16"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateYear']",
					RuntimeVariables.replace("label=2016"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));

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

			case 100:
				label = -1;
			}
		}
	}
}