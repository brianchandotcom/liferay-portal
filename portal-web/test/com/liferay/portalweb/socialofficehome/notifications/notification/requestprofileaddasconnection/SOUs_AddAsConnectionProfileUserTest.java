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

package com.liferay.portalweb.socialofficehome.notifications.notification.requestprofileaddasconnection;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_AddAsConnectionProfileUserTest extends BaseTestCase {
	public void testSOUs_AddAsConnectionProfileUser() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/joebloggs/so/profile/");
		selenium.waitForVisible("//nav/ul/li[contains(.,'Profile')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Profile')]/a/span",
			RuntimeVariables.replace("Profile"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[@class='lfr-contact-extra']"));
		assertEquals(RuntimeVariables.replace("Add Connection"),
			selenium.getText("//span[@class='action add-connection']/a"));
		selenium.clickAt("//span[@class='action add-connection']/a",
			RuntimeVariables.replace("Add Connection"));
		selenium.waitForVisible("//span[@class='disabled']/span");
		assertEquals(RuntimeVariables.replace("Connection Requested"),
			selenium.getText("//span[@class='disabled']/span"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='action add-connection']/a"));
	}
}