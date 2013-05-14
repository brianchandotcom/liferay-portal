/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.useradmin.usermanagementuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignUser1UserGroup1Test extends BaseTestCase {
	public void testAssignUser1UserGroup1() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_127_keywords']",
					RuntimeVariables.replace("User Group 1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("User Group 1"),
					selenium.getText("//td[2]/a"));
				selenium.waitForVisible(
					"//span[@title='Actions']/ul/li/strong/a");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[6]/a");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[6]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[6]/a",
					RuntimeVariables.replace("Assign Members"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Available"),
					selenium.getText("link=Available"));
				selenium.clickAt("link=Available",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isElementPresent(
						"link=Advanced \u00bb");

				if (advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Advanced \u00bb",
					RuntimeVariables.replace("Advanced"));

			case 2:
				selenium.type("//input[@id='_127_toggle_id_users_admin_user_searchkeywords']",
					RuntimeVariables.replace("selen01"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Submit"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("selenium01"),
					selenium.getText("//tr[3]/td[3]"));
				selenium.clickAt("//input[@name='_127_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Current"),
					selenium.getText("link=Current"));
				selenium.clickAt("link=Current",
					RuntimeVariables.replace("Current"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("selenium01"),
					selenium.getText("//tr[3]/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}