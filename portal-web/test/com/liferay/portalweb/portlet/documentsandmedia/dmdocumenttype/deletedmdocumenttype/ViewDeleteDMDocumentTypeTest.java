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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.deletedmdocumenttype;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteDMDocumentTypeTest extends BaseTestCase {
	public void testViewDeleteDMDocumentType() throws Exception {
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
		selenium.waitForVisible("//div[@class='lfr-component lfr-menu-list']");
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Document Type Name')]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Document Types"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a",
			RuntimeVariables.replace("Document Types"));
		selenium.waitForVisible("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.selectFrame("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.waitForVisible("//input[@title='Search Entries']");
		selenium.type("//input[@title='Search Entries']",
			RuntimeVariables.replace("Name"));
		selenium.clickAt("//input[@class='aui-button-input aui-button-input-submit']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("DM DocumentType Name"));
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText(
				"//div[@class='portlet-body']/div[@class='portlet-msg-info']"));
		selenium.selectFrame("relative=top");
	}
}