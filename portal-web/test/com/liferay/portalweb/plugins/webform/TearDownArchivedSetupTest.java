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

package com.liferay.portalweb.plugins.webform;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownArchivedSetupTest extends BaseTestCase {
	public void testTearDownArchivedSetup() throws Exception {
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
						if (selenium.isVisible("link=Web Form Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Web Form Test Page",
					RuntimeVariables.replace("Web Form Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.click("//strong/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_86_rjus']/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Archive/Restore Setup"),
					selenium.getText("//a[@id='_86_rjus']/span"));
				selenium.click(RuntimeVariables.replace(
						"//a[@id='_86_rjus']/span"));
				selenium.waitForPageToLoad("30000");

				boolean archiveSetup1Present = selenium.isElementPresent(
						"//div[@class='portlet-msg-info']");

				if (archiveSetup1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//a[@id='_86_ctvk']"));
				selenium.click(RuntimeVariables.replace("//a[@id='_86_ctvk']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archiveSetup2Present = selenium.isElementPresent(
						"//div[@class='portlet-msg-info']");

				if (archiveSetup2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//a[@id='_86_ctvk']"));
				selenium.click(RuntimeVariables.replace("//a[@id='_86_ctvk']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archiveSetup3Present = selenium.isElementPresent(
						"//div[@class='portlet-msg-info']");

				if (archiveSetup3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//a[@id='_86_ctvk']"));
				selenium.click(RuntimeVariables.replace("//a[@id='_86_ctvk']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archiveSetup4Present = selenium.isElementPresent(
						"//div[@class='portlet-msg-info']");

				if (archiveSetup4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//a[@id='_86_ctvk']"));
				selenium.click(RuntimeVariables.replace("//a[@id='_86_ctvk']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean archiveSetup5Present = selenium.isElementPresent(
						"//div[@class='portlet-msg-info']");

				if (archiveSetup5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//a[@id='_86_ctvk']"));
				selenium.click(RuntimeVariables.replace("//a[@id='_86_ctvk']"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}