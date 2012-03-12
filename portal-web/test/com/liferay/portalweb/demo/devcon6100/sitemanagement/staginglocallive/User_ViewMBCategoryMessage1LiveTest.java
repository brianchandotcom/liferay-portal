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
public class User_ViewMBCategoryMessage1LiveTest extends BaseTestCase {
	public void testUser_ViewMBCategoryMessage1Live() throws Exception {
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
						"//body[contains(@class,'live-staging')]"));
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//td[1]/a/strong"));
				selenium.clickAt("//td[1]/a/strong",
					RuntimeVariables.replace("MB Category Name"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("MB Category Name"),
					selenium.getText("//h1[@class='header-title']/span"));

				boolean threadVisible = selenium.isVisible("//td[1]/a");

				if (threadVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[4]/div/div/div[1]/div/span",
					RuntimeVariables.replace("Threads"));

			case 2:
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//td[1]/a"));
				selenium.clickAt("//td[1]/a",
					RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace(
						"MB Category Thread Message1 Body"),
					selenium.getText("//div[@class='thread-body']"));

			case 100:
				label = -1;
			}
		}
	}
}