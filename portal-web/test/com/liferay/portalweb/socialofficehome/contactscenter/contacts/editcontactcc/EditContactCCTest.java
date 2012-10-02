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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.editcontactcc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditContactCCTest extends BaseTestCase {
	public void testEditContactCC() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForVisible(
			"//nav/ul/li[contains(.,'Contacts Center')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
			RuntimeVariables.replace("Contacts Center"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//input[@id='_1_WAR_contactsportlet_name']"));
		selenium.type("//input[@id='_1_WAR_contactsportlet_name']",
			RuntimeVariables.replace("social"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Social01 Office01 Contact01"),
			selenium.getText("//div[@class='lfr-contact-name']"));
		selenium.clickAt("//div[@class='lfr-contact-name']",
			RuntimeVariables.replace("Social01 Office01 Contact01"));
		selenium.waitForVisible("xPath=(//div[@class='lfr-contact-name'])[2]");
		assertEquals(RuntimeVariables.replace("Social01 Office01 Contact01"),
			selenium.getText("xPath=(//div[@class='lfr-contact-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//button[@id='_1_WAR_contactsportlet_edit']"));
		selenium.clickAt("//button[@id='_1_WAR_contactsportlet_edit']",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible(
			"//input[@id='_1_WAR_contactsportlet_fullName']");
		selenium.type("//input[@id='_1_WAR_contactsportlet_fullName']",
			RuntimeVariables.replace("Social02 Office02 Contact02"));
		selenium.type("//input[@id='_1_WAR_contactsportlet_emailAddress']",
			RuntimeVariables.replace("socialofficecontact02@liferay.com"));
		selenium.type("//textarea[@id='_1_WAR_contactsportlet_comments']",
			RuntimeVariables.replace("Social02 Office02 Contact02 Comments"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//span[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the contact."),
			selenium.getText("//span[@class='portlet-msg-success']"));
	}
}