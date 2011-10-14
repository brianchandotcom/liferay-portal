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
public class Gmail_ViewMBThreadMessageGmailMailingListTest extends BaseTestCase {
	public void testGmail_ViewMBThreadMessageGmailMailingList()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();
				selenium.selectFrame("relative=top");
				selenium.saveScreenShotAndSource();
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				selenium.saveScreenShotAndSource();
				Thread.sleep(80000);

				boolean signedIn1 = selenium.isElementPresent("link=Sign out");

				if (!signedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));
				selenium.clickAt("//span/a",
					RuntimeVariables.replace("Sign in to Gmail"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

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
				selenium.saveScreenShotAndSource();

			case 3:

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='Email']",
					RuntimeVariables.replace("liferay.qa.server.trunk"));
				selenium.saveScreenShotAndSource();
				selenium.type("//input[@id='Passwd']",
					RuntimeVariables.replace("loveispatient"));
				selenium.saveScreenShotAndSource();

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='PersistentCookie']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@id='PersistentCookie']",
					RuntimeVariables.replace("Stay signed in"));

			case 4:
				assertTrue(selenium.isChecked("//input[@id='PersistentCookie']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@id='signIn']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();
				Thread.sleep(10000);
				selenium.openWindow("http://groups.google.com/",
					RuntimeVariables.replace("Google Groups"));
				selenium.waitForPopUp("Google Groups",
					RuntimeVariables.replace(""));
				selenium.selectWindow("Google Groups");
				selenium.saveScreenShotAndSource();
				Thread.sleep(10000);
				assertEquals(RuntimeVariables.replace("liferay-mailinglist"),
					selenium.getText("//a[1]/font/b"));
				selenium.clickAt("//a[1]/font/b",
					RuntimeVariables.replace("liferay-mailinglist"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isElementPresent("//span[1]/a"));
				selenium.clickAt("//span[1]/a",
					RuntimeVariables.replace(
						"[MB Category Name] MB Message Subject"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isPartialText(
						"//table[@id='top']/tbody/tr[2]/td[1]/span/span/i",
						"liferay.qa.server"));
				assertEquals(RuntimeVariables.replace(
						"MB Message Body\n--\nLiferay Message Boards\nhttp://localhost:8080/web/site-name/message-boards-test-page/-/message_boards/view_message/10508\n\nhttp://localhost"),
					selenium.getText(
						"//div[1]/table/tbody/tr/td[2]/div[1]/div[2]/div"));
				assertEquals(RuntimeVariables.replace("Trunk Liferay QA"),
					selenium.getText(
						"//div[2]/table/tbody/tr/td[2]/div[1]/div[1]/table/tbody/tr[2]/td[1]/span/span"));
				assertEquals(RuntimeVariables.replace("MB Message Email Reply"),
					selenium.getText("//p"));

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
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}