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
public class KD_CompleteFormInProgressTaskKFTest extends BaseTestCase {
	public void testKD_CompleteFormInProgressTaskKF() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Ticket Process"),
			selenium.getText(
				"//div[@id='workflowMyTasksPanel']/div[2]/div/div/div/table/tbody/tr[3]/td[2]"));
		Thread.sleep(5000);
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));

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

		assertEquals(RuntimeVariables.replace("Complete Form"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='aui-panel-content aui-dialog-content yui3-widget-stdmod']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_1_WAR_kaleoformsportlet_PullRequestURL']",
			RuntimeVariables.replace("https://github.com"));
		selenium.select("//select[@id='_1_WAR_kaleoformsportlet_status']",
			RuntimeVariables.replace("label=In Progress"));
		selenium.clickAt("//input[@id='_1_WAR_kaleoformsportlet_saveButton']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("There are no tasks."),
			selenium.getText("//div[@id='workflowMyTasksPanel']/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("There are no tasks."),
			selenium.getText(
				"//div[@id='workflowMyRolesTasksPanel']/div[2]/div[1]"));
	}
}