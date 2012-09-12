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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteAdmin_EditImageTest extends BaseTestCase {
	public void testSiteAdmin_EditImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2 Edited"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder 2 Edited - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder 2 Edited - ']",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2 Edited"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Subfolder 2 - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder 2 - ']",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image Test"),
			selenium.getText("//a[@title='Permissions Image Test - ']"));
		selenium.click("//a[@title='Permissions Image Test - ']");
		selenium.waitForVisible("//img[@title='Edit']");
		selenium.click("//img[@title='Edit']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/search_container.js')]");
		selenium.waitForVisible("//input[@id='_31_title']");
		selenium.type("//input[@id='_31_title']",
			RuntimeVariables.replace("Permissions Image Test Edited"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2 Edited"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder 2 Edited - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder 2 Edited - ']",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2 Edited"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Subfolder 2 - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder 2 - ']",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image Test Edited"),
			selenium.getText("//a[@title='Permissions Image Test Edited - ']"));
	}
}