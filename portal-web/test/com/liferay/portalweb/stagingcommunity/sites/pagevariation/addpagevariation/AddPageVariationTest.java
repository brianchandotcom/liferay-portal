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

package com.liferay.portalweb.stagingcommunity.sites.pagevariation.addpagevariation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageVariationTest extends BaseTestCase {
	public void testAddPageVariation() throws Exception {
		selenium.open("/web/guest/home/");

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
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue yui3-skin-sam live-view controls-visible signed-in public-page dockbar-ready staging-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue yui3-skin-sam staging local-staging controls-visible signed-in public-page dockbar-ready staging-ready']"));
		assertTrue(selenium.isPartialText("//li[2]/span/a", "Staging"));
		selenium.clickAt("//li[2]/span/a", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Main Site Pages Variation of Site Name"),
			selenium.getText("//span[@class='layout-set-branch-description']"));
		assertEquals(RuntimeVariables.replace("Manage Page Variations"),
			selenium.getText("//a[@id='_170_manageLayoutRevisions']/span"));
		selenium.clickAt("//a[@id='_170_manageLayoutRevisions']/span",
			RuntimeVariables.replace("Manage Page Variations"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[@id='_170_addRootLayoutBranch']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@id='_170_addRootLayoutBranch']",
			RuntimeVariables.replace("Add Page Variation"));

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
			RuntimeVariables.replace("Page Variation Name"));
		selenium.type("//textarea[@id='_170_description']",
			RuntimeVariables.replace("Page Variation Description"));
		selenium.clickAt("//input[@value='Add']",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Page variation was added."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");

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
		assertTrue(selenium.isElementPresent(
				"//body[@class='blue yui3-skin-sam live-view controls-visible signed-in public-page dockbar-ready staging-ready']"));
		assertFalse(selenium.isElementPresent(
				"//body[@class='blue yui3-skin-sam staging local-staging controls-visible signed-in public-page dockbar-ready staging-ready']"));
		assertTrue(selenium.isPartialText("//li[2]/span/a", "Staging"));
		selenium.clickAt("//li[2]/span/a", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Main Variation"),
			selenium.getText("//li[1]/span/span"));
		assertEquals(RuntimeVariables.replace("Page Variation Name"),
			selenium.getText("//li[2]/span/a"));
	}
}