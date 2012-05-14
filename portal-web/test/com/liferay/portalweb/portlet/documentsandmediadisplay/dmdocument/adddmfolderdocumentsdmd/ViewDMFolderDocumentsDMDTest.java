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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.adddmfolderdocumentsdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderDocumentsDMDTest extends BaseTestCase {
	public void testViewDMFolderDocumentsDMD() throws Exception {
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
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText("//span[@class='entry-title']"));
		selenium.clickAt("//span[@class='entry-title']",
			RuntimeVariables.replace("DM Folder Document1 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document1 Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertTrue(selenium.isPartialText(
				"//span[@class='download-document']", "Download"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("link=\u00ab Back"));
		selenium.clickAt("link=\u00ab Back",
			RuntimeVariables.replace("\u00ab Back"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText("xPath=(//span[@class='entry-title'])[2]"));
		selenium.clickAt("xPath=(//span[@class='entry-title'])[2]",
			RuntimeVariables.replace("DM Folder Document2 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document2 Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertTrue(selenium.isPartialText(
				"//span[@class='download-document']", "Download"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("link=\u00ab Back"));
		selenium.clickAt("link=\u00ab Back",
			RuntimeVariables.replace("\u00ab Back"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText("xPath=(//span[@class='entry-title'])[3]"));
		selenium.clickAt("xPath=(//span[@class='entry-title'])[3]",
			RuntimeVariables.replace("DM Folder Document3 Title"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Title"),
			selenium.getText("//h2[@class='document-title']"));
		assertEquals(RuntimeVariables.replace("DM Folder Document3 Description"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertTrue(selenium.isPartialText(
				"//span[@class='download-document']", "Download"));
	}
}