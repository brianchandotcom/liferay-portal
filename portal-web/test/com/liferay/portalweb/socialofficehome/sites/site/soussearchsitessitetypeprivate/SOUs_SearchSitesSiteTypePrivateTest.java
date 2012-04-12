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

package com.liferay.portalweb.socialofficehome.sites.site.soussearchsitessitetypeprivate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_SearchSitesSiteTypePrivateTest extends BaseTestCase {
	public void testSOUs_SearchSitesSiteTypePrivate() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/socialoffice01/so/dashboard/");
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
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Private"));
				Thread.sleep(5000);
				assertFalse(selenium.isElementPresent(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));

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
				assertEquals(RuntimeVariables.replace("There are no results."),
					selenium.getText("//li[@class='empty']"));

			case 100:
				label = -1;
			}
		}
	}
}