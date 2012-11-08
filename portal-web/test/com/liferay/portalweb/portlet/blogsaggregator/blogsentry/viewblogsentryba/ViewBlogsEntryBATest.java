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

package com.liferay.portalweb.portlet.blogsaggregator.blogsentry.viewblogsentryba;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryBATest extends BaseTestCase {
	public void testViewBlogsEntryBA() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Aggregator Test Page",
			RuntimeVariables.replace("Blogs Aggregator Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//span[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Description"),
			selenium.getText("//div[@class='abstract']"));
		assertEquals(RuntimeVariables.replace(
				"Read More About Blogs Entry Title \u00bb"),
			selenium.getText("//div[@class='comments']/a"));
		selenium.clickAt("//div[@class='comments']/a",
			RuntimeVariables.replace("Read More About Blogs Entry Title \u00bb"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='entry-body']/p"));
	}
}