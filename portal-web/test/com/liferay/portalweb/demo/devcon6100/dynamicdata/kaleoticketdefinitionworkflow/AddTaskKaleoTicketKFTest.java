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

package com.liferay.portalweb.demo.devcon6100.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTaskKaleoTicketKFTest extends BaseTestCase {
	public void testAddTaskKaleoTicketKF() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Kaleo Forms Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Kaleo Forms Test Page",
			RuntimeVariables.replace("Kaleo Forms Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Submit New"),
			selenium.getText("//span[@title='Submit New']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Submit New']/ul/li/strong/a",
			RuntimeVariables.replace("Submit New"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Ticket Process"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a",
			RuntimeVariables.replace("Ticket Process"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_priority']",
			RuntimeVariables.replace("Major"));
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_component_name']",
			RuntimeVariables.replace("Kaleo"));
		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_summary']",
			RuntimeVariables.replace("Kaleo Designer does not deploy"));
		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_affect_version']",
			RuntimeVariables.replace("6.1.x"));
		selenium.type("//textarea[@id='_1_WAR_kaleoformsportlet_description']",
			RuntimeVariables.replace(
				"A user is unable to deploy the Kaleo Designer portlet"));
		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_attachments']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\demo\\devcon6100\\dynamicdata\\kaleoticketdefinitionworkflow\\dependencies\\test.txt"));
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_status']",
			RuntimeVariables.replace("Open"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}