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

package com.liferay.portalweb.demo.devcon6100.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_AddSPVariationChristmasCopyFromSeasonTest extends BaseTestCase {
	public void testUser_AddSPVariationChristmasCopyFromSeason()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Site Name")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertFalse(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertFalse(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));

				boolean seasonPresent = selenium.isElementPresent("link=Season");

				if (!seasonPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Season",
					RuntimeVariables.replace("Season"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Season Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				Thread.sleep(5000);
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Staging dropdown"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Manage Site Pages Variations"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a",
					RuntimeVariables.replace("Manage Site Pages Variations"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//input[@id='_170_addBranchButton']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@id='_170_addBranchButton']",
					RuntimeVariables.replace("Add Site Pages Variation"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='_170_name']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='_170_name']",
					RuntimeVariables.replace("Christmas"));
				selenium.type("//textarea[@id='_170_description']",
					RuntimeVariables.replace(
						"Christmas Site Pages Variation of Site Name"));
				selenium.select("//select[@id='_170_copyLayoutSetBranchId']",
					RuntimeVariables.replace("Season"));
				selenium.clickAt("//input[@value='Add']",
					RuntimeVariables.replace("Add"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"Site page variation was added."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}