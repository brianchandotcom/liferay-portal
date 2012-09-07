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

package com.liferay.portalweb.portlet.calendar.event.addeventdaterepeatinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventDateRepeatInvalidTest extends BaseTestCase {
	public void testAddEventDateRepeatInvalid() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_8_title",
			RuntimeVariables.replace("Invalid End Date Test Event"));
		selenium.waitForElementPresent("_8_dailyInterval");
		selenium.type("_8_dailyInterval", RuntimeVariables.replace("1"));
		selenium.clickAt("_8_endDateMonth",
			RuntimeVariables.replace("End Date Month"));
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=February"));
		Thread.sleep(5000);
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=February"));
		assertFalse(selenium.isPartialText("_8_endDateDay", "30"));
	}
}