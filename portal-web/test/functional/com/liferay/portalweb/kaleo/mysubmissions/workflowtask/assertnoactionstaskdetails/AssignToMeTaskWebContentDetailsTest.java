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

package com.liferay.portalweb.kaleo.mysubmissions.workflowtask.assertnoactionstaskdetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignToMeTaskWebContentDetailsTest extends BaseTestCase {
	public void testAssignToMeTaskWebContentDetails() throws Exception {
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
				selenium.clickAt("link=Workflow",
					RuntimeVariables.replace("Workflow"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Submissions",
					RuntimeVariables.replace("Submissions"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Pending",
					RuntimeVariables.replace("Pending"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Single Approver"),
					selenium.getText("//td[1]/a"));
				assertEquals(RuntimeVariables.replace("WC WebContent Title"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Web Content"),
					selenium.getText("//td[3]/a"));
				assertEquals(RuntimeVariables.replace("Review"),
					selenium.getText("//td[4]/a"));
				assertTrue(selenium.isElementPresent("//td[5]/a"));
				assertEquals(RuntimeVariables.replace("Never"),
					selenium.getText("//td[6]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("WC WebContent Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Review"),
					selenium.getText("//tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("Never"),
					selenium.getText("//tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("No"),
					selenium.getText("//tr[3]/td[3]"));
				assertFalse(selenium.isTextPresent(
						"Joe Bloggs assigned the task to himself."));
				assertEquals(RuntimeVariables.replace("Assign to Me"),
					selenium.getText(
						"//a[@id='_151_workflowTasksSearchContainer_1_menu_assign-to-me_1']"));
				selenium.clickAt("//a[@id='_151_workflowTasksSearchContainer_1_menu_assign-to-me_1']",
					RuntimeVariables.replace("Assign to Me"));
				selenium.waitForVisible(
					"//span[@class='toolbar-content']/button[contains(.,'OK')]");
				selenium.clickAt("//span[@class='toolbar-content']/button[contains(.,'OK')]",
					RuntimeVariables.replace("OK"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Review"),
					selenium.getText("//tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("Never"),
					selenium.getText("//tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("No"),
					selenium.getText("//tr[3]/td[3]"));

				boolean activitiesExpanded = selenium.isVisible(
						"//div[@class='task-activity task-type-1'][2]/div[2]");

				if (activitiesExpanded) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-panel-container task-panel-container']/div[3]/div[1]/a",
					RuntimeVariables.replace("Expand"));
				selenium.waitForVisible(
					"//div[@class='task-activity task-type-1'][2]/div[2]");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Joe Bloggs assigned the task to himself."),
					selenium.getText(
						"//div[@class='task-activity task-type-1'][2]/div[2]"));

			case 100:
				label = -1;
			}
		}
	}
}