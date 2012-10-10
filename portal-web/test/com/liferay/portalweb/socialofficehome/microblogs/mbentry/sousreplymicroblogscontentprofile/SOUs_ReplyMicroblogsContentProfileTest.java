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
public class SOUs_ReplyMicroblogsContentProfileTest extends BaseTestCase {
	public void testSOUs_ReplyMicroblogsContentProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/joebloggs/so/profile");
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("//span[@class='action comment']/a"));
		selenium.clickAt("//span[@class='action comment']/a",
			RuntimeVariables.replace("Comment"));
		selenium.waitForVisible("//div[@class='autocomplete textarea']");
		Thread.sleep(5000);
		selenium.clickAt("//div[@class='autocomplete textarea']",
			RuntimeVariables.replace("Leave a comment..."));
		selenium.waitForVisible("//textarea");
		selenium.clickAt("//textarea",
			RuntimeVariables.replace("Leave a comment..."));
		selenium.sendKeys("//textarea",
			RuntimeVariables.replace("Microblogs Post Comment"));
		selenium.waitForText("//span[@class='microblogs-countdown']", "127");
		assertEquals(RuntimeVariables.replace("127"),
			selenium.getText("//span[@class='microblogs-countdown']"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		selenium.waitForVisible("//div[@class='content']");
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
		selenium.waitForVisible("xPath=(//div[@class='content'])[2]");
		assertEquals(RuntimeVariables.replace("Joe Bloggs says"),
			selenium.getText("xPath=(//div[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01 says"),
			selenium.getText("xPath=(//div[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
	}
}