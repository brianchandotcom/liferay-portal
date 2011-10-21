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

package com.liferay.portalweb.portal.controlpanel.bookmarks;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertEntriesTest extends BaseTestCase {
	public void testAssertEntries() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Control Panel")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Bookmarks", RuntimeVariables.replace("Bookmarks"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Entries",
			RuntimeVariables.replace("My Entries"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("exact:http://www.digg.com"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("exact:http://www.liferay.com"),
			selenium.getText("//tr[4]/td[2]/a"));
		selenium.clickAt("link=Recent Entries",
			RuntimeVariables.replace("Recent Entries"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("exact:http://www.digg.com"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("exact:http://www.liferay.com"),
			selenium.getText("//tr[4]/td[2]/a"));
	}
}