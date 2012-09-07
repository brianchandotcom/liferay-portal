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
public class ViewEditContactCCTest extends BaseTestCase {
	public void testViewEditContactCC() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Social02 Office02 Contact02"),
			selenium.getText("//div[@class='lfr-contact-name']"));
		selenium.clickAt("//div[@class='lfr-contact-name']",
			RuntimeVariables.replace("Social02 Office02 Contact02"));
		selenium.waitForVisible("xPath=(//div[@class='lfr-contact-name'])[2]");
		assertEquals(RuntimeVariables.replace("Social02 Office02 Contact02"),
			selenium.getText("xPath=(//div[@class='lfr-contact-name'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"socialofficecontact02@liferay.com"),
			selenium.getText("xPath=(//div[@class='lfr-contact-extra'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"Social02 Office02 Contact02 Comments"),
			selenium.getText("//div[@class='comments']"));
	}
}