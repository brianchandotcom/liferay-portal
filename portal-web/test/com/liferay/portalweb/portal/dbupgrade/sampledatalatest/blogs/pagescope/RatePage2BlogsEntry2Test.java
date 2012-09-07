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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RatePage2BlogsEntry2Test extends BaseTestCase {
	public void testRatePage2BlogsEntry2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page2");
		selenium.clickAt("link=Blogs Test Page2",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertTrue(selenium.isPartialText(
				"//div[@class='taglib-ratings stars']/div[2]/div/div", "0 Votes"));
		selenium.clickAt("//a[4]", RuntimeVariables.replace("4 Star"));
		selenium.waitForPartialText("//div[@class='taglib-ratings stars']/div[2]/div/div",
			"1 Vote");
		assertTrue(selenium.isPartialText(
				"//div[@class='taglib-ratings stars']/div[2]/div/div", "1 Vote"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[4]"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[4]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[5]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[5]"));
	}
}