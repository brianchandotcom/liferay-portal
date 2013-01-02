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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class GetVacationEventsTest extends BaseTestCase {
	public void testGetVacationEvents() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]	");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Calendar", RuntimeVariables.replace("Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Day", RuntimeVariables.replace("Day"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_8_eventType']",
			RuntimeVariables.replace("Vacation"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Off to Yosemite!"),
			selenium.getText(
				"//div[@class='event-title']/a[contains(.,'Off to Yosemite!')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='event-title']/a[contains(.,'Test Event')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='event-title']/a[contains(.,'Caedmons Call Concert!')]"));
		selenium.clickAt("link=Week", RuntimeVariables.replace("Week"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_8_eventType']",
			RuntimeVariables.replace("Vacation"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Off to Yosemite!"),
			selenium.getText("//tr[contains(.,'Off to Yosemite!')]/td[1]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//tr[contains(.,'Test Event')]/td[1]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//tr[contains(.,'Caedmons Call Concert!')]/td[1]/a"));
		selenium.clickAt("link=Month", RuntimeVariables.replace("Month"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_8_eventType']",
			RuntimeVariables.replace("Vacation"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Off to Yosemite!"),
			selenium.getText("//tr[contains(.,'Off to Yosemite!')]/td[1]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//tr[contains(.,'Test Event')]/td[1]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//tr[contains(.,'Caedmons Call Concert!')]/td[1]/a"));
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Off to Yosemite!"),
			selenium.getText("//tr[contains(.,'Off to Yosemite!')]/td[3]/a"));
	}
}