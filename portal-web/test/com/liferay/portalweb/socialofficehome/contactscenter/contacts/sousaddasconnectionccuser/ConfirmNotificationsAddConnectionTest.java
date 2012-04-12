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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.sousaddasconnectionccuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfirmNotificationsAddConnectionTest extends BaseTestCase {
	public void testConfirmNotificationsAddConnection()
		throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//li[@id='_145_notificationsMenu']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='notification-entry']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Social01 would like to add you as a connection."),
			selenium.getText("//div[@class='notification-entry']/div/span"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText("//div[@class='request']/span/a"));
		selenium.clickAt("//div[@class='request']/span/a",
			RuntimeVariables.replace("Confirm"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0")
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
	}
}