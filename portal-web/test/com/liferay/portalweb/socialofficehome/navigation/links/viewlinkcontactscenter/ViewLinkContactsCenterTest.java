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

package com.liferay.portalweb.socialofficehome.navigation.links.viewlinkcontactscenter;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewLinkContactsCenterTest extends BaseTestCase {
	public void testViewLinkContactsCenter() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//nav/ul/li[contains(.,'Contacts Center')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//div[@class='aui-layout contacts-result']"));
		assertTrue(selenium.isVisible("//div[@class='lfr-contact-checkbox']"));
		assertEquals(RuntimeVariables.replace("Contacts Center"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("You have 0 connections."),
			selenium.getText("//a[contains(.,'You have 0 connections.')]"));
		assertEquals(RuntimeVariables.replace("You are following 0 people."),
			selenium.getText("//a[contains(.,'You are following 0 people.')]"));
		assertTrue(selenium.isPartialText("//a[contains(.,'View all')]",
				"View all"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//div/input[1]"));
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//li[3]/span[2]"));
	}
}