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

package com.liferay.portalweb.socialofficehome.events.event.viewdeleteevented;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageCalendarSOTest extends BaseTestCase {
	public void testTearDownPageCalendarSO() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//nav[@id='navigation']",
					RuntimeVariables.replace("Navigation"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//nav/ul/li[contains(.,'Calendar Test Page')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean calendarPagePresent = selenium.isElementPresent(
						"//nav/ul/li[contains(.,'Calendar Test Page')]/a/span");

				if (!calendarPagePresent) {
					label = 2;

					continue;
				}

				selenium.mouseOver(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']"));
				selenium.click(
					"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this page?".equals(
									selenium.getConfirmation())) {
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
						if (selenium.isElementNotPresent(
									"//nav/ul/li[contains(.,'Calendar Test Page')]/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
			case 100:
				label = -1;
			}
		}
	}
}