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

package com.liferay.portalweb.portal.controlpanel.admin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class BrowseServerPluginsInstallationTest extends BaseTestCase {
	public void testBrowseServerPluginsInstallation() throws Exception {
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
		selenium.clickAt("link=Plugins Installation",
			RuntimeVariables.replace("Plugins Installation"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Portlet Plugins",
			RuntimeVariables.replace("Portlet Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Portlet"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Active"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Search Index"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		selenium.clickAt("link=Theme Plugins",
			RuntimeVariables.replace("Theme Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Theme"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Active"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Roles"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		selenium.clickAt("link=Layout Template Plugins",
			RuntimeVariables.replace("Layout Template Plugins"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Layout Template"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Active"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Roles"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
	}
}