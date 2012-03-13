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

package com.liferay.portalweb.demo.devcon6100.sitemanagement.staginglocalliveworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class WCC_PublishToLiveNowWebContentNullSiteStagingTest
	extends BaseTestCase {
	public void testWCC_PublishToLiveNowWebContentNullSiteStaging()
		throws Exception {
		selenium.open("/web/community-site-test/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Staging")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Staging", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		selenium.clickAt("//span[@class='staging-icon-menu-container']/span/ul/li/strong/a",
			RuntimeVariables.replace("Staging Drop Down"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Publish to Live Now"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@value='Publish']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
	}
}