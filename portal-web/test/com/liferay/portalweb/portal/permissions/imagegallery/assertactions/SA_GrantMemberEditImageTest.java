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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_GrantMemberEditImageTest extends BaseTestCase {
	public void testSA_GrantMemberEditImage() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Media Gallery Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//div[@id='kldx_menu']/ul/li[3]/a"));
		selenium.clickAt("//div[@id='kldx_menu']/ul/li[3]/a",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//input[@name='10424_ACTION_ACCESS']"));
		selenium.clickAt("//input[@name='10424_ACTION_ACCESS']",
			RuntimeVariables.replace("Member Folder Access"));
		assertTrue(selenium.isChecked("//input[@name='10424_ACTION_ACCESS']"));
		assertFalse(selenium.isChecked(
				"//input[@name='10424_ACTION_ADD_DOCUMENT']"));
		selenium.clickAt("//input[@name='10424_ACTION_ADD_DOCUMENT']",
			RuntimeVariables.replace("Member Folder Add Document"));
		assertTrue(selenium.isChecked(
				"//input[@name='10424_ACTION_ADD_DOCUMENT']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked("//input[@name='10424_ACTION_ACCESS']"));
		assertTrue(selenium.isChecked(
				"//input[@name='10424_ACTION_ADD_DOCUMENT']"));
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"link=Media Gallery Permissions Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Subfolder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Subfolder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//div[@id='kldx_menu']/ul/li[3]/a"));
		selenium.clickAt("//div[@id='kldx_menu']/ul/li[3]/a",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//input[@name='10424_ACTION_ACCESS']"));
		selenium.clickAt("//input[@name='10424_ACTION_ACCESS']",
			RuntimeVariables.replace("Member Subfolder Access"));
		assertTrue(selenium.isChecked("//input[@name='10424_ACTION_ACCESS']"));
		assertFalse(selenium.isChecked(
				"//input[@name='10424_ACTION_ADD_DOCUMENT']"));
		selenium.clickAt("//input[@name='10424_ACTION_ADD_DOCUMENT']",
			RuntimeVariables.replace("Member Subfolder Add Document"));
		assertTrue(selenium.isChecked(
				"//input[@name='10424_ACTION_ADD_DOCUMENT']"));
		assertFalse(selenium.isChecked("//input[@name='10424_ACTION_UPDATE']"));
		selenium.clickAt("//input[@name='10424_ACTION_UPDATE']",
			RuntimeVariables.replace("Member Subfolder Update"));
		assertTrue(selenium.isChecked("//input[@name='10424_ACTION_UPDATE']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked("//input[@name='10424_ACTION_ACCESS']"));
		assertTrue(selenium.isChecked(
				"//input[@name='10424_ACTION_ADD_DOCUMENT']"));
		assertTrue(selenium.isChecked("//input[@name='10424_ACTION_UPDATE']"));
	}
}