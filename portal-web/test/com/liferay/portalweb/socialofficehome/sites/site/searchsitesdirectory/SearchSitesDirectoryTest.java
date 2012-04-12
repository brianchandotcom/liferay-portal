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

package com.liferay.portalweb.socialofficehome.sites.site.searchsitesdirectory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchSitesDirectoryTest extends BaseTestCase {
	public void testSearchSitesDirectory() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[contains(@class, 'selected')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText(
						"//li[contains(@class, 'selected')]/a/span"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//button[contains(.,'More Sites')]/span[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("More Sites"),
					selenium.getText(
						"//button[contains(.,'More Sites')]/span[2]"));
				selenium.clickAt("//button[contains(.,'More Sites')]/span[2]",
					RuntimeVariables.replace("More Sites"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//h1[@class='header-title']/span)[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Directory"),
					selenium.getText(
						"xPath=(//h1[@class='header-title']/span)[1]"));
				assertTrue(selenium.isVisible(
						"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
				selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
					RuntimeVariables.replace("Open Site1 Name"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Open Site1 Name"),
					selenium.getText("//span[@class='name']/a"));
				assertEquals(RuntimeVariables.replace("Open Site1 Description"),
					selenium.getText("//span[@class='description']"));
				selenium.clickAt("//span[@class='name']/a",
					RuntimeVariables.replace("Open Site1 Name"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Open Site1 Name"),
					selenium.getText("//div[@class='community-title']"));
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[contains(@class, 'selected')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText(
						"//li[contains(@class, 'selected')]/a/span"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//button[contains(.,'More Sites')]/span[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("More Sites"),
					selenium.getText(
						"//button[contains(.,'More Sites')]/span[2]"));
				selenium.clickAt("//button[contains(.,'More Sites')]/span[2]",
					RuntimeVariables.replace("More Sites"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//h1[@class='header-title']/span)[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Directory"),
					selenium.getText(
						"xPath=(//h1[@class='header-title']/span)[1]"));

				boolean mySitesChecked1 = selenium.isChecked(
						"//input[@id='_5_WAR_soportlet_userSites']");

				if (!mySitesChecked1) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_5_WAR_soportlet_userSites']",
					RuntimeVariables.replace("My Sites"));

			case 2:
				assertTrue(selenium.isVisible(
						"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
				selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
					RuntimeVariables.replace("Private Site2 Name"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("There are no results."),
					selenium.getText("//li[@class='empty']"));

				boolean mySitesChecked2 = selenium.isChecked(
						"//input[@id='_5_WAR_soportlet_userSites']");

				if (mySitesChecked2) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_5_WAR_soportlet_userSites']",
					RuntimeVariables.replace("My Sites"));

			case 3:
				assertTrue(selenium.isVisible(
						"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
				selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
					RuntimeVariables.replace("Private Site2 Name"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Private Site2 Name"),
					selenium.getText("//span[@class='name']/a"));
				assertEquals(RuntimeVariables.replace(
						"Private Site2 Description"),
					selenium.getText("//span[@class='description']"));
				selenium.clickAt("//span[@class='name']/a",
					RuntimeVariables.replace("Private Site2 Name"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Private Site2 Name"),
					selenium.getText("//div[@class='community-title']"));
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//li[contains(@class, 'selected')]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText(
						"//li[contains(@class, 'selected')]/a/span"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//button[contains(.,'More Sites')]/span[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("More Sites"),
					selenium.getText(
						"//button[contains(.,'More Sites')]/span[2]"));
				selenium.clickAt("//button[contains(.,'More Sites')]/span[2]",
					RuntimeVariables.replace("More Sites"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//h1[@class='header-title']/span)[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Directory"),
					selenium.getText(
						"xPath=(//h1[@class='header-title']/span)[1]"));
				assertTrue(selenium.isVisible(
						"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
				selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
					RuntimeVariables.replace("Restricted Site3 Name"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Restricted Site3 Name"),
					selenium.getText("//span[@class='name']/a"));
				assertEquals(RuntimeVariables.replace(
						"Restricted Site3 Description"),
					selenium.getText("//span[@class='description']"));
				selenium.clickAt("//span[@class='name']/a",
					RuntimeVariables.replace("Restricted Site3 Name"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Restricted Site3 Name"),
					selenium.getText("//div[@class='community-title']"));

			case 100:
				label = -1;
			}
		}
	}
}