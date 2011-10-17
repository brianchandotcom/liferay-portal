/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
public class Gmail_ReplyMBCategoryThreadMessageEmailTest extends BaseTestCase {
	public void testGmail_ReplyMBCategoryThreadMessageEmail()
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
				Thread.sleep(80000);

				boolean SignedIn1 = selenium.isElementPresent("link=Sign out");

				if (!SignedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));
				selenium.clickAt("//span/a",
					RuntimeVariables.replace("Sign in to Gmail"));
				selenium.waitForPageToLoad("30000");

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
					RuntimeVariables.replace("liferay.qa.testing.trunk"));
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
				assertEquals(RuntimeVariables.replace("liferay.qa.server.trunk"),
					selenium.getText("//td[3]/div/span"));
				selenium.clickAt("//td[3]/div/span",
					RuntimeVariables.replace("liferay.qa.server.trunk"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//h1/span[1]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertTrue(selenium.isPartialText("//h1/span[1]",
						"[MB Category Name]"));
				assertTrue(selenium.isPartialText("//h1/span[1]",
						"MB Message Subject"));
				assertTrue(selenium.isPartialText(
						"//div[contains(child::text(),'MB Message Body')]",
						"MB Message Body"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[7]/div/table/tbody/tr/td[1]/div/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[7]/div/table/tbody/tr/td[1]/div/span",
					RuntimeVariables.replace("Reply"));
				Thread.sleep(5000);
				selenium.selectFrame("//iframe[@id='canvas_frame']");
				selenium.selectFrame("//iframe[@class='Am Al editable']");
				selenium.type("//body",
					RuntimeVariables.replace("MB Message Email Reply"));
				selenium.selectFrame("relative=top");
				assertEquals(RuntimeVariables.replace("Send"),
					selenium.getText("//div[3]/div/div/div/div[1]/b"));
				selenium.clickAt("//div[3]/div/div/div/div[1]/b",
					RuntimeVariables.replace("Send"));
				Thread.sleep(10000);

				boolean SignedIn2 = selenium.isElementPresent("link=Sign out");

				if (!SignedIn2) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 5:
				Thread.sleep(10000);
				selenium.close();
				selenium.selectWindow("null");

			case 100:
				label = -1;
			}
		}
	}
}