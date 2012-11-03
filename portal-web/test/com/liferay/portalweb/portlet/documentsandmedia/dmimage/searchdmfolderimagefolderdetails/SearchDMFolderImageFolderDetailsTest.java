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

package com.liferay.portalweb.portlet.documentsandmedia.dmimage.searchdmfolderimagefolderdetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchDMFolderImageFolderDetailsTest extends BaseTestCase {
	public void testSearchDMFolderImageFolderDetails()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForText("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			"DM Folder Image Title");
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("DM Folder Image Title"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//span[@class='keywords']",
			"Searched for DM Folder Image Title in DM Folder Name");
		assertEquals(RuntimeVariables.replace(
				"Searched for DM Folder Image Title in DM Folder Name"),
			selenium.getText("//span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.click("//input[@value='Search Everywhere']");
		selenium.waitForText("//span[@class='keywords']",
			"Searched for DM Folder Image Title everywhere.");
		assertEquals(RuntimeVariables.replace(
				"Searched for DM Folder Image Title everywhere."),
			selenium.getText("//span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("DM1 Folder1 Image1 Title1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//span[@class='keywords']",
			"Searched for DM1 Folder1 Image1 Title1 in DM Folder Name");
		assertEquals(RuntimeVariables.replace(
				"Searched for DM1 Folder1 Image1 Title1 in DM Folder Name"),
			selenium.getText("//span[@class='keywords']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.click("//input[@value='Search Everywhere']");
		selenium.waitForText("//span[@class='keywords']",
			"Searched for DM1 Folder1 Image1 Title1 everywhere.");
		assertEquals(RuntimeVariables.replace(
				"Searched for DM1 Folder1 Image1 Title1 everywhere."),
			selenium.getText("//span[@class='keywords']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
	}
}