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

package com.liferay.portalweb.socialofficehome.microblogs.mbentry.sousviewmbcontentmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMicroblogsContentViewableByFollowersTest extends BaseTestCase {
	public void testAddMicroblogsContentViewableByFollowers()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard");
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]"));
		selenium.clickAt("//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]",
			RuntimeVariables.replace("Update your status..."));
		selenium.waitForElementPresent("//textarea");
		selenium.clickAt("//textarea", RuntimeVariables.replace("Text area"));
		selenium.sendKeys("//textarea",
			RuntimeVariables.replace("Followers Microblogs Post"));
		selenium.waitForText("//span[@class='microblogs-countdown']", "125");
		assertEquals(RuntimeVariables.replace("125"),
			selenium.getText("//span[@class='microblogs-countdown']"));
		selenium.select("//select[@id='_1_WAR_microblogsportlet_socialRelationType']",
			RuntimeVariables.replace("Followers"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		selenium.waitForVisible(
			"xPath=(//div[@class='content'])[contains(.,'Followers Microblogs Post')]");
		assertEquals(RuntimeVariables.replace("Followers Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[1]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//span[@class='action repost']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Connections Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[2]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//span[@class='action repost']/a)[2]"));
		assertEquals(RuntimeVariables.replace("Everyone Microblogs Post"),
			selenium.getText("xPath=(//div[@class='content'])[3]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[3]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//span[@class='action repost']/a)[3]"));
	}
}