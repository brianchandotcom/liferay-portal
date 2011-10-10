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

package com.liferay.portalweb.portlet.myaccount.datepicker.selectbirthday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectBirthdayTest extends BaseTestCase {
	public void testSelectBirthday() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Joe Bloggs")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Joe Bloggs",
			RuntimeVariables.replace("Joe Bloggs"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//select[@id='_2_birthdayMonth']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		Thread.sleep(5000);
		selenium.click("//select[@id='_2_birthdayMonth']");
		selenium.select("//select[@id='_2_birthdayMonth']",
			RuntimeVariables.replace("March"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("March".equals(selenium.getSelectedLabel(
								"//select[@id='_2_birthdayMonth']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.select("//select[@id='_2_birthdayYear']",
			RuntimeVariables.replace("1986"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if ("1986".equals(selenium.getSelectedLabel(
								"//select[@id='_2_birthdayYear']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//button[@id='buttonTest']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//button[@id='buttonTest']",
			RuntimeVariables.replace("Date Picker"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='aui-calendar-title']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("March 1986"),
			selenium.getText("//div[@class='aui-calendar-title']"));
		selenium.clickAt("link=Prev", RuntimeVariables.replace("Prev"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("April 1986")
										.equals(selenium.getText(
								"//div[@class='aui-calendar-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("April 1986"),
			selenium.getText("//div[@class='aui-calendar-title']"));
		selenium.clickAt("link=Prev", RuntimeVariables.replace("Prev"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("May 1986")
										.equals(selenium.getText(
								"//div[@class='aui-calendar-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("May 1986"),
			selenium.getText("//div[@class='aui-calendar-title']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=31")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=31", RuntimeVariables.replace("31"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (!selenium.isVisible("//div[@class='aui-calendar-title']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
	}
}