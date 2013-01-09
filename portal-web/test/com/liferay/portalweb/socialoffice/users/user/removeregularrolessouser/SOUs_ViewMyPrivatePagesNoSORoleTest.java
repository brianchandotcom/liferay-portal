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

package com.liferay.portalweb.socialoffice.users.user.removeregularrolessouser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewMyPrivatePagesNoSORoleTest extends BaseTestCase {
	public void testSOUs_ViewMyPrivatePagesNoSORole() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("//span[contains(.,'My Private Pages')]");
		selenium.clickAt("//span[contains(.,'My Private Pages')]",
			RuntimeVariables.replace("My Private Pages"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//a[@title='Go to Social01 Office01 User01']"));
		assertTrue(selenium.isElementNotPresent("link=Dashboard"));
		assertTrue(selenium.isElementNotPresent("link=Contacts Center"));
		assertTrue(selenium.isElementNotPresent("link=Microblogs"));
		assertTrue(selenium.isElementNotPresent("link=Messages"));
		assertTrue(selenium.isElementNotPresent("link=My Documents"));
		assertTrue(selenium.isElementNotPresent("link=Tasks"));
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
		assertFalse(selenium.isTextPresent("Dashboard"));
	}
}