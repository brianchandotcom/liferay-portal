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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePage2PortletDocumentsPerPage1Test extends BaseTestCase {
	public void testViewConfigurePage2PortletDocumentsPerPage1()
		throws Exception {
		selenium.open("/web/document-library-page-scope-community/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=DL Page2 Name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace(
							"Documents and Media (DL Page2 Name)")
										.equals(selenium.getText(
								"//span[@class='portlet-title-text']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-first-link')])[2]"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-prev-link')])[2]"));
		assertTrue(selenium.isElementPresent("//a[@page='1']"));
		assertTrue(selenium.isElementPresent("//a[@page='2']"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-next-link')])[2]"));
		assertTrue(selenium.isElementPresent(
				"xPath=(//a[contains(@class,'aui-paginator-last-link')])[2]"));
		selenium.clickAt("//a[@page='2']", RuntimeVariables.replace("2"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder3 Name")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder2 Name"));
		selenium.clickAt("//a[@page='1']", RuntimeVariables.replace("1"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder2 Name")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-next-link')])[2]",
			RuntimeVariables.replace("Next"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder3 Name")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder2 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-prev-link')])[2]",
			RuntimeVariables.replace("Previous"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder2 Name")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-last-link')])[2]",
			RuntimeVariables.replace("Last"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder3 Name")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder2 Name"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-paginator-first-link')])[2]",
			RuntimeVariables.replace("First"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("DL Folder2 Name")
										.equals(selenium.getText(
								"//span[@class='document-title']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText("//span[@class='document-title']"));
		assertFalse(selenium.isTextPresent("DL Folder3 Name"));
	}
}