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

package com.liferay.portalweb.portlet.pollsdisplay.archivedsetup.savearchivedsetup;

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
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Polls Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Polls Display Test Page",
					RuntimeVariables.replace("Polls Display Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//strong/a"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Options"));

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
					RuntimeVariables.replace("Configuration"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='archived-setups']/span/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Archive/Restore Setup"),
					selenium.getText(
						"//div[@class='archived-setups']/span/a/span"));
				selenium.clickAt("//div[@class='archived-setups']/span/a/span",
					RuntimeVariables.replace("Archive/Restore Setup"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean pdArchivedSetup1Present = selenium.isElementPresent(
						"//tr[3]/td[4]/span/ul/li/strong/a");

				if (!pdArchivedSetup1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//tr[3]/td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 2:

				boolean pdArchivedSetup2Present = selenium.isElementPresent(
						"//tr[3]/td[4]/span/ul/li/strong/a");

				if (!pdArchivedSetup2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//tr[3]/td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean pdArchivedSetup3Present = selenium.isElementPresent(
						"//tr[3]/td[4]/span/ul/li/strong/a");

				if (!pdArchivedSetup3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//tr[3]/td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean pdArchivedSetup4Present = selenium.isElementPresent(
						"//tr[3]/td[4]/span/ul/li/strong/a");

				if (!pdArchivedSetup4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//tr[3]/td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean pdArchivedSetup5Present = selenium.isElementPresent(
						"//tr[3]/td[4]/span/ul/li/strong/a");

				if (!pdArchivedSetup5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//tr[3]/td[4]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a[@id='_86_ctvk']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}