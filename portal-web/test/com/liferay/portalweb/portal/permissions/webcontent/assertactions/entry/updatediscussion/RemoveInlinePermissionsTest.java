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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.updatediscussion;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveInlinePermissionsTest extends BaseTestCase {
	public void testRemoveInlinePermissions() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("WC WebContent Title"),
					selenium.getText("//a[@class='entry-link']/span"));
				selenium.clickAt("//a[@class='entry-link']/span",
					RuntimeVariables.replace("WC WebContent Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[contains(@id,'articleToolbar')]/span/button[contains(.,'Permissions')]"));
				selenium.clickAt("//div[contains(@id,'articleToolbar')]/span/button[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'articlePermissions')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'articlePermissions')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/search_container.js')]");

				boolean guestAddDiscussionChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_ADD_DISCUSSION']");

				if (!guestAddDiscussionChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_ADD_DISCUSSION']",
					RuntimeVariables.replace("Guest Add Discussion"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_DELETE_DISCUSSION']"));

				boolean guestViewChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (!guestViewChecked) {
					label = 3;

					continue;
				}

				selenium.uncheck("//input[@id='guest_ACTION_VIEW']");

			case 3:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_ADD_DISCUSSION']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_DELETE']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_DELETE_DISCUSSION']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_EXPIRE']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_PERMISSIONS']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_UPDATE']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_UPDATE_DISCUSSION']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}