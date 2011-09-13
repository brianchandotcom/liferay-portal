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

package com.liferay.portalweb.socialofficeprofile.mycontacts.mycontact.filtermcallcontacts;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfirmNotificationsMultipleTest extends BaseTestCase {
	public void testConfirmNotificationsMultiple() throws Exception {
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/div[1]/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div/div/div/div[1]/ul/li[1]/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Notifications"),
			selenium.getText("//div[2]/div/section/header/h1/span"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/div/section/div")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln says you are a coworker."),
			selenium.getText("//section/div/div/div/div/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[2]/span[1]/a"));
		selenium.clickAt("//div[2]/span[1]/a",
			RuntimeVariables.replace("Confirm"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/user/joebloggs/home/");
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln wants to be your friend."),
			selenium.getText("//section/div/div/div/div/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[2]/span[1]/a"));
		selenium.clickAt("//div[2]/span[1]/a",
			RuntimeVariables.replace("Confirm"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//section/div/div/div/div/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//section/div/div/div/div/ul/li[2]/a",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Friends"),
			selenium.getText(
				"//div/div/div/div[2]/div/div/div/div/div/div/div/span"));
		assertTrue(selenium.isVisible("//div[1]/a/img"));
		selenium.mouseOver("//div[1]/a/img");
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
			selenium.getText("//li/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendea@liferay.com"),
			selenium.getText("//div[3]/span"));
		assertEquals(RuntimeVariables.replace("Manage 1 friends."),
			selenium.getText("//div[1]/div/div/div/div[2]/a"));
		assertEquals(RuntimeVariables.replace("Coworkers"),
			selenium.getText("//div[2]/div/div/span"));
		assertTrue(selenium.isVisible("//div[1]/a/img"));
		selenium.mouseOver("//div[2]/div[2]/div/ul/li/div/a/img");
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln"),
			selenium.getText("//div[2]/div[2]/div/ul/li/div[2]/div"));
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerea@liferay.com"),
			selenium.getText("//div[2]/div[2]/div/ul/li/div[2]/div[3]/span"));
		assertEquals(RuntimeVariables.replace("Manage 1 coworkers."),
			selenium.getText("//div[2]/div[2]/a"));
		selenium.open("/user/joebloggs/home/");
		assertFalse(selenium.isElementPresent(
				"xPath=(//div[@class='notification-entry'])[1]"));
		assertFalse(selenium.isElementPresent(
				"xPath=(//div[@class='notification-entry'])[2]"));
		assertFalse(selenium.isTextPresent(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln wants to be your friend."));
		assertFalse(selenium.isTextPresent(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln says you are a coworker."));
	}
}