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
public class SOUs_ViewReplyMicroblogsContentTagTest extends BaseTestCase {
	public void testSOUs_ViewReplyMicroblogsContentTag()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/socialoffice01/so/dashboard/");
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");

				boolean replyVisible = selenium.isElementPresent(
						"//div[@class='microblogs-entry show-comments']");

				if (replyVisible) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("1 Comment"),
					selenium.getText("//span[@class='action comment']/a"));
				selenium.clickAt("//span[@class='action comment']/a",
					RuntimeVariables.replace("1 Comment"));
				selenium.waitForVisible("xPath=(//div[@class='content'])[2]");

			case 2:
				assertTrue(selenium.isPartialText(
						"xPath=(//div[@class='content'])[2]",
						"Microblogs Post Comment"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//div[@class='content']/span/a"));
				selenium.clickAt("//div[@class='content']/span/a",
					RuntimeVariables.replace("Joe Bloggs"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//div[@class='user-profile-detail']"));
				assertEquals(RuntimeVariables.replace("Profile"),
					selenium.getText(
						"//nav[contains(.,'Profile')]/ul/li/a/span"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText(
						"xPath=(//div[@class='lfr-contact-name']/a)[2]"));
				assertEquals(RuntimeVariables.replace("test@liferay.com"),
					selenium.getText("//div[@class='lfr-contact-extra']"));
				assertEquals(RuntimeVariables.replace("Activities"),
					selenium.getText("//span[@class='portlet-title-text']"));
				assertTrue(selenium.isPartialText(
						"xPath=(//div[@class='activity-title'])[1]",
						"Microblogs Post Comment"));
				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText(
						"xPath=(//div[@class='activity-title'])[2]"));

			case 100:
				label = -1;
			}
		}
	}
}