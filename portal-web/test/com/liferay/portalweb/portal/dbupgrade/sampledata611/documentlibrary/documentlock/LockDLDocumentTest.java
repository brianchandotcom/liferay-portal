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

package com.liferay.portalweb.portal.dbupgrade.sampledata611.documentlibrary.documentlock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LockDLDocumentTest extends BaseTestCase {
	public void testLockDLDocument() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("web/document-library-document-lock-community/");
		selenium.clickAt("link=Document Lock Page",
			RuntimeVariables.replace("Document Lock Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test1 Folder1"),
			selenium.getText("//div/a/span[contains(.,'Test1 Folder1')]"));
		selenium.clickAt("//div/a/span[contains(.,'Test1 Folder1')]",
			RuntimeVariables.replace("Test1 Folder1"));
		selenium.waitForText("//div/a/span[contains(.,'Test1 Document1.txt')]",
			"Test1 Document1.txt");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//div/a/span[contains(.,'Test1 Document1.txt')]"));
		selenium.click(RuntimeVariables.replace(
				"//div/a/span[contains(.,'Test1 Document1.txt')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//button[contains(.,'Checkout')]");
		selenium.clickAt("//button[contains(.,'Checkout')]",
			RuntimeVariables.replace("Checkout"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"You now have a lock on this document. No one else can edit this document until you unlock it. This lock will automatically expire in 1 day."),
			selenium.getText(
				"//div[@class='portlet-msg-lock portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("This is Test1 Document1"),
			selenium.getText("//span[@class='document-description']"));
	}
}