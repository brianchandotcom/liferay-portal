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

package com.liferay.portalweb.demo.sitemanagement.brazilianworldcup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewPrivatePagesSiteBWCTest extends BaseTestCase {
	public void testGuest_ViewPrivatePagesSiteBWC() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.openWindow("http://www.baker.com:8080",
			RuntimeVariables.replace("home"));
		selenium.waitForPopUp("home", RuntimeVariables.replace(""));
		selenium.selectWindow("home");
		Thread.sleep(5000);
		Thread.sleep(5000);
		selenium.waitForVisible("//a[@class='logo custom-logo']");
		assertTrue(selenium.isVisible("//a[@class='logo custom-logo']"));
		assertTrue(selenium.isElementPresent("//img[@height='156']"));
		assertTrue(selenium.isElementPresent("//img[@width='320']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='logo default-logo']"));
		assertTrue(selenium.isElementPresent(
				"//body[@class='green yui3-skin-sam controls-visible page-maximized signed-out public-page site']"));
		assertTrue(selenium.isVisible("link=Home"));
		assertTrue(selenium.isVisible("link=Arenas"));
		assertTrue(selenium.isElementNotPresent("link=Accommodations"));
		assertTrue(selenium.isElementNotPresent("link=Maps"));
		assertEquals(RuntimeVariables.replace("Welcome to Brazil"),
			selenium.getText("//footer[@id='footer']"));
		Thread.sleep(5000);
		Thread.sleep(5000);
		selenium.close();
		selenium.selectWindow("null");
	}
}