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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymicroblogscontenttagprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ReplyMicroblogsContentTagProfileTest extends BaseTestCase {
	public void testSOUs_ReplyMicroblogsContentTagProfile()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/joebloggs/so/profile/");

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
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//div[@class='user-name']/span"));
				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText("//div[@class='content']"));

				boolean replyVisible = selenium.isElementPresent(
						"//span[@class='placeholder-text']");

				if (replyVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Comment"),
					selenium.getText("//span[@class='action comment']/a"));
				selenium.clickAt("//span[@class='action comment']/a",
					RuntimeVariables.replace("Comment"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//span[@class='placeholder-text']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
				Thread.sleep(5000);
				assertTrue(selenium.isVisible(
						"//span[@class='placeholder-text']"));
				selenium.clickAt("//span[@class='placeholder-text']",
					RuntimeVariables.replace("Leave a comment..."));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//textarea")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//textarea",
					RuntimeVariables.replace("Leave a comment..."));
				selenium.typeKeys("//textarea",
					RuntimeVariables.replace("Microblogs Post Comment @Joe"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//span[@class='user-name']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//span[@class='user-name']",
					RuntimeVariables.replace("Joe Bloggs"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("122")
												.equals(selenium.getText(
										"//span[@class='microblogs-countdown']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("122"),
					selenium.getText("//span[@class='microblogs-countdown']"));
				selenium.clickAt("//input[@value='Post']",
					RuntimeVariables.replace("Post"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[@class='content']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText("//div[@class='content']"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//div[@class='content'])[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Joe Bloggs says"),
					selenium.getText("xPath=(//div[@class='user-name'])[1]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText("xPath=(//div[@class='content'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01 says"),
					selenium.getText("xPath=(//div[@class='user-name'])[2]"));
				assertTrue(selenium.isPartialText(
						"xPath=(//div[@class='content'])[2]",
						"Microblogs Post Comment"));
				assertTrue(selenium.isPartialText(
						"xPath=(//div[@class='content'])[2]", "Joe Bloggs"));

			case 100:
				label = -1;
			}
		}
	}
}