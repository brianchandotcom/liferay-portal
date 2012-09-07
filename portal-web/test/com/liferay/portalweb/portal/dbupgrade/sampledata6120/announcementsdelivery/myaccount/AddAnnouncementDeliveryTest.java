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

package com.liferay.portalweb.portal.dbupgrade.sampledata6120.announcementsdelivery.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAnnouncementDeliveryTest extends BaseTestCase {
	public void testAddAnnouncementDelivery() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertTrue(selenium.isPartialText(
						"//a[@id='_2_announcementsLink']", "Announcements"));
				selenium.clickAt("//a[@id='_2_announcementsLink']",
					RuntimeVariables.replace("Announcements"));
				selenium.waitForVisible(
					"//input[@id='_2_announcementsTypegeneralEmailCheckbox']");

				boolean generalEmailChecked = selenium.isChecked(
						"_2_announcementsTypegeneralEmailCheckbox");

				if (generalEmailChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_2_announcementsTypegeneralEmailCheckbox']",
					RuntimeVariables.replace("General Email Checkbox"));

			case 2:

				boolean generalSmsChecked = selenium.isChecked(
						"_2_announcementsTypegeneralSmsCheckbox");

				if (generalSmsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_2_announcementsTypegeneralSmsCheckbox']",
					RuntimeVariables.replace("General SMS Checkbox"));

			case 3:

				boolean newsEmailChecked = selenium.isChecked(
						"_2_announcementsTypenewsEmailCheckbox");

				if (newsEmailChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_2_announcementsTypenewsEmailCheckbox']",
					RuntimeVariables.replace("News Email Checkbox"));

			case 4:

				boolean newsSmsChecked = selenium.isChecked(
						"_2_announcementsTypenewsSmsCheckbox");

				if (newsSmsChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_2_announcementsTypenewsSmsCheckbox']",
					RuntimeVariables.replace("News SMS Checkbox"));

			case 5:

				boolean testEmailChecked = selenium.isChecked(
						"_2_announcementsTypetestEmailCheckbox");

				if (testEmailChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_2_announcementsTypetestEmailCheckbox']",
					RuntimeVariables.replace("Test Email Checkbox"));

			case 6:

				boolean testSmsChecked = selenium.isChecked(
						"_2_announcementsTypetestSmsCheckbox");

				if (testSmsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_2_announcementsTypetestSmsCheckbox']",
					RuntimeVariables.replace("Test SMS Checkbox"));

			case 7:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
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

			case 100:
				label = -1;
			}
		}
	}
}