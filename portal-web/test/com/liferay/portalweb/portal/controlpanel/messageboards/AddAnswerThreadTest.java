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
public class AddAnswerThreadTest extends BaseTestCase {
	public void testAddAnswerThread() throws Exception {
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
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st Cat\u00e9gory Edit\u00e9d"),
			selenium.getText(
				"//tr[contains(.,'T\u00e9st Cat\u00e9gory Edit\u00e9d')]/td[2]/a/strong"));
		selenium.clickAt("//tr[contains(.,'T\u00e9st Cat\u00e9gory Edit\u00e9d')]/td[2]/a/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory Edit\u00e9d"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Waiting for an Answer"),
			selenium.getText(
				"//tr[contains(.,'Favorite Color Test Question')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'Favorite Color Test Question')]/td[3]/a",
			RuntimeVariables.replace("Waiting for an Answer"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText(
				"//tr[contains(.,'Favorite Color Test Question')]/td[2]/div/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Reply')]"));
		selenium.click(RuntimeVariables.replace(
				"//tr[contains(.,'Favorite Color Test Question')]/td[2]/div/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Reply')]"));
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
			RuntimeVariables.replace(
				"I like green because it is so natural. Obviously."));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent(
			"//textarea[@id='_162_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__162_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__162_editor']/iframe");
		selenium.waitForText("//body",
			"I like green because it is so natural. Obviously.");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"RE: Favorite Color Test Question"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Favorite Color Test Question"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[1]"));
		assertEquals(RuntimeVariables.replace("What is your favorite color?"),
			selenium.getText("xpath=(//div[@class='thread-body'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"RE: Favorite Color Test Question"),
			selenium.getText("xpath=(//div[@class='subject']/a/strong)[2]"));
		assertEquals(RuntimeVariables.replace(
				"I like green because it is so natural. Obviously."),
			selenium.getText("xpath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("Mark as an Answer"),
			selenium.getText(
				"//tr[contains(.,'RE: Favorite Color Test Question')]/td[2]/div/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Mark as an Answer')]"));
		selenium.clickAt("//tr[contains(.,'RE: Favorite Color Test Question')]/td[2]/div/ul[@class='edit-controls lfr-component']/li/span/a[contains(.,'Mark as an Answer')]",
			RuntimeVariables.replace("Mark as an Answer"));
		selenium.waitForVisible(
			"//tr[contains(.,'RE: Favorite Color Test Question')]/td[2]/div/div/div[@class='answer']");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'RE: Favorite Color Test Question')]/td[2]/div/div/div[@class='answer']"));
		selenium.clickAt("link=T\u00e9st Cat\u00e9gory Edit\u00e9d",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory Edit\u00e9d"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Favorite Color Test Question"),
			selenium.getText(
				"//tr[contains(.,'Favorite Color Test Question')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Resolved"),
			selenium.getText(
				"//tr[contains(.,'Favorite Color Test Question')]/td[3]/a"));
		assertFalse(selenium.isTextPresent("Waiting for an Answer"));
	}
}