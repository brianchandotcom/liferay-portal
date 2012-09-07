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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.portletpermissions.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletBlogsTest extends BaseTestCase {
	public void testAddPortletBlogs() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("link=Blogs Portlet Permissions Page");
		selenium.clickAt("link=Blogs Portlet Permissions Page",
			RuntimeVariables.replace("Blogs Portlet Permissions Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add Application",
			RuntimeVariables.replace("Add Application"));
		selenium.waitForElementPresent("//div[@id='Collaboration-Blogs']/p/a");
		selenium.clickAt("//div[@id='Collaboration-Blogs']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForElementPresent("//td[1]/div/div[1]/div");
		assertTrue(selenium.isElementPresent("//td[1]/div/div[1]/div"));
	}
}