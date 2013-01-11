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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_AddMemberAnnouncementTest extends BaseTestCase {
	public void testSA_AddMemberAnnouncement() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Announcements Permissions Page",
			RuntimeVariables.replace("Announcements Permissions Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Manage Entries",
			RuntimeVariables.replace("Manage Entries"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_84_distributionScope']",
			RuntimeVariables.replace("Site Member"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Entry']",
			RuntimeVariables.replace("Add Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_84_title']",
			RuntimeVariables.replace("Test Member Announcement"));
		selenium.type("//input[@id='_84_url']",
			RuntimeVariables.replace("http://www.liferay.com"));
		selenium.waitForElementPresent(
			"//textarea[@id='_84_editor' and @style='display: none;']");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__84_editor']/textarea");
		selenium.type("//td[@id='cke_contents__84_editor']/textarea",
			RuntimeVariables.replace("This is a test Member Announcement."));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__84_editor']/iframe");
		selenium.waitForElementPresent(
			"//textarea[@id='_84_editor' and @style='display: none;']");
		selenium.selectFrame("//td[@id='cke_contents__84_editor']/iframe");
		selenium.waitForText("//body", "This is a test Member Announcement.");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Member Announcement"),
			selenium.getText(
				"//tr[contains(.,'Test Member Announcement')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText(
				"//tr[contains(.,'Test Member Announcement')]/td[2]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Test Member Announcement')]/td[3]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Test Member Announcement')]/td[4]/a"));
		assertTrue(selenium.isElementPresent(
				"//tr[contains(.,'Test Member Announcement')]/td[5]/a"));
	}
}