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

package com.liferay.portalweb.portlet.directory.usergroup.viewusergroupuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUserGroupUserTest extends BaseTestCase {
	public void testViewUserGroupUser() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Directory Test Page",
			RuntimeVariables.replace("Directory Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=User Groups",
			RuntimeVariables.replace("User Groups"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User Group Name"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("User Group Description"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("View Users"),
			selenium.getText("//a[contains(@id,'_1_menu_view-users')]/span"));
		selenium.clickAt("//a[contains(@id,'_1_menu_view-users')]/span",
			RuntimeVariables.replace("View Users"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("userln"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[5]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("userfn"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn usermn userln"),
			selenium.getText("//div[@class='user-information']/div[1]/h2"));
		assertEquals(RuntimeVariables.replace("Email Address"),
			selenium.getText("//dl[@class='property-list']/dt[1]"));
		assertEquals(RuntimeVariables.replace("userea@liferay.com"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("Birthday"),
			selenium.getText("//dl[@class='property-list']/dt[2]"));
		assertEquals(RuntimeVariables.replace("4/10/86"),
			selenium.getText("//dl[@class='property-list']/dd[2]"));
		assertEquals(RuntimeVariables.replace("Gender"),
			selenium.getText("//dl[@class='property-list']/dt[3]"));
		assertEquals(RuntimeVariables.replace("Male"),
			selenium.getText("//dl[@class='property-list']/dd[3]"));
	}
}