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

package com.liferay.portalweb.portlet.messageboards.message.previouscategorymessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PreviousCategoryMessageTest extends BaseTestCase {
	public void testPreviousCategoryMessage() throws Exception {
		selenium.open("/web/guest/home/");

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
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread1 Message Subject"),
			selenium.getText("//tr[5]/td[1]/a"));
		selenium.clickAt("//tr[5]/td[1]/a",
			RuntimeVariables.replace("MB Category Thread1 Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread1 Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//div[@class='thread-navigation']/a"));
		selenium.clickAt("//div[@class='thread-navigation']/a",
			RuntimeVariables.replace("Previous"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread2 Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//div[@class='thread-navigation']/a"));
		selenium.clickAt("//div[@class='thread-navigation']/a",
			RuntimeVariables.replace("Previous"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread3 Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
	}
}