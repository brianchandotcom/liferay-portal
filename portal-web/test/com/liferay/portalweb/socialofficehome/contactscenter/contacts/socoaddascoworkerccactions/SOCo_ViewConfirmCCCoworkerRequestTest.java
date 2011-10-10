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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.socoaddascoworkerccactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOCo_ViewConfirmCCCoworkerRequestTest extends BaseTestCase {
	public void testSOCo_ViewConfirmCCCoworkerRequest()
		throws Exception {
		selenium.open("/user/socialofficecoworkersn/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div/div/div[1]/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//div/div/div/div[1]/ul/li[2]/a",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//div[1]/a/img"));
		assertEquals(RuntimeVariables.replace("Manage 1 coworkers."),
			selenium.getText("//div[1]/div/div/div/div[2]/a"));
		selenium.mouseOver("//div[1]/a/img");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//li/div[2]/div[1]"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[3]/span"));
	}
}