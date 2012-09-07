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

package com.liferay.portalweb.portlet.recentbloggers.entry.viewusername;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUserNameTest extends BaseTestCase {
	public void testViewUserName() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Recent Bloggers Test Page");
		selenium.clickAt("link=Recent Bloggers Test Page",
			RuntimeVariables.replace("Recent Bloggers Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText("//th[1]"));
		assertEquals(RuntimeVariables.replace("Posts"),
			selenium.getText("//th[2]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText("//th[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[3]/a"));
		assertTrue(selenium.isElementNotPresent("//img[@class='avatar']"));
		assertFalse(selenium.isTextPresent("Posts:"));
		assertFalse(selenium.isTextPresent("Stars:"));
		assertFalse(selenium.isTextPresent("Date:"));
	}
}