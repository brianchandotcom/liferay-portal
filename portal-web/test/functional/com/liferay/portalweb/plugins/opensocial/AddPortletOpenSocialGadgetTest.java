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

package com.liferay.portalweb.plugins.opensocial;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletOpenSocialGadgetTest extends BaseTestCase {
	public void testAddPortletOpenSocialGadget() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=OpenSocial Test Page",
			RuntimeVariables.replace("OpenSocial Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_addContent']/a/span");
		selenium.waitForVisible("//a[@id='_145_addApplication']");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-live-search/aui-live-search-min.js')]");
		selenium.waitForVisible("//input[@id='layout_configuration_content']");
		selenium.sendKeys("//input[@id='layout_configuration_content']",
			RuntimeVariables.replace("h"));
		selenium.waitForVisible("//li[@title='Hello World!']/p/a");
		selenium.clickAt("//li[@title='Hello World!']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		selenium.waitForVisible("//header/h1/span");
		assertEquals(RuntimeVariables.replace("Hello World!"),
			selenium.getText("//header/h1/span"));
		selenium.waitForVisible("//iframe[@class='aui-gadget']");
		selenium.selectFrame("//iframe[@class='aui-gadget']");
		selenium.waitForVisible("//body");
		assertEquals(RuntimeVariables.replace("Hello, world!"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
	}
}