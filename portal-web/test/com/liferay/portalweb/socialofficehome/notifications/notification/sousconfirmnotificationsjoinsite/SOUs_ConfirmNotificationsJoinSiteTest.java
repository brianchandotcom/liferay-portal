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

package com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationsjoinsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ConfirmNotificationsJoinSiteTest extends BaseTestCase {
	public void testSOUs_ConfirmNotificationsJoinSite()
		throws Exception {
		selenium.open("/user/socialoffice01/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span[@class='notification-count']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//span[@class='notification-count']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//div[contains(@class, 'user-notification-events-container')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"//div[contains(@class, 'user-notification-events-container')]"));
		assertTrue(selenium.isPartialText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div",
				"Joe Bloggs invited you to join"));
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div/a[2]"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[@class='notification-entry']/div[2]/span/a"));
		selenium.clickAt("//div[@class='notification-entry']/div[2]/span/a",
			RuntimeVariables.replace("Confirm"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (!RuntimeVariables.replace("1")
										 .equals(selenium.getText(
								"//span[@class='notification-count']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.open("/user/socialoffice01/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[contains(@class, 'selected')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_5_WAR_soportlet_tabs1']", "My Sites"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("My Sites"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (!selenium.isPartialText(
							"//ul[contains(@class, 'site-list')]/",
							"liferay.com")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertFalse(selenium.isPartialText(
				"//ul[contains(@class, 'site-list')]/", "liferay.com"));
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
	}
}