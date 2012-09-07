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

package com.liferay.portalweb.portal.permissions.imagegallery.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_RemoveViewPortletPermissionsTest extends BaseTestCase {
	public void testSA_RemoveViewPortletPermissions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent(
			"link=Media Gallery Permissions Test Page");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//li[@id='_86_tabs1112101114109105115115105111110115TabsId']/span/a");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//li[@id='_86_tabs1112101114109105115115105111110115TabsId']/span/a"));
		selenium.clickAt("//li[@id='_86_tabs1112101114109105115115105111110115TabsId']/span/a",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForVisible("//tr[5]/td[2]/input");
		assertTrue(selenium.isChecked("//tr[5]/td[2]/input"));
		selenium.clickAt("//tr[5]/td[2]/input",
			RuntimeVariables.replace("Media Gallery View"));
		assertFalse(selenium.isChecked("//tr[5]/td[2]/input"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isChecked("//tr[5]/td[2]/input"));
	}
}