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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentnoworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentNoWorkflowTest extends BaseTestCase {
	public void testViewWebContentNoWorkflow() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Pending"),
			selenium.getText("link=Pending"));
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to you."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to your roles."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("There are no completed tasks."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Pending"),
			selenium.getText("link=Pending"));
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no pending publications requested by me."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no completed publications requested by me."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//a[@class='entry-link']/span"));
		selenium.clickAt("link=Workflow", RuntimeVariables.replace("Workflow"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Submissions"),
			selenium.getText("link=Submissions"));
		selenium.clickAt("link=Submissions",
			RuntimeVariables.replace("Submissions"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Pending"),
			selenium.getText("link=Pending"));
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no pending publication requests."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no completed publication requests."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}