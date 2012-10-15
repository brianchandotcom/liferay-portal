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

package com.liferay.portalweb.socialofficehome.tasks.task.filtertasksfilterbyplace;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class FilterTasksFilterByPlaceTest extends BaseTestCase {
	public void testFilterTasksFilterByPlace() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Portal Task Description"),
			selenium.getText("//td[1]/div[1]/a"));
		assertTrue(selenium.isPartialText("//td[1]/div[2]/span[1]",
				"Site: Liferay"));
		assertEquals(RuntimeVariables.replace("Task Description"),
			selenium.getText("//tr[4]/td[1]/div[1]/a"));
		assertTrue(selenium.isVisible("//a[@class='filter-tasks']"));
		selenium.clickAt("//a[@class='filter-tasks']",
			RuntimeVariables.replace("Filter"));
		selenium.waitForVisible("//h3[contains(.,'Filter by Place')]");
		assertEquals(RuntimeVariables.replace("Filter by Place"),
			selenium.getText("//h3[contains(.,'Filter by Place')]"));
		selenium.waitForVisible(
			"//div[@class='filter-container group-filter']/select");
		selenium.select("//div[@class='filter-container group-filter']/select",
			RuntimeVariables.replace("label=Liferay"));
		selenium.waitForElementNotPresent("//tr[4]/td[1]/div[1]/a");
		assertTrue(selenium.isElementNotPresent("//tr[4]/td[1]/div[1]/a"));
		assertEquals(RuntimeVariables.replace("Portal Task Description"),
			selenium.getText("//td[1]/div[1]/a"));
		assertTrue(selenium.isPartialText("//td[1]/div[2]/span[1]",
				"Site: Liferay"));
	}
}