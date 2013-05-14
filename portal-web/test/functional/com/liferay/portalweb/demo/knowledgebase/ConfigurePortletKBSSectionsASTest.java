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

package com.liferay.portalweb.demo.knowledgebase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletKBSSectionsASTest extends BaseTestCase {
	public void testConfigurePortletKBSSectionsAS() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Knowledge Base Section Test Page",
			RuntimeVariables.replace("Knowledge Base Section Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//section/div/div/div[@class='portlet-body']", "General"));
		assertTrue(selenium.isPartialText(
				"//section/div/div/div[@class='portlet-body']",
				"No articles were found."));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a/span",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//select[@id='_86_kbArticlesSections']");
		selenium.select("//select[@id='_86_kbArticlesSections']",
			RuntimeVariables.replace("Application Server"));
		assertEquals("Application Server",
			selenium.getSelectedLabel("//select[@id='_86_kbArticlesSections']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//div[@class='kb-articles-sections-title']");
		assertEquals(RuntimeVariables.replace("Application Server"),
			selenium.getText("//div[@class='kb-articles-sections-title']"));
		assertEquals(RuntimeVariables.replace("The third"),
			selenium.getText("//div[@class='kb-articles']/div/span/a/span"));
	}
}