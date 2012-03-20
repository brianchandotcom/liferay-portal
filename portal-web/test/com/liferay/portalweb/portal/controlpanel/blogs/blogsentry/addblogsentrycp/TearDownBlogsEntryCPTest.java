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

package com.liferay.portalweb.portal.controlpanel.blogs.blogsentry.addblogsentrycp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownBlogsEntryCPTest extends BaseTestCase {
	public void testTearDownBlogsEntryCP() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Control Panel")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean entry1Present = selenium.isElementPresent(
						"//input[@name='_161_rowIds']");

				if (!entry1Present) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@name='_161_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

				boolean entry2Present = selenium.isElementPresent(
						"//input[@name='_161_rowIds']");

				if (!entry2Present) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@name='_161_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

				boolean entry3Present = selenium.isElementPresent(
						"//input[@name='_161_rowIds']");

				if (!entry3Present) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@name='_161_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

				boolean entry4Present = selenium.isElementPresent(
						"//input[@name='_161_rowIds']");

				if (!entry4Present) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@name='_161_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

				boolean entry5Present = selenium.isElementPresent(
						"//input[@name='_161_rowIds']");

				if (!entry5Present) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@name='_161_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_161_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}