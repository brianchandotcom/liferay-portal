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

package com.liferay.portalweb.properties.messageboards.mailintegration.replymbcategorythreadmessageemail;

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
				Thread.sleep(60000);

				boolean SignedIn = selenium.isElementPresent("link=Sign out");

				if (!SignedIn) {
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

			case 2:

				boolean signInAsADifferentUserPresent = selenium.isElementPresent(
						"link=Sign in as a different user");

				if (!signInAsADifferentUserPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Sign in as a different user",
					RuntimeVariables.replace("Sign in as a different user"));
				selenium.waitForPageToLoad("30000");

			case 3:
				selenium.waitForElementPresent("//input[@id='Email']");

				boolean usernamePopulated = selenium.isElementPresent(
						"//input[@value='liferay.qa.testing.trunk@gmail.com']");

				if (usernamePopulated) {
					label = 4;

					continue;
				}

				selenium.type("//input[@id='Email']",
					RuntimeVariables.replace("liferay.qa.testing.trunk"));

			case 4:
				selenium.type("//input[@id='Passwd']",
					RuntimeVariables.replace("loveispatient"));

				boolean staySignedInChecked = selenium.isChecked(
						"PersistentCookie");

				if (staySignedInChecked) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='PersistentCookie']"));
				selenium.clickAt("//input[@id='PersistentCookie']",
					RuntimeVariables.replace("Stay signed in"));
				assertTrue(selenium.isChecked("//input[@id='PersistentCookie']"));

			case 5:
				selenium.clickAt("//input[@id='signIn']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.waitForPartialText("//span[@email='liferay.qa.server.trunk@gmail.com']",
					"Admin");
				assertTrue(selenium.isPartialText(
						"//span[@email='liferay.qa.server.trunk@gmail.com']",
						"Admin"));
				selenium.clickAt("//span[@email='liferay.qa.server.trunk@gmail.com']",
					RuntimeVariables.replace("Admin"));
				selenium.waitForPartialText("//h1/span[1]", "MB Message Subject");
				assertTrue(selenium.isPartialText("//h1/span[1]",
						"MB Message Subject"));
				assertTrue(selenium.isPartialText(
						"//div[contains(child::text(),'MB Message Body')]",
						"MB Message Body"));
				selenium.waitForVisible("//td[4]/div/img");
				selenium.clickAt("//td[4]/div/img",
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

				boolean signedIn2 = selenium.isElementPresent("link=Sign out");

				if (!signedIn2) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Sign out"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//td[2]/a",
					RuntimeVariables.replace("Sign out"));

			case 6:
				Thread.sleep(10000);
				selenium.close();
				selenium.selectWindow("null");

			case 100:
				label = -1;
			}
		}
	}
}