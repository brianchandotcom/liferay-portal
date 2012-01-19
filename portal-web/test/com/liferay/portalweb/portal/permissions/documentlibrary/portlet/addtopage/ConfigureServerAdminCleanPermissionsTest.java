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

package com.liferay.portalweb.portal.permissions.documentlibrary.portlet.addtopage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureServerAdminCleanPermissionsTest extends BaseTestCase {
	public void testConfigureServerAdminCleanPermissions()
		throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Server Administration",
			RuntimeVariables.replace("Server Administration"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Clean Up Permissions This process removes the assignment of some permissions on the Guest, User and Power User roles in order to simplify the management of \"User Customizable Pages\". Notably, \"Add To Page\" permissions is removed from the Guest, and User role for all portlets. Likewise the same permission is reduced in scope for Power Users from portal wide to scoped to \"User Personal Site\"."),
			selenium.getText("//tr[9]/td"));
		selenium.clickAt("xpath=(//input[@value='Execute'])[9]",
			RuntimeVariables.replace("Clean Up Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}