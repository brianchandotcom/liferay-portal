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

package com.liferay.portalweb.socialofficehome.sites.site.sousjoinsitessite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_JoinSitesSiteTest extends BaseTestCase {
	public void testSOUs_JoinSitesSite() throws Exception {
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
			RuntimeVariables.replace("Open Site Name"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//span[@class='name']/a"));
		assertEquals(RuntimeVariables.replace("Open Site Description"),
			selenium.getText("//span[@class='description']"));
		assertTrue(selenium.isVisible("//a[@class='join-site']"));
		selenium.clickAt("//a[@class='join-site']",
			RuntimeVariables.replace("Join Site"));
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to join Open Site Name[\\s\\S]$"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[@class='leave-site']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}
}