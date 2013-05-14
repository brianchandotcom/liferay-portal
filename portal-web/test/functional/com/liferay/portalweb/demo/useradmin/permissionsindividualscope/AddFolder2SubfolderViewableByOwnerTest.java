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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolder2SubfolderViewableByOwnerTest extends BaseTestCase {
	public void testAddFolder2SubfolderViewableByOwner()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DL Folder 2 Name"),
			selenium.getText(
				"xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		selenium.clickAt("xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]",
			RuntimeVariables.replace("DL Folder 2 Name"));
		selenium.waitForText("//li[@class='folder selected']/a/span[2]",
			"DL Folder 2 Name");
		assertEquals(RuntimeVariables.replace("DL Folder 2 Name"),
			selenium.getText("//li[@class='folder selected']/a/span[2]"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Subfolder"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
			RuntimeVariables.replace("Subfolder"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("DL Folder 2 SubFolder Name"));
		selenium.type("//textarea[@id='_20_description']",
			RuntimeVariables.replace("DL Folder 2 SubFolder Description"));
		selenium.select("//select[@id='_20_inputPermissionsViewRole']",
			RuntimeVariables.replace("Owner"));
		assertEquals("Owner",
			selenium.getSelectedLabel(
				"//select[@id='_20_inputPermissionsViewRole']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DL Folder 2 SubFolder Name"),
			selenium.getText(
				"xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		selenium.clickAt("xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]",
			RuntimeVariables.replace("DL Folder 2 SubFolder Name"));
		selenium.waitForText("//li[@class='folder selected']/a/span[2]",
			"DL Folder 2 SubFolder Name");
		assertEquals(RuntimeVariables.replace("DL Folder 2 SubFolder Name"),
			selenium.getText("//li[@class='folder selected']/a/span[2]"));
	}
}