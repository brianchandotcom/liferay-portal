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

package com.liferay.portalweb.permissions.blogs.blogsentry.deleteblogsentry.regroleinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteBlogsEntryTest extends BaseTestCase {
	public void testViewDeleteBlogsEntry() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@value='Add Blog Entry']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertTrue(selenium.isElementNotPresent("//div[@class='entry-title']"));
		assertTrue(selenium.isElementNotPresent("//div[@class='entry-body']/p"));
		assertTrue(selenium.isElementNotPresent("//div[@class='entry-author']"));
		assertTrue(selenium.isElementNotPresent("//span[@class='comments']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='taglib-flags']/span/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//li[@class='taglib-social-bookmark-twitter']"));
		assertTrue(selenium.isElementNotPresent(
				"//li[@class='taglib-social-bookmark-facebook']"));
		assertTrue(selenium.isElementNotPresent(
				"//li[@class='taglib-social-bookmark-plusone']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@id,'ratingStar')]/div"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@id,'ratingScore')]/div"));
		assertTrue(selenium.isPartialText(
				"//div[@class='subscribe']/span/a[contains(@href,'rss')]", "RSS"));
		assertTrue(selenium.isPartialText(
				"//div[@class='subscribe']/span/a[contains(@href,'subscribe')]",
				"Subscribe"));
		assertEquals(RuntimeVariables.replace("Showing 0 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}