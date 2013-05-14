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

package com.liferay.portalweb.permissions.wcwebcontentdisplay.wcwebcontent.viewwcwebcontent.guestinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveGuestInlineViewWCWebContentTest extends BaseTestCase {
	public void testRemoveGuestInlineViewWCWebContent()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Web Content Display Test Page",
					RuntimeVariables.replace("Web Content Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("WC WebContent Content"),
					selenium.getText("//div[@class='journal-content-article']"));
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//span[@class='icon-action icon-action-edit']/a"));
				selenium.clickAt("//span[@class='icon-action icon-action-edit']/a",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//span[@class='toolbar-content']/button[contains(.,'Permissions')]"));
				selenium.clickAt("//span[@class='toolbar-content']/button[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'articlePermissions')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'articlePermissions')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");
				assertTrue(selenium.isVisible(
						"//input[@id='guest_ACTION_VIEW']"));

				boolean guestViewChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (!guestViewChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest View"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("WC WebContent Content"),
					selenium.getText("//div[@class='journal-content-article']"));

			case 100:
				label = -1;
			}
		}
	}
}