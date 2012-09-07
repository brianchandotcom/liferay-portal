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

package com.liferay.portalweb.demo.useradmin.permissionsindividualscope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertNotViewableFolder2Test extends BaseTestCase {
	public void testGuest_AssertNotViewableFolder2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Documents and Media Test Page");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"xpath=(//a[contains(@class,'document-link')])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder 1 Name"),
			selenium.getText("xpath=(//a[contains(@class,'document-link')])[1]"));
		assertTrue(selenium.isElementNotPresent(
				"xpath=(//a[contains(@class,'document-link')])[2]"));
		assertTrue(selenium.isElementNotPresent(
				"xpath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertFalse(selenium.isTextPresent("DL Folder 2 Name"));
	}
}