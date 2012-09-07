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

package com.liferay.portalweb.portlet.wiki.comment.editfrontpagecommentbody;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditFrontPageCommentBodyTest extends BaseTestCase {
	public void testEditFrontPageCommentBody() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Wiki Test Page");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki Front Page Comment Body"),
			selenium.getText("//div/div[3]/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//li[contains(.,'Edit')]/span/a/span"));
		selenium.clickAt("//li[contains(.,'Edit')]/span/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.type("//textarea[@name='_36_editReplyBody1']",
			RuntimeVariables.replace("Wiki Front Page Comment Body Edit"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Wiki Front Page Comment Body Edit"),
			selenium.getText("//div/div[3]/div/div[1]"));
		assertNotEquals(RuntimeVariables.replace("Wiki Front Page Comment Body"),
			selenium.getText("//div/div[3]/div/div[1]"));
	}
}