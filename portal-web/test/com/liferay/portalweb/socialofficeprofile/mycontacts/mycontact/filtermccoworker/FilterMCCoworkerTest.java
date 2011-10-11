/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficeprofile.mycontacts.mycontact.filtermccoworker;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class FilterMCCoworkerTest extends BaseTestCase {
	public void testFilterMCCoworker() throws Exception {
		selenium.open("/web/joebloggs/profile/");
		assertEquals(RuntimeVariables.replace("My Contacts"),
			selenium.getText("//h1/span[2]"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//span/input")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertTrue(selenium.isVisible("//div/a/img"));
		assertTrue(selenium.isVisible("//li[2]/div/a/img"));
		assertTrue(selenium.isVisible("//span/input"));
		assertTrue(selenium.isVisible("//div/a/img"));
		assertTrue(selenium.isVisible("//li[2]/div/a/img"));
		selenium.typeKeys("//span/input", RuntimeVariables.replace("coworker"));
		Thread.sleep(5000);
		assertTrue(selenium.isVisible("//div/a/img"));
		selenium.mouseOver("//div/a/img");
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln"),
			selenium.getText("//li/div[2]/div"));
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerea@liferay.com"),
			selenium.getText("//div[3]/span"));
		assertFalse(selenium.isVisible("//li[2]/div/a/img"));
		selenium.clickAt("//div/a/img",
			RuntimeVariables.replace("Social Office Coworker"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln"),
			selenium.getText("//div/h1/span"));
		assertTrue(selenium.isTextPresent("socialofficecoworkerea@liferay.com"));
		assertEquals(RuntimeVariables.replace("Coworker"),
			selenium.getText("//div[2]/div/div/div/div[2]/div"));
		assertTrue(selenium.isVisible("//span/input"));
		assertTrue(selenium.isVisible("//div/a/img"));
		assertTrue(selenium.isVisible("//li[2]/div/a/img"));
		selenium.typeKeys("//span/input", RuntimeVariables.replace("coworker"));
		Thread.sleep(5000);
		assertTrue(selenium.isVisible("//div/a/img"));
		selenium.mouseOver("//div/a/img");
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerfn socialofficecoworkermn socialofficecoworkerln"),
			selenium.getText("//li/div[2]/div"));
		assertEquals(RuntimeVariables.replace(
				"socialofficecoworkerea@liferay.com"),
			selenium.getText("//div[3]/span"));
		assertFalse(selenium.isVisible("//li[2]/div/a/img"));
	}
}