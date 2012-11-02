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

package com.liferay.portalweb.asset.documentsandmedia.dmimage.addnewdmfolderimageapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderImageAPTest extends BaseTestCase {
	public void testViewDMFolderImageAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='asset-resource-info']/div/img"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForVisible("//button[@title='Icon View']");
		selenium.clickAt("//button[@title='Icon View']",
			RuntimeVariables.replace("Icon View"));
		Thread.sleep(5000);
		selenium.waitForVisible(
			"//button[contains(@class,'aui-state-active') and @title='Icon View']");
		assertTrue(selenium.isVisible(
				"//button[contains(@class,'aui-state-active') and @title='Icon View']"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//a[contains(@class,'entry-link')]/span"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//li[@class='app-view-navigation-entry folder selected']/a/span[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//li[@class='app-view-navigation-entry folder selected']/a/span[2]"));
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//a[contains(@class,'entry-link')]/span"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span",
			RuntimeVariables.replace("DM Folder Image Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[@class='version ']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (13k)"),
			selenium.getText("//span[1]/span/a/span"));
		assertTrue(selenium.isPartialText(
				"//div[2]/div[2]/div/div[1]/div[2]/div[1]", "image/jpeg"));
		assertTrue(selenium.isPartialText("//div[2]/div/div[1]/div[2]/div[2]",
				"8"));
		assertTrue(selenium.isPartialText("//div[2]/div/div/div[2]/div[3]", "92"));
		assertTrue(selenium.isPartialText("//div[2]/div[4]", "394"));
	}
}