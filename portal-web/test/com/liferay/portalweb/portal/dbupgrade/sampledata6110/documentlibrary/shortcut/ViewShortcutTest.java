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

package com.liferay.portalweb.portal.dbupgrade.sampledata6110.documentlibrary.shortcut;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewShortcutTest extends BaseTestCase {
	public void testViewShortcut() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-shortcut-community/");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace("Document Library Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div/a/span[contains(.,'Test2 Folder2')]");
		assertEquals(RuntimeVariables.replace("Test2 Folder2"),
			selenium.getText("//div/a/span[contains(.,'Test2 Folder2')]"));
		selenium.clickAt("//div/a/span[contains(.,'Test2 Folder2')]",
			RuntimeVariables.replace("Test2 Folder2"));
		selenium.waitForText("//div/a/span[contains(.,'Test1 Document1.txt')]",
			"Test1 Document1.txt");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//div/a/span[contains(.,'Test1 Document1.txt')]"));
		assertTrue(selenium.isVisible(
				"//div[contains(.,'Test1 Document1.txt')]/a/span/img[@alt='Shortcut']"));
		selenium.clickAt("//div/a/span[contains(.,'Test1 Document1.txt')]",
			RuntimeVariables.replace("Test1 Document1.txt"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test1 Document1.txt"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("This is test1 document1."),
			selenium.getText("//span[@class='document-description']"));
	}
}