/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.documentlibrary.image.editdlfolderimagedetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditDLFolderImageDetailsTest extends BaseTestCase {
	public void testEditDLFolderImageDetails() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Document Library Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Document Library Test Page",
			RuntimeVariables.replace("Document Library Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("DL Folder Name"),
			selenium.getText("//span[@class='document-title']"));
		selenium.clickAt("//span[@class='document-title']",
			RuntimeVariables.replace("DL Folder Name"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder Image Title")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("DL Folder Image Title"),
			selenium.getText("//span[@class='document-title']"));
		selenium.clickAt("//span[@class='document-title']",
			RuntimeVariables.replace("DL Folder Image Title"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//button[1]"));
		selenium.clickAt("//button[1]", RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("DL Folder Image Title Edit"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_20_description']",
			RuntimeVariables.replace("DL Folder Image Description Edit"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder Image Title Edit")
										.equals(selenium.getText(
								"//h2[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("DL Folder Image Title Edit"),
			selenium.getText("//h2[@class='document-title']"));
		assertTrue(selenium.isElementPresent(
				"//div[@class='lfr-preview-file-content lfr-preview-image-content']/img"));
		assertEquals(RuntimeVariables.replace(
				"DL Folder Image Description Edit"),
			selenium.getText("//span[@class='document-description']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Download (12.9k)"),
			selenium.getText("//span[@class='download-document']"));
		assertEquals(RuntimeVariables.replace("Content Type image/jpeg"),
			selenium.getText("//div[2]/div[2]/div/div[2]/div[2]/div"));
	}
}