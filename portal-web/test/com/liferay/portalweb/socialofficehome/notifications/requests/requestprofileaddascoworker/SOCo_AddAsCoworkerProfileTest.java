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

package com.liferay.portalweb.socialofficehome.notifications.requests.requestprofileaddascoworker;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOCo_AddAsCoworkerProfileTest extends BaseTestCase {
	public void testSOCo_AddAsCoworkerProfile() throws Exception {
		selenium.open("/web/joebloggs/profile/");
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//div[2]/div/div/section/header/h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText("//div[2]/div/div[1]/div/div[1]/div/a"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div[2]/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/ul/li[2]/a",
			RuntimeVariables.replace("Add as Coworker"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Coworker Requested"),
			selenium.getText("//div[2]/ul/li[2]/span"));
		selenium.open("/user/socialofficecoworkersn/home/");
	}
}