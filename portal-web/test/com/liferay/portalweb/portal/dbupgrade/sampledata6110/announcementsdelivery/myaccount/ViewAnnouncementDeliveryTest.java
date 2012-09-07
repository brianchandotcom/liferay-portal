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

package com.liferay.portalweb.portal.dbupgrade.sampledata6110.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAnnouncementDeliveryTest extends BaseTestCase {
	public void testViewAnnouncementDelivery() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//a[@id='_2_announcementsLink']");
		assertTrue(selenium.isPartialText("//a[@id='_2_announcementsLink']",
				"Announcements"));
		selenium.clickAt("//a[@id='_2_announcementsLink']",
			RuntimeVariables.replace("Announcements"));
		selenium.waitForVisible(
			"//input[@id='_2_announcementsTypegeneralEmailCheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_2_announcementsTypegeneralEmailCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_announcementsTypegeneralSmsCheckbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypegeneralWebsiteCheckbox' and @disabled='']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_announcementsTypenewsEmailCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_announcementsTypenewsSmsCheckbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypenewsWebsiteCheckbox' and @disabled='']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_announcementsTypetestEmailCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_2_announcementsTypetestSmsCheckbox']"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypetestWebsiteCheckbox' and @disabled='']"));
	}
}