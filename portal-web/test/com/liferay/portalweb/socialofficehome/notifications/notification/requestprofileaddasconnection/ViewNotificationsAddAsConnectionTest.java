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

package com.liferay.portalweb.socialofficehome.notifications.notification.requestprofileaddasconnection;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewNotificationsAddAsConnectionTest extends BaseTestCase {
	public void testViewNotificationsAddAsConnection()
		throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
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
		assertEquals(RuntimeVariables.replace(
				"Social01 would like to add you as a connection."),
			selenium.getText(
				"//div[contains(@class, 'user-notification-event-content')]/div[2]/div/span"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[@class='notification-entry']/div[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Ignore"),
			selenium.getText(
				"//div[@class='notification-entry']/div[2]/span[2]/a"));
	}
}