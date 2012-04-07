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

package com.liferay.portalweb.properties.blogs.tagsuggestions.viewblogstagsuggestionsno;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsTagSuggestionsNoTest extends BaseTestCase {
	public void testViewBlogsTagSuggestionsNo() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Blogs Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//input[@value='Add Blog Entry']",
					RuntimeVariables.replace("Add Blog Entry"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@id='blogsEntryCategorizationPanel']/div/div/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean tagsVisible = selenium.isVisible(
						"//input[@title='Add Tags']");

				if (tagsVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='blogsEntryCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@title='Add Tags']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
				assertEquals(RuntimeVariables.replace("Tags"),
					selenium.getText(
						"//div[4]/div[2]/fieldset/div/span/span/label"));
				assertTrue(selenium.isVisible("//input[@title='Add Tags']"));
				assertFalse(selenium.isTextPresent("Suggestions"));

			case 100:
				label = -1;
			}
		}
	}
}