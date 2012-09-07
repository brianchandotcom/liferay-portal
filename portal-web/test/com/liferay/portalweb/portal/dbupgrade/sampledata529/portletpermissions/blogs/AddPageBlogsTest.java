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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.portletpermissions.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageBlogsTest extends BaseTestCase {
	public void testAddPageBlogs() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("//div[@id='add-page']/a/span");
		selenium.clickAt("//div[@id='add-page']/a/span",
			RuntimeVariables.replace(""));
		selenium.type("new_page",
			RuntimeVariables.replace("Blogs Portlet Permissions Page"));
		selenium.clickAt("link=Save", RuntimeVariables.replace(""));
		selenium.waitForElementPresent("link=Blogs Portlet Permissions Page");
		selenium.clickAt("link=Blogs Portlet Permissions Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
	}
}