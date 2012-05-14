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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmfolderdocumentdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMFolderDocument3DMDTest extends BaseTestCase {
	public void testAddDMFolderDocument3DMD() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Documents and Media Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("link=DM Folder Name"));
		selenium.clickAt("link=DM Folder Name",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Add Document"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[7]/a"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[7]/a",
			RuntimeVariables.replace("Add Document"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Basic Document")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Basic Document",
			RuntimeVariables.replace("Basic Document"));
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);
		selenium.uploadCommonFile("//span[@class='aui-field aui-field-text']/span/span/input[contains(@id,'_file')]",
			RuntimeVariables.replace("Document_1.docx"));
		selenium.type("//input[contains(@id,'_title')]",
			RuntimeVariables.replace("DM Folder Document3 Title"));
		selenium.type("//textarea[contains(@id,'_description')]",
			RuntimeVariables.replace("DM Folder Document3 Description"));
		assertTrue(selenium.isVisible("//input[@value='Publish']"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText("xPath=(//span[@class='entry-title'])[3]"));
	}
}