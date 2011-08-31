/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAnnouncementDeliveryTest extends BaseTestCase {
	public void testViewAnnouncementDelivery() throws Exception {
		selenium.open("/web/guest/home");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Joe Bloggs")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Joe Bloggs", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("announcementsLink", RuntimeVariables.replace(""));
		assertTrue(selenium.isChecked(
				"_2_announcementsTypegeneralEmailCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_2_announcementsTypegeneralSmsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypegeneralWebsiteCheckbox' and @disabled='']"));
		assertTrue(selenium.isChecked("_2_announcementsTypenewsSmsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_2_announcementsTypenewsSmsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypenewsWebsiteCheckbox' and @disabled='']"));
		assertTrue(selenium.isChecked("_2_announcementsTypetestEmailCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isChecked("_2_announcementsTypetestSmsCheckbox"));
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isElementPresent(
				"//input[@id='_2_announcementsTypetestWebsiteCheckbox' and @disabled='']"));
	}
}