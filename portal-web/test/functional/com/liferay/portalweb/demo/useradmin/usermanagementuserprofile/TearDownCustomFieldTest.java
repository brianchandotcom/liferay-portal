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

package com.liferay.portalweb.demo.useradmin.usermanagementuserprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownCustomFieldTest extends BaseTestCase {
	public void testTearDownCustomField() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Custom Fields",
					RuntimeVariables.replace("Custom Fields"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("User"),
					selenium.getText("//tr[13]/td[1]/a/strong"));
				selenium.clickAt("//tr[13]/td[1]/a/strong",
					RuntimeVariables.replace("User"));
				selenium.waitForPageToLoad("30000");

				boolean CustomFieldsPresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a");

				if (!CustomFieldsPresent) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[3]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"No custom fields are defined for User."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 2:
			case 100:
				label = -1;
			}
		}
	}
}