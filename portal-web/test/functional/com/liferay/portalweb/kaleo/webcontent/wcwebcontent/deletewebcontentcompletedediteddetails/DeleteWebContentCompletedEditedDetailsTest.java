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

package com.liferay.portalweb.kaleo.webcontent.wcwebcontent.deletewebcontentcompletedediteddetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWebContentCompletedEditedDetailsTest extends BaseTestCase {
	public void testDeleteWebContentCompletedEditedDetails()
		throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Web Content Name Edited (Pending)"),
			selenium.getText("//a[@class='entry-link']/span"));
		assertTrue(selenium.isVisible("//div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace(
				"Web Content Name Edited (Pending)"),
			selenium.getText("//a[@class='entry-link']/span"));
		selenium.clickAt("//div[@class='entry-thumbnail']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"There is a publication workflow in process. Some actions may be disabled depending on the status and your role through this process."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isElementPresent(
				"//input[@value='Submit for Publication' and @disabled='']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='_15_articleToolbar']/span/button[2]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@id='_15_articleToolbar']/span/button[2]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("Web Content Name Edited"),
			selenium.getText("//a[@class='entry-link']/span"));
	}
}