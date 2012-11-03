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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocument.lockdmfolderdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMLockFolderDocumentTest extends BaseTestCase {
	public void testViewDMLockFolderDocument() throws Exception {
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
		selenium.waitForText("//li[contains(@class,'folder selected')]/a",
			"DM Folder Name");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		assertTrue(selenium.isVisible("//img[@class='locked-icon']"));
		assertEquals(RuntimeVariables.replace(
				"DM Folder Document Title (Draft)"),
			selenium.getText(
				"//a[contains(@class,'entry-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'entry-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document Title (Draft)"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You now have a lock on this document. No one else can edit this document until you unlock it. This lock will automatically expire in 1 day."),
			selenium.getText(
				"//div[@class='portlet-msg-lock portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//button[2]"));
		selenium.clickAt("//button[2]", RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You now have a lock on this document. No one else can edit this document until you unlock it. This lock will automatically expire in 1 day."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}