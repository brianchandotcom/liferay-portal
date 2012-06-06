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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.viewcontactcc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownContactTest extends BaseTestCase {
	public void testTearDownContact() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/so/dashboard/");
				loadRequiredJavaScriptModules();

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
				loadRequiredJavaScriptModules();
				assertTrue(selenium.isVisible(
						"//input[@id='_1_WAR_contactsportlet_name']"));
				selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
					RuntimeVariables.replace("social"));
				Thread.sleep(5000);

				boolean contact1Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact1Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact1Name", contact1Name);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[contains(.,'Delete')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete " +
						RuntimeVariables.getValue("contact1Name") +
						" from your contacts[\\s\\S]$"));

			case 2:
				selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
					RuntimeVariables.replace("social"));
				Thread.sleep(5000);

				boolean contact2Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact2Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact2Name", contact2Name);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[contains(.,'Delete')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete " +
						RuntimeVariables.getValue("contact2Name") +
						" from your contacts[\\s\\S]$"));

			case 3:
				selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
					RuntimeVariables.replace("social"));
				Thread.sleep(5000);

				boolean contact3Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact3Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact3Name", contact3Name);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[contains(.,'Delete')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete " +
						RuntimeVariables.getValue("contact3Name") +
						" from your contacts[\\s\\S]$"));

			case 4:
				selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
					RuntimeVariables.replace("social"));
				Thread.sleep(5000);

				boolean contact4Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact4Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact4Name", contact4Name);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[contains(.,'Delete')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete " +
						RuntimeVariables.getValue("contact4Name") +
						" from your contacts[\\s\\S]$"));

			case 5:
				selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
					RuntimeVariables.replace("social"));
				Thread.sleep(5000);

				boolean contact5Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact5Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact5Name", contact5Name);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//button[contains(.,'Delete')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete " +
						RuntimeVariables.getValue("contact5Name") +
						" from your contacts[\\s\\S]$"));

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}