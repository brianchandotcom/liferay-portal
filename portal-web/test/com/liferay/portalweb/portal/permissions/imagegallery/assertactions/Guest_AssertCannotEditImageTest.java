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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertCannotEditImageTest extends BaseTestCase {
	public void testGuest_AssertCannotEditImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Permissions Test Page",
			RuntimeVariables.replace("Media Gallery Permissions Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image 2 Test"),
			selenium.getText("//a[@title='Permissions Image 2 Test - ']"));
		selenium.clickAt("//a[@title='Permissions Image 2 Test - ']",
			RuntimeVariables.replace("Permissions Image 2 Test"));
		selenium.waitForVisible("//div/div[2]/div[1]");
		assertTrue(selenium.isElementPresent("//img[@title='Download (30k)']"));
		assertTrue(selenium.isElementPresent("//img[@title='View']"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Edit']"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Permissions']"));
		assertTrue(selenium.isElementNotPresent(
				"//img[@title='Move to the Recycle Bin']"));
	}
}