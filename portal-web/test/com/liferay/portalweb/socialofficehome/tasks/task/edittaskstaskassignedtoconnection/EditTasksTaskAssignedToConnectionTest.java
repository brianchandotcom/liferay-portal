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

package com.liferay.portalweb.socialofficehome.tasks.task.edittaskstaskassignedtoconnection;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditTasksTaskAssignedToConnectionTest extends BaseTestCase {
	public void testEditTasksTaskAssignedToConnection()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/so/dashboard/");

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
				assertEquals(RuntimeVariables.replace("Tasks"),
					selenium.getText("//span[@class='portlet-title-default']"));
				assertEquals(RuntimeVariables.replace("I Have Created"),
					selenium.getText("link=I Have Created"));
				selenium.clickAt("link=I Have Created",
					RuntimeVariables.replace("I Have Created"));
				selenium.waitForPageToLoad("30000");
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
				assertEquals(RuntimeVariables.replace(
						"Assigned to Social01 Office01 User01"),
					selenium.getText("//div[@class='task-data assignee']"));
				assertEquals(RuntimeVariables.replace("Open"),
					selenium.getText("//div[@class='task-data status']"));
				assertEquals(RuntimeVariables.replace("Normal"),
					selenium.getText("//div[@class='task-data normal']"));
				selenium.clickAt("//input[@value='Edit']",
					RuntimeVariables.replace("Edit"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_1_WAR_tasksportlet_title']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_1_WAR_tasksportlet_title']",
					RuntimeVariables.replace("Task Description Edit"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isElementPresent(
						"//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']"));

				boolean neverDueChecked = selenium.isChecked(
						"_1_WAR_tasksportlet_neverDueCheckbox");

				if (!neverDueChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']",
					RuntimeVariables.replace("Uncheck Never Due"));

			case 2:
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateMonth']",
					RuntimeVariables.replace("label=April"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateDay']",
					RuntimeVariables.replace("label=17"));
				selenium.select("//select[@id='_1_WAR_tasksportlet_dueDateYear']",
					RuntimeVariables.replace("label=2017"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Task Description Edit")
												.equals(selenium.getText(
										"//h1[@class='header-title']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Task Description Edit"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace(
						"Assigned to Social01 Office01 User01"),
					selenium.getText("//div[@class='task-data assignee']"));
				assertTrue(selenium.isPartialText(
						"//div[@class='task-data due-date']", "4/17/17"));

			case 100:
				label = -1;
			}
		}
	}
}