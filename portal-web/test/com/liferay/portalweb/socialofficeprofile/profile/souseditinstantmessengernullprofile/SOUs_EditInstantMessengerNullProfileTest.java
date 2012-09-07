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

package com.liferay.portalweb.socialofficeprofile.profile.souseditinstantmessengernullprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditInstantMessengerNullProfileTest extends BaseTestCase {
	public void testSOUs_EditInstantMessengerNullProfile()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("Instant Messenger:"),
			selenium.getText("//div[@data-title='Instant Messenger']/h3"));
		assertEquals(RuntimeVariables.replace("AIM"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'AIM')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'AIM')]/span[2]"));
		assertEquals(RuntimeVariables.replace("ICQ"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'ICQ')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'ICQ')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Jabber"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Jabber')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Jabber')]/span[2]"));
		assertEquals(RuntimeVariables.replace("msn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'msn')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'msn')]/span[2]"));
		assertEquals(RuntimeVariables.replace("Skype"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Skype')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'Skype')]/span[2]"));
		assertEquals(RuntimeVariables.replace("ym"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'ym')]/span"));
		assertEquals(RuntimeVariables.replace("socialofficesn"),
			selenium.getText(
				"//div[@data-title='Instant Messenger']/ul/li[contains(.,'ym')]/span[2]"));
		selenium.click("//div[@data-title='Instant Messenger']");
		selenium.waitForVisible("//input[contains(@id,'aimSn')]");
		selenium.type("//input[contains(@id,'aimSn')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'icqSn')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'jabberSn')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'msnSn')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'skypeSn')]",
			RuntimeVariables.replace(""));
		selenium.type("//input[contains(@id,'ymSn')]",
			RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//li[@data-title='Instant Messenger']");
		assertEquals(RuntimeVariables.replace("Instant Messenger"),
			selenium.getText("//li[@data-title='Instant Messenger']"));
		assertFalse(selenium.isTextPresent("Instant Messenger:"));
		assertFalse(selenium.isTextPresent("AIM"));
		assertFalse(selenium.isTextPresent("ICQ"));
		assertFalse(selenium.isTextPresent("Jabber"));
		assertFalse(selenium.isTextPresent("msn"));
		assertFalse(selenium.isTextPresent("Skype"));
		assertFalse(selenium.isTextPresent("ym"));
		assertFalse(selenium.isTextPresent("socialofficesnedit"));
	}
}