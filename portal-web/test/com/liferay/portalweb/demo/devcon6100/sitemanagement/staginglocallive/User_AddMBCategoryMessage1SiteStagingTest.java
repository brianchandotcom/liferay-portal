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
public class User_AddMBCategoryMessage1SiteStagingTest extends BaseTestCase {
	public void testUser_AddMBCategoryMessage1SiteStaging()
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
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertFalse(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertEquals(RuntimeVariables.replace(
				"The data of this portlet is not staged. Any data changes are immediately available to the Local Live site. The portlet's own workflow is still honored. Portlet setup is still managed from staging."),
			selenium.getText("//div[@class='portlet-msg-alert']"));
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//td[1]/a/strong"));
		selenium.clickAt("//td[1]/a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("//input[@value='Post New Thread']",
			RuntimeVariables.replace("Post New Thread"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace("MB Category Thread Message1 Subject"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__19_editor']/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//td[@id='cke_contents__19_editor']/iframe");
		selenium.type("//body",
			RuntimeVariables.replace("MB Category Thread Message1 Body"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message1 Subject"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message1 Body"),
			selenium.getText("//div[@class='thread-body']"));
	}
}