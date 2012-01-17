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

package com.liferay.portalweb.stagingcommunity.sites.usecase.demo1;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_SelectPreviousVersionNumberHistoryTest extends BaseTestCase {
	public void testUser_SelectPreviousVersionNumberHistory()
		throws Exception {
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

		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'live-staging')]"));
		assertTrue(selenium.isPartialText("//li[2]/span/a", "Staging"));
		selenium.clickAt("//li[2]/span/a", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertEquals(RuntimeVariables.replace("History"),
			selenium.getText("//button/span[.='History']"));
		selenium.clickAt("//button/span[.='History']",
			RuntimeVariables.replace("History"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span/span/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//span/span/span"));
		assertEquals(RuntimeVariables.replace("Current Version"),
			selenium.getText("//td[3]/span[2]"));
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//a"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText("//tr[4]/td[2]/span/span/span"));
		assertTrue(selenium.isVisible("//td[3]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("Previous Version Number"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[@id='column-2']/div/div[contains(@class,'portlet-search')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-search')]"));
		assertEquals(RuntimeVariables.replace("Draft"),
			selenium.getText(
				"//div[@id='_170_layoutRevisionDetails']/div/div/span[2]/strong"));
	}
}