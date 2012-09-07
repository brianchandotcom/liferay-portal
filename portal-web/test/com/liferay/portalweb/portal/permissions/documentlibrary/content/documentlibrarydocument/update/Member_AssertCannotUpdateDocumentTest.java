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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibrarydocument.update;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertCannotUpdateDocumentTest extends BaseTestCase {
	public void testMember_AssertCannotUpdateDocument()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Documents and Media",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertFalse(selenium.isVisible("link=Move"));
		assertFalse(selenium.isVisible("link=Checkout"));
		assertEquals(RuntimeVariables.replace("TestDocument.txt"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("TestDocument.txt"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText("//div[@id='_20_fileEntryToolbar']/span/button[1]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='_20_fileEntryToolbar']/span/button[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='_20_fileEntryToolbar']/span/button[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='_20_fileEntryToolbar']/span/button[4]"));
		assertFalse(selenium.isTextPresent("Edit"));
		assertFalse(selenium.isTextPresent("Move"));
		assertFalse(selenium.isTextPresent("Checkout"));
	}
}