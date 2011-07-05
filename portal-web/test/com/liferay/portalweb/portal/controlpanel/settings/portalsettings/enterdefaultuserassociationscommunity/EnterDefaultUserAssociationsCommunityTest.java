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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.enterdefaultuserassociationscommunity;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EnterDefaultUserAssociationsCommunityTest extends BaseTestCase {
	public void testEnterDefaultUserAssociationsCommunity()
		throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Portal Settings", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isPartialText("//a[@id='_130_usersLink']", "Users"));
		selenium.clickAt("//a[@id='_130_usersLink']",
			RuntimeVariables.replace("Users"));
		selenium.clickAt("link=Default User Associations",
			RuntimeVariables.replace(""));
		selenium.type("_130_settings--admin.default.group.names--",
			RuntimeVariables.replace("Guest"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//section/div/div/div/div[1]"));
		selenium.clickAt("link=Default User Associations",
			RuntimeVariables.replace(""));
		assertEquals("Guest",
			selenium.getValue("_130_settings--admin.default.group.names--"));
	}
}