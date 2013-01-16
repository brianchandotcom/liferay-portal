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

package com.liferay.portalweb.socialoffice.users.user.selectregularrolessouser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewWelcomePageTest extends BaseTestCase {
	public void testSOUs_ViewWelcomePage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//a[contains(.,'Dashboard')]"));
		selenium.clickAt("//a[contains(.,'Dashboard')]",
			RuntimeVariables.replace("Dashboard"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//a[@title='Go to Dashboard']"));
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("link=Dashboard"));
		assertEquals(RuntimeVariables.replace("Contacts Center"),
			selenium.getText("link=Contacts Center"));
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("link=Microblogs"));
		assertEquals(RuntimeVariables.replace("Messages"),
			selenium.getText("link=Messages"));
		assertEquals(RuntimeVariables.replace("My Documents"),
			selenium.getText("link=My Documents"));
		assertEquals(RuntimeVariables.replace("Tasks"),
			selenium.getText("link=Tasks"));
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("link=Welcome"));
		selenium.clickAt("link=Welcome", RuntimeVariables.replace("Welcome"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Language"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title'])[contains(.,'Language')]"));
		assertEquals(RuntimeVariables.replace("My Sites"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title'])[contains(.,'My Sites')]"));
		assertEquals(RuntimeVariables.replace("Dictionary"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title'])[contains(.,'Dictionary')]"));
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title'])[contains(.,'Calendar')]"));
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText(
				"xPath=(//h1[@class='portlet-title'])[contains(.,'Directory')]"));
	}
}