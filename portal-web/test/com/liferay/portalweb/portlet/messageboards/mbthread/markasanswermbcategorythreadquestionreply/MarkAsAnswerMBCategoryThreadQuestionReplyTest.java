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

package com.liferay.portalweb.portlet.messageboards.mbthread.markasanswermbcategorythreadquestionreply;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MarkAsAnswerMBCategoryThreadQuestionReplyTest extends BaseTestCase {
	public void testMarkAsAnswerMBCategoryThreadQuestionReply()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Waiting for an Answer"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Mark as an Answer"),
			selenium.getText(
				"//div[5]/table/tbody/tr[1]/td[2]/div[1]/ul/li[1]/span/a/span"));
		selenium.clickAt("//div[5]/table/tbody/tr[1]/td[2]/div[1]/ul/li[1]/span/a/span",
			RuntimeVariables.replace("Mark as an Answer"));
		selenium.waitForNotVisible(
			"//div[5]/table/tbody/tr[1]/td[2]/div[1]/ul/li[1]/span/a/span");
		assertFalse(selenium.isVisible(
				"//div[5]/table/tbody/tr[1]/td[2]/div[1]/ul/li[1]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Answer"),
			selenium.getText(
				"//div[5]/table/tbody/tr[1]/td[2]/div[1]/div/div[2]/span/span"));
		assertEquals(RuntimeVariables.replace("Unmark"),
			selenium.getText(
				"//div[5]/table/tbody/tr[1]/td[2]/div[1]/div/div[2]/a"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Resolved"),
			selenium.getText("//td[2]/a"));
		assertNotEquals(RuntimeVariables.replace("Waiting for an Answer"),
			selenium.getText("//td[2]/a"));
	}
}