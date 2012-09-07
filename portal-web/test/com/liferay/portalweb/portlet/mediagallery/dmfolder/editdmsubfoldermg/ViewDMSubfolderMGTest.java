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

package com.liferay.portalweb.portlet.mediagallery.dmfolder.editdmsubfoldermg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMSubfolderMGTest extends BaseTestCase {
	public void testViewDMSubfolderMG() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//ul[@class='top-links-navigation']/li[1]/span"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText("//ul[@class='top-links-navigation']/li[2]/span"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText("//ul[@class='top-links-navigation']/li[3]/span"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Subfolder Name Edit"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Subfolder Name Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Subfolder Name Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-asset-icon lfr-asset-date']", "Last Updated"));
		assertEquals(RuntimeVariables.replace("0 Subfolders"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-subfolders']"));
		assertEquals(RuntimeVariables.replace("0 Images"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-items last']"));
		assertEquals(RuntimeVariables.replace(
				"There are no media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-avatar']/img"));
		assertEquals(RuntimeVariables.replace("DM Subfolder Name Edit"),
			selenium.getText("//div[@class='lfr-asset-name']/h4"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[1]/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[2]/a[contains(.,'Move')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[3]/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[4]/a[contains(.,'Move to the Recycle Bin')]"));
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[5]/a[contains(.,'Add Subfolder')]"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[6]/a[contains(.,'Multiple Media')]"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[7]/a[contains(.,'Add Media')]"));
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[8]/a[contains(.,'Add Shortcut')]"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[9]/a[contains(.,'Access from Desktop')]"));
	}
}