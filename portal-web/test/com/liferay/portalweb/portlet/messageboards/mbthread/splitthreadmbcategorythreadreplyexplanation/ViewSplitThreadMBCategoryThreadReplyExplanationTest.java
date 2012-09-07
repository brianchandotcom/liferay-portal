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

package com.liferay.portalweb.portlet.messageboards.mbthread.splitthreadmbcategorythreadreplyexplanation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSplitThreadMBCategoryThreadReplyExplanationTest
	extends BaseTestCase {
	public void testViewSplitThreadMBCategoryThreadReplyExplanation()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("3"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[1]/a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertTrue(selenium.isPartialText("//td[6]/a", "By: Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[7]/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[4]/td[4]/a"));
		assertTrue(selenium.isVisible("//tr[4]/td[5]/a"));
		assertTrue(selenium.isPartialText("//tr[4]/td[6]/a", "By: Joe Bloggs"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//tr[4]/td[7]/span/ul/li/strong/a/span"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//td[1]/a/strong"));
		selenium.clickAt("//td[1]/a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[1]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[1]/td[2]/a"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[1]/td[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Explanation Subject"),
			selenium.getText(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[2]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[2]/td[2]/a"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[2]/td[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[1]"));
		assertEquals(RuntimeVariables.replace("MB Category Thread Message Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Explanation Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='thread-body'])[2]",
				"The new thread can be found at http://localhost:8080/web/guest/message-boards-test-page/-/message_boards/view_message/"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='thread-body'])[2]/a",
				"http://localhost:8080/web/guest/message-boards-test-page/-/message_boards/view_message/"));
		selenium.clickAt("xPath=(//div[@class='thread-body'])[2]/a",
			RuntimeVariables.replace(
				"http://localhost:8080/web/guest/message-boards-test-page/-/message_boards/view_message/"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[1]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Reply Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[1]"));
	}
}