/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.messageboards.message.splitthreadcategorymessagereplybackbutton;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SplitThreadCategoryMessageReplyBackButtonTest extends BaseTestCase {
	public void testSplitThreadCategoryMessageReplyBackButton()
		throws Exception {
		selenium.open("/web/guest/home/");

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
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"RE: MB Category Thread Message Subject"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
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
		selenium.clickAt("//input[@value='OK']", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		selenium.keyPress("//div[1]/span", RuntimeVariables.replace("\\8"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='OK']", RuntimeVariables.replace("OK"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace(
				"Click OK to create a new thread with the following messages."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals("RE: MB Category Thread Message Subject",
			selenium.getValue("//input[@id='_19_splitThreadSubject']"));
	}
}