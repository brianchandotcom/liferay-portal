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

package com.liferay.portalweb.demo.media.dmkaleo2workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditDMFolderMusicSubfolderDefaultWorkflowSingleApproverTest
	extends BaseTestCase {
	public void testEditDMFolderMusicSubfolderDefaultWorkflowSingleApprover()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Music Folder"),
			selenium.getText(
				"//div[@data-title='DM Music Folder']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Music Folder']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Music Folder"));
		selenium.waitForVisible(
			"//div[@data-title='DM Music Workflow Subfolder']/a/span[@class='entry-title']");
		assertEquals(RuntimeVariables.replace("DM Music Workflow Subfolder"),
			selenium.getText(
				"//div[@data-title='DM Music Workflow Subfolder']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Music Workflow Subfolder']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Music Workflow Subfolder"));
		Thread.sleep(5000);
		selenium.clickAt("//span[@class='overlay document-action']/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isVisible(
				"//select[@id='_20_workflowDefinition-1']"));
		selenium.clickAt("//input[@id='_20_overrideFileEntryTypes']",
			RuntimeVariables.replace("Workflow"));
		selenium.waitForVisible("//select[@id='_20_workflowDefinition-1']");
		assertEquals(RuntimeVariables.replace(
				"No workflow Single Approver (Version 1)"),
			selenium.getText("//select[@id='_20_workflowDefinition-1']"));
		selenium.select("//select[@id='_20_workflowDefinition-1']",
			RuntimeVariables.replace("Single Approver (Version 1)"));
		selenium.waitForVisible(
			"//span[@class='modify-link select-file-entry-type']/a/span");
		assertEquals(RuntimeVariables.replace("Select Document Type"),
			selenium.getText(
				"//span[@class='modify-link select-file-entry-type']/a/span"));
		selenium.clickAt("//span[@class='modify-link select-file-entry-type']/a/span",
			RuntimeVariables.replace("Select Document Type"));
		Thread.sleep(5000);
		selenium.waitForVisible("//iframe[@id='_20_fileEntryTypeSeclector']");
		selenium.selectFrame("//iframe[@id='_20_fileEntryTypeSeclector']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//td[@id='_20_dlFileEntryTypesSearchContainer_col-name_row-4']/a");
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText(
				"//td[@id='_20_dlFileEntryTypesSearchContainer_col-name_row-4']/a"));
		selenium.clickAt("//td[@id='_20_dlFileEntryTypesSearchContainer_col-name_row-4']/a",
			RuntimeVariables.replace("Music"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//div[@id='_20_dlFileEntryTypesSearchContainerSearchContainer']/table/tr/td");
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText(
				"//div[@id='_20_dlFileEntryTypesSearchContainerSearchContainer']/table/tr/td"));
		assertEquals(RuntimeVariables.replace(
				"No workflow Single Approver (Version 1)"),
			selenium.getText(
				"//table[@data-searchcontainerid='_20_dlFileEntryTypesSearchContainer']/tr/td[2]/span/span/span/select"));
		selenium.select("//table[@data-searchcontainerid='_20_dlFileEntryTypesSearchContainer']/tr/td[2]/span/span/span/select",
			RuntimeVariables.replace("Single Approver (Version 1)"));
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText("//select[@id='_20_defaultFileEntryTypeId']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^You are making changes to the document types. If a document in this folder or its subfolders is currently in draft or pending workflow states, you may lose some or all of its metadata. A new version will be created for all other documents. Are you sure you wish to continue[\\s\\S]$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		Thread.sleep(5000);
		selenium.clickAt("//span[@class='overlay document-action']/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Music"),
			selenium.getText("//select[@id='_20_defaultFileEntryTypeId']"));
		assertEquals("Single Approver (Version 1)",
			selenium.getSelectedLabel(
				"//table[@data-searchcontainerid='_20_dlFileEntryTypesSearchContainer']/tbody/tr/td[2]/span/span/span/select"));
	}
}