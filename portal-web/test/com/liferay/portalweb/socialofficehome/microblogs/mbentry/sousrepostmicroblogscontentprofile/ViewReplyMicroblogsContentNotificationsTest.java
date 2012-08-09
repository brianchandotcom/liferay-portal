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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousrepostmicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewReplyMicroblogsContentNotificationsTest extends BaseTestCase {
	public void testViewReplyMicroblogsContentNotifications()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
					selenium.getText("//div[@class='body']"));
				selenium.clickAt("//div[@class='title']",
					RuntimeVariables.replace(
						"Social01 Office01 User01 commented on your post."));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText(
						"xPath=(//div[@class='user-name']/span)[contains(.,'Joe Bloggs')]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText("xPath=(//div[@class='content'])[1]"));
				assertEquals(RuntimeVariables.replace("1 Comment"),
					selenium.getText("//span[@class='action comment']/a"));

				boolean commentVisible = selenium.isVisible(
						"//div[@class='comments-container reply']");

				if (commentVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[@class='action comment']/a",
					RuntimeVariables.replace("1 Comment"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='comments-container reply']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText(
						"xPath=(//div[@class='user-name']/span)[contains(.,'Social01 Office01 User01')]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
					selenium.getText("xPath=(//div[@class='content'])[2]"));
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
				assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
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

			case 100:
				label = -1;
			}
		}
	}
}