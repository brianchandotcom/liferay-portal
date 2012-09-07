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

package com.liferay.portalweb.socialofficehome.events.event.vieweventmultipleed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEventMultipleEDTest extends BaseTestCase {
	public void testViewEventMultipleED() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForText("xPath=(//span[@class='portlet-title-text'])[4]",
			"Events");
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		assertTrue(selenium.isPartialText("//h2[contains(.,'Events')]", "Events"));
		assertEquals(RuntimeVariables.replace("Calendar Event1 Title"),
			selenium.getText("xPath=(//span[@class='event-name']/a)[1]"));
		selenium.clickAt("xPath=(//span[@class='event-name']/a)[1]",
			RuntimeVariables.replace("Calendar Event1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event1 Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Calendar Event1 Description"),
			selenium.getText("//p[2]"));
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForText("xPath=(//span[@class='portlet-title-text'])[4]",
			"Events");
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		assertTrue(selenium.isPartialText("//h2[contains(.,'Events')]", "Events"));
		assertEquals(RuntimeVariables.replace("Calendar Event2 Title"),
			selenium.getText("xPath=(//span[@class='event-name']/a)[2]"));
		selenium.clickAt("xPath=(//span[@class='event-name']/a)[2]",
			RuntimeVariables.replace("Calendar Event2 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event2 Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Calendar Event2 Description"),
			selenium.getText("//p[2]"));
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForText("xPath=(//span[@class='portlet-title-text'])[4]",
			"Events");
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		assertTrue(selenium.isPartialText("//h2[contains(.,'Events')]", "Events"));
		assertEquals(RuntimeVariables.replace("Calendar Event3 Title"),
			selenium.getText("xPath=(//span[@class='event-name']/a)[3]"));
		selenium.clickAt("xPath=(//span[@class='event-name']/a)[3]",
			RuntimeVariables.replace("Calendar Event3 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event3 Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Calendar Event3 Description"),
			selenium.getText("//p[2]"));
	}
}