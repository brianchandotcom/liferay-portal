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

package com.liferay.portalweb.socialofficehome.upcomingtasks.task.sousviewtaskstaskassignedtoconnectionut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewTasksTaskAssignedToConnectionUTTest extends BaseTestCase {
	public void testSOUs_ViewTasksTaskAssignedToConnectionUT()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForText("//h1/span[contains(.,'Upcoming Tasks')]",
			"Upcoming Tasks");
		assertEquals(RuntimeVariables.replace("Upcoming Tasks"),
			selenium.getText("//h1/span[contains(.,'Upcoming Tasks')]"));
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("//li[@class='tasks-title normal']/a"));
		selenium.clickAt("//li[@class='tasks-title normal']/a",
			RuntimeVariables.replace("Task Description"));
		selenium.waitForText("//h1[@class='header-title']", "Task Description");
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace(
				"Assigned to Social01 Office01 User01"),
			selenium.getText("//div[@class='task-data assignee']"));
		assertEquals(RuntimeVariables.replace("Open"),
			selenium.getText("//div[@class='task-data status']"));
		assertEquals(RuntimeVariables.replace("Normal"),
			selenium.getText("//div[@class='task-data normal']"));
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForText("//h1/span[contains(.,'Upcoming Tasks')]",
			"Upcoming Tasks");
		assertEquals(RuntimeVariables.replace("Upcoming Tasks"),
			selenium.getText("//h1/span[contains(.,'Upcoming Tasks')]"));
		assertEquals(RuntimeVariables.replace("View All Tasks"),
			selenium.getText("//div[@class='view-all-tasks']/a"));
		selenium.clickAt("//div[@class='view-all-tasks']/a",
			RuntimeVariables.replace("View All Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("link=Task Description"));
	}
}