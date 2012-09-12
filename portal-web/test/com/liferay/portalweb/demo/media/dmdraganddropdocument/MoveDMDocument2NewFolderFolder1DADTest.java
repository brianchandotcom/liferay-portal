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

package com.liferay.portalweb.demo.media.dmdraganddropdocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveDMDocument2NewFolderFolder1DADTest extends BaseTestCase {
	public void testMoveDMDocument2NewFolderFolder1DAD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("DM Document2 Title"),
			selenium.getText(
				"//div[@data-title='DM Document2 Title']/a/span[2]"));
		selenium.mouseDown("//div[@data-title='DM Document2 Title']/a/span[2]");
		selenium.waitForVisible(
			"//div[@class='yui3-dd-proxy active-area-proxy']");
		assertEquals(RuntimeVariables.replace("1 item is ready to be moved."),
			selenium.getText("//div[@class='yui3-dd-proxy active-area-proxy']"));
		Thread.sleep(5000);
		selenium.mouseMoveAt("//div[@data-title='DM Folder1 Name']/a/span/img",
			RuntimeVariables.replace("0,200"));
		selenium.waitForText("//div[@class='yui3-dd-proxy active-area-proxy']",
			"1 item is ready to be moved to \"DM Folder1 Name\".");
		assertEquals(RuntimeVariables.replace(
				"1 item is ready to be moved to \"DM Folder1 Name\"."),
			selenium.getText("//div[@class='yui3-dd-proxy active-area-proxy']"));
		selenium.mouseUp("//div[@data-title='DM Folder1 Name']/a/span/img");
		selenium.waitForVisible("//li[@class='move-file']");
		assertEquals(RuntimeVariables.replace("DM Document2 Title"),
			selenium.getText("//li[@class='move-file']"));
		assertEquals(RuntimeVariables.replace("DM Folder1 Name"),
			selenium.getText("//a[@id='_20_folderName']"));
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@data-title='DM Document2 Title']/a/span[2]"));
	}
}