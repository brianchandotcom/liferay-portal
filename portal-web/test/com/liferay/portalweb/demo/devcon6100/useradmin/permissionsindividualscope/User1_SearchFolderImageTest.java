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

package com.liferay.portalweb.demo.devcon6100.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User1_SearchFolderImageTest extends BaseTestCase {
	public void testUser1_SearchFolderImage() throws Exception {
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
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("dog"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder 1 Image 1 Title")
										.equals(selenium.getText(
								"//a[@class='document-link']/span[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder 1 Image 1 Title"),
			selenium.getText("//a[@class='document-link']/span[2]"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("cat"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder 2 Image 2 Title")
										.equals(selenium.getText(
								"//a[@class='document-link']/span[2]"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder 2 Image 2 Title"),
			selenium.getText("//a[@class='document-link']/span[2]"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("fish"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"No documents were found that matched the keywords: fish.")
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
				"No documents were found that matched the keywords: fish."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("frog"));
		selenium.clickAt("//input[@id='_20_search']",
			RuntimeVariables.replace("Search"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"No documents were found that matched the keywords: frog.")
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
				"No documents were found that matched the keywords: frog."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}