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

package com.liferay.portalweb.socialofficeprofile.profile.viewprofileinstantmessenger;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewProfileInstantMessengerTest extends BaseTestCase {
	public void testViewProfileInstantMessenger() throws Exception {
		selenium.open("/web/socialoffice01/so/profile");
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

		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		assertTrue(selenium.isVisible(
				"//div[@class='section field-group lfr-user-instant-messenger']/h3"));
		assertEquals(RuntimeVariables.replace("Instant Messenger:"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/h3"));
		assertEquals(RuntimeVariables.replace("AIM"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[1]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[1]/span[2]"));
		assertEquals(RuntimeVariables.replace("ICQ"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[2]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[2]/span[2]"));
		assertEquals(RuntimeVariables.replace("Jabber"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[3]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[3]/span[2]"));
		assertEquals(RuntimeVariables.replace("MSN"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[4]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[4]/span[2]"));
		assertEquals(RuntimeVariables.replace("Skype"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[5]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[5]/span[2]"));
		assertEquals(RuntimeVariables.replace("YM"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[6]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@class='section field-group lfr-user-instant-messenger']/ul/li[6]/span[2]"));
	}
}