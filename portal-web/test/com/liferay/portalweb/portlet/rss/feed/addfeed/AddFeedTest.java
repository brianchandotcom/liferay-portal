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

package com.liferay.portalweb.portlet.rss.feed.addfeed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFeedTest extends BaseTestCase {
	public void testAddFeed() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=RSS Test Page");
		selenium.clickAt("link=RSS Test Page",
			RuntimeVariables.replace("RSS Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForVisible("//strong/a");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForElementPresent(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[3]/a");
		selenium.waitForVisible("//div[2]/span/span/button[1]");
		selenium.click("//div[2]/span/span/button[1]");
		selenium.waitForVisible("//input[@id='_86_url3']");
		selenium.type("//input[@id='_86_url3']",
			RuntimeVariables.replace("http://feeds.digg.com/digg/popular.rss"));
		selenium.select("//select[@id='_86_entriesPerFeed']",
			RuntimeVariables.replace("4"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=RSS Test Page");
		selenium.clickAt("link=RSS Test Page",
			RuntimeVariables.replace("RSS Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@href='http://feeds.digg.com']");
		assertEquals(RuntimeVariables.replace("Stories(Opens New Window)"),
			selenium.getText("//a[@href='http://feeds.digg.com']"));
	}
}