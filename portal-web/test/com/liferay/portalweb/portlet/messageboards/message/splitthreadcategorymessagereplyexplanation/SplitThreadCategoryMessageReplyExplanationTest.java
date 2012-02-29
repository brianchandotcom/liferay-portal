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

package com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereplyexplanation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SplitThreadCategoryMessageReplyExplanationTest extends BaseTestCase {
	public void testSplitThreadCategoryMessageReplyExplanation()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isTextPresent(
				"RE: MB Category Thread Message Subject"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//tr[2]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[2]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Reply Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("Split Thread"),
			selenium.getText(
				"//div[5]/table/tbody/tr[2]/td/ul/li[4]/span/a/span"));
		selenium.clickAt("//div[5]/table/tbody/tr[2]/td/ul/li[4]/span/a/span",
			RuntimeVariables.replace("Split Thread"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isChecked(
				"//input[@id='_19_addExplanationPostCheckbox']"));
		selenium.clickAt("//input[@id='_19_addExplanationPostCheckbox']",
			RuntimeVariables.replace(
				"Add explanation post to the source thread."));
		assertTrue(selenium.isChecked(
				"//input[@id='_19_addExplanationPostCheckbox']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@id='_19_subject']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace("MB Explanation Post Subject"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='OK']", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//tr[3]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//tr[4]/td[1]/a"));
		selenium.clickAt("//tr[3]/td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertFalse(selenium.isTextPresent(
				"RE: MB Category Thread Message Subject"));
		assertFalse(selenium.isTextPresent(
				"MB Category Thread Message Reply Body"));
		assertEquals(RuntimeVariables.replace("MB Explanation Post Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[2]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='thread-body'])[2]",
				"The new thread can be found at http://localhost:8080/web/guest/message-boards-test-page/-/message_boards/view_message"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[@class='thread-body'])[2]",
				"The new thread can be found at http://localhost:8080/web/guest/message-boards-test-page/-/message_boards/view_message"));
		assertTrue(selenium.isPartialText("//div[@class='thread-body']/a",
				"http://localhost:8080/web/guest/message-boards-test-page/-/message_boards/view_message"));
		selenium.clickAt("//div[@class='thread-body']/a",
			RuntimeVariables.replace("Link to new thread"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Reply Body"),
			selenium.getText("//div[@class='thread-body']"));
	}
}