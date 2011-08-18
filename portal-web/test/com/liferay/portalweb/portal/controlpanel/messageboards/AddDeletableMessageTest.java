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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDeletableMessageTest extends BaseTestCase {
	public void testAddDeletableMessage() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Message Boards",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"S\u00e9cond T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("S\u00e9cond T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Post New Thread']",
			RuntimeVariables.replace("Post New Thread"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_162_subject']",
			RuntimeVariables.replace(
				"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_162_editor']",
			RuntimeVariables.replace(
				"This m\u00e9ssag\u00e9 will b\u00e9 d\u00e9l\u00e9t\u00e9d!"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st M\u00e9ssag\u00e9 to b\u00e9 D\u00e9l\u00e9t\u00e9d"),
			selenium.getText("//a/strong"));
		assertEquals(RuntimeVariables.replace(
				"This m\u00e9ssag\u00e9 will b\u00e9 d\u00e9l\u00e9t\u00e9d!"),
			selenium.getText("//td[2]/div[2]"));
	}
}