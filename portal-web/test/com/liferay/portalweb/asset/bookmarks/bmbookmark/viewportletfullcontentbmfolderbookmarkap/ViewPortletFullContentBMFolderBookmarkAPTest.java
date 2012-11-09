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

package com.liferay.portalweb.asset.bookmarks.bmbookmark.viewportletfullcontentbmfolderbookmarkap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletFullContentBMFolderBookmarkAPTest extends BaseTestCase {
	public void testViewPortletFullContentBMFolderBookmarkAP()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("BM Folder Bookmark Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"BM Folder Bookmark Name (http://www.liferay.com)(Opens New Window)"),
			selenium.getText("//div[@class='asset-content']/a"));
		assertEquals(RuntimeVariables.replace("View in Context \u00bb"),
			selenium.getText("//div[@class='asset-more']/a"));
		selenium.clickAt("//div[@class='asset-more']/a",
			RuntimeVariables.replace("View in Context \u00bb"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=http://www.liferay.com",
			RuntimeVariables.replace("http://www.liferay.com"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//img[@alt='Liferay']"));
	}
}