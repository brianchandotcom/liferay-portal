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

package com.liferay.portalweb.demo.sitemanagement.staginglocalliveworkflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWorkflowConfigurationTest extends BaseTestCase {
	public void testTearDownWorkflowConfiguration() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//strong/a", RuntimeVariables.replace("liferay.com"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Community Site Test"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Workflow", RuntimeVariables.replace("Workflow"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Default Configuration",
			RuntimeVariables.replace("Default Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portal.model.LayoutRevision']",
			RuntimeVariables.replace("No workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portal.model.User']",
			RuntimeVariables.replace("No workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']",
			RuntimeVariables.replace("No workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']",
			RuntimeVariables.replace("No workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion']",
			RuntimeVariables.replace("No workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']",
			RuntimeVariables.replace("No workflow"));
		selenium.select("//select[@id='_151_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage']",
			RuntimeVariables.replace("No workflow"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portal.model.LayoutRevision']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portal.model.User']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']"));
		assertEquals("No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_151_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage']"));
		selenium.clickAt("link=Workflow Configuration",
			RuntimeVariables.replace("Workflow Configuration"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portal.model.LayoutRevision']",
			RuntimeVariables.replace("Default: No workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']",
			RuntimeVariables.replace("Default: No workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']",
			RuntimeVariables.replace("Default: No workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion']",
			RuntimeVariables.replace("Default: No workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']",
			RuntimeVariables.replace("Default: No workflow"));
		selenium.select("//select[@id='_152_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage']",
			RuntimeVariables.replace("Default: No workflow"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("Default: No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portal.model.LayoutRevision']"));
		assertEquals("Default: No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.blogs.model.BlogsEntry']"));
		assertEquals("Default: No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.journal.model.JournalArticle']"));
		assertEquals("Default: No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBDiscussion']"));
		assertEquals("Default: No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.messageboards.model.MBMessage']"));
		assertEquals("Default: No workflow",
			selenium.getSelectedLabel(
				"//select[@id='_152_workflowDefinitionName@com.liferay.portlet.wiki.model.WikiPage']"));
	}
}