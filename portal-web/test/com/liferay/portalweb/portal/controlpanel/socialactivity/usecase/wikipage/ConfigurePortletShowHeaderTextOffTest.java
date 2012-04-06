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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.wikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletShowHeaderTextOffTest extends BaseTestCase {
	public void testConfigurePortletShowHeaderTextOff()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/site-name/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=User Statistics Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=User Statistics Test Page",
					RuntimeVariables.replace("User Statistics Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("User Statistics"),
					selenium.getText("//span[@class='portlet-title-text']"));
				selenium.clickAt("//span[@class='portlet-title-text']",
					RuntimeVariables.replace("User Statistics"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//div[@class='yui3-dd-shim']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Configuration')]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//iframe[@id='_180_configurationIframeDialog']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame(
					"//iframe[@id='_180_configurationIframeDialog']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_86_showHeaderTextCheckbox']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean showHeaderTextChecked = selenium.isChecked(
						"//input[@id='_86_showHeaderTextCheckbox']");

				if (!showHeaderTextChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_showHeaderTextCheckbox']",
					RuntimeVariables.replace("Show Header Text"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_86_showHeaderTextCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"You have successfully updated the setup.")
												.equals(selenium.getText(
										"//div[@class='portlet-msg-success']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_86_showHeaderTextCheckbox']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}