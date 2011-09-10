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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessageattachment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPMMessageAttachmentTest extends BaseTestCase {
	public void testViewPMMessageAttachment() throws Exception {
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("//div[2]/div[1]/ul/li[4]/a",
			RuntimeVariables.replace("Private Messaging"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Private Messaging"),
			selenium.getText("//h1/span[2]"));
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
			selenium.getText("//td[3]/div[1]/div/a"));
		assertEquals(RuntimeVariables.replace(
				"Message Subject\n Message Subject Reply"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[2]/div[2]/div/div"));
		selenium.clickAt("//td[4]/a",
			RuntimeVariables.replace("Message Subject\n Message Subject Reply"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Message Subject"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"Between socialofficefriendfn socialofficefriendmn socialofficefriendln and you"),
			selenium.getText("//div/div/div/div[3]/div"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[2]/div[1]/div/span[1]/a"));
		assertTrue(selenium.isPartialText("//td[2]/div[2]/div", "Message Body"));
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
			selenium.getText("//tr[4]/td[2]/div[1]/div/span[1]/a"));
		assertTrue(selenium.isPartialText("//tr[4]/td[2]/div[2]/div",
				"Message Subject Reply"));
		assertTrue(selenium.isVisible("//div/img"));
		assertEquals(RuntimeVariables.replace("PM_Attachment.jpg"),
			selenium.getText("//td[2]/a"));
	}
}