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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferaySeleniumHelper {

	public static void assertAlert(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertEquals(pattern, liferaySelenium.getAlert());
	}

	public static void assertChecked(
		LiferaySelenium liferaySelenium, String locator) {

		BaseTestCase.assertTrue(liferaySelenium.isChecked(locator));
	}

	public static void assertConfirmation(
		LiferaySelenium liferaySelenium, String pattern) {

		String confirmation = liferaySelenium.getConfirmation();

		BaseTestCase.assertTrue(
			confirmation.matches(
				"^" + StringUtil.replace(pattern, "?", "[\\\\s\\\\S]") + "$"));
	}

	public static void assertElementNotPresent(
		LiferaySelenium liferaySelenium, String locator) {

		BaseTestCase.assertFalse(liferaySelenium.isElementPresent(locator));
	}

	public static void assertElementPresent(
		LiferaySelenium liferaySelenium, String locator) {

		BaseTestCase.assertTrue(liferaySelenium.isElementPresent(locator));
	}

	public static void assertLocation(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertEquals(pattern, liferaySelenium.getLocation());
	}

	public static void assertNotAlert(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertNotEquals(pattern, liferaySelenium.getAlert());
	}

	public static void assertNotChecked(
		LiferaySelenium liferaySelenium, String locator) {

		BaseTestCase.assertFalse(liferaySelenium.isChecked(locator));
	}

	public static void assertNotLocation(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertNotEquals(pattern, liferaySelenium.getLocation());
	}

	public static void assertNotPartialText(
		LiferaySelenium liferaySelenium, String locator, String pattern) {

		BaseTestCase.assertFalse(
			liferaySelenium.isPartialText(locator, pattern));
	}

	public static void assertNotSelectedLabel(
		LiferaySelenium liferaySelenium, String selectLocator, String pattern) {

		BaseTestCase.assertNotEquals(
			pattern, liferaySelenium.getSelectedLabel(selectLocator));
	}

	public static void assertNotText(
		LiferaySelenium liferaySelenium, String locator, String pattern) {

		BaseTestCase.assertNotEquals(pattern, liferaySelenium.getText(locator));
	}

	public static void assertNotValue(
		LiferaySelenium liferaySelenium, String locator, String pattern) {

		BaseTestCase.assertNotEquals(
			pattern, liferaySelenium.getValue(locator));
	}

	public static void assertNotVisible(
		LiferaySelenium liferaySelenium, String locator) {

		BaseTestCase.assertFalse(liferaySelenium.isVisible(locator));
	}

	public static void assertPartialText(
		LiferaySelenium liferaySelenium, String locator, String pattern) {

		BaseTestCase.assertTrue(
			liferaySelenium.isPartialText(locator, pattern));
	}

	public static void assertSelectedLabel(
		LiferaySelenium liferaySelenium, String selectLocator, String pattern) {

		BaseTestCase.assertEquals(
			pattern, liferaySelenium.getSelectedLabel(selectLocator));
	}

	public static void assertText(
		LiferaySelenium liferaySelenium, String locator, String pattern) {

		BaseTestCase.assertEquals(pattern, liferaySelenium.getText(locator));
	}

	public static void assertTextNotPresent(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertFalse(liferaySelenium.isTextPresent(pattern));
	}

	public static void assertTextPresent(
		LiferaySelenium liferaySelenium, String pattern) {

		BaseTestCase.assertTrue(liferaySelenium.isTextPresent(pattern));
	}

	public static void assertValue(
		LiferaySelenium liferaySelenium, String locator, String pattern) {

		BaseTestCase.assertEquals(pattern, liferaySelenium.getValue(locator));
	}

	public static void assertVisible(
		LiferaySelenium liferaySelenium, String locator) {

		BaseTestCase.assertTrue(liferaySelenium.isVisible(locator));
	}

	public static void downloadTempFile(String value) {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox") &&
			!_BROWSER_TYPE.equals("*iehta") &&
			!_BROWSER_TYPE.equals("*iexplore")) {

			return;
		}

		try {
			Thread.sleep(5000);

			Runtime runtime = Runtime.getRuntime();

			String command = RuntimeVariables.replace(
				TestPropsValues.SELENIUM_BROWSER_COMMANDS_DIR +
					TestPropsValues.SELENIUM_DOWNLOAD_FILE);

			runtime.exec(command);

			Thread.sleep(30000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void echo(String message) {
		System.out.println(message);
	}

	public static String getNumberDecrement(String value) {
		return StringUtil.valueOf(GetterUtil.getInteger(value) - 1);
	}

	public static String getNumberIncrement(String value) {
		return StringUtil.valueOf(GetterUtil.getInteger(value) + 1);
	}

	public static boolean isText(
		LiferaySelenium liferaySelenium, String locator, String value) {

		return value.equals(liferaySelenium.getText(locator));
	}

	public static void pause(String waitTime) throws Exception {
		Thread.sleep(GetterUtil.getInteger(waitTime));
	}

	public static void setBrowserOption() {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox")) {

			return;
		}

		try {
			Runtime runtime = Runtime.getRuntime();

			String[] commands = {
				RuntimeVariables.replace(
					TestPropsValues.SELENIUM_BROWSER_COMMANDS_DIR +
						TestPropsValues.SELENIUM_SET_BROWSER_OPTION),
					TestPropsValues.OUTPUT_DIR
			};

			runtime.exec(commands);

			Thread.sleep(10000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void waitForElementNotPresent(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail(
					"Timeout: unable to find the locator \"" + locator + "\"");
			}

			try {
				if (!liferaySelenium.isElementPresent(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotPartialText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (!liferaySelenium.isPartialText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotSelectedLabel(
			LiferaySelenium liferaySelenium, String selectLocator,
			String pattern)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (!pattern.equals(
						liferaySelenium.getSelectedLabel(selectLocator))) {

					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (!value.equals(liferaySelenium.getText(locator))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotValue(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (!value.equals(liferaySelenium.getValue(locator))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForNotVisible(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail(
					"Timeout: unable to find the locator \"" + locator + "\"");
			}

			try {
				if (!liferaySelenium.isVisible(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForPartialText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (liferaySelenium.isPartialText(locator, value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForSelectedLabel(
			LiferaySelenium liferaySelenium, String selectLocator,
			String pattern)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (pattern.equals(
						liferaySelenium.getSelectedLabel(selectLocator))) {

					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForText(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (value.equals(liferaySelenium.getText(locator))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForTextNotPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail(
					"Timeout: unable to find the text \"" + value + "\"");
			}

			try {
				if (!liferaySelenium.isTextPresent(value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForTextPresent(
			LiferaySelenium liferaySelenium, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail(
					"Timeout: unable to find the text \"" + value + "\"");
			}

			try {
				if (liferaySelenium.isTextPresent(value)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForValue(
			LiferaySelenium liferaySelenium, String locator, String value)
		throws Exception {

		value = RuntimeVariables.replace(value);

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail("Timeout");
			}

			try {
				if (value.equals(liferaySelenium.getValue(locator))) {

					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public static void waitForVisible(
			LiferaySelenium liferaySelenium, String locator)
		throws Exception {

		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				BaseTestCase.fail(
					"Timeout: unable to find the locator \"" + locator + "\"");
			}

			try {
				if (liferaySelenium.isVisible(locator)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	private static final String _BROWSER_TYPE = TestPropsValues.BROWSER_TYPE;

}