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

package com.liferay.portalweb.kaleo.myworkflowtasks.workflowtask.approvecommentwebcontentactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentCompletedCommentTest extends BaseTestCase {
	public void testViewWebContentCompletedComment() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=My Workflow Tasks",
					RuntimeVariables.replace("My Workflow Tasks"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Pending",
					RuntimeVariables.replace("Pending"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"There are no pending tasks assigned to you."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-info'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"There are no pending tasks assigned to your roles."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-info'])[2]"));
				selenium.clickAt("link=Completed",
					RuntimeVariables.replace("Completed"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Review"),
					selenium.getText("//td[1]/a"));
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Web Content"),
					selenium.getText("//td[3]/a"));
				assertTrue(selenium.isVisible("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("Never"),
					selenium.getText("//td[5]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Web Content Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Activities"),
					selenium.getText(
						"//div[@class='lfr-panel-container task-panel-container']/div[2]/div/div/span"));

				boolean activities1NotVisible = selenium.isVisible(
						"//div[@class='task-activity task-type-3']/div[2]");

				if (activities1NotVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-panel-container task-panel-container']/div[2]/div/div/span",
					RuntimeVariables.replace("Activities"));
				selenium.waitForVisible(
					"//div[@class='task-activity task-type-3']/div[2]");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Joe Bloggs completed the task Review."),
					selenium.getText(
						"//div[@class='task-activity task-type-3']/div[2]"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Comment Approve"),
					selenium.getText(
						"//div[@class='task-activity task-type-3']/div[3]"));
				selenium.clickAt("link=My Submissions",
					RuntimeVariables.replace("My Submissions"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Pending",
					RuntimeVariables.replace("Pending"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"There are no pending publications requested by me."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				selenium.clickAt("link=Completed",
					RuntimeVariables.replace("Completed"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Web Content Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Activities"),
					selenium.getText(
						"//div[@class='lfr-panel-container task-panel-container']/div[2]/div/div/span"));
				selenium.clickAt("//div[@class='lfr-panel-container task-panel-container']/div[2]/div/div/span",
					RuntimeVariables.replace("Activities"));
				selenium.waitForVisible(
					"//div[@class='task-activity task-type-3']/div[2]");
				assertEquals(RuntimeVariables.replace(
						"Joe Bloggs completed the task Review."),
					selenium.getText(
						"//div[@class='task-activity task-type-3']/div[2]"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Comment Approve"),
					selenium.getText(
						"//div[@class='task-activity task-type-3']/div[3]"));
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//a[contains(@title,'Web Content Name')]/div/img"));
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText(
						"//a[@class='entry-link']/span[@class='entry-title']"));
				assertFalse(selenium.isPartialText(
						"//a[@class='entry-link']/span[@class='entry-title']",
						"(Pending)"));
				selenium.clickAt("//a[@class='entry-link']/span[@class='entry-title']",
					RuntimeVariables.replace("Web Content Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Approved"),
					selenium.getText(
						"//strong[@class='workflow-status-approved']"));
				selenium.clickAt("link=Workflow",
					RuntimeVariables.replace("Workflow"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Submissions",
					RuntimeVariables.replace("Submissions"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Pending",
					RuntimeVariables.replace("Pending"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"There are no pending publication requests."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				selenium.clickAt("link=Completed",
					RuntimeVariables.replace("Completed"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Web Content Name"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Web Content Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Activities"),
					selenium.getText(
						"//div[@class='lfr-panel-container task-panel-container']/div[3]/div/div/span"));

				boolean activities2NotVisible = selenium.isVisible(
						"//div[@class='task-activity task-type-3']/div[2]");

				if (activities2NotVisible) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-panel-container task-panel-container']/div[3]/div/div/span",
					RuntimeVariables.replace("Activities"));
				selenium.waitForVisible(
					"//div[@class='task-activity task-type-3']/div[2]");

			case 3:
				assertEquals(RuntimeVariables.replace(
						"Joe Bloggs completed the task Review."),
					selenium.getText(
						"//div[@class='task-activity task-type-3']/div[2]"));
				assertEquals(RuntimeVariables.replace(
						"Web Content Comment Approve"),
					selenium.getText(
						"//div[@class='task-activity task-type-3']/div[3]"));

			case 100:
				label = -1;
			}
		}
	}
}