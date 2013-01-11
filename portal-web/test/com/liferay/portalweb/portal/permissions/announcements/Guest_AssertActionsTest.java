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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertActionsTest extends BaseTestCase {
	public void testGuest_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Announcements Permissions Page",
			RuntimeVariables.replace("Announcements Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Edit')]/td[1]/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Delete')]/td[2]/span/a"));
		assertTrue(selenium.isElementNotPresent("link=Manage Entries"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Mark as Read')]/td[3]/a"));
	}
}