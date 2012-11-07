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
public class AssertExpiredQuestionTest extends BaseTestCase {
	public void testAssertExpiredQuestion() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Edited Test Question 2"),
			selenium.getText(
				"//tr[contains(.,'Edited Test Question 2')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Edited Test Question 2')]/td[1]/a",
			RuntimeVariables.replace("Edited Test Question 2"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//div[2]/div/span[1]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//span[2]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//span[3]/span/span/input"));
		assertTrue(selenium.isElementNotPresent("//span[4]/span/span/input"));
		assertEquals(RuntimeVariables.replace(
				"Voting is disabled because this poll expired on 1/1/08 12:00 AM."),
			selenium.getText("//div[6]"));
	}
}