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

package com.liferay.portalweb.properties.mailintegration.webcontent.wcwebcontent.gmailviewwcwebcontentaddedemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersSiteUserTest extends BaseTestCase {
	public void testAssignMembersSiteUser() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText("//tr[4]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xpath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
				selenium.clickAt("xpath=(//span[@title='Actions']/ul/li/strong/a/span)[2]",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Manage Memberships"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a",
					RuntimeVariables.replace("Manage Memberships"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Add Members")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Add Members",
					RuntimeVariables.replace("Add Members"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("User"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a",
					RuntimeVariables.replace("User"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@id='_174_toggle_id_users_admin_user_searchkeywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//tr[3]/td[3]"));
				assertFalse(selenium.isChecked("//input[@name='_174_rowIds']"));
				selenium.clickAt("//input[@name='_174_rowIds']",
					RuntimeVariables.replace("User Selected"));
				assertTrue(selenium.isChecked("//input[@name='_174_rowIds']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Site Memberships",
					RuntimeVariables.replace("Summary"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[4]/td[1]"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//tr[4]/td[2]"));

			case 100:
				label = -1;
			}
		}
	}
}