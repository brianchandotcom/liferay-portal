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

package com.liferay.portalweb.permissions.blogs.blogsentry.addblogsentry.siteroleinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineSiteRoleInlineBlogsAddEntryTest extends BaseTestCase {
	public void testDefineSiteRoleInlineBlogsAddEntry()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/site-name/");
				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Permissions']",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean blogsAddEntryChecked = selenium.isChecked(
						"//input[@id='roles-siterole-name_ACTION_ADD_ENTRY']");

				if (blogsAddEntryChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='roles-siterole-name_ACTION_ADD_ENTRY']",
					RuntimeVariables.replace("Blogs Add Entry"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='roles-siterole-name_ACTION_ADD_ENTRY']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='roles-siterole-name_ACTION_ADD_ENTRY']"));

			case 100:
				label = -1;
			}
		}
	}
}