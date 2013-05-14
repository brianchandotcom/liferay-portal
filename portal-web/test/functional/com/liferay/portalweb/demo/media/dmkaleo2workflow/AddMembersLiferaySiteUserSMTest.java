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

package com.liferay.portalweb.demo.media.dmkaleo2workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMembersLiferaySiteUserSMTest extends BaseTestCase {
	public void testAddMembersLiferaySiteUserSM() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.clickAt("//li[@id='_145_mySites']/a/span",
					RuntimeVariables.replace("Go to"));
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Liferay"),
					selenium.getText("//tr[4]/td/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[2]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[2]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]");
				assertEquals(RuntimeVariables.replace("Manage Memberships"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add Members"),
					selenium.getText(
						"//span[@title='Add Members']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Add Members']/ul/li/strong/a/span",
					RuntimeVariables.replace("Add Members"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]");
				assertEquals(RuntimeVariables.replace("User"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.waitForVisible("//input[@name='_174_keywords']");
				selenium.type("//input[@name='_174_keywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertFalse(selenium.isChecked("//tr[3]/td[1]/input"));
				selenium.clickAt("//tr[3]/td[1]/input",
					RuntimeVariables.replace("usersn"));
				assertTrue(selenium.isChecked("//tr[3]/td[1]/input"));
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("usersn"),
					selenium.getText("//tr[3]/td[3]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}