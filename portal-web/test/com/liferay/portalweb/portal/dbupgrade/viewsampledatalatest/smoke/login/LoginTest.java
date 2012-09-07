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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.smoke.login;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginTest extends BaseTestCase {
	public void testLogin() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.setTimeout("180000");
				selenium.open("/web/guest/home");
				selenium.waitForVisible("link=Sign In");
				selenium.clickAt("link=Sign In",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@id='_58_login']");
				selenium.type("//input[@id='_58_login']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.type("//input[@id='_58_password']",
					RuntimeVariables.replace("test"));
				selenium.waitForVisible("//input[@id='_58_rememberMeCheckbox']");
				selenium.click("//input[@id='_58_rememberMeCheckbox']");
				selenium.waitForVisible("//input[@value='Sign In']");
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");

				boolean agreementAvailable = selenium.isTextPresent(
						"Terms of Use");

				if (!agreementAvailable) {
					label = 2;

					continue;
				}

				selenium.waitForVisible("//input[@value='I Agree']");
				selenium.clickAt("//input[@value='I Agree']",
					RuntimeVariables.replace("I Agree"));
				selenium.waitForPageToLoad("30000");

			case 2:

				boolean newPasswordPresent = selenium.isTextPresent(
						"New Password");

				if (!newPasswordPresent) {
					label = 3;

					continue;
				}

				selenium.waitForVisible("//input[@id='password1']");
				selenium.type("//input[@id='password1']",
					RuntimeVariables.replace("test1"));
				selenium.type("//input[@id='password2']",
					RuntimeVariables.replace("test1"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");

			case 3:

				boolean reminderQueryPresent = selenium.isElementPresent(
						"reminderQueryAnswer");

				if (!reminderQueryPresent) {
					label = 4;

					continue;
				}

				selenium.waitForVisible("//input[@id='reminderQueryAnswer']");
				selenium.type("//input[@id='reminderQueryAnswer']",
					RuntimeVariables.replace("Test"));
				selenium.waitForVisible("//input[@value='Save']");
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");

			case 4:
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//header/h1/span"));

			case 100:
				label = -1;
			}
		}
	}
}