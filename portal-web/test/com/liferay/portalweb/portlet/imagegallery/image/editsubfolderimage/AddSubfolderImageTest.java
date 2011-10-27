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

package com.liferay.portalweb.portlet.imagegallery.image.editsubfolderimage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSubfolderImageTest extends BaseTestCase {
	public void testAddSubfolderImage() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Image Gallery Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Image Gallery Test Page",
			RuntimeVariables.replace("Image Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("MG Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Folder Name"),
			selenium.getText("//div/h1/span"));
		assertEquals(RuntimeVariables.replace("MG Subfolder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("MG Subfolder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Subfolder Name"),
			selenium.getText("//div/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"There are no media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[8]/a"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[8]/a",
			RuntimeVariables.replace("Add Media"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[8]/a"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[8]/a",
			RuntimeVariables.replace("Add Media"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Image")
										.equals(selenium.getText("//tr[4]/td/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText("//tr[4]/td/a"));
		Thread.sleep(5000);
		selenium.clickAt("//tr[4]/td/a", RuntimeVariables.replace("Image"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_31_file']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portlet\\imagegallery\\image\\editsubfolderimage\\dependencies\\test_image.jpg"));
		selenium.type("//input[@id='_31_title']",
			RuntimeVariables.replace("MG Subfolder Image Title"));
		selenium.type("//textarea[@id='_31_description']",
			RuntimeVariables.replace("MG Subfolder Image Description"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("MG Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("MG Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Subfolder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("MG Subfolder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MG Subfolder Image Title"),
			selenium.getText("//span[@class='image-title']"));
	}
}