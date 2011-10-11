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

package com.liferay.portalweb.stagingcommunity.webcontentdisplay.asserteditwcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertCannotEditWCWebContentWCDTest extends BaseTestCase {
	public void testAssertCannotEditWCWebContentWCD() throws Exception {
		selenium.open("/web/guest/home");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Site Name")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name is staged."),
			selenium.getText("//span[@class='staging-live-group-name']"));
		assertEquals(RuntimeVariables.replace(
				"You are viewing the live version of Site Name and cannot make changes here. Make your changes in staging and publish them to Live afterwards to make them public."),
			selenium.getText("//span[@class='staging-live-help']"));
		assertFalse(selenium.isElementPresent("//img[@alt='Edit Web Content']"));
		assertFalse(selenium.isElementPresent(
				"//img[@alt='Select Web Content']"));
		assertFalse(selenium.isElementPresent("//img[@alt='Add Web Content']"));
	}
}