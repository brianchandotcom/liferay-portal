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
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Blog Entry']",
					RuntimeVariables.replace("Add Blog Entry"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent(
					"//textarea[@id='_33_editor' and @style='display: none;']");

				boolean categorizationCollapsed = selenium.isElementPresent(
						"//div[@id='blogsEntryCategorizationPanel' and contains(@class,'lfr-collapsed')]");

				if (!categorizationCollapsed) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='blogsEntryCategorizationPanel']/div/div/span",
					RuntimeVariables.replace("Categorization"));
				selenium.waitForElementNotPresent(
					"//div[@id='blogsEntryCategorizationPanel' and contains(@class,'lfr-collapsed')]");

			case 2:
				assertTrue(selenium.isVisible("//input[@title='Add Tags']"));
				assertFalse(selenium.isTextPresent("Suggestions"));
				assertTrue(selenium.isElementNotPresent(
						"//button[@id='suggest']"));

			case 100:
				label = -1;
			}
		}
	}
}