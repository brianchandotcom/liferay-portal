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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.followccusermultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs2_ViewFollowCCUserMultipleTest extends BaseTestCase {
	public void testSOUs2_ViewFollowCCUserMultiple() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice02/so/dashboard/");
		selenium.waitForVisible(
			"//nav/ul/li[contains(.,'Contacts Center')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("You have 0 connections."),
			selenium.getText("link=You have 0 connections."));
		selenium.clickAt("link=You have 0 connections.",
			RuntimeVariables.replace("You have 0 connections."));
		selenium.waitForText("//div[@class='empty']", "There are no results.");
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//div[@class='empty']"));
		assertEquals(RuntimeVariables.replace("You are following 0 people."),
			selenium.getText("link=You are following 0 people."));
		selenium.clickAt("link=You are following 0 people.",
			RuntimeVariables.replace("You are following 0 people."));
		selenium.waitForText("//div[@class='empty']", "There are no results.");
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//div[@class='empty']"));
	}
}