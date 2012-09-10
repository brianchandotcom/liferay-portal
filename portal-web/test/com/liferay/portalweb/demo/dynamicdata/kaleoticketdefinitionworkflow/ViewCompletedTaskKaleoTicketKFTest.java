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

package com.liferay.portalweb.demo.dynamicdata.kaleoticketdefinitionworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCompletedTaskKaleoTicketKFTest extends BaseTestCase {
	public void testViewCompletedTaskKaleoTicketKF() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Kaleo Forms Test Page");
				selenium.clickAt("link=Kaleo Forms Test Page",
					RuntimeVariables.replace("Kaleo Forms Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=My Completed Requests",
					RuntimeVariables.replace("My Completed Requests"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Ticket Process"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("EndNode"),
					selenium.getText("//tr[3]/td[2]/a"));
				selenium.clickAt("//tr[3]/td[1]/a",
					RuntimeVariables.replace("Ticket Process"));
				selenium.waitForPageToLoad("30000");
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
				assertEquals(RuntimeVariables.replace(
						"Attachments Document_1.txt"),
					selenium.getText("//div[@class='lfr-panel-content']/div[6]"));
				assertEquals(RuntimeVariables.replace("Tested Revision 95200"),
					selenium.getText("//div[@class='lfr-panel-content']/div[7]"));
				assertEquals(RuntimeVariables.replace("Tested Status Passed"),
					selenium.getText("//div[@class='lfr-panel-content']/div[8]"));
				assertEquals(RuntimeVariables.replace(
						"Pull Request URL https://github.com"),
					selenium.getText("//div[@class='lfr-panel-content']/div[9]"));
				assertEquals(RuntimeVariables.replace("Status Closed"),
					selenium.getText(
						"//div[@class='lfr-panel-content']/div[10]"));

				boolean activityVisible = selenium.isVisible(
						"xPath=(//div[@class='task-activity-message'])[1]");

				if (activityVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/div/a",
					RuntimeVariables.replace("Plus"));

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Task initially assigned to the Kaleo Developer role."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[1]"));
				assertEquals(RuntimeVariables.replace("Assigned initial task."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-comment'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Kaleo Developer assigned the task to himself."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[2]"));
				assertEquals(RuntimeVariables.replace(
						"Kaleo Developer completed the task Developer."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[3]"));
				assertEquals(RuntimeVariables.replace(
						"Task initially assigned to the Code Reviewer role."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[4]"));
				assertEquals(RuntimeVariables.replace("Assigned initial task."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-comment'])[4]"));
				assertEquals(RuntimeVariables.replace(
						"Code Reviewer assigned the task to himself."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[5]"));
				assertEquals(RuntimeVariables.replace(
						"Code Reviewer completed the task Code Review."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[6]"));
				assertEquals(RuntimeVariables.replace(
						"Task initially assigned to the QA Engineer role."),
					selenium.getText(
						" xPath=(//div[@class='task-activity-message'])[contains(.,'QA Engineer')]"));
				assertEquals(RuntimeVariables.replace("Assigned initial task."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-comment'])[7]"));
				assertEquals(RuntimeVariables.replace(
						"Task initially assigned to the QA Manager role."),
					selenium.getText(
						" xPath=(//div[@class='task-activity-message'])[contains(.,'QA Manager')]"));
				assertEquals(RuntimeVariables.replace("Assigned initial task."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-comment'])[8]"));
				assertEquals(RuntimeVariables.replace(
						"QA Engineer assigned the task to himself."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[9]"));
				assertEquals(RuntimeVariables.replace(
						"QA Engineer completed the task QA."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[10]"));
				assertEquals(RuntimeVariables.replace(
						"QA Manager assigned the task to himself."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[11]"));
				assertEquals(RuntimeVariables.replace(
						"QA Manager completed the task QA Management."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[12]"));
				assertEquals(RuntimeVariables.replace(
						"Task initially assigned to the Project Manager role."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[13]"));
				assertEquals(RuntimeVariables.replace("Assigned initial task."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-comment'])[13]"));
				assertEquals(RuntimeVariables.replace(
						"Project Manager assigned the task to himself."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[14]"));
				assertEquals(RuntimeVariables.replace(
						"Project Manager completed the task Project Manager Review."),
					selenium.getText(
						"xPath=(//div[@class='task-activity-message'])[15]"));

			case 100:
				label = -1;
			}
		}
	}
}