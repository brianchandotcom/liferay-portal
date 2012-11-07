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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddQuestion2Test extends BaseTestCase {
	public void testAddQuestion2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Question']",
			RuntimeVariables.replace("Add Question"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_25_title_en_US']",
			RuntimeVariables.replace("Test Poll Question 2"));
		selenium.type("//textarea[@id='_25_description_en_US']",
			RuntimeVariables.replace("This is a test poll 2 description."));
		selenium.type("//input[@id='_25_choiceDescriptiona_en_US']",
			RuntimeVariables.replace("Test Choice A"));
		selenium.type("//input[@id='_25_choiceDescriptionb_en_US']",
			RuntimeVariables.replace("Test Choice B"));
		selenium.clickAt("//input[@value='Add Choice']",
			RuntimeVariables.replace("Add Choice"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_25_choiceDescriptionc_en_US']",
			RuntimeVariables.replace("Test Choice C"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test Poll Question 2"),
			selenium.getText("//tr[contains(.,'Test Poll Question 2')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Test Poll Question 2')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Test Poll Question 2')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Never"),
			selenium.getText("//tr[contains(.,'Test Poll Question 2')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Test Poll Question 2')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
	}
}