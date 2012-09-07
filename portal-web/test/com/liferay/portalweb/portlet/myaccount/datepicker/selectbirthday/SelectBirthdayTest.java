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

package com.liferay.portalweb.portlet.myaccount.datepicker.selectbirthday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectBirthdayTest extends BaseTestCase {
	public void testSelectBirthday() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Joe Bloggs");
		selenium.clickAt("link=Joe Bloggs",
			RuntimeVariables.replace("Joe Bloggs"));
		Thread.sleep(5000);
		selenium.waitForVisible("//select[@id='_2_birthdayMonth']");
		Thread.sleep(5000);
		selenium.click("//select[@id='_2_birthdayMonth']");
		selenium.select("//select[@id='_2_birthdayMonth']",
			RuntimeVariables.replace("March"));
		selenium.waitForSelectedLabel("//select[@id='_2_birthdayMonth']",
			"March");
		selenium.select("//select[@id='_2_birthdayYear']",
			RuntimeVariables.replace("1986"));
		selenium.waitForSelectedLabel("//select[@id='_2_birthdayYear']", "1986");
		selenium.waitForVisible("//button[@id='buttonTest']");
		selenium.clickAt("//button[@id='buttonTest']",
			RuntimeVariables.replace("Date Picker"));
		selenium.waitForVisible("//div[@class='aui-calendar-title']");
		assertEquals(RuntimeVariables.replace("March 1986"),
			selenium.getText("//div[@class='aui-calendar-title']"));
		selenium.clickAt("link=Prev", RuntimeVariables.replace("Prev"));
		selenium.waitForText("//div[@class='aui-calendar-title']", "April 1986");
		assertEquals(RuntimeVariables.replace("April 1986"),
			selenium.getText("//div[@class='aui-calendar-title']"));
		selenium.clickAt("link=Prev", RuntimeVariables.replace("Prev"));
		selenium.waitForText("//div[@class='aui-calendar-title']", "May 1986");
		assertEquals(RuntimeVariables.replace("May 1986"),
			selenium.getText("//div[@class='aui-calendar-title']"));
		selenium.waitForVisible("link=31");
		selenium.clickAt("link=31", RuntimeVariables.replace("31"));
		selenium.waitForNotVisible("//div[@class='aui-calendar-title']");
		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
	}
}