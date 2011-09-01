/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.calendar.calendarevent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCalendarEventTest extends BaseTestCase {
	public void testAddCalendarEvent() throws Exception {
		selenium.open("/web/calendar-event-community/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Calendar Event Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Calendar Event Page",
			RuntimeVariables.replace("Calendar Event Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_8_startDateMonth']",
			RuntimeVariables.replace("May"));
		selenium.select("//select[@id='_8_startDateDay']",
			RuntimeVariables.replace("label=31"));
		selenium.select("//select[@id='_8_startDateYear']",
			RuntimeVariables.replace("2010"));
		selenium.select("//select[@name='_8_startDateHour']",
			RuntimeVariables.replace("12"));
		selenium.select("//select[@name='_8_startDateMinute']",
			RuntimeVariables.replace(":00"));
		selenium.select("//select[@name='_8_startDateAmPm']",
			RuntimeVariables.replace("PM"));
		selenium.clickAt("//input[@id='_8_allDayCheckbox']",
			RuntimeVariables.replace("All Day Event Checkbox"));
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Hashi's birthday bash"));
		selenium.saveScreenShotAndSource();
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__8_editor']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.selectFrame("//td[@id='cke_contents__8_editor']/iframe");
		selenium.type("//body", RuntimeVariables.replace("This is so much fun!"));
		selenium.selectFrame("relative=top");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("5/31/10"),
			selenium.getText("//td[1]/a"));
		assertTrue(selenium.isElementPresent("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Hashi's birthday bash"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("5/31/10"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("5/31/10"),
			selenium.getText("//dd[1]"));
		assertEquals(RuntimeVariables.replace("This is so much fun!"),
			selenium.getText("//p[2]"));
	}
}