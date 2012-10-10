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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousreplymicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewReplyMicroblogsContentTest extends BaseTestCase {
	public void testViewReplyMicroblogsContent() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				assertTrue(selenium.isElementPresent(
						"//div[contains(@id,'_2_WAR_microblogsportlet_autocompleteContent')]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText("//div[@class='content']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
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
				selenium.waitForVisible(
					"//div[@class='comments-container reply']");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01"),
					selenium.getText(
						"xPath=(//div[@class='user-name']/span)[contains(.,'Social01 Office01 User01')]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
					selenium.getText("xPath=(//div[@class='content'])[2]"));

			case 100:
				label = -1;
			}
		}
	}
}