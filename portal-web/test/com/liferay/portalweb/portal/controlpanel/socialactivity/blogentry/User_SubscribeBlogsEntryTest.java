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

package com.liferay.portalweb.portal.controlpanel.socialactivity.blogentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_SubscribeBlogsEntryTest extends BaseTestCase {
	public void testUser_SubscribeBlogsEntry() throws Exception {
		selenium.open(
			"/web/blog-community-name/blog-community-social-activity-blogs-public-page");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"link=Blog Community Social Activity Blogs Public Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blog Community Social Activity Blogs Public Page",
			RuntimeVariables.replace(
				"Blog Community Social Activity Blogs Public Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=0 Comments")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isElementPresent("link=0 Comments"));
		selenium.clickAt("link=0 Comments",
			RuntimeVariables.replace("0 Comments"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//fieldset/div/span/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//fieldset/div/span/a/span",
			RuntimeVariables.replace("Subscribe to Comments"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Your request processed successfully.")
										.equals(selenium.getText(
								"//div[@id='_33_discussion-status-messages']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Unsubscribe from Comments"),
			selenium.getText("//fieldset/div/span/a/span"));
		selenium.clickAt("link=\u00ab Back",
			RuntimeVariables.replace("link=\u00ab Back"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td/div[1]/a/span[2]"));
		assertEquals(RuntimeVariables.replace("exact:Rank: 1"),
			selenium.getText("//td/div/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 2"),
			selenium.getText("//td/div/div/div[2]"));
		assertEquals(RuntimeVariables.replace("Participation Score: 5"),
			selenium.getText("//td/div/div/div[3]"));
		assertEquals(RuntimeVariables.replace("User's Blog Entries: 2"),
			selenium.getText("//td/div[3]"));
		assertEquals(RuntimeVariables.replace("User User"),
			selenium.getText("//tr[2]/td/div[1]/a/span[2]"));
		assertEquals(RuntimeVariables.replace("exact:Rank: 2"),
			selenium.getText("//tr[2]/td/div[1]/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText("//tr[2]/td/div[1]/div/div[2]"));
		assertEquals(RuntimeVariables.replace("Participation Score: 3"),
			selenium.getText("//tr[2]/td/div[1]/div/div[3]"));
		assertTrue(selenium.isElementPresent("//tr[2]/td/div[3]"));
	}
}