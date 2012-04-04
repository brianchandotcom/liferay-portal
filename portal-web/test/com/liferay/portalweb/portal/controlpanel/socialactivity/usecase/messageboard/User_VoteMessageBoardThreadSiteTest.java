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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.messageboard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_VoteMessageBoardThreadSiteTest extends BaseTestCase {
	public void testUser_VoteMessageBoardThreadSite() throws Exception {
		selenium.open("/web/site-name/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']/a"));
		selenium.clickAt("//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']/a",
			RuntimeVariables.replace("MB Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertTrue(selenium.isElementPresent(
				"//a[contains(@class,'aui-rating-thumb-up')]"));
		selenium.clickAt("//a[contains(@class,'aui-rating-thumb-up')]",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
	}
}