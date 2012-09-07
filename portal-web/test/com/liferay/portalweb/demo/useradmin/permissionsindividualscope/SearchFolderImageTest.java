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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchFolderImageTest extends BaseTestCase {
	public void testSearchFolderImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("dog"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//a[@class='document-link']/span[2]",
			"DL Folder 1 Image 1 Title");
		assertEquals(RuntimeVariables.replace("DL Folder 1 Image 1 Title"),
			selenium.getText("//a[@class='document-link']/span[2]"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("cat"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//a[@class='document-link']/span[2]",
			"DL Folder 2 Image 2 Title");
		assertEquals(RuntimeVariables.replace("DL Folder 2 Image 2 Title"),
			selenium.getText("//a[@class='document-link']/span[2]"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("fish"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//a[@class='document-link']/span[2]",
			"DL Folder 2 Image 3 Title");
		assertEquals(RuntimeVariables.replace("DL Folder 2 Image 3 Title"),
			selenium.getText("//a[@class='document-link']/span[2]"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("frog"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//a[@class='document-link']/span[2]",
			"DL Folder 2 SubFolder Image 4 Title");
		assertEquals(RuntimeVariables.replace(
				"DL Folder 2 SubFolder Image 4 Title"),
			selenium.getText("//a[@class='document-link']/span[2]"));
	}
}