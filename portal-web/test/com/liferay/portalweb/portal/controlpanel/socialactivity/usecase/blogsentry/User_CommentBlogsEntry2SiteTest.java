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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.blogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_CommentBlogsEntry2SiteTest extends BaseTestCase {
	public void testUser_CommentBlogsEntry2Site() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.waitForVisible("link=Blogs Test Page");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Blogs Entry2 Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//fieldset[contains(@class,'add-comment')]/div/a");
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset[contains(@class,'add-comment')]/div/a"));
		selenium.clickAt("//fieldset[contains(@class,'add-comment')]/div/a",
			RuntimeVariables.replace("Be the first."));
		selenium.waitForVisible("//textarea[@name='_33_postReplyBody0']");
		selenium.type("//textarea[@name='_33_postReplyBody0']",
			RuntimeVariables.replace("Blogs Entry2 Comment"));
		selenium.clickAt("//input[@value='Reply']",
			RuntimeVariables.replace("Reply"));
		selenium.waitForText("//div[@id='_33_discussion-status-messages']",
			"Your request processed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@id='_33_discussion-status-messages']"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("//span[@class='comments']"));
	}
}