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
public class User_ViewApproveDMFolderDocumentDocTest extends BaseTestCase {
	public void testUser_ViewApproveDMFolderDocumentDoc()
		throws Exception {
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
			selenium.getText(
				"//div[@data-title='DM Folder Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Folder Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Name")
										.equals(selenium.getText(
								"//li[contains(@class,'folder selected')]/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//li[contains(@class,'folder selected')]/a"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText(
				"//div[@data-title='DM Document Title Edit']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='DM Document Title Edit']/a/span[@class='entry-title']",
			RuntimeVariables.replace("DM Document Title Edit"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("DM Document Title Edit"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DM Document Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Version 1.0"),
			selenium.getText("//h3[contains(@class,'version')]"));
		assertEquals(RuntimeVariables.replace("Last Updated by userfn userln"),
			selenium.getText("//div[contains(@class,'lfr-asset-author')]"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("DM Document Description"),
			selenium.getText("//blockquote[@class='lfr-asset-description']"));
		assertEquals(RuntimeVariables.replace("Download (22.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
	}
}