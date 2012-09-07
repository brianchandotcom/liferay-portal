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

package com.liferay.portalweb.portlet.wiki.comment.addfrontpagecommentmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFrontPageComment2Test extends BaseTestCase {
	public void testAddFrontPageComment2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Wiki Test Page");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Comment"),
			selenium.getText("//fieldset/div/span[1]/a/span"));
		selenium.clickAt("//fieldset/div/span[1]/a/span",
			RuntimeVariables.replace("Add Comment"));
		selenium.waitForVisible("//textarea[@name='_36_postReplyBody0']");
		selenium.type("//textarea[@name='_36_postReplyBody0']",
			RuntimeVariables.replace("Wiki Front Page Comment2 Body"));
		selenium.click("//input[@value='Reply']");
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki Front Page Comment1 Body"),
			selenium.getText("//div/div[3]/div/div[1]"));
		assertEquals(RuntimeVariables.replace("Wiki Front Page Comment2 Body"),
			selenium.getText("//div[2]/div[3]/div/div[1]"));
	}
}