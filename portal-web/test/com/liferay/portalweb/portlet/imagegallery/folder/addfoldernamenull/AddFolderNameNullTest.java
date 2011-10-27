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

package com.liferay.portalweb.portlet.imagegallery.folder.addfoldernamenull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFolderNameNullTest extends BaseTestCase {
	public void testAddFolderNameNull() throws Exception {
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
		assertTrue(selenium.isVisible(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[2]/a"));
		selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[2]/a",
			RuntimeVariables.replace("Add Folder"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_31_name']", RuntimeVariables.replace(""));
		selenium.type("//textarea[@id='_31_description']",
			RuntimeVariables.replace("MG Folder Description"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("This field is required."),
			selenium.getText(
				"xPath=(//div[@class='aui-form-validator-message required'])"));
	}
}