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

package com.liferay.portalweb.plugins.wsrp.helloworld.manageportletconsumerhwutf8;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ManagePortletConsumerHWUTF8Test extends BaseTestCase {
	public void testManagePortletConsumerHWUTF8() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=WSRP", RuntimeVariables.replace("WSRP"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Consumers", RuntimeVariables.replace("Consumers"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Portlets')]");
		assertEquals(RuntimeVariables.replace("Manage Portlets"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Portlets')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Portlets')]"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Portlet']",
			RuntimeVariables.replace("Add Portlet"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_1_WAR_wsrpportlet_name']",
			RuntimeVariables.replace("WSRP \u4e16\u754c\u60a8\u597d Portlet"));
		selenium.select("//select[@name='_1_WAR_wsrpportlet_portletHandle']",
			RuntimeVariables.replace("label=Hello World"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"WSRP \u4e16\u754c\u60a8\u597d Portlet"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Hello World"),
			selenium.getText("//tr[3]/td[2]"));
	}
}