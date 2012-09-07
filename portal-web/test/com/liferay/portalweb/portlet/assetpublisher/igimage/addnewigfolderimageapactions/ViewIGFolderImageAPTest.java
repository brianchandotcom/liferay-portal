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

package com.liferay.portalweb.portlet.assetpublisher.igimage.addnewigfolderimageapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewIGFolderImageAPTest extends BaseTestCase {
	public void testViewIGFolderImageAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isElementPresent(
				"//div[@class='asset-resource-info']/div"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("IG Folder Name"),
			selenium.getText("//div[@class='document-container']/div/a/span[2]"));
		selenium.clickAt("//div[@class='document-container']/div/a/span[2]",
			RuntimeVariables.replace("IG Folder Name"));
		selenium.waitForText("//li[@class='folder selected']/a/span[2]",
			"IG Folder Name");
		assertEquals(RuntimeVariables.replace("IG Folder Name"),
			selenium.getText("//li[@class='folder selected']/a/span[2]"));
		assertTrue(selenium.isVisible("//span[@class='document-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("IG Folder Image Name"),
			selenium.getText("//a[@class='document-link']/span[2]"));
	}
}