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

package com.liferay.portalweb.asset.blogs.blogsentry.rateblogsentryap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateBlogsEntryAPTest extends BaseTestCase {
	public void testRateBlogsEntryAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"xPath=(//div[@class='rating-label-element'])[2]");
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertTrue(selenium.isVisible(
				"//div[@class='asset-ratings']/div/div/div/a[5]"));
		selenium.clickAt("//div[@class='asset-ratings']/div/div/div/a[5]",
			RuntimeVariables.replace("5 Stars"));
		selenium.waitForText("xPath=(//div[@class='rating-label-element'])[2]",
			"Average (1 Vote)");
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
	}
}