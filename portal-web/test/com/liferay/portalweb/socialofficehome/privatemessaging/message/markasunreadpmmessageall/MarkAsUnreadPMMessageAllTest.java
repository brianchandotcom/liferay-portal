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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.markasunreadpmmessageall;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MarkAsUnreadPMMessageAllTest extends BaseTestCase {
	public void testMarkAsUnreadPMMessageAll() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/user/joebloggs/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//nav/ul/li[1]/a/span")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//div[2]/div[1]/ul/li[4]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[2]/div[1]/ul/li[4]/a",
					RuntimeVariables.replace("Private Messaging"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Private Messaging"),
					selenium.getText("//h1/span[2]"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//td[3]/div[1]/div/a"));
				assertEquals(RuntimeVariables.replace(
						"Message3 Subject\n Message3 Body"),
					selenium.getText("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//tr[4]/td[3]/div[1]/div/a"));
				assertEquals(RuntimeVariables.replace(
						"Message2 Subject\n Message2 Body"),
					selenium.getText("//tr[4]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//tr[5]/td[3]/div[1]/div/a"));
				assertEquals(RuntimeVariables.replace(
						"Message Subject\n Message Body"),
					selenium.getText("//tr[5]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("Showing 3 results."),
					selenium.getText("//div[2]/div[2]/div/div"));
				assertTrue(selenium.isElementPresent(
						"//td[1]/span/span/span/input[2]"));

				boolean message1Checked = selenium.isChecked(
						"//td[1]/span/span/span/input[2]");

				if (!message1Checked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Uncheck"));

			case 2:
				assertTrue(selenium.isElementPresent(
						"//tr[4]/td[1]/span/span/span/input[2]"));

				boolean message2Checked = selenium.isChecked(
						"//tr[4]/td[1]/span/span/span/input[2]");

				if (!message2Checked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//tr[4]/td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Uncheck"));

			case 3:
				assertTrue(selenium.isElementPresent(
						"//tr[5]/td[1]/span/span/span/input[2]"));

				boolean message3Checked = selenium.isChecked(
						"//tr[5]/td[1]/span/span/span/input[2]");

				if (!message3Checked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//tr[5]/td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("Uncheck"));

			case 4:
				assertFalse(selenium.isChecked(
						"//td[1]/span/span/span/input[2]"));
				assertFalse(selenium.isChecked(
						"//tr[4]/td[1]/span/span/span/input[2]"));
				assertFalse(selenium.isChecked(
						"//tr[5]/td[1]/span/span/span/input[2]"));
				assertEquals(RuntimeVariables.replace("All"),
					selenium.getText("//div/span[1]/a"));
				selenium.clickAt("//div/span[1]/a",
					RuntimeVariables.replace("All"));
				assertEquals("Mark as Unread",
					selenium.getValue("//input[@value='Mark as Unread']"));
				selenium.clickAt("//input[@value='Mark as Unread']",
					RuntimeVariables.replace("Mark as Unread"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"xPath=(//tr[contains(@class, 'unread')])[1]"));
				assertTrue(selenium.isElementPresent(
						"xPath=(//tr[contains(@class, 'unread')])[2]"));
				assertTrue(selenium.isElementPresent(
						"xPath=(//tr[contains(@class, 'unread')])[3]"));

			case 100:
				label = -1;
			}
		}
	}
}