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

package com.liferay.portalweb.portlet.pagecomments.comment.canceladdcommentreply;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CancelAddCommentReplyTest extends BaseTestCase {
	public void testCancelAddCommentReply() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Post Reply"),
			selenium.getText("//li[1]/span/a/span"));
		selenium.clickAt("//li[1]/span/a/span",
			RuntimeVariables.replace("Post Reply"));
		selenium.waitForVisible("//textarea[@name='_107_postReplyBody1']");
		assertTrue(selenium.isVisible("//textarea[@name='_107_postReplyBody1']"));
		selenium.type("//textarea[@name='_107_postReplyBody1']",
			RuntimeVariables.replace("PC Comment Reply"));
		selenium.clickAt("xPath=(//input[@value='Cancel'])[2]",
			RuntimeVariables.replace("Cancel"));
		selenium.waitForNotVisible("//textarea[@name='_107_postReplyBody1']");
		assertFalse(selenium.isTextPresent("PC Comment Reply"));
		assertFalse(selenium.isVisible(
				"//textarea[@name='_107_postReplyBody1']"));
	}
}