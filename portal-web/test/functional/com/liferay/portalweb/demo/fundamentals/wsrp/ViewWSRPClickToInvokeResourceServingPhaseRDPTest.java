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
import com.liferay.portalweb.portal.util.BrowserCommands;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWSRPClickToInvokeResourceServingPhaseRDPTest
	extends BaseTestCase {
	public void testViewWSRPClickToInvokeResourceServingPhaseRDP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=WSRP Remote Test Misc Test Page");
		selenium.clickAt("link=WSRP Remote Test Misc Test Page",
			RuntimeVariables.replace("WSRP Remote Test Misc Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ResourceResponse)"),
			selenium.getText("//div[@class='portlet-body']/h3[4]"));
		assertEquals(RuntimeVariables.replace("Download File"),
			selenium.getText("//div[@class='portlet-body']/p[4]/a[2]"));
		selenium.clickAt("//div[@class='portlet-body']/p[4]/a[2]",
			RuntimeVariables.replace("Download File"));
		BrowserCommands.downloadTempFile("logo(1).png");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadTempFile("//input[@id='_20_file']",
			RuntimeVariables.replace("logo(1).png"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Your request completed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("logo(1).png"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("logo.png"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]",
			RuntimeVariables.replace("logo(1).png"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("logo(1).png"),
			selenium.getText("//h2[@class='document-title']"));
		selenium.waitForText("//span[@class='download-document']/span/a/span",
			"Download (2.0k)");
		assertEquals(RuntimeVariables.replace("Download (2.0k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}