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

package com.liferay.portalweb.plugins.mail.message.deletemessagenullsentmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteMessageNullSentMailTest extends BaseTestCase {
	public void testDeleteMessageNullSentMail() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForVisible("link=Mail Test Page");
		selenium.click(RuntimeVariables.replace("link=Mail Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"liferay.qa.testing.trunk@gmail.com"),
			selenium.getText("//div/div/div[1]/div/ul/li/span/span/a"));
		selenium.clickAt("//div/div/div[1]/div/ul/li/span/span/a",
			RuntimeVariables.replace("liferay.qa.testing.trunk@gmail.com"));
		selenium.waitForVisible("//a[contains(.,'Sent Mail')]");
		assertTrue(selenium.isPartialText("//a[contains(.,'Sent Mail')]",
				"Sent Mail"));
		selenium.clickAt("//a[contains(.,'Sent Mail')]",
			RuntimeVariables.replace("Sent Mail"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@value='Delete']");
		selenium.clickAt("//input[@value='Delete']",
			RuntimeVariables.replace("Delete"));
		selenium.waitForText("//span[@class='message portlet-msg-error']",
			"No messages selected.");
		assertEquals(RuntimeVariables.replace("No messages selected."),
			selenium.getText("//span[@class='message portlet-msg-error']"));
	}
}