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

package com.liferay.portalweb.kaleo.assetpublisher.blogsentry.viewblogsentrycompleted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryCompletedGuestTest extends BaseTestCase {
	public void testViewBlogsEntryCompletedGuest() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//section"));
		assertEquals(RuntimeVariables.replace("Asset Publisher"),
			selenium.getText("//h1/span[contains(.,'Asset Publisher')]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText(
				"//div[@class='asset-abstract ']/h3/a[contains(.,'Blogs Entry Title')]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText(
				"//div[@class='asset-content']/div[contains(.,'Blogs Entry Content')]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='asset-content']/div[contains(.,'Read More')]",
				"Read More"));
		assertFalse(selenium.isTextPresent("There are no results."));
		selenium.clickAt("//div[@class='asset-more']/a[contains(.,'Read More')]",
			RuntimeVariables.replace("Read More"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[contains(.,'Blogs Entry Title')]/h1/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText(
				"//div[@class='asset-full-content show-asset-title']/div[contains(.,'Blogs Entry Content')]"));
	}
}