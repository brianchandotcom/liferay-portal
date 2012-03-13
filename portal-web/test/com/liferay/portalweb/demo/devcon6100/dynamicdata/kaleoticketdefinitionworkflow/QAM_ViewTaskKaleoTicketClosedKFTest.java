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
public class QAM_ViewTaskKaleoTicketClosedKFTest extends BaseTestCase {
	public void testQAM_ViewTaskKaleoTicketClosedKF() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
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
		selenium.clickAt("//div[@id='workflowMyTasksPanel']/div[2]/div/div/div/table/tbody/tr[3]/td[2]/a",
			RuntimeVariables.replace("Ticket Process"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Priority Major"),
			selenium.getText("//div[@class='lfr-panel-content']/div[1]"));
		assertEquals(RuntimeVariables.replace("Component Kaleo"),
			selenium.getText("//div[@class='lfr-panel-content']/div[2]"));
		assertEquals(RuntimeVariables.replace(
				"Summary Kaleo Designer does not deploy"),
			selenium.getText("//div[@class='lfr-panel-content']/div[3]"));
		assertEquals(RuntimeVariables.replace("Affects Version/s 6.1.x"),
			selenium.getText("//div[@class='lfr-panel-content']/div[4]"));
		assertEquals(RuntimeVariables.replace(
				"Description A user is unable to deploy the Kaleo Designer portlet"),
			selenium.getText("//div[@class='lfr-panel-content']/div[5]"));
		assertEquals(RuntimeVariables.replace("Attachments test.txt"),
			selenium.getText("//div[@class='lfr-panel-content']/div[6]"));
		assertEquals(RuntimeVariables.replace("Tested Revision 95200"),
			selenium.getText("//div[@class='lfr-panel-content']/div[7]"));
		assertEquals(RuntimeVariables.replace("Tested Status Passed"),
			selenium.getText("//div[@class='lfr-panel-content']/div[8]"));
		assertEquals(RuntimeVariables.replace(
				"Pull Request URL https://github.com"),
			selenium.getText("//div[@class='lfr-panel-content']/div[9]"));
		assertEquals(RuntimeVariables.replace("Status Closed"),
			selenium.getText("//div[@class='lfr-panel-content']/div[10]"));
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText(
				"//a[@id='_1_WAR_kaleoformsportlet_oktaskChangeStatusLink']"));
		selenium.clickAt("//a[@id='_1_WAR_kaleoformsportlet_oktaskChangeStatusLink']",
			RuntimeVariables.replace("OK"));

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

		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText(
				"//div[@class='aui-panel-content aui-dialog-content yui3-widget-stdmod']/div[3]/span/span/button[1]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='aui-panel-content aui-dialog-content yui3-widget-stdmod']/div[3]/span/span/button[1]"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}