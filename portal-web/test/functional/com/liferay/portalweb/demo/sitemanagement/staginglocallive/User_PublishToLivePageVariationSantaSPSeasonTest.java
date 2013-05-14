/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.demo.sitemanagement.staginglocallive;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_PublishToLivePageVariationSantaSPSeasonTest
	extends BaseTestCase {
	public void testUser_PublishToLivePageVariationSantaSPSeason()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertEquals(RuntimeVariables.replace("Staging"),
					selenium.getText(
						"//div[@class='staging-bar']/ul/li[2]/span/a"));
				selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));

				boolean seasonPresent = selenium.isElementPresent("link=Season");

				if (!seasonPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Season",
					RuntimeVariables.replace("Season"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"Season Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));

				boolean santaPresent = selenium.isElementPresent("link=Santa");

				if (!santaPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("link=Santa", RuntimeVariables.replace("Santa"));
				selenium.waitForPageToLoad("30000");

			case 3:
				assertEquals(RuntimeVariables.replace("Santa Description"),
					selenium.getText(
						"//div[@class='layout-branch-description']"));
				assertEquals(RuntimeVariables.replace("Santa"),
					selenium.getText("//form/div/ul/li[2]/span/span"));
				Thread.sleep(5000);
				selenium.clickAt("//span[2]/span/ul/li/strong/a",
					RuntimeVariables.replace("Season Staging"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
				assertEquals(RuntimeVariables.replace(
						"Publish Season to Live now."),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
					RuntimeVariables.replace("Publish Season to Live now."));
				selenium.waitForVisible("//div[2]/div[1]/a");
				Thread.sleep(5000);

				boolean allVisible = selenium.isVisible("_88_rangeAll");

				if (allVisible) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 4:
				selenium.waitForElementPresent("//input[@id='_88_rangeAll']");
				selenium.clickAt("//input[@id='_88_rangeAll']",
					RuntimeVariables.replace("All"));
				selenium.waitForVisible("//input[@value='Publish']");
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}