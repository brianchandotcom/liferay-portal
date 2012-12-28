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
public class EditEventTest extends BaseTestCase {
	public void testEditEvent() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Test Event"),
			selenium.getText("//tr[contains(.,'Test Event')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[3]"));
		selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a/span)[3]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Edited Test Event"));
		selenium.waitForElementPresent(
			"//textarea[@id='_8_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__8_editor']/textarea");
		selenium.type("//td[@id='cke_contents__8_editor']/textarea",
			RuntimeVariables.replace(
				"This is a test event! This test event has been edited."));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_8_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__8_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__8_editor']/iframe");
		selenium.waitForText("//body",
			"This is a test event! This test event has been edited.");
		selenium.selectFrame("relative=top");
		selenium.select("//select[@name='_8_startDateHour']",
			RuntimeVariables.replace("5"));
		selenium.select("//select[@name='_8_startDateAmPm']",
			RuntimeVariables.replace("AM"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Edited Test Event"),
			selenium.getText("//tr[contains(.,'Edited Test Event')]/td[2]/a"));
	}
}