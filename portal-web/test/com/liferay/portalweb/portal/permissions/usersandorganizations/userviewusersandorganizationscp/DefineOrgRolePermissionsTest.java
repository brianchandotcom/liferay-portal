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

package com.liferay.portalweb.portal.permissions.usersandorganizations.userviewusersandorganizationscp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineOrgRolePermissionsTest extends BaseTestCase {
	public void testDefineOrgRolePermissions() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
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
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//tr[10]/td[4]/span/ul/li/strong/a/span"));
		selenium.clickAt("//tr[10]/td[4]/span/ul/li/strong/a/img",
			RuntimeVariables.replace("Actions"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//a[@id='_128_ocerSearchContainer_8_menu_define_permissions']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//a[@id='_128_ocerSearchContainer_8_menu_define_permissions']",
			RuntimeVariables.replace("Define Permissions"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Organization Administration Announcements Asset Publisher Blogs Bookmarks Calendar Categories (Control Panel) Custom Fields Documents and Media Documents and Media Display Dynamic Data Lists Dynamic Data Mapping Media Gallery Message Boards Mobile Device Rules Page Templates Polls Shopping Site Templates Software Catalog Tags (Control Panel) Web Content Wiki Activities Alerts Amazon Rankings Announcements Asset Publisher Blogs Blogs Aggregator Bookmarks Breadcrumb Calendar Categories Navigation Currency Converter Dictionary Directory Documents and Media Documents and Media Display Dynamic Data List Display Group Statistics Hello Velocity Hello World IFrame Invitation Language Loan Calculator Media Gallery Message Boards My Sites Navigation Nested Portlets Network Utilities Page Comments Page Flags Page Ratings Password Generator Polls Display Quick Note RSS Recent Bloggers Recent Downloads Related Assets Requests Search Shopping Sign In Site Map Software Catalog Tag Cloud Tags Navigation Translator Unit Converter User Statistics Web Content Display Web Content List Web Content Search Web Proxy Wiki Wiki Display XSL Content Blogs (Control Panel) Bookmarks Calendar Categories (Control Panel) Documents and Media Dynamic Data Lists Message Boards (Control Panel) Mobile Device Rules (Control Panel) Polls Recent Content Recycle Bin Site Memberships Site Pages Site Settings Social Activity Software Catalog Tags (Control Panel) Web Content Wiki (Control Panel) Workflow Configuration"),
			selenium.getText("//select[@id='_128_add-permissions']"));
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Organization Administration"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Manage Users"),
			selenium.getText("//tr[8]/td[2]"));
		selenium.clickAt("//tr[8]/td/input",
			RuntimeVariables.replace("Manage Users"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Users and Organizations"),
			selenium.getText("//td/a"));
		assertEquals(RuntimeVariables.replace("Manage Users"),
			selenium.getText("//tr[3]/td[3]"));
	}
}