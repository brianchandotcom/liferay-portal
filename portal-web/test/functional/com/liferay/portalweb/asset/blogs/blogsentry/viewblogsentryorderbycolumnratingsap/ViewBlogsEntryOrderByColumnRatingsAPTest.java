/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.asset.blogs.blogsentry.viewblogsentryorderbycolumnratingsap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryOrderByColumnRatingsAPTest extends BaseTestCase {
	public void testViewBlogsEntryOrderByColumnRatingsAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("xPath=(//h1[@class='header-title'])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='asset-content'])[1]",
				"Blogs Entry1 Content"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[contains(@id,'ratingStarContent')])[1]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText(
				"xPath=(//div[contains(@id,'ratingScoreContent')])[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[contains(@id,'ratingScoreContent')]/img[@class='rating-element rating-element-on'][5])[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("xPath=(//h1[@class='header-title'])[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='asset-content'])[2]",
				"Blogs Entry2 Content"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[contains(@id,'ratingStarContent')])[2]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText(
				"xPath=(//div[contains(@id,'ratingScoreContent')])[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[contains(@id,'ratingScoreContent')]/img[@class='rating-element rating-element-on'][4])[2]"));
	}
}