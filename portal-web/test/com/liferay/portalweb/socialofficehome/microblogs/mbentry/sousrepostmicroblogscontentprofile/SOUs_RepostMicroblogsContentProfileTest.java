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
public class SOUs_RepostMicroblogsContentProfileTest extends BaseTestCase {
	public void testSOUs_RepostMicroblogsContentProfile()
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
		assertEquals(RuntimeVariables.replace("Repost"),
			selenium.getText("//span[@class='action repost']/a"));
		selenium.clickAt("//span[@class='action repost']/a",
			RuntimeVariables.replace("Repost"));
		selenium.waitForVisible("//div[@class='repost-header']");
		assertEquals(RuntimeVariables.replace(
				"Do you want to repost this entry?"),
			selenium.getText("//div[@class='repost-header']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
	}
}