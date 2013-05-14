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
public class AddTaskJavascriptTicketKFTest extends BaseTestCase {
	public void testAddTaskJavascriptTicketKF() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Kaleo Forms Test Page");
		selenium.clickAt("link=Kaleo Forms Test Page",
			RuntimeVariables.replace("Kaleo Forms Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.click("//span[@title='Submit New']/ul/li/strong/a");
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Ticket Process"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
			RuntimeVariables.replace("Ticket Process"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_priority']",
			RuntimeVariables.replace("Critical"));
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_component_name']",
			RuntimeVariables.replace("Javascript"));
		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_summary']",
			RuntimeVariables.replace(
				"Options field is throwing JavaScript errors"));
		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_affect_version']",
			RuntimeVariables.replace("6.1.x"));
		selenium.type("//textarea[@id='_1_WAR_kaleoformsportlet_description']",
			RuntimeVariables.replace(
				"Editing the options field throws a JavaScript error in the console"));
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_status']",
			RuntimeVariables.replace("Open"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}