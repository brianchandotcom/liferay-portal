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

package com.liferay.portalweb.portlet.documentsandmediadisplay.dmdocument.searchdmfolderdocumentdmd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchDMFolderDocumentDMDTest extends BaseTestCase {
	public void testSearchDMFolderDocumentDMD() throws Exception {
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
		selenium.type("//input[@title='Search Documents']",
			RuntimeVariables.replace("DL Folder Document Title"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Name")
										.equals(selenium.getText("//td[2]/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//td[3]/a"));
		selenium.type("//input[@title='Search Documents']",
			RuntimeVariables.replace("DM2 Folder2 Document2 Title2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"No documents were found that matched the keywords: DM2 Folder2 Document2 Title2.")
										.equals(selenium.getText(
								"//div[@class='portlet-msg-info']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"No documents were found that matched the keywords: DM2 Folder2 Document2 Title2."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("DL Folder Name"));
		assertFalse(selenium.isTextPresent("DL Folder Document Title"));
		selenium.type("//input[@title='Search Documents']",
			RuntimeVariables.replace("\"DM Folder Document Title\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DM Folder Name")
										.equals(selenium.getText("//td[2]/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("DM Folder Document Title"),
			selenium.getText("//td[3]/a"));
		selenium.type("//input[@title='Search Documents']",
			RuntimeVariables.replace("\"DM2 Folder2 Document2 Title2\""));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"No documents were found that matched the keywords: \"DM2 Folder2 Document2 Title2\".")
										.equals(selenium.getText(
								"//div[@class='portlet-msg-info']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"No documents were found that matched the keywords: \"DM2 Folder2 Document2 Title2\"."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("DL Folder Name"));
		assertFalse(selenium.isTextPresent("DL Folder Document Title"));
	}
}