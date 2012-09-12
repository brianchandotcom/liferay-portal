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

package com.liferay.portalweb.demo.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchDMMusicTest extends BaseTestCase {
	public void testSearchDMMusic() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("mp3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//div[@class='search-info']/span[@class='keywords']",
			"Searched for mp3 everywhere.");
		assertEquals(RuntimeVariables.replace("Searched for mp3 everywhere."),
			selenium.getText(
				"//div[@class='search-info']/span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DM Music Title"),
			selenium.getText(
				"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("music"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//div[@class='search-info']/span[@class='keywords']",
			"Searched for music everywhere.");
		assertEquals(RuntimeVariables.replace("Searched for music everywhere."),
			selenium.getText(
				"//div[@class='search-info']/span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DM Music Title"),
			selenium.getText(
				"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("\"DM Music Title\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//div[@class='search-info']/span[@class='keywords']",
			"Searched for \"DM Music Title\" everywhere.");
		assertEquals(RuntimeVariables.replace(
				"Searched for \"DM Music Title\" everywhere."),
			selenium.getText(
				"//div[@class='search-info']/span[@class='keywords']"));
		assertEquals(RuntimeVariables.replace("DM Music Title"),
			selenium.getText(
				"//div[@data-title='DM Music Title']/a/span[@class='entry-title']"));
	}
}