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

package com.liferay.portalweb.portlet.pagecomments.portlet.editportletname;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPortletPCNameTest extends BaseTestCase {
	public void testEditPortletPCName() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Page Comments"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		selenium.clickAt("//h1[@class='portlet-title']/span[2]",
			RuntimeVariables.replace("Page Comments"));
		assertEquals(RuntimeVariables.replace("Page Comments"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		selenium.clickAt("//h1[@class='portlet-title']/span[2]",
			RuntimeVariables.replace("Page Comments"));
		selenium.waitForVisible("//div[5]/div/div/div/span[1]/span/input");
		selenium.type("//div[5]/div/div/div/span[1]/span/input",
			RuntimeVariables.replace("Page Comments Edit"));
		selenium.waitForVisible("//button[@id='save']");
		selenium.clickAt("//button[@id='save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//h1[@class='portlet-title']/span[2]",
			"Page Comments Edit");
		assertEquals(RuntimeVariables.replace("Page Comments Edit"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Page Comments Edit"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		selenium.clickAt("//h1[@class='portlet-title']/span[2]",
			RuntimeVariables.replace("Page Comments Edit"));
		assertEquals(RuntimeVariables.replace("Page Comments Edit"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		selenium.clickAt("//h1[@class='portlet-title']/span[2]",
			RuntimeVariables.replace("Page Comments Edit"));
		selenium.waitForVisible("//div[5]/div/div/div/span[1]/span/input");
		selenium.type("//div[5]/div/div/div/span[1]/span/input",
			RuntimeVariables.replace("Page Comments"));
		selenium.waitForVisible("//button[@id='save']");
		selenium.clickAt("//button[@id='save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//h1[@class='portlet-title']/span[2]",
			"Page Comments");
		assertEquals(RuntimeVariables.replace("Page Comments"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Page Comments"),
			selenium.getText("//h1[@class='portlet-title']/span[2]"));
	}
}