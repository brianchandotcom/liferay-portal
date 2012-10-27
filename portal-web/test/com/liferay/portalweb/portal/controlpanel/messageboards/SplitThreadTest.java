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

package com.liferay.portalweb.portal.controlpanel.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SplitThreadTest extends BaseTestCase {
	public void testSplitThread() throws Exception {
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
		selenium.clickAt("link=Message Boards",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//td[2]/a/strong"));
		selenium.clickAt("//td[2]/a/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//td[2]/a/strong"));
		selenium.clickAt("//td[2]/a/strong",
			RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Split Thread"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Split Thread')]"));
		selenium.click(RuntimeVariables.replace(
				"//ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Split Thread')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Click OK to create a new thread with the following messages."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.clickAt("//input[@value='OK']", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"This is a t\u00e9st m\u00e9ssag\u00e9!"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[1]"));
		assertEquals(RuntimeVariables.replace(
				"This is a t\u00e9st r\u00e9ply m\u00e9ssag\u00e9!"),
			selenium.getText("xpath=(//div[@class='thread-body'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"RE: RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[2]"));
		assertEquals(RuntimeVariables.replace("This is a second reply message."),
			selenium.getText("xpath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[3]"));
		assertEquals(RuntimeVariables.replace("This is a third reply message."),
			selenium.getText("xpath=(//div[@class='thread-body'])[3]"));
		selenium.clickAt("link=T\u00e9st Subcat\u00e9gory",
			RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("link=T\u00e9st M\u00e9ssag\u00e9"));
		assertEquals(RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//tr[4]/td[2]/a"));
	}
}