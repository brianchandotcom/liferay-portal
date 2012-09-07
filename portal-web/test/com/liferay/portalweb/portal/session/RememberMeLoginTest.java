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

package com.liferay.portalweb.portal.session;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RememberMeLoginTest extends BaseTestCase {
	public void testRememberMeLogin() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.setTimeout("180000");
				selenium.open("/web/guest/home/");

				boolean NotSignedOut = selenium.isElementPresent(
						"link=Sign Out");

				if (!NotSignedOut) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace("link=Sign Out"));
				selenium.waitForPageToLoad("30000");

			case 2:
				selenium.type("_58_login",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.type("_58_password", RuntimeVariables.replace("test"));
				selenium.click("_58_rememberMeCheckbox");
				selenium.waitForElementPresent("//input[@value='Sign In']");
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Sign In']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("link=Session Expiration Test Page");
				selenium.click(RuntimeVariables.replace(
						"link=Session Expiration Test Page"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}