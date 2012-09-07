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

package com.liferay.portalweb.portal.permissions.blogs.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Portlet_EditCommentTest extends BaseTestCase {
	public void testPortlet_EditComment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Blogs Permissions Page");
		selenium.clickAt("link=Blogs Permissions Page",
			RuntimeVariables.replace("Blogs Permissions Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Blogs Entry Title Temporary"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//li[@class='lfr-discussion-delete-reply']/span/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//li[@class='lfr-discussion-delete-reply']/span/a"));
		selenium.clickAt("//li[@class='lfr-discussion-delete-reply']/span/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//textarea[@name='_33_editReplyBody1']");
		selenium.waitForVisible("//input[@value='Publish']");
		selenium.type("//textarea[@name='_33_editReplyBody1']",
			RuntimeVariables.replace("Portlet Comment Edited"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Portlet Comment Edited"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertNotEquals(RuntimeVariables.replace("Portlet Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
	}
}