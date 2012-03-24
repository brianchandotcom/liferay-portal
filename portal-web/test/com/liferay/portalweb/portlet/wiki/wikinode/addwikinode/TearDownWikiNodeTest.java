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

package com.liferay.portalweb.portlet.wiki.wikinode.addwikinode;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWikiNodeTest extends BaseTestCase {
	public void testTearDownWikiNode() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
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
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Wiki"),
					selenium.getText(
						"//ul[@class='category-portlets']/li[contains(.,'Wiki')]/a"));
				selenium.clickAt("//ul[@class='category-portlets']/li[contains(.,'Wiki')]/a",
					RuntimeVariables.replace("Wiki"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean wikiNode1Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]");

				if (!wikiNode1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
					RuntimeVariables.replace("Actions"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean wikiNode2Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]");

				if (!wikiNode2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean wikiNode3Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]");

				if (!wikiNode3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean wikiNode4Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]");

				if (!wikiNode4Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean wikiNode5Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]");

				if (!wikiNode5Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[contains(.,'Delete')]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 100:
				label = -1;
			}
		}
	}
}