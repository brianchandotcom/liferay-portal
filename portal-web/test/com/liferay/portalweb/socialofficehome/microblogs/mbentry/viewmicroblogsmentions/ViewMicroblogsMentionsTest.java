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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.viewmicroblogsmentions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsMentionsTest extends BaseTestCase {
	public void testViewMicroblogsMentions() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//li[@id='_145_notificationsMenu']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='title']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 commented on your post."),
			selenium.getText("//div[@class='title']"));
		assertEquals(RuntimeVariables.replace(
				"Microblogs Post Comment [@joebloggs]"),
			selenium.getText("//div[@class='body']"));
		assertEquals(RuntimeVariables.replace("Mark as Read"),
			selenium.getText("//span[@class='dismiss-notifications']/a"));
		selenium.clickAt("//span[@class='dismiss-notifications']/a",
			RuntimeVariables.replace("Mark as Read"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//li[@id='_145_notificationsMenu']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		assertFalse(selenium.isTextPresent(
				"Social01 Office01 User01 commented on your post."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//nav/ul/li[contains(.,'Microblogs')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Mentions"),
			selenium.getText("link=Mentions"));
		selenium.clickAt("link=Mentions", RuntimeVariables.replace("Mentions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Social01 Office01 User01")
										.equals(selenium.getText(
								"//div[@class='user-name']/span"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace(
				"Microblogs Post Comment Joe Bloggs"),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='content']/span/a"));
	}
}