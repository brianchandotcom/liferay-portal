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

package com.liferay.portalweb.portal.controlpanel.settings.address.addsettingsaddresses;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSettingsAddress2Test extends BaseTestCase {
	public void testAddSettingsAddress2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portal Settings",
			RuntimeVariables.replace("Portal Settings"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_130_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_130_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//span/button[1]");
		selenium.clickAt("//span/button[1]", RuntimeVariables.replace("Add Row"));
		selenium.waitForVisible("//input[@id='_130_addressStreet1_2']");
		selenium.type("//input[@id='_130_addressStreet1_2']",
			RuntimeVariables.replace("123. Liferay Ln."));
		selenium.type("//input[@id='_130_addressCity2']",
			RuntimeVariables.replace("Rays of Light"));
		selenium.type("//input[@id='_130_addressZip2']",
			RuntimeVariables.replace("12345"));
		selenium.select("//select[@id='_130_addressTypeId2']",
			RuntimeVariables.replace("label=Billing"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForVisible("//input[@id='_130_addressStreet1_1']");
		assertEquals("123. Liferay Ln.",
			selenium.getValue("//input[@id='_130_addressStreet1_1']"));
		assertEquals("Rays of Light",
			selenium.getValue("//input[@id='_130_addressCity1']"));
		assertEquals("12345",
			selenium.getValue("//input[@id='_130_addressZip1']"));
		assertEquals("Billing",
			selenium.getSelectedLabel("//select[@id='_130_addressTypeId1']"));
	}
}