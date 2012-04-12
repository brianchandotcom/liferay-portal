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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontent151character;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWHEntryContent151CharacterTest extends BaseTestCase {
	public void testAddWHEntryContent151Character() throws Exception {
		selenium.open("/user/joebloggs/so/dashboard");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='_2_WAR_microblogsportlet_highlighterContent']"));
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("//div[@id='_2_WAR_microblogsportlet_highlighterContent']",
			RuntimeVariables.replace("Update your status..."));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isElementPresent("//textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//textarea", RuntimeVariables.replace("Text area"));
		selenium.typeKeys("//textarea",
			RuntimeVariables.replace(
				"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15|"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1")
										.equals(selenium.getText(
								"//span[@class='microblogs-countdown microblogs-countdown-warned']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("-1"),
			selenium.getText(
				"//span[@class='microblogs-countdown microblogs-countdown-warned']"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		Thread.sleep(5000);
		selenium.open("/user/joebloggs/so/dashboard");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='_2_WAR_microblogsportlet_highlighterContent']"));
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("//div[@class='portlet-msg-info']"));

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
		assertEquals(RuntimeVariables.replace("You have no microblogs entry."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}