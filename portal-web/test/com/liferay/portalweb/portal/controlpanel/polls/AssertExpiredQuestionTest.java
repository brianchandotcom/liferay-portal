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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertExpiredQuestionTest extends BaseTestCase {
	public void testAssertExpiredQuestion() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("link=Control Panel")) {
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
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Edited Test Question 2",
			RuntimeVariables.replace("Edited Test Question 2"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isElementPresent(
				"//div[2]/div/span[1]/span/span/input"));
		assertFalse(selenium.isElementPresent("//span[2]/span/span/input"));
		assertFalse(selenium.isElementPresent("//span[3]/span/span/input"));
		assertFalse(selenium.isElementPresent("//span[4]/span/span/input"));
		assertEquals(RuntimeVariables.replace(
				"Voting is disabled because this poll expired on 1/1/08 12:00 AM."),
			selenium.getText("//div[6]"));
	}
}