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

package com.liferay.portalweb.demo.media.dmkaleo1workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewDMFolderDocumentTxtTest extends BaseTestCase {
	public void testGuest_ViewDMFolderDocumentTxt() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText(
				"//div[@data-title='DM Folder Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Folder Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForVisible("//li[contains(@class,'folder selected')]/a");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Sort By"),
			selenium.getText("//span[@title='Sort By']/ul/li/strong/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Add']/ul/li/strong/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Manage']/ul/li/strong/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@data-title='DM Document Title']/a/span[@class='entry-title']"));
		assertEquals(RuntimeVariables.replace(
				"There are no documents or media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}