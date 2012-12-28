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

package com.liferay.portalweb.portal.controlpanel.calendar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddInvalidRepeatEventTest extends BaseTestCase {
	public void testAddInvalidRepeatEvent() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]	");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Calendar", RuntimeVariables.replace("Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@id='_8_allDayCheckbox']",
			RuntimeVariables.replace("All Day Event Checkbox"));
		selenium.waitForVisible("//input[@id='_8_title']");
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Invalid Repeat Test Event"));
		selenium.select("//select[@id='_8_startDateMonth']",
			RuntimeVariables.replace("February"));
		selenium.select("//select[@id='_8_startDateDay']",
			RuntimeVariables.replace("25"));
		selenium.select("//select[@id='_8_startDateYear']",
			RuntimeVariables.replace("2009"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='3']",
			RuntimeVariables.replace("Repeat Daily"));
		selenium.waitForVisible("//input[@id='_8_dailyInterval']");
		selenium.type("//input[@id='_8_dailyInterval']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//input[@name='_8_endDateType' and @value='2']",
			RuntimeVariables.replace("End by"));
		selenium.select("//select[@id='_8_endDateMonth']",
			RuntimeVariables.replace("February"));
		selenium.select("//select[@id='_8_endDateDay']",
			RuntimeVariables.replace("23"));
		selenium.select("//select[@id='_8_endDateYear']",
			RuntimeVariables.replace("2009"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace("Please enter a valid end date."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}