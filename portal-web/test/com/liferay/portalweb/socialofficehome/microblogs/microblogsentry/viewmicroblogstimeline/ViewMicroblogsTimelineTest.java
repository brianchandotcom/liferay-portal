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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.viewmicroblogstimeline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMicroblogsTimelineTest extends BaseTestCase {
	public void testViewMicroblogsTimeline() throws Exception {
		selenium.open("/web/joebloggs/home/");

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
		assertEquals(RuntimeVariables.replace("Timeline"),
			selenium.getText("link=Timeline"));
		selenium.clickAt("link=Timeline", RuntimeVariables.replace("Timeline"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='my-entry-bubble '])[1]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("xPath=(//div/span/a/img)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs test@liferay.com"),
			selenium.getText("xPath=(//div[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Content"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='my-entry-bubble '])[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("xPath=(//div/span/a/img)[2]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs test@liferay.com"),
			selenium.getText("xPath=(//div[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs Content"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='my-entry-bubble '])[3]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("xPath=(//div/span/a/img)[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs test@liferay.com"),
			selenium.getText("xPath=(//div[@class='user-name'])[3]"));
		assertEquals(RuntimeVariables.replace("Microblogs Content"),
			selenium.getText("xPath=(//div[@class='content'])[3]"));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[@class='my-entry-bubble '])[4]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isVisible("xPath=(//div/span/a/img)[4]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs test@liferay.com"),
			selenium.getText("xPath=(//div[@class='user-name'])[4]"));
		assertEquals(RuntimeVariables.replace("Microblogs Content"),
			selenium.getText("xPath=(//div[@class='content'])[4]"));
	}
}