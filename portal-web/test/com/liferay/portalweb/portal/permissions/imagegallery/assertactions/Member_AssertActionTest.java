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
public class Member_AssertActionTest extends BaseTestCase {
	public void testMember_AssertAction() throws Exception {
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

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
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementNotPresent("link=Look and Feel"));
		assertTrue(selenium.isElementNotPresent("link=Configuration"));
		assertTrue(selenium.isElementNotPresent("link=Export / Import"));
		assertTrue(selenium.isElementNotPresent("//img[@alt='Remove']"));
		assertTrue(selenium.isElementNotPresent("link=Add Subfolder"));
		assertTrue(selenium.isElementNotPresent("link=Permissions"));
		assertTrue(selenium.isElementNotPresent("link=Add Folder"));
		assertTrue(selenium.isElementNotPresent("link=Add Repository"));
		assertTrue(selenium.isElementNotPresent("link=Add Shortcut"));
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Folder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Folder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder"),
			selenium.getText(
				"//a[@title='Media Gallery Permissions Test Subfolder - ']"));
		selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder - ']",
			RuntimeVariables.replace("Media Gallery Permissions Test Subfolder"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementNotPresent("link=Add Subfolder"));
		assertTrue(selenium.isElementPresent("link=Add Media"));
		selenium.open("/web/guest/home/");
		loadRequiredJavaScriptModules();

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
		loadRequiredJavaScriptModules();
		selenium.clickAt("link=Mine", RuntimeVariables.replace("Mine"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isTextPresent("Permissions Image 3 Test Edited"));
		selenium.clickAt("link=Recent", RuntimeVariables.replace("Recent"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent(
							"//img[@alt='Permissions Image 2 Test - ']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isTextPresent("Permissions Image 2 Test"));
		assertTrue(selenium.isTextPresent("Permissions Image 3 Test Edited"));
	}
}