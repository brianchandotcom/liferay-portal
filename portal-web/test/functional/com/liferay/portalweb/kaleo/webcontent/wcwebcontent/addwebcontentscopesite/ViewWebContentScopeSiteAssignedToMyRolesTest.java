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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWebContentScopeSiteAssignedToMyRolesTest extends BaseTestCase {
	public void testViewWebContentScopeSiteAssignedToMyRoles()
		throws Exception {
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
		selenium.waitForVisible("//span[@title='Liferay']/ul/li/strong/a/span");
		selenium.clickAt("//span[@title='Liferay']/ul/li/strong/a/span",
			RuntimeVariables.replace("Scope Selector"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@title='Site Name']/ul/li/strong/a/span",
			"Site Name");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//span[@title='Site Name']/ul/li/strong/a/span"));
		selenium.clickAt("link=My Workflow Tasks",
			RuntimeVariables.replace("My Workflow Tasks"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace(
				"There are no pending tasks assigned to you."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[3]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'WC WebContent Title')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[5]/a"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace("There are no completed tasks."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=My Submissions",
			RuntimeVariables.replace("My Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//tr[contains(.,'Single Approver')]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[4]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Single Approver')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[6]/a"));
		assertEquals(RuntimeVariables.replace("Withdraw Submission"),
			selenium.getText(
				"//tr[contains(.,'Single Approver')]/td[7]/span/a/span"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no completed publications requested by me."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title (Pending)"),
			selenium.getText(
				"//a[@class='entry-link']/span[@class='entry-title']"));
		selenium.clickAt("link=Workflow", RuntimeVariables.replace("Workflow"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Submissions",
			RuntimeVariables.replace("Submissions"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//tr[contains(.,'Single Approver')]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Single Approver"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Review"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[4]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Single Approver')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Single Approver')]/td[6]/a"));
		selenium.clickAt("link=Completed", RuntimeVariables.replace("Completed"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There are no completed publication requests."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}