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

package com.liferay.portalweb.portal.controlpanel.virtualhosting;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertPrivatePageHostURLTest extends BaseTestCase {
	public void testAssertPrivatePageHostURL() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("http://www.baker.com:8080/");

				boolean LoggedOut = selenium.isElementPresent("_58_login");

				if (!LoggedOut) {
					label = 2;

					continue;
				}

				selenium.type("//input[@id='_58_login']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.type("//input[@id='_58_password']",
					RuntimeVariables.replace("test"));
				selenium.clickAt("//input[@id='_58_rememberMeCheckbox']",
					RuntimeVariables.replace("Remember Me Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_58_rememberMeCheckbox']"));
				selenium.clickAt("//input[@value='Sign In']",
					RuntimeVariables.replace("Sign In"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace(
						"http://www.baker.com:8080/"), selenium.getLocation());
				assertEquals(RuntimeVariables.replace(
						"Virtual Hosting Community"),
					selenium.getText(
						"//span[@title='Go to Virtual Hosting Community']"));
				assertEquals(RuntimeVariables.replace(
						"Virtual Hosting Community"),
					selenium.getText(
						"//a[contains(text(),'Virtual Hosting Community')]"));
				selenium.clickAt("link=Private Page",
					RuntimeVariables.replace("Private Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.clickAt("link=Private Page",
					RuntimeVariables.replace("Private Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"http://www.baker.com:8080/private-page"),
					selenium.getLocation());

			case 100:
				label = -1;
			}
		}
	}
}