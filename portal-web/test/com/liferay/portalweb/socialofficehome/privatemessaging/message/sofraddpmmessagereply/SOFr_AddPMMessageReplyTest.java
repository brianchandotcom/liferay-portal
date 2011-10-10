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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.sofraddpmmessagereply;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOFr_AddPMMessageReplyTest extends BaseTestCase {
	public void testSOFr_AddPMMessageReply() throws Exception {
		selenium.open("/user/socialofficefriendsn/home");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//nav/ul/li[1]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/div[1]/ul/li[4]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div[2]/div[1]/ul/li[4]/a",
			RuntimeVariables.replace("Private Messaging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Private Messaging"),
			selenium.getText("//h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/div[1]/div/a"));
		assertEquals(RuntimeVariables.replace("Message Subject\n Message Body"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[2]/div[2]/div/div"));
		selenium.clickAt("//td[4]/a",
			RuntimeVariables.replace("Message Subject\n Message Body"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Subject"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("Between Joe Bloggs and you"),
			selenium.getText("//div/div[3]/div"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[2]/div[1]/div/span[1]/a"));
		assertEquals(RuntimeVariables.replace("Message Body"),
			selenium.getText("//td[2]/div[2]/div"));
		assertTrue(selenium.isVisible("//textarea"));
		selenium.type("//textarea",
			RuntimeVariables.replace("Message Subject Reply"));
		selenium.clickAt("//input[@value='Send']",
			RuntimeVariables.replace("Send"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Message Subject\n Message Subject Reply"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[2]/div[2]/div/div"));
	}
}