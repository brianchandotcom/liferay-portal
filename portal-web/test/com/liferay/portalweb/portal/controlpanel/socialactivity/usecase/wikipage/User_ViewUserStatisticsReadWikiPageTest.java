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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.wikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewUserStatisticsReadWikiPageTest extends BaseTestCase {
	public void testUser_ViewUserStatisticsReadWikiPage()
		throws Exception {
		selenium.open("/web/site-name/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=User Statistics Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=User Statistics Test Page",
			RuntimeVariables.replace("User Statistics Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Joe Bloggs")
										.equals(selenium.getText(
								"xPath=(//span[@class='user-name'])[1]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//span[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Rank: 1"),
			selenium.getText("xPath=(//div[@class='user-rank'])[1]"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText("xPath=(//div[@class='contribution-score'])[1]"));
		assertEquals(RuntimeVariables.replace("Participation Score: 28"),
			selenium.getText("xPath=(//div[@class='participation-score'])[1]"));
		assertEquals(RuntimeVariables.replace("User's Wiki Pages: 1"),
			selenium.getText("//div[@class='social-counter-user.wikis']"));
		assertEquals(RuntimeVariables.replace("User's Attachments: 2"),
			selenium.getText("//div[@class='social-counter-user.attachments']"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("xPath=(//span[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Rank: 2"),
			selenium.getText("xPath=(//div[@class='user-rank'])[2]"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText("xPath=(//div[@class='contribution-score'])[2]"));
		assertEquals(RuntimeVariables.replace("Participation Score: 4"),
			selenium.getText("xPath=(//div[@class='participation-score'])[2]"));
	}
}