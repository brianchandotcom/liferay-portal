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

package com.liferay.portalweb.plugins.mail.message.sendmessagetonull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SendMessageToNullTest extends BaseTestCase {
	public void testSendMessageToNull() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Mail Test Page");
		selenium.clickAt("link=Mail Test Page",
			RuntimeVariables.replace("Mail Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"liferay.qa.testing.trunk@gmail.com"),
			selenium.getText("//div/div/div[1]/div/ul/li/span/span/a"));
		selenium.clickAt("//div/div/div[1]/div/ul/li/span/span/a",
			RuntimeVariables.replace("liferay.qa.testing.trunk@gmail.com"));
		Thread.sleep(5000);
		selenium.waitForText("//a[@class='compose-message']", "Compose Email");
		assertEquals(RuntimeVariables.replace("Compose Email"),
			selenium.getText("//a[@class='compose-message']"));
		selenium.clickAt("//a[@class='compose-message']",
			RuntimeVariables.replace("Compose Email"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@id='_1_WAR_mailportlet_subject']");
		selenium.type("//input[@id='_1_WAR_mailportlet_subject']",
			RuntimeVariables.replace("Mail Subject"));
		selenium.clickAt("//input[@value='Send']",
			RuntimeVariables.replace("Send"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Please specify at least one recipient."),
			selenium.getText("//span[@class='message portlet-msg-error']"));
	}
}