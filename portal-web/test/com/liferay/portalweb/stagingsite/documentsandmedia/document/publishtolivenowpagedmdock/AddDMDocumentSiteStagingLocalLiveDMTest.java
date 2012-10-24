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

package com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowpagedmdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentSiteStagingLocalLiveDMTest extends BaseTestCase {
	public void testAddDMDocumentSiteStagingLocalLiveDM()
		throws Exception {
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
		selenium.waitForVisible("link=Site Name (Staging)");
		selenium.clickAt("link=Site Name (Staging)",
			RuntimeVariables.replace("Site Name (Staging)"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'live-view')]"));
		assertFalse(selenium.isTextPresent(
				"The data of this portlet is not staged. Any data changes are immediately available to the Local Live site. The portlet's own workflow is still honored. Portlet setup is still managed from staging."));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='portlet-msg-alert']"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[3]/span/ul/li/strong/a"));
		selenium.clickAt("//span[3]/span/ul/li/strong/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Document')]");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Document')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Basic Document')]"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"The data of this portlet is not staged. Any data changes are immediately available to the Local Live site. The portlet's own workflow is still honored. Portlet setup is still managed from staging."));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='portlet-msg-alert']"));
		selenium.uploadCommonFile("//input[@id='_20_file']",
			RuntimeVariables.replace("Document_1.docx"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Document Title"));
		selenium.type("//textarea[@id='_20_description']",
			RuntimeVariables.replace("DM Document Description"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='portlet-msg-success']",
			"Your request completed successfully.");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText(
				"//a[@title='DM Document Title - DM Document Description']"));
		selenium.clickAt("//a[@title='DM Document Title - DM Document Description']",
			RuntimeVariables.replace("DM Document Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DM Document Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (12k)"),
			selenium.getText("//span[@class='download-document']"));
	}
}