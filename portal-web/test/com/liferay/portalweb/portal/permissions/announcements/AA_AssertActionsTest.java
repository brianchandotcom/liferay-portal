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
public class AA_AssertActionsTest extends BaseTestCase {
	public void testAA_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Announcements Permissions Page",
			RuntimeVariables.replace("Announcements Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Edit')]/td[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Delete')]/td[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Mark as Read"),
			selenium.getText(
				"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Mark as Read')]/td[3]/a"));
		selenium.clickAt("//div[@class='edit-actions']/table/tbody/tr[contains(.,'Mark as Read')]/td[3]/a",
			RuntimeVariables.replace("Mark as Read"));
		selenium.waitForVisible(
			"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Show')]/td[3]/a");
		selenium.clickAt("//div[@class='edit-actions']/table/tbody/tr[contains(.,'Show')]/td[3]/a",
			RuntimeVariables.replace("Show"));
		selenium.waitForVisible(
			"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Hide')]/td[3]/a");
		selenium.clickAt("//div[@class='edit-actions']/table/tbody/tr[contains(.,'Hide')]/td[3]/a",
			RuntimeVariables.replace("Hide"));
		selenium.waitForVisible(
			"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Show')]/td[3]/a");
		selenium.waitForElementNotPresent(
			"//div[@class='edit-actions']/table/tbody/tr[contains(.,'Hide')]/td[3]/a");
		selenium.clickAt("link=Manage Entries",
			RuntimeVariables.replace("Manage Entries"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//input[@value='Add Entry']"));
	}
}