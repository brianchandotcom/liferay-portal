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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportExportLARPrivatePagesSiteBWCTest extends BaseTestCase {
	public void testImportExportLARPrivatePagesSiteBWC()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("LAR Import Site"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("LAR Import Site"),
			selenium.getText("//td/a"));
		selenium.clickAt("//td/a", RuntimeVariables.replace("LAR Import Site"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Site Pages",
			RuntimeVariables.replace("Site Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Private Pages",
			RuntimeVariables.replace("Private Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Import"),
			selenium.getText("//button[3]"));
		selenium.clickAt("//button[3]", RuntimeVariables.replace("Import"));
		selenium.waitForVisible("//input[@id='_156_importFileName']");
		selenium.uploadTempFile("//input[@id='_156_importFileName']",
			RuntimeVariables.replace("World_Cup_Private_Pages.lar"));
		assertFalse(selenium.isChecked(
				"//input[@id='_156_DELETE_MISSING_LAYOUTSCheckbox']"));
		selenium.clickAt("//input[@id='_156_DELETE_MISSING_LAYOUTSCheckbox']",
			RuntimeVariables.replace("Delete Missing Pages"));
		assertTrue(selenium.isChecked(
				"//input[@id='_156_DELETE_MISSING_LAYOUTSCheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_156_PERMISSIONSCheckbox']"));
		selenium.clickAt("//input[@id='_156_PERMISSIONSCheckbox']",
			RuntimeVariables.replace("Permissions"));
		assertTrue(selenium.isChecked("//input[@id='_156_PERMISSIONSCheckbox']"));
		assertFalse(selenium.isChecked("//input[@id='_156_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@id='_156_CATEGORIESCheckbox']",
			RuntimeVariables.replace("Categories"));
		assertTrue(selenium.isChecked("//input[@id='_156_CATEGORIESCheckbox']"));
		selenium.waitForVisible(
			"//input[@id='_156_DELETE_PORTLET_DATACheckbox']");
		assertFalse(selenium.isChecked(
				"//input[@id='_156_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_156_DELETE_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Delete portlet data before importing"));
		assertTrue(selenium.isChecked(
				"//input[@id='_156_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}