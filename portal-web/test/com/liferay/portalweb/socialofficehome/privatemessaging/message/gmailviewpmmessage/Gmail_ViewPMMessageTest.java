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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.gmailviewpmmessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Gmail_ViewPMMessageTest extends BaseTestCase {
	public void testGmail_ViewPMMessage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.openWindow("http://www.gmail.com/",
					RuntimeVariables.replace("gmail"));
				selenium.waitForPopUp("gmail", RuntimeVariables.replace(""));
				selenium.selectWindow("gmail");
				selenium.saveScreenShotAndSource();
				Thread.sleep(60000);

				boolean signedIn1 = selenium.isElementPresent(
						"//div[2]/div/nobr/a[2]");

				if (!signedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//div[2]/div/nobr/a[2]"));
				selenium.clickAt("//div[2]/div/nobr/a[2]",
					RuntimeVariables.replace("Sign out"));

			case 2:

				boolean signedIn2 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn2) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

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
					RuntimeVariables.replace("liferay.qa.testing.trunk"));
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

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText("//td[3]/div/span",
									"liferay.qa")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isPartialText("//td[3]/div/span",
						"liferay.qa"));
				selenium.clickAt("//td[3]/div/span",
					RuntimeVariables.replace("liferay.qa"));
				assertTrue(selenium.isPartialText("//h1/span[1]",
						"Joe Bloggs sent you a message on Liferay"));
				assertTrue(selenium.isPartialText(
						"//div[5]/div/div/div/div[3]",
						"Message Subject \n Message Body"));
				Thread.sleep(5000);

				boolean signedIn3 = selenium.isElementPresent(
						"//div[2]/div[2]/div/nobr/a[2]");

				if (!signedIn3) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//div[2]/div[2]/div/nobr/a[2]"));
				selenium.clickAt("//div[2]/div[2]/div/nobr/a[2]",
					RuntimeVariables.replace("Sign out"));

			case 5:

				boolean signedIn4 = selenium.isElementPresent(
						"//span[2]/span/span[1]");

				if (!signedIn4) {
					label = 6;

					continue;
				}

				selenium.clickAt("//span[2]/span/span[1]",
					RuntimeVariables.replace(
						"liferay.qa.testing.trunk@gmail.com"));
				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("link=Sign out"));
				selenium.clickAt("link=Sign out",
					RuntimeVariables.replace("Sign out"));

			case 6:
				Thread.sleep(5000);
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");
				selenium.saveScreenShotAndSource();

			case 100:
				label = -1;
			}
		}
	}
}