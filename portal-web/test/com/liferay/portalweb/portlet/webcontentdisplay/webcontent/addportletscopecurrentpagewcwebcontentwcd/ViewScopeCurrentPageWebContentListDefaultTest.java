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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.addportletscopecurrentpagewcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewScopeCurrentPageWebContentListDefaultTest extends BaseTestCase {
	public void testViewScopeCurrentPageWebContentListDefault()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Web Content Display Test Page1");
		selenium.clickAt("link=Web Content Display Test Page1",
			RuntimeVariables.replace("Web Content Display Test Page1"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		Thread.sleep(5000);
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		selenium.waitForVisible("link=Setup");
		selenium.clickAt("link=Setup", RuntimeVariables.replace("Setup"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("//iframe");
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='portlet-msg-info'])[1]",
				"Please select a web content from the list below."));
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		selenium.selectFrame("relative=top");
		selenium.click("//button[@id='closethick']");
	}
}