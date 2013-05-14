/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class QAM_AssignToMeTaskKFTest extends BaseTestCase {
	public void testQAM_AssignToMeTaskKF() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Kaleo Forms Test Page");
		selenium.clickAt("link=Kaleo Forms Test Page",
			RuntimeVariables.replace("Kaleo Forms Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Ticket Process"),
			selenium.getText(
				"//div[@id='workflowMyRolesTasksPanel']//tr[3]/td[2]"));
		Thread.sleep(5000);
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Assign to Me"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		selenium.waitForVisible(
			"//div[@class='panel-content dialog-content yui3-widget-stdmod']");
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText(
				"//div[@class='panel-content dialog-content yui3-widget-stdmod']/div[3]/span/span/button[1]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='panel-content dialog-content yui3-widget-stdmod']/div[3]/span/span/button[1]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Ticket Process"),
			selenium.getText("//div[@id='workflowMyTasksPanel']//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("There are no tasks."),
			selenium.getText(
				"//div[@id='workflowMyRolesTasksPanel']/div[2]/div[1]"));
	}
}