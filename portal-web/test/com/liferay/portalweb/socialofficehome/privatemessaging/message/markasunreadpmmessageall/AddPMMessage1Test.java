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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.markasunreadpmmessageall;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPMMessage1Test extends BaseTestCase {
	public void testAddPMMessage1() throws Exception {
		selenium.open("/user/joebloggs/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//nav/ul/li[contains(.,'Messages')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
			RuntimeVariables.replace("Messages"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Private Messaging"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertEquals("Mark as Unread",
			selenium.getValue("//input[@value='Mark as Unread']"));
		assertEquals("Delete", selenium.getValue("//input[@value='Delete']"));
		assertEquals("New Message",
			selenium.getValue("//input[@value='New Message']"));
		selenium.clickAt("//input[@value='New Message']",
			RuntimeVariables.replace("New Message"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span[2]/span/button")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//span[2]/span/button",
			RuntimeVariables.replace("Dropdown"));
		selenium.typeKeys("//input[@id='_1_WAR_privatemessagingportlet_to']",
			RuntimeVariables.replace("Social01"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Social01 Office01 User01")
										.equals(selenium.getText("//li"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//li"));
		selenium.clickAt("//li",
			RuntimeVariables.replace("Social01 Office01 User01"));
		assertEquals("Social01 Office01 User01 <socialoffice01>,",
			selenium.getValue(
				"//input[@id='_1_WAR_privatemessagingportlet_to']"));
		assertTrue(selenium.isVisible(
				"//input[@id='_1_WAR_privatemessagingportlet_subject']"));
		selenium.type("//input[@id='_1_WAR_privatemessagingportlet_subject']",
			RuntimeVariables.replace("Message1 Subject"));
		assertTrue(selenium.isVisible(
				"//textarea[@id='_1_WAR_privatemessagingportlet_body']"));
		selenium.type("//textarea[@id='_1_WAR_privatemessagingportlet_body']",
			RuntimeVariables.replace("Message1 Body"));
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
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//span[@class='author-sender']"));
		assertEquals(RuntimeVariables.replace("Message1 Subject"),
			selenium.getText("//div[@class='subject']"));
		assertEquals(RuntimeVariables.replace("Message1 Body"),
			selenium.getText("//div[@class='body']"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}