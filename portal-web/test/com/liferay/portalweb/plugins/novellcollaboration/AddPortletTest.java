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

package com.liferay.portalweb.plugins.novellcollaboration;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPortletTest extends BaseTestCase {
	public void testAddPortlet() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Novell Collaboration Test Page");
		selenium.click(RuntimeVariables.replace(
				"link=Novell Collaboration Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_145_addApplication']",
				"More"));
		selenium.clickAt("//a[@id='_145_addApplication']",
			RuntimeVariables.replace("More"));
		selenium.waitForElementPresent("//li[@title='Bookmark']/p/a");
		selenium.clickAt("//li[@title='Bookmark']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		selenium.waitForElementPresent("//li[@title='GroupWise Calendar']/p/a");
		selenium.clickAt("//li[@title='GroupWise Calendar']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		selenium.waitForVisible("//div[2]/section");
		assertTrue(selenium.isVisible("//div[2]/section"));
		selenium.waitForElementPresent("//li[@title='GroupWise Mail']/p/a");
		selenium.clickAt("//li[@title='GroupWise Mail']/p/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//section");
		assertTrue(selenium.isVisible("//section"));
		selenium.waitForVisible("//div[2]/section");
		assertTrue(selenium.isVisible("//div[2]/section"));
		selenium.waitForVisible("//div[3]/section");
		assertTrue(selenium.isVisible("//div[3]/section"));
	}
}