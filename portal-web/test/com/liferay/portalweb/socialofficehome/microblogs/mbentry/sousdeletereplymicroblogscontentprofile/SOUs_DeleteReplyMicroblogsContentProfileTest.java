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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousdeletereplymicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_DeleteReplyMicroblogsContentProfileTest extends BaseTestCase {
	public void testSOUs_DeleteReplyMicroblogsContentProfile()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/joebloggs/so/profile");
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");

				boolean microblogReplyPresent = selenium.isElementPresent(
						"xPath=(//div[@class='content'])[2]");

				if (microblogReplyPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[@class='action comment']/a",
					RuntimeVariables.replace("1 Comment"));

			case 2:
				selenium.waitForVisible("xPath=(//div[@class='user-name'])[2]");
				assertEquals(RuntimeVariables.replace("Joe Bloggs says"),
					selenium.getText("xPath=(//div[@class='user-name'])[1]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post"),
					selenium.getText("xPath=(//div[@class='content'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Social01 Office01 User01 says"),
					selenium.getText("xPath=(//div[@class='user-name'])[2]"));
				assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
					selenium.getText("xPath=(//div[@class='content'])[2]"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//span[@class='action delete']/a"));
				selenium.clickAt("//span[@class='action delete']/a",
					RuntimeVariables.replace("Delete"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this post[\\s\\S]$"));
				selenium.waitForTextNotPresent("Microblogs Post Comment");

			case 100:
				label = -1;
			}
		}
	}
}