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

package com.liferay.portalweb.portlet.sitemap.portlet.viewportletsitemap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletSMTest extends BaseTestCase {
	public void testViewPortletSM() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Site Map Test Page",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("xPath=(//div[@class='portlet-body']/ul/li/a)[1]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[1]",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Sign In"),
			selenium.getText("xPath=(//h1[@class='portlet-title'])[1]"));
		assertTrue(selenium.isVisible("xPath=(//section[@class='portlet'])[1]"));
		assertEquals(RuntimeVariables.replace("Hello World"),
			selenium.getText("xPath=(//h1[@class='portlet-title'])[2]"));
		assertTrue(selenium.isVisible("xPath=(//section[@class='portlet'])[2]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Site Map Test Page",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Map Test Page"),
			selenium.getText("xPath=(//div[@class='portlet-body']/ul/li/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='portlet-body']/ul/li/a)[2]",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Map"),
			selenium.getText("//h1[@class='portlet-title']"));
		assertTrue(selenium.isVisible("//section[@class='portlet']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Site Map Test Page",
			RuntimeVariables.replace("Site Map Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Map Test Child Page"),
			selenium.getText("//div[@class='portlet-body']/ul/li/ul/li/a"));
		selenium.clickAt("//div[@class='portlet-body']/ul/li/ul/li/a",
			RuntimeVariables.replace("Site Map Test Child Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//section[@class='portlet']"));
	}
}