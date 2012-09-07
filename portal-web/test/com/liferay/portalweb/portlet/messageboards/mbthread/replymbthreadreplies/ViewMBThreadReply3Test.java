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

package com.liferay.portalweb.portlet.messageboards.mbthread.replymbthreadreplies;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMBThreadReply3Test extends BaseTestCase {
	public void testViewMBThreadReply3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("4"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertTrue(selenium.isPartialText("//td[6]/a", "By: Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[7]/span/ul/li/strong/a/span"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[1]"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[1]"));
		assertEquals(RuntimeVariables.replace("RE: MB Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[2]"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Reply1 Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("RE: MB Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[3]"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Reply2 Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[3]"));
		assertEquals(RuntimeVariables.replace("RE: MB Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[4]"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Reply3 Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[4]"));
	}
}