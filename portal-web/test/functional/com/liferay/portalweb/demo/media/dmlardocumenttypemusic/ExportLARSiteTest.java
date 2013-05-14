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

package com.liferay.portalweb.demo.media.dmlardocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.BrowserCommands;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ExportLARSiteTest extends BaseTestCase {
	public void testExportLARSite() throws Exception {
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
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Site Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]");
		assertEquals(RuntimeVariables.replace("Manage Pages"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Export"),
			selenium.getText("//button[.='Export']"));
		selenium.clickAt("//button[.='Export']",
			RuntimeVariables.replace("Export"));
		Thread.sleep(5000);
		selenium.waitForVisible("//iframe[@id='_156_exportDialog']");
		selenium.selectFrame("//iframe[@id='_156_exportDialog']");
		selenium.waitForVisible("//input[@id='_156_exportFileName']");
		selenium.type("//input[@id='_156_exportFileName']",
			RuntimeVariables.replace("DocumentsAndMedia_DEMO.lar"));
		selenium.clickAt("//input[@value='Export']",
			RuntimeVariables.replace("Export"));
		BrowserCommands.downloadTempFile("DocumentsAndMedia_DEMO.lar");
		Thread.sleep(5000);
		selenium.selectFrame("relative=top");
	}
}