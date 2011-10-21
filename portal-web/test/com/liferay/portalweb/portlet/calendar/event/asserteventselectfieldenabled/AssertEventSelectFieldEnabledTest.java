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

package com.liferay.portalweb.portlet.calendar.event.asserteventselectfieldenabled;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertEventSelectFieldEnabledTest extends BaseTestCase {
	public void testAssertEventSelectFieldEnabled() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Calendar Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_startDateMonth']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_startDateDay']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_startDateYear']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_startDateHour']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_startDateMinute']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_startDateAmPm']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_durationHour']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_durationMinute']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_type']"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='5']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_8_monthlyType")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementPresent("//select[@name='_8_monthlyPos']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_monthlyDay1']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_monthlyDay1']"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='6']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_8_yearlyType")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_yearlyMonth0']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_yearlyPos']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_yearlyDay1']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_yearlyMonth1']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_endDateMonth']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_endDateDay']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_endDateYear']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_endDateHour']"));
		assertTrue(selenium.isElementPresent(
				"//select[@name='_8_endDateMinute']"));
		assertTrue(selenium.isElementPresent("//select[@name='_8_endDateAmPm']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_startDateMonth' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_startDateDay' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_startDateYear' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_startDateHour' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_startDateMinute' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_startDateAmPm' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_durationHour' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_durationMinute' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_type' and @disabled='']"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='5']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_8_monthlyType")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_monthlyPos' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_monthlyDay1' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_monthlyDay1' and @disabled='']"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='6']",
			RuntimeVariables.replace(""));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_8_yearlyType")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_yearlyMonth0' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_yearlyPos' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_yearlyDay1' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_yearlyMonth1' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_endDateMonth' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_endDateDay' and @disabled='']"));
		assertFalse(selenium.isElementPresent(
				"//select[@name='_8_endDateYear' and @disabled='']"));
	}
}