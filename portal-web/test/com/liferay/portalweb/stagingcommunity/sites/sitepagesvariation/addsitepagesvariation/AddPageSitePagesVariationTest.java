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

package com.liferay.portalweb.stagingcommunity.sites.sitepagesvariation.addsitepagesvariation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageSitePagesVariationTest extends BaseTestCase {
	public void testAddPageSitePagesVariation() throws Exception {
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
			selenium.getText("//li[1]/span/span/span[1]"));
		assertEquals(RuntimeVariables.replace("Site Pages Variation Name"),
			selenium.getText("//li[2]/span/span/a"));
		selenium.clickAt("//li[2]/span/span/a",
			RuntimeVariables.replace("Site Pages Variation Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Pages Variation Name"),
			selenium.getText("//li[2]/span/span/span[1]"));
		selenium.clickAt("//nav[@id='navigation']",
			RuntimeVariables.replace("Navigation"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//a[@id='addPage']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[@id='addPage']",
			RuntimeVariables.replace("Add Page"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@type='text']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Test Page"));
		selenium.clickAt("//button[@id='save']",
			RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Test Page", RuntimeVariables.replace("Test Page"));
		selenium.waitForPageToLoad("30000");
	}
}