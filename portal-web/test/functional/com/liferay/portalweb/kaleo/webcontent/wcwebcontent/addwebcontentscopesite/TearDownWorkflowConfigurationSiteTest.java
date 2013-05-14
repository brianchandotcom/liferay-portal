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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.addwebcontentscopesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWorkflowConfigurationSiteTest extends BaseTestCase {
	public void testTearDownWorkflowConfigurationSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//span[@title='Liferay']/ul/li/strong/a/span");
		selenium.clickAt("//span[@title='Liferay']/ul/li/strong/a/span",
			RuntimeVariables.replace("Scope Selector"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@title='Site Name']/ul/li/strong/a/span",
			"Site Name");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//span[@title='Site Name']/ul/li/strong/a/span"));
		selenium.clickAt("link=Workflow", RuntimeVariables.replace("Workflow"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Default Configuration");
		selenium.clickAt("link=Default Configuration",
			RuntimeVariables.replace("Default Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//select[@id='_151_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']");
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']",
			RuntimeVariables.replace("No Workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']",
			RuntimeVariables.replace("No Workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']",
			RuntimeVariables.replace("No Workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']",
			RuntimeVariables.replace("No Workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage']",
			RuntimeVariables.replace("No Workflow"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.clickAt("link=Workflow Configuration",
			RuntimeVariables.replace("Workflow Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']");
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']",
			RuntimeVariables.replace("Default: No Workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion']",
			RuntimeVariables.replace("Default: No Workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']",
			RuntimeVariables.replace("Default: No Workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']",
			RuntimeVariables.replace("Default: No Workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage']",
			RuntimeVariables.replace("Default: No Workflow"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}