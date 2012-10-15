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
public class ViewEditTasksTaskAssignedToConnectionTest extends BaseTestCase {
	public void testViewEditTasksTaskAssignedToConnection()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
			RuntimeVariables.replace("Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Tasks"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertEquals(RuntimeVariables.replace("Assigned to Me"),
			selenium.getText("link=Assigned to Me"));
		selenium.clickAt("link=Assigned to Me",
			RuntimeVariables.replace("Assigned to Me"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No tasks were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("I Have Created"),
			selenium.getText("link=I Have Created"));
		selenium.clickAt("link=I Have Created",
			RuntimeVariables.replace("I Have Created"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Task Description Edit"),
			selenium.getText("link=Task Description Edit"));
		selenium.clickAt("link=Task Description Edit",
			RuntimeVariables.replace("Task Description Edit"));
		selenium.waitForText("//h1[@class='header-title']",
			"Task Description Edit");
		assertEquals(RuntimeVariables.replace("Task Description Edit"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace(
				"Assigned to Social01 Office01 User01"),
			selenium.getText("//div[@class='task-data assignee']"));
		assertEquals(RuntimeVariables.replace("Open"),
			selenium.getText("//div[@class='task-data status']"));
		assertEquals(RuntimeVariables.replace("Normal"),
			selenium.getText("//div[@class='task-data normal']"));
		selenium.waitForText("//h1[@class='header-title']",
			"Task Description Edit");
		assertEquals(RuntimeVariables.replace("Task Description Edit"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace(
				"Assigned to Social01 Office01 User01"),
			selenium.getText("//div[@class='task-data assignee']"));
		assertTrue(selenium.isPartialText(
				"//div[@class='task-data due-date']", "4/17/17"));
	}
}