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

package com.liferay.portalweb.asset.blogs.blogsentry.deleteblogsentry2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteBlogsEntry2Test extends BaseTestCase {
	public void testDeleteBlogsEntry2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		selenium.clickAt("//h3[@class='asset-title']/a",
			RuntimeVariables.replace("Blogs Entry2 Title"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//h1[@class='header-title']"));
		selenium.click(RuntimeVariables.replace("link=Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("The asset could not be found."),
			selenium.getText("//div[@class='portlet-msg-error']"));
		assertTrue(selenium.isPartialText(
				"//div[@class='portlet-msg-success taglib-trash-undo']/form",
				"The selected item was moved to the Recycle Bin."));
		assertFalse(selenium.isTextPresent("Blogs Entry2 Title"));
	}
}