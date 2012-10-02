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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.documentlibrary.documentlock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LockDLDocumentTest extends BaseTestCase {
	public void testLockDLDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-document-lock-community/");
		selenium.clickAt("link=Document Lock Page",
			RuntimeVariables.replace("Document Lock Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test1 Folder1"),
			selenium.getText("//a/strong[contains(.,'Test1 Folder1')]"));
		selenium.clickAt("//a/strong[contains(.,'Test1 Folder1')]",
			RuntimeVariables.replace("Test1 Folder1"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//td[1]/a", "Test1 Document1.txt"));
		assertTrue(selenium.isPartialText("//td[1]/a", "This is Test1 Document1"));
		assertFalse(selenium.isTextPresent("0.0k"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[5]/ul/li/strong/a"));
		selenium.clickAt("//td[5]/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Lock')]");
		assertEquals(RuntimeVariables.replace("Lock"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Lock')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Lock')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText("//td[1]/a", "Test1 Document1.txt"));
		assertTrue(selenium.isPartialText("//td[1]/a", "This is Test1 Document1"));
		assertFalse(selenium.isTextPresent("0.0k"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//td[4]/a"));
	}
}