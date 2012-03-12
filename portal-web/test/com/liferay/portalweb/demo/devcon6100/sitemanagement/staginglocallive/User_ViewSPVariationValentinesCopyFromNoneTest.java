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
public class User_ViewSPVariationValentinesCopyFromNoneTest extends BaseTestCase {
	public void testUser_ViewSPVariationValentinesCopyFromNone()
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
				assertEquals(RuntimeVariables.replace(
						"Christmas 2 Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));

				boolean christmas2Present = selenium.isElementPresent(
						"link=Christmas 2");

				if (!christmas2Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Christmas 2",
					RuntimeVariables.replace("Christmas 2"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 2:
				assertEquals(RuntimeVariables.replace("Christmas 2"),
					selenium.getText("//li[2]/span/span/span[1]"));
				assertEquals(RuntimeVariables.replace("Valentines"),
					selenium.getText("//li[4]/span/span/a"));
				selenium.clickAt("//li[4]/span/span/a",
					RuntimeVariables.replace("Valentines"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Valentines"),
					selenium.getText("//li[4]/span/span/span[1]"));
				assertEquals(RuntimeVariables.replace(
						"The page Home is not enabled in Valentines, but is available for other pages variations."),
					selenium.getText(
						"//div[@id='_170_layoutRevisionDetails']/div"));
				assertFalse(selenium.isElementPresent("//nav/ul/li[1]/a/span"));
				assertFalse(selenium.isElementPresent("//nav/ul/li[2]/a/span"));
				assertFalse(selenium.isElementPresent("//nav/ul/li[3]/a/span"));
				assertFalse(selenium.isElementPresent("//nav/ul/li[4]/a/span"));

			case 100:
				label = -1;
			}
		}
	}
}