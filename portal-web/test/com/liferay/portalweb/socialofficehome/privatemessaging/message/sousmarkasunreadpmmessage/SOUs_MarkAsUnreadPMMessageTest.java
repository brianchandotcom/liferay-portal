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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.sousmarkasunreadpmmessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_MarkAsUnreadPMMessageTest extends BaseTestCase {
	public void testSOUs_MarkAsUnreadPMMessage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/socialoffice01/so/dashboard");
				selenium.waitForVisible(
					"//nav/ul/li[contains(.,'Messages')]/a/span");
				selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
					RuntimeVariables.replace("Messages"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//tr[contains(@class, 'unread')]"));
				assertEquals(RuntimeVariables.replace("Private Messaging"),
					selenium.getText("//span[@class='portlet-title-default']"));
				assertEquals("Mark as Unread",
					selenium.getValue("//input[@value='Mark as Unread']"));
				assertEquals("Delete",
					selenium.getValue("//input[@value='Delete']"));
				assertEquals("New Message",
					selenium.getValue("//input[@value='New Message']"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//span[@class='author-sender']"));
				assertEquals(RuntimeVariables.replace("Message Subject"),
					selenium.getText("//div[@class='subject']"));
				assertEquals(RuntimeVariables.replace("Message Body"),
					selenium.getText("//div[@class='body']"));
				assertEquals(RuntimeVariables.replace("Showing 1 result."),
					selenium.getText("//div[@class='search-results']"));
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
				assertFalse(selenium.isChecked(
						"//td[1]/span/span/span/input[2]"));
				selenium.clickAt("//td[1]/span/span/span/input[2]",
					RuntimeVariables.replace("All"));
				assertTrue(selenium.isChecked("//td[1]/span/span/span/input[2]"));
				assertEquals("Mark as Unread",
					selenium.getValue("//input[@value='Mark as Unread']"));
				selenium.clickAt("//input[@value='Mark as Unread']",
					RuntimeVariables.replace("Mark as Unread"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//tr[contains(@class, 'unread')]"));

			case 100:
				label = -1;
			}
		}
	}
}