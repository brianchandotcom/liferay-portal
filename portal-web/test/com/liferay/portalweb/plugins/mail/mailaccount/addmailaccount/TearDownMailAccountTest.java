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

package com.liferay.portalweb.plugins.mail.mailaccount.addmailaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownMailAccountTest extends BaseTestCase {
	public void testTearDownMailAccount() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home");
				selenium.getEval("window.Liferay.fire('initDockbar');");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Mail Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Mail Test Page",
					RuntimeVariables.replace("Mail Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.getEval("window.Liferay.fire('initDockbar');");
				Thread.sleep(5000);

				boolean enterYourPasswordPresent = selenium.isElementPresent(
						"//input[@id='_1_WAR_mailportlet_password']");

				if (!enterYourPasswordPresent) {
					label = 2;

					continue;
				}

				selenium.type("//input[@id='_1_WAR_mailportlet_password']",
					RuntimeVariables.replace("loveispatient"));
				selenium.clickAt("//input[@value='Login']",
					RuntimeVariables.replace("Login"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (!selenium.isElementPresent(
									"//input[@id='_1_WAR_mailportlet_password']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				Thread.sleep(5000);

			case 2:

				boolean emailPresent = selenium.isElementPresent(
						"//div/div/div[1]/div/ul/li/span/span");

				if (!emailPresent) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"liferay.qa.testing.trunk@gmail.com"),
					selenium.getText("//div/div/div[1]/div/ul/li/span/span"));
				selenium.clickAt("//div/div/div[1]/div/ul/li/span/span",
					RuntimeVariables.replace(
						"liferay.qa.testing.trunk@gmail.com"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Edit Account")
												.equals(selenium.getText(
										"//a[@class='edit-account']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Edit Account"),
					selenium.getText("//a[@class='edit-account']"));
				selenium.clickAt("//a[@class='edit-account']",
					RuntimeVariables.replace("Edit Account"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//a[@class='delete-account']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isElementPresent(
						"//a[@class='delete-account']"));
				selenium.clickAt("//a[@class='delete-account']",
					RuntimeVariables.replace("Delete Account"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this account[\\s\\S]$"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//span[@class='message portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Account has been deleted."),
					selenium.getText(
						"//span[@class='message portlet-msg-success']"));
				Thread.sleep(5000);

			case 3:
				selenium.open("/web/guest/home/");
				selenium.getEval("window.Liferay.fire('initDockbar');");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Mail Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Mail Test Page",
					RuntimeVariables.replace("Mail Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.getEval("window.Liferay.fire('initDockbar');");
				assertFalse(selenium.isTextPresent(
						"liferay.qa.testing.trunk@gmail.com"));

			case 100:
				label = -1;
			}
		}
	}
}