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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.updatediscussion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_EditCommentTest extends BaseTestCase {
	public void testMember_EditComment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		selenium.clickAt("link=Edit", RuntimeVariables.replace("Edit"));
		selenium.waitForVisible(
			"//div[@class='lfr-discussion-form lfr-discussion-form-edit']/span/span/span/textarea");
		selenium.type("//div[@class='lfr-discussion-form lfr-discussion-form-edit']/span/span/span/textarea",
			RuntimeVariables.replace("WC WebContent Comment Edited"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Comment Edited"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
		assertNotEquals(RuntimeVariables.replace("WC WebContent Comment"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
	}
}