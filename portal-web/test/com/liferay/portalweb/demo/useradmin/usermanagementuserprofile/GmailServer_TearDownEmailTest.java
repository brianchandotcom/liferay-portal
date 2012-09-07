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

package com.liferay.portalweb.demo.useradmin.usermanagementuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class GmailServer_TearDownEmailTest extends BaseTestCase {
	public void testGmailServer_TearDownEmail() throws Exception {
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

				boolean usernamePresent = selenium.isElementPresent(
						"//input[@id='Email']");

				if (usernamePresent) {
					label = 4;

					continue;
				}

				boolean signedIn1 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn1) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 2:
				Thread.sleep(5000);

				boolean signInToGmailPresent = selenium.isTextPresent(
						"Sign in to Gmail");

				if (!signInToGmailPresent) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign in to Gmail"),
					selenium.getText("//a[@id='button']"));
				selenium.clickAt("//a[@id='button']",
					RuntimeVariables.replace("Sign in to Gmail"));
				selenium.waitForPageToLoad("30000");

			case 3:
			case 4:

				boolean signInAsADifferentUserPresent = selenium.isElementPresent(
						"//a[@id='link-force-reauth']");

				if (!signInAsADifferentUserPresent) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"Sign out and sign in as a different user"),
					selenium.getText("//a[@id='link-force-reauth']"));
				selenium.clickAt("//a[@id='link-force-reauth']",
					RuntimeVariables.replace(
						"Sign out and sign in as a different user"));
				selenium.waitForPageToLoad("30000");

			case 5:
				selenium.waitForVisible("//input[@id='Email']");
				selenium.type("//input[@id='Email']",
					RuntimeVariables.replace("liferay.qa.server.trunk2"));
				selenium.type("//input[@id='Passwd']",
					RuntimeVariables.replace("loveispatient"));

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='PersistentCookie']",
					RuntimeVariables.replace("Stay signed in"));

			case 6:
				assertTrue(selenium.isChecked("//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='signIn']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.waitForVisible("//span[@role='checkbox']/div");
				selenium.clickAt("//span[@role='checkbox']/div",
					RuntimeVariables.replace("All"));
				Thread.sleep(5000);

				boolean deleteVisible = selenium.isVisible(
						"//div/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div[3]");

				if (!deleteVisible) {
					label = 7;

					continue;
				}

				selenium.waitForVisible(
					"//div/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div[3]");
				selenium.clickAt("//div/div/div/div[1]/div[1]/div[1]/div/div/div[2]/div[3]",
					RuntimeVariables.replace("Delete"));

			case 7:
				Thread.sleep(10000);

				boolean signedIn2 = selenium.isPartialText("//td/a", "Sign out");

				if (!signedIn2) {
					label = 8;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td/a"));
				selenium.clickAt("//td/a", RuntimeVariables.replace("Sign out"));

			case 8:
				Thread.sleep(5000);
				Thread.sleep(5000);
				selenium.close();
				selenium.selectWindow("null");

			case 100:
				label = -1;
			}
		}
	}
}