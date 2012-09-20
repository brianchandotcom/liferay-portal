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

package com.liferay.portalweb.portlet.messageboards.mblar.importmblar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportMBLARTest extends BaseTestCase {
	public void testImportMBLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("//a[@title='Manage Site Content']");
		assertEquals(RuntimeVariables.replace("Site Content"),
			selenium.getText("//a[@title='Manage Site Content']"));
		selenium.clickAt("//a[@title='Manage Site Content']",
			RuntimeVariables.replace("Site Content"));
		selenium.waitForVisible("//iframe[@id='manageContentDialog']");
		selenium.selectFrame("//iframe[@id='manageContentDialog']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/transition/transition-min.js')]");
		selenium.waitForVisible("//ul[@class='category-portlets']/li[6]/a");
		assertEquals(RuntimeVariables.replace("Message Boards"),
			selenium.getText("//ul[@class='category-portlets']/li[6]/a"));
		selenium.clickAt("//ul[@class='category-portlets']/li[6]/a",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText(
				"//a[contains(@id,'_162_') and contains(@id,'menuButton')]"));
		selenium.clickAt("//a[contains(@id,'_162_') and contains(@id,'menuButton')]",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//li[@class='portlet-export-import portlet-export-import-icon last']/a");
		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//li[@class='portlet-export-import portlet-export-import-icon last']/a"));
		selenium.clickAt("//li[@class='portlet-export-import portlet-export-import-icon last']/a",
			RuntimeVariables.replace("Export / Import"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace("link=Import"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadFile("//input[@id='_86_importFileName']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\messageboards\\dependencies\\Message_Boards-Selenium.portlet.lar"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_86_DELETE_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Delete portlet data before importing."));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_86_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Data"));
		assertTrue(selenium.isChecked("//input[@id='_86_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}