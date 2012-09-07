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
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertTrue(selenium.isElementPresent(
				"//li[@id='_145_notificationsMenu']"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//span[@class='notification-count']"));
		selenium.mouseOver("//li[@id='_145_notificationsMenu']");
		selenium.waitForVisible("//div[@class='title']");
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 would like to add you as a connection."),
			selenium.getText("//div[@class='title']"));
		assertEquals(RuntimeVariables.replace("Confirm"),
			selenium.getText(
				"//span[@class='lfr-user-action-item lfr-user-action-confirm']/a"));
		selenium.clickAt("//span[@class='lfr-user-action-item lfr-user-action-confirm']/a",
			RuntimeVariables.replace("Confirm"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//span[@class='notification-count']");
		selenium.clickAt("//span[@class='notification-count']",
			RuntimeVariables.replace("Notification Count"));
		assertFalse(selenium.isTextPresent(
				"Social01 would like to add you as a connection."));
	}
}