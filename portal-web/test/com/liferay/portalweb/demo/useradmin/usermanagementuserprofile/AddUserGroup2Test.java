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

package com.liferay.portalweb.demo.useradmin.usermanagementuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserGroup2Test extends BaseTestCase {
	public void testAddUserGroup2() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("link=Add"));
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='_127_name']");
		selenium.type("//input[@id='_127_name']",
			RuntimeVariables.replace("User Group 2"));
		selenium.type("//textarea[@id='_127_description']",
			RuntimeVariables.replace("This is a selenium user group."));
		assertTrue(selenium.isPartialText(
				"//select[@id='_127_publicLayoutSetPrototypeId']",
				"Community Site"));
		selenium.select("//select[@id='_127_publicLayoutSetPrototypeId']",
			RuntimeVariables.replace("Community Site"));
		selenium.waitForVisible(
			"//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
		selenium.clickAt("//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']",
			RuntimeVariables.replace("Enable propagation of changes"));
		assertFalse(selenium.isChecked(
				"//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("User Group 2"),
			selenium.getText("//tr[4]/td[2]/a"));
	}
}