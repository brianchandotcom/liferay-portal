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

package com.liferay.portalweb.portlet.pagecomments.comment.editcommentreplybody;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditCommentReplyBodyTest extends BaseTestCase {
	public void testEditCommentReplyBody() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("PC Comment Reply"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//div[2]/div[3]/div/div[2]/ul/li[3]/span/a/span"));
		selenium.clickAt("//div[2]/div[3]/div/div[2]/ul/li[3]/span/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//textarea[@name='_107_editReplyBody2']");
		selenium.type("//textarea[@name='_107_editReplyBody2']",
			RuntimeVariables.replace("PC Comment Reply Edit"));
		selenium.clickAt("xPath=(//input[@value='Publish'])[2]",
			RuntimeVariables.replace("Publish"));
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("PC Comment Reply Edit"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
	}
}