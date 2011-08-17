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

package com.liferay.portalweb.plugins.testclp.settings.savesettingsplaysound;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SaveSettingsPlaySoundTest extends BaseTestCase {
	public void testSaveSettingsPlaySound() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Test CLP Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Test CLP Test Page",
					RuntimeVariables.replace("Test CLP Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("true"),
					selenium.getText(
						"//div[@class='portlet-body']/table/tbody/tr[2]/td[8]"));
				selenium.clickAt("//ul[@class='chat-tabs']/li[2]/div[1]/span",
					RuntimeVariables.replace("Settings"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@id='playSound']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();

				boolean playSoundChecked = selenium.isChecked("playSound");

				if (!playSoundChecked) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isChecked("//input[@id='playSound']"));
				selenium.saveScreenShotAndSource();
				selenium.clickAt("//input[@id='playSound']",
					RuntimeVariables.replace("Play a Sound"));
				assertFalse(selenium.isChecked("//input[@id='playSound']"));
				selenium.saveScreenShotAndSource();

			case 2:
				selenium.clickAt("//input[@id='saveSettings']",
					RuntimeVariables.replace("Save Settings"));

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (!selenium.isElementPresent(
									"//li[@class='chat-settings saved']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				assertFalse(selenium.isElementPresent(
						"//li[@class='chat-settings saved']"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("link=Test CLP Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Test CLP Test Page",
					RuntimeVariables.replace("Test CLP Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("false"),
					selenium.getText(
						"//div[@class='portlet-body']/table/tbody/tr[2]/td[8]"));

			case 100:
				label = -1;
			}
		}
	}
}