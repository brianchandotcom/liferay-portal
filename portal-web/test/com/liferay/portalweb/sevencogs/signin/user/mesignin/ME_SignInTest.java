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

package com.liferay.portalweb.sevencogs.signin.user.mesignin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ME_SignInTest extends BaseTestCase {
	public void testME_SignIn() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("xPath=(//a[@class='express_login'])[3]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Login as michelle"),
			selenium.getText("xPath=(//a[@class='express_login'])[3]"));
		selenium.clickAt("xPath=(//a[@class='express_login'])[3]",
			RuntimeVariables.replace("Login as michelle"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='password1']",
			RuntimeVariables.replace("password"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='password2']",
			RuntimeVariables.replace("password"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Michelle Writer"),
			selenium.getText("//a[contains(@class,'user-fullname')]"));
		assertEquals(RuntimeVariables.replace(
				"You are signed in as Michelle Writer."),
			selenium.getText("//section[@id='portlet_58']/div/div/div"));
	}
}