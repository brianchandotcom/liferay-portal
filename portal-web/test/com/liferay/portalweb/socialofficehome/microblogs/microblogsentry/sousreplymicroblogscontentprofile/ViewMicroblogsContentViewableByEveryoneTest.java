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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.sousreplymicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsContentViewableByEveryoneTest extends BaseTestCase {
	public void testViewMicroblogsContentViewableByEveryone()
		throws Exception {
		selenium.open("/user/joebloggs/so/dashboard");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='_2_WAR_microblogsportlet_autocompleteContent']"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//nav/ul/li[contains(.,'Microblogs')]/a/span")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//div[@class='user-name']/span"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
	}
}