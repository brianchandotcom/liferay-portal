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

package com.liferay.portalweb.portlet.blogs.portlet.configureportletremoveallpermissions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletRemoveAllPermissionTest extends BaseTestCase {
	public void testConfigurePortletRemoveAllPermission()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible("//iframe[@id='_33_configurationIframeDialog']");
		selenium.selectFrame("//iframe[@id='_33_configurationIframeDialog']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("link=Permissions");
		selenium.clickAt("link=Permissions",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isChecked("//td[4]/input"));
		selenium.clickAt("//td[4]/input", RuntimeVariables.replace("Guest View"));
		assertFalse(selenium.isChecked("//td[4]/input"));
		assertTrue(selenium.isChecked("//tr[4]/td[2]/input"));
		selenium.clickAt("//tr[4]/td[2]/input",
			RuntimeVariables.replace("Owner Add to Page"));
		assertFalse(selenium.isChecked("//tr[4]/td[2]/input"));
		assertTrue(selenium.isChecked("//tr[4]/td[3]/input"));
		selenium.clickAt("//tr[4]/td[3]/input",
			RuntimeVariables.replace("Owner Configuration"));
		assertFalse(selenium.isChecked("//tr[4]/td[3]/input"));
		assertTrue(selenium.isChecked("//tr[4]/td[4]/input"));
		selenium.clickAt("//tr[4]/td[4]/input",
			RuntimeVariables.replace("Owner View"));
		assertFalse(selenium.isChecked("//tr[4]/td[4]/input"));
		assertTrue(selenium.isChecked("//tr[4]/td[5]/input"));
		selenium.clickAt("//tr[4]/td[5]/input",
			RuntimeVariables.replace("Owner Permissions"));
		assertFalse(selenium.isChecked("//tr[4]/td[5]/input"));
		assertTrue(selenium.isChecked("//tr[7]/td[4]/input"));
		selenium.clickAt("//tr[7]/td[4]/input",
			RuntimeVariables.replace("Site Member View"));
		assertFalse(selenium.isChecked("//tr[7]/td[4]/input"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible("//iframe[@id='_33_configurationIframeDialog']");
		selenium.selectFrame("//iframe[@id='_33_configurationIframeDialog']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("link=Permissions");
		selenium.clickAt("link=Permissions",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//td[4]/input"));
		assertFalse(selenium.isChecked("//tr[4]/td[2]/input"));
		assertFalse(selenium.isChecked("//tr[4]/td[3]/input"));
		assertFalse(selenium.isChecked("//tr[4]/td[4]/input"));
		assertFalse(selenium.isChecked("//tr[4]/td[5]/input"));
		assertFalse(selenium.isChecked("//tr[7]/td[4]/input"));
		selenium.selectFrame("relative=top");
	}
}