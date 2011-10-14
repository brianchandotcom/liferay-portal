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

package com.liferay.portalweb.properties.mailintegration.webcontent.wcwebcontent.gmailviewwcwebcontentaddedemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Gmail_ViewWCWebContentAddedEmailTest extends BaseTestCase {
	public void testGmail_ViewWCWebContentAddedEmail()
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
				Thread.sleep(60000);

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
									"Administrator")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertTrue(selenium.isPartialText("//td[3]/div/span",
						"Administrator"));
				selenium.clickAt("//td[3]/div/span",
					RuntimeVariables.replace("Administrator"));
				Thread.sleep(5000);
				assertTrue(selenium.isPartialText("//h1/span[1]",
						"Web Content: Article Added"));
				assertTrue(selenium.isPartialText(
						"//p[contains(.,'Web Content Name')]",
						"Web Content Name was added."));
				Thread.sleep(5000);

				boolean SignedIn2 = selenium.isElementPresent("link=Sign out");

				if (!SignedIn2) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 5:
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