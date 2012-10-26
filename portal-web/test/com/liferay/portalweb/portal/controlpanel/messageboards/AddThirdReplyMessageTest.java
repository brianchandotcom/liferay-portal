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
public class AddThirdReplyMessageTest extends BaseTestCase {
	public void testAddThirdReplyMessage() throws Exception {
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
		selenium.clickAt("link=T\u00e9st M\u00e9ssag\u00e9",
			RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//tr[contains(.,'This is a second reply message.')]/td[2]/div/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Reply')]"));
		selenium.click(RuntimeVariables.replace(
				"//tr[contains(.,'This is a second reply message. ')]/td[2]/div/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Reply')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/textarea");
		selenium.type("//td[@id='cke_contents__162_editor']/textarea",
			RuntimeVariables.replace("This is a third reply message."));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__162_editor']/iframe");
		selenium.waitForText("//body", "This is a third reply message.");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[1]"));
		assertEquals(RuntimeVariables.replace(
				"This is a t\u00e9st m\u00e9ssag\u00e9!"),
			selenium.getText("xpath=(//div[@class='thread-body'])[1]"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[2]"));
		assertEquals(RuntimeVariables.replace(
				"This is a t\u00e9st r\u00e9ply m\u00e9ssag\u00e9!"),
			selenium.getText("xpath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[3]"));
		assertEquals(RuntimeVariables.replace("This is a second reply message."),
			selenium.getText("xpath=(//div[@class='thread-body'])[3]"));
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[4]"));
		assertEquals(RuntimeVariables.replace("This is a third reply message."),
			selenium.getText("xpath=(//div[@class='thread-body'])[4]"));
	}
}