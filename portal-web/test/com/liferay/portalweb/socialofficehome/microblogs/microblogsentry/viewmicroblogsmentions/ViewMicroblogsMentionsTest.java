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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.viewmicroblogsmentions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsMentionsTest extends BaseTestCase {
	public void testViewMicroblogsMentions() throws Exception {
		selenium.open("/web/joebloggs/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//ul[2]/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//ul[2]/li[2]/a",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Microblogs"),
			selenium.getText("//div[2]/div/div/section/header/h1"));
		assertEquals(RuntimeVariables.replace("Mentions"),
			selenium.getText("link=Mentions"));
		selenium.clickAt("link=Mentions", RuntimeVariables.replace("Mentions"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("//div[@class='entry-bubble ']"));
		assertTrue(selenium.isVisible("//div/span/a/img"));
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln socialofficefriendea@liferay.com"),
			selenium.getText("//div[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[2]/span/a"));
		assertTrue(selenium.isPartialText("//div[@class='content']",
				"Microblogs Content Repl"));
		assertFalse(selenium.isElementPresent(
				"//div[@class='my-entry-bubble ']"));
		assertFalse(selenium.isTextPresent("Joe Bloggs test@liferay.com"));
	}
}