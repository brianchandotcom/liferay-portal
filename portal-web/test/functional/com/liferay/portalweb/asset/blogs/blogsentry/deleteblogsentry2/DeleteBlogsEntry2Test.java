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
			selenium.getText(
				"//h3[@class='asset-title']/a[contains(.,'Blogs Entry2 Title')]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText(
				"//div[@class='asset-content']/div[contains(.,'Blogs Entry2 Content')]"));
		selenium.clickAt("//h3[@class='asset-title']/a[contains(.,'Blogs Entry2 Title')]",
			RuntimeVariables.replace("Blogs Entry2 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertTrue(selenium.isPartialText("//div[@class='asset-content']",
				"Blogs Entry2 Content"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//a[@class=' taglib-icon']/span[contains(.,'Move to the Recycle Bin')]"));
		selenium.click(RuntimeVariables.replace(
				"//a[@class=' taglib-icon']/span[contains(.,'Move to the Recycle Bin')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Error"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("The asset could not be found."),
			selenium.getText("//div[@class='portlet-msg-error']"));
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']"));
		assertFalse(selenium.isTextPresent("Blogs Entry2 Title"));
		assertFalse(selenium.isTextPresent("Blogs Entry2 Content"));
	}
}