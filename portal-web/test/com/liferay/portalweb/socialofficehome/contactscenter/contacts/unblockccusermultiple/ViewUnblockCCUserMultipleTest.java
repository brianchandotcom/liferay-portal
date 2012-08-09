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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.unblockccusermultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUnblockCCUserMultipleTest extends BaseTestCase {
	public void testViewUnblockCCUserMultiple() throws Exception {
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
		assertEquals(RuntimeVariables.replace("You have 0 connections."),
			selenium.getText("link=You have 0 connections."));
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
		selenium.clickAt("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User01, Social01')]",
			RuntimeVariables.replace("User01, Social01"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice01@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Block"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_blockButton']"));
		assertEquals(RuntimeVariables.replace("Add Connection"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_addConnectionButton']"));
		assertEquals(RuntimeVariables.replace("Follow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_unblockButton']"));

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
		selenium.clickAt("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User02, Social02')]",
			RuntimeVariables.replace("User02, Social02"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Social02 Office02 User02")
										.equals(selenium.getText(
								"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social02 Office02 User02"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice02@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Block"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_blockButton']"));
		assertEquals(RuntimeVariables.replace("Add Connection"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_addConnectionButton']"));
		assertEquals(RuntimeVariables.replace("Follow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_unblockButton']"));

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
		selenium.clickAt("xPath=(//div[contains(@class, 'lfr-contact-name')]/a)[contains(.,'User03, Social03')]",
			RuntimeVariables.replace("User03, Social03"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[contains(@class, 'contacts-profile')]")) {
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
				if (RuntimeVariables.replace("Social03 Office03 User03")
										.equals(selenium.getText(
								"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Social03 Office03 User03"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		assertEquals(RuntimeVariables.replace("socialoffice03@liferay.com"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class, 'contacts-center-home-content')]"));
		assertEquals(RuntimeVariables.replace("Block"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_blockButton']"));
		assertEquals(RuntimeVariables.replace("Add Connection"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_addConnectionButton']"));
		assertEquals(RuntimeVariables.replace("Follow"),
			selenium.getText(
				"//button[@id='_1_WAR_contactsportlet_followButton']"));
		assertFalse(selenium.isVisible(
				"//button[@id='_1_WAR_contactsportlet_unblockButton']"));
	}
}