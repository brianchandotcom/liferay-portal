/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.asset.blogs.blogsentry.viewblogsentryscopecurrentpageap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryScopeCurrentPageAPTest extends BaseTestCase {
	public void testViewBlogsEntryScopeCurrentPageAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add New in Liferay"),
			selenium.getText(
				"//span[@title='Add New in Liferay']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Add New in Blogs Test Page"),
			selenium.getText(
				"//span[@title='Add New in Blogs Test Page']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText(
				"//div[@class='portlet-body']/div/h3[@class='asset-title']"));
		assertEquals(RuntimeVariables.replace("Edit Blogs Entry Title"),
			selenium.getText("//div[@class='portlet-body']/div/div[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='portlet-body']/div/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace(
				"Read More About Blogs Entry Title \u00bb"),
			selenium.getText("//div[@class='portlet-body']/div/div[2]/div[2]"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//tr[contains(.,'Blogs Test Page')]/td/span");
		assertEquals(RuntimeVariables.replace("Blogs Test Page"),
			selenium.getText("//tr[contains(.,'Blogs Test Page')]/td/span"));
		selenium.selectFrame("relative=top");
	}
}