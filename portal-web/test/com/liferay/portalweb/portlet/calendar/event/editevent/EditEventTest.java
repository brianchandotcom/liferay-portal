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

package com.liferay.portalweb.portlet.calendar.event.editevent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditEventTest extends BaseTestCase {
	public void testEditEvent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[3]/td[2]/a"));
		Thread.sleep(5000);
		selenium.clickAt("//td[4]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForElementPresent(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("_8_title", RuntimeVariables.replace("Test Event Edited"));
		selenium.type("_8_description",
			RuntimeVariables.replace("This is a Test Event. Edited."));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		assertEquals(RuntimeVariables.replace("Test Event Edited"),
			selenium.getText(
				"//table[@class='taglib-search-iterator']/tbody/tr[3]/td[2]/a"));
	}
}