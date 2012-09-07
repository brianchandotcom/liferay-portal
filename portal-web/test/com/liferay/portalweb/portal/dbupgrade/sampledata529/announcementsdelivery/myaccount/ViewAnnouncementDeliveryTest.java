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

package com.liferay.portalweb.portal.dbupgrade.sampledata529.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAnnouncementDeliveryTest extends BaseTestCase {
	public void testViewAnnouncementDelivery() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("link=My Account");
		selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("announcementsLink", RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked(
				"_2_announcementsTypegeneralEmailCheckbox"));
		assertTrue(selenium.isChecked("_2_announcementsTypegeneralSmsCheckbox"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypegeneralWebsiteCheckbox' and @disabled='disabled']"));
		assertTrue(selenium.isChecked("_2_announcementsTypenewsSmsCheckbox"));
		assertTrue(selenium.isChecked("_2_announcementsTypenewsSmsCheckbox"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypenewsWebsiteCheckbox' and @disabled='disabled']"));
		assertTrue(selenium.isChecked("_2_announcementsTypetestEmailCheckbox"));
		assertTrue(selenium.isChecked("_2_announcementsTypetestSmsCheckbox"));
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypetestWebsiteCheckbox' and @disabled='disabled']"));
	}
}