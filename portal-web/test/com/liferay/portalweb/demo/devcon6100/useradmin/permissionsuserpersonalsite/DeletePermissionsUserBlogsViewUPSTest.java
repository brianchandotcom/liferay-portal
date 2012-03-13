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

package com.liferay.portalweb.demo.devcon6100.useradmin.permissionsuserpersonalsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeletePermissionsUserBlogsViewUPSTest extends BaseTestCase {
	public void testDeletePermissionsUserBlogsViewUPS()
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
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText("//tr[13]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[13]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[13]/td[4]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//tr[14]/td[1]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[14]/td[2]"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[14]/td[3]"));
		assertEquals(RuntimeVariables.replace("User Personal Site"),
			selenium.getText("//tr[14]/td[4]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//tr[14]/td[5]/span/a/span"));
		selenium.clickAt("//tr[14]/td[5]/span/a/span",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("The permission was deleted."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertNotEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[14]/td[3]"));
	}
}