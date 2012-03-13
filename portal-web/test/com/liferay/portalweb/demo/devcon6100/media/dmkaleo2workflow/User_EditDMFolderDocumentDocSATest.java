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

package com.liferay.portalweb.demo.devcon6100.media.dmkaleo2workflow;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_EditDMFolderDocumentDocSATest extends BaseTestCase {
	public void testUser_EditDMFolderDocumentDocSA() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//span[@class='entry-title' and contains(.,'Mine')]"));
		selenium.clickAt("//span[@class='entry-title' and contains(.,'Mine')]",
			RuntimeVariables.replace("Mine"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//li[contains(@class,'folder') and contains(@class,'selected')]/a/span[contains(.,'Mine')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//li[contains(@class,'folder') and contains(@class,'selected')]/a/span[contains(.,'Mine')]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@data-title='DM Document Title']/a/span[@class='entry-title']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Document Title (Pending)"),
			selenium.getText(
				"//div[@data-title='DM Document Title']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Document Title']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Document Title (Pending)"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//button[.='Edit']"));
		selenium.clickAt("//button[.='Edit']", RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.uploadCommonFile("//input[@id='_20_file']",
			RuntimeVariables.replace("Document_2.doc"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DM Document Title Edit"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Document Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DM Document Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Last Updated by userfn userln"),
			selenium.getText("//div[contains(@class,'lfr-asset-author')]"));
		assertEquals(RuntimeVariables.replace("Status: Pending (Review)"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("DM Document Description"),
			selenium.getText("//blockquote[@class='lfr-asset-description']"));
		assertEquals(RuntimeVariables.replace("Download (22.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}