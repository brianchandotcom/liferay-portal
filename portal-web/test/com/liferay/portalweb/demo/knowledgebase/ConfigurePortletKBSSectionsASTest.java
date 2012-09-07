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
		selenium.waitForVisible("link=Knowledge Base Section Test Page");
		selenium.clickAt("link=Knowledge Base Section Test Page",
			RuntimeVariables.replace("Knowledge Base Section Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace(
				"General \n No articles were found."),
			selenium.getText("//section/div/div/div[@class='portlet-body']"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a/span",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible("//select[@id='_86_kbArticlesSections']");
		selenium.addSelection("//select[@id='_86_kbArticlesSections']",
			RuntimeVariables.replace("Application Server"));
		assertEquals("Application Server",
			selenium.getSelectedLabel("//select[@id='_86_kbArticlesSections']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForVisible("//div[@class='kb-articles-sections-title']");
		selenium.waitForVisible("//div[@class='kb-articles']/div/span/a/span");
		assertEquals(RuntimeVariables.replace("Application Server"),
			selenium.getText("//div[@class='kb-articles-sections-title']"));
		assertEquals(RuntimeVariables.replace("The third"),
			selenium.getText("//div[@class='kb-articles']/div/span/a/span"));
	}
}