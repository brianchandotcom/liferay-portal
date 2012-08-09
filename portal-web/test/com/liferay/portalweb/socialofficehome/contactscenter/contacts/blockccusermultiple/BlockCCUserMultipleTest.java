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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.blockccusermultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class BlockCCUserMultipleTest extends BaseTestCase {
	public void testBlockCCUserMultiple() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//nav/ul/li[contains(.,'Contacts Center')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//input[@id='_1_WAR_contactsportlet_name']"));
		assertTrue(selenium.isVisible(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
			RuntimeVariables.replace("socialoffice"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("User01, Social01")
										.equals(selenium.getText(
								"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User01, Social01')]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User01, Social01"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User01, Social01')]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("User02, Social02")
										.equals(selenium.getText(
								"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User02, Social02')]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User02, Social02"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User02, Social02')]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("User03, Social03")
										.equals(selenium.getText(
								"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User03, Social03')]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("User03, Social03"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User03, Social03')]"));
		assertFalse(selenium.isChecked(
				"xPath=(//div[@class='lfr-contact-checkbox']/input)[1]"));
		selenium.clickAt("xPath=(//div[@class='lfr-contact-checkbox']/input)[1]",
			RuntimeVariables.replace("Checkbox"));
		assertTrue(selenium.isChecked(
				"xPath=(//div[@class='lfr-contact-checkbox']/input)[1]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='lfr-contact-thumb'])[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='lfr-contact-thumb'])[4]"));
		assertFalse(selenium.isChecked(
				"xPath=(//div[@class='lfr-contact-checkbox']/input)[2]"));
		selenium.clickAt("xPath=(//div[@class='lfr-contact-checkbox']/input)[2]",
			RuntimeVariables.replace("Checkbox"));
		assertTrue(selenium.isChecked(
				"xPath=(//div[@class='lfr-contact-checkbox']/input)[2]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='lfr-contact-thumb'])[5]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='lfr-contact-thumb'])[5]"));
		assertFalse(selenium.isChecked(
				"xPath=(//div[@class='lfr-contact-checkbox']/input)[3]"));
		selenium.clickAt("xPath=(//div[@class='lfr-contact-checkbox']/input)[3]",
			RuntimeVariables.replace("Checkbox"));
		assertTrue(selenium.isChecked(
				"xPath=(//div[@class='lfr-contact-checkbox']/input)[3]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='lfr-contact-thumb'])[6]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='lfr-contact-thumb'])[6]"));
		assertEquals(RuntimeVariables.replace("Block"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_blockButton']"));
		selenium.clickAt("//button[@id='_1_WAR_contactsportlet_blockButton']",
			RuntimeVariables.replace("Block"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span[@class='portlet-msg-success']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("You have blocked this user."),
			selenium.getText("//span[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("blocked"),
			selenium.getText("xPath=(//div[@class='lfr-social-relations'])[1]"));
		assertEquals(RuntimeVariables.replace("blocked"),
			selenium.getText("xPath=(//div[@class='lfr-social-relations'])[2]"));
		assertEquals(RuntimeVariables.replace("blocked"),
			selenium.getText("xPath=(//div[@class='lfr-social-relations'])[3]"));
		assertEquals(RuntimeVariables.replace("Unblock"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_unblockButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_blockButton']"));
	}
}