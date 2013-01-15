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

package com.liferay.portalweb.socialoffice.users.usergroups.assignrolesousergroup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewSODashboardTest extends BaseTestCase {
	public void testSOUs_ViewSODashboard() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.clickAt("//li[3]/a", RuntimeVariables.replace("Dashboard"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//a[@class='profile-name']"));
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//nav/ul/li/a/span"));
		assertEquals(RuntimeVariables.replace("Contacts Center"),
			selenium.getText("//nav/ul/li[2]/a/span"));
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//nav/ul/li[3]/a/span"));
		assertEquals(RuntimeVariables.replace("Messages"),
			selenium.getText("//nav/ul/li[4]/a/span"));
		assertEquals(RuntimeVariables.replace("My Documents"),
			selenium.getText("//nav/ul/li[5]"));
		assertEquals(RuntimeVariables.replace("Tasks"),
			selenium.getText("//li[6]/a/span"));
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("//li[7]/a/span"));
	}
}