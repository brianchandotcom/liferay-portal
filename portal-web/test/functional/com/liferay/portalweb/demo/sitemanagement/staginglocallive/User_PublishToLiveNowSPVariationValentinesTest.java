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
public class User_PublishToLiveNowSPVariationValentinesTest extends BaseTestCase {
	public void testUser_PublishToLiveNowSPVariationValentines()
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

				boolean valentinesPresent = selenium.isElementPresent(
						"link=Valentines");

				if (!valentinesPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=Valentines",
					RuntimeVariables.replace("Valentines"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace("Valentines"),
					selenium.getText("//li[4]/span/span/span[1]"));
				assertEquals(RuntimeVariables.replace(
						"Valentines Site Pages Variation of Site Name"),
					selenium.getText(
						"//span[@class='layout-set-branch-description']"));
				selenium.waitForVisible("link=Prices");
				selenium.clickAt("link=Prices",
					RuntimeVariables.replace("Prices"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.clickAt("//span[2]/span/ul/li/strong/a",
					RuntimeVariables.replace(""));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
				assertEquals(RuntimeVariables.replace(
						"Publish Valentines to Live now."),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
					RuntimeVariables.replace("Publish Valentines to Live now."));
				selenium.waitForVisible("//div[2]/div[1]/a");
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@value='Change Selection']");
				selenium.clickAt("//input[@value='Change Selection']",
					RuntimeVariables.replace("Change Selection"));
				selenium.waitForText("//div[@class='portlet-msg-info']",
					"Note that selecting no pages from the tree reverts to implicit selection of all pages.");
				assertEquals(RuntimeVariables.replace(
						"Note that selecting no pages from the tree reverts to implicit selection of all pages."),
					selenium.getText("//div[@class='portlet-msg-info']"));

				boolean treeNotExpanded = selenium.isElementPresent(
						"//div[contains(@class,'aui-tree-expanded')]");

				if (treeNotExpanded) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));
				selenium.clickAt("//li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));
				selenium.waitForVisible(
					"//div[contains(@class,'aui-tree-expanded')]");
				assertTrue(selenium.isVisible(
						"//div[contains(@class,'aui-tree-expanded')]"));
				assertTrue(selenium.isVisible(
						"//div[contains(@class,'aui-tree-collapsed')]"));

			case 3:
				Thread.sleep(5000);
				selenium.waitForVisible("//div[4]");
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//div[4]"));
				selenium.clickAt("//div[4]",
					RuntimeVariables.replace("Public Pages"));
				selenium.clickAt("//div[4]",
					RuntimeVariables.replace("Public Pages"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Prices"),
					selenium.getText("//li[5]/div/div[4]"));
				selenium.clickAt("//li[5]/div/div[4]",
					RuntimeVariables.replace("Prices"));
				Thread.sleep(5000);
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
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