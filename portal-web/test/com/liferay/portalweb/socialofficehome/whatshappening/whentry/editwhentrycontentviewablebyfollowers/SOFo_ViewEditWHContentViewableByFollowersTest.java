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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.editwhentrycontentviewablebyfollowers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOFo_ViewEditWHContentViewableByFollowersTest extends BaseTestCase {
	public void testSOFo_ViewEditWHContentViewableByFollowers()
		throws Exception {
		selenium.open("/web/socialofficefollowersn/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
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

		selenium.clickAt("//div/div[1]/div/div/div/ul[1]/li[1]/a",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("What's happening?"),
			selenium.getText("//div[1]/h1/span"));
		assertTrue(selenium.isVisible("//div[@class='entry-bubble ']"));
		assertTrue(selenium.isVisible("//div[1]/div/div/div[2]/div/span/a/img"));
		assertTrue(selenium.isPartialText(
				"//div[2]/div/div[1]/div/div/div[2]/div/div/div[1]",
				"Joe Bloggs test@liferay.com"));
		assertEquals(RuntimeVariables.replace("Whats Happening Content Edit"),
			selenium.getText("//div[1]/div/div/div[2]/div/div/div[2]/span"));
	}
}