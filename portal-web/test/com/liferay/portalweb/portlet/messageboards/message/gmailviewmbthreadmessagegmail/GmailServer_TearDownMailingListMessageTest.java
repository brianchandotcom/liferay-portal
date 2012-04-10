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

package com.liferay.portalweb.portlet.messageboards.message.gmailviewmbthreadmessagegmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class GmailServer_TearDownMailingListMessageTest extends BaseTestCase {
	public void testGmailServer_TearDownMailingListMessage()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				Thread.sleep(10000);

				boolean SignedIn1 = selenium.isElementPresent("link=Sign out");

				if (!SignedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Sign out"));
				selenium.clickAt("//span/a",
					RuntimeVariables.replace("Sign in to Gmail"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 2:

				boolean signInAsADifferentUserPresent = selenium.isElementPresent(
						"link=Sign out and sign in as a different user");

				if (!signInAsADifferentUserPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Sign out and sign in as a different user",
					RuntimeVariables.replace(
						"Sign out and sign in as a different user"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='Email']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.type("//input[@id='Email']",
					RuntimeVariables.replace("liferay.qa.server.trunk"));
				selenium.type("//input[@id='Passwd']",
					RuntimeVariables.replace("loveispatient"));

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='PersistentCookie']",
					RuntimeVariables.replace("Stay signed in"));

			case 4:
				assertTrue(selenium.isChecked("//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='signIn']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				Thread.sleep(10000);
				selenium.openWindow("http://groups.google.com/",
					RuntimeVariables.replace("Google Groups"));
				selenium.waitForPopUp("Google Groups",
					RuntimeVariables.replace(""));
				selenium.selectWindow("Google Groups");
				Thread.sleep(10000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//td/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("My groups"),
					selenium.getText("//td/a/span"));
				selenium.click("//td/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//tr[2]/td/div/div/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.click("//tr[2]/td/div/div/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("liferay-mailinglist")
												.equals(selenium.getText(
										"//span/span[2]/span"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("liferay-mailinglist"),
					selenium.getText("//span/span[2]/span"));
				Thread.sleep(10000);

				boolean MLMessage1Present = selenium.isElementPresent(
						"//span[contains(@class,'CheckBox')]/span/span");

				if (!MLMessage1Present) {
					label = 5;

					continue;
				}

				selenium.click("//span[contains(@class,'CheckBox')]/span/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//div[contains(@id,'more_actions')]/span/span"));
				selenium.clickAt("//div[contains(@id,'more_actions')]/span/span",
					RuntimeVariables.replace("Actions"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@id,'DELETE_TOPICS')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//div[contains(@id,'DELETE_TOPICS')]"));
				selenium.click("//div[contains(@id,'DELETE_TOPICS')]");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[@id='dialog_ok']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("OK"),
					selenium.getText("//div[@id='dialog_ok']"));
				selenium.click("//div[@id='dialog_ok']");
				Thread.sleep(5000);

				boolean MLMessage2Present = selenium.isElementPresent(
						"//span[contains(@class,'CheckBox')]/span/span");

				if (!MLMessage2Present) {
					label = 6;

					continue;
				}

				selenium.click("//span[contains(@class,'CheckBox')]/span/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//div[contains(@id,'more_actions')]/span/span"));
				selenium.clickAt("//div[contains(@id,'more_actions')]/span/span",
					RuntimeVariables.replace("Actions"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@id,'DELETE_TOPICS')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//div[contains(@id,'DELETE_TOPICS')]"));
				selenium.click("//div[contains(@id,'DELETE_TOPICS')]");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[@id='dialog_ok']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("OK"),
					selenium.getText("//div[@id='dialog_ok']"));
				selenium.click("//div[@id='dialog_ok']");
				Thread.sleep(5000);

				boolean MLMessage3Present = selenium.isElementPresent(
						"//span[contains(@class,'CheckBox')]/span/span");

				if (!MLMessage3Present) {
					label = 7;

					continue;
				}

				selenium.click("//span[contains(@class,'CheckBox')]/span/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//div[contains(@id,'more_actions')]/span/span"));
				selenium.clickAt("//div[contains(@id,'more_actions')]/span/span",
					RuntimeVariables.replace("Actions"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@id,'DELETE_TOPICS')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//div[contains(@id,'DELETE_TOPICS')]"));
				selenium.click("//div[contains(@id,'DELETE_TOPICS')]");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[@id='dialog_ok']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("OK"),
					selenium.getText("//div[@id='dialog_ok']"));
				selenium.click("//div[@id='dialog_ok']");
				Thread.sleep(5000);

				boolean MLMessage4Present = selenium.isElementPresent(
						"//span[contains(@class,'CheckBox')]/span/span");

				if (!MLMessage4Present) {
					label = 8;

					continue;
				}

				selenium.click("//span[contains(@class,'CheckBox')]/span/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//div[contains(@id,'more_actions')]/span/span"));
				selenium.clickAt("//div[contains(@id,'more_actions')]/span/span",
					RuntimeVariables.replace("Actions"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@id,'DELETE_TOPICS')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//div[contains(@id,'DELETE_TOPICS')]"));
				selenium.click("//div[contains(@id,'DELETE_TOPICS')]");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[@id='dialog_ok']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("OK"),
					selenium.getText("//div[@id='dialog_ok']"));
				selenium.click("//div[@id='dialog_ok']");
				Thread.sleep(5000);

				boolean MLMessage5Present = selenium.isElementPresent(
						"//span[contains(@class,'CheckBox')]/span/span");

				if (!MLMessage5Present) {
					label = 9;

					continue;
				}

				selenium.click("//span[contains(@class,'CheckBox')]/span/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//div[contains(@id,'more_actions')]/span/span"));
				selenium.clickAt("//div[contains(@id,'more_actions')]/span/span",
					RuntimeVariables.replace("Actions"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@id,'DELETE_TOPICS')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//div[contains(@id,'DELETE_TOPICS')]"));
				selenium.click("//div[contains(@id,'DELETE_TOPICS')]");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[@id='dialog_ok']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("OK"),
					selenium.getText("//div[@id='dialog_ok']"));
				selenium.click("//div[@id='dialog_ok']");
				Thread.sleep(5000);

			case 5:
			case 6:
			case 7:
			case 8:
			case 9:

				boolean SignedIn2 = selenium.isPartialText("//td[2]/a",
						"Sign out");

				if (!SignedIn2) {
					label = 10;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Sign out"));

			case 10:
				Thread.sleep(10000);
				selenium.close();
				selenium.selectWindow("null");

			case 100:
				label = -1;
			}
		}
	}
}