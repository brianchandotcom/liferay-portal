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

package com.liferay.portalweb.socialofficehome.events.event.configureportletdisplaydays2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventDisplayDays2SOTest extends BaseTestCase {
	public void testAddEventDisplayDays2SO() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");

		String dayNumber = selenium.getFirstNumberIncrement(
				"//div[@class='day-number']");
		RuntimeVariables.setValue("dayNumber", dayNumber);
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_8_startDateDay']",
			RuntimeVariables.replace(RuntimeVariables.getValue("dayNumber")));
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Calendar Event Title"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_48_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_48_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__8_editor']/textarea");
		selenium.type("//td[@id='cke_contents__8_editor']/textarea",
			RuntimeVariables.replace("Calendar Event Description"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_48_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_48_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__8_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__8_editor']/iframe");
		selenium.waitForText("//body", "Calendar Event Description");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("link=Events"));
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText("//td[4]/a"));
	}
}