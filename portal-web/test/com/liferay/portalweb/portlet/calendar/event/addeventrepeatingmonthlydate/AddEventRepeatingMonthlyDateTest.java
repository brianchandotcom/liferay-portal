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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingmonthlydate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingMonthlyDateTest extends BaseTestCase {
	public void testAddEventRepeatingMonthlyDate() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_8_startDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_startDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_startDateYear",
			RuntimeVariables.replace("label=2010"));
		selenium.type("_8_title",
			RuntimeVariables.replace("Monthly Date Repeating Event"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='5']",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@name='_8_monthlyType' and @value='1']",
			RuntimeVariables.replace(""));
		selenium.waitForElementPresent("_8_monthlyPos");
		selenium.select("_8_monthlyPos", RuntimeVariables.replace("label=First"));
		selenium.select("_8_monthlyDay1",
			RuntimeVariables.replace("label=Thursday"));
		selenium.type("_8_monthlyInterval1", RuntimeVariables.replace("1"));
		selenium.clickAt("//fieldset[2]/div/div/div/span[2]/span/span/input",
			RuntimeVariables.replace(""));
		selenium.select("_8_endDateMonth",
			RuntimeVariables.replace("label=January"));
		selenium.select("_8_endDateDay", RuntimeVariables.replace("label=1"));
		selenium.select("_8_endDateYear", RuntimeVariables.replace("label=2011"));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Monthly Date Repeating Event",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Monthly Date Repeating Event"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("1/1/10"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("1/1/11"),
			selenium.getText("//dl[@class='property-list']/dd[2]"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.clickAt("//td[6]/span/ul/li/strong/a",
			RuntimeVariables.replace(""));
		selenium.waitForElementPresent(
			"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals("January", selenium.getSelectedLabel("_8_startDateMonth"));
		assertEquals("1", selenium.getSelectedLabel("_8_startDateDay"));
		assertEquals("2010", selenium.getSelectedLabel("_8_startDateYear"));
		assertEquals("Monthly Date Repeating Event",
			selenium.getValue("_8_title"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_recurrenceType' and @value='5']"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_monthlyType' and @value='1']"));
		selenium.waitForElementPresent("_8_monthlyPos");
		assertEquals("First", selenium.getSelectedLabel("_8_monthlyPos"));
		assertEquals("Thursday", selenium.getSelectedLabel("_8_monthlyDay1"));
		assertEquals("1", selenium.getValue("_8_monthlyInterval1"));
		assertTrue(selenium.isChecked(
				"//fieldset[2]/div/div/div/span[2]/span/span/input"));
		assertEquals("January", selenium.getSelectedLabel("_8_endDateMonth"));
		assertEquals("1", selenium.getSelectedLabel("_8_endDateDay"));
		assertEquals("2011", selenium.getSelectedLabel("_8_endDateYear"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2010"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 4, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 4, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Monthly Date Repeating Event"));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2010"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(2, 4, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 4, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"link=Monthly Date Repeating Event"));
		selenium.clickAt("link=Year", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=2010"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(2, 11, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 11, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"link=Monthly Date Repeating Event"));
	}
}