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

package com.liferay.portalweb.properties.blogs.entrycomments.viewblogsentrycommentsno;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryCommentsNoTest extends BaseTestCase {
	public void testViewBlogsEntryCommentsNo() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Blogs Test Page");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("0 Comments"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("0 Comments"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-panel-title']/span[contains(.,'Comments')]"));
		assertFalse(selenium.isTextPresent("No comments yet."));
		assertFalse(selenium.isTextPresent("Be the first."));
		assertFalse(selenium.isTextPresent("Subscribe to Comments"));
		assertTrue(selenium.isElementNotPresent(
				"//fieldset[contains(@class,'add-comment')]/div/a"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='subscribe-link']"));
	}
}