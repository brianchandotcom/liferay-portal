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

package com.liferay.portalweb.socialofficehome.activities.microblogsentryactivity.viewmicroblogsentryactivityme;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsEntryActivityMeTest extends BaseTestCase {
	public void testViewMicroblogsEntryActivityMe() throws Exception {
		selenium.open("/user/socialofficefriendsn/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//div/div[1]/div/div/div/ul[1]/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div/div[1]/div/div/div/ul[1]/li[1]/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("//div[2]/section/header/h1/span"));
		assertEquals(RuntimeVariables.replace("Me"),
			selenium.getText("//li[5]/span/span/a"));
		selenium.clickAt("//li[5]/span/span/a", RuntimeVariables.replace("Me"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("//div[1]/span[1]/img"));
		assertTrue(selenium.isVisible("//span[2]/span/img"));
		assertEquals(RuntimeVariables.replace("Microblogs Content"),
			selenium.getText("//section/div/div/div/div/div/div[2]/div[1]"));
		assertTrue(selenium.isPartialText(
				"//section/div/div/div/div/div/div[2]/div[2]", "Joe"));
	}
}