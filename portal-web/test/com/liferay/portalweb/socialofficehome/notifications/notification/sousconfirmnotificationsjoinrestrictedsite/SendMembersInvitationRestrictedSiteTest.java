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

package com.liferay.portalweb.socialofficehome.notifications.notification.sousconfirmnotificationsjoinrestrictedsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SendMembersInvitationRestrictedSiteTest extends BaseTestCase {
	public void testSendMembersInvitationRestrictedSite()
		throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
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

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[contains(@class, 'social-office-enabled')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Restricted Site Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Restricted Site Name"),
			selenium.getText("//div[@class='community-title']/a/span"));
		assertEquals(RuntimeVariables.replace("Members"),
			selenium.getText("//nav/ul/li[contains(.,'Members')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Members')]/a/span",
			RuntimeVariables.replace("Members"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Invite members to this site."),
			selenium.getText(
				"//a[contains(text(),'Invite members to this site.')]"));
		selenium.clickAt("//a[contains(text(),'Invite members to this site.')]",
			RuntimeVariables.replace("Invite members to this site."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='search']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[contains(@class, 'user')]/span"));
		selenium.clickAt("//div[contains(@class, 'user')]/span",
			RuntimeVariables.replace("Social01 Office01 User01"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[@class='user-invited']/div/div")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 socialoffice01@liferay.com"),
			selenium.getText("//div[@class='user-invited']/div/div"));
		assertEquals("Send Invitations",
			selenium.getValue("//input[@value='Send Invitations']"));
		selenium.clickAt("//input[@value='Send Invitations']",
			RuntimeVariables.replace("Send Invitations"));

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
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}