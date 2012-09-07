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

package com.liferay.portalweb.socialofficehome.sites.site.viewsitesdirectory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSitesDirectoryTest extends BaseTestCase {
	public void testViewSitesDirectory() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		selenium.waitForVisible(
			"//button[contains(.,'Sites Directory')]/span[2]");
		assertEquals(RuntimeVariables.replace("Sites Directory"),
			selenium.getText("//button[contains(.,'Sites Directory')]/span[2]"));
		selenium.clickAt("//button[contains(.,'Sites Directory')]/span[2]",
			RuntimeVariables.replace("Sites Directory"));
		selenium.waitForVisible("xPath=(//h1[@class='header-title']/span)[1]");
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		selenium.select("//span[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("All Sites"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site1 Name"),
			selenium.getText(
				"xPath=(//span[@class='name']/a)[contains(.,'Open Site1 Name')]"));
		assertEquals(RuntimeVariables.replace("Open Site1 Description"),
			selenium.getText(
				"xPath=(//span[@class='description'])[contains(.,'Open Site1 Description')]"));
		assertEquals(RuntimeVariables.replace("Private Restricted Site2 Name"),
			selenium.getText(
				"xPath=(//span[@class='name']/a)[contains(.,'Private Restricted Site2 Name')]"));
		assertEquals(RuntimeVariables.replace(
				"Private Restricted Site2 Description"),
			selenium.getText(
				"xPath=(//span[@class='description'])[contains(.,'Private Restricted Site2 Description')]"));
		assertFalse(selenium.isTextPresent(
				"xPath=(//span[@class='name']/a)[contains(.,'Private Site3 Name')]"));
		assertFalse(selenium.isTextPresent(
				"xPath=(//span[@class='description'])[contains(.,'Private Site3 Description')]"));
		assertEquals(RuntimeVariables.replace("Public Restricted Site4 Name"),
			selenium.getText(
				"xPath=(//span[@class='name']/a)[contains(.,'Public Restricted Site4 Name')]"));
		assertEquals(RuntimeVariables.replace(
				"Public Restricted Site4 Description"),
			selenium.getText(
				"xPath=(//span[@class='description'])[contains(.,'Public Restricted Site4 Description')]"));
		assertEquals(RuntimeVariables.replace("All Sites My Sites My Favorites"),
			selenium.getText("//select[@id='_5_WAR_soportlet_tabs1']"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("My Sites"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Private Site3 Name"),
			selenium.getText(
				"xPath=(//span[@class='name']/a)[contains(.,'Private Site3 Name')]"));
		assertEquals(RuntimeVariables.replace("Private Site3 Description"),
			selenium.getText(
				"xPath=(//span[@class='description'])[contains(.,'Private Site3 Description')]"));
	}
}