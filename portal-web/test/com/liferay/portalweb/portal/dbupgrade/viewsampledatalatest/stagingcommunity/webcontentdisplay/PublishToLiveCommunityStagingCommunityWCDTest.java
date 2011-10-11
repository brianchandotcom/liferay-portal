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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingcommunity.webcontentdisplay;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveCommunityStagingCommunityWCDTest extends BaseTestCase {
	public void testPublishToLiveCommunityStagingCommunityWCD()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open(
					"/web/community-staging-community-web-content-display-staging/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"link=Page Staging Community Web Content Display")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Page Staging Community Web Content Display",
					RuntimeVariables.replace(
						"Page Staging Community Web Content Display"));
				selenium.waitForPageToLoad("30000");

				boolean markAsReadyPresent = selenium.isElementPresent(
						"//button[3]");

				if (!markAsReadyPresent) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace(
						"Mark as Ready for Publication"),
					selenium.getText("//button[3]"));
				selenium.clickAt("//button[3]",
					RuntimeVariables.replace("Mark as Ready for Publication"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace(
									"Status: Ready for Publication")
												.equals(selenium.getText(
										"//span[@class='workflow-status']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Status: Ready for Publication"),
					selenium.getText("//span[@class='workflow-status']"));

			case 2:
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Staging Dropdown"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Publish to Live Now"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a",
					RuntimeVariables.replace("Publish to Live Now"));
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//input[@value='Publish']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));

			case 100:
				label = -1;
			}
		}
	}
}