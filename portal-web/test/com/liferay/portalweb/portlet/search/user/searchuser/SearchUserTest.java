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

package com.liferay.portalweb.portlet.search.user.searchuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUserTest extends BaseTestCase {
	public void testSearchUser() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Search Test Page");
		selenium.clickAt("link=Search Test Page",
			RuntimeVariables.replace("Search Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@name='_3_keywords']");
		selenium.type("//input[@name='_3_keywords']",
			RuntimeVariables.replace("selenium*"));
		selenium.clickAt("//input[@type='image']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("selen01 lenn nium01"),
			selenium.getText("//span[@class='asset-entry-title']/a"));
	}
}