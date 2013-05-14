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

package com.liferay.portalweb.demo.fundamentals.wsrp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWSRPConsumerRemoteDPTest extends BaseTestCase {
	public void testAddWSRPConsumerRemoteDP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Consumers", RuntimeVariables.replace("Consumers"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("consumer demo"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Manage Portlets"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Portlet']",
			RuntimeVariables.replace("Add Portlet"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_1_WAR_wsrpportlet_name']",
			RuntimeVariables.replace("Remote Test Misc"));
		selenium.select("//select[@name='_1_WAR_wsrpportlet_portletHandle']",
			RuntimeVariables.replace("Test Misc"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Remote Test Misc"),
			selenium.getText("//tr[3]/td"));
		assertEquals(RuntimeVariables.replace("Test Misc"),
			selenium.getText("//tr[3]/td[2]"));
	}
}