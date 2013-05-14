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

package com.liferay.portalweb.kaleo.assetpublisher.dmdocument.guestviewpendingdmfolderdocumentnoaptest;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditDMFolderWorkflowSingleApproverTest extends BaseTestCase {
	public void testEditDMFolderWorkflowSingleApprover()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.clickAt("//span[@class='entry-action overlay']/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Default Workflow for all Document Types"),
			selenium.getText("//label[@for='_20_workflowDefinition-1']"));
		selenium.select("//select[@id='_20_workflowDefinition-1']",
			RuntimeVariables.replace("Single Approver (Version 1)"));
		assertEquals("Single Approver (Version 1)",
			selenium.getSelectedLabel(
				"//select[@id='_20_workflowDefinition-1']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}