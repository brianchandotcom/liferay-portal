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

package com.liferay.portalweb.demo.media.dmkaleo2workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderMusicSubfolderMusicTest extends BaseTestCase {
	public void testAddDMFolderMusicSubfolderMusic() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Folder')]");
		assertEquals(RuntimeVariables.replace("Folder"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Folder')]"));
		assertEquals(RuntimeVariables.replace("Shortcut"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Shortcut')]"));
		assertEquals(RuntimeVariables.replace("Repository"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Repository')]"));
		assertEquals(RuntimeVariables.replace("Multiple Documents"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Multiple Documents')]"));
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Document')]"));
		assertEquals(RuntimeVariables.replace("Contract"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Contract')]"));
		assertEquals(RuntimeVariables.replace("Marketing Banner"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Marketing Banner')]"));
		assertEquals(RuntimeVariables.replace("Online Training"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Online Training')]"));
		assertEquals(RuntimeVariables.replace("Sales Presentation"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Sales Presentation')]"));
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Music')]"));
		assertEquals(RuntimeVariables.replace("DM Music Folder"),
			selenium.getText(
				"//div[@data-title='DM Music Folder']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Music Folder']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Music Folder"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subfolder')]");
		assertEquals(RuntimeVariables.replace("Subfolder"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subfolder')]"));
		assertEquals(RuntimeVariables.replace("Shortcut"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Shortcut')]"));
		assertEquals(RuntimeVariables.replace("Multiple Documents"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Multiple Documents')]"));
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Document')]"));
		assertEquals(RuntimeVariables.replace("Contract"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Contract')]"));
		assertEquals(RuntimeVariables.replace("Marketing Banner"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Marketing Banner')]"));
		assertEquals(RuntimeVariables.replace("Online Training"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Online Training')]"));
		assertEquals(RuntimeVariables.replace("Sales Presentation"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Sales Presentation')]"));
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Music')]"));
		selenium.waitForVisible(
			"//div[@data-title='DM Music Workflow Subfolder']/a/span[@class='entry-title']");
		assertEquals(RuntimeVariables.replace("DM Music Workflow Subfolder"),
			selenium.getText(
				"//div[@data-title='DM Music Workflow Subfolder']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Music Workflow Subfolder']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Music Workflow Subfolder"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subfolder')]");
		assertEquals(RuntimeVariables.replace("Subfolder"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subfolder')]"));
		assertEquals(RuntimeVariables.replace("Shortcut"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Shortcut')]"));
		assertEquals(RuntimeVariables.replace("Multiple Documents"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Multiple Documents')]"));
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Music')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Document')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Contract')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Marketing Banner')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Online Training')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Sales Presentation')]"));
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Music')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Music')]"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadCommonFile("//input[@id='_20_file']",
			RuntimeVariables.replace("Document_1.mp3"));
		selenium.type("//input[contains(@name,'artist')]",
			RuntimeVariables.replace("John Piper"));
		selenium.clickAt("//input[@value='Submit for Publication']",
			RuntimeVariables.replace("Submit for Publication"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Document_1.mp3 (Pending)"),
			selenium.getText(
				"//div[@data-title='Document_1.mp3']/a/span[@class='entry-title']"));
	}
}