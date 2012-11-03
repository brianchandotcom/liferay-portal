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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmsubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMSubfolderBreadcrumbTest extends BaseTestCase {
	public void testViewDMSubfolderBreadcrumb() throws Exception {
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
		selenium.waitForText("//li[contains(@class,'folder selected')]/a[2]",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a[2]"));
		assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Subfolder Name"));
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DM Subfolder Name");
		assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='document-library-breadcrumb']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
			selenium.getText(
				"//div[@class='document-library-breadcrumb']/ul/li[3]/span/a"));
	}
}