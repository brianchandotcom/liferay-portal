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

package com.liferay.portalweb.asset.blogs.blogsentry.selectexistingblogsentryapactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectExistingBlogsEntryAPActionsTest extends BaseTestCase {
	public void testSelectExistingBlogsEntryAPActions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertFalse(selenium.isTextPresent("Blogs Entry Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry Content"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.click(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//div[@class='select-asset-selector']/div/span/ul/li/strong/a");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='select-asset-selector']/div/span/ul/li/strong/a"));
		selenium.clickAt("//div[@class='select-asset-selector']/div/span/ul/li/strong/a",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Blogs Entry')]");
		assertEquals(RuntimeVariables.replace("Blogs Entry"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Blogs Entry')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Blogs Entry')]"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[contains(@id,'_86_selectAsset')]");
		selenium.selectFrame("//iframe[contains(@id,'_86_selectAsset')]");
		selenium.waitForVisible("//tr[contains(.,'Blogs Entry Title')]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//tr[contains(.,'Blogs Entry Title')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Blogs Entry Title')]/td[1]/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//tr[contains(.,'Blogs Entry Title')]/td[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry"),
			selenium.getText("//tr[contains(.,'Blogs Entry Title')]/td[2]"));
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='asset-summary']"));
	}
}