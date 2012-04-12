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

package com.liferay.portalweb.socialofficehome.sites.site.viewsitessitetyperestricted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSitesSiteTypeRestrictedTest extends BaseTestCase {
	public void testViewSitesSiteTypeRestricted() throws Exception {
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
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//select[@id='_5_WAR_soportlet_tabs1']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isPartialText(
				"//select[@id='_5_WAR_soportlet_tabs1']", "All Sites"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("All Sites"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Restricted"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Restricted Site Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText("//div[@class='community-title']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//nav/ul/li[1]/a/span"));
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("//nav/ul/li[2]/a/span"));
		assertEquals(RuntimeVariables.replace("Documents"),
			selenium.getText("//nav/ul/li[3]/a/span"));
		assertEquals(RuntimeVariables.replace("Forums"),
			selenium.getText("//nav/ul/li[4]/a/span"));
		assertEquals(RuntimeVariables.replace("Blog"),
			selenium.getText("//nav/ul/li[5]/a/span"));
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//nav/ul/li[6]/a/span"));
		assertEquals(RuntimeVariables.replace("Members"),
			selenium.getText("//nav/ul/li[7]/a/span"));
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
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));

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
			selenium.getText("//button[contains(.,'More Sites')]/span[2]"));
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
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		assertTrue(selenium.isVisible(
				"//input[@id='_5_WAR_soportlet_dialogKeywords']"));
		selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
			RuntimeVariables.replace("Restricted Site Name"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText("//span[@class='name']/a"));
		assertEquals(RuntimeVariables.replace("Restricted Site Description"),
			selenium.getText("//span[@class='description']"));
		selenium.clickAt("//span[@class='name']/a",
			RuntimeVariables.replace("Restricted Site Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText("//div[@class='community-title']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//nav/ul/li[1]/a/span"));
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("//nav/ul/li[2]/a/span"));
		assertEquals(RuntimeVariables.replace("Documents"),
			selenium.getText("//nav/ul/li[3]/a/span"));
		assertEquals(RuntimeVariables.replace("Forums"),
			selenium.getText("//nav/ul/li[4]/a/span"));
		assertEquals(RuntimeVariables.replace("Blog"),
			selenium.getText("//nav/ul/li[5]/a/span"));
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//nav/ul/li[6]/a/span"));
		assertEquals(RuntimeVariables.replace("Members"),
			selenium.getText("//nav/ul/li[7]/a/span"));
	}
}