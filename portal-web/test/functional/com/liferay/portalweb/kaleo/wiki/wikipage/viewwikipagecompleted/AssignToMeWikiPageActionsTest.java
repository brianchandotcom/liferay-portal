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

package com.liferay.portalweb.kaleo.wiki.wikipage.viewwikipagecompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignToMeWikiPageActionsTest extends BaseTestCase {
	public void testAssignToMeWikiPageActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to you."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page"),
			selenium.getText("//td[3]/a"));
		assertTrue(selenium.isVisible("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign to Me')]/a");
		assertEquals(RuntimeVariables.replace("Assign to Me"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign to Me')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign to Me')]/a",
			RuntimeVariables.replace("Assign to Me"));
		selenium.waitForVisible("//div[3]/span/span/button");
		assertEquals(RuntimeVariables.replace("OK"),
			selenium.getText("//div[3]/span/span/button"));
		selenium.clickAt("//div[3]/span/span/button",
			RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page Title"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Wiki Page"),
			selenium.getText("//td[3]/a"));
		assertTrue(selenium.isVisible("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//td[5]/a"));
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to your roles."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}