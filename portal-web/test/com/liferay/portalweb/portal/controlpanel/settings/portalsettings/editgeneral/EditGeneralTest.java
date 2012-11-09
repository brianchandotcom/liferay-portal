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

package com.liferay.portalweb.portal.controlpanel.settings.portalsettings.editgeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditGeneralTest extends BaseTestCase {
	public void testEditGeneral() throws Exception {
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
		assertTrue(selenium.isPartialText("//a[@id='_130_generalLink']",
				"General"));
		selenium.clickAt("//a[@id='_130_generalLink']",
			RuntimeVariables.replace("General"));
		selenium.type("//input[@id='_130_legalId']",
			RuntimeVariables.replace("LIFE"));
		selenium.type("//input[@id='_130_sicCode']",
			RuntimeVariables.replace("1234"));
		selenium.type("//input[@id='_130_tickerSymbol']",
			RuntimeVariables.replace("LFRY"));
		selenium.type("//input[@id='_130_industry']",
			RuntimeVariables.replace("Web Portal"));
		selenium.type("//input[@id='_130_type']",
			RuntimeVariables.replace("Open Source"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("LIFE", selenium.getValue("//input[@id='_130_legalId']"));
		assertEquals("1234", selenium.getValue("//input[@id='_130_sicCode']"));
		assertEquals("LFRY",
			selenium.getValue("//input[@id='_130_tickerSymbol']"));
		assertEquals("Web Portal",
			selenium.getValue("//input[@id='_130_industry']"));
		assertEquals("Open Source",
			selenium.getValue("//input[@id='_130_type']"));
	}
}