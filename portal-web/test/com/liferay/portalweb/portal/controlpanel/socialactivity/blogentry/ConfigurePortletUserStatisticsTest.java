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

package com.liferay.portalweb.portal.controlpanel.socialactivity.blogentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletUserStatisticsTest extends BaseTestCase {
	public void testConfigurePortletUserStatistics() throws Exception {
		selenium.open(
			"/web/blog-community-name/blog-community-social-activity-blogs-public-page");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"link=Blog Community Social Activity Blogs Public Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blog Community Social Activity Blogs Public Page",
			RuntimeVariables.replace(
				"Blog Community Social Activity Blogs Public Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("link=Options"));
		selenium.clickAt("link=Options", RuntimeVariables.replace("Options"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Configuration")
										.equals(selenium.getText(
								"link=Configuration"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Configuration",
			RuntimeVariables.replace("Configuration"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("_86_showHeaderTextCheckbox")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("_86_showHeaderTextCheckbox",
			RuntimeVariables.replace("Show Header Text"));
		selenium.clickAt("_86_showTotalsCheckbox",
			RuntimeVariables.replace("Show Totals"));
		selenium.click("_86_displayActivityCounterName0");
		selenium.select("_86_displayActivityCounterName0",
			RuntimeVariables.replace("label=User's Subscriptions"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//fieldset/div[2]/div[1]/span/span/button[1]"));
		selenium.click("//fieldset/div[2]/div[1]/span/span/button[1]");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_86_displayActivityCounterName2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementPresent("_86_displayActivityCounterName2"));
		selenium.select("_86_displayActivityCounterName2",
			RuntimeVariables.replace("label=User's Comments"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//fieldset/div[2]/div[1]/span/span/button[1]"));
		selenium.click("//fieldset/div[2]/div[1]/span/span/button[1]");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_86_displayActivityCounterName3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("_86_displayActivityCounterName3");
		selenium.select("_86_displayActivityCounterName3",
			RuntimeVariables.replace("label=User's Blog Entries"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//fieldset/div[2]/div[1]/span/span/button[1]"));
		selenium.click("//fieldset/div[2]/div[1]/span/span/button[1]");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_86_displayActivityCounterName4")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("_86_displayActivityCounterName4");
		selenium.select("_86_displayActivityCounterName4",
			RuntimeVariables.replace("label=User's Blog Entry Updates"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//fieldset/div[2]/div[1]/span/span/button[1]"));
		selenium.click("//fieldset/div[2]/div[1]/span/span/button[1]");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("_86_displayActivityCounterName5")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.click("_86_displayActivityCounterName5");
		selenium.select("_86_displayActivityCounterName5",
			RuntimeVariables.replace("label=User's Votes"));
		selenium.clickAt("//div[4]/span/span/input",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"You have successfully updated the setup.")
										.equals(selenium.getText(
								"//div[@id='p_p_id_86_']/div/div/div[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@id='p_p_id_86_']/div/div/div[1]"));
		selenium.selectFrame("relative=up");
		selenium.clickAt("closethick", RuntimeVariables.replace(""));
	}
}