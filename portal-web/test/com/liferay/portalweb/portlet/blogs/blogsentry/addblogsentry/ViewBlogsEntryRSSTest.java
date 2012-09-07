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

package com.liferay.portalweb.portlet.blogs.blogsentry.addblogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryRSSTest extends BaseTestCase {
	public void testViewBlogsEntryRSS() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Blogs Test Page");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("RSS (Opens New Window)"),
			selenium.getText("//div[@class='subscribe']/span[1]/a"));

		String rssURL = selenium.getAttribute(
				"//div[@class='subscribe']/span[1]/a@href");
		RuntimeVariables.setValue("rssURL", rssURL);
		selenium.open(RuntimeVariables.getValue("rssURL"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//x:h1[@id='feedTitleText']"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//x:h2[@id='feedSubtitleText']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//x:div[@id='feedContent']/x:div[1]/x:h3/x:a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//x:div[@id='feedContent']/x:div[1]/x:div/x:p"));
		selenium.clickAt("//x:div[@id='feedContent']/x:div[1]/x:h3/x:a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='entry-body']/p"));
	}
}