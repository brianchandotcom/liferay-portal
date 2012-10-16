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

package com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbytags;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTasksTaskAssignedToMeTagPortalTest extends BaseTestCase {
	public void testAddTasksTaskAssignedToMeTagPortal()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Tasks Test Page",
					RuntimeVariables.replace("Tasks Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("No tasks were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("Add Task"),
					selenium.getText("link=Add Task"));
				selenium.clickAt("link=Add Task",
					RuntimeVariables.replace("Add Task"));
				selenium.waitForVisible(
					"//input[@id='_1_WAR_tasksportlet_title']");
				selenium.type("//input[@id='_1_WAR_tasksportlet_title']",
					RuntimeVariables.replace("Portal Task Description"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_assigneeUserId']",
					RuntimeVariables.replace("label=Joe Bloggs"));
				selenium.select("//select[@name='_1_WAR_tasksportlet_priority']",
					RuntimeVariables.replace("label=Normal"));

				boolean neverDueChecked = selenium.isChecked(
						"_1_WAR_tasksportlet_neverDueCheckbox");

				if (neverDueChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']",
					RuntimeVariables.replace(
						"Enable propagation of changes from the site template."));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_1_WAR_tasksportlet_neverDueCheckbox']"));
				selenium.waitForVisible("//input[@title='Add Tags']");
				selenium.type("//input[@title='Add Tags']",
					RuntimeVariables.replace("PortalTag1"));
				selenium.waitForVisible("//button[@id='add']");
				selenium.clickAt("//button[@id='add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible("//li[contains(.,'Tag1')]/span/span");
				assertEquals(RuntimeVariables.replace("PortalTag1"),
					selenium.getText("//li[contains(.,'Tag1')]/span/span"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForText("//h1[@class='header-title']",
					"Portal Task Description");
				assertEquals(RuntimeVariables.replace("Portal Task Description"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace("Assigned to Joe Bloggs"),
					selenium.getText("//div[@class='task-data assignee']"));
				assertEquals(RuntimeVariables.replace("portaltag1"),
					selenium.getText("//span[@class='tag']"));

			case 100:
				label = -1;
			}
		}
	}
}