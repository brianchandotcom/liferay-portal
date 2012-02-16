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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.groups.usergroup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersUserGroupTest extends BaseTestCase {
	public void testAssignMembersUserGroup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.type("//input[@id='_127_name']",
					RuntimeVariables.replace("User Group Sample Test"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("User Group Sample Test"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//strong/span"));
				selenium.clickAt("//strong/span",
					RuntimeVariables.replace("Actions"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Assign Members")
												.equals(selenium.getText(
										"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[4]/a"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Available",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));

			case 2:
				selenium.type("//input[@name='_127_keywords']",
					RuntimeVariables.replace("Joe Bloggs"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//div[4]/table/tbody/tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("joebloggs"),
					selenium.getText("//div[4]/table/tbody/tr[3]/td[3]"));

				boolean userChecked = selenium.isChecked("_127_rowIds");

				if (userChecked) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked("//input[@name='_127_rowIds']"));
				selenium.clickAt("//input[@name='_127_rowIds']",
					RuntimeVariables.replace("Checkbox"));

			case 3:
				assertTrue(selenium.isChecked("//input[@name='_127_rowIds']"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked("//input[@name='_127_rowIds']"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//div[4]/table/tbody/tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("joebloggs"),
					selenium.getText("//div[4]/table/tbody/tr[3]/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}