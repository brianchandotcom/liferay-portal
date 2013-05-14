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

package com.liferay.portalweb.plugins.testmisc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.BrowserCommands;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewActionResponseNormalStateMiscTest extends BaseTestCase {
	public void testViewActionResponseNormalStateMisc()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Test Misc Page",
			RuntimeVariables.replace("Test Misc Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ActionResponse, Normal State)"),
			selenium.getText("//h3[2]"));
		assertEquals(RuntimeVariables.replace("Download File"),
			selenium.getText("//p[2]/a"));
		selenium.clickAt("//p[2]/a", RuntimeVariables.replace("Download File"));
		BrowserCommands.downloadTempFile("logo.png");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Document')]");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Document')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Document')]"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadTempFile("//input[@id='_20_file']",
			RuntimeVariables.replace("logo.png"));
		Thread.sleep(5000);
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace(
				"Portlet Response (ActionResponse,Normal State)"));
		selenium.type("//textarea[@id='_20_description']",
			RuntimeVariables.replace(
				"Portlet Response (ActionResponse,Normal State)"));
		Thread.sleep(1000);
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ActionResponse,Normal State)"),
			selenium.getText(
				"link=Portlet Response (ActionResponse,Normal State)"));
		selenium.clickAt("link=Portlet Response (ActionResponse,Normal State)",
			RuntimeVariables.replace(
				"Portlet Response (ActionResponse,Normal State)"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ActionResponse,Normal State)"),
			selenium.getText("//h2[@class='document-title']"));
		selenium.waitForText("//span[@class='download-document']/span/a/span",
			"Download (2k)");
		assertEquals(RuntimeVariables.replace("Download (2k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}