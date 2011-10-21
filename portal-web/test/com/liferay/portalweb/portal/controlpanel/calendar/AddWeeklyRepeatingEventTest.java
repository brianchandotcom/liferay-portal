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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWeeklyRepeatingEventTest extends BaseTestCase {
	public void testAddWeeklyRepeatingEvent() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Calendar",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[6]/span/ul/li/strong/a"));
				selenium.clickAt("//td[6]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div/div/span[3]/span/span/input",
					RuntimeVariables.replace("Repeat Weekly"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_8_weeklyInterval']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_8_weeklyInterval']",
					RuntimeVariables.replace("1"));

				boolean sundayChecked = selenium.isChecked(
						"_8_weeklyDayPos1Checkbox");

				if (!sundayChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos1Checkbox']",
					RuntimeVariables.replace("Sunday Checkbox"));

			case 2:

				boolean mondayChecked = selenium.isChecked(
						"_8_weeklyDayPos2Checkbox");

				if (!mondayChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos2Checkbox']",
					RuntimeVariables.replace("Monday Checkbox"));

			case 3:

				boolean tuesdayChecked = selenium.isChecked(
						"_8_weeklyDayPos3Checkbox");

				if (!tuesdayChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos3Checkbox']",
					RuntimeVariables.replace("Tuesday Checkbox"));

			case 4:

				boolean wednesdayChecked = selenium.isChecked(
						"_8_weeklyDayPos4Checkbox");

				if (!wednesdayChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos4Checkbox']",
					RuntimeVariables.replace("Wednesday Checkbox"));

			case 5:

				boolean thursdayChecked = selenium.isChecked(
						"_8_weeklyDayPos5Checkbox");

				if (thursdayChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos5Checkbox']",
					RuntimeVariables.replace("Thursday Checkbox"));

			case 6:

				boolean fridayChecked = selenium.isChecked(
						"_8_weeklyDayPos6Checkbox");

				if (!fridayChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos6Checkbox']",
					RuntimeVariables.replace("Friday Checkbox"));

			case 7:

				boolean saturdayChecked = selenium.isChecked(
						"_8_weeklyDayPos7Checkbox");

				if (!saturdayChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos7Checkbox']",
					RuntimeVariables.replace("Saturday Checkbox"));

			case 8:
				selenium.select("//select[@id='_8_endDateMonth']",
					RuntimeVariables.replace("label=January"));
				selenium.select("//select[@id='_8_endDateDay']",
					RuntimeVariables.replace("label=1"));
				selenium.select("//select[@id='_8_endDateYear']",
					RuntimeVariables.replace("label=2011"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("//select", "2010")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("//select", RuntimeVariables.replace("2010"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[contains(@href, 'javascript:_8_updateCalendar(0, 7, 2010);')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 7, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Repeating Test Event")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible("link=Repeating Test Event"));
				selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("//select", "2010")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("//select", RuntimeVariables.replace("2010"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[contains(@href, 'javascript:_8_updateCalendar(0, 14, 2010);')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 14, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Repeating Test Event")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isVisible("link=Repeating Test Event"));
				selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("//select", "2010")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.select("//select", RuntimeVariables.replace("2010"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//a[contains(@href, 'javascript:_8_updateCalendar(0, 15, 2010);')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(0, 15, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (!selenium.isElementPresent(
									"link=Repeating Test Event")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertFalse(selenium.isElementPresent(
						"link=Repeating Test Event"));

			case 100:
				label = -1;
			}
		}
	}
}