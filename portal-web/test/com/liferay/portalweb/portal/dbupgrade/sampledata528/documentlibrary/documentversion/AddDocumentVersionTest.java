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

package com.liferay.portalweb.portal.dbupgrade.sampledata528.documentlibrary.documentversion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDocumentVersionTest extends BaseTestCase {
	public void testAddDocumentVersion() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/home/");
		selenium.waitForElementPresent("link=Communities I Own");
		selenium.clickAt("link=Communities I Own", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_29_name",
			RuntimeVariables.replace(
				"Document Library Document Version Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Open", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("link=Document Library Page");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//b", RuntimeVariables.replace("Test1 Folder1"));
		selenium.waitForPageToLoad("30000");
		selenium.click("//strong/span");
		selenium.click(RuntimeVariables.replace("link=Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("_20_file");
		selenium.type("_20_file",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata527\\documentlibrary\\documentversion\\dependencies\\test_document2.txt"));
		selenium.type("_20_title", RuntimeVariables.replace("Test2 Document2"));
		selenium.type("_20_description",
			RuntimeVariables.replace("This is test2 document2."));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.selectWindow("null");
		assertEquals(RuntimeVariables.replace(
				"Test2 Document2.txt\nThis is test2 document2."),
			selenium.getText("//td[1]/a"));
		selenium.click("//strong/span");
		selenium.clickAt("link=View", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//tr[3]/td[2]/a", "1.1");
		selenium.waitForText("//tr[2]/td[2]", "1.1");
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("1.1"),
			selenium.getText("//tr[3]/td[2]/a"));
	}
}